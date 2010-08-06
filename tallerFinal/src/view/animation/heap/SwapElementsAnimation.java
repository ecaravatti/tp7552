/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.animation.heap;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import view.animation.common.AbstractUndoAnimationSteps;
import view.animation.common.AnimationSteps;
import view.animation.common.MobileAnimationSteps;
import view.collection.heap.HeapView;
import view.collection.tree.BSTNodeShape;
import view.command.common.ChangeColorBSTNodeShapeCommand;
import view.command.common.ChangeTextColorCommand;
import view.command.common.Command;
import view.command.common.ShowMessageCommand;
import view.command.common.StepFinishedCommand;
import view.shape.DefaultShapeSettings;
import view.shape.Text;

/**
 *
 */
public class SwapElementsAnimation<T> extends AbstractUndoAnimationSteps {

	private static final int FACT = 3;
	private static final int PATH_LENGTH = 40;
	private HeapView<T> view;
	private Text parentLabel;
	private Text currentLabel;
	private BSTNodeShape parentNode;
	private BSTNodeShape currentNode;
	private double deltaLabel;
	private double deltaNode;

	public SwapElementsAnimation(HeapView<T> view) {
		this.view = view;
	}

	private void moveElements(List<Command> commands) {

		String text = "";
		if (parentLabel.getTitle().equals("")) {
			text = "Moviendo elemento " + currentLabel.getTitle()
					+ " a la raíz ...";
		} else {
			text = "Moviendo elementos " + currentLabel.getTitle() + " y "
					+ parentLabel.getTitle() + " ...";
		}
		commands.add(new ShowMessageCommand(view, text));

		AnimationSteps moveParent = new MobileAnimationSteps(view, parentLabel,
				parentLabel.getPosition(), currentLabel.getPosition(),
				deltaLabel * FACT);
		AnimationSteps moveCurrent = new MobileAnimationSteps(view,
				currentLabel, currentLabel.getPosition(),
				parentLabel.getPosition(), deltaLabel * FACT);

		AnimationSteps moveParentNode = new MobileAnimationSteps(view,
				parentNode, parentNode.getPosition(),
				currentNode.getPosition(), deltaNode * FACT);
		AnimationSteps moveCurrentNode = new MobileAnimationSteps(view,
				currentNode, currentNode.getPosition(),
				parentNode.getPosition(), deltaNode * FACT);

		List<Command> parentMovements = moveParent.getSteps();
		List<Command> currentMovements = moveCurrent.getSteps();

		List<Command> parentNodeMovements = moveParentNode.getSteps();
		List<Command> currentNodeMovements = moveCurrentNode.getSteps();

		int i = 0;
		for (i = 0; i < parentMovements.size()
				&& i < parentNodeMovements.size(); i++) {
			commands.add(parentMovements.get(i));
			commands.add(currentMovements.get(i));
			commands.add(parentNodeMovements.get(i));
			commands.add(currentNodeMovements.get(i));
		}

		for (int j = i; j < parentMovements.size(); j++) {
			commands.add(parentMovements.get(j));
			commands.add(currentMovements.get(j));
		}

		for (int j = i; j < parentNodeMovements.size(); j++) {
			commands.add(parentNodeMovements.get(j));
			commands.add(currentNodeMovements.get(j));
		}

		if (parentLabel.getTitle().equals("")) {
			text = "Se movió el elemento " + currentLabel.getTitle()
					+ " a la raíz";
		} else {
			text = "Se intercambió el elemento " + currentLabel.getTitle()
					+ " con el elemento " + parentLabel.getTitle();
		}
		commands.add(new ShowMessageCommand(view, text));
	}

	@Override
	protected void initializeListUndoSteps() {
		this.undoSteps = new ArrayList<Command>();

		changeLabelColor(Color.BLUE, undoSteps);
		changeNodeColor(Color.BLUE, undoSteps);

		moveElements(undoSteps);

		changeLabelColor(Color.BLACK, undoSteps);
		changeNodeColor(DefaultShapeSettings.GREEN_COLOR, undoSteps);

		this.undoSteps.add(new StepFinishedCommand(view, false, this));
	}

	@Override
	protected void initializeListSteps() {
		parentLabel = view.getParentLabel();
		currentLabel = view.getCurrentLabel();

		parentNode = view.getParentNode();
		currentNode = view.getCurrentNode();

		calculateDelta();

		steps = new ArrayList<Command>();

		changeLabelColor(DefaultShapeSettings.RED_COLOR, steps);
		changeNodeColor(DefaultShapeSettings.RED_COLOR, steps);

		moveElements(steps);

		changeLabelColor(Color.BLACK, steps);
		changeNodeColor(DefaultShapeSettings.GREEN_COLOR, steps);

		steps.add(new StepFinishedCommand(view, false, this));
	}

	private void changeLabelColor(Color color, List<Command> commands) {
		commands.add(new ChangeTextColorCommand(parentLabel, color));
		commands.add(new ChangeTextColorCommand(currentLabel, color));
	}

	private void changeNodeColor(Color color, List<Command> commands) {
		commands.add(new ChangeColorBSTNodeShapeCommand(parentNode, color));
		commands.add(new ChangeColorBSTNodeShapeCommand(currentNode, color));
	}

	private void calculateDelta() {
		deltaLabel = currentLabel.getPosition().distance(
				parentLabel.getPosition())
				/ PATH_LENGTH;
		deltaNode = currentNode.getPosition()
				.distance(parentNode.getPosition()) / PATH_LENGTH;
	}
}
