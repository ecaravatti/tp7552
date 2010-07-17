package view.commons.animations;

import java.util.List;
import view.commons.commands.Command;

/**
 * 
 * @author Agustina Freije
 */
public interface AnimationSteps {

  /**
   * Obtiene una lista con cada uno de los pasos a ser ejecutaador durante una
   * animacion.
   * @return una lista con los pasos para ejecutar la animacion
   */
  List<Command> getSteps();

}
