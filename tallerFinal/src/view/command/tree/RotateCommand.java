package view.command.tree;

import view.collection.tree.BSTNodeView;
import view.collection.tree.BinarySearchTreeView;
import view.command.common.Command;

public class RotateCommand implements Command {
	private int side;
	private BSTNodeView node;
	private BinarySearchTreeView bstView;

	public RotateCommand(int side, BSTNodeView node,
			BinarySearchTreeView bstView) {
		this.side = side;
		this.node = node;
		this.bstView = bstView;
	}

	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	public void execute() {
		// Esta linea viene de BSTNodeRotatedAnimation
		node = bstView.getNodesVisited().pollLast();

		bstView.setChangeLocation(false);
		BSTNodeView temp;
		if (side == BSTNodeView.LEFT)
			temp = node.rotateLeft(node);
		else
			temp = node.rotateRight(node);

		bstView.getNodesVisited().addLast(temp);
		temp.changeSelectionColor(BSTNodeView.DEF_COLOR_VISITED);

		if (bstView.isModeDelete()) {
			bstView.setLastPolled(node);
		}
	}
}
