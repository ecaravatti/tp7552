package view.structures.trees.commands;

import view.commons.commands.Command;
import view.structures.trees.BSTHeightBalancedView;

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
            // Saco el ultimo nodo pues lo actualizo ActualizeParentCommand
            //bstView.setLastPolled(bstView.getNodesVisited().pollLast());
        }
        bstView.setDobleRotation(bstView.isDobleRotation() - 1);
    }
}
