/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.Iterator;

import javax.swing.JTextArea;

import model.collection.stack.StackObservable;
import view.animation.stack.ItemPushedAnimation;
import view.collection.stack.StackPanel;
import view.collection.stack.StackPrimitives;
import view.collection.stack.StackView;
import view.command.common.ShowPrimitiveCodeCommand;
import view.command.common.StepFinishedCommand;

/**
 *
 */
public class StackController<T> extends InteractiveController {
    private StackObservable<T> stack;
    private StackView<T> view;
    private StackPanel<T> panel;

    public StackController(StackObservable<T> stack, StackPanel<T> panel, JTextArea operationsLog) {
        super(panel.getView(), operationsLog);
        this.stack = stack;
        this.panel = panel;
        this.view = panel.getView();
        this.stack.addListener(view);
        this.view.initStackSampleCapacity(this.stack.getCapacity()); 
    }

    public void popAllItem() {
    	this.panel.getButtonsPanel().enableComponents(false);
        this.stack.clear();
        this.view.initStackSampleCapacity(this.stack.getCapacity()); 
        this.showLogMessage("La pila se encuentra vacia.");
        this.panel.getButtonsPanel().enableComponents(true);
    }

    public void pushItem(T item) {
        this.panel.getButtonsPanel().enableComponents(false);
        this.view.prepareAnimation();
        this.stack.push(item);
        this.panel.getButtonsPanel().enableComponents(true);
    }

    public void popItem() {
        this.panel.getButtonsPanel().enableComponents(false);
        try {
            this.view.prepareAnimation();
            this.stack.pop();
        } catch (Exception e) {
        }
        this.panel.getButtonsPanel().enableComponents(true);
    }
    
    public boolean stackIsEmpty() {
    	return this.stack.isEmpty();
    }
    
    public boolean stackIsFull() {
    	return this.stack.isFull();
    }
    
    public void createStack(Integer capacity) {
        this.panel.getButtonsPanel().enableComponents(false);
    	stack.setCapacity(capacity);
    	view.initStackSampleCapacity(capacity);
        this.panel.getButtonsPanel().enableComponents(true);
    }

    @Override
    public void primitiveFinished() {
        this.panel.getButtonsPanel().enableComponents(true);
    }
}
