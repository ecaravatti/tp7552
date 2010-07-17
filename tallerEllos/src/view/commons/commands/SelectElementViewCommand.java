package view.commons.commands;

import view.commons.commands.Command;
import java.awt.Color;
import javax.swing.JComponent;
import view.commons.elements.ElementView;

/**
 * Este comando debe ejecutarse cuando se desea seleccionars, un elemento del
 * vista.
 * 
 * @author Agustina
 */
public class SelectElementViewCommand implements Command {
  private JComponent component;
  private int delay;
  private ElementView element;
  private Color color;

  /**
   * @param component componente al que pertenece el nodo seleccionado.
   * @param delay retardo en el parpadeo
   * @param element elemento seleccionado
   * @param selectionColor color de seleccion del nodo seleccionado
   */
  public SelectElementViewCommand(JComponent component, int delay, ElementView element,
      Color selectionColor) {
    this.delay = delay;
    this.component = component;
    this.element = element;
    this.color = selectionColor;
  }

  @Override
  public boolean canExecute() {
    return true;
  }

  @Override
  public void execute() {
    element.setFlashingColor(color);
    element.setFlashing(delay);
    component.repaint();
  }

}
