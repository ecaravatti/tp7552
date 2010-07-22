/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package view.command.queue;

import view.collection.queue.QueueNodeView;
import view.collection.queue.QueueView;
import view.command.common.Command;

/**
 *
 * @author pgorin
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
