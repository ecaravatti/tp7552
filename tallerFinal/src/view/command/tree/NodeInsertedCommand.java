package view.command.tree;

import view.collection.tree.BSTNodeView;
import view.collection.tree.BinarySearchTreeView;
import view.command.common.Command;
import view.memento.tree.BSTMemento;

public class NodeInsertedCommand implements Command {
	private BSTNodeView node;
	private int side;
	private BinarySearchTreeView bstView;

	public NodeInsertedCommand(BSTNodeView node, int side,
			BinarySearchTreeView bstView) {
		super();
		this.node = node;
		this.side = side;
		this.bstView = bstView;
	}

	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	public void execute() {

		if (node == null) {

			if (side == 0) {
				bstView.saveState(bstView.getRoot());
				bstView.setRoot(bstView.getNodesVisited().getFirst());
			} else if (bstView.isInserting()) {
				int index = bstView.getNodesVisited().size() - 2;
				BSTMemento memento = bstView.saveState(bstView
						.getNodesVisited().get(index));
				bstView.setLastPolled(bstView.getNodesVisited().pollLast());
				BSTNodeView parent = bstView.getNodesVisited().peekLast();

				if (parent != null) {
					if (side == BSTNodeView.LEFT)
						parent.left = bstView.getLastPolled();
					else
						parent.right = bstView.getLastPolled();
				}
				memento.refreshState(parent);

			} else {
				bstView.saveState();
				bstView.setInserting(true);
			}
			if (bstView.getNodeRemoved() != null) {
				// bstView.saveState();
				bstView.setNodeRemoved(null);
				bstView.setChangeLocation(true);
			}
		} else {
			// Se agrego un nodo hijo nuevo
			// Establezco el padre
			BSTNodeView parent = bstView.getNodesVisited().peekLast();
			node.setParent(parent);
			bstView.saveState(node);
		}

	}
}
