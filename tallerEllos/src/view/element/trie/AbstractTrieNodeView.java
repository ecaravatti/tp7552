package view.element.trie;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;

import view.element.common.AbstractElementView;
import view.element.common.Selectable;
import view.shape.NodeShape;

public abstract class AbstractTrieNodeView extends AbstractElementView {

  public enum Colors {
    NODE_ADDED(new Color(238, 232, 170)),
    NODE_FOUND(Color.green.darker()),
    NODE_CANNOT_REMOVE(new Color(255, 69, 0)),
    SELECT_NODE_FOUND(Color.GREEN.brighter()),
    SELECT_NODE_NOT_FOUND(Color.RED.brighter());
    
    private Color color;

    Colors(Color color) {
      this.color = color;
    }

    public Color getColor() {
      return this.color;
    }
  };

  protected final static BasicStroke DEF_STROKE = new BasicStroke(1.5f);
  private final static int DEF_WIDTH = 30;
  private final static int DEF_HEIGHT = 30;
  private final static Color DEF_TRANSP = new Color(255, 255, 255, 50);
  
  protected NodeShape shape;
  protected TrieViewPrimitives trieView;
  private double depthSib; /** Profundidad de los hermanos (en nodos) */
  private String data;
  private PointerView ptrParent;
  private boolean invisible;
  private boolean toEliminate;
  private boolean preTransparent;
  private boolean transparent;
  private Color transColor;
  


  /**
   * Indica la distancia por defecto de los nodos hijos.
   */
  protected final static int DEF_DIST = 5;

  /**
   * @param data dato que contiene el nodo
   * @param position posicion del nodo
   * @param ptrParent puntero padre
   * @param trieView 
   */
  public AbstractTrieNodeView(String data, Point2D position, PointerView ptrParent,
          TrieViewPrimitives trieView) {
    super(position, DEF_WIDTH, DEF_HEIGHT, false);
    this.depthSib = 0;
    this.ptrParent = ptrParent;
    this.invisible = true;
    this.toEliminate = false;
    this.transparent = false;
    this.preTransparent = false;
    this.transColor = DEF_TRANSP;
    this.data = data;
    this.trieView = trieView;
    this.shape = createShape();
  }

  /**
   * Obtiene la profundidad de los hermanos
   * @return la profundidad de los hermanos
   */
  public double getDepthSibling() {
    return depthSib;
  }

  /**
   * Cambia la profundida de los hermanos
   * @param depthSib nueva profunidad de los hermanos
   */
  public void setDepthSibling(double depthSib) {
    this.depthSib = depthSib;
  }

  /**
   * Determina si el nodo tiene padre
   * @return true si tiene padre false, en caso contrario
   */
  public boolean hasParent() {
    return this.getParent() != null;
  }

  /**
   * Obtiene el padre del nodo
   * @return el padre del nodo
   */
  public PointerView getParent() {
    return ptrParent;
  }

  /**
   * Cambia el padre del nodo
   * @param ptrParent el nuevo padre
   */
  public void setParent(PointerView ptrParent) {
    this.ptrParent = ptrParent;
  }

  /**
   * Cambia la distancia por defecto al padre
   */
  public void setDefaultDistanceParent() {
    if (hasParent()) {
      this.ptrParent.changeDistanceTargetNode();
      this.ptrParent.setDistanceTargetNode(DEF_DIST);
    }
  }

  /**
   * Cambia la visibilidad del puntero padre
   * @param visible true si debe ser visible, false en caso contrario
   */
  public void setVisibleParentPointer(boolean visible) {
    this.ptrParent.setVisible(visible);
  }

  /**
   * Obtiene la posicion final del nodo
   * @return la posicion final del nodo.
   */
  public Point2D getFinalPosition() {
    if (hasParent())
      return this.ptrParent.getTargetNodeFinalPosition();

    return null;
  }

  /**
   * Indica si el nodo esta marcado para eliminar
   * @return true si esta marcado para eliminar, false en caso contrario
   */
  public boolean isToEliminate() {
    return toEliminate;
  }

  /**
   * Indica si el nodo es invisible (o no). Un nodo invisible no influye en la
   * profundidad de los hijos o hermanos del padre.
   * @return true si es invisible (false en caso contrario)
   */
  public boolean isInvisible() {
    return invisible;
  }

  /**
   * Cambia el valor de invisibilidad del nodo
   * @param invisible true si debe ser invisible, false en caso contrario
   */
  public void setInvisible(boolean invisible) {
    this.invisible = invisible;
    trieView.calculateDepths();
  }

  /**
   * Indica si el nodo es transparente
   * @return true si es transparente, false en caso contrario
   */
  public boolean isTransparent() {
    return transparent;
  }

  /**
   * Cambia la transparencia
   * @param transparent true si debe ser transparente, false en caso contrario.
   */
  public void setTransparent(boolean transparent) {

    if (transparent)
      this.transparent = transparent;
    else {
      preTransparent = true;
    }
  }

  /**
   * Obtiene el dato
   * @return el dato
   */
  public String getData() {
    return data;
  }

  @Override
  public void setFlashing(int delay) {
    if (hasParent())
      getParent().stopFlashing();

    super.setFlashing(delay);
  }

  @Override
  public void setWidth(int width) {
    super.setWidth( width );
    this.shape = createShape();
  }
  
  @Override
  public void setHeight(int height) {
    super.setHeight( height );
    this.shape = createShape();
  }
  
  @Override
  public Selectable getSelectable() {
    return shape;
  }
  
  
  /**
   * Cambia el color del nodo
   * @param color nuevo color del nodo
   */
  public void setColor(Color color){
    this.shape.setNodeColor( color );
  }

  /**
   * Restaura los colores a los establecidos por defecto
   */
  public void changeNodeColor(){
    shape.changeNodeColor();
  }

  /**
   * Obtiene el color por defecto
   * @return el color por defecto
   */
  public Color getDefaultColor(){
    return shape.getDefaultNodeColor();
  }

  /**
   * Pinta el nodo
   * @param g contexto sobre el que se dibuja
   */
  protected abstract void paintTrieNode(Graphics g);

  /**
   * Crea la forma del nodo
   * @return la forma del nodo
   */
  protected abstract NodeShape createShape();
  
  /**
   * Determina si el nodo debe ser marcado para eliminar (o no)
   * @param toEliminate true si debe ser marcado para eliminar, false en caso
   *        contrario.
   */
  protected void setToEliminate(boolean toEliminate) {
    this.toEliminate = toEliminate;
  }

  @Override
  protected void paintElement(Graphics g) {
    this.paintTrieNode(g);
  }

  /**
   * Obtiene el color transparente
   * @return el color transparente
   */
  protected Color getTransparentColor() {
    Color color = this.transColor;

    if (!preTransparent) {
      int alpha = color.getAlpha() + 20;
      if (alpha > 200)
        alpha = 200;
      this.transColor = new Color(color.getRed(), color.getGreen(), color
          .getBlue(), alpha);
      return color;
    } else {
      int alpha = color.getAlpha() - 20;
      if (alpha < DEF_TRANSP.getAlpha()) {
        this.preTransparent = false;
        this.transparent = false;
        alpha = DEF_TRANSP.getAlpha();
      }
      this.transColor = new Color(color.getRed(), color.getGreen(), color
          .getBlue(), alpha);
      return color;
    }
  }
  
}
