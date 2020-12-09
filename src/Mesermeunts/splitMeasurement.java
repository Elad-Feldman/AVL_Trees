package Mesermeunts;

import Tree.AVLTree;
import com.sun.tools.javac.util.ArrayUtils;




public class splitMeasurement {

    public static long[] randomSplit(int i) {
        // load tree
        AVLTree t = new AVLTree();
        int[] nodes = sort.createRandArray(1000*i);
        splitMes.loadTree(nodes, t);

        // split
        int randomKey = nodes[0];
        t.split(randomKey);


        long avgJoin = t.getSplitComplexity() / t.getNumJoins(); // average join complexity for average split
        long maxJoin = t.getMaxSplitComplexity(); // max join complexity for average split

        return new long[]{avgJoin, maxJoin};
    }

    public static long[] leftLeafSplit(int i) {
        // load tree
        AVLTree t = new AVLTree();
        int[] nodes = sort.createRandArray(1000*i);
        splitMes.loadTree(nodes, t);

        // split
        int leftLeafKey = t.findMaxInLeftTree(t.getRoot());
        t.split(leftLeafKey);


        long avgJoin = t.getSplitComplexity() / t.getNumJoins(); // average join complexity for left leaf split
        long maxJoin = t.getMaxSplitComplexity(); // max join complexity for left leaf split

        return new long[]{avgJoin, maxJoin};
    }



    public  static void loadTree(int[] values, AVLTree tree) {
        for (int val : values) {
            String info = Integer.toString(val);
            tree.insert(val, info);
        }
    }



    public static void main(String[] args) {
        long[] random,leftLeaf;


        for (int i = 1; i <= 10; i++) {
            random = randomSplit(i);
            leftLeaf = leftLeafSplit(i);

            //long[] results = {random[0], random[1], leftLeaf[0], leftLeaf[1]};

            String SPACE ="        ";
            String SPACE2 ="                            ";


            System.out.println("n = "+10000*i);
            System.out.println("avgJoinRandomSplit" + SPACE + "maxJoinRandomSplit" + SPACE + "avgJoinLeftLeafSplit" + SPACE + "maxJoinLeftLeafSplit" );
            System.out.println(random[0] + SPACE2 + random[1] + SPACE2 + leftLeaf[0] + SPACE2 + leftLeaf[1] );
            System.out.println();

        }




    }




}
