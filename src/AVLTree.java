import java.util.Arrays;

/**
 *
 * AVLTree
 *
 * An implementation of a AVL Tree with
 * distinct integer keys and info
 *
 */

public class AVLTree {
	private IAVLNode root;
	private int size;
	private int treeScanner;

	public AVLTree() {
		this.root = new AVLNode();
		this.size = 0;
	}


	/**
	 * public boolean empty()
	 * <p>
	 * returns true if and only if the tree is empty
	 */
	public boolean empty() {
		return (!this.root.isRealNode());
	}

	/**
	 * public String search(int k)
	 * <p>
	 * returns the info of an item with key k if it exists in the tree
	 * otherwise, returns null
	 */
	public String search(int k) {
		return findNodeByKey(this.root, k).getValue();
	}

	private IAVLNode findNodeByKey(IAVLNode node, int k) {
		System.out.println(node.getKey());
		if ((node.isRealNode() == false) ||  (node.getKey() == k)) {
			return node; //return virtual node

			}
		else {
			if (node.getKey() > k) {
				node = node.getLeft();
			} else {
				node = node.getRight();
			}
		}
		return findNodeByKey(node, k);
	}

	private IAVLNode findLastNodeEncountered(IAVLNode node, int k) {
		IAVLNode lastNode = node;
		while (node.isRealNode()) {
			lastNode = node;
			if (k == node.getKey()) {
				return node;
			} else {
				if (k < node.getKey()) {
					node = node.getLeft();
				} else {
					node = node.getRight();
				}
			}
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
//		System.out.println("newNode:" + newNode.getKey());

		if (this.empty()) {
			this.size++;
			this.root = newNode;
			return 0;
		} //Insert to root

		IAVLNode lastNode = findLastNodeEncountered(this.root, k);


		if (lastNode.getKey() == k) {
			return 0;
		}
		this.size++;

		if (lastNode.getKey() < k) {
			lastNode.setRight(newNode);
//		   System.out.println("father:"+lastNode.getKey()+" ,new right Son:"+ k );
		} else {
			lastNode.setLeft(newNode);
//		   System.out.println("father:"+lastNode.getKey()+" ,new left Son:"+ k );
		}

		//TODO: return number of balance actions

		return  balanceTree(lastNode,0);

	}
	public int balanceTree(IAVLNode node, int balance_actions){


		if (!node.isRealNode() || balance_actions>this.size()+10) { //got up to parent

			return balance_actions;
		}
		int Bcase = findBalanceCase(node);
		balance_actions++;
		updateHeight(node);

//
		if (Bcase == 2){
			balance_actions++;
			RotateRight(node);
		}
		if (Bcase == 3) {
			balance_actions=balance_actions+2;
			doubleRightRotation(node) ;
		}


		if (Bcase == -2){
			balance_actions++;
			RotateLeft(node);
		}
		if (Bcase == -3) {
			balance_actions=balance_actions+2;
			doubleLeftRotation(node) ;
		}
//		System.out.println(node.getKey()+"-->"+node.getParent().getKey());
		return balanceTree( node.getParent(),balance_actions );

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
//		IAVLNode node = findLastNodeEncountered(this.root, k);
//		if (node.getKey() !=k) // not in tree
//			return -1;
//
//		switch (isLeaf(node)) {
//			case "leaf":
//				node = new IAVLNode();
//				break;
//			case "rightChild":
//				return 5;
//
//		}



		return 42;    // to be replaced by student code
	}

	public IAVLNode findSuccessor(IAVLNode node){
		if (node.getRight().isRealNode()){
			return minNode(node.getRight());
		}
		IAVLNode parent = node.getParent();
		System.out.println(parent.getKey()+","+node.getKey() );
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
		return minNode(this.getRoot()).getValue();
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
		IAVLNode node = this.root;
		while (node.getRight().isRealNode()) {
			node = node.getRight();
		}
		return node.getValue();
	}

	/**
	 * public int[] keysToArray()
	 * <p>
	 * Returns a sorted array which contains all keys in the tree,
	 * or an empty array if the tree is empty.
	 */
	public int[] keysToArray() {
		int[] arr = new int[this.size()];
		this.treeScanner = 0;
		fillKeysArrayInorder(root, arr); //fill array in order
		return arr;              // to be replaced by student code
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
		this.treeScanner = 0;
		fillValuesArrayInorder(root, arr); //fill array in order
		return arr;                    // to be replaced by student code
	}

	private void fillKeysArrayInorder(IAVLNode root, int[] intArr) {
		if (root.isRealNode()) {
			fillKeysArrayInorder(root.getLeft(), intArr);

			intArr[this.treeScanner] = root.getKey();
			this.treeScanner++;

			fillKeysArrayInorder(root.getRight(), intArr);
		}

	}

	private void fillValuesArrayInorder(IAVLNode root, String[] intArr) {
		if (root.isRealNode()) {
			fillValuesArrayInorder(root.getLeft(), intArr);
			intArr[this.treeScanner] = root.getValue();
			this.treeScanner++;
			fillValuesArrayInorder(root.getRight(), intArr);
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
		return this.size; // to be replaced by student code
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

	/**
	 * public string split(int x)
	 * <p>
	 * splits the tree into 2 trees according to the key x.
	 * Returns an array [t1, t2] with two AVL trees. keys(t1) < x < keys(t2).
	 * precondition: search(x) != null (i.e. you can also assume that the tree is not empty)
	 * postcondition: none
	 */
	public AVLTree[] split(int x) {
		return null;
	}

	/**
	 * public join(IAVLNode x, AVLTree t)
	 * <p>
	 * joins t and x with the tree.
	 * Returns the complexity of the operation (|tree.rank - t.rank| + 1).
	 * precondition: keys(x,t) < keys() or keys(x,t) > keys(). t/tree might be empty (rank = -1).
	 * postcondition: none
	 */
	public int join(IAVLNode x, AVLTree t) {
		return 0;
	}

	private IAVLNode insertNode(IAVLNode root, IAVLNode newNode) {
		if (!(root.isRealNode())) {
			root = newNode;
//			System.out.println("inserted:"+ newNode.getKey());
			return root;
		}
		if (root.getKey() > newNode.getKey())
			root = root.getLeft();
		else {
			root = root.getRight();
		}
		return insertNode(root, newNode);
	}

	private String searchKey(IAVLNode root, int k) {
		if ((!root.isRealNode()))
			return null;

		if (root.getKey() == k) {
			System.out.println(root.getKey());
			return root.getValue();
		}

		if (root.getKey() > k)
			root = root.getLeft();
		else {
			root = root.getRight();
		}
		return searchKey(root, k);
	}

	public int getTreeHeight() {
		return this.root.getHeight();
	}

	private String isLeaf(IAVLNode node){
		boolean rightReal = node.getRight().isRealNode();
		boolean leftReal  = node.getLeft().isRealNode();
		if ((rightReal) && (leftReal )){
			return "twoChildren";
		}
		if (rightReal){
			return "rightChild";
		}
		if (leftReal){
			return "leftChild";
		}
		return "leaf";



	}

	public void RotateRight( AVLTree.IAVLNode x){
		AVLTree.IAVLNode y = x.getLeft();
		AVLTree.IAVLNode b = y.getRight();
		AVLTree.IAVLNode p = x.getParent();

		x.setLeft(b);

		y.setRight(x);
		x.setParent(y);


		swtichParents(p,x,y);
		updateHeight(y);
		updateHeight(x);


	}
	public void RotateLeft( AVLTree.IAVLNode y){
		AVLTree.IAVLNode x = y.getRight();
		AVLTree.IAVLNode b = x.getLeft();
		AVLTree.IAVLNode p = y.getParent();


		y.setRight(b);
		x.setLeft(y);
		y.setParent(x);
		swtichParents(p,y,x);
		updateHeight(x);
		updateHeight(y);







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
		updateHeight(z);
		updateHeight(y);
		updateHeight(x);


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
		updateHeight(z);
		updateHeight(y);
		updateHeight(x);


	}

	private void swtichParents(IAVLNode p, IAVLNode oldNode,IAVLNode newNode) {

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

	public int[] rankDiff(IAVLNode node ){
		int[] rankdiff  = new int[2];
		rankdiff[0] = node.getHeight() - node.getLeft().getHeight();
		rankdiff[1] = node.getHeight() - node.getRight().getHeight();
		return rankdiff;
	}

	public int findBalanceCase(IAVLNode node){
		int[] diff =rankDiff(node);

		//promote up
		if ((diff[0]==0) && (diff[1]==1)){return 1;}
		if ((diff[0]==1) && (diff[1]==0)){return 1;}


		if ((diff[0]==0) && (diff[1]==2)){
			int[] diffLeft = rankDiff(node.getLeft());
			if ((diffLeft[0]==1) && (diffLeft[1]==2)){return 2;} //rotate right
			if ((diffLeft[0]==2) && (diffLeft[1]==1)){return -3;} // Double rotate right
		}

		if ((diff[0]==2) && (diff[1]==0)){

			int[] diffRight = rankDiff(node.getRight());
			if ((diffRight[0]==2) && (diffRight[1]==1)){return -2;} //rotate left
			if ((diffRight[0]==1) && (diffRight[1]==2)){return 3;} // Double rotate left
		}

		return 1;



	}

	public void updateHeight(IAVLNode node){
		node.setHeight(1 + Math.max(node.getLeft().getHeight(), node.getRight().getHeight() ) );

	}

	public boolean isAVLTree(IAVLNode node){
		return true; //TODO write test, to check on every itration


	}

	public void printDiff(IAVLNode node){
		System.out.println("k:"+node.getKey()+", dif: "+ Arrays.toString(rankDiff(node))+" ,case:"+ findBalanceCase(node));
	}




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



//		String getText();
	}

	/**
	 * public class AVLNode
	 * <p>
	 * If you wish to implement classes other than AVLTree
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


		public AVLNode() {
			this.key = -1; // TODO change to -1
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
			this.parent  = new AVLNode(); //virtual node
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
//







		}

		public IAVLNode getLeft() {
			return this.left;
		}

		public void setRight(IAVLNode node) {
			this.right = node;
			this.right.setParent(this);
			this.setHeight(1 + Math.max(this.left.getHeight(), this.right.getHeight() ) );

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
				IAVLNode p = this.getParent();
//				if (p.isRealNode()) {
//					p.setHeight(1 + Math.max(p.getLeft().getHeight(), p.getRight().getHeight()));

//				}

		}



		public int getHeight() {
			return this.height;
		}




		}

}




  

