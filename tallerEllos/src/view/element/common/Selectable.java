package view.element.common;

import java.awt.Color;

/**
 * Define a un objeto como seleccionable.
 * 
 * @author Agustina
 */
public interface Selectable {

  /**
   * Cambia el color de seleccion.
   * 
   * @param color nuevo color de seleccion.
   */
  public void setSelectionColor(Color color);

  /**
   * Cambia el color durante la seleccion.
   */
  public void changeColor();

  /**
   * Restaura los colores a los establecidos antes de la seleccion
   */
  public void restoreColor();

}
