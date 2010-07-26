/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.collection.heap.Heap;
import model.exception.heap.EmptyHeapException;
import view.collection.heap.HeapPrimitives;
import view.collection.heap.HeapView;
import view.command.common.ShowPrimitiveCodeCommand;

/**
 *
 */
public class HeapController<T extends Comparable<T>> extends InteractiveController {

    private Heap<T> heap;
    private HeapView<T> view;

    public HeapController(Heap<T> heap, HeapView<T> view) {
        super(view);

        this.heap = heap;
        this.view = view;

        heap.addListener(view);
        view.addController(this);
    }

    public void addItem(T item) {
      new ShowPrimitiveCodeCommand(this, HeapPrimitives.insert.getCode()).execute();
      this.heap.insert(item);
    }

    public void deleteItem() {
      new ShowPrimitiveCodeCommand(this, HeapPrimitives.delete.getCode()).execute();
      try { 
    	  this.heap.remove();
      } catch (EmptyHeapException e) {
    	  
      }
    }
    
    public void clear() {
        this.heap.clear();
    }

    @Override
    public void primitiveFinished() {
        view.setEnabledButtons(true);
    }
}
