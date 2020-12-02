package Tree;

import java.util.ArrayList;
import java.util.List;

public class printableTree extends AVLTree {
     //you should implement IAVLnode
    // you should write insert(k, info)
    public String getText(IAVLNode node, String what2print){
         String result="";

        if (node.isRealNode()){
            int h = node.getHeight();
            int[] diff =node.rankDiff();
            int key = node.getKey();

            if (what2print=="d")
                return "(" +diff[0]+ ","+ diff[1] + ")";
            if (what2print=="kh")
                return  "(k"+ key +",h:"+h+")";
            if (what2print=="khd")
                return  +diff[0]- diff[1] + ","+key;
            if (what2print=="")
                return  ""+key;
            if (what2print=="sk")
                return  key +","+node.getSize();

        }
        return "*";

    }

    public void printTree(String what2print) {
        IAVLNode root = this.getRoot();
        List<List<String>> lines = new ArrayList<List<String>>();

        List<IAVLNode> level = new ArrayList<IAVLNode>();
        List<IAVLNode> next = new ArrayList<IAVLNode>();

        level.add(root);
        int nn = 1;

        int widest = 0;

        while (nn != 0) {
            List<String> line = new ArrayList<String>();

            nn = 0;

            for (IAVLNode n : level) {
                if (n == null) {
                    line.add(null);

                    next.add(null);
                    next.add(null);
                } else {
                    String aa = getText(n,what2print);
                    line.add(aa);
                    if (aa.length() > widest) widest = aa.length();

                    next.add(n.getLeft());
                    next.add(n.getRight());

                    if (n.getLeft() != null) nn++;
                    if (n.getRight() != null) nn++;
                }
            }

            if (widest % 2 == 1) widest++;

            lines.add(line);

            List<IAVLNode> tmp = level;
            level = next;
            next = tmp;
            next.clear();
        }

        int perpiece = lines.get(lines.size()-2).size() * (widest+8);
        for (int i = 0; i < lines.size(); i++) {
            List<String> line = lines.get(i);
            int hpw = (int) Math.floor(perpiece / 2f) - 1;

            if (i > 0) {
                for (int j = 0; j < line.size(); j++) {

                    // split node
                    char c = ' ';
                    if (j % 2 == 1) {
                        if (line.get(j - 1) != null) {
                            c = (line.get(j) != null) ? '┴' : '┘';
                        } else {
                            if (j < line.size() && line.get(j) != null) c = '└';
                        }
                    }
                    System.out.print(c);

                    // lines and spaces
                    if (line.get(j) == null) {
                        for (int k = 0; k < perpiece - 1; k++) {
                            System.out.print(" ");
                        }
                    } else {

                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? " " : "─");
                        }
                        System.out.print(j % 2 == 0 ? "┌" : "┐");
                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? "─" : " ");
                        }
                    }
                }
                System.out.println();
            }

            // print line of numbers
            for (int j = 0; j < line.size(); j++) {

                String f = line.get(j);
                if (f == null) f = "";
                int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
                int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);

                // a number
                for (int k = 0; k < gap1; k++) {
                    System.out.print(" ");
                }
                System.out.print(f);
                for (int k = 0; k < gap2; k++) {
                    System.out.print(" ");
                }
            }
            System.out.println();

            perpiece /= 2;
        }
    }


}