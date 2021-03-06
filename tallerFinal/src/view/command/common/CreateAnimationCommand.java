package view.command.common;

import view.animation.common.AnimationSteps;
import view.common.AnimatedPanel;

/**
 * Este comando debe ejecutarse cuando se deba crear cada uno de los pasos de la
 * animacion.
 * 
 */
public class CreateAnimationCommand implements Command {

	private AnimatedPanel view;
	private Command command;
	private AnimationSteps animation;
	private int number;

	/**
	 * Crea un Comando en base a una Animacion
	 * 
	 * @param view
	 *            una vista
	 * @param animation
	 *            animacion a crear
	 * @param number
	 *            numero de animacion
	 */
	public CreateAnimationCommand(AnimatedPanel view, AnimationSteps animation,
			int number) {
		this.view = view;
		this.number = number;
		this.animation = animation;
		this.command = null;
	}

	/**
	 * Crea un Comando en base a un Comando
	 * 
	 * @param view
	 *            una vista
	 * @param animation
	 *            animacion a crear
	 * @param number
	 *            numero de animacion
	 */
	public CreateAnimationCommand(AnimatedPanel view, Command command,
			int number) {
		this.view = view;
		this.number = number;
		this.command = command;
		this.animation = null;
	}

	@Override
	public boolean canExecute() {
		return view.canExecuteAnimation(number);
	}

	@Override
	public void execute() {
		if (command != null) {
			this.view.addCommand(this.command);
			this.view.nextAnimation();
		} else
			// if (animation != null)
			this.view.addCommands(this.animation.getSteps());
	}
}
