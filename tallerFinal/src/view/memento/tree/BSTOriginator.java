package view.memento.tree;

import view.collection.tree.BSTNodeView;

public interface BSTOriginator {

	public BSTMemento saveToMemento(BSTNodeView node);

	public void restoreFromMemento(BSTMemento memento);

}
