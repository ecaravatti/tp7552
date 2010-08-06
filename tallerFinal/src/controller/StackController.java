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
	public static final int MIN_VALUE = 0;
	public static final int MAX_VALUE = 999;

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
		this.view.initCapacity(this.stack.getCapacity());
	}

	public void popAllItem() {
		if (stackIsEmpty()) {
			showLogMessage("La pila ya se encuentra vacía.");
		} else {
			this.panel.getButtonsPanel().enableComponents(false);
			this.stack.clear();
			this.view.clear();
			this.view.repaint();
			this.showLogMessage("La pila se encuentra vacía.");
			this.panel.getButtonsPanel().enableComponents(true);
		}
	}

	public void pushItem(T item) {
		this.panel.getButtonsPanel().enableComponents(false);
		this.view.prepareAnimation();
		this.stack.push(item);
	}

	public void popItem() {
		if (stackIsEmpty()) {
			showLogMessage("Ningún elemento para desapilar.");
		} else {
			try {
				this.panel.getButtonsPanel().enableComponents(false);
				this.view.prepareAnimation();
				this.stack.pop();
			} catch (Exception e) {
				//
			}
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
		view.initCapacity(capacity);
		this.panel.getButtonsPanel().enableComponents(true);
	}

	@Override
	public void primitiveFinished() {
		this.panel.getButtonsPanel().enableComponents(true);
	}
}
