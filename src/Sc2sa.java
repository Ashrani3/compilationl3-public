import sc.analysis.*;
import sa.*;
import sc.node.*;

public class Sc2sa extends DepthFirstAdapter
{
    private SaNode returnValue;

    public SaNode getRoot() {
        return returnValue;
    }

    private <T> T retrieveNode(Node node) {
        node.apply(this);
        //noinspection unchecked
        return (T) returnValue;
    }

    @Override
    public void caseStart(Start node) {
        retrieveNode(node.getPProgramme());
    }

    @Override
    public void caseADecvarldecfoncProgramme(ADecvarldecfoncProgramme node) {
        returnValue = new SaProg(retrieveNode(node.getOptdecvar()), retrieveNode(node.getListedecfonc()));
    }

    @Override
    public void caseALdecfoncProgramme(ALdecfoncProgramme node) {
        returnValue = new SaProg(null, retrieveNode(node.getListedecfonc()));
    }

    @Override
    public void caseAOptdecvar(AOptdecvar node) {
        retrieveNode(node.getListedecvar());
    }

    @Override
    public void caseADecvarldecvarListedecvar(ADecvarldecvarListedecvar node) {
        this.returnValue = new SaLDec(
                retrieveNode(node.getDecvar()),
                retrieveNode(node.getListedecvarbis())
        );
    }

    @Override
    public void caseADecvarListedecvar(ADecvarListedecvar node) {
        this.returnValue = new SaLDec(retrieveNode(node.getDecvar()), null);
    }

    @Override
    public void caseADecvarldecvarListedecvarbis(ADecvarldecvarListedecvarbis node) {
        this.returnValue = new SaLDec(
                retrieveNode(node.getDecvar()),
                retrieveNode(node.getListedecvarbis())
        );
    }

    @Override
    public void caseADecvarListedecvarbis(ADecvarListedecvarbis node) {
        this.returnValue = new SaLDec(retrieveNode(node.getDecvar()), null);
    }

    @Override
    public void caseADecvarentierDecvar(ADecvarentierDecvar node) {
        String nom = node.getIdentif().getText();
        this.returnValue = new SaDecVar(nom);
    }

    @Override
    public void caseADecvartableauDecvar(ADecvartableauDecvar node) {
        String nom = node.getIdentif().getText();
        int capacite = Integer.parseInt(node.getNombre().getText());
        this.returnValue=  new SaDecTab(nom, capacite);
    }

    @Override
    public void caseALdecfoncrecListedecfonc(ALdecfoncrecListedecfonc node) {
        this.returnValue = new SaLDec(
                retrieveNode(node.getDecfonc()),
                retrieveNode(node.getListedecfonc())
        );
    }

    @Override
    public void caseALdecfoncfinalListedecfonc(ALdecfoncfinalListedecfonc node) {
        // TODO
        this.returnValue = null;
    }

    @Override
    public void caseADecvarinstrDecfonc(ADecvarinstrDecfonc node) {
        String nom = node.getIdentif().getText();
        returnValue = new SaDecFonc(
                nom,
                retrieveNode(node.getListeparam()),
                retrieveNode(node.getOptdecvar()),
                retrieveNode(node.getInstrbloc())
        );
    }

    @Override
    public void caseAInstrDecfonc(AInstrDecfonc node) {
        String nom = node.getIdentif().getText();
        returnValue = new SaDecFonc(
                nom,
                retrieveNode(node.getListeparam()),
                null,
                retrieveNode(node.getInstrbloc())
        );
    }

    @Override
    public void caseASansparamListeparam(ASansparamListeparam node) {
        returnValue = null;
    }

    @Override
    public void caseAAvecparamListeparam(AAvecparamListeparam node) {
        retrieveNode(node.getListedecvar());
    }

    @Override
    public void caseAInstraffectInstr(AInstraffectInstr node) {
        retrieveNode(node.getInstraffect());
    }

    @Override
    public void caseAInstrblocInstr(AInstrblocInstr node) {
        retrieveNode(node.getInstrbloc());
    }

    @Override
    public void caseAInstrsiInstr(AInstrsiInstr node) {
        retrieveNode(node.getInstrsi());
    }

    @Override
    public void caseAInstrtantqueInstr(AInstrtantqueInstr node) {
        retrieveNode(node.getInstrtantque());
    }

    @Override
    public void caseAInstrappelInstr(AInstrappelInstr node) {
        retrieveNode(node.getInstrappel());
    }

    @Override
    public void caseAInstrretourInstr(AInstrretourInstr node) {
        retrieveNode(node.getInstrretour());
    }

    @Override
    public void caseAInstrecritureInstr(AInstrecritureInstr node) {
        retrieveNode(node.getInstrecriture());
    }

    @Override
    public void caseAInstrvideInstr(AInstrvideInstr node) {
        this.returnValue = null;
    }

    @Override
    public void caseAInstraffect(AInstraffect node) {
        returnValue = new SaInstAffect(
                retrieveNode(node.getVar()),
                retrieveNode(node.getExp())
        );
    }

    @Override
    public void caseAInstrbloc(AInstrbloc node) {
        returnValue = new SaInstBloc(retrieveNode(node.getListeinst()));
    }

    @Override
    public void caseALinstrecListeinst(ALinstrecListeinst node) {
        returnValue = new SaLInst(
                retrieveNode(node.getInstr()),
                retrieveNode(node.getListeinst())
        );
    }

    @Override
    public void caseALinstfinalListeinst(ALinstfinalListeinst node) {
        this.returnValue = null;
    }

    @Override
    public void caseAAvecsinonInstrsi(AAvecsinonInstrsi node) {
        this.returnValue = new SaInstSi(
                retrieveNode(node.getExp()),
                retrieveNode(node.getInstrbloc()),
                retrieveNode(node.getInstrsinon())
        );
    }

    @Override
    public void caseASanssinonInstrsi(ASanssinonInstrsi node) {
        this.returnValue = new SaInstSi(
                retrieveNode(node.getExp()),
                retrieveNode(node.getInstrbloc()),
                null
        );
    }

    @Override
    public void caseAInstrsinon(AInstrsinon node) {
        retrieveNode(node.getInstrbloc());
    }

    @Override
    public void caseAInstrtantque(AInstrtantque node) {
        this.returnValue = new SaInstTantQue(
                retrieveNode(node.getExp()),
                retrieveNode(node.getInstrbloc())
        );
    }

    @Override
    public void caseAInstrappel(AInstrappel node) {
        // TODO
        retrieveNode(node.getAppelfct());
    }

    @Override
    public void caseAInstrretour(AInstrretour node) {
        this.returnValue = new SaInstRetour(
                retrieveNode(node.getExp())
        );
    }

    @Override
    public void caseAInstrecriture(AInstrecriture node) {
        this.returnValue = new SaInstEcriture(
                retrieveNode(node.getExp())
        );
    }

    @Override
    public void caseAInstrvide(AInstrvide node) {
        this.returnValue = null;
    }

    @Override
    public void caseAExp1Exp(AExp1Exp node) {
        retrieveNode(node.getExp1());
    }

    @Override
    public void caseAEtExp1(AEtExp1 node) {
        this.returnValue = new SaExpAnd(
                retrieveNode(node.getExp1()),
                retrieveNode(node.getExp2())
        );
    }

    @Override
    public void caseAExp2Exp1(AExp2Exp1 node) {
        retrieveNode(node.getExp2());
    }

    @Override
    public void caseAExp3Exp2(AExp3Exp2 node) {
        retrieveNode(node.getExp3());
    }

    @Override
    public void caseAExp4Exp3(AExp4Exp3 node) {
        retrieveNode(node.getExp4());
    }

    @Override
    public void caseAExp5Exp4(AExp5Exp4 node) {
        retrieveNode(node.getExp5());
    }

    @Override
    public void caseAExp6Exp5(AExp6Exp5 node) {
        retrieveNode(node.getExp6());
    }

    @Override
    public void caseAParenthesesExp6(AParenthesesExp6 node) {
        // TODO
        retrieveNode(node.getExp());
    }

    @Override
    public void caseALireExp6(ALireExp6 node) {
        this.returnValue = new SaExpLire();
    }

    @Override
    public void caseAVartabVar(AVartabVar node) {
        this.returnValue = new SaVarIndicee(
                node.getIdentif().getText(),
                retrieveNode(node.getExp())
        );
    }

    @Override
    public void caseAVarsimpleVar(AVarsimpleVar node) {
        this.returnValue = new SaVarSimple(
                node.getIdentif().getText()
        );
    }

    @Override
    public void caseARecursifListeexp(ARecursifListeexp node) {
        this.returnValue = new SaLExp(
                retrieveNode(node.getExp()),
                retrieveNode(node.getListeexpbis())
        );
    }

    @Override
    public void caseAFinalListeexp(AFinalListeexp node) {
        this.returnValue = null;
    }

    @Override
    public void caseARecursifListeexpbis(ARecursifListeexpbis node) {
        this.returnValue = new SaLExp(
                retrieveNode(node.getExp()),
                retrieveNode(node.getListeexpbis())
        );
    }

    @Override
    public void caseAFinalListeexpbis(AFinalListeexpbis node) {
        this.returnValue = null;
    }

    @Override
    public void caseAAppelfct(AAppelfct node) {
        this.returnValue = new SaAppel(
                node.getIdentif().getText(),
                retrieveNode(node.getListeexp())
        );
    }

    public void caseAOuExp(AOuExp node){
        SaExp op1 =null;
        SaExp op2 =null;
        node.getExp().apply(this);
        op1 = (SaExp) this.returnValue;
        node.getExp1().apply(this);
        op2 = (SaExp)this.returnValue;
        this.returnValue =new SaExpOr(op1, op2);
    }

    public void caseAEtExp(AEtExp1 node){
        SaExp op1 =null;
        SaExp op2 =null;
        node.getExp1().apply(this);
        op1 = (SaExp) this.returnValue;
        node.getExp2().apply(this);
        op2 = (SaExp)this.returnValue;
        this.returnValue =new SaExpAnd(op1, op2);
    }

    public void caseAInfExp2(AInfExp2 node) {
        SaExp op1 =null;
        SaExp op2 =null;
        node.getExp2().apply(this);
        op1 = (SaExp) this.returnValue;
        node.getExp3().apply(this);
        op2 = (SaExp)this.returnValue;
        this.returnValue =new SaExpInf(op1, op2);
    }

    public void caseAEgalExp2(AEgalExp2 node) {
        SaExp op1 =null;
        SaExp op2 =null;
        node.getExp2().apply(this);
        op1 = (SaExp) this.returnValue;
        node.getExp3().apply(this);
        op2 = (SaExp)this.returnValue;
        this.returnValue =new SaExpEqual(op1, op2);
    }

    public void caseAPlusExp3(APlusExp3 node) {
        SaExp op1 =null;
        SaExp op2 =null;
        node.getExp3().apply(this);
        op1 = (SaExp) this.returnValue;
        node.getExp4().apply(this);
        op2 = (SaExp)this.returnValue;
        this.returnValue =new SaExpAdd(op1, op2);
    }

    public void caseAMoinsExp3(AMoinsExp3 node) {
        SaExp op1 =null;
        SaExp op2 =null;
        node.getExp3().apply(this);
        op1 = (SaExp) this.returnValue;
        node.getExp4().apply(this);
        op2 = (SaExp)this.returnValue;
        this.returnValue =new SaExpSub(op1, op2);
    }

    public void caseAFoisExp4(AFoisExp4 node) {
        SaExp op1 =null;
        SaExp op2 =null;
        node.getExp4().apply(this);
        op1 = (SaExp) this.returnValue;
        node.getExp5().apply(this);
        op2 = (SaExp)this.returnValue;
        this.returnValue =new SaExpMult(op1, op2);
    }

    public void caseADiviseExp4(ADiviseExp4 node) {
        SaExp op1 =null;
        SaExp op2 =null;
        node.getExp4().apply(this);
        op1 = (SaExp) this.returnValue;
        node.getExp5().apply(this);
        op2 = (SaExp)this.returnValue;
        this.returnValue =new SaExpDiv(op1, op2);
    }

    public void caseANonExp5(ANonExp5 node) {
        SaExp op1 =null;
        node.getExp5().apply(this);
        op1 = (SaExp)this.returnValue;
        this.returnValue =new SaExpNot(op1);
    }

   public void caseANombreExp6(ANombreExp6 node) {
        this.returnValue =new SaExpInt(Integer.parseInt(node.getNombre().getText()));
    }

    public void caseAVarExp6(AVarExp6 node) {
        this.returnValue =new SaExpVar(retrieveNode(node.getVar()));
    }

    public void caseAAppelfctExp6(AAppelfctExp6 node) {
        this.returnValue =new SaExpAppel(retrieveNode(node.getAppelfct()));
    }
}