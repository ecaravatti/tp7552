package view.element.common;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import view.element.common.Mobile;

public interface ElementView extends Mobile {

  /**
   * Determina si elemento de la vista es visible (o no)
   * @param visible true si debe ser visible, false en caso contrario
   */
  void setVisible(boolean visible);

  /**
   * Indica si el elemento es visible o no
   * @return
   */
  boolean isVisible();

  /**
   * Pinta el nodo en el contexto dado
   * @param graphics contexto sobre el que se dibuja
   */
  public void paint(Graphics graphics);

  /**
   * La dimension del elemento
   * @return la dimension
   */
  Dimension getDimension();

  /**
   * Obtiene el ancho del elemento
   * @return el ancho del elemento
   */
  int getWidth();

  /**
   * Cambia el ancho del elemento nuevo ancho
   * @param width el nuevo ancho
   */
  void setWidth(int width);

  /**
   * Obtiene el ancho del elemento
   * @return el ancho del elemento
   */
  int getHeight();

  /**
   * Cambia la altura del elemento
   * @param height la nueva altura
   */
  void setHeight(int height);

  /**
   * Cambia el color de parpadeo
   * @param color Color de parpadeo
   */
  public void setFlashingColor(Color color);

  /**
   * Obtiene el color de parpadeo
   * @return el color de parpadeo
   */
  public Color getFlashingColor();

  /**
   * Detiene el parpadeo.
   */
  public void stopFlashing();

  /**
   * Inicia el parpadeo
   * @param delay pausa en el parpedeo
   */
  public void setFlashing(int delay);
}
