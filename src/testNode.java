import org.junit.Assert;
import org.junit.Test;
import java.util.Random;

public class testNode {


    @Test
    public void testNodeSimple() {
        AVLTree tree = new AVLTree();
        AVLTree.IAVLNode n1 = tree.new AVLNode(1, "*");
        AVLTree.IAVLNode n2 = tree.new AVLNode(2, "!");
        //geting the right keys and values
        Assert.assertEquals(1, n1.getKey());
        Assert.assertEquals(2, n2.getKey());
        Assert.assertEquals("*", n1.getValue());
        Assert.assertEquals("!", n2.getValue());

        Assert.assertEquals(0, n1.getHeight());

        //sons are virtual leafs
        Assert.assertFalse(n1.getRight().isRealNode());
        Assert.assertFalse(n1.getLeft().isRealNode());

        n1.setRight(n2);
        Assert.assertEquals(1, n1.getHeight()); //got the max height of childerns
        Assert.assertTrue(n1.getRight().isRealNode());
        Assert.assertFalse(n1.getLeft().isRealNode());

        Assert.assertFalse(n2.getRight().isRealNode());
        Assert.assertFalse(n2.getLeft().isRealNode());


    }

    @Test
    public void testIsTreeEmpty() {
        AVLTree tree = new AVLTree();
        Assert.assertEquals(true, tree.empty());
        tree.insert(1, "*");
        Assert.assertEquals(false, tree.empty());
    }

    public void printNode(AVLTree.IAVLNode node) {
        String nodedata = "key: " + node.getKey() + ",val:  " + node.getValue() + ", height: " + node.getHeight();
        System.out.println(nodedata);
    }

    public AVLTree.IAVLNode[] createNodeList(int n) {
        AVLTree treeTMP = new AVLTree();
        AVLTree.IAVLNode[] nodeList = new AVLTree.IAVLNode[n];
        for (int i = 0; i < n; i++) {
            nodeList[i] = treeTMP.new AVLNode(i, "*" + i);

        }


        return nodeList;


    }

    @Test
    public void createTree() {
        printableTree tree = new printableTree();
        Assert.assertNull(tree.getRoot().getValue());
        Assert.assertEquals(tree.getTreeHeight(), -1);
        Assert.assertTrue(tree.empty());
        tree.insert(50, "50");
        Assert.assertEquals(tree.getRoot().getHeight(), 0);
        Assert.assertEquals(tree.getRoot().getKey(), 50);
        Assert.assertEquals(tree.getRoot().getValue(), "50");


        Assert.assertFalse(tree.empty());
        Assert.assertEquals("50", tree.max());
        Assert.assertEquals("50", tree.min());

        tree.insert(55, "55");
        Assert.assertEquals("55", tree.max());
        tree.insert(65, "65");
        tree.insert(75, "75");

        Assert.assertEquals("75", tree.max());
        Assert.assertEquals("50", tree.min());
        tree.insert(25, "25");
        tree.insert(12, "12");
        Assert.assertEquals("12", tree.min());
        Assert.assertEquals(6, tree.size());
//        System.out.println(Arrays.toString(tree.keysToArray()));
//        System.out.println(Arrays.toString(tree.infoToArray()));
        Assert.assertArrayEquals(new int[]{12, 25, 50, 55, 65, 75}, tree.keysToArray());
        Assert.assertArrayEquals(new String[]{"12", "25", "50", "55", "65", "75"}, tree.infoToArray());
        AVLTree.IAVLNode s1 = tree.findSuccessor(tree.getRoot());
        Assert.assertEquals(55, s1.getKey());
        s1 = tree.findSuccessor(s1);
        Assert.assertEquals(65, s1.getKey());


//        TreePrinter.printTree(tree.getRoot());


    }

    @Test
    public void createTree2() {
        printableTree tree = new printableTree();
        int [] values = {12, 75, 50,6, 65, 55, 25};
        for (int val:values){
            String info = Integer.toString(val);
            tree.insert(val, info);
        }
        tree.printTree();


        Assert.assertEquals("75", tree.max());
        Assert.assertEquals("6", tree.min());
        Assert.assertEquals(7, tree.size());
//        System.out.println(Arrays.toString(tree.keysToArray()));
//        System.out.println(Arrays.toString(tree.infoToArray()));
        Assert.assertArrayEquals(new int[]{6,12, 25, 50, 55, 65, 75}, tree.keysToArray());
        Assert.assertArrayEquals(new String[]{"6","12", "25", "50", "55", "65", "75"}, tree.infoToArray());

        Assert.assertEquals("12", tree.search(12));
        Assert.assertEquals(12, tree.getRoot().getKey());
        Assert.assertEquals(75, tree.getRoot().getRight().getKey());
        Assert.assertEquals(12, tree.getRoot().getRight().getParent().getKey());
        Assert.assertEquals(12, tree.getRoot().getLeft().getParent().getKey());
        Assert.assertEquals("25", tree.search(25));
        Assert.assertEquals("55", tree.search(55));
        Assert.assertEquals("6", tree.search(6));


        AVLTree.IAVLNode s1 = tree.findSuccessor(tree.getRoot());
        int[] pre= {25, 50, 55, 65, 75}; //no root
//        Arrays.sort(pre);
        for (int val:pre) {
            Assert.assertEquals(val, s1.getKey());
            s1 = tree.findSuccessor(s1);
        }
//
//




    }

    @Test
    public void printRandomTree(){
        int n =20;
        printableTree tree = new printableTree();
        Random rand = new Random();
        for (int i=0;i<n;i++){
            int val =  rand.nextInt(n);
            String info = Integer.toString(val);
                 tree.insert(val, info);
        }
        tree.printTree();


    }
    public void printBalancedTree() {
        printableTree tree = new printableTree();
        int[] values = {5, 8, 9, 2, 6, 7};
        for (int val : values) {
            String info = Integer.toString(val);
            tree.insert(val, info);
        }
    }
    }
