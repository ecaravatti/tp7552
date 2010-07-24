/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.animation.heap;

import java.util.ArrayList;

import view.animation.common.AbstractUndoAnimationSteps;
import view.collection.heap.HeapView;
import view.command.common.Command;
import view.command.common.ShowMessageCommand;
import view.command.common.StepFinishedCommand;

/**
 *
 * 
 */
public class EmptyAnimation<T> extends AbstractUndoAnimationSteps {

    private HeapView<T> view;

    public EmptyAnimation(HeapView<T> view) {
        this.view = view;
    }

    @Override
    protected void initializeListUndoSteps() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void initializeListSteps() {
        steps = new ArrayList<Command>();

        steps.add(new ShowMessageCommand(view, "El heap se encuentra vacío"));
        steps.add(new StepFinishedCommand(view, true));
    }
}
