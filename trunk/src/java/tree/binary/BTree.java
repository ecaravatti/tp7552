package tree.binary;


public abstract class BTree {

	public static final int INSERT = +1;
	public static final int DELETE = -1;

	public static final int FINDMAX = +1;
	public static final int FINDMIN = -1;

	protected BTNode root;
	
	//Resultados del último locate()
	protected BTNode lastNode; //Último nodo en la búsqueda
	protected int nextSide; //Lado del próximo hijo.

	public BTree() {
		root = null;
	}

	// BST methods

	/**
	 * Devuelve el nodo que contiene la data, o null si no lo encuentra.
	 * Guarda el último nodo en la búsqueda y el side del siguiente nodo para otros métodos.
	 */
	public BTNode locate(BTData data) {
		BTNode node = root;
		BTNode next = null;
		int side = 0;

		while (node != null) {
			side = node.getNextSide(data);
			next = node.getChild(side);
			if (next == node || next == null) {
				break;
			}
			node = next;
		}
		
		lastNode = node;
		nextSide = side;
		
		return next;
	}

	/**
	 * Crea un nodo hoja con la data recibida, y lo pone como hijo del nodo recibido.
	 * El nodo puede ser null, significando que el árbol está vacío.
	 * Devuelve el nodo creado.
	 */
	public BTNode add(BTNode node, int side, BTData data) {
		BTNode newnode = new BTNode(data);
		link(node, side, newnode);
		return newnode;
	}

	/**
	 * Quita un nodo del árbol, y devuelve el padre del nodo removido.
	 */
	public BTNode remove(BTNode node) {
		int side;
		BTNode child, parent;

		child = node.getChild(); // single child
		parent = node.getParent();
		side = node.getSide();
		link(parent, side, child);
		return parent;
	}

	// rotate() links the participating nodes and
	// returns the node which is on top after the rotation.
	// the 'node' is rotated down and to the specified 'side'
	public BTNode rotate(BTNode node, int side) {
		BTNode parent = node.getParent(); // may be null
		BTNode child = node.getChild(-side); // never null
		BTNode grand = child.getChild(side); // may be null

		link(node, -side, grand);
		link(parent, node.getSide(), child);
		link(child, side, node);
		if (node == root) {
			root = child;
		}
		return child;
	}

	// link() method is used by add(), remove() and rotate()
	// handles null pointers and updates root pointer when needed
	public void link(BTNode parent, int side, BTNode child) {
		if (child != null) {
			child.setParent(parent);
		}
		if (parent != null) {
			parent.setChild(side, child);
		} else {
			root = child;
		}
	}

	public abstract BTNode insert(BTData data);
	
	//TODO [manugarciacab] el minmax creo que es solo para AVL, pero como aun no lo vi lo dejo aca.
	public abstract BTNode delete(BTData data, int minmax);

}
