package tree.binary.weight;

import tree.binary.BTData;
import tree.binary.BTNode;
import tree.binary.BTree;
import tree.binary.KeyAlreadyExistsException;
import tree.binary.KeyNotFoundException;


public class WeightBTree extends BTree {

	private static final double UPPER_LIMIT 		= 0.707011;
	private static final double UPPER_LEFT_LIMIT 	= 0.414213;
	private static final double LOWER_LIMIT 		= 0.292893;
	private static final double LOWER_RIGHT_LIMIT 	= 0.585786;
	private static final int 	DEFAULT_WEIGHT 		= 2;
	
	private int maxWeightDiff;
	
	public WeightBTree(int maxWeightDiff) {
		super();
		this.maxWeightDiff = maxWeightDiff;
	}
	
	public WeightBTree() {
		super();
		this.maxWeightDiff = 1; // Default
	}
	
	@Override
	// Insert data into the tree
	// Throws KeyAlreadyExistsException if the data already exists
	public BTNode insert(BTData data) {
		BTNode node;
		if (root == null) {
			node = add(null, BTNode.NONE, data);
			node.setWeight(DEFAULT_WEIGHT);
		} else if (locate(data) != null) {
			throw new KeyAlreadyExistsException();
		} else {
			node = add(lastNode, nextSide, data);
			node.setWeight(weight(node.getChild(BTNode.LEFT)) + weight(node.getChild(BTNode.RIGHT)));
			node = checkRotations(node);
		}
		return node;
	}
	
	@Override
	// Delete data from the tree
	// Returns the parent of the deleted node (null if the node was the root)
	// or KeyNotFoundException if the data has not been found
	// Uses helper method swap() if necessary
	public BTNode delete(BTData data, int minmax) {
		BTNode node = locate(data);
		if (node == null) {
			throw new KeyNotFoundException();
		}

		if (node.hasTwoChildren()) {
			node = swap(node, minmax);
		}

		node = remove(node);
		
		if (node != null) {
			node.setWeight(weight(node.getChild(BTNode.LEFT)) + weight(node.getChild(BTNode.RIGHT)));
			node = checkRotations(node);
		}
		
		return node;
	}
	
	private BTNode checkRotations(BTNode node) {
		double wbal = weight(node.getChild(BTNode.LEFT)) / (double)node.getWeight();
		if (wbal > UPPER_LIMIT) { // left subtree too heavy: right rotation needed
			if ((weight(node.getChild(BTNode.LEFT).getChild(BTNode.LEFT)) / (double)weight(node.getChild(BTNode.LEFT))) > UPPER_LEFT_LIMIT) {
				node = rotateRightAndSetWeight(node);
			} else {
				node.setChild(BTNode.LEFT, rotateLeftAndSetWeight(node.getChild(BTNode.LEFT)));
				node = rotateRightAndSetWeight(node);
			}
		} else if (wbal < LOWER_LIMIT) {// right subtree too heavy: left rotation needed
			if ((weight(node.getChild(BTNode.RIGHT).getChild(BTNode.LEFT)) / (double)weight(node.getChild(BTNode.RIGHT))) < LOWER_RIGHT_LIMIT) {
				node = rotateLeftAndSetWeight(node);
			} else {
				node.setChild(BTNode.RIGHT, rotateRightAndSetWeight(node.getChild(BTNode.RIGHT)));
				node = rotateLeftAndSetWeight(node);
			}
		}
		return node;
	}
	
	private int weight(BTNode node) {
		if (node == null)
			return 1;
		else
			return leavesCount(node.getChild(BTNode.LEFT)) + leavesCount(node.getChild(BTNode.RIGHT));
	}
	
	private int leavesCount(BTNode node) {
		if (node == null) {
			return 0;
		} else if (node.isLeaf())
			return 1;
		
		return leavesCount(node.getChild(BTNode.LEFT)) + leavesCount(node.getChild(BTNode.RIGHT));
		
	}
	
	private BTNode rotateRightAndSetWeight(BTNode node) {
		BTNode child = rotateRight(node);
		
		// Adjust weight
		child.setWeight(node.getWeight());
		node.setWeight(weight(node.getChild(BTNode.LEFT)) + weight(node.getChild(BTNode.RIGHT)));
		
		return child;
	}
	
	private BTNode rotateLeftAndSetWeight(BTNode node) {
		BTNode child = rotateLeft(node);
		
		// Adjust weight
		child.setWeight(node.getWeight());
		node.setWeight(weight(node.getChild(BTNode.LEFT)) + weight(node.getChild(BTNode.RIGHT)));
		
		return child;
	}
	
}
