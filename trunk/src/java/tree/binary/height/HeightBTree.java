package tree.binary.height;

import tree.binary.BTData;
import tree.binary.BTNode;
import tree.binary.BTree;


public class HeightBTree extends BTree{
	
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
		rebalanceAVL(lastNode, nextSide, INSERT);
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
		rebalanceAVL(node, side, DELETE);
		return node;
	}
	
	// rebalanceAVL() performs AVL rebalancing of the tree
	// after INSERT or DELETE using rotations when necessary
	// argument node is the parent of inserted or deleted node
	// argument side is the grown or shrunken side
	// argument in is -1 for DELETE and +1 for INSERT
	public void rebalanceAVL(BTNode node, int side, int in) {
		for (; node != null; node = node.getParent()) {
			if (node.getBalance() != in * side)
				node.setBalance(node.getBalance() + in * side);
			else
				node = rotateAVL(node);

			if (in == INSERT && node.getBalance() == 0 || in == DELETE
					&& node.getBalance() != 0)
				break;
			side = node.getSide();
		}
	}
	
	// rotateAVL() is called by rebalanceAVL() after INSERT or DELETE
	// It performs rotation(s) and updates balance factors.
	// Returns the top node of the rotated subtree.
	// If the balance factor of this node is 0:
	// for INSERT: the subtree has not grown,
	// for DELETE: the subtree has shrunken.
	// If the balance factor of this node is not 0 (left-high or right-high):
	// for INSERT: the subtree has grown,
	// for DELETE: the subtree has not shrunken.
	public BTNode rotateAVL(BTNode node) {
		int side = node.getBalance();
		BTNode child = node.getChild(side);

		if (child.getBalance() == -side) {
			BTNode grand = child.getChild(-side);
			if (grand.getBalance() == -side) {
				grand.setBalance(0);
				child.setBalance(side);
				node.setBalance(0);
			} else if (grand.getBalance() == side) {
				grand.setBalance(0);
				child.setBalance(0);
				node.setBalance(-side);
			} else {
				node.setBalance(0);
				child.setBalance(0);
			}
			rotate(child, side);
		} else if (child.getBalance() == side) {
			node.setBalance(0);
			child.setBalance(0);
		} else if (child.getBalance() == 0) // only after DELETE, never after INSERT
		{
			node.setBalance(side);
			child.setBalance(-side);
		}
		node = rotate(node, -side);
		return node;
	}
}
