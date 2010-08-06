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
import view.command.tree.RemoveCommand;
import view.command.tree.RestoreBSTCommand;

/**
 *
 * 
 */
public class BSTNodeRemovedAnimation extends AbstractUndoAnimationSteps {

	private BinarySearchTreeView bstView;
	private BSTNodeView node;

	public BSTNodeRemovedAnimation(BinarySearchTreeView bstView,
			BSTNodeView node) {
		this.bstView = bstView;
		this.node = node;
		this.setRedoPause(false);
		this.setUndoPause(false);
	}

	@Override
	protected void initializeListUndoSteps() {
		this.undoSteps = new ArrayList<Command>();
		undoSteps.add(new ShowMessageCommand(bstView,
				"Deshaciendo eliminacion del nodo"));
		undoSteps.add(new RestoreBSTCommand(bstView));
		undoSteps.add(new StopFlashingElementViewCommand(bstView, node));
		undoSteps.add(new StepFinishedCommand(bstView, false, this));
	}

	@Override
	protected void initializeListSteps() {
		steps = new ArrayList<Command>();

		String s;
		s = "Nodo (" + node.getData().toString() + ") ";

		String t;
		if (node.left == null && node.right == null)
			t = "es una hoja: puede eliminarse";
		else
			t = "ahora tiene un unico sucesor: puede eliminarse";
		steps.add(new ShowMessageCommand(bstView, s + t));

		steps.add(new SelectElementViewCommand(bstView, bstView
				.getFlashingDelay(), node, BSTNodeView.DEF_COLOR_FOUND));
		steps.addAll(bstView.getPaintCommands());

		steps.add(new RemoveCommand(bstView, node));
		steps.addAll(bstView.getPaintCommands());
		// steps.add(new UpdateParentCommand(bstView));

		// Point2D finalPos = new Point2D.Double(node.getPosition().getX(),
		// bstView.getHeight() + node.getHeight());
		// AnimationSteps animation = new MobileAnimationSteps(bstView, node,
		// node.getPosition(), finalPos, bstView.getDelta());
		// steps.addAll(animation.getSteps());
		// steps.addAll(bstView.getPaintCommands());

		steps.add(new ShowMessageCommand(bstView, s + "fue eliminado"));

		steps.add(new StepFinishedCommand(bstView, false, this));
	}
}
