package view.structures.trees.commands;

import view.commons.commands.Command;
import view.structures.trees.BSTHeightBalancedView;
import view.structures.trees.BSTNodeView;

public class DobleRotationStartedCommand implements Command {
  private BSTHeightBalancedView bstView;
  private int side;
  private BSTNodeView node;

  public DobleRotationStartedCommand(BSTHeightBalancedView bstView, BSTNodeView node,
          int side) {
    super();
    this.bstView = bstView;
    this.side = side;
    this.node = node;
  }

  @Override
  public boolean canExecute() {
    return true;
  }

  @Override
  public void execute() {
    BSTNodeView temp;
    if (side == BSTNodeView.LEFT) {
      temp = node.left;
    } else if (side == BSTNodeView.RIGHT) {
      temp = node.right;
    } else {
      temp = bstView.getLastPolled();
    }

    bstView.getNodesVisited().addLast(temp);
    bstView.setDobleRotation(2);
  }
}
