package tree.binary.weight;

import tree.binary.BTData;
import tree.binary.BTNode;
import tree.binary.BTree;
import tree.binary.KeyAlreadyExistsException;
import tree.binary.KeyNotFoundException;


public class WeightBTree extends BTree {

	private static final double DEFAULT_ALPHA	= 1 - Math.sqrt(2)/2;
	private static final int 	DEFAULT_WEIGHT	= 2;
	
	private double alpha; // Should takes values between 0 and 0.5
	
	public WeightBTree(double alpha) {
		super();
		this.alpha = alpha;
	}
	
	public WeightBTree() {
		super();
		this.alpha = DEFAULT_ALPHA;
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
			
			for(BTNode temp = node; temp != null; temp = temp.getParent()) {
				temp.setWeight(	weight(temp.getChild(BTNode.LEFT)) + 
								weight(temp.getChild(BTNode.RIGHT)));
				temp = checkRotations(temp);
			}
		}
		return node;
	}
	
	@Override
	// Delete data from the tree
	// Returns the parent of the deleted node (null if the node was the root)
	// or KeyNotFoundException if the data has not been found
	// Uses helper method swap() if necessary
	public BTNode delete(BTData data) {
		BTNode node = locate(data);
		if (node == null) {
			throw new KeyNotFoundException();
		}

		if (node.hasTwoChildren()) {
//			node = swap(node, BTree.FINDMAX);
			// Rotate on heavier side
			if (weight(node.getChild(BTNode.LEFT)) > weight(node.getChild(BTNode.LEFT))) {
				node = rotateRightAndSetWeight(node);
				delete(data);
			} else {
				node = rotateLeftAndSetWeight(node);
				delete(data);
			}
		} else {
			node = remove(node);
		}
		
		for(BTNode temp = node; temp != null; temp = temp.getParent()) {
			temp.setWeight(	weight(temp.getChild(BTNode.LEFT)) + 
							weight(temp.getChild(BTNode.RIGHT)));
			temp = checkRotations(temp);
		}
		
		return node;
	}
	
	private BTNode checkRotations(BTNode node) {
		
		double wbal = 	(double)weight(node.getChild(BTNode.LEFT)) /
						(double)node.getWeight();
		
		if (wbal > (1-alpha)) {
			// left subtree too heavy: right rotation needed
			if (((double)weight(node.getChild(BTNode.LEFT).getChild(BTNode.LEFT)) /
				(double)weight(node.getChild(BTNode.LEFT))) > (1-2*alpha)){
				node = rotateRightAndSetWeight(node);
			} else {
				node.setChild(BTNode.LEFT,
							rotateLeftAndSetWeight(node.getChild(BTNode.LEFT)));
				node = rotateRightAndSetWeight(node);
			}
		} else if (wbal < alpha) {
			// right subtree too heavy: left rotation needed
			if (((double)weight(node.getChild(BTNode.RIGHT).getChild(BTNode.LEFT)) /
				(double)weight(node.getChild(BTNode.RIGHT))) < (2*alpha)){
				node = rotateLeftAndSetWeight(node);
			} else {
				node.setChild(BTNode.RIGHT,
						rotateRightAndSetWeight(node.getChild(BTNode.RIGHT)));
				node = rotateLeftAndSetWeight(node);
			}
		}
		return node;
	}
	
	private int weight(BTNode node) {
		if (node == null)
			return 1;
		else
			return nodesCount(node);
	}
	
	private int nodesCount(BTNode node) {
		if (node == null) {
			return 0;
		} else {
			return 	1 + nodesCount(node.getChild(BTNode.LEFT)) +
					nodesCount(node.getChild(BTNode.RIGHT));
		}
	}
	
//	private int leavesCount(BTNode node) {
//		if (node == null) {
//			return 0;
//		} else if (node.isLeaf()) {
//			return 1;
//		}
//		
//		return 	leavesCount(node.getChild(BTNode.LEFT)) +
//				leavesCount(node.getChild(BTNode.RIGHT));
//		
//	}
	
	private BTNode rotateRightAndSetWeight(BTNode node) {
		BTNode child = rotateRight(node);
		
		// Adjust weight
		child.setWeight(node.getWeight());
		node.setWeight(	weight(node.getChild(BTNode.LEFT)) +
						weight(node.getChild(BTNode.RIGHT)));
		
		return child;
	}
	
	private BTNode rotateLeftAndSetWeight(BTNode node) {
		BTNode child = rotateLeft(node);
		
		// Adjust weight
		child.setWeight(node.getWeight());
		node.setWeight(	weight(node.getChild(BTNode.LEFT)) +
						weight(node.getChild(BTNode.RIGHT)));
		
		return child;
	}
	
}
