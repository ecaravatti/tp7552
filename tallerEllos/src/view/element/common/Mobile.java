package view.element.common;

import java.awt.geom.Point2D;

/**
 * Define a un objeto como movil.
 * 
 * @author Agustina
 */
public interface Mobile {

  /**
   * Obtiene la posicion actual.
   * @return la posicion actual.
   */
  public Point2D getPosition();

  /**
   * Mueve el objeto a la posicion pasada como parametro.
   * @param position la nueva posicion.
   */
  public void moveTo(Point2D position);

}
