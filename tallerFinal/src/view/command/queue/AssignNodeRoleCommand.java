package view.command.queue;

import view.collection.queue.QueueNodeRoles;
import view.collection.queue.QueueNodeView;
import view.command.common.Command;

/**
 * Comando para asignarle un rol a un nodo (Roles= frente o final)
 */
public class AssignNodeRoleCommand<T> implements Command {

	private QueueNodeView<T> node;
	private QueueNodeRoles role;

	public AssignNodeRoleCommand(QueueNodeView<T> node, QueueNodeRoles role) {
		this.node = node;
		this.role = role;
	}

	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	public void execute() {
		this.node.assignRole(this.role);
	}

}
