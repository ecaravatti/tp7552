package view.command.common;

import view.animation.common.AbstractUndoAnimationSteps;
import view.common.AnimatedPanel;

public class AddExecutedAnimationCommand implements Command {
  private AnimatedPanel panel;
  private AbstractUndoAnimationSteps animation;
  
  
  public AddExecutedAnimationCommand(AnimatedPanel panel,
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
