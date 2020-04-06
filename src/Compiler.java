import sa.Sa2Xml;
import sa.SaNode;
import sc.parser.*;
import sc.lexer.*;
import sc.node.*;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
//import sa.*;
//import ts.*;
//import c3a.*;
//import nasm.*;
//import fg.*;

public class Compiler {
    public static void main(String[] args) {
        ArrayList<String> fileNames = new ArrayList<>();

        for (File file : Objects.requireNonNull(Paths.get("test", "input").toFile().listFiles())) {
            if (file.isFile() && file.getAbsolutePath().endsWith(".l")) {
                fileNames.add(file.getAbsolutePath());
            }
        }

        for (String fileName : fileNames) {
            System.out.println(fileName);

            PushbackReader br = null;
            String baseName = null;
            try {
                br = new PushbackReader(new FileReader(fileName));
                baseName = removeSuffix(fileName, ".l");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                // Create a Parser instance.
                Parser p = new Parser(new Lexer(br));
                // Parse the input.
                Start tree = p.parse();

                System.out.println("[SC]");
                tree.apply(new Sc2Xml(baseName));

				System.out.println("[SA]");
				Sc2sa sc2sa = new Sc2sa();
				tree.apply(sc2sa);
				SaNode saRoot = sc2sa.getRoot();
                System.out.println("saRoot = " + saRoot);
				new Sa2Xml(saRoot, baseName);

				/*System.out.println("[TABLE SYMBOLES]");
				Ts table = new Sa2ts(saRoot).getTableGlobale();
				table.afficheTout(baseName);

				System.out.println("[C3A]");
				C3a c3a = new Sa2c3a(saRoot, table).getC3a();
				c3a.affiche(baseName);

				System.out.println("[NASM]");
				Nasm nasm = new C3a2nasm(c3a, table).getNasm();
				nasm.affiche(baseName);

				System.out.println("[FLOW GRAPH]");
				Fg fg = new Fg(nasm);
				fg.affiche(baseName);

				System.out.println("[FLOW GRAPH SOLVE]");
				FgSolution fgSolution = new FgSolution(nasm, fg);
				fgSolution.affiche(baseName);*/


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static String removeSuffix(final String s, final String suffix) {
        if (s != null && suffix != null && s.endsWith(suffix)) {
            return s.substring(0, s.length() - suffix.length());
        }
        return s;
    }

}
