package collection.tree.binary;

import java.util.ArrayList;
import java.util.List;

import collection.KeyAlreadyExistsException;
import collection.KeyNotFoundException;

import command.Command;
import command.tree.DeleteCommand;
import command.tree.InsertCommand;
import command.tree.LeftRotationCommand;
import command.tree.NodeHighlightCommand;
import command.tree.RightRotationCommand;
import command.tree.SwapCommand;
import common.Element;

public abstract class BTree {

	public static final int INSERT = +1;
	public static final int DELETE = -1;

	public static final int FIND_MAX = +1;
	public static final int FIND_MIN = -1;

	protected BTNode root;

	protected List<Command> commands;

	// Resultados del último locate()
	protected BTNode lastNode; // Último nodo en la búsqueda
	protected int nextSide; // Lado del próximo hijo.

	public BTree() {
		root = null;
	}

	/**
	 * Inserta el elemento en el arbol
	 * 
	 * @throws KeyAlreadyExistsException
	 *             si el elemento ya existe
	 */
	public abstract BTNode insert(int value) throws KeyAlreadyExistsException;

	/**
	 * Borra el elemento del arbol. Devuelve el padre del nodo borrado (null si
	 * el nodo era la raíz)
	 * 
	 * @throws KeyNotFoundException
	 *             si el elemento no se encuentra
	 */
	public abstract BTNode delete(int value) throws KeyNotFoundException;

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		if (root != null) {
			inOrderPrint(root, buffer);
		} else {
			buffer.append("Árbol vacío");
		}
		return buffer.toString();
	}

	/**
	 * Devuelve el nodo que contiene el elemento. Tira KeyNotFoundException si
	 * no lo encuentra. Guarda el último nodo en la búsqueda y el side del
	 * siguiente nodo para otros métodos.
	 */
	public BTNode locate(int value) throws KeyNotFoundException {
		initCommands();
		Element<Integer> element = new Element<Integer>(value, value);
		BTNode node = root;
		BTNode next = null;
		int side = 0;

		Integer lastNodeId = null;
		Integer currentNodeId = null;
		while (node != null) {
			currentNodeId = node.getElement().getId();
			commands.add(new NodeHighlightCommand(currentNodeId, lastNodeId));
			lastNodeId = currentNodeId;

			side = node.getNextSide(element);
			next = node.getChild(side);
			if (next == node || next == null) {
				break;
			}
			node = next;
		}

		lastNode = node;
		nextSide = side;

		commands.add(new NodeHighlightCommand(null, lastNodeId));

		if (next == null) {
			throw new KeyNotFoundException();
		}

		return next;
	}

	/**
	 * Crea un nodo hoja con el elemento recibido, y lo pone como hijo del nodo
	 * recibido. El nodo puede ser null, significando que el árbol está vacío.
	 * Devuelve el nodo creado.
	 */
	protected BTNode add(BTNode node, int side, Element<Integer> element) {
		BTNode newNode = new BTNode(element);
		link(node, side, newNode);

		commands.add(new InsertCommand(newNode.getElement().getValue(),
				side == BTNode.LEFT, node != null ? node.getElement().getId()
						: null, newNode.getBalance()));
		return newNode;
	}

	/**
	 * Quita un nodo del árbol, y devuelve el padre del nodo removido.
	 */
	protected BTNode remove(BTNode node) {
		int side;
		BTNode child, parent;

		child = node.getChild(); // A lo sumo un hijo
		parent = node.getParent();
		side = node.getSide();
		link(parent, side, child);

		int deletedId = node.getElement().getId();
		int deletedValue = node.getElement().getValue();
		Integer parentId = parent != null ? parent.getElement().getId() : null;
		Integer childId = child != null ? child.getElement().getId() : null;
		commands.add(new DeleteCommand(deletedId, deletedValue, parentId, childId,
				side == BTNode.LEFT));

		return parent;
	}

	/**
	 * Realiza la rotación a derecha del nodo recibido, y devuelve el nodo que
	 * queda arriba luego de la rotación
	 */
	protected BTNode rotateRight(BTNode node) {
		BTNode parent = node.getParent(); // Puede ser null
		BTNode child = node.getChild(BTNode.LEFT); // Nunca es null
		BTNode grand = child.getChild(BTNode.RIGHT); // Puede ser null

		link(node, BTNode.LEFT, grand);
		link(parent, node.getSide(), child);
		link(child, BTNode.RIGHT, node);
		if (node == root) {
			root = child;
		}

		Integer parentId = parent != null ? parent.getElement().getId() : null;
		int nodeId = node.getElement().getId();
		int childId = child.getElement().getId();
		Integer grandId = grand != null ? grand.getElement().getId() : null;

		commands.add(new RightRotationCommand(parentId, nodeId, childId,
				grandId, node.getSide() == BTNode.LEFT, childId, grandId,
				nodeId));

		return child;
	}

	/**
	 * Realiza la rotación a izquierda del nodo recibido, y devuelve el nodo que
	 * queda arriba luego de la rotación
	 */
	protected BTNode rotateLeft(BTNode node) {
		BTNode parent = node.getParent(); // Puede ser null
		BTNode child = node.getChild(BTNode.RIGHT); // Nunca es null
		BTNode grand = child.getChild(BTNode.LEFT); // Puede ser null

		link(node, BTNode.RIGHT, grand);
		link(parent, node.getSide(), child);
		link(child, BTNode.LEFT, node);
		if (node == root) {
			root = child;
		}

		Integer parentId = parent != null ? parent.getElement().getId() : null;
		int nodeId = node.getElement().getId();
		int childId = child.getElement().getId();
		Integer grandId = grand != null ? grand.getElement().getId() : null;

		commands.add(new LeftRotationCommand(parentId, nodeId, childId,
				grandId, node.getSide() == BTNode.LEFT, childId, grandId,
				nodeId));

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
	 * Intercambia el Element de node, con la del nodo menor de los mayores
	 * (minmax == FIND_MIN) o con la del mayor de los menores (minmax ==
	 * FIND_MAX)
	 */
	protected BTNode swap(BTNode node, int minmax) {
		BTNode temp = node;
		BTNode swap = (minmax == FIND_MAX) ? node.prevInO() : node.nextInO();
		commands.add(new SwapCommand(node.getElement().getId(), swap
				.getElement().getId()));

		// Intercambio data
		swapData(node, swap);

		// Intercambio nodos
		node = swap;
		swap = temp;

		return node;
	}

	/**
	 * Intercambia el Element de dos nodos.
	 */
	private void swapData(BTNode node1, BTNode node2) {
		Element<Integer> element = node1.getElement();
		node1.setElement(node2.getElement());
		node2.setElement(element);
	}

	/**
	 * Llena buffer con un recorrido in order del arbol.
	 */
	private void inOrderPrint(BTNode node, StringBuffer buffer) {
		if (node.getChild(BTNode.LEFT) != null)
			inOrderPrint(node.getChild(BTNode.LEFT), buffer);
		buffer.append("Yo: " + node);
		buffer.append(" Izq: " + node.getChild(BTNode.LEFT));
		buffer.append(" Der: " + node.getChild(BTNode.RIGHT) + "\n");
		if (node.getChild(BTNode.RIGHT) != null)
			inOrderPrint(node.getChild(BTNode.RIGHT), buffer);
	}

	protected void initCommands() {
		commands = new ArrayList<Command>();
	}

	public BTNode getRoot() {
		return root;
	}

	public List<Command> getCommands() {
		return commands;
	}

}
