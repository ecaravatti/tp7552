/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.collection.heap;

import event.ObservableBase;
import event.heap.HeapListener;

/**
 *
 * 
 */
public abstract class HeapObservable<T> extends ObservableBase<HeapListener<T>> {

    protected void fireItemAdded(T item) {
        for (HeapListener<T> listener : this.getListeners()) {
            listener.itemAdded(item);
        }
    }

    protected void fireAddingItem(T item) {
        for (HeapListener<T> listener : this.getListeners()) {
            listener.addingItem(item);
        }
    }

    protected void fireItemDeleted(T item) {
        for (HeapListener<T> listener : this.getListeners()) {
            listener.itemDeleted(item);
        }
    }

    protected void fireDeletingItem(T item) {
        for (HeapListener<T> listener : this.getListeners()) {
            listener.deletingItem(item);
        }
    }

    protected void fireItemsSwapped(Integer item1, Integer item2) {
        for (HeapListener<T> listener : this.getListeners()) {
            listener.itemsSwapped(item1, item2);
        }
    }
}
