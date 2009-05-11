package tree.binary.height;

import tree.binary.BTData;
import tree.binary.BTNode;
import tree.binary.BTree;


public class HeightBTree extends BTree {
	
	private int variacionMaxima;
	
	public HeightBTree(int variacionMaxima) {
		super();
		this.variacionMaxima = variacionMaxima;
	}
	
	public HeightBTree() {
		super();
		this.variacionMaxima = 1; //Valor por defecto
	}
	
	// insert data into an AVL tree
	// returns new node or null if the data already exists
	public BTNode insert(BTData data) {
		BTNode node;
		if (root == null)
			return add(null, 0, data);
		if (locate(data) != null)
			return null;
		node = add(lastNode, nextSide, data);
		rebalance(lastNode, nextSide, INSERT);
		return node;
	}
	
	// delete data from an AVL tree
	// returns the parent of the deleted node
	// or null if the data has not been found (ojo que el padre es null si era la raiz)
	// uses helper method swap() if necessary
	public BTNode delete(BTData data) {
		int side;
		BTNode node;

		node = locate(data);
		if (node == null)
			return null;

		if (node.hasTwoChildren()) {
			node = swap(node, BTree.FINDMAX);
		}

		side = node.getSide();
		node = remove(node);
		rebalance(node, side, DELETE);
		return node;
	}
	
	private void rebalance(BTNode node, int side, int in) {
		int newBalance;
		int balanceInicial;
		boolean noEmpeoraBalanceo;
		boolean huboRotacion = false;
		boolean cambioAltura = false;
		BTNode nodeAux;
		for (; node != null; node = node.getParent()) {
			huboRotacion = false;
			cambioAltura = false;
			balanceInicial = node.getBalance();
			newBalance = node.getBalance() + in * side;
			node.setBalance(newBalance);
			if (Math.abs(newBalance) > variacionMaxima) {
				huboRotacion = true;

				if (newBalance < 0) {
					BTNode leftChild = node.getChild(BTNode.LEFT);
					if (leftChild.getBalance() > 0) {
						cambioAltura = true;
						nodeAux = rotateLeftAndSetBalance(leftChild);
						if (nodeAux.getBalance() >= 0) {
							//El subarbol decreció en un nivel.
							node.setBalance(node.getBalance() - in * side);
						} 
					}
					node = rotateRightAndSetBalance(node);
					if (!cambioAltura) {
						cambioAltura = node.getBalance() <= 0;
					}
				} else {
					BTNode rightChild = node.getChild(BTNode.RIGHT);
					if (rightChild.getBalance() < 0) {
						cambioAltura = true;
						nodeAux = rotateRightAndSetBalance(rightChild);
						if (nodeAux.getBalance() <= 0) {
							//El subarbol decreció en un nivel.
							node.setBalance(node.getBalance() - in * side);
						}
					}
					node = rotateLeftAndSetBalance(node);
					if (!cambioAltura) {
						cambioAltura = node.getBalance() >= 0;
					}
				}
				
			}
			
			noEmpeoraBalanceo = Math.abs(node.getBalance()) - Math.abs(balanceInicial) <= 0;
			
			if ( (huboRotacion && !cambioAltura) || (in == INSERT && noEmpeoraBalanceo) || (in == DELETE && !noEmpeoraBalanceo) ) {
				break;
			}
			side = node.getSide();
		}
	}
	
	private BTNode rotateRightAndSetBalance(BTNode node) {
		BTNode child = rotateRight(node);
		
		//Balances
		int balanceAux = node.getBalance();
		node.setBalance(balanceAux + 1 + Math.max(-child.getBalance(), 0));
		child.setBalance(-Math.min(-balanceAux - 2, Math.min(-balanceAux - child.getBalance() - 2, -child.getBalance() - 1)));

		return child;
	}
	
	private BTNode rotateLeftAndSetBalance(BTNode node) {
		BTNode child = rotateLeft(node);
		
		//Balances
		int balanceAux = node.getBalance();
		node.setBalance(balanceAux - 1 - Math.max(child.getBalance(), 0));
		child.setBalance(Math.min(balanceAux - 2, Math.min(balanceAux + child.getBalance() - 2, child.getBalance() - 1)));
		
		return child;
	}

}
