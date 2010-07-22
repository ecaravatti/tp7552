package view.command.tree;

import view.collection.tree.BSTHeightBalancedView;
import view.collection.tree.BSTNodeView;
import view.command.common.Command;

public class SaveStateBST implements Command {
  private BSTHeightBalancedView bstView;
  private BSTNodeView node;
  
  public SaveStateBST(BSTHeightBalancedView bstView, BSTNodeView node) {
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
