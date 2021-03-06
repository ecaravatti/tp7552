package view.memento.tree;

import view.collection.tree.BSTNodeView;

public class BSTNodeMemento {
	private BSTNodeView node;
	private BSTNodeView copy;

	public BSTNodeMemento(BSTNodeView node) {
		this.node = node;

		if (node != null)
			this.copy = (BSTNodeView) node.clone();
		else
			this.copy = node;
	}

	public BSTNodeView getNode() {
		return node;
	}

	public BSTNodeView getCopy() {
		return copy;
	}

}
