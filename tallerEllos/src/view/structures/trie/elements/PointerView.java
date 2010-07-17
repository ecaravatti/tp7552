package view.structures.trie.elements;

import view.commons.elements.AbstractElementView;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import view.commons.shapes.Arrow;
import view.commons.elements.Selectable;

/**
 * Esta define como se representa visualmente un puntero.
 * 
 * @author Agustina
 */
public abstract class PointerView extends AbstractElementView implements Selectable {
  private final static Color DEF_COLOR = Color.YELLOW.brighter();
    
  private TrieNodeView srcNode;
  private AbstractTrieNodeView trgNode;
  private Point2D initPointerPos;
  private int defLength;
  private double length;
  private boolean paintDefArrow;

  /**
   * Crea un PointerView
   * @param srcNode nodo fuente
   * @param trgNode nodo destino
   * @param initPointerPosition posicion inicial del puntero
   * @param defLength longitud por defecto del puntero
   */
  public PointerView(TrieNodeView srcNode, AbstractTrieNodeView trgNode,
      Point2D initPointerPosition, int defLength) {
    super(initPointerPosition, true);
    this.srcNode = srcNode;
    this.trgNode = trgNode;
    this.setSelectionColor(DEF_COLOR);
    this.initPointerPos = initPointerPosition;
    this.defLength = defLength;
    this.length = 0;
    this.paintDefArrow = true;
  }

  /**
   * Obtiene el nodo fuente
   * @return el nodo fuente
   */
  public TrieNodeView getSourceNode() {
    return srcNode;
  }

  /**
   * Cambia el nodo fuente
   * @param srcNode nodo fuente
   */
  public void setSourceNode(TrieNodeView srcNode) {
    this.srcNode = srcNode;
  }

  /**
   * Obtiene el nodo destino
   * @return el nodo destino
   */
  public AbstractTrieNodeView getTargetNode() {
    return trgNode;
  }

  /**
   * Cambia el nodo destino
   * @param trgNode nuevo nodo destino
   */
  public void setTargetNode(AbstractTrieNodeView trgNode) {
    this.trgNode = trgNode;
  }

  /**
   * Obtiene la posicion del nodo destino
   * @return posicion del nodo destino
   */
  abstract public Point2D getTargetNodePosition();

  /**
   * Obtiene la posicion final del nodo destino
   * @return posicion final del nodo destino
   */
  abstract public Point2D getTargetNodeFinalPosition();

  /**
   * Cambia la distancia del nodo destino. Teniendo en cuenta las restricciones
   * impuestas por la posicion final del nodo.
   * @param delta variacion de la distancia
   */
  abstract public void setDistanceTargetNode(double delta);

  /**
   * Cambia la distancia del nodo destino a cero.
   */
  public void changeDistanceTargetNode() {
    this.length = 0;
  }

  /**
   * Obtiene el trazo utilizado para dibujar el puntero
   * @return el stroke
   */
  abstract public BasicStroke getStroke();

  /**
   * Obtiene el color utilizado para dibujar el puntero
   * @return el color
   */
  abstract public Color getColor();

  /**
   * Obtiene la posicion inicial del puntero
   * @return la posicion inicial del puntero
   */
  public Point2D getInitialPosition() {
    return this.initPointerPos;
  }

  /**
   * Cambia la posicion inicial del puntero
   * @param position la nueva posicion
   */
  public void setInitialPosition(Point2D position) {
    this.initPointerPos = position;
  }

  /**
   * @return la longitud por defecto del puntero.
   */
  public int getDefaultLength() {
    return this.defLength;
  }

  /**
   * @param length la nueva longitud por defecto
   */
  public void setDefaultLength(int length) {
    this.defLength = length;
  }

  /**
   * Obtiene la posicion final del puntero
   * @return la posicion final del puntero
   */
  abstract public Point2D getFinalPosition();

  @Override
  public void changeColor() {
    this.paintDefArrow = !paintDefArrow;
  }

  @Override
  public void restoreColor() {
    this.paintDefArrow = true;
  }

  @Override
  public void setSelectionColor(Color color) {
    this.setFlashingColor(color);
  }

 
  @Override
  public void setFlashing(int delay) {
    srcNode.stopFlashing();
    if (hasTargetNode())
      trgNode.stopFlashing();
    super.setFlashing(delay);
  }

  /**
   * Obtiene la longitud del puntero
   * @return la longitud del puntero
   */
  protected double getLength() {
    return length;
  }

  /**
   * Cambia la longitud del puntero
   * @param length nueva longitud del puntero
   */
  protected void setLength(double length) {
    this.length = length;
  }

  @Override
  protected Selectable getSelectable() {
    return this;
  }

  @Override
  protected void paintElement(Graphics g) {
    if (hasTargetNode() && trgNode.isVisible())
      this.paintArrow(g);
  }

  protected void paintDefArrow(Graphics g) {
    Arrow arrow = new Arrow(this.getInitialPosition(), this.getFinalPosition(),
        true, this.getStroke(), this.getColor());
    arrow.paint(g);
  }

  protected void paintAltArrow(Graphics g) {
    Arrow arrow = new Arrow(this.getInitialPosition(), this.getFinalPosition(),
        true, new BasicStroke(3.0f), getFlashingColor());
    arrow.paint(g);
  }

  protected void paintArrow(Graphics g) {
    if (paintDefArrow)
      this.paintDefArrow(g);
    else
      this.paintAltArrow(g);
  }

  protected boolean hasTargetNode() {
    return (this.trgNode != null);
  }
}
