package view.command.tree;

import view.collection.tree.BinarySearchTreeView;
import view.collection.tree.BSTNodeView;
import view.command.common.Command;

public class SaveStateBST implements Command {
  private BinarySearchTreeView bstView;
  private BSTNodeView node;
  
  public SaveStateBST(BinarySearchTreeView bstView, BSTNodeView node) {
    super();
    this.bstView = bstView;
    this.node = node;
  }

  @Override
  public boolean canExecute() {
    return true;
  }

  @Override
  public void execute() {
    bstView.saveState(node);
  }

}
