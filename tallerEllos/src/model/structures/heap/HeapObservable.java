/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.structures.heap;

import events.ObservableBase;
import events.heap.HeapListener;

/**
 *
 * @author Duilio
 */
public abstract class HeapObservable<T> extends ObservableBase<HeapListener<T>> {

    protected void fireItemAdded(T item) {
        for (HeapListener<T> listener : this.cloneListeners()) {
            listener.itemAdded(item);
        }
    }

    protected void fireAddingItem(T item) {
        for (HeapListener<T> listener : this.cloneListeners()) {
            listener.addingItem(item);
        }
    }

    protected void fireItemDeleted(T item) {
        for (HeapListener<T> listener : this.cloneListeners()) {
            listener.itemDeleted(item);
        }
    }

    protected void fireDeletingItem(T item) {
        for (HeapListener<T> listener : this.cloneListeners()) {
            listener.deletingItem(item);
        }
    }

    protected void fireItemsSwapped(Integer item1, Integer item2) {
        for (HeapListener<T> listener : this.cloneListeners()) {
            listener.itemsSwapped(item1, item2);
        }
    }
}
