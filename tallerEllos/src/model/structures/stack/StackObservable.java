/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.structures.stack;

import events.ObservableBase;
import events.stacks.StackListener;

public abstract class StackObservable<T> extends ObservableBase<StackListener<T>> implements Stack<T>,  Iterable<T> {

    protected void fireItemPushed(T item) {
        for (StackListener<T> listener : this.cloneListeners()) {
            listener.itemPushed(item);
        }
    }

    protected void fireItemPopped(T item) {
        for (StackListener<T> listener : this.cloneListeners()) {
            listener.itemPopped(item);
        }
    }

    protected void fireEmptyStackCondition() {
        for (StackListener<T> listener : this.cloneListeners()) {
            listener.emptyStackCondition();
        }
    }


}
