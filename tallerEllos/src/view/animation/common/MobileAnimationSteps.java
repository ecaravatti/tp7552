package view.animation.common;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import view.command.common.Command;
import view.command.common.MoveCommand;
import view.element.common.Mobile;
import view.shape.Segment;

/**
 * Crea los pasos necesarios para mover un objeto, desde una posicion final o
 * una posicion inicial.
 * 
 * 
 */
public class MobileAnimationSteps implements AnimationSteps {
  private Point2D point1;
  private Point2D point2;
  private Mobile mobile;
  private JComponent component;
  private double delta;

  /**
   * @param component el componente al que pertenece el mobile
   * @param mobile el objeto a mover
   * @param initialPos la posicion inicial
   * @param finalPos la posicion final
   * @param delta la variacion del paso
   */
  public MobileAnimationSteps(JComponent component, Mobile mobile, Point2D initialPos,
      Point2D finalPos, double delta) {
    super();
    this.point1 = initialPos;
    this.point2 = finalPos;
    this.mobile = mobile;
    this.component = component;
    this.delta = delta;
  }

  @Override
  public List<Command> getSteps() {
    List<Command> commands = new ArrayList<Command>();
    Segment mov1 = new Segment(this.point1, this.point2, delta);
    ArrayList<Point2D> points = mov1.getPoints();

    for (Point2D p : points) {
      commands.add(new MoveCommand(component, mobile, p));
    }

    return commands;
  }

}
