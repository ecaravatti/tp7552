package view.command.tree;

import view.collection.tree.BSTNodeView;
import view.command.common.Command;

public class UpdateBalanceCommand implements Command {
  private BSTNodeView node;
  private Integer balance;
  
  
  public UpdateBalanceCommand(BSTNodeView node, Integer balance) {
    super();
    this.node = node;
    this.balance = balance;
  }

  @Override
  public boolean canExecute() {
    return true;
  }

  @Override
  public void execute() {
    node.updateBalance(balance);
  }

}
