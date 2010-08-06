package view.command.common;

import view.common.AnimatedPanel;
import controller.InteractiveController;

/**
 * Este comando debe ejecutarse cuando se desea mostrar el pseudocodigo de una
 * primitiva.
 * 
 * 
 */
public class ShowPrimitiveCodeCommand implements Command {

	private InteractiveController controller;
	private String primitiveCode;

	/**
	 * @param controller
	 *            el controlador asociado con la vista en la cual se desea
	 *            mostrar el código de la primitiva
	 * @param message
	 *            mensaje a mostrar
	 */
	public ShowPrimitiveCodeCommand(InteractiveController controller,
			String primitiveCode) {
		this.controller = controller;
		this.primitiveCode = primitiveCode;
	}

	public ShowPrimitiveCodeCommand(AnimatedPanel component,
			String primitiveCode) {
		this.controller = component.getController();
		this.primitiveCode = primitiveCode;
	}

	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	public void execute() {
		controller.showPrimitiveCode(primitiveCode);
	}
}
