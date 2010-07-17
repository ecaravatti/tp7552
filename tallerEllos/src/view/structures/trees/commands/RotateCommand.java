package view.structures.trees.commands;

import view.commons.commands.Command;
import view.structures.trees.BSTHeightBalancedView;
import view.structures.trees.BSTNodeView;

public class RotateCommand implements Command {
    private int side;
    private BSTNodeView node;
    private BSTHeightBalancedView bstView;

    public RotateCommand(int side, BSTNodeView node, BSTHeightBalancedView bstView) {
        this.side = side;
        this.node = node;
        this.bstView = bstView;
    }

    @Override
    public boolean canExecute() {
        return true;
    }

    @Override
    public void execute() {
        //Esta linea viene de BSTNodeRotatedAnimation
        node = bstView.getNodesVisited().pollLast();
        
        bstView.setChangeLocation(false);
        BSTNodeView temp;
        if (side == BSTNodeView.LEFT)
            temp = node.rotateLeft(node);
        else
            temp = node.rotateRight(node);

        bstView.getNodesVisited().addLast(temp);
        temp.changeSelectionColor(BSTNodeView.DEF_COLOR_VISITED);

        if (bstView.isModeDelete()) {
            bstView.setLastPolled(node);
        }
    }
}  
