package controller;

import javax.swing.JTextArea;

import view.common.InteractivePanel;
import view.common.AnimatedPanel;
import view.exception.common.CannotUndoException;

public abstract class InteractiveController {

    private InteractivePanel interactivePanel;
    private AnimatedPanel animatedPanel;
    private JTextArea operationsLog;
    private JTextArea primitivesCodeArea;
    private boolean interactive;

    /**
     * Construye un InteractiveController
     * @param animatedPanel el panel
     * @param operationsLog area de texto donde debe mostrarse los mensajes de las
     *        operaciones
     */
    public InteractiveController(AnimatedPanel animatedPanel, JTextArea operationsLog) {
        super();
        this.interactivePanel = new InteractivePanel();
        this.interactivePanel.addInteractiveController(this);
        this.animatedPanel = animatedPanel;
        this.operationsLog = operationsLog;
        this.interactive = false;
    }

    public void setPrimitivesCodeArea(JTextArea primitivesCodeArea) {
        this.primitivesCodeArea = primitivesCodeArea;
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

    public void showPrimitiveCode(String primitiveCode) {
        primitivesCodeArea.setText(primitiveCode);
        primitivesCodeArea.setCaretPosition(0);
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
        operationsLog.append("\n" + message);
        operationsLog.setCaretPosition(operationsLog.getText().length());
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
