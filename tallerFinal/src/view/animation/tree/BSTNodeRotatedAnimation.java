package view.animation.tree;

import java.util.ArrayList;

import view.animation.common.AbstractUndoAnimationSteps;
import view.collection.tree.BSTNodeView;
import view.collection.tree.BinarySearchTreeView;
import view.command.common.Command;
import view.command.common.ShowMessageCommand;
import view.command.common.StepFinishedCommand;
import view.command.tree.MoveNodesCommand;
import view.command.tree.RestoreBSTCommand;
import view.command.tree.RotateCommand;
import view.command.tree.RotationFinishedCommand;
import view.command.tree.SaveStateBST;
import view.command.tree.UpdateParentCommand;

/**
 *
 * 
 */
public class BSTNodeRotatedAnimation extends AbstractUndoAnimationSteps {
	private BinarySearchTreeView bstView;
	private int side;

	public BSTNodeRotatedAnimation(BinarySearchTreeView bstView, int side) {
		this.bstView = bstView;
		this.side = side;
	}

	@Override
	protected void initializeListUndoSteps() {
		this.undoSteps = new ArrayList<Command>();
		undoSteps
				.add(new ShowMessageCommand(bstView, "Deshaciendo rotacion..."));
		undoSteps.add(new RestoreBSTCommand(bstView));
		undoSteps.add(new StepFinishedCommand(bstView, false, this));
	}

	@Override
	protected void initializeListSteps() {
		steps = new ArrayList<Command>();

		/*
		 * BSTNodeView node = bstView.getNodesVisited().pollLast(); if
		 * (bstView.modeDelete) steps.add(new ShowMessageCommand(bstView,
		 * "Nodo (" + node.getData() +
		 * ") tiene a todos sus sucesores: necesita rotacion simple")); else if
		 * (bstView.isDobleRotation() == 0) steps.add(new
		 * ShowMessageCommand(bstView, "Nodo (" + node.getData() +
		 * ") desbalanceado: necesita rotacion simple"));
		 */

		// Para q funcione el deshacer (varios seguidos)
		steps.add(new SaveStateBST(bstView, bstView.getNodesVisited()
				.peekLast()));

		// La siguiente linea se mueve RotateCommand para q funcione el deshacer
		// (varios seguidos)
		// BSTNodeView node = bstView.getNodesVisited().pollLast();

		BSTNodeView node = bstView.getNodesVisited().peekLast();
		if (bstView.isModeDelete())
			steps.add(new ShowMessageCommand(bstView, "Nodo (" + node.getData()
					+ ") tiene a todos sus sucesores: necesita rotacion simple"));
		else if (bstView.isDobleRotation() == 0)
			steps.add(new ShowMessageCommand(bstView, "Nodo (" + node.getData()
					+ ") desbalanceado: necesita rotacion simple"));

		/*
		 * BSTNodeView temp; if (side == BSTNodeView.LEFT) temp =
		 * node.rotateLeft(node); else temp = node.rotateRight(node);
		 * 
		 * bstView.getNodesVisited().addLast(temp);
		 */

		/*
		 * if (bstView.isDobleRotation()) { bstView.setDobleRotation(false);
		 * 
		 * bstView.setLastPolled(bstView.getNodesVisited().pollLast());
		 * BSTNodeView parent = bstView.getNodesVisited().peekLast();
		 * 
		 * if (side == BSTNodeView.LEFT) parent.left = bstView.getLastPolled();
		 * else parent.right = bstView.getLastPolled(); }
		 */
		steps.add(new RotateCommand(side, node, bstView));
		steps.add(new UpdateParentCommand(bstView));
		steps.add(new MoveNodesCommand(bstView));
		steps.add(new RotationFinishedCommand(bstView));
		// steps.add( new DobleRotationCommand(bstView, side) );

		// steps.addAll(bstView.getPaintCommands());
		steps.add(new ShowMessageCommand(bstView, "Nodo (" + node.getData()
				+ ") rotado hacia la "
				+ (side == BSTNodeView.LEFT ? "izquierda" : "derecha")));

		steps.add(new StepFinishedCommand(bstView, false, this));
	}

}
