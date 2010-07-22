package model.collection.tree;

import model.exception.tree.*;

/**
 * Un <tt>Árbol Binario de Búsqueda</tt> está <tt>Balanceado por Peso</tt>
 * cuando para cada uno de sus nodos ocurre que el balance <tt>p(t)</tt> está acotado
 * por: <tt>[a, 1-a]</tt>, con <tt>a</tt> tal que <tt>0 &le a &le 1/2</tt>.
 *
 * <p>El balance del nodo t, <tt>p(t)</tt>, se define como:
 * <p><code>p(t) = cant_nodos en t_izquierdo / cant_nodos en t</code>
 *
 * <p>Para esta implementacion, el parametro <tt>a</tt> está acotado entre:
 * <p><tt>2/11 &le a &le 1 - sqrt(2)/2</tt>.
 *
 * <p>Se cumple que la <u>altura total del arbol [h(n)]</u>
 * con n nodos está acotada por:
 * <p><code>log2(n+1) &le h(n) &le log2(n)/log2(1 - a)</code>
 *
 * <p>Fue introducido por Nievergelt y Reingold (1973).
 * 
 * @author Exe Curia
 * @param <K> clase tipo almacenado en la estructura.
 */
public class BSTWeightBalanced<K extends Comparable<K>> extends BinarySearchTree<K> {

	private int weight;

	public BSTWeightBalanced(int weight) {
		super();
		this.weight = weight;
	}

    /**
     * Calcula el peso del subarbol.
     * @param node subarbol
     * @return el peso del subarbol.
     */
    protected int getWeight(BSTNode<K> node) {
		if (node == null)
            return 1;
        else
            return node.getBalance();
	}

    @Override
	protected BSTNode<K> insertNode(K data, BSTNode<K> node) throws BSTKeyFoundException {
		if (node == null) {
			node = new BSTNode<K>(data, null, null);
			node.setBalance(2);
			return node;
		}
		
		int side = node.compareTo(data);
		if (side == BSTNode.EQUAL) {
            this.fireNodeFound(this, node);
			throw new BSTKeyFoundException(); // Ya exite la clave
        }
		
		BSTNode<K> next = node.getChild(side);
		BSTNode<K> brother = node.getBrother(side); //node.getChild(-side);
		next = insertNode(data, next);

		node.setChild(side, next);
		node.setBalance(getWeight(next) + getWeight(brother));
		
		return checkRotations(node);
	}

    /**
     * Realiza las rotaciones que sean necesarias en el nodo
     * para balancearlo
     * @param node nodo a balancear
     * @return nodo balanceado
     */
	protected BSTNode<K> checkRotations(BSTNode<K> node) {
        if (node == null)
            return null;

		int wl = getWeight(node.left);
		int wr = getWeight(node.right);

		if (wr > wl) {
			// Rotar a la Izquierda
			int wrr = getWeight(node.right.right);
			if ((wrr > wl) && (2*wrr >= wr)) {
				node = rotate(node, BSTNode.LEFT);
				node.left = checkRotations(node.left);
			}
			else if (wr-wrr > wl) {
				// Doble rotacion
                this.fireDobleRotationStarted(this, node, BSTNode.RIGHT);
				node.right = rotate(node.right, BSTNode.RIGHT);
				node = rotate(node, BSTNode.LEFT);
				node.left = checkRotations(node.left);
				node.right = checkRotations(node.right);
			}
		}
		else if (wl > wr) {
			// Rotar a la Derecha
			int wll = getWeight(node.left.left);
			if ((wll > wr) && (2*wll >= wl)) {
				node = rotate(node, BSTNode.RIGHT);
				node.right = checkRotations(node.right);
			}
			else if (wl-wll > wr) {
				// Doble rotacion
                this.fireDobleRotationStarted(this, node, BSTNode.LEFT);
				node.left = rotate(node.left, BSTNode.LEFT);
				node = rotate(node, BSTNode.RIGHT);
                node.left = checkRotations(node.left);
                node.right = checkRotations(node.right);
			}
		}
		return node;
	}
    
    @Override
    protected BSTNode<K> balanceAfterDelete(BSTNode<K> node) {
        if (node == null)
            return null;

        node.setBalance(getWeight(node.left) + getWeight(node.right));
        return checkRotations(node);
    }

    @Override
    protected void adjustBalance(BSTNode<K> node, BSTNode<K> temp, int side) {
		node.setBalance(temp.getBalance());
		temp.setBalance(getWeight(temp.left) + getWeight(temp.right));
	}

    @Override
    protected int getNodeBalance(BSTNode<K> node) {
        return getWeight(node);
    }
}
