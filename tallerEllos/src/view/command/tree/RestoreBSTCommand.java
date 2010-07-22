package view.command.tree;

import view.collection.tree.BSTHeightBalancedView;
import view.command.common.Command;

public class RestoreBSTCommand implements Command {
  private BSTHeightBalancedView bstView;

  public RestoreBSTCommand(BSTHeightBalancedView bstView) {
    super();
    this.bstView = bstView;
  }

  @Override
  public boolean canExecute() {
    return true;
  }

  @Override
  public void execute() {
    bstView.restoreFromLastState();
  }

}
