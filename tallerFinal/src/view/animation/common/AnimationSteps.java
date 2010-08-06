package view.animation.common;

import java.util.List;

import view.command.common.Command;

/**
 * 
 * 
 */
public interface AnimationSteps {

  /**
   * Obtiene una lista con cada uno de los pasos a ser ejecutaador durante una
   * animacion.
   * @return una lista con los pasos para ejecutar la animacion
   */
  List<Command> getSteps();

}
