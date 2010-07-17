package view.structures.trees.memento;

import view.structures.trees.BSTNodeView;

public interface BSTOriginator {

  public BSTMemento saveToMemento(BSTNodeView node);

  public void restoreFromMemento(BSTMemento memento);

}
