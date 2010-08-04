/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.collection.queue;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import view.animation.queue.ItemDequeuedAnimation;
import view.animation.queue.ItemEnqueuedAnimation;
import view.command.common.ShowMessageCommand;
import view.command.common.ShowMessageDialogCommand;
import view.command.common.ShowPrimitiveCodeCommand;
import view.command.common.StepFinishedCommand;
import view.common.AnimatedPanel;
import view.shape.DefaultShapeSettings;
import event.queue.QueueListener;

/**
 * Queue data structure view.
 */
public class QueueView<T> extends AnimatedPanel implements QueueListener<T> {

	private static final long serialVersionUID = 1L;

	private static final int INITIAL_HORIZONTAL = 20;
	private static final int INITIAL_VERTICAL = 20;
	
	private List<Shape> capacityPreviewNodes = new ArrayList<Shape>();
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
	}

	@Override
	public void itemDequeued(T item) {
		this.addCommandToQueue(new ShowPrimitiveCodeCommand(this,
				QueuePrimitives.dequeue.getCode()));
		this.addAnimationToQueue(new ItemDequeuedAnimation<T>(this));
		this.addCommandToQueue(new StepFinishedCommand(this, true));
	}

	@Override
	public void emptyQueueCondition() {
		this.addCommandToQueue(new ShowPrimitiveCodeCommand(this,
				QueuePrimitives.dequeue.getCode()));
		this.addCommandToQueue(new ShowMessageCommand(this,
				"La cola se encuentra vacía."));
		this.addCommandToQueue(new ShowMessageDialogCommand("La cola se encuentra" +
				" vacía", "Warning", JOptionPane.WARNING_MESSAGE));
		this.addCommandToQueue(new StepFinishedCommand(this, true));
	}

	@Override
	public void paintPanel(Graphics2D graphics) {
		for (Shape shape : capacityPreviewNodes) {
			graphics.setPaint(DefaultShapeSettings.SHADOW_COLOR);
			graphics.fill(shape);
		}
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

	public void clear() {
		this.queueNodes = new LinkedList<QueueNodeView<T>>();
	}
	
	public void initCapacity(Integer capacity) {
		capacityPreviewNodes.clear();
		queueNodes.clear();
		
		for (int i = 0; i < capacity; i++) {
			new Rectangle(INITIAL_HORIZONTAL + i*(50+50), 100, 50, 50);
			capacityPreviewNodes.add(new Rectangle(INITIAL_HORIZONTAL + i*(QueueNodeShape.DEF_WIDTH_NODE +
														DefaultShapeSettings.DISTANCE_BETWEEN_QUEUE_NODES),
												   INITIAL_VERTICAL + (QueueNodeShape.DEF_HEIGHT_NODE +
														DefaultShapeSettings.DISTANCE_BETWEEN_QUEUE_NODES),
												   QueueNodeShape.DEF_WIDTH_NODE,
												   QueueNodeShape.DEF_HEIGHT_NODE));
		}
		
		setStructureCapacity(capacity);
	}
	
	@Override
	protected void adjustGraphicDimensionForScrolling() {
		this.graphicDimension = new Dimension(2*INITIAL_HORIZONTAL +
											  structureCapacity * (QueueNodeShape.DEF_WIDTH_NODE +
													  	DefaultShapeSettings.DISTANCE_BETWEEN_QUEUE_NODES),
											  2*INITIAL_VERTICAL + 3*QueueNodeShape.DEF_HEIGHT_NODE);
	}
}
