/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.structures.heap.animations;

import java.util.ArrayList;
import view.commons.animations.AbstractUndoAnimationSteps;
import view.commons.commands.Command;
import view.commons.commands.ShowMessageCommand;
import view.commons.commands.StepFinishedCommand;
import view.structures.heap.HeapView;

/**
 *
 * @author Duilio
 */
public class EndAnimation<T> extends AbstractUndoAnimationSteps {

    private HeapView view;
    private String text;

    public EndAnimation(HeapView view, String text) {
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
