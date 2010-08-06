package view.command.stack;

import view.collection.stack.StackNodeRoles;
import view.collection.stack.StackNodeView;
import view.command.common.Command;

/**
 * Comando para asignarle un rol a un nodo. (Roles = tope o final)
 */
public class AssignNodeRoleCommand<T> implements Command {

	private StackNodeView<T> node;
	private StackNodeRoles role;

	public AssignNodeRoleCommand(StackNodeView<T> node, StackNodeRoles role) {
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
