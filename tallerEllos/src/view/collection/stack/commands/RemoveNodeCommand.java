/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package view.structures.stacks.commands;

import view.commons.commands.Command;
import view.structures.stacks.StackNodeView;
import view.structures.stacks.StackView;

/**
 *
 * @author pgorin
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
