/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.structures.stacks.animations;

import java.awt.geom.Point2D;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import view.commons.animations.AbstractUndoAnimationSteps;
import view.commons.animations.MobileAnimationSteps;
import view.commons.commands.Command;
import view.commons.commands.ShowMessageCommand;
import view.commons.commands.StepFinishedCommand;
import view.structures.stacks.StackNodeRoles;
import view.structures.stacks.StackNodeView;
import view.structures.stacks.StackView;
import view.structures.stacks.commands.AssignNodeRoleCommand;
import view.structures.stacks.commands.LinkMobilesCommand;
import view.structures.stacks.commands.RemoveNodeCommand;

/**
 *
 * @author pgorin
 */
public class ItemPoppedAnimation<T> extends AbstractUndoAnimationSteps {

    private static final int DELTA_HORIZONTAL = 100;
    private static final int DELTA_VERTCAL = 100;
    private StackView view;

    public ItemPoppedAnimation(StackView view) {
        this.view = view;
    }

    @Override
    protected void initializeListUndoSteps() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void initializeListSteps() {
        steps = new ArrayList<Command>();

        List<StackNodeView> stackNodes = view.getStackNodes();
        int stackSize = stackNodes.size();
        StackNodeView<T> poppedNode = stackNodes.get(stackSize - 1);

        //remove top role of the popped item.
        steps.add(new AssignNodeRoleCommand(poppedNode, null));
        final StackNodeView<T> poppedParentNode = poppedNode.getParent();

        //assign top role to the parent popped item.
        if ((poppedParentNode != null) && (stackSize > 2)) {
            steps.add(new AssignNodeRoleCommand(poppedNode.getParent(), StackNodeRoles.top));
        }

        //unlink the popped item as bottom of the queue.
        steps.add(new LinkMobilesCommand(poppedNode, false));

        //move the popped item to the final position.
        MobileAnimationSteps moveSteps = new MobileAnimationSteps(view, poppedNode, poppedNode.getPosition(), this.moveNodeToInitialPosition(poppedNode), 8);
        steps.addAll(moveSteps.getSteps());

        //remove the popped item of the stack.
        steps.add(new RemoveNodeCommand(view, poppedNode));
        steps.add(new ShowMessageCommand(view, MessageFormat.format("Item eliminado: {0}", poppedNode.getItem().toString())));
        steps.add(new StepFinishedCommand(view, true));
    }

    private Point2D moveNodeToInitialPosition(StackNodeView<T> node) {
        Point2D newPosition = (Point2D) node.getPosition().clone();
        newPosition.setLocation(newPosition.getX(), newPosition.getY() - DELTA_VERTCAL);

        return newPosition;
    }
}
