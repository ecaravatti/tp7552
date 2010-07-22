package view.structures.trees.animations;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import view.commons.animations.AbstractUndoAnimationSteps;
import view.commons.animations.AnimationSteps;
import view.commons.animations.MobileAnimationSteps;
import view.commons.commands.Command;
import view.commons.commands.SelectElementViewCommand;
import view.commons.commands.ShowMessageCommand;
import view.commons.commands.StepFinishedCommand;
import view.commons.commands.StopFlashingElementViewCommand;
import view.structures.trees.BSTHeightBalancedView;
import view.structures.trees.BSTNodeView;
import view.structures.trees.commands.ActualizeParentCommand;
import view.structures.trees.commands.RemoveCommand;
import view.structures.trees.commands.RestoreBSTCommand;

/**
 *
 * @author Exe Curia
 */
public class BSTNodeRemovedAnimation extends AbstractUndoAnimationSteps {

    private BSTHeightBalancedView bstView;
    private BSTNodeView node;

    public BSTNodeRemovedAnimation(BSTHeightBalancedView bstView, BSTNodeView node) {
        this.bstView = bstView;
        this.node = node;
        this.setRedoPause(false);
        this.setUndoPause(false);
    }

    @Override
    protected void initializeListUndoSteps() {
        this.undoSteps = new ArrayList<Command>();
        undoSteps.add(new ShowMessageCommand(bstView, "Deshaciendo eliminacion del nodo"));
        undoSteps.add(new RestoreBSTCommand(bstView));
        undoSteps.add(new StopFlashingElementViewCommand(bstView, node));
        undoSteps.add(new StepFinishedCommand(bstView, false, this));
    }

    @Override
    protected void initializeListSteps() {
        steps = new ArrayList<Command>();

        String s;
        s = "Nodo (" + node.getData().toString() + ") ";
        
        String t;
        if (node.left == null && node.right == null)
            t = "es una hoja: puede eliminarse";
        else
            t = "ahora tiene un unico sucesor: puede eliminarse";
        steps.add(new ShowMessageCommand(bstView, s + t));

        steps.add(new SelectElementViewCommand(bstView, bstView.getFlashingDelay(), node, BSTNodeView.DEF_COLOR_FOUND));
        steps.addAll(bstView.getPaintCommands());

        steps.add(new RemoveCommand(bstView, node));
        steps.addAll(bstView.getPaintCommands());
        //steps.add(new ActualizeParentCommand(bstView));

        Point2D finalPos = new Point2D.Double(node.getPosition().getX(), bstView.getHeight() + node.getHeight());
        AnimationSteps animation = new MobileAnimationSteps(bstView, node, node.getPosition(), finalPos, bstView.getDelta());
        steps.addAll(animation.getSteps());
        //steps.addAll(bstView.getPaintCommands());
        
        steps.add(new ShowMessageCommand(bstView, s + "fue eliminado"));

      
        steps.add(new StepFinishedCommand(bstView, false, this));
    }
}
