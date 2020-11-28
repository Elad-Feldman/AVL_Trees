import Tree.AVLTree;

public class writeStuff {

      public void RotateRight( AVLTree.IAVLNode y){
        AVLTree.IAVLNode x = y.getLeft();
        AVLTree.IAVLNode b = x.getLeft();
        AVLTree.IAVLNode p = y.getParent();

        y.setRight(b);
        x.setRight(y);

        if ( p.getLeft().getKey()== y.getKey()){// include setParent
            p.setLeft(x);
        }

        else{
            p.setRight(x);
        }

    }
    public void RotateLeft( AVLTree.IAVLNode x){
        AVLTree.IAVLNode y = x.getRight();
        AVLTree.IAVLNode b = y.getLeft();
        AVLTree.IAVLNode p = x.getParent();

        x.setRight(b);
        y.setLeft(x);

        if ( p.getLeft().getKey()== x.getKey()){// include setParent
            p.setLeft(y);
        }

        else{
            p.setRight(y);
        }

    }
}
