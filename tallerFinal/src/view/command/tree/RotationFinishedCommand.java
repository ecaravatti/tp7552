package view.command.tree;

import view.collection.tree.BinarySearchTreeView;
import view.command.common.Command;

public class RotationFinishedCommand implements Command {
    private BinarySearchTreeView bstView;

    public RotationFinishedCommand(BinarySearchTreeView bstView) {
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
