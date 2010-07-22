package view.animation.common;

import java.util.List;

import view.command.common.Command;

/**
 * 
 * @author Agustina Freije
 */
public interface UndoAnimationSteps extends AnimationSteps {

  /**
   * Obtiene los pasos que deben ejecutarse para deshacer una operacion.
   * @return la lista con los pasos necesarios para deshacer la animacion
   */
  List<Command> getUndoSteps();
  
  boolean getRedoPause();
  
  boolean getUndoPause();
  
  void setRedoPause(boolean pause);
  
  void setUndoPause(boolean pause);

}
