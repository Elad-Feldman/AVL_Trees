package Tree;

import java.util.Arrays;

/**
 *
 * Tree.AVLTree
 *
 * An implementation of a AVL Tree with
 * distinct integer keys and info
 *
 */

public class AVLTree {
	public IAVLNode root;
	public int size;
	public int nodeIndex; //for a linear tree scan
	private long splitComplexity;
	private long maxSplitComplexity;
	private long numJoins;

	public AVLTree() {
		this.root = new AVLNode();
		this.size = 0;
	}


	/**
	 * public boolean empty() O(1)
	 * <p>
	 * returns true if and only if the tree is empty
	 */
	public boolean empty() {
		return (!this.root.isRealNode());
	}

	/**
	 * public String search(int k)  O(logn)
	 * <p>
	 * returns the info of an item with key k if it exists in the tree
	 * otherwise, returns null
	 */
	public String search(int k) {
		return recSearch(this.root, k).getValue();
	}

	/**
	 * private IAVLNode findNodeByKey(IAVLNode node, int k)  O(logn)
	 *  returns the nodem with key k if it exists in the tree
	 * otherwise, returns null
	 */
	private IAVLNode recSearch(IAVLNode node, int k) {

		if ((!node.isRealNode()) ||  (node.getKey() == k)) {

			return node; //return virtual node or the right node

			}
		else {
			if (node.getKey() > k) {
				node = node.getLeft();
			}  //go left
			else {
				node = node.getRight(); //go right
			}
		}
		return recSearch(node, k);
	}

	public IAVLNode findLastNodeEncountered(IAVLNode node, int k) {
		IAVLNode lastNode = node;

		while (node.isRealNode()) {
			lastNode = node;
			if (k == node.getKey()) {return node;}
			if (k < node.getKey()) {node = node.getLeft();}
			else {node = node.getRight();}
		}
		return lastNode;
	}

	/**
	 * public int insert(int k, String i)
	 * <p>
	 * inserts an item with key k and info i to the AVL tree.
	 * the tree must remain valid (keep its invariants).
	 * returns the number of rebalancing operations, or 0 if no rebalancing operations were necessary.
	 * promotion/rotation - counted as one rebalnce operation, double-rotation is counted as 2.
	 * returns -1 if an item with key k already exists in the tree.
	 */
	public int insert(int k, String i) {
		IAVLNode newNode = new AVLNode(k, i);

		if (this.empty()) {
			this.size++;
			this.root = newNode;
			return 0;
		} //Insert to root

		IAVLNode lastNode = findLastNodeEncountered(this.root, k);


		if (lastNode.getKey() == k) {return -1;} //exists


		// got here- need to insert k
		this.size++;

		if (lastNode.getKey() < k) {lastNode.setRight(newNode);} //go right
		else {lastNode.setLeft(newNode);}

		return  balanceTree(lastNode,0);

	}
	public int balanceTree(IAVLNode node, int balance_actions){


		if (!node.isRealNode()) { return balance_actions; //got to root, stop
		}
		int caseNum = node.balancedCase(); //read balancedCase to understand what each number means
		balance_actions ++; //setting Height
		node.setHeight(); //this is promote/demote for node


		if (caseNum == 2){
			balance_actions ++;
			RotateRight(node);
		}
		if (caseNum == 3) {
			balance_actions=balance_actions+2;
			doubleRightRotation(node) ;
		}


		if (caseNum == -2){
			balance_actions ++;
			RotateLeft(node);
		}
		if (caseNum == -3) {
			balance_actions += 2;
			doubleLeftRotation(node) ;
		}
		return balanceTree( node.getParent(),balance_actions ); //TODO is a single rotate sohuld be count also?

	}


	/**
	 * public int delete(int k)
	 * <p>
	 * deletes an item with key k from the binary tree, if it is there;
	 * the tree must remain valid (keep its invariants).
	 * returns the number of rebalancing operations, or 0 if no rebalancing operations were needed.
	 * demotion/rotation - counted as one rebalnce operation, double-rotation is counted as 2.
	 * returns -1 if an item with key k was not found in the tree.
	 */
	public int delete(int k) {


		IAVLNode node = findLastNodeEncountered(this.root, k);
		if (node.getKey() !=k){ return -1;} //not in tree


		this.size--;
		IAVLNode p = node.getParent();
		IAVLNode vir = new AVLNode();

		int numChild = node.numberOfChildren();

		if (numChild == 0) {
			swtichParents(p, node, vir);
			return  balanceTree(p,0);
		}

		if (numChild == 1) {
			swtichParents(p, node, node.getRight());
			return  balanceTree(p,0);
		}
		if (numChild == -1) {
			swtichParents(p, node, node.getLeft());
			return  balanceTree(p,0);
		}
		if (numChild == 2) {
			IAVLNode successor = findSuccessor(node);
			IAVLNode successorSon = successor.getRight(); // in this case, node have a right child !
			IAVLNode successorParent = successor.getParent();
			swtichParents(successorParent, successor, successorSon); //remove successor from tree
			successor.setRight(node.getRight());
			successor.setLeft(node.getLeft());
			swtichParents(p, node, successor); //remove node
			return  balanceTree(successorParent,0);
		}

		return -1;    // to be replaced by student code
	}

	public IAVLNode findSuccessor(IAVLNode node){
		if (node.getRight().isRealNode()){
			return minNode(node.getRight());
		}
		IAVLNode parent = node.getParent();
		while ((parent.isRealNode()) && (node.equals(parent.getRight()) ) ) {

			node = parent;
			parent = node.getParent();
		}
		return parent;
	}

	/**
	 * public String min()
	 * <p>
	 * Returns the info of the item with the smallest key in the tree,
	 * or null if the tree is empty
	 */
	public String min() {

		if (!root.isRealNode())
			return null;
		return minNode(root).getValue();
	}
	private IAVLNode minNode(IAVLNode node){
		while (node.getLeft().isRealNode()) {
			node = node.getLeft();
		}
		return node;
	}

	/**
	 * public String max()
	 * <p>
	 * Returns the info of the item with the largest key in the tree,
	 * or null if the tree is empty
	 */
	public String max() {
		if (!root.isRealNode())
			return null;
		return maxNode(root).getValue();
	}

	public IAVLNode maxNode(IAVLNode node) {
		while (node.getRight().isRealNode()) {
			node = node.getRight();
		}
		return node;

	}

	/**
	 * public int[] keysToArray()
	 * <p>
	 * Returns a sorted array which contains all keys in the tree,
	 * or an empty array if the tree is empty.
	 */
	public int[] keysToArray() {
		int[] arr = new int[this.size()];
		this.nodeIndex = 0;
		fillKeysArrayInorder(root, arr); //fill array in order
		return arr;
	}

	/**
	 * public String[] infoToArray()
	 * <p>
	 * Returns an array which contains all info in the tree,
	 * sorted by their respective keys,
	 * or an empty array if the tree is empty.
	 */
	public String[] infoToArray() {
		String[] arr = new String[this.size()];
		this.nodeIndex = 0;
		fillValuesArrayInorder(root, arr); //fill array in order
		return arr;                    // to be replaced by student code
	}

	public void fillKeysArrayInorder(IAVLNode node, int[] intArr) {
		if (node.isRealNode()) {
			fillKeysArrayInorder(node.getLeft(), intArr);

			intArr[this.nodeIndex] = node.getKey();
			this.nodeIndex++;

			fillKeysArrayInorder(node.getRight(), intArr);
		}

	}

	public void fillValuesArrayInorder(IAVLNode node, String[] intArr) {
		if (node.isRealNode()) {
			fillValuesArrayInorder(node.getLeft(), intArr);
			intArr[this.nodeIndex] = node.getValue();
			this.nodeIndex++;
			fillValuesArrayInorder(node.getRight(), intArr);
		}
	}

	/**
	 * public int size()
	 * <p>
	 * Returns the number of nodes in the tree.
	 * <p>
	 * precondition: none
	 * postcondition: none
	 */
	public int size() {
		return this.root.getSize(); // to be replaced by student code
	}

	/**
	 * public int getRoot()
	 * <p>
	 * Returns the root AVL node, or null if the tree is empty
	 * <p>
	 * precondition: none
	 * postcondition: none
	 */
	public IAVLNode getRoot() {
		return this.root;
	}



	private AVLTree createTreeFromNode(IAVLNode node) {
		AVLTree tree = new AVLTree();
		tree.root = node;
		tree.root.setParent(new AVLNode());
		return tree;
	}

	/**
	 * public string split(int x)
	 * <p>
	 * splits the tree into 2 trees according to the key x.
	 * Returns an array [t1, t2] with two AVL trees. keys(t1) < x < keys(t2).
	 * precondition: search(x) != null (i.e. you can also assume that the tree is not empty)
	 * postcondition: none
	 */
	public AVLTree[] split(int x) {
		this.splitComplexity = 0;
		this.maxSplitComplexity = 0;
		this.numJoins = 0;

		long z = 0;

		IAVLNode xNode = recSearch(this.getRoot(), x);

		AVLTree t1 = createTreeFromNode(xNode.getLeft());
		AVLTree t2 = createTreeFromNode(xNode.getRight());

		IAVLNode current = xNode.getParent();
		if (xNode.equals(this.getRoot())) {
			return new AVLTree[]{t1, t2};
		}

		System.out.println(xNode.getKey());

		boolean isRightChild = xNode.getParent().getRight().getKey() == xNode.getKey();

		while (current.isRealNode()) {
			if (isRightChild) {
				AVLTree leftTree = createTreeFromNode(current.getLeft());
				IAVLNode nodeBetweenTrees = leftTree.new AVLNode(current.getKey(), current.getValue());

				z = t1.join(nodeBetweenTrees, leftTree);
			}

			else {
				AVLTree rightTree = createTreeFromNode(current.getRight());
				IAVLNode nodeBetweenTrees = t2.new AVLNode(current.getKey(), current.getValue());

				z = t2.join(nodeBetweenTrees, rightTree);
			}

			// update complexity
			this.splitComplexity += z;
			this.numJoins++;
			if (z > this.maxSplitComplexity) {
				this.maxSplitComplexity = z;
			}

			if (current.equals(this.getRoot())){
				break;
			}

			isRightChild = current.getParent().getRight().equals(current);
			current = current.getParent();
		}

		this.splitComplexity = z;
		return new AVLTree[]{t1, t2};
	}




	private AVLTree taller(AVLTree t1, AVLTree t2) {
		if (t1.getTreeHeight() >= t2.getTreeHeight()) {
			return t1;
		}
		return t2;
	}

	private AVLTree shorter(AVLTree t1, AVLTree t2) {
		if (t2.getTreeHeight() <= t1.getTreeHeight()) {
			return t2;
		}
		return t1;
	}

	private IAVLNode getNodeOfRankKLeft(AVLTree tree, int k) {
		// find most left node in higher tree with rank <= k
		IAVLNode current = tree.getRoot();
		while (current.getHeight() > k) {
			current = current.getLeft();
		}
		return current;
	}

	private IAVLNode getNodeOfRankKRight(AVLTree tree, int k) {
		// find most left node in higher tree with rank <= k
		IAVLNode current = tree.getRoot();
		while (current.getHeight() > k) {
			current = current.getRight();
		}
		return current;
	}

	/**
	 * public join(IAVLNode x, Tree.AVLTree t)
	 * <p>
	 * joins t and x with the tree.
	 * Returns the complexity of the operation (|tree.rank - t.rank| + 1).
	 * precondition: keys(x,t) < keys() or keys(x,t) > keys(). t/tree might be empty (rank = -1).
	 * postcondition: none
	 */
	public int join(IAVLNode x, AVLTree t) {
		int complexity;

		// either trees is empty
		if (t.empty()) {
			this.insert(x.getKey(), x.getValue());
			complexity = Math.abs(this.getTreeHeight() + 1) + 1;
			return complexity;
		}
		else if (this.empty()) {
			this.size = t.root.getSize();
			this.root = t.getRoot();
			this.insert(x.getKey(), x.getValue());
			complexity = Math.abs(-1 - this.getTreeHeight()) + 1;
			return complexity;
		}

		complexity = Math.abs(this.getTreeHeight() - t.getTreeHeight()) + 1;

		AVLTree tallTree = taller(this, t);
		AVLTree shortTree = shorter(this, t);

		int k = shortTree.getTreeHeight();
		x.setHeight(k+1);

		IAVLNode b;

		// if taller is bigger
		if (maxNode(tallTree.getRoot()).getKey() > maxNode(shortTree.getRoot()).getKey()) {
			b = getNodeOfRankKLeft(tallTree, k);
		}
		// if taller is smaller
		else {
			b = getNodeOfRankKRight(tallTree, k);
		}

		IAVLNode c = b.getParent();
		swtichParents(c, b, x);

		if (x.getKey() < b.getKey()) {
			x.setRight(b);
			x.setLeft(shortTree.getRoot());
		}
		else {
			x.setRight(shortTree.getRoot());
			x.setLeft(b);
		}


		this.root = tallTree.getRoot();
		balanceTree(x, 0);
		this.size = tallTree.root.getSize();

		return complexity;
	}

	public int getTreeHeight() {
		return this.root.getHeight();
	}

	public void RotateRight( AVLTree.IAVLNode y){
		AVLTree.IAVLNode x = y.getLeft();
		AVLTree.IAVLNode b = x.getRight();
		AVLTree.IAVLNode p = y.getParent();

		y.setLeft(b);

		x.setRight(y);
		y.setParent(x);


		swtichParents(p,y,x);

		y.setHeight();
		x.setHeight();


	}

	public void RotateLeft( AVLTree.IAVLNode x){
		AVLTree.IAVLNode y = x.getRight();
		AVLTree.IAVLNode b = y.getLeft();
		AVLTree.IAVLNode p = x.getParent();


		x.setRight(b);
		y.setLeft(x);
		x.setParent(y);
		swtichParents(p,x,y);
		x.setHeight();
		y.setHeight();
		
	}

	public void doubleRightRotation ( AVLTree.IAVLNode z){
		AVLTree.IAVLNode p = z.getParent();

		AVLTree.IAVLNode a = z.getLeft();
		AVLTree.IAVLNode y = z.getRight();

		AVLTree.IAVLNode b =y.getLeft();
		AVLTree.IAVLNode x = y.getLeft();

		AVLTree.IAVLNode c = x.getLeft();
		AVLTree.IAVLNode d = x.getRight();

		z.setRight(c);
		y.setLeft(d);
		x.setLeft(z);
		x.setRight(y);
		swtichParents(p,z,x);
		z.setHeight();
		y.setHeight();
		x.setHeight();


	}

	public void doubleLeftRotation ( AVLTree.IAVLNode z){
		AVLTree.IAVLNode p = z.getParent();

		AVLTree.IAVLNode a = z.getRight();
		AVLTree.IAVLNode y = z.getLeft();



		AVLTree.IAVLNode x = y.getRight();
		AVLTree.IAVLNode b =y.getLeft();


		AVLTree.IAVLNode d = x.getRight();
		AVLTree.IAVLNode c = x.getLeft();

		y.setRight(c);
		z.setLeft(d);

		x.setRight(z);
		x.setLeft(y);

		swtichParents(p,z,x);
		z.setHeight();
		y.setHeight();
		x.setHeight();


	}

	public void swtichParents(IAVLNode p, IAVLNode oldNode,IAVLNode newNode) {

		if (p.isRealNode()){
			if (p.getLeft().getKey() == oldNode.getKey()){// include setParent
				p.setLeft(newNode);
			}
			else{
				p.setRight(newNode);
			}
		}
		else{
			newNode.setParent(p);
			this.root =newNode;
		}
	}

	public long getSplitComplexity() {
		return this.splitComplexity;
	}
	public long getMaxSplitComplexity() {
		return this.maxSplitComplexity;
	}
	public long getNumJoins() {
		return this.numJoins;
	}



	public boolean isAVLTree(IAVLNode node){
		return true; //TODO write test, to check on every itration


	} //TODO



	/**
	 * public interface IAVLNode
	 * ! Do not delete or modify this - otherwise all tests will fail !
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

		//WE ADDED:

		int[] rankDiff(); //rank diff from two children

		void setHeight(); //get height from children

		int balancedCase(); //2 rotate right, -2 rotate left, 3 rotate twice right, -3 rotate twice left

		void printDiff(); //print key and rankdiff

		int numberOfChildren(); // 0:leaf, 1:Right, -1:Left, 2:Left and Right
		int getSize();
		void setSize();



//		String getText();
	}

	/**
	 * public class AVLNode
	 * <p>
	 * If you wish to implement classes other than Tree.AVLTree
	 * (for example AVLNode), do it in this file, not in
	 * another file.
	 * This class can and must be modified.
	 * (It must implement IAVLNode)
	 */
	public class AVLNode implements IAVLNode {
		private final int key;
		private int height;
		private final String val;
		private IAVLNode left;
		private IAVLNode right;
		private IAVLNode parent;
		private final boolean realNode;
		private int size;



		public AVLNode() {
			this.key = -1; // TODO change to -1
			this.val = null;
			this.height = -1;
			this.realNode = false;
			this.size=0;
//
		} // create virtual node


		public AVLNode(int key, String val) {
			this.key = key;
			this.val = val;
			this.height = 0;
			this.realNode = true;
			this.left = new AVLNode(); //virtual node
			this.right = new AVLNode(); //virtual node
			this.parent  = new AVLNode(); //virtual node
			this.size=1;
		}

		public int getKey() {
			return this.key;
		}

		public String getValue() {
			return this.val;
		}

		public void setLeft(IAVLNode node) {
			this.left = node;
			this.left.setParent(this);
		}

		public IAVLNode getLeft() {
			return this.left;
		}

		public void setRight(IAVLNode node) {
			this.right = node;
			this.right.setParent(this);
		}

		public IAVLNode getRight() {
			return this.right;
		}

		public void setParent(IAVLNode node) {
			this.parent = node;
		}

		public IAVLNode getParent() {
			return this.parent;
		}

		public boolean isRealNode() {
			return this.realNode;
		}

		public void setHeight(int height) {
				this.height = height;
			this.setSize();


		}

		public void setHeight(){
			this.setHeight(1 + Math.max(this.getLeft().getHeight(), this.getRight().getHeight() ) );

		}

		public int getHeight() {
			return this.height;
		}

		public int[] rankDiff( ){
			int[] rankdiff  = new int[2];
			rankdiff[0] = this.getHeight() - this.getLeft().getHeight();
			rankdiff[1] = this.getHeight() - this.getRight().getHeight();
			return rankdiff;
		}

		public int balancedCase(){
			int[] diff =this.rankDiff();

			//Rotate Right
			if (((diff[0]==0) && (diff[1]==2)) || ((diff[0]==1) && (diff[1]==3))){

				int[] diffLeft = this.getLeft().rankDiff();

				if ((diffLeft[0]==1) && (diffLeft[1]==1)){return 2;} //rotate right(delete case)
				if ((diffLeft[0]==1) && (diffLeft[1]==2)){return 2;} //rotate right
				if ((diffLeft[0]==2) && (diffLeft[1]==1)){return -3;} // Double rotate right
			}


			// Rotate Left
			if (((diff[0]==2) && (diff[1]==0)) || ((diff[0]==3) && (diff[1]==1))){

				int[] diffRight = this.getRight().rankDiff();

				if ((diffRight[0]==1) && (diffRight[1]==1)){return -2;} //rotate left, (delete case)
				if ((diffRight[0]==2) && (diffRight[1]==1)){return -2;} //rotate left
				if ((diffRight[0]==1) && (diffRight[1]==2)){return 3;} // Double rotate left
			}

			return 1; //should not get here
		}

		public void printDiff(){
			System.out.println("k:"+this.getKey()+", dif: "+ Arrays.toString(this.rankDiff())+" ,case:"+ this.balancedCase());
		}

		public int numberOfChildren(){
			if((this.getLeft().isRealNode()) && (this.getRight().isRealNode())){return 2;}
			if(this.getLeft().isRealNode() ){return -1;}
			if(this.getRight().isRealNode()){return 1;}
			return 0;

		}

		public int getSize() {
			return this.size;
		}
		 public void setSize(){
			this.size = (1+this.getLeft().getSize()+this.getRight().getSize());
		 }

	}

}




  

