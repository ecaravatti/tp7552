package view.structures.trees.memento;

import model.structures.stacks.LinkedListStackImpl;

public class BSTCaretaker {
  private LinkedListStackImpl<BSTMemento> savedStates;
  
  
  public BSTCaretaker() {
    super();
    this.savedStates = new LinkedListStackImpl<BSTMemento>();
  }

  public void addMemento(BSTMemento m) {
      savedStates.push(m);
  }

  public BSTMemento getMemento() {
      return savedStates.pop();
  }
}
