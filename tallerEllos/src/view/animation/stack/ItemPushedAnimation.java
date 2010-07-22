/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.animation.stack;

import java.awt.geom.Point2D;
import java.text.MessageFormat;
import java.util.ArrayList;

import view.animation.common.AbstractUndoAnimationSteps;
import view.animation.common.MobileAnimationSteps;
import view.collection.stack.StackNodeRoles;
import view.collection.stack.StackNodeView;
import view.collection.stack.StackView;
import view.command.common.Command;
import view.command.common.MakeVisibleCommand;
import view.command.common.ShowMessageCommand;
import view.command.common.StepFinishedCommand;
import view.command.stack.AssignNodeRoleCommand;
import view.command.stack.LinkMobilesCommand;

/**
 *
 */
public class ItemPushedAnimation<T> extends AbstractUndoAnimationSteps {

    private final static int DEF_WIDTH_NODE = 50;
//    private final static int DEF_HEIGTH_NODE = 50;
    private static final int DELTA_HORIZONTAL = 50;
    private static final int DELTA_VERTICAL = 100;
    private StackView<T> view;
    private StackNodeView<T> node;

    public ItemPushedAnimation(StackView<T> view, StackNodeView<T> node) {
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
        StackNodeView<T> pushedParentNode = node.getParent();

        //display the new pushed item.
        this.steps.add(new MakeVisibleCommand(view, node));

        //move the pushed item to the final position.
        this.moveNodeToList(node);

        if (pushedParentNode != null) {
            //link the pushed item as bottom of the stack.
            this.steps.add(new LinkMobilesCommand(node, true));

            //assign new role to the pushed parent item.
            this.steps.add(new AssignNodeRoleCommand<T>(pushedParentNode, (pushedParentNode.getParent() == null) ? StackNodeRoles.bottom : null));
        }

        //assign the role to the pushed item.
        this.steps.add(new AssignNodeRoleCommand<T>(node, StackNodeRoles.top));

        this.steps.add(new ShowMessageCommand(view, MessageFormat.format("Item insertado: {0}", node.getItem().toString())));
        this.steps.add(new StepFinishedCommand(view, true));
    }

    private void moveNodeToList(StackNodeView<T> node) {
        //first move to the right to the final horizontal position.
        Point2D horizontalPosition = (Point2D) node.getPosition().clone();
        horizontalPosition.setLocation(horizontalPosition.getX() - ((this.getNodeIndex(node) - 1) * (DEF_WIDTH_NODE + DELTA_HORIZONTAL)), horizontalPosition.getY());

        steps.addAll(new MobileAnimationSteps(view, node, node.getPosition(), horizontalPosition, 8).getSteps());

        //then move to the final vertical position.
        Point2D verticalPosition = (Point2D) horizontalPosition.clone();
        verticalPosition.setLocation(verticalPosition.getX(), verticalPosition.getY() + DELTA_VERTICAL);

        steps.addAll(new MobileAnimationSteps(view, node, horizontalPosition, verticalPosition, 8).getSteps());
    }

    private int getNodeIndex(StackNodeView<T> node) {
        int index = 1;
        StackNodeView<T> parentNode = node.getParent();
        while (parentNode != null) {
            index++;
            parentNode = parentNode.getParent();
        }

        return index;
    }
}
