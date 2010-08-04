/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.animation.queue;

import java.awt.geom.Point2D;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import view.animation.common.AbstractUndoAnimationSteps;
import view.animation.common.MobileAnimationSteps;
import view.collection.queue.QueueNodeRoles;
import view.collection.queue.QueueNodeShape;
import view.collection.queue.QueueNodeView;
import view.collection.queue.QueueView;
import view.command.common.Command;
import view.command.common.ShowMessageCommand;
import view.command.common.StepFinishedCommand;
import view.command.queue.AssignNodeIndexCommand;
import view.command.queue.AssignNodeRoleCommand;
import view.command.queue.LinkMobilesCommand;
import view.command.queue.RemoveNodeCommand;
import view.shape.DefaultShapeSettings;

/**
 *
 */
public class ItemDequeuedAnimation<T> extends AbstractUndoAnimationSteps {

    private QueueView<T> view;

    public ItemDequeuedAnimation(QueueView<T> view) {
        this.view = view;
    }

    @Override
    protected void initializeListUndoSteps() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void initializeListSteps() {
        steps = new ArrayList<Command>();

        List<QueueNodeView<T>> queueNodes = view.getQueueNodes();
        int queueSize = queueNodes.size();
        QueueNodeView<T> dequeuedNode = queueNodes.get(0);

        //unlink the dequeued item as head of the queue.
        if (queueSize > 1) {
            steps.add(new LinkMobilesCommand(queueNodes.get(1), false));
        }

        //remove head role of the popped item.
        steps.add(new AssignNodeRoleCommand<T>(dequeuedNode, null));

        //remove zero index of the dequeued item.
        steps.add(new AssignNodeIndexCommand<T>(dequeuedNode, null));

        //move the dequeued item to the final position.
        MobileAnimationSteps moveSteps = new MobileAnimationSteps(view, dequeuedNode, dequeuedNode.getPosition(), this.moveNodeToInitialPosition(dequeuedNode), 8);
        steps.addAll(moveSteps.getSteps());

        //assign top role to the next item of the queue.
        if (queueSize > 1) {
            steps.add(new AssignNodeRoleCommand<T>(queueNodes.get(1), QueueNodeRoles.head));
        }

        //move one position to the right each item in the queue except the dequeued one.
        //reassign index to each item in the queue excepto the dequeued one.
        for (QueueNodeView<T> nodeToMove : queueNodes) {
            if (!nodeToMove.equals(dequeuedNode)) {
                MobileAnimationSteps moveRightSteps = new MobileAnimationSteps(view, nodeToMove, nodeToMove.getPosition(), this.moveNodeToLeft(nodeToMove), 8);
                steps.addAll(moveRightSteps.getSteps());
                steps.add(new AssignNodeIndexCommand<T>(nodeToMove, queueNodes.indexOf(nodeToMove) - 1));
                if (nodeToMove.getParent().equals(dequeuedNode)) {
                	nodeToMove.removeParent();
                }
            }
        }

        //remove the dequeued item of the queue.
        steps.add(new RemoveNodeCommand<T>(view, dequeuedNode));
        steps.add(new ShowMessageCommand(view, MessageFormat.format("Item eliminado: {0}", dequeuedNode.getItem().toString())));
        steps.add(new StepFinishedCommand(view, false));
    }

    private Point2D moveNodeToInitialPosition(QueueNodeView<T> node) {
        Point2D newPosition = (Point2D) node.getPosition().clone();
        newPosition.setLocation(newPosition.getX(), newPosition.getY()
        											- DefaultShapeSettings.DISTANCE_BETWEEN_QUEUE_NODES * 2);

        return newPosition;
    }

    private Point2D moveNodeToLeft(QueueNodeView<T> node) {
        Point2D newPosition = (Point2D) node.getPosition().clone();
        newPosition.setLocation(newPosition.getX() - QueueNodeShape.DEF_WIDTH_NODE
        						- DefaultShapeSettings.DISTANCE_BETWEEN_QUEUE_NODES, newPosition.getY());

        return newPosition;
    }

}
