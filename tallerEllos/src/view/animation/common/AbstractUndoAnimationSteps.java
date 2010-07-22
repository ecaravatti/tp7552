package view.animation.common;

import java.util.ArrayList;
import java.util.List;

import view.command.common.Command;

/**
 * 
 * @author Agustina Freije
 */
public abstract class AbstractUndoAnimationSteps implements UndoAnimationSteps {
  protected List<Command> steps;
  protected List<Command> undoSteps;
  private boolean redoPause;
  private boolean undoPause;
  
  public AbstractUndoAnimationSteps() {
    this.steps = null;
    this.undoSteps = null;
    this.redoPause = true;
    this.undoPause = true;
  }

  @Override
  public List<Command> getSteps() {
    if (steps == null)
      this.initializeListSteps();
    return new ArrayList<Command>(steps);
  }

  @Override
  public List<Command> getUndoSteps() {
    if (undoSteps == null)
      this.initializeListUndoSteps();
    return new ArrayList<Command>(undoSteps);
  }

  public boolean getRedoPause(){
    return redoPause;
  }
  
  public boolean getUndoPause(){
    return undoPause;
  }
  
  public void setRedoPause(boolean pause){
    redoPause = pause;
  }
  
  public void setUndoPause(boolean pause){
    undoPause = pause;
  }
  
  /**
   * Crea la lista de pasos necesarios para deshacer el proceso de agregar un
   * nodo
   */
  abstract protected void initializeListUndoSteps();

  /**
   * Crea la lista de pasos para realizar la animacion
   */
  abstract protected void initializeListSteps();

}
