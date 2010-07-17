/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.structures.queues;

import events.ObservableBase;
import events.queues.QueueListener;

/**
 *
 * @author pgorin
 */
public abstract class QueueObservable<T> extends ObservableBase<QueueListener<T>> implements Queue<T>,  Iterable<T> {

    protected void fireItemEnqueued(T item) {
        for (QueueListener<T> listener : this.cloneListeners()) {
            listener.itemEnqueued(item);
        }
    }

    protected void fireItemDequeued(T item) {
        for (QueueListener<T> listener : this.cloneListeners()) {
            listener.itemDequeued(item);
        }
    }

    protected void fireEmptyQueueCondition() {
        for (QueueListener<T> listener : this.cloneListeners()) {
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
