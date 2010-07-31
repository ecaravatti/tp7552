/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.collection.stack.StackObservable;
import view.collection.stack.StackPanel;
import view.collection.stack.StackView;

/**
 *
 */
public class StackController<T> extends InteractiveController {
    private StackObservable<T> stack;
    private StackView<T> view;
    private StackPanel<T> panel;

    public StackController(StackObservable<T> stack, StackPanel<T> panel) {
        super(panel.getView());
        this.stack = stack;
        this.panel = panel;
        this.view = panel.getView();
        this.stack.addListener(view);
        this.stack.setCapacity(panel.getButtonsPanel().getSelectedCapacity());
        this.view.setStructureCapacity(panel.getButtonsPanel().getSelectedCapacity());
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
    }

    public void popItem() {
        this.panel.getButtonsPanel().enableComponents(false);
        try {
            this.view.prepareAnimation();
            this.stack.pop();
        } catch (Exception e) {
        }
    }
    
    public boolean stackIsEmpty() {
    	return this.stack.isEmpty();
    }
    
    public boolean stackIsFull() {
    	return this.stack.isFull();
    }
    
    public int getStackCapacity() {
    	return stack.getCapacity();
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
