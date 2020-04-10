import sa.*;
import ts.*;

import java.util.HashMap;
import java.util.Map;

public class Sa2ts extends SaDepthFirstVisitor <Void> {

    Ts GlobalTable;
    Ts LocalTable;

    public Sa2ts(SaNode saRoot) {
        super();
    }

    public Void visit(SaVarSimple node) {
        if (node == null)
            throw new RuntimeException("The node is empty.");

        String identificator = node.getNom();
        if (LocalTable == null || !LocalTable.containsVar(identificator) ) {

            if (!GlobalTable.containsVar(identificator)) {
                throw new RuntimeException("Variable " + identificator + " isn't defined.");
            }
            else node.tsItem = GlobalTable.getVar(identificator);
        }
        node.tsItem = LocalTable.getVar(identificator);
        return super.visit(node);
    }

    @Override
    public Void visit(SaVarIndicee node) {
        if (node == null)
            throw new RuntimeException("The node is empty.");

        String identificator = node.getNom();
        if (!GlobalTable.containsVar(identificator))
            throw new RuntimeException("Table " + identificator + " isn't defined.");
        if (node.getIndice() == null)
            throw new RuntimeException("Table must have an index.");
        node.tsItem = GlobalTable.getVar(identificator);
        return super.visit(node);
    }

    @Override
    public Void visit(SaAppel node) {
        if (node == null)
            throw new RuntimeException("The node is empty.");

        String identificator = node.getNom();
        if (!GlobalTable.containsFonc(identificator)) {

            throw new RuntimeException("Function " + identificator + "isn't defined.");
        }
        node.tsItem = GlobalTable.getFct(identificator);
        return super.visit(node);
    }

    @Override
    public Void visit(SaDecTab node) {
        if (node == null)
            throw new RuntimeException("The node is empty.");

        if (LocalTable != null)
        {
            throw new RuntimeException("A table can't be a local variable.");
        }
        String identificator = node.getNom();
        int taille = node.getTaille();
        TsItemVar variable = GlobalTable.addVar(identificator, taille);
        node.tsItem = variable;
        return super.visit(node);
    }

    @Override
    public Void visit(SaDecFonc node) {
        if (node == null)
            throw new RuntimeException("The node is empty.");

        String identificator = node.getNom();
        if (GlobalTable.containsFonc(identificator))
            throw new RuntimeException("This function already exist.");

        int nbArgs = node.getParametres() == null ? 0 : node.getParametres().length();
        this.LocalTable = new Ts();
        if (nbArgs != 0)
        {
            SaLDec queue = node.getParametres();
            SaDec tete = queue.getTete();
            while(queue != null)
            {
                LocalTable.addParam(tete.getNom());
                queue = queue.getQueue();
                if (queue != null)
                    tete = queue.getTete();
            }
        }
        if (node.getVariable() != null)
        {
            SaLDec queue = node.getVariable();
            SaDec tete = queue.getTete();
            while(queue != null)
            {
                LocalTable.addVar(tete.getNom(),1);
                queue = queue.getQueue();
                if (queue != null)
                    tete = queue.getTete();
            }
        }

        TsItemFct fonction = GlobalTable.addFct(identificator, nbArgs, LocalTable, node);
        node.tsItem = fonction;

        return super.visit(node);
    }

    @Override
    public Void visit(SaDecVar node) {
        if (node == null)
            throw new RuntimeException("The node is empty.");

        String identificator = node.getNom();
        TsItemVar variable;
        if((LocalTable == null && GlobalTable.containsVar(identificator))||LocalTable.containsVar(identificator))
        {
            throw new RuntimeException("Variable "+identificator+" is already exist.");
        }
        if(LocalTable != null)
        {
            variable = LocalTable.addVar(identificator,1);
        }else{
            variable = GlobalTable.addVar(identificator,1);
        }
        node.tsItem = variable;
        return super.visit(node);
    }

    public Ts getTableGlobale() {
        return GlobalTable;
    }
}
