/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.collection.queue;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

import view.animation.queue.ItemDequeuedAnimation;
import view.animation.queue.ItemEnqueuedAnimation;
import view.command.common.ShowMessageCommand;
import view.command.common.ShowPrimitiveCodeCommand;
import view.command.common.StepFinishedCommand;
import view.common.AnimatedPanel;
import event.queue.QueueListener;

/**
 * Queue data structure view.
 */
public class QueueView<T> extends AnimatedPanel implements QueueListener<T> {

	private static final long serialVersionUID = 1L;

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
		QueueNodeView<T> parentNode = (queueSize > 0) ? this.getQueueNodes()
				.get(queueSize - 1) : null;

		QueueNodeView<T> node = new QueueNodeView<T>(item, queueSize,
				INITIAL_HORIZONTAL, INITIAL_VERTICAL, parentNode);

		this.getQueueNodes().add(node);

		this.addCommandToQueue(new ShowPrimitiveCodeCommand(this,
				QueuePrimitives.enqueue.getCode()));
		this.addAnimationToQueue(new ItemEnqueuedAnimation<T>(this, node));
		this.addCommandToQueue(new StepFinishedCommand(this, true));
		rerender();
	}

	@Override
	public void itemDequeued(T item) {
		this.addCommandToQueue(new ShowPrimitiveCodeCommand(this,
				QueuePrimitives.dequeue.getCode()));
		this.addAnimationToQueue(new ItemDequeuedAnimation<T>(this));
		this.addCommandToQueue(new StepFinishedCommand(this, true));
		rerender();
	}

	@Override
	public void emptyQueueCondition() {
		this.addCommandToQueue(new ShowPrimitiveCodeCommand(this,
				QueuePrimitives.dequeue.getCode()));
		this.addCommandToQueue(new ShowMessageCommand(this,
				"La cola se encuentra vacía."));
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
