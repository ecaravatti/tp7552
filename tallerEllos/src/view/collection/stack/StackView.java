/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.collection.stack;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import view.animation.stack.ItemPoppedAnimation;
import view.animation.stack.ItemPushedAnimation;
import view.command.common.ShowMessageCommand;
import view.command.common.ShowPrimitiveCodeCommand;
import view.command.common.StepFinishedCommand;
import view.common.AnimatedPanel;
import view.shape.NodeShape;
import view.shape.Text;
import event.stack.StackListener;

/**
 * Queue data structure view.
 */
public class StackView<T> extends AnimatedPanel implements StackListener<T> {

	private static final long serialVersionUID = 1L;
	private static final int INITIAL_HORIZONTAL = 50;
	private static final int INITIAL_VERTICAL = 435;
	private static final Font FONT = new Font("SansSerif", Font.BOLD, 12);
	private static final Stroke STROKE = new BasicStroke(1.0f);
	private static final int SQUARESIZE = 25;
	private List<NodeShape> rectangles = new ArrayList<NodeShape>();
	//private GroupLayout
	private List<Text> labels = new ArrayList<Text>();
	private List<StackNodeView<T>> stackNodes;
	private int labelPosX = 260;
	private int labelPosY = 504;
	private Text lblCapacity = new Text("Stack Capacity", FONT, new Point2D.Double(225,480));

	public StackView() {
		super();
		this.stackNodes = new LinkedList<StackNodeView<T>>();
	}

	@Override
	public void itemPushed(T item) {
		int stackSize = this.stackNodes.size();
		
		this.labels.add(createLabel(String.valueOf(item)));
		
		StackNodeView<T> parentNode = (stackSize > 0) ? this.stackNodes
				.get(stackSize - 1) : null;
		StackNodeView<T> node = new StackNodeView<T>(item, stackSize,
				INITIAL_HORIZONTAL, INITIAL_VERTICAL, parentNode);

		this.stackNodes.add(node);

		this.addCommandToQueue(new ShowPrimitiveCodeCommand(this,
				StackPrimitives.push.getCode()));
		this.addAnimationToQueue(new ItemPushedAnimation<T>(this, node));
		this.addCommandToQueue(new StepFinishedCommand(this, true));
		
		rerender();
	}

	@Override
	public void itemPopped(T item) {
		this.labels.remove(this.labels.size() - 1);
		
		this.addCommandToQueue(new ShowPrimitiveCodeCommand(this,
				StackPrimitives.pop.getCode()));
		this.addAnimationToQueue(new ItemPoppedAnimation<T>(this));
		this.addCommandToQueue(new StepFinishedCommand(this, true));
		rerender();
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
		lblCapacity.paint(graphics);
		
		for (NodeShape nodeShape : rectangles) {
			nodeShape.paint(graphics);
		}
		for (Text label : labels) {
			label.paint(graphics);
		}
		for (StackNodeView<T> node : getStackNodes()) {
			node.paintElement(graphics);
		}
	}
	
	private Text createLabel(String title) {
		int current = this.stackNodes.size();
		Point2D currentPosition = new Point2D.Double(labelPosX + (current - 1) * 25,
				labelPosY);
		return new Text(title, FONT, currentPosition);
	}
	
	
	public void initStackSampleCapacity(Integer capacity) {
		rectangles.clear();
		labels.clear();
		stackNodes.clear();
		
		for (int i = 1; i < capacity + 1; i++) {
			rectangles.add(createRect(i * SQUARESIZE + 200, INITIAL_VERTICAL + 50, SQUARESIZE, SQUARESIZE, ""));
		}
		repaint();
	}
	private NodeShape createRect(int x, int y, int width, int height, String label) {
		Point2D position = new Point2D.Double(x, y);
		// Nodo rectangular con colores por defecto
		return new NodeShape(label, position, width, height, FONT, STROKE, false);
	}
	

	/**
	 * @return the stackNodes
	 */
	public List<StackNodeView<T>> getStackNodes() {
		return stackNodes;
	}

}
