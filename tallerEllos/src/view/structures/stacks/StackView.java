/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.structures.stacks;

import events.stacks.StackListener;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;
import view.commons.PanelAnimated;
import view.commons.commands.ShowMessageCommand;
import view.commons.commands.ShowPrimitiveCodeCommand;
import view.commons.commands.StepFinishedCommand;
import view.structures.stacks.animations.ItemPoppedAnimation;
import view.structures.stacks.animations.ItemPushedAnimation;

/**
 * Queue data structure view.
 * @author pgorin
 */
public class StackView<T> extends PanelAnimated implements StackListener<T> {

    private static final int INITIAL_HORIZONTAL = 920;
    private static final int INITIAL_VERTICAL = 50;
    private List<StackNodeView<T>> stackNodes;

    public StackView() {
        super();
        this.stackNodes = new LinkedList<StackNodeView<T>>();
    }

    @Override
    public void itemPushed(T item) {
        int stackSize = this.stackNodes.size();
        StackNodeView<T> parentNode = (stackSize > 0) ? this.stackNodes.get(stackSize - 1) : null;
        StackNodeView<T> node = new StackNodeView(item, stackSize, INITIAL_HORIZONTAL, INITIAL_VERTICAL, parentNode);

        this.stackNodes.add(node);

        this.addCommandToQueue(new ShowPrimitiveCodeCommand(this, StackPrimitives.push.getCode()));
        this.addAnimationToQueue(new ItemPushedAnimation(this, node));
        this.addCommandToQueue(new StepFinishedCommand(this, true));
    }

    @Override
    public void itemPopped(T item) {
        this.addCommandToQueue(new ShowPrimitiveCodeCommand(this, StackPrimitives.pop.getCode()));
        this.addAnimationToQueue(new ItemPoppedAnimation(this));
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
