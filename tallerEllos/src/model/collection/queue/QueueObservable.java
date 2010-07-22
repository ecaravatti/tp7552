/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.collection.queue;

import event.ObservableBase;
import event.queue.QueueListener;


public abstract class QueueObservable<T> extends ObservableBase<QueueListener<T>> implements Queue<T>,  Iterable<T> {

    protected void fireItemEnqueued(T item) {
        for (QueueListener<T> listener : this.getListeners()) {
            listener.itemEnqueued(item);
        }
    }

    protected void fireItemDequeued(T item) {
        for (QueueListener<T> listener : this.getListeners()) {
            listener.itemDequeued(item);
        }
    }

    protected void fireEmptyQueueCondition() {
        for (QueueListener<T> listener : this.getListeners()) {
            listener.emptyQueueCondition();
        }
    }

    @Override
    public abstract boolean isEmpty();

    @Override
    public abstract int getLength();

    @Override
    public abstract void enqueue(T item);

    @Override
    public abstract T dequeue();
}
