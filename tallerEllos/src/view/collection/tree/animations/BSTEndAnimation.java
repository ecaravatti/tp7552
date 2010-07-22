package view.structures.trees.animations;

import java.util.ArrayList;

import view.commons.animations.AbstractUndoAnimationSteps;
import view.commons.commands.Command;
import view.commons.commands.PaintCommand;
import view.commons.commands.StepFinishedCommand;
import view.commons.exceptions.CannotUndoException;
import view.structures.trees.BSTHeightBalancedView;

public class BSTEndAnimation extends AbstractUndoAnimationSteps {
  private BSTHeightBalancedView bstView;
  
  public BSTEndAnimation(BSTHeightBalancedView bstView) {
    super();
    this.bstView = bstView;
  }

  @Override
  protected void initializeListSteps() {
    steps = new ArrayList<Command>();
    steps.add( new StepFinishedCommand(this.bstView, true, this) );
  }

  @Override
  protected void initializeListUndoSteps() {
    undoSteps = new ArrayList<Command>();
    undoSteps.add( new PaintCommand(bstView) );
    try {
      bstView.undoLastStep();
    } catch (CannotUndoException e) {
    }
  }

}
