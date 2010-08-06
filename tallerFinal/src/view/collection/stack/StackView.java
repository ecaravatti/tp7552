/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.collection.stack;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import view.animation.stack.ItemPoppedAnimation;
import view.animation.stack.ItemPushedAnimation;
import view.command.common.ShowMessageCommand;
import view.command.common.ShowPrimitiveCodeCommand;
import view.command.common.StepFinishedCommand;
import view.common.AnimatedPanel;
import view.shape.DefaultShapeSettings;
import event.stack.StackListener;

/**
 * Queue data structure view.
 */
public class StackView<T> extends AnimatedPanel implements StackListener<T> {

	private static final long serialVersionUID = 1L;

	private static final int INITIAL_HORIZONTAL = 250;
	private static final int INITIAL_VERTICAL = 20;

	// private GroupLayout
	private List<Shape> capacityPreviewNodes = new ArrayList<Shape>();
	private List<StackNodeView<T>> stackNodes;

	public StackView() {
		super();
		this.stackNodes = new LinkedList<StackNodeView<T>>();
	}

	@Override
	public void itemPushed(T item) {
		int stackSize = this.stackNodes.size();

		StackNodeView<T> parentNode = (stackSize > 0) ? this.stackNodes
				.get(stackSize - 1) : null;
		StackNodeView<T> node = new StackNodeView<T>(item, stackSize,
				INITIAL_HORIZONTAL, INITIAL_VERTICAL, parentNode);

		this.stackNodes.add(node);

		this.addCommandToQueue(new ShowPrimitiveCodeCommand(this,
				StackPrimitives.push.getCode()));
		this.addAnimationToQueue(new ItemPushedAnimation<T>(this, node));
		this.addCommandToQueue(new StepFinishedCommand(this, true));
	}

	@Override
	public void itemPopped(T item) {
		this.addCommandToQueue(new ShowPrimitiveCodeCommand(this,
				StackPrimitives.pop.getCode()));
		this.addAnimationToQueue(new ItemPoppedAnimation<T>(this));
		this.addCommandToQueue(new StepFinishedCommand(this, true));
	}

	@Override
	public void emptyStackCondition() {
		this.addCommandToQueue(new ShowPrimitiveCodeCommand(this,
				StackPrimitives.pop.getCode()));
		this.addCommandToQueue(new ShowMessageCommand(this,
				"La pila se encuentra vac�a."));
		this.addCommandToQueue(new StepFinishedCommand(this, true));
	}

	@Override
	public void paintPanel(Graphics2D graphics) {
		graphics.setPaint(DefaultShapeSettings.SHADOW_COLOR);
		for (Shape shape : capacityPreviewNodes) {
			graphics.fill(shape);
		}
		for (StackNodeView<T> node : getStackNodes()) {
			node.paintElement(graphics);
		}
	}

	public void initCapacity(Integer capacity) {
		capacityPreviewNodes.clear();
		stackNodes.clear();

		for (int i = 0; i < capacity; i++) {
			capacityPreviewNodes
					.add(new Rectangle(
							INITIAL_HORIZONTAL,
							INITIAL_VERTICAL
									+ (StackNodeShape.DEF_HEIGHT_NODE + DefaultShapeSettings.DISTANCE_BETWEEN_STACK_NODES)
									* (i + 1), StackNodeShape.DEF_WIDTH_NODE,
							StackNodeShape.DEF_HEIGHT_NODE));
		}

		setStructureCapacity(capacity);
	}

	public void clear() {
		this.stackNodes.clear();
	}

	/**
	 * @return the stackNodes
	 */
	public List<StackNodeView<T>> getStackNodes() {
		return stackNodes;
	}

	@Override
	protected void adjustGraphicDimensionForScrolling() {
		this.graphicDimension = new Dimension(
				2 * INITIAL_HORIZONTAL + StackNodeShape.DEF_WIDTH_NODE,
				2
						* INITIAL_VERTICAL
						+ (StackNodeShape.DEF_HEIGHT_NODE + DefaultShapeSettings.DISTANCE_BETWEEN_STACK_NODES)
						* (structureCapacity + 1));
	}

}
