package view.command.common;

import view.common.AnimatedPanel;
import controller.InteractiveController;

/**
 * Este comando debe ejecutarse cuando se desea mostrar un mensaje
 * 
 * 
 */
public class ShowMessageCommand implements Command {

    private InteractiveController controller;
    private String message;

    /**
     * @param controller el controlador asociado con la vista en la cual se desea
     *        mostrar el mensaje
     * @param message mensaje a mostrar
     */
    public ShowMessageCommand(InteractiveController controller, String message) {
        this.controller = controller;
        this.message = message;
    }

    public ShowMessageCommand(AnimatedPanel component, String message) {
        this.controller = component.getController();
        this.message = message;
    }


    @Override
    public boolean canExecute() {
        return true;
    }

    @Override
    public void execute() {
        controller.showLogMessage(message);
    }
}
