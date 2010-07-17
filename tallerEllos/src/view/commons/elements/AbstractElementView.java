package view.commons.elements;

import view.commons.elements.ElementView;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Point2D;

import view.commons.elements.Selectable;

/**
 * Esta clase representa a un elemento de la vista
 * 
 * @author Agustina
 */
public abstract class AbstractElementView implements ElementView, Cloneable {
  private Point2D position;
  private boolean visible;
  private int delay;
  private int count;
  private Color flashingColor;
  private boolean flashing;
  private int width;
  private int height;

  /**
   * Construye un AbstractElementView.
   * @param position posicion del elemento
   * @param visible true si debe ser visible, false en caso contrario.
   */
  public AbstractElementView(Point2D position, boolean visible) {
    super();
    this.position = position;
    this.visible = visible;
    this.delay = 0;
    this.count = 0;
    this.flashingColor = Color.WHITE;
    this.width = 0;
    this.height = 0;
  }

  /**
   * Construye un AbstractElementView.
   * @param position posicion del elemento
   * @param width ancho del elemento
   * @param height alto del elemento
   * @param visible true si debe ser visible, false en caso contrario
   */
  public AbstractElementView(Point2D position, int width, int height,
      boolean visible) {
    super();
    this.position = position;
    this.visible = visible;
    this.delay = 0;
    this.count = 0;
    this.width = width;
    this.height = height;
  }

  /*
   * (non-Javadoc)
   * @see view.trie.elements.ElementView#getSelectable()
   */
  protected abstract Selectable getSelectable();

  @Override
  public void paint(Graphics graphics) {

    if (visible) {

      if (this.flashing)
        this.changeSelectableColor();

      this.paintElement(graphics);
    }
  }

  @Override
  public Color getFlashingColor() {
    return this.flashingColor;
  }

  @Override
  public boolean isVisible() {
    return this.visible;
  }

  @Override
  public void setFlashing(int delay) {
    this.flashing = true;
    this.delay = delay;
    this.count = 0;
    if (getSelectable() != null)
      getSelectable().setSelectionColor(this.flashingColor);
  }

  @Override
  public void setFlashingColor(Color color) {
    this.flashingColor = color;
  }

  @Override
  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  @Override
  public void stopFlashing() {
    this.flashing = false;
    if (getSelectable() != null)
      getSelectable().restoreColor();
  }

  @Override
  public Point2D getPosition() {
    return this.position;
  }

  @Override
  public void moveTo(Point2D position) {
    this.position = position;
  }

  @Override
  public Dimension getDimension() {
    return new Dimension(this.width, this.height);
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public void setWidth(int width) {
    this.width = width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public void setHeight(int height) {
    this.height = height;
  }

  @Override
  public Object clone() {
    try {
      return super.clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Determina si hay algun elemento parpadeando
   * @return true si parpadea, false en caso contrario.
   */
  public boolean isFlashing() {
    return this.flashing;
  }

  /**
   * Dibuja el elemento en el contexto grafico
   * @param g contexto sobre el que se dibuja
   */
  abstract protected void paintElement(Graphics g);

  /**
   * Cambia el color del elemento "selectable"
   */
  protected void changeSelectableColor() {
    this.count++;
    if (this.count < this.delay)
      return;
    if (this.getSelectable() != null)
      this.getSelectable().changeColor();
    this.count = 0;
  }

  /**
   * Obtiene el retardo en el parpadeo
   * @return el retardo en el parpadeo
   */
  protected int getDelay() {
    return delay;
  }

  /**
   * Obtiene la cantidad de parpadeos acumulados.
   * @return la cantidad de parpadeos acumulados.
   */
  protected int getCount() {
    return count;
  }
  
    public void changeSelectionColor(Color color) {
        getSelectable().setSelectionColor(color);
    }
}
