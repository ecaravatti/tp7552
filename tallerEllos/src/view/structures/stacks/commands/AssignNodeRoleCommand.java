/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package view.structures.stacks.commands;

import view.commons.commands.Command;
import view.structures.queues.QueueNodeRoles;
import view.structures.queues.QueueNodeView;
import view.structures.stacks.StackNodeRoles;
import view.structures.stacks.StackNodeView;

/**
 *
 * @author pgorin
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
