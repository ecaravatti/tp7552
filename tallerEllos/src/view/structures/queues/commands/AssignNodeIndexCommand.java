/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.structures.queues.commands;

import view.commons.commands.Command;
import view.structures.queues.QueueNodeView;

/**
 *
 * @author pgorin
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
