/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.Iterator;

import model.collection.queue.QueueObservable;
import model.exception.queue.QueueFullException;
import view.collection.queue.QueuePanel;
import view.collection.queue.QueueView;

/**
 *
 */
public class QueueController<T> extends InteractiveController {
	public static final int MIN_VALUE = 0;
	public static final int MAX_VALUE = 999;

    private QueueObservable<T> queue;
    private QueueView<T> view;
    private QueuePanel<T> panel;

    public QueueController(QueueObservable<T> queue, QueuePanel<T> panel) {
        super(panel.getView());
        this.queue = queue;
        this.panel = panel;
        this.view = panel.getView();
        this.queue.addListener(view);
        this.queue.setCapacity(panel.getButtonsPanel().getSelectedCapacity());
        this.view.initCapacity(panel.getButtonsPanel().getSelectedCapacity());
    }

    public void dequeueAllItem() {
    	if (isQueueEmpty()) {
    		showLogMessage("La cola ya se encuentras vacía.");
    	} else {
	        boolean showEmptyMessage = true;
	        this.panel.getButtonsPanel().enableComponents(false);
	
	        try {
	            this.view.prepareAnimation();
	
	            Iterator<T> iterator = queue.iterator();
	            while (iterator.hasNext()) {
	                showEmptyMessage = false;
	                queue.dequeue();
	            }
	        } catch (Exception e) {
	        	//
	        }
	
	        if (showEmptyMessage) {
	            this.showLogMessage("La cola se encuentra vacía.");
	        }
	
	        this.panel.getButtonsPanel().enableComponents(true);
    	}
    }

    public void enqueueItem(T item) {
        try {
        	this.panel.getButtonsPanel().enableComponents(false);
        	this.view.prepareAnimation();
        	this.queue.enqueue(item);
        } catch (QueueFullException e) {
        	//Hacer algo? En realidad no podemos permitir que llegue a esta linea
        }
    }

    public void dequeueItem() {
    	if (isQueueEmpty()) {
    		showLogMessage("Ningún elemento para desencolar.");
    	} else {
	        try {
	        	this.panel.getButtonsPanel().enableComponents(false);
	            this.view.prepareAnimation();
	            this.queue.dequeue();
	        } catch (Exception e) {
	        	//
	        }
    	}
    }

    @Override
    public void primitiveFinished() {
        this.panel.getButtonsPanel().enableComponents(true);
    }
    
    /**
     * Limpia la vista
     */
    public void clear() {
      this.queue.clear();
      this.view.clear();
      this.view.repaint();
      this.panel.getButtonsPanel().enableComponents(true);
    }
    
    public void setNewCapacity(int capacity) {
    	this.queue.clear();
        this.view.clear();
    	this.queue.setCapacity(capacity);
    	this.view.initCapacity(capacity);
    	this.panel.getButtonsPanel().enableComponents(true);
    }
    
    public boolean isQueueFull() {
    	return queue.isFull();
    }
    
    public boolean isQueueEmpty() {
    	return queue.isEmpty();
    }
    
    public int getQueueCapacity() {
    	return queue.getCapacity();
    }
}
