package view.command.queue;

import view.collection.queue.QueueNodeView;
import view.command.common.Command;

/**
 * Comando para asignarle un indice a un nodo.
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
