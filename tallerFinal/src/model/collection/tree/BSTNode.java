package model.collection.tree;

/**
 * Nodo de un <tt>Arbol Binario de Busqueda</tt>.
 *
 * 
 * @param <K> clase tipo almacenado en la estructura.
 */
public class BSTNode<K extends Comparable<K>> extends BSTNodeObservable<K> {
	
	public static final int LEFT = -1;
	public static final int RIGHT = +1;
	public static final int EQUAL = 0;
	/*public static final int NONE = EQUAL;
	public static final int ERROR = 0666;*/

	//protected BSTNode<K> parent;
	protected BSTNode<K> left;
	protected BSTNode<K> right;
	protected K data;
	protected int balance;      // usado como peso o altura (weight or height) 
	
	public BSTNode(K data, BSTNode<K> left, BSTNode<K> right) {
		this.data = data;
		this.left = left;
		this.right = right;
	}

	public K getData() {
		return data;
	}

	public void setData(K data) {
		this.data = data;
	}
	
	public int getBalance() {
		return balance;
	}
	
	public void setBalance(int balance) {
		this.balance = balance;
        fireBalanceUpdated(this);
	}

    public BSTNode<K> getLeft() {
        return left;
    }

    public BSTNode<K> getRight() {
        return right;
    }

	public BSTNode<K> getChild(int side) {
		if (side <= LEFT) {
            fireGetLeftChild(this);
			return left;
        }
		else if (side >= RIGHT) {
            fireGetRightChild(this);
			return right;
        }
//		else if (side == EQUAL)
			return this; // match
	}

    public BSTNode<K> getBrother(int side) {
		if (side <= LEFT)
			return right;
		else if (side >= RIGHT)
			return left;
//		else if (side == EQUAL)
			return null; // match
	}
	
	public void setChild(int side, BSTNode<K> child) {
		if (side <= LEFT) {
			left = child;
            fireLeftChildAdded(this);
        }
		else if (side >= RIGHT) {
			right = child;
            fireRightChildAdded(this);
        }
	}
	
/*	public BSTNode<K> getParent() {
		return parent;
	}
	
	public void setParent(BSTNode<K> parent) {
		this.parent = parent;
	}
	
	// on which side of the parent am I?
	public int getSide() {
		if (parent == null)
			return NONE; // this is root
		else if (this == parent.left)
			return LEFT;
		else if (this == parent.right)
			return RIGHT;
		return ERROR;
	}*/

    /**
     * Compara el nodo con un Dato de otro nodo
     * @param other dato de otro nodo
     * @return resultado de la comparacion
     */
	public int compareTo(K other) {
		return other.compareTo(this.data);
	}
}
