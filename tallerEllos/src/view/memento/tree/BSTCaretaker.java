package view.memento.tree;

import model.collection.stack.ArrayListStackImpl;

public class BSTCaretaker {
  private ArrayListStackImpl<BSTMemento> savedStates;
  
  
  public BSTCaretaker() {
    super();
    this.savedStates = new ArrayListStackImpl<BSTMemento>();
  }

  public void addMemento(BSTMemento m) {
      savedStates.push(m);
  }

  public BSTMemento getMemento() {
      return savedStates.pop();
  }
}
