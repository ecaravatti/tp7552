package model.collection.tree;

import java.util.Iterator;
import java.util.Vector;

/**
 * Iterador para recorrer la estructura de un <tt>Arbol Binario de Busqueda</tt>
 * .
 * 
 * 
 * @param <K>
 *            clase tipo almacenado en la estructura.
 */
public class BSTIterator<K extends Comparable<K>> implements
		Iterator<BSTNode<K>> {

	public enum Order {
		PRE_ORDER, IN_ORDER, POST_ORDER,
	}

	private Vector<BSTNode<K>> storage;
	private Iterator<BSTNode<K>> storageIterator;

	/**
	 * Construye un iterador para el Arbol Binario de Busqueda.
	 * 
	 * @param tree
	 *            estructura a ser recorrida
	 * @param order
	 *            tipo de orden en el que se recorrerá
	 */
	public BSTIterator(BinarySearchTree<K> tree, Order order) {
		storage = new Vector<BSTNode<K>>(tree.getSize());
		poblate(tree.root, order);
		storageIterator = storage.iterator();
		tree.fireTraverseFinished(tree);
	}

	private void poblate(BSTNode<K> node, Order order) {
		if (node == null)
			return;

		switch (order) {
		case PRE_ORDER:
			node.fireNodeTraversed(node);
			storage.add(node);

			node.fireGetLeftChild(node);
			poblate(node.left, order);

			node.fireGetRightChild(node);
			poblate(node.right, order);
			break;

		case IN_ORDER:
			node.fireGetLeftChild(node);
			poblate(node.left, order);

			node.fireNodeTraversed(node);
			storage.add(node);

			node.fireGetRightChild(node);
			poblate(node.right, order);
			break;

		case POST_ORDER:
			node.fireGetLeftChild(node);
			poblate(node.left, order);

			node.fireGetRightChild(node);
			poblate(node.right, order);

			node.fireNodeTraversed(node);
			storage.add(node);
			break;
		}
	}

	@Override
	public boolean hasNext() {
		return storageIterator.hasNext();
	}

	@Override
	public BSTNode<K> next() {
		return storageIterator.next();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
