package view.structures.trees.commands;

import view.commons.commands.Command;
import view.structures.trees.BSTNodeView;

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
