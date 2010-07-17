/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.structures.queues;

import events.queues.QueueListener;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;
import view.commons.PanelAnimated;
import view.commons.commands.ShowMessageCommand;
import view.commons.commands.ShowPrimitiveCodeCommand;
import view.commons.commands.StepFinishedCommand;
import view.structures.queues.animations.ItemDequeuedAnimation;
import view.structures.queues.animations.ItemEnqueuedAnimation;

/**
 * Queue data structure view.
 * @author pgorin
 */
public class QueueView<T> extends PanelAnimated implements QueueListener<T> {

    private static final int INITIAL_HORIZONTAL = 920;
    private static final int INITIAL_VERTICAL = 50;
    private List<QueueNodeView<T>> queueNodes;

    public QueueView() {
        super();
        this.queueNodes = new LinkedList<QueueNodeView<T>>();
    }

    @Override
    public void itemEnqueued(T item) {
        int queueSize = this.queueNodes.size();
        QueueNodeView<T> parentNode = (queueSize > 0) ? this.getQueueNodes().get(queueSize - 1) : null;

        QueueNodeView<T> node = new QueueNodeView(item, queueSize, INITIAL_HORIZONTAL, INITIAL_VERTICAL, parentNode);

        this.getQueueNodes().add(node);

        this.addCommandToQueue(new ShowPrimitiveCodeCommand(this, QueuePrimitives.enqueue.getCode()));
        this.addAnimationToQueue(new ItemEnqueuedAnimation(this, node));
        this.addCommandToQueue(new StepFinishedCommand(this, true));
    }

    @Override
    public void itemDequeued(T item) {
        this.addCommandToQueue(new ShowPrimitiveCodeCommand(this, QueuePrimitives.dequeue.getCode()));
        this.addAnimationToQueue(new ItemDequeuedAnimation(this));
        this.addCommandToQueue(new StepFinishedCommand(this, true));
    }

    @Override
    public void emptyQueueCondition() {
        this.addCommandToQueue(new ShowPrimitiveCodeCommand(this, QueuePrimitives.dequeue.getCode()));
        this.addCommandToQueue(new ShowMessageCommand(this, "La cola se encuentra vacía."));
        this.addCommandToQueue(new StepFinishedCommand(this, true));
    }

    @Override
    public void paintPanel(Graphics2D graphics) {
        for (QueueNodeView<T> node : getQueueNodes()) {
            node.paintElement(graphics);
        }
    }

    /**
     * @return the queueNodes
     */
    public List<QueueNodeView<T>> getQueueNodes() {
        return queueNodes;
    }
}
