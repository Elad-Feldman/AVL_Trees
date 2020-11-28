package Tree;


/**
 * Binary tree printer
 *
 * @author MightyPork
 */
public class TreePrinter {
    /**
     * Node that can be printed
     */
    public interface IAVLNode {
        int getKey(); //returns node's key (for virtuval node return -1)

        String getValue(); //returns node's value [info] (for virtuval node return null)

        void setLeft(IAVLNode node); //sets left child

        IAVLNode getLeft(); //returns left child (if there is no left child return null)

        void setRight(IAVLNode node); //sets right child

        IAVLNode getRight(); //returns right child (if there is no right child return null)

        void setParent(IAVLNode node); //sets parent

        IAVLNode getParent(); //returns the parent (if there is no parent return null)

        boolean isRealNode(); // Returns True if this is a non-virtual AVL node

        void setHeight(int height); // sets the height of the node

        int getHeight(); // Returns the height of the node (-1 for virtual nodes)


        String getText();
    }


    /**
     * Print a tree
     * <p>
     * //     * @param root
     * tree root node
     */
    public class AVLNode implements IAVLNode {
        private final int key;
        private int height;
        private final String val;
        private IAVLNode left;
        private IAVLNode right;
        private IAVLNode parent;
        private final boolean realNode;


        public AVLNode() {
            this.key = -1;
            this.val = null;
            this.height = -1;
            this.realNode = false;
//
        } //virtual node


        public AVLNode(int key, String val) {
            this.key = key;
            this.val = val;
            this.height = 0;
            this.realNode = true;
            this.left = new AVLNode(); //virtual node
            this.right = new AVLNode(); //virtual node
            this.parent = new AVLNode(); //virtual node
        }

        public int getKey() {
            return this.key;
        }

        public String getValue() {
            return this.val;
        }

        public void setLeft(IAVLNode node) {
            this.left = node;
            this.setHeight(1 + Math.max(this.left.getHeight(), this.right.getHeight()));
            this.left.setParent(this);


        }

        public IAVLNode getLeft() {
            return this.left;
        }

        public void setRight(IAVLNode node) {
            this.right = node;
            this.right.setParent(this);
            this.setHeight(1 + Math.max(this.left.getHeight(), this.right.getHeight()));
        }

        public IAVLNode getRight() {
            return this.right;

        }

        public void setParent(IAVLNode node) {
            this.parent = node;
//			this.parent.setHeight(1 + Math.max(node.getLeft().getHeight(), node.getRight().getHeight() ) );
        }

        public IAVLNode getParent() {
            return this.parent;
        }

        public boolean isRealNode() {
            // Returns True if this is a non-virtual AVL node

            return this.realNode;
        }

        public void setHeight(int height) {
            this.height = height;

        }


        public int getHeight() {
            return this.height;
        }

        public String getText() {
            String txt = "k:" + this.key + ",v:" + this.val;
            return txt;
        }
    }
//        public void printTree(IAVLNode root) {
//            List<List<String>> lines = new ArrayList<List<String>>();
//
//            List<IAVLNode> level = new ArrayList<IAVLNode>();
//            List<IAVLNode> next = new ArrayList<IAVLNode>();
//
//            level.add(root);
//            int nn = 1;
//
//            int widest = 0;
//
//            while (nn != 0) {
//                List<String> line = new ArrayList<String>();
//
//                nn = 0;
//
//                for (IAVLNode n : level) {
//                    if (n == null) {
//                        line.add(null);
//
//                        next.add(null);
//                        next.add(null);
//                    } else {
//                        String aa = n.getText();
//                        line.add(aa);
//                        if (aa.length() > widest) widest = aa.length();
//
//                        next.add(n.getLeft());
//                        next.add(n.getRight());
//
//                        if (n.getLeft() != null) nn++;
//                        if (n.getRight() != null) nn++;
//                    }
//                }
//
//                if (widest % 2 == 1) widest++;
//
//                lines.add(line);
//
//                List<IAVLNode> tmp = level;
//                level = next;
//                next = tmp;
//                next.clear();
//            }
//
//            int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);
//            for (int i = 0; i < lines.size(); i++) {
//                List<String> line = lines.get(i);
//                int hpw = (int) Math.floor(perpiece / 2f) - 1;
//
//                if (i > 0) {
//                    for (int j = 0; j < line.size(); j++) {
//
//                        // split node
//                        char c = ' ';
//                        if (j % 2 == 1) {
//                            if (line.get(j - 1) != null) {
//                                c = (line.get(j) != null) ? '┴' : '┘';
//                            } else {
//                                if (j < line.size() && line.get(j) != null) c = '└';
//                            }
//                        }
//                        System.out.print(c);
//
//                        // lines and spaces
//                        if (line.get(j) == null) {
//                            for (int k = 0; k < perpiece - 1; k++) {
//                                System.out.print(" ");
//                            }
//                        } else {
//
//                            for (int k = 0; k < hpw; k++) {
//                                System.out.print(j % 2 == 0 ? " " : "─");
//                            }
//                            System.out.print(j % 2 == 0 ? "┌" : "┐");
//                            for (int k = 0; k < hpw; k++) {
//                                System.out.print(j % 2 == 0 ? "─" : " ");
//                            }
//                        }
//                    }
//                    System.out.println();
//                }
//
//                // print line of numbers
//                for (int j = 0; j < line.size(); j++) {
//
//                    String f = line.get(j);
//                    if (f == null) f = "";
//                    int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
//                    int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);
//
//                    // a number
//                    for (int k = 0; k < gap1; k++) {
//                        System.out.print(" ");
//                    }
//                    System.out.print(f);
//                    for (int k = 0; k < gap2; k++) {
//                        System.out.print(" ");
//                    }
//                }
//                System.out.println();
//
//                perpiece /= 2;
//            }
//        }
    }
