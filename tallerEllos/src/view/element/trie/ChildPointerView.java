package view.element.trie;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * Esta clase representa a un puntero a un hijo.
 * 
 * @author Agustina
 */
public class ChildPointerView extends PointerView {
  private final static BasicStroke DEF_STROKE = new BasicStroke(1.4f);
  private final static Color DEF_COLOR = Color.BLACK;
  private final static float DEF_DASH[] = { 1f };
  private final static BasicStroke DEF_DATA_STROKE = new BasicStroke(1.4f,
      BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, DEF_DASH, 0.0f);
  private final static Color DEF_DATA_COLOR = Color.BLUE.brighter();

  /**
   * Construye un ChildPointerView
   * @param srcNode nodo fuente
   * @param trgNode nodo destino
   * @param initPosPointer posicion inicial del puntero
   * @param defLength longitud por defecto.
   */
  public ChildPointerView(TrieNodeView srcNode, AbstractTrieNodeView trgNode,
      Point2D initPosPointer, int defLength) {
    super(srcNode, trgNode, initPosPointer, defLength);
  }

  @Override
  public Color getColor() {
    if (!getSourceNode().hasDataNode()
        || !getSourceNode().getDataNode().isVisible())
      return DEF_COLOR;
    return DEF_DATA_COLOR;
  }

  @Override
  public Point2D getFinalPosition() {
    Point2D point = this.getInitialPosition();
    return new Point2D.Double(point.getX(), point.getY() + this.getLength());
  }

  @Override
  public BasicStroke getStroke() {
    if (!getSourceNode().hasDataNode()
        || !getSourceNode().getDataNode().isVisible())
      return DEF_STROKE;
    return DEF_DATA_STROKE;
  }

  @Override
  public Point2D getTargetNodeFinalPosition() {
    double finalLength = this.getFinalLength();
    return getTargetNodePosition(finalLength);
  }

  @Override
  public Point2D getTargetNodePosition() {
    return getTargetNodePosition(this.getLength());
  }

  @Override
  public void setDistanceTargetNode(double delta) {
    double finalLength = this.getFinalLength();
    double length = this.getLength();

    if (Math.signum(delta) == 1 && (length + delta > getFinalLength()))
      length = finalLength;
    else if (Math.signum(delta) == -1 && (length + delta) < getFinalLength())
      length = finalLength;
    else
      length += delta;

    this.setLength(length);

    if (this.hasTargetNode())
      this.getTargetNode().moveTo(this.getTargetNodePosition(length));

  }

  /**
   * Calcula la posicion final del puntero.
   */
  private Point2D getTargetNodePosition(double distance) {
    TrieNodeView source = getSourceNode();
    Point2D pos = source.getPosition();
    return new Point2D.Double(pos.getX(), this.getInitialPosition().getY()
        + distance);
  }

  /**
   * Obtiene la longitud final del puntero.
   */
  private double getFinalLength() {
    return this.getDefaultLength();
  }

}
