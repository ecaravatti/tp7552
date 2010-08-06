/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.collection.heap.Heap;
import model.exception.heap.EmptyHeapException;
import view.collection.heap.HeapPanel;

/**
 *
 */
public class HeapController<T extends Comparable<T>> extends InteractiveController {
	public static final int MIN_VALUE = 0;
	public static final int MAX_VALUE = 999;
	
    private Heap<T> heap;
    private HeapPanel<T> panel;

    public HeapController(Heap<T> heap, HeapPanel<T> panel) {
        super(panel.getView());

        this.heap = heap;
        this.panel = panel;

        this.heap.addListener(panel.getView());
        this.panel.getView().addController(this);
        
        this.heap.setCapacity(this.panel.getButtonsPanel().getSelectedCapacity());
        this.panel.getView().initCapacity(this.panel.getButtonsPanel().getSelectedCapacity());
    }

    public void addItem(T item) {
    	this.panel.getButtonsPanel().enableComponents(false);
    	this.panel.getView().prepareAnimation();
    	this.heap.insert(item);
    }

    public void deleteItem() {
		if (isHeapEmpty()) {
			showLogMessage("Ningún nodo para eliminar");
		} else {
			try {
				this.panel.getButtonsPanel().enableComponents(false);
				this.panel.getView().prepareAnimation();
				this.heap.remove();
			} catch (EmptyHeapException e) {

			}
		}
	}
    
    public void clear() {
		if (isHeapEmpty()) {
			showLogMessage("El heap ya se encuentra vacío");
		} else {
	    	this.panel.getButtonsPanel().enableComponents(false);
	        this.heap.clear();
	        this.panel.getView().initCapacity(panel.getButtonsPanel().getSelectedCapacity());
	        this.panel.getButtonsPanel().enableComponents(true);
		}
    }
    
    public void setNewCapacity(int capacity) {
    	this.heap.clear();
    	this.heap.setCapacity(capacity);
        this.panel.getView().initCapacity(panel.getButtonsPanel().getSelectedCapacity());
    	this.panel.getButtonsPanel().enableComponents(true);
    }

    @Override
    public void primitiveFinished() {
    	panel.getButtonsPanel().enableComponents(true);
    }
    
    public boolean isHeapFull() {
    	return heap.isFull();
    }
    
    public boolean isHeapEmpty() {
    	return heap.isEmpty();
    }
    
    public int getHeapCapacity() {
    	return heap.getCapacity();
    }
}
