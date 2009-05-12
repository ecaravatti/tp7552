package collection.tree.binary;

import java.util.ArrayList;
import java.util.List;

import command.Command;
import command.tree.DeleteCommand;
import command.tree.InsertCommand;
import command.tree.LeftRotationCommand;
import command.tree.SwapCommand;


public abstract class BTree {

	public static final int INSERT = +1;
	public static final int DELETE = -1;

	public static final int FIND_MAX = +1;
	public static final int FIND_MIN = -1;

	protected BTNode root;
	
	protected List<Command> commands = new ArrayList<Command>(); //TODO [manugarciacab] ESTO NO VA ACA!
	
	//Resultados del último locate()
	protected BTNode lastNode; //Último nodo en la búsqueda
	protected int nextSide; //Lado del próximo hijo.

	public BTree() {
		root = null;
	}

	public abstract BTNode insert(BTData data) throws KeyAlreadyExistsException;
	
	public abstract BTNode delete(BTData data) throws KeyNotFoundException;

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		inOrderPrint(root, buffer);
		return buffer.toString();
	}
	
	/**
	 * Devuelve el nodo que contiene la data. Tira KeyNotFoundException si no lo encuentra.
	 * Guarda el último nodo en la búsqueda y el side del siguiente nodo para otros métodos.
	 */
	public BTNode locate(BTData data) throws KeyNotFoundException {
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
		
		if (next == null) {
			throw new KeyNotFoundException();
		}
		
		return next;
	}

	/**
	 * Crea un nodo hoja con la data recibida, y lo pone como hijo del nodo recibido.
	 * El nodo puede ser null, significando que el árbol está vacío.
	 * Devuelve el nodo creado.
	 */
	protected BTNode add(BTNode node, int side, BTData data) {
		BTNode newNode = new BTNode(data);
		link(node, side, newNode);
		
		commands.add(new InsertCommand(newNode.getData().getKey(), side == BTNode.LEFT, node != null ? node.getData().getKey() : null, newNode.getBalance()));
		return newNode;
	}

	/**
	 * Quita un nodo del árbol, y devuelve el padre del nodo removido.
	 */
	protected BTNode remove(BTNode node) {
		int side;
		BTNode child, parent;

		child = node.getChild(); //A lo sumo un hijo
		parent = node.getParent();
		side = node.getSide();
		link(parent, side, child);
		
		int deletedId = node.getData().getKey();
		Integer parentId = parent != null ? parent.getData().getKey() : null;
		Integer childId = child != null ? child.getData().getKey() : null;
		commands.add(new DeleteCommand(deletedId, parentId, childId, side == BTNode.LEFT));
		
		return parent;
	}

	/**
	 * Realiza la rotación a derecha del nodo recibido, y devuelve el nodo que queda arriba luego de la rotación
	 */
	protected BTNode rotateRight(BTNode node) {
		BTNode parent = node.getParent(); //Puede ser null
		BTNode child = node.getChild(BTNode.LEFT); //Nunca es null
		BTNode grand = child.getChild(BTNode.RIGHT); //Puede ser null

		link(node, BTNode.LEFT, grand);
		link(parent, node.getSide(), child);
		link(child, BTNode.RIGHT, node);
		if (node == root) {
			root = child;
		}
		
		Integer parentId = parent != null ? parent.getData().getKey() : null;
		int nodeId = node.getData().getKey();
		int childId = child.getData().getKey();
		Integer grandId = grand != null ? grand.getData().getKey() : null;
		
		commands.add(new LeftRotationCommand(parentId, nodeId, childId, grandId, node.getSide() == BTNode.LEFT, childId, grandId, nodeId));
		
		return child;
	}
	
	/**
	 * Realiza la rotación a izquierda del nodo recibido, y devuelve el nodo que queda arriba luego de la rotación
	 */
	protected BTNode rotateLeft(BTNode node) {
		BTNode parent = node.getParent(); //Puede ser null
		BTNode child = node.getChild(BTNode.RIGHT); //Nunca es null
		BTNode grand = child.getChild(BTNode.LEFT); //Puede ser null
		
		link(node, BTNode.RIGHT, grand);
		link(parent, node.getSide(), child);
		link(child, BTNode.LEFT, node);
		if (node == root) {
			root = child;
		}
		
		Integer parentId = parent != null ? parent.getData().getKey() : null;
		int nodeId = node.getData().getKey();
		int childId = child.getData().getKey();
		Integer grandId = grand != null ? grand.getData().getKey() : null;
		
		commands.add(new LeftRotationCommand(parentId, nodeId, childId, grandId, node.getSide() == BTNode.LEFT, childId, grandId, nodeId));
		
		return child;
	}

	/**
	 * Asocia un padre a un hijo y viceversa
	 */
	protected void link(BTNode parent, int side, BTNode child) {
		if (child != null) {
			child.setParent(parent);
		}
		if (parent != null) {
			parent.setChild(side, child);
		} else {
			root = child;
		}
	}
	
	/**
	 * Intercambia la BTData de node, con la del nodo menor de los mayores (minmax == FIND_MIN)
	 * o con la del mayor de los menores (minmax == FIND_MAX)
	 */
	protected BTNode swap(BTNode node, int minmax) {
		BTNode temp = node;
		BTNode swap = (minmax == FIND_MAX) ? node.prevInO() : node.nextInO();
		commands.add(new SwapCommand(node.getData().getKey(), swap.getData().getKey()));
		swapData(node, swap); // swap data first
		node = swap; // now swap nodes
		swap = temp;
		return node;
	}

	/**
	 * Intercambia la BTData de dos nodos.
	 */
	private void swapData(BTNode node1, BTNode node2) {
		BTData data = node1.getData();
		node1.setData(node2.getData());
		node2.setData(data);
	}
	
	/**
	 * Llena buffer con un recorrido in order del arbol.
	 */
	private void inOrderPrint(BTNode node, StringBuffer buffer) {
		if (node.getChild(BTNode.LEFT) != null) inOrderPrint(node.getChild(BTNode.LEFT), buffer);
		buffer.append("Yo: " + node);
		buffer.append(" Izq: " + node.getChild(BTNode.LEFT));
		buffer.append(" Der: " + node.getChild(BTNode.RIGHT) + "\n");
		if (node.getChild(BTNode.RIGHT) != null) inOrderPrint(node.getChild(BTNode.RIGHT), buffer);
	}

	public BTNode getRoot() {
		return root;
	}

}
