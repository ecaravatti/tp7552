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
public class EndAnimation<T> extends AbstractUndoAnimationSteps {

    private HeapView<T> view;
    private String text;

    public EndAnimation(HeapView<T> view, String text) {
        this.view = view;
        this.text = text;
    }

    @Override
    protected void initializeListUndoSteps() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void initializeListSteps() {
        steps = new ArrayList<Command>();

        steps.add(new ShowMessageCommand(view, "Cantidad de intercambios: " + view.getSwapCount()));
        steps.add(new ShowMessageCommand(view, text));
        steps.add(new StepFinishedCommand(view, true));
    }
}
