package view.structures.trees.commands;

import view.commons.commands.Command;
import view.structures.trees.BSTHeightBalancedView;

public class MoveNodesCommand implements Command{
  private BSTHeightBalancedView tree;
  
  public MoveNodesCommand(BSTHeightBalancedView tree) {
    super();
    this.tree = tree;
  }

  @Override
  public boolean canExecute() {
    return true;
  }

  @Override
  public void execute() {
   // tree.setChangeLocation(true);
    
    //Muevo los nodos y agrego comando para mover nuevamente si es 
    //necesario
    if ( tree.moveAllNodes() ) {
      tree.getCommandQueue().executeImmediate( new MoveNodesCommand(tree) );
    }
    
    tree.repaint();
  }
  
}
