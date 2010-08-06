package view.element.trie;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * Representa a un puntero a un hermano.
 * 
 * 
 */
public class SiblingPointerView extends PointerView {
  private final static BasicStroke DEF_STROKE = new BasicStroke(1.4f);
  private final static Color DEF_COLOR = Color.BLACK;

  /**
   * Construye un SiblingPointerView
   * @param srcNode nodo fuente
   * @param trgNode nodo destion
   * @param initPointerPosition posicion inicial del puntero
   * @param defLength longitud por defecto
   */
  public SiblingPointerView(TrieNodeView srcNode, AbstractTrieNodeView trgNode,
      Point2D initPointerPosition, int defLength) {
    super(srcNode, trgNode, initPointerPosition, defLength);
  }

  @Override
  public Color getColor() {
    return DEF_COLOR;
  }

  @Override
  public Point2D getFinalPosition() {
    Point2D pos = this.getInitialPosition();
    return new Point2D.Double(pos.getX() + this.getLength(), pos.getY());
  }

  @Override
  public BasicStroke getStroke() {
    return DEF_STROKE;
  }

  @Override
  public Point2D getTargetNodeFinalPosition() {
    return calculatePosition(getFinalLength());
  }

  @Override
  public Point2D getTargetNodePosition() {
    return calculatePosition(getLength());
  }

  @Override
  public void setDistanceTargetNode(double delta) {
    double finalLength = this.getFinalLength();
    double length = this.getLength();

    if (Math.signum(delta) == 1 && (length + delta > finalLength))
      length = finalLength;
    else if (Math.signum(delta) == -1 && (length + delta) < finalLength)
      length = finalLength;
    else
      length += delta;

    setLength(length);

    if (this.hasTargetNode())
      this.getTargetNode().moveTo(getTargetNodePosition());

  }

  /**
   * Calcula la posicion del nodo destino teniendo en cuenta la longitud pasada
   * como parametro
   */
  private Point2D calculatePosition(double size) {
    Point2D pos = getSourceNode().getPosition();
    return new Point2D.Double(getInitialPosition().getX() + size, pos.getY());
  }

  /**
   * Obtiene la longitud final del puntero
   */
  private double getFinalLength() {
    TrieNodeView src = getSourceNode();
    double skip = src.getDepthSibling();
    return ((skip + 1) * this.getDefaultLength() + skip * src.getWidth());
  }

}
