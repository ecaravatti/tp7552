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
 */
public class InsertItemHeapAnimation<T> extends AbstractUndoAnimationSteps {

	private HeapView<T> view;
	private T item;

	public InsertItemHeapAnimation(HeapView<T> view, T item) {
		this.view = view;
		this.item = item;
	}

	@Override
	protected void initializeListUndoSteps() {
	}

	@Override
	protected void initializeListSteps() {
		steps = new ArrayList<Command>();

		steps.add(new ShowMessageCommand(view, "Insertando elemento "
				+ item.toString()));
		steps.add((new StepFinishedCommand(view, false)));
	}
}
