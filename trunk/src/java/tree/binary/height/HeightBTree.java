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
	
	// if the node has two children
	// swap the nodes before delete()
	// does not apply to splay trees
	public BTNode swap(BTNode node, int minmax) {
		BTNode temp = node;
		BTNode swap = (minmax == FINDMAX) ? node.prevInO() : node.nextInO();
		swapData(node, swap); // swap data first
		node = swap; // now swap nodes
		swap = temp;
		return node;
	}

	// helper method for swap()
	public void swapData(BTNode node1, BTNode node2) {
		BTData data;
		data = node1.getData();
		node1.setData(node2.getData());
		node2.setData(data);
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
	public BTNode delete(BTData data, int minmax) {
		int side;
		BTNode node;

		node = locate(data);
		if (node == null)
			return null;

		if (node.hasTwoChildren())
			node = swap(node, minmax);

		side = node.getSide();
		node = remove(node);
		rebalance(node, side, DELETE);
		return node;
	}
	
	private void rebalance(BTNode node, int side, int in) {
		int newBalance;
		for (; node != null; node = node.getParent()) {
			newBalance = node.getBalance() + in * side;
			node.setBalance(newBalance);
			if (Math.abs(newBalance) > variacionMaxima) {
				if (newBalance < 0) {
					if (node.getChild(BTNode.LEFT).getBalance() > 0) {
						rotateLeftAndSetBalance(node.getChild(BTNode.LEFT));
					}
					node = rotateRightAndSetBalance(node);
				} else {
					if (node.getChild(BTNode.RIGHT).getBalance() < 0) {
						rotateRightAndSetBalance(node.getChild(BTNode.RIGHT));
					}
					node = rotateLeftAndSetBalance(node);
				}
			}

			if (in == INSERT && node.getBalance() == 0 || in == DELETE && node.getBalance() != 0) {
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
