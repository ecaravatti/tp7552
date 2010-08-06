/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package view.command.stack;

import view.collection.stack.StackNodeView;
import view.collection.stack.StackView;
import view.command.common.Command;

/**
 *
 */
public class RemoveNodeCommand<T> implements Command {

    private StackView<T> view;
    private StackNodeView<T> node;

    public RemoveNodeCommand(StackView<T> view, StackNodeView<T> node) {
        this.view = view;
        this.node = node;
    }

    @Override
    public boolean canExecute() {
        return true;
    }

    @Override
    public void execute() {
        this.view.getStackNodes().remove(node);
    }

}
