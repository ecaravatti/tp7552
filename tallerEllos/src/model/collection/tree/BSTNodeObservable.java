package model.collection.tree;

import event.ObservableBase;
import event.tree.BSTNodeEvent;
import event.tree.BSTNodeListener;

/**
 *
 * @author Exe Curia
 */
public class BSTNodeObservable<K extends Comparable<K>> extends ObservableBase<BSTNodeListener<K>> {

    public BSTNodeObservable() {
        super();
    }

    protected void fireBalanceUpdated(BSTNode<K> source) {
        for (BSTNodeListener<K> listener : this.cloneListeners()) {
            listener.balanceUpdated(new BSTNodeEvent(source));
        }
    }

    protected void fireLeftChildAdded(BSTNode<K> source) {
        for (BSTNodeListener<K> listener : this.cloneListeners()) {
            listener.leftChildAdded(new BSTNodeEvent(source));
        }
    }

    protected void fireRightChildAdded(BSTNode<K> source) {
        for (BSTNodeListener<K> listener : this.cloneListeners()) {
            listener.rightChildAdded(new BSTNodeEvent(source));
        }
    }

    protected void fireGetLeftChild(BSTNode<K> source) {
        for (BSTNodeListener<K> listener : this.cloneListeners()) {
            listener.getLeftChild(new BSTNodeEvent(source));
        }
    }

    protected void fireGetRightChild(BSTNode<K> source) {
        for (BSTNodeListener<K> listener : this.cloneListeners()) {
            listener.getRightChild(new BSTNodeEvent(source));
        }
    }

    protected void fireNodeTraversed(BSTNode<K> source) {
        for (BSTNodeListener<K> listener : this.cloneListeners()) {
            listener.nodeTraversed(new BSTNodeEvent(source));
        }
    }

    protected void fireRemoved(BSTNode<K> source) {
        for (BSTNodeListener<K> listener : this.cloneListeners()) {
            listener.removed(new BSTNodeEvent(source));
        }
    }
}
