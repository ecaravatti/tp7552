package view.command.tree;

import view.collection.tree.BSTHeightBalancedView;
import view.command.common.Command;

public class RotationFinishedCommand implements Command {
    private BSTHeightBalancedView bstView;

    public RotationFinishedCommand(BSTHeightBalancedView bstView) {
        this.bstView = bstView;
    }

    @Override
    public boolean canExecute() {
        return true;
    }

    @Override
    public void execute() {
        bstView.setInserting(false);
        
        if (bstView.isDobleRotation() == 1) {
            // Saco el ultimo nodo pues lo actualizo UpdateParentCommand
            //bstView.setLastPolled(bstView.getNodesVisited().pollLast());
        }
        bstView.setDobleRotation(bstView.isDobleRotation() - 1);
    }
}
