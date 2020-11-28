package Tree;

public class FingerTree extends printableTree {
    private IAVLNode finger;
    public int searchCount;


    public IAVLNode findLastNodeEncountered_FINGER(IAVLNode node, int k) {
        IAVLNode lastNode = node;

        // FIND COMMAND FATHER
        while (node.isRealNode()) {
            this.searchCount++;
            if ((node.getKey() == this.root.getKey()) || (k == node.getKey()) || (k >= node.getParent().getKey())) {
                break;
            } else {
                node = node.getParent();
            }
        }
//            System.out.println("common fater:"+ node.getKey());

            // FIND LAST ENCOTHER
            while (node.isRealNode()) {
                this.searchCount++;
                lastNode = node;
                if (k == node.getKey()) {return node;}
                if (k < node.getKey()) {node = node.getLeft();}
                else {node = node.getRight();}
            }
            return lastNode;


    }



    public int insert_FINGER(int k, String i) {
        IAVLNode newNode = new AVLNode(k, i);



        if (this.empty()) {
            this.size++;
            this.finger = newNode;
            this.root = newNode;
//            System.out.println("new finger:"+ this.finger.getKey());
            return 0;
        } //Insert to root

        if (k>this.finger.getKey() ){
            IAVLNode curFinger = this.finger;
            curFinger.setRight(newNode);
            newNode.setParent(curFinger);
            curFinger.setHeight();
            this.finger = newNode;
//            System.out.println("new finger:"+ this.finger.getKey());
            return balanceTree(newNode, 0);


        } // new max



        IAVLNode lastNode = findLastNodeEncountered_FINGER(this.finger, k);


        if (lastNode.getKey() == k) {
            return -1;
        } //exists


        // got here- need to insert k
        this.size++;

        if (lastNode.getKey() < k) {
            lastNode.setRight(newNode);
        } //go right
        else {
            lastNode.setLeft(newNode);
        }


        balanceTree(lastNode, 0);
        this.finger = maxNode(root);
//        System.out.println("new finger:"+ this.finger.getKey());
        return searchCount;

    }


    public int getSearchCount() {
        return this.searchCount;
    }

    public int insertArray(int[] arr) {
        this.searchCount =0 ;
        for (int val : arr) {
            String info = Integer.toString(val);
            this.insert_FINGER(val, info);

        }
        return  this.searchCount;



    }

}


