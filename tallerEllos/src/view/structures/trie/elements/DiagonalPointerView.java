package view.structures.trie.elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * Representa a un puntero diagonal.
 * 
 * @author Agustina
 */
public class DiagonalPointerView extends PointerView {
  private final static BasicStroke DEF_STROKE = new BasicStroke(1.4f);
  private final static Color DEF_COLOR = Color.BLACK;

  private double angle;

  /**
   * Construye un SiblingPointerView
   * @param srcNode nodo fuente
   * @param trgNode nodo destion
   * @param initPointerPosition posicion inicial del puntero
   * @param defLength longitud por defecto
   */
  public DiagonalPointerView(TrieNodeView srcNode,
      AbstractTrieNodeView trgNode, Point2D initPointerPosition, int defLength) {
    super(srcNode, trgNode, initPointerPosition, defLength);
    this.calculateAngle();
  }

  @Override
  public Color getColor() {
    return DEF_COLOR;
  }

  @Override
  public Point2D getFinalPosition() {
    Point2D point = this.getInitialPosition();
    double length = this.getLength();
    return new Point2D.Double(point.getX() + length * Math.sin(angle), point
        .getY()
        + length * Math.cos(angle));
  }

  @Override
  public BasicStroke getStroke() {
    return DEF_STROKE;
  }

  @Override
  public Point2D getTargetNodeFinalPosition() {
    return getSourceNode().getFirstChildPosition();
  }

  @Override
  public Point2D getTargetNodePosition() {
    Point2D srcPos = this.getInitialPosition();
    double length = getLength();
    return new Point2D.Double(srcPos.getX() + length * Math.sin(angle), srcPos
        .getY()
        + length * Math.cos(angle));
  }

  @Override
  public void setDistanceTargetNode(double delta) {
    Point2D point = getTargetNodePosition();

    double x = point.getX() + delta * Math.sin(angle);
    double y = point.getY() + delta * Math.cos(angle);
    double length = this.getLength() + delta;
    Point2D finalPoint = getTargetNodeFinalPosition();
    boolean lengthChanged = false;

    if (Math.signum(delta) == 1 && x > finalPoint.getX()) {
      x = finalPoint.getX();
      lengthChanged = true;
    } else if (Math.signum(delta) == -1 && x < finalPoint.getX()) {
      x = finalPoint.getX();
      lengthChanged = true;
    }

    if (Math.signum(delta) == 1 && y > finalPoint.getY()) {
      y = finalPoint.getY();
      lengthChanged = true;
    } else if (Math.signum(delta) == -1 && y < finalPoint.getY()) {
      y = finalPoint.getY();
      lengthChanged = true;
    }

    this.setLength(length);

    Point2D destPoint = new Point2D.Double(x, y);
    if (lengthChanged) {
      this.setLength(this.getInitialPosition().distance(destPoint));
    }

    if (this.hasTargetNode())
      this.getTargetNode().moveTo(destPoint);
  }

  /**
   * Calcula el angulo del puntero.
   */
  private void calculateAngle() {
    Point2D srcPos = this.getInitialPosition();
    Point2D pos = getSourceNode().getFirstChildPosition();
    this.angle = Math.atan((pos.getX() - srcPos.getX())
        / (pos.getY() - srcPos.getY()));
  }

}
