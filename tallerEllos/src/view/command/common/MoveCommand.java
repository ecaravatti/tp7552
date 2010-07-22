package view.command.common;

import java.awt.geom.Point2D;

import javax.swing.JComponent;

import view.element.common.Mobile;

/**
 * Este comando debe ejecutarse para mover un elemento.
 * 
 */
public class MoveCommand implements Command {
  private Point2D point;
  private Mobile mobile;
  private JComponent component;

  /**
   * @param component componente al que pertenece el elemento que debe moverse
   * @param mobile el elemento a mover.
   * @param finalPos la posicion final del elemento a mover.
   */
  public MoveCommand(JComponent component, Mobile mobile, Point2D finalPos) {
    super();
    this.point = finalPos;
    this.mobile = mobile;
    this.component = component;
  }

  @Override
  public boolean canExecute() {
    return true;
  }

  @Override
  public void execute() {
    mobile.moveTo(point);
    component.repaint();
  }
}
