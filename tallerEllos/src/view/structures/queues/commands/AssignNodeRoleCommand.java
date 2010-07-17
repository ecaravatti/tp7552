/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package view.structures.queues.commands;

import view.commons.commands.Command;
import view.structures.queues.QueueNodeRoles;
import view.structures.queues.QueueNodeView;

/**
 *
 * @author pgorin
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
