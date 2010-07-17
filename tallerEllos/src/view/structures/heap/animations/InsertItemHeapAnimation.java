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
public class InsertItemHeapAnimation<T> extends AbstractUndoAnimationSteps {

    private HeapView view;
    private T item;

    public InsertItemHeapAnimation(HeapView view, T item) {
        this.view = view;
        this.item = item;
    }

    @Override
    protected void initializeListUndoSteps() {
    }

    @Override
    protected void initializeListSteps() {
        steps = new ArrayList<Command>();

        steps.add(new ShowMessageCommand(view, "Insertando elemento " + item.toString()));
        steps.add((new StepFinishedCommand(view, false)));
    }
}
