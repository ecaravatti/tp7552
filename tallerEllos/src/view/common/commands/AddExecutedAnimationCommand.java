package view.commons.commands;

import view.commons.PanelAnimated;
import view.commons.animations.AbstractUndoAnimationSteps;

public class AddExecutedAnimationCommand implements Command {
  private PanelAnimated panel;
  private AbstractUndoAnimationSteps animation;
  
  
  public AddExecutedAnimationCommand(PanelAnimated panel,
      AbstractUndoAnimationSteps animation) {
    super();
    this.panel = panel;
    this.animation = animation;
  }

  @Override
  public boolean canExecute() {
    return true;
  }

  @Override
  public void execute() {
    this.panel.addExecutedAnimationSteps( animation );
  }

}
