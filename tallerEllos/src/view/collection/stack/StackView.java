/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.collection.stack;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

import view.animation.stack.ItemPoppedAnimation;
import view.animation.stack.ItemPushedAnimation;
import view.command.common.ShowMessageCommand;
import view.command.common.ShowPrimitiveCodeCommand;
import view.command.common.StepFinishedCommand;
import view.common.AnimatedPanel;
import event.stack.StackListener;

/**
 * Queue data structure view.
 */
public class StackView<T> extends AnimatedPanel implements StackListener<T> {

	private static final long serialVersionUID = 1L;
	private static final int INITIAL_HORIZONTAL = 50;
    private static final int INITIAL_VERTICAL = 450;
    private List<StackNodeView<T>> stackNodes;

    public StackView() {
        super();
        this.stackNodes = new LinkedList<StackNodeView<T>>();
    }

    @Override
    public void itemPushed(T item) {
        int stackSize = this.stackNodes.size();
        StackNodeView<T> parentNode = (stackSize > 0) ? this.stackNodes.get(stackSize - 1) : null;
        StackNodeView<T> node = new StackNodeView<T>(item, stackSize, INITIAL_HORIZONTAL, INITIAL_VERTICAL, parentNode);

        this.stackNodes.add(node);

        this.addCommandToQueue(new ShowPrimitiveCodeCommand(this, StackPrimitives.push.getCode()));
        this.addAnimationToQueue(new ItemPushedAnimation<T>(this, node));
        this.addCommandToQueue(new StepFinishedCommand(this, true));
    }

    @Override
    public void itemPopped(T item) {
        this.addCommandToQueue(new ShowPrimitiveCodeCommand(this, StackPrimitives.pop.getCode()));
        this.addAnimationToQueue(new ItemPoppedAnimation<T>(this));
        this.addCommandToQueue(new StepFinishedCommand(this, true));
    }

    @Override
    public void emptyStackCondition() {
        this.addCommandToQueue(new ShowPrimitiveCodeCommand(this, StackPrimitives.pop.getCode()));
        this.addCommandToQueue(new ShowMessageCommand(this, "La pila se encuentra vacía."));
        this.addCommandToQueue(new StepFinishedCommand(this, true));
    }

    @Override
    public void paintPanel(Graphics2D graphics) {
        for (StackNodeView<T> node : getStackNodes()) {
            node.paintElement(graphics);
        }
    }

    /**
     * @return the stackNodes
     */
    public List<StackNodeView<T>> getStackNodes() {
        return stackNodes;
    }
}
