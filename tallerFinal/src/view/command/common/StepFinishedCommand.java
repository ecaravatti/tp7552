package view.command.common;

import view.animation.common.AbstractUndoAnimationSteps;
import view.common.AnimatedPanel;
import controller.InteractiveController;

public class StepFinishedCommand implements Command {

	private AnimatedPanel component;
	private boolean finishPrimitive;
	private AbstractUndoAnimationSteps animation;

	public StepFinishedCommand(AnimatedPanel component, boolean finishPrimitive) {
		this.component = component;
		this.finishPrimitive = finishPrimitive;
		this.animation = null;
	}

	// Este constructor es para deshacer
	public StepFinishedCommand(AnimatedPanel component,
			boolean finishPrimitive, AbstractUndoAnimationSteps animation) {
		this.component = component;
		this.finishPrimitive = finishPrimitive;
		this.animation = animation;
	}

	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	public void execute() {
		InteractiveController controller = component.getController();

		if (!component.isExecutingUndo()) {
			component.nextAnimation();
			if (animation != null)
				component.addExecutedAnimationSteps(animation);
		}

		if (controller.isInteractive() && this.pause() && !finishPrimitive) {
			component.wait(true);
			if (!finishPrimitive)
				controller.stepFinished();
			controller.showLogMessage("Presione siguiente para continuar...");
		}

		if (component.isExecutingUndo() && pause())
			component.setExecutingUndo(false);

		if (finishPrimitive) {
			controller.primitiveFinished();
		}

		component.repaint();

		/*
		 * component.nextAnimation(); if (finishPrimitive) {
		 * component.getController().primitiveFinished(); }
		 * 
		 * component.repaint();
		 */
	}

	private boolean pause() {

		if (animation == null)
			return true;

		if (component.isExecutingUndo() && animation.getUndoPause())
			return true;

		if (!component.isExecutingUndo() && animation.getRedoPause())
			return true;

		return false;
	}
}
