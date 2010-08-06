/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.animation.stack;

import java.awt.geom.Point2D;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import view.animation.common.AbstractUndoAnimationSteps;
import view.animation.common.MobileAnimationSteps;
import view.collection.stack.StackNodeRoles;
import view.collection.stack.StackNodeShape;
import view.collection.stack.StackNodeView;
import view.collection.stack.StackView;
import view.command.common.Command;
import view.command.common.ShowMessageCommand;
import view.command.common.StepFinishedCommand;
import view.command.stack.AssignNodeRoleCommand;
import view.command.stack.LinkMobilesCommand;
import view.command.stack.RemoveNodeCommand;
import view.shape.DefaultShapeSettings;

/**
 *
 */
public class ItemPoppedAnimation<T> extends AbstractUndoAnimationSteps {

    private StackView<T> view;

    public ItemPoppedAnimation(StackView<T> view) {
        this.view = view;
    }

    @Override
    protected void initializeListUndoSteps() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void initializeListSteps() {
        steps = new ArrayList<Command>();

        List<StackNodeView<T>> stackNodes = view.getStackNodes();
        int stackSize = stackNodes.size();
        StackNodeView<T> poppedNode = stackNodes.get(stackSize - 1);

        //remove top role of the popped item.
        steps.add(new AssignNodeRoleCommand<T>(poppedNode, null));
        final StackNodeView<T> poppedParentNode = poppedNode.getParent();

        if (poppedParentNode != null) {
        	//assign top role to the parent popped item.
            steps.add(new AssignNodeRoleCommand<T>(poppedNode.getParent(), StackNodeRoles.top));
        }

        //unlink the popped item as bottom of the queue.
        steps.add(new LinkMobilesCommand(poppedNode, false));
        
        //move the popped item to the final position.
        MobileAnimationSteps moveSteps = new MobileAnimationSteps(view, poppedNode, poppedNode.getPosition(), this.moveNodeToInitialPosition(poppedNode), 8);
        steps.addAll(moveSteps.getSteps());

        //remove the popped item of the stack.
        steps.add(new RemoveNodeCommand<T>(view, poppedNode));
        steps.add(new ShowMessageCommand(view, MessageFormat.format("Item eliminado: {0}", poppedNode.getItem().toString())));
        steps.add(new StepFinishedCommand(view, true));
    }

    private Point2D moveNodeToInitialPosition(StackNodeView<T> node) {
        Point2D newPosition = (Point2D) node.getPosition().clone();
        newPosition.setLocation(newPosition.getX(), newPosition.getY()
        		- StackNodeShape.DEF_HEIGHT_NODE - DefaultShapeSettings.DISTANCE_BETWEEN_STACK_NODES);

        return newPosition;
    }
}
