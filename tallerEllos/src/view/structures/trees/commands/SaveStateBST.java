package view.structures.trees.commands;

import view.commons.commands.Command;
import view.structures.trees.BSTHeightBalancedView;
import view.structures.trees.BSTNodeView;

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
