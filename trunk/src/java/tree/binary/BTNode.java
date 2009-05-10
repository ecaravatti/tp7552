package tree.binary;


//class BTNode defines the tree node data structure
//and provides a collection of get/set methods protecting private data
//all balancing methods are defined in BSTree class
public class BTNode {

	public static final int LEFT = -1;
	public static final int RIGHT = +1;
	public static final int NONE = 0;
	public static final int EQUAL = 0;
	public static final int ERROR = 666;

	private BTNode parent;
	private BTNode leftChild;
	private BTNode rightChild;
	private int balance;
	private BTData data;

	public BTNode() {
		parent = null;
		leftChild = null;
		rightChild = null;
		balance = 0;
		data = null;
	}

	//TODO [manugarciacab] borrar este comment: este metodo lo agregue yo, manu
	public BTNode(BTData data) {
		parent = null;
		leftChild = null;
		rightChild = null;
		balance = 0;
		this.data = data;
	}

	public BTNode getChild(int side) {
		switch (side) {
		case LEFT:
			return leftChild;
		case RIGHT:
			return rightChild;
		default: //EQUAL
			return this;
		}
	}
	
	public int getNextSide(BTData data) {
		int cmp = this.data.compareTo(data);
		int side;
		if (cmp > 0) {
			side = LEFT;
		} else if (cmp < 0) {
			side = RIGHT;
		} else {
			side = EQUAL;
		}
		return side;
	}

	public BTNode getChild() {
		return (leftChild != null) ? leftChild : rightChild;
	}

	public void setChild(int side, BTNode node) {
		switch (side) {
		case LEFT:
			leftChild = node;
			break;
		case RIGHT:
			rightChild = node;
			break;
		default:
			//TODO [manugarciacab] Habría que hacer algo por default?
			//TODO [dpisaturo] Creo que no, a lo sumo lanzar una InvalidSideException :P
		}
	}

	/**
	 * Determina en que lado del padre estoy (NONE, LEFT, RIGHT)
	 */
	public int getSide() {
		if (parent == null) {
			return NONE; //Es la raiz
		} else if (this == parent.leftChild) {
			return LEFT;
		} else if (this == parent.rightChild) {
			return RIGHT;
		}
		return ERROR; //TODO [manugarciacab] No me gusta que devuelva esto, debería ser Exception en todo caso.
	}
	
	//TODO [manugarciacab] Ver que es esto de InO (aparentemente tiene algo que ver como logaritmo, no inOrden).
	public BTNode prevInO() {
		BTNode temp = this;
		BTNode node = null;
		if (temp.leftChild != null) {
			node = temp.leftChild.lastInO();
		} else {
			//TODO [manugarciacab] Puede que funcione, pero es inentendible
			for (; (node = temp.parent) != null && temp == node.leftChild; temp = node);
		}
		return node;
	}

	public BTNode nextInO() {
		BTNode temp = this;
		BTNode node = null;
		if (temp.rightChild != null) {
			node = temp.rightChild.firstInO();
		} else {
			//TODO [manugarciacab] Puede que funcione, pero es inentendible
			for (; (node = temp.parent) != null && temp == node.rightChild; temp = node);
		}
		return node;
	}

	public BTNode firstInO() {
		BTNode node;
		//TODO [manugarciacab] Puede que funcione, pero es inentendible
		for (node = this; node.leftChild != null; node = node.leftChild);
		return node;
	}

	public BTNode lastInO() {
		BTNode node;
		//TODO [manugarciacab] Puede que funcione, pero es inentendible
		for (node = this; node.rightChild != null; node = node.rightChild);
		return node;
	}

	public boolean hasTwoChildren() {
		return (leftChild != null && rightChild != null);
	}

	public boolean isLeaf() {
		return (leftChild == null && rightChild == null);
	}
	
	@Override
	public String toString() {
		return "[B: " + balance + " - K: " + data.toString() + "]";
	}

	public BTNode getParent() {
		return parent;
	}

	public void setParent(BTNode parent) {
		this.parent = parent;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	public int getWeight() {
		return balance;
	}
	
	public void setWeight(int weight) {
		balance = weight;
	}

	public BTData getData() {
		return data;
	}

	public void setData(BTData data) {
		this.data = data;
	}

}
