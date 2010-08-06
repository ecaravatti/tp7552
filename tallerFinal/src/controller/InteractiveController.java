package controller;

import view.common.AnimatedPanel;
import view.common.InteractivePanel;
import view.common.OperationsLogPane;
import view.common.PseudoCodePane;
import view.exception.common.CannotUndoException;

public abstract class InteractiveController {

    private InteractivePanel interactivePanel;
    private AnimatedPanel animatedPanel;
    private OperationsLogPane operationsLogPane;
    private PseudoCodePane pseudoCodePane;
    private boolean interactive;

    /**
     * Construye un InteractiveController
     * @param animatedPanel el panel
     * @param operationsLog Panel donde deben loggearse los mensajes de las
     *        operaciones
     */
    public InteractiveController(AnimatedPanel animatedPanel) {
        super();
        this.interactivePanel = new InteractivePanel();
        this.interactivePanel.addInteractiveController(this);
        this.animatedPanel = animatedPanel;
        this.interactive = false;
    }
    
    public void setOperationsLogPane(OperationsLogPane operationsLogPane) {
		this.operationsLogPane = operationsLogPane;
	}
    
    public void setPseudoCodePane(PseudoCodePane pseudoCodePane) {
		this.pseudoCodePane = pseudoCodePane;
	}

    /**
     * Agrega el panel interactivo
     * @param panel panel a agregar.
     */
    public void addInteractivePanel(InteractivePanel panel) {
        this.interactivePanel = panel;
    }

    /**
     * Indica si esta activado o no el modo interactivo (paso x paso)
     * @return
     */
    public boolean isInteractive() {
        return interactive;
    }

    /**
     * Cambia el modo interactivo (paso x paso)
     * @param interactive true si debe pasar a modo interactivo, false en caso
     *        contrario.
     */
    public void setInteractive(boolean interactive) {
        this.interactive = interactive;
        if (!interactive) {
            animatedPanel.wait(interactive);
            if (animatedPanel.isPrimitiveFinished()) {
                primitiveFinished();
            }
        }
    }

    /**
     * Invocado cuando debe ejecutarse el proximo paso.
     */
    public void nextStep() {
        animatedPanel.nextStep();
    }

    /**
     * Muestra el pseudo código en el panel de Pseudo-Código.
     * @param pseudoCode 
     * 				El pseudo código a mostrar.
     */
    public void showPrimitiveCode(String pseudoCode) {
        pseudoCodePane.showPseudoCode(pseudoCode);
    }

    /**
     * Invocado cuando debe deshacerse un paso.
     *
     * @throws CannotUndoException Cuando no puede deshacerse una operacion
     */
    public void undoStep() throws CannotUndoException {
        animatedPanel.undoLastStep();
    }

    /**
     * Muestra log mensage referido a las operaciones.
     * @param message mensaje a mostrar.
     */
    public void showLogMessage(String message) {
        operationsLogPane.logMessage(message);
    }

    /**
     * Invocado cuando se varia la velocidad
     * @param speed nueva velocidad
     */
    public void speedChanged(int speed) {
        animatedPanel.changeSpeed(speed);
    }

    /**
     * Este metodo es llamado cuando finaliza la ejecucion de un paso.
     */
    public void stepFinished() {
        interactivePanel.setEnabledButtons(true);
    }

    /**
     * Este metodo es llamado cuando se finaliza la ejecucion de una primitiva.
     */
    // X ejemplo :Activar los botones para insertar, eliminar, etc)...etc
    public abstract void primitiveFinished();

    /**
     * Habilita (o no) la posibilidad de deshacer
     * @param undo true para habilitar, false en caso contrario.
     */
    public void enableUndo(boolean undo) {
        interactivePanel.setEnabledUndoButton(undo);
    }

    public InteractivePanel getInteractivePanel() {
        return interactivePanel;
    }

    public void setWait(boolean wait) {
        if (interactive) {
            return;
        }
        animatedPanel.wait(wait);
    }

    protected void setInteractivePanel(InteractivePanel interactivePanel) {
        this.interactivePanel = interactivePanel;
    }
}
