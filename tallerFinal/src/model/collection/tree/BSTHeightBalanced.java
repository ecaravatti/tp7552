package model.collection.tree;

import model.exception.tree.BSTKeyFoundException;

/**
 * Un <tt>Árbol Binario de Búsqueda</tt> está <tt>Balanceado por Altura</tt>
 * cuando para cada uno de sus nodos ocurre que las <u>alturas</u> de sus dos
 * subarboles difieren a lo sumo en <tt>K</tt> (parámetro entero no nulo).
 * 
 * <p>
 * Para el caso particular en que <tt>K</tt> vale 1, a este árbol se lo llama
 * Árbol AVL (por sus autores Adel'son-Vels'ki y Landis).
 * 
 * <p>
 * Gracias a este balanceo, se cumple que la <u>altura total del arbol
 * [h(n)]</u> con n nodos está acotada por:
 * <p>
 * <code>log2(n+1) &le h(n) &le 1.44042 * log2(n+2) - 0.33772</code>
 * 
 * 
 * @param <K>
 *            clase tipo almacenado en la estructura.
 */
public class BSTHeightBalanced<K extends Comparable<K>> extends
		BinarySearchTree<K> {

	private int maxHeight;
	private int increment;

	public BSTHeightBalanced(int maxHeight) {
		super();
		this.maxHeight = maxHeight;
	}

	@Override
	protected BSTNode<K> insertNode(K data, BSTNode<K> node)
			throws BSTKeyFoundException {
		increment = 0;
		if (node == null) {
			node = new BSTNode<K>(data, null, null);
			node.setBalance(0);
			increment = 1;
			return node;
		}

		int side = node.compareTo(data);
		if (side == BSTNode.EQUAL) {
			this.fireNodeFound(this, node);
			throw new BSTKeyFoundException(); // Ya exite la clave
		}

		BSTNode<K> next = node.getChild(side);
		next = insertNode(data, next);

		node.setChild(side, next);
		node.balance += side * increment; // (increment ? 0 : 1);
		node.fireBalanceUpdated(node);

		if ((increment != 0) && (node.balance != 0))
			node = reBalance(node);
		else
			increment = 0;

		return node;
	}

	@Override
	protected BSTNode<K> balanceAfterDelete(BSTNode<K> node) {
		if (node == null)
			return null;

		node.balance = getHeightNode(node.right) - getHeightNode(node.left);
		node.fireBalanceUpdated(node);
		return reBalance(node);
	}

	/**
	 * Rebalancea un subarbol (que no está balanceado).
	 * 
	 * @param node
	 *            subarbol NO nulo
	 * @return subarbol balanceado
	 */
	protected BSTNode<K> reBalance(BSTNode<K> node) {
		increment = 0;
		if (node.balance < -this.maxHeight) {
			if (node.left.balance <= 0)
				node = rotate(node, BSTNode.RIGHT);
			else {
				this.fireDobleRotationStarted(this, node, BSTNode.LEFT);
				node.left = rotate(node.left, BSTNode.LEFT);
				node = rotate(node, BSTNode.RIGHT);
			}
		} else if (node.balance > this.maxHeight) {
			if (node.right.balance >= 0)
				node = rotate(node, BSTNode.LEFT);
			else {
				this.fireDobleRotationStarted(this, node, BSTNode.RIGHT);
				node.right = rotate(node.right, BSTNode.RIGHT);
				node = rotate(node, BSTNode.LEFT);
			}
		} else
			increment = 1;
		return node;
	}

	/**
	 * <a href=
	 * "http://www.cmcrossroads.com/bradapp/ftp/src/libs/C++/AvlTrees.html">
	 * Fuente para el ajuste de balance luego de una rotacion </a>
	 */
	@Override
	protected void adjustBalance(BSTNode<K> node, BSTNode<K> temp, int side) {
		if (side == BSTNode.LEFT) {
			temp.balance -= 1 + Math.max(node.balance, 0);
			node.balance -= 1 - Math.min(temp.balance, 0);
		} else {
			temp.balance += 1 - Math.min(node.balance, 0);
			node.balance += 1 + Math.max(temp.balance, 0);
		}
		temp.fireBalanceUpdated(temp);
		node.fireBalanceUpdated(node);
	}

	@Override
	protected int getNodeBalance(BSTNode<K> node) {
		return getHeightNode(node);
	}

	public int getMaxHeight() {
		return maxHeight;
	}
}
