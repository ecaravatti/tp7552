package view.shape;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Define la forma de un nodo de un Trie.
 * 
 * 
 */
public class TrieNodeShape extends NodeShape {
//  private final static Color DEF_NODE_COLOR = Color.gray.brighter();
  
  private Rectangle2D rectDataPtr;
  private Rectangle2D rectChildPtr;
  private Rectangle2D rectSiblPtr;
  private boolean visibleData;

  /**
   * Construye un TrieNodeShape
   * @param data dato que contiene.
   * @param pos la posicion.
   * @param width ancho del nodo.
   * @param height altura del nodo.
   * @param font fuente utilizada para dibujar el dato.
   * @param stroke trazo utilizado para dibujar el nodo.
   */
  public TrieNodeShape(String data, Point2D pos, int width, int height, Font font, Stroke stroke) {
    super(data, pos, width, height, font, stroke, false);
    this.visibleData = false;
//    this.setNodeColor(DEF_NODE_COLOR);
//    this.setDefaultNodeColor(DEF_NODE_COLOR);
    this.createDefRectChildPtr();
    this.createDefRectSiblPtr();
  }

  /**
   * Obtiene la posicion del puntero hermano.
   * @return un punto que indica la posicion de inicio del puntero al nodo
   *         hermano
   */
  public Point2D getPositionSiblingPtr() {
    return new Point2D.Double(rectSiblPtr.getCenterX(), rectSiblPtr.getCenterY());
  }

  /**
   * Obtiene la posicion del puntero hijo.
   * @return un punto que indica la posicion de inicio del puntero al nodo hijo
   */
  public Point2D getPositionChildPtr() {
    return new Point2D.Double(rectChildPtr.getCenterX(), rectChildPtr.getCenterY());
  }

  /**
   * Obtiene la posicion del puntero al dato.
   * @return un punto que indica la posicion de inicio del puntero al nodo dato
   */
  public Point2D getPositionDataPtr() {
    if (visibleData) {
      return new Point2D.Double(rectDataPtr.getCenterX(), rectDataPtr.getCenterY());
    }
    return null;
  }

  /**
   * Determina si el area del puntero a los datos es visible.
   * @return true si el area rectangular que contiene el puntero que apunta a
   *         los datos es visible, falso en caso contrario
   */
  public boolean isPtrDataVisible() {
    return visibleData;
  }

  @Override
  public void moveTo(Point2D point) {
    super.moveTo(point);

    if (visibleData) {
      this.createRectSiblPtr();
      this.createRectChildPtr();
      this.createRectDataPtr();
    } else {
      this.createDefRectSiblPtr();
      this.createDefRectChildPtr();
    }
  }

  /**
   * Indica si el area que contiene el puntero a los datos debe ser visible
   * @param visible true si debe ser visible, false en caso contrario.
   */
  public void setVisiblePtrData(boolean visible) {
    if (visible != this.visibleData) {
      if (visible) {
    	this.createRectSiblPtr();
        this.createRectChildPtr();
        this.createRectDataPtr();
      } else {
    	this.createDefRectSiblPtr();
        this.createDefRectChildPtr();
      }
    }
    this.visibleData = visible;
  }

  /**
   * Indica si el sector del puntero a los datos es visible (o no)
   * @return true si es visible, false en caso contrario.
   */
  public boolean isVisiblePtrData() {
    return visibleData;
  }

  @Override
  protected void paintNode(Graphics graphics) {
    Graphics2D g2 = (Graphics2D) graphics;

    super.paintNode(graphics);
    paintRectPointers(g2);
  }

  /**
   * Pinta los rectangulos que contienen a los punteros.
   * @param graphics contexto sobre el que se dibuja.
   */
  protected void paintRectPointers(Graphics graphics) {
    Graphics2D g2 = (Graphics2D) graphics;
    Paint paint = new Color(0, 100, 50);
//    GradientPaint grad1 = new GradientPaint((float) rectChildPtr.getCenterX(),
//        (float) rectChildPtr.getCenterY(), Color.black, (float) rectChildPtr
//            .getMaxX(), (float) rectChildPtr.getCenterY(), Color.WHITE
//            .brighter(), true);
    paintRectGrad(g2, paint, rectChildPtr);
//    GradientPaint grad2 = new GradientPaint((float) rectSiblPtr.getCenterX(),
//        (float) rectSiblPtr.getCenterY(), Color.black, (float) rectSiblPtr
//            .getCenterX(), (float) rectSiblPtr.getMaxY(), Color.WHITE
//            .brighter(), true);
    paintRectGrad(g2, paint, rectSiblPtr);

    if (visibleData) {
      paintRectGrad(g2, paint, rectDataPtr);
    }

  }

  /**
   * Crea el rectangulo que contiene espacio para los punteros hijos, cuando
   * todavia no se ha creado el espacio para el puntero a los datos.
   */
  protected void createDefRectChildPtr() {
    Rectangle2D bounds = getRectNode();
    this.rectChildPtr = new Rectangle2D.Double(bounds.getX(), bounds.getMaxY()
        - bounds.getHeight() / 5, bounds.getWidth()  - bounds.getWidth() / 5, bounds.getHeight() / 5);
  }

  /**
   * Crea el rectangulo que contiene espacio para los punteros hijos.
   */
  protected void createRectChildPtr() {
    Rectangle2D bounds = getRectNode();
    this.rectChildPtr = new Rectangle2D.Double(bounds.getX() - 1
        + bounds.getWidth() / 4, bounds.getMaxY() - bounds.getHeight() / 5,
        (bounds.getWidth() / 2) + 3, bounds.getHeight() / 5);
  }

  /**
   * Crea el rectangulo que contiene el espacio para el puntero a los datos
   */
  protected void createRectDataPtr() {
	  Rectangle2D bounds = getRectNode();
	  this.rectDataPtr = new Rectangle2D.Double(bounds.getX(), bounds.getY(), bounds.getWidth() / 5,
	        bounds.getHeight());
  }

  /**
   * Crea el rectangulo que contiene espacio para el puntero al hermano, cuando
   * todavia no se ha creado el espacio para el puntero a los datos.
   */
  protected void createDefRectSiblPtr() {
    Rectangle2D bounds = getRectNode();
    this.rectSiblPtr = new Rectangle2D.Double(bounds.getMaxX()
        - bounds.getWidth() / 5, bounds.getY(), bounds.getWidth() / 5,
        bounds.getHeight());
  }
  
  /**
   * Crea el rectangulo que contiene espacio para el puntero al hermano, cuando
   * ya existe el puntero a los datos.
   */
  protected void createRectSiblPtr() {
    Rectangle2D bounds = getRectNode();
    this.rectSiblPtr = new Rectangle2D.Double(bounds.getMaxX()
        - bounds.getWidth() / 5, bounds.getY(), bounds.getWidth() / 5,
        bounds.getHeight());
  }

  @Override
  protected Rectangle2D getTextRect() {
    Rectangle2D bounds = getBounds();
    return new Rectangle2D.Double(bounds.getX(), bounds.getY(), bounds
        .getWidth()
        - rectSiblPtr.getWidth(), bounds.getHeight() - rectChildPtr.getHeight());
  }

}
