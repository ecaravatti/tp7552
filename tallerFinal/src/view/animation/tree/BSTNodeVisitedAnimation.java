package view.animation.tree;

import java.util.ArrayList;

import view.animation.common.AbstractUndoAnimationSteps;
import view.collection.tree.BSTNodeView;
import view.collection.tree.BinarySearchTreeView;
import view.command.common.Command;
import view.command.common.SelectElementViewCommand;
import view.command.common.ShowMessageCommand;
import view.command.common.StepFinishedCommand;
import view.command.common.StopFlashingElementViewCommand;
import view.command.tree.ChangeSelectableColorCommand;

/**
 *
 * 
 */
public class BSTNodeVisitedAnimation extends AbstractUndoAnimationSteps {

	private BinarySearchTreeView bstView;
	private BSTNodeView node;
	SelectionMode mode;

	public enum SelectionMode {
		VISITED, FOUND, TRAVERSED,
	}

	public BSTNodeVisitedAnimation(BinarySearchTreeView bstView,
			BSTNodeView node, SelectionMode mode) {
		this.bstView = bstView;
		this.node = node;
		this.mode = mode;
		this.setRedoPause(false);
		this.setUndoPause(false);
	}

	@Override
	protected void initializeListUndoSteps() {
		undoSteps = new ArrayList<Command>();
		undoSteps.add(new ShowMessageCommand(bstView,
				"Deshaciendo nodo visitado..."));
		undoSteps.add(new StopFlashingElementViewCommand(bstView, node));
		undoSteps.add(new StepFinishedCommand(bstView, false, this));
	}

	@Override
	protected void initializeListSteps() {
		steps = new ArrayList<Command>();

		switch (mode) {
		case VISITED:
			steps.add(new ShowMessageCommand(bstView, "Visitando nodo ("
					+ node.getData().toString() + ")"));

			steps.add(new SelectElementViewCommand(bstView, bstView
					.getFlashingDelay(), node, BSTNodeView.DEF_COLOR_SELECTION));
			steps.addAll(bstView.getPaintCommands());
			steps.add(new ChangeSelectableColorCommand(bstView, node,
					BSTNodeView.DEF_COLOR_VISITED));
			// steps.add(new StopFlashingElementViewCommand(bstView, node));
			steps.addAll(bstView.getPaintCommands());
			steps.add(new SelectElementViewCommand(bstView, bstView
					.getFlashingDelay(), node, BSTNodeView.DEF_COLOR_SELECTION));
			steps.addAll(bstView.getPaintCommands());
			steps.add(new ChangeSelectableColorCommand(bstView, node,
					BSTNodeView.DEF_COLOR_VISITED));
			break;

		case FOUND:
			steps.add(new ShowMessageCommand(bstView, "Nodo nodo ("
					+ node.getData().toString()
					+ ") encontrado: no se puede insertar"));

			steps.add(new SelectElementViewCommand(bstView, bstView
					.getFlashingDelay(), node, BSTNodeView.DEF_COLOR_FOUND));
			steps.addAll(bstView.getPaintCommands());
			break;

		case TRAVERSED:
			steps.add(new ShowMessageCommand(bstView, "Nodo ("
					+ node.getData().toString() + ") recorrido"));

			steps.add(new SelectElementViewCommand(bstView, bstView
					.getFlashingDelay(), node, BSTNodeView.DEF_COLOR_FOUND));
			steps.addAll(bstView.getPaintCommands());
			steps.addAll(bstView.getPaintCommands());
			steps.add(new StopFlashingElementViewCommand(bstView, node));
			break;
		}

		// steps.add(new StepFinishedCommand(bstView, false));
		// para el deshacer
		steps.add(new StepFinishedCommand(bstView, false, this));
	}
}
