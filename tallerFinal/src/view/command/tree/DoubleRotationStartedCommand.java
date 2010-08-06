package view.command.tree;

import view.collection.tree.BSTNodeView;
import view.collection.tree.BinarySearchTreeView;
import view.command.common.Command;

public class DoubleRotationStartedCommand implements Command {
	private BinarySearchTreeView bstView;
	private int side;
	private BSTNodeView node;

	public DoubleRotationStartedCommand(BinarySearchTreeView bstView,
			BSTNodeView node, int side) {
		super();
		this.bstView = bstView;
		this.side = side;
		this.node = node;
	}

	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	public void execute() {
		BSTNodeView temp;
		if (side == BSTNodeView.LEFT) {
			temp = node.left;
		} else if (side == BSTNodeView.RIGHT) {
			temp = node.right;
		} else {
			temp = bstView.getLastPolled();
		}

		bstView.getNodesVisited().addLast(temp);
		bstView.setDobleRotation(2);
	}
}
