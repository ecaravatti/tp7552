package model.collection.tree;

import model.exception.tree.BSTEmptyTreeException;
import model.exception.tree.BSTKeyFoundException;
import model.exception.tree.BSTKeyNotFoundException;

/**
 * Un <tt>Arbol Binario de Busqueda</tt> solo puede tener hasta 2 nodos hijos.
 * 
 * <p>
 * Los nodos hijos derechos tienen claves menores y los nodos hijos izquierdos
 * tienen claves mayores respecto del nodo padre.
 * 
 * 
 * @param <K>
 *            clase tipo almacenado en la estructura.
 */
public abstract class BinarySearchTree<K extends Comparable<K>> extends
		BinarySearchTreeObservable<K> {

	protected BSTNode<K> root;

	/**
	 * Construye un <code>Arbol Binario de Busqueda</code> vacio
	 */
	public BinarySearchTree() {
		root = null;
	}

	/**
	 * Limpia el arbol
	 */
	public void clear() {
		root = null;
	}

	protected abstract BSTNode<K> insertNode(K data, BSTNode<K> node)
			throws BSTKeyFoundException;

	/**
	 * Inserta un nodo con la data indicada
	 * 
	 * @param data
	 *            la informacion que identifica al nodo
	 * @throws model.exception.tree.BSTKeyFoundException
	 *             si ya existe la clave del nodo
	 */
	public void insert(K data) throws BSTKeyFoundException {
		root = insertNode(data, root);
		this.fireRootAdded(this, root);
	}

	/**
	 * Borra un nodo recursivamente
	 * 
	 * @param data
	 *            la informacion que identifica al nodo
	 * @param node
	 *            subarbol donde buscar al nodo
	 * @return el subarbol actualizado
	 * @throws model.exception.tree.BSTKeyNotFoundException
	 */
	protected BSTNode<K> deleteNode(K data, BSTNode<K> node)
			throws BSTKeyNotFoundException {
		if (node == null) {
			this.fireNodeNotFound(this);
			throw new BSTKeyNotFoundException();
		}

		// Busco nodo a borrar
		int side = node.compareTo(data);
		BSTNode<K> next = null;

		if (side != BSTNode.EQUAL) {
			next = node.getChild(side);
			next = deleteNode(data, next);
			node.setChild(side, next);
		}

		// Nodo encontrado, borrar si no tiene algun hijo
		else if (node.left == null) {
			node.fireRemoved(node);
			node = node.right;
		} else if (node.right == null) {
			node.fireRemoved(node);
			node = node.left;
		}

		// Tiene todos sus Hijos, rotar del lado mas pesado
		else if (getNodeBalance(node.left) > getNodeBalance(node.right)) {
			node = rotate(node, BSTNode.RIGHT);
			node.right = deleteNode(data, node.right);
			this.fireRotateFinishedInDelete(this, node, BSTNode.RIGHT);
		} else {
			node = rotate(node, BSTNode.LEFT);
			node.left = deleteNode(data, node.left);
			this.fireRotateFinishedInDelete(this, node, BSTNode.LEFT);
		}

		// Balanceo y actualizo pesos
		return balanceAfterDelete(node);
	}

	protected abstract int getNodeBalance(BSTNode<K> node);

	/**
	 * Borra un nodo con la data indicada
	 * 
	 * @param data
	 *            la informacion que identifica al nodo
	 * @throws model.exception.tree.BSTKeyNotFoundException
	 *             si no existe la clave del nodo
	 */
	public void delete(K data) throws BSTKeyNotFoundException,
			BSTEmptyTreeException {
		if (root == null) {
			this.fireEmptyTree(this);
			throw new BSTEmptyTreeException();
		}
		root = deleteNode(data, root);
		this.fireRootRemoved(this, root);
	}

	/**
	 * Rota un <code>nodo</code> hacia el lado indicado por <code>side</code>
	 * 
	 * @param node
	 *            Subarbol a rotar
	 * @param side
	 *            Lado hacia donde rotar
	 * @return El nodo raiz del subarbol rotado
	 */
	protected BSTNode<K> rotate(BSTNode<K> node, int side) {
		BSTNode<K> temp = node;
		if (side == BSTNode.LEFT) {
			this.fireNodeRotatedToLeft(this, node);
			node = node.right;
			temp.right = node.left;
			node.left = temp;
		} else if (side == BSTNode.RIGHT) {
			this.fireNodeRotatedToRight(this, node);
			node = node.left;
			temp.left = node.right;
			node.right = temp;
		}
		if (node != null)
			adjustBalance(node, temp, side);

		return node;
	}

	/**
	 * Ajusta el balance en una rotacion.
	 */
	protected abstract void adjustBalance(BSTNode<K> node, BSTNode<K> temp,
			int side);

	/**
	 * Balancea el subarbol luego de una eliminacion.
	 * 
	 * @param node
	 *            subarbol
	 * @return subarbol balanceado
	 */
	protected abstract BSTNode<K> balanceAfterDelete(BSTNode<K> node);

	/**
	 * Calcula la cantidad de nodos en el arbol.
	 * 
	 * @return cantidad de nodos
	 */
	public int getSize() {
		return getSizeNode(root);
	}

	protected int getSizeNode(BSTNode<K> node) {
		if (node == null)
			return 0;
		else
			return getSizeNode(node.left) + getSizeNode(node.right) + 1;
	}

	/**
	 * Calcula la altura del arbol, es decir el camino más largo de la raíz a
	 * cualquier hoja.
	 * <p>
	 * Con dicha definición, un arbol nulo tiene altura 0 y un arbol con un
	 * único nodo tiene altura 1.
	 * 
	 * @return altura del arbol
	 */
	public int getHeight() {
		return getHeightNode(root);
	}

	protected int getHeightNode(BSTNode<K> node) {
		if (node == null)
			return 0;

		int sizeNodeLeft = getHeightNode(node.left);
		int sizeNodeRight = getHeightNode(node.right);

		return 1 + Math.max(sizeNodeLeft, sizeNodeRight);
	}

	public BSTNode<K> getRoot() {
		return root;
	}

	public K getRootData() {
		if (root == null)
			return null;
		else
			return root.getData();
	}

	/**
	 * Recorre el arbol en pre-order (visita la raiz, luego el subarbol
	 * izquierdo y finalmente el subarbol derecho).
	 * 
	 * @return iterador para recorrer el arbol
	 */
	public BSTIterator<K> traversePreOrder() {
		return new BSTIterator<K>(this, BSTIterator.Order.PRE_ORDER);
	}

	/**
	 * Recorre el arbol en in-order (visita el subarbol izquierdo, luego la raiz
	 * y finalmente el subarbol derecho).
	 * 
	 * @return iterador para recorrer el arbol
	 */
	public BSTIterator<K> traverseInOrder() {
		return new BSTIterator<K>(this, BSTIterator.Order.IN_ORDER);
	}

	/**
	 * Recorre el arbol en post-order (visita el subarbol izquierdo, luego el
	 * subarbol derecho y finalmente la raiz).
	 * 
	 * @return iterador para recorrer el arbol
	 */
	public BSTIterator<K> traversePostOrder() {
		return new BSTIterator<K>(this, BSTIterator.Order.POST_ORDER);
	}
}
