/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.collection.queue;

import java.awt.BasicStroke;
import java.awt.Color;
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
	private int circularQueueIndex;
	private double arcAngle;

	public QueueView() {
		super();
		this.queueNodes = new LinkedList<QueueNodeView<T>>();
		this.circularQueueIndex = 0;
		this.arcAngle = 0;
	}

	@Override
	public void itemEnqueued(T item) {
		int queueSize = this.queueNodes.size();
		QueueNodeView<T> parentNode = (queueSize > 0) ? this.getQueueNodes()
				.get(queueSize - 1) : null;

		QueueNodeView<T> node = new QueueNodeView<T>(item, queueSize, INITIAL_HORIZONTAL, INITIAL_VERTICAL,
													 parentNode, circularQueueIndex, arcAngle);

		this.getQueueNodes().add(node);
		this.circularQueueIndex = (this.circularQueueIndex + 1) % structureCapacity;

		this.addCommandToQueue(new ShowPrimitiveCodeCommand(this, QueuePrimitives.enqueue.getCode()));
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
		graphics.setPaint(DefaultShapeSettings.SHADOW_COLOR);
		graphics.setStroke(new BasicStroke(3f));

		// Vista previa de la capacidad de la cola
		for (Shape shape : capacityPreviewNodes) {
			graphics.fill(shape);
		}
		
		int initial_offset = DefaultShapeSettings.INITIAL_CIRCULAR_QUEUE_VERTICAL;
		int max_radius = DefaultShapeSettings.CIRCULAR_QUEUE_MAX_RADIUS;
		int min_radius = DefaultShapeSettings.CIRCULAR_QUEUE_MIN_RADIUS;
		
		// Círculo de radio mayor
		graphics.fillOval(initial_offset, initial_offset, max_radius*2, max_radius*2);
		
		graphics.setPaint(new Color(0, 0, 0));
		
		int centerX = initial_offset + max_radius;
		int centerY = centerX;
		
		// Índices de la cola circular
		for (int i = 0; i < structureCapacity; i++) {
			int finalX = (int) (centerX - (max_radius * Math.cos(i * arcAngle + Math.PI/32) * 1.1));
			int finalY = (int) (centerY - (max_radius * Math.sin(i * arcAngle + Math.PI/32) * 1.1));
			graphics.drawString(String.valueOf(i), finalX, finalY);
		}

		graphics.setPaint(new Color(255, 255, 255));
		
		// Separadores de nodos de la cola circular
		if (structureCapacity > 1) {
			for (int i = 0; i < structureCapacity; i++) {
				int finalX = (int) (centerX - max_radius * Math.cos(i * arcAngle));
				int finalY = (int) (centerY - max_radius * Math.sin(i * arcAngle));
				graphics.drawLine(centerX, centerY, finalX, finalY);
			}
		}
		
		// Círculo de radio menor
		graphics.fillOval(initial_offset + min_radius, initial_offset + min_radius, max_radius, max_radius);
		
		// Nodos
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
		circularQueueIndex = 0;
		arcAngle = 0;
	}
	
	public void initCapacity(Integer capacity) {
		capacityPreviewNodes.clear();
		queueNodes.clear();
		
		for (int i = 0; i < capacity; i++) {
			capacityPreviewNodes.add(new Rectangle(INITIAL_HORIZONTAL + i*(QueueNodeShape.DEF_WIDTH_NODE +
														DefaultShapeSettings.DISTANCE_BETWEEN_QUEUE_NODES),
												   INITIAL_VERTICAL + (QueueNodeShape.DEF_HEIGHT_NODE +
														DefaultShapeSettings.DISTANCE_BETWEEN_QUEUE_NODES),
												   QueueNodeShape.DEF_WIDTH_NODE,
												   QueueNodeShape.DEF_HEIGHT_NODE));
		}
		
		arcAngle = 2 * Math.PI / capacity;
		
		setStructureCapacity(capacity);
	}
	
	@Override
	protected void adjustGraphicDimensionForScrolling() {
		this.graphicDimension = new Dimension(2*INITIAL_HORIZONTAL +
											  structureCapacity * (QueueNodeShape.DEF_WIDTH_NODE +
													  	DefaultShapeSettings.DISTANCE_BETWEEN_QUEUE_NODES),
											  DefaultShapeSettings.INITIAL_CIRCULAR_QUEUE_VERTICAL
											  + DefaultShapeSettings.CIRCULAR_QUEUE_MAX_RADIUS * 2
											  + INITIAL_VERTICAL);
	}
}
