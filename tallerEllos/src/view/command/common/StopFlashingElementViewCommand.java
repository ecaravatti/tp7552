package view.command.common;

import javax.swing.JComponent;

import view.element.common.ElementView;

/**
 * Este comando debe ejecutarse cuando se desea que un elemento deje
 * de parpadear
 *
 */
public class StopFlashingElementViewCommand implements Command {
  private JComponent component;
  private ElementView element;

  /**
   * @param component componente sobre el que se dibuja el elemento
   * @param element el elemento que debe dejar de parpadear
   */
  public StopFlashingElementViewCommand(JComponent component, ElementView element) {
    this.component = component;
    this.element = element;
  }

  @Override
  public boolean canExecute() {
    return true;
  }

  @Override
  public void execute() {
    element.stopFlashing();
    component.repaint();
  }

}
