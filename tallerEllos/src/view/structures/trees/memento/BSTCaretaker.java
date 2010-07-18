package view.structures.trees.memento;

import model.structures.stack.ArrayListStackImpl;

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
