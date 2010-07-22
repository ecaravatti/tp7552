package view.command.tree;

import view.collection.tree.BSTHeightBalancedView;
import view.command.common.Command;

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
