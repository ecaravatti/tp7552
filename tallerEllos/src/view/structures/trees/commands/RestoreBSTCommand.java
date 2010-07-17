package view.structures.trees.commands;

import view.commons.commands.Command;
import view.structures.trees.BSTHeightBalancedView;

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
