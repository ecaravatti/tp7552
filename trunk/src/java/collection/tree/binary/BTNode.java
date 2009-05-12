package collection.tree.binary;

public class BTNode {

	public static final int LEFT = -1;
	public static final int RIGHT = +1;
	public static final int NONE = 0;
	public static final int EQUAL = 0;

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
		default: // EQUAL
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
			throw new InconsistentStateException();
		}
	}

	/**
	 * Determina en que lado del padre estoy (NONE, LEFT, RIGHT)
	 */
	public int getSide() {
		if (parent == null) {
			return NONE; // Es la raiz
		} else if (this == parent.leftChild) {
			return LEFT;
		} else if (this == parent.rightChild) {
			return RIGHT;
		}
		throw new InconsistentStateException();
	}

	public BTNode prevInO() {
		BTNode temp = this;
		BTNode node = null;
		if (temp.leftChild != null) {
			node = temp.leftChild.lastInO();
		} else {
			for (; (node = temp.parent) != null && temp == node.leftChild; temp = node)
				;
		}
		return node;
	}

	public BTNode nextInO() {
		BTNode temp = this;
		BTNode node = null;
		if (temp.rightChild != null) {
			node = temp.rightChild.firstInO();
		} else {
			for (; (node = temp.parent) != null && temp == node.rightChild; temp = node)
				;
		}
		return node;
	}

	public BTNode firstInO() {
		BTNode node;
		for (node = this; node.leftChild != null; node = node.leftChild)
			;
		return node;
	}

	public BTNode lastInO() {
		BTNode node;
		for (node = this; node.rightChild != null; node = node.rightChild)
			;
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

	public int depth() {
		int ld = 0, rd = 0;
		if (leftChild != null)
			ld = leftChild.depth();
		if (rightChild != null)
			rd = rightChild.depth();
		return Math.max(ld, rd) + 1;
	}

	/**
	 * Se utiliza en pruebas
	 */
	public int getBalanceTeorico() {
		int heightLeft = 0, heightRight = 0;
		if (rightChild != null)
			heightRight = rightChild.depth();
		if (leftChild != null)
			heightLeft = leftChild.depth();
		return heightRight - heightLeft;
	}
}
