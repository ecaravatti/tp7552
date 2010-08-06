package view.command.common;

import javax.swing.JOptionPane;

/**
 * Este comando debe ejecutarse cuando se desea mostrar un mensaje de Dialogo
 * 
 */
public class ShowMessageDialogCommand implements Command {
  private String message;
  private String title;
  private int messageType;

  /**
   * @param message mensaje a mostrar
   * @param title titulo del dialogo
   * @param messageType tipo de mensaje
   */
  public ShowMessageDialogCommand(String message, String title, int messageType) {
    super();
    this.message = message;
    this.title = title;
    this.messageType = messageType;
  }

  @Override
  public boolean canExecute() {
    return true;
  }

  @Override
  public void execute() {
    JOptionPane.showMessageDialog(null, message, title, messageType);
  }
}
