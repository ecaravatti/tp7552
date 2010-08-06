package view.command.queue;

import view.collection.queue.QueueNodeView;
import view.collection.queue.QueueView;
import view.command.common.Command;

/**
 * Comando para remover un nodo de la cola.
 */
public class RemoveNodeCommand<T> implements Command {

	private QueueView<T> view;
	private QueueNodeView<T> node;

	public RemoveNodeCommand(QueueView<T> view, QueueNodeView<T> node) {
		this.view = view;
		this.node = node;
	}

	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	public void execute() {
		this.view.getQueueNodes().remove(node);
	}

}
