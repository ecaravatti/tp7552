/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.Iterator;
import javax.swing.JTextArea;

import model.collection.stack.StackObservable;
import view.collection.stack.StackPanel;
import view.collection.stack.StackView;
import view.command.common.ShowMessageCommand;

/**
 *
 * @author pgorin
 */
public class StackController<T> extends InteractiveController {

    private StackObservable<T> stack;
    private StackView<T> view;
    private StackPanel panel;
    private JTextArea operationsLog;

    public StackController(StackObservable<T> stack, StackPanel panel, JTextArea operationsLog) {
        super(panel.getView(), operationsLog);
        this.stack = stack;
        this.panel = panel;
        this.view = panel.getView();
        this.stack.addListener(view);
        this.operationsLog = operationsLog;
    }

    public void popAllItem() {
        boolean showEmptyMessage = true;
        this.panel.getButtonsPanel().enableComponents(false);

        try {
            this.view.prepareAnimation();

            Iterator<T> iterator = stack.iterator();
            while (iterator.hasNext()) {
                showEmptyMessage = false;
                stack.pop();
            }
        } catch (Exception e) {
        }

        if (showEmptyMessage) {
            this.showLogMessage("La pila se encuentra vacía.");
        }

        this.panel.getButtonsPanel().enableComponents(true);
    }

    public void pushItem(T item) {
        this.panel.getButtonsPanel().enableComponents(false);
        this.view.prepareAnimation();
        this.stack.push(item);
    }

    public void popItem() {
        this.panel.getButtonsPanel().enableComponents(false);
        try {
            this.view.prepareAnimation();
            this.stack.pop();
        } catch (Exception e) {
        }
    }

    @Override
    public void primitiveFinished() {
        this.panel.getButtonsPanel().enableComponents(true);
    }
}
