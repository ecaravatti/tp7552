/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.animation.queue;

import java.awt.geom.Point2D;
import java.text.MessageFormat;
import java.util.ArrayList;

import view.animation.common.AbstractUndoAnimationSteps;
import view.animation.common.MobileAnimationSteps;
import view.collection.queue.QueueNodeRoles;
import view.collection.queue.QueueNodeView;
import view.collection.queue.QueueView;
import view.command.common.Command;
import view.command.common.MakeVisibleCommand;
import view.command.common.ShowMessageCommand;
import view.command.common.StepFinishedCommand;
import view.command.queue.AssignNodeRoleCommand;
import view.command.queue.LinkMobilesCommand;

/**
 *
 */
public class ItemEnqueuedAnimation<T> extends AbstractUndoAnimationSteps {

    private final static int DEF_WIDTH_NODE = 50;
//    private final static int DEF_HEIGTH_NODE = 50;
    private static final int DELTA_HORIZONTAL = 50;
    private static final int DELTA_VERTICAL = 100;
    private QueueView<T> view;
    private QueueNodeView<T> node;

    public ItemEnqueuedAnimation(QueueView<T> view, QueueNodeView<T> node) {
        this.view = view;
        this.node = node;
    }

    @Override
    protected void initializeListUndoSteps() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void initializeListSteps() {
        this.steps = new ArrayList<Command>();
        QueueNodeView<T> enqueuedParentNode = node.getParent();

        //display the new enqueued item.
        this.steps.add(new MakeVisibleCommand(view, node));

        //move the enqueued item to the final position.
        this.moveNodeToList(node);

        if (enqueuedParentNode != null) {
            //link the enqueued item as bottom of the stack.
            this.steps.add(new LinkMobilesCommand(node, true));

            //assign new role to the enqueued parent item.
            this.steps.add(new AssignNodeRoleCommand<T>(enqueuedParentNode, (enqueuedParentNode.getParent() == null) ? QueueNodeRoles.head : null));
        }

        //assign the role to the enqueued item.
        this.steps.add(new AssignNodeRoleCommand<T>(node, (enqueuedParentNode == null) ? QueueNodeRoles.head : QueueNodeRoles.tail));

        this.steps.add(new ShowMessageCommand(view, MessageFormat.format("Item insertado: {0}", node.getItem().toString())));
        this.steps.add(new StepFinishedCommand(view, false));
    }

    private void moveNodeToList(QueueNodeView<T> node) {
        //first move to the right to the final horizontal position.
        Point2D horizontalPosition = (Point2D) node.getPosition().clone();
        horizontalPosition.setLocation(horizontalPosition.getX() - ((this.getNodeIndex(node) - 1) * (DEF_WIDTH_NODE + DELTA_HORIZONTAL)), horizontalPosition.getY());

        steps.addAll(new MobileAnimationSteps(view, node, node.getPosition(), horizontalPosition, 8).getSteps());

        //then move to the final vertical position.
        Point2D verticalPosition = (Point2D) horizontalPosition.clone();
        verticalPosition.setLocation(verticalPosition.getX(), verticalPosition.getY() + DELTA_VERTICAL);

        steps.addAll(new MobileAnimationSteps(view, node, horizontalPosition, verticalPosition, 8).getSteps());
    }

    private int getNodeIndex(QueueNodeView<T> node) {
        int index = 1;
        QueueNodeView<T> parentNode = node.getParent();
        while (parentNode != null) {
            index++;
            parentNode = parentNode.getParent();
        }

        return index;
    }
}
