package view.command.tree;

import view.collection.tree.BinarySearchTreeView;
import view.collection.tree.BSTNodeView;
import view.command.common.Command;

public class UpdateParentCommand implements Command {
    private BinarySearchTreeView bstView;

    public UpdateParentCommand(BinarySearchTreeView bstView) {
        this.bstView = bstView;
    }

    @Override
    public boolean canExecute() {
        return true;
    }

    @Override
    public void execute() {
        BSTNodeView temp = null;
        if (bstView.isModeDelete()) {
            //temp = bstView.getNodesVisited().peekLast();
            temp = bstView.getLastPolled();
        }

        bstView.setLastPolled(bstView.getNodesVisited().pollLast());
        BSTNodeView parent = bstView.getNodesVisited().peekLast();

        if (parent == null) {
            bstView.setRoot(bstView.getLastPolled());
            bstView.getNodesVisited().add(bstView.getLastPolled());
        } else {
            BSTNodeView last = bstView.getLastPolled();

            if (parent.getData().compareTo(last.getData()) == -1) {
                parent.right = last;
            } else {
                parent.left = last;
            }
        }

        if (bstView.isModeDelete()) {
            if (parent != null)
                bstView.getNodesVisited().add(bstView.getLastPolled());
            bstView.getNodesVisited().addLast(temp);
        }

        bstView.setFinalPositions();
    }
}
