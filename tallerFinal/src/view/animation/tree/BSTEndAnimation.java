package view.animation.tree;

import java.util.ArrayList;

import view.animation.common.AbstractUndoAnimationSteps;
import view.collection.tree.BinarySearchTreeView;
import view.command.common.Command;
import view.command.common.PaintCommand;
import view.command.common.StepFinishedCommand;
import view.exception.common.CannotUndoException;

public class BSTEndAnimation extends AbstractUndoAnimationSteps {
  private BinarySearchTreeView bstView;
  
  public BSTEndAnimation(BinarySearchTreeView bstView) {
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
