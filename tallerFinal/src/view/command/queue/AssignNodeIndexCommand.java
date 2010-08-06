/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.command.queue;

import view.collection.queue.QueueNodeView;
import view.command.common.Command;

/**
 *
 * 
 */
public class AssignNodeIndexCommand<T> implements Command {

	private QueueNodeView<T> node;
	private Integer index;

	public AssignNodeIndexCommand(QueueNodeView<T> node, Integer index) {
		this.node = node;
		this.index = index;
	}

	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	public void execute() {
		this.node.setIndex(index);
	}
}
