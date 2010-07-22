package model.collection.tree;

import event.ObservableBase;
import event.tree.BSTNodeEvent;
import event.tree.BSTNodeListener;

/**
 *
 */
public class BSTNodeObservable<K extends Comparable<K>> extends ObservableBase<BSTNodeListener<K>> {

    public BSTNodeObservable() {
        super();
    }

    protected void fireBalanceUpdated(BSTNode<K> source) {
        for (BSTNodeListener<K> listener : this.getListeners()) {
            listener.balanceUpdated(new BSTNodeEvent<K>(source));
        }
    }

    protected void fireLeftChildAdded(BSTNode<K> source) {
        for (BSTNodeListener<K> listener : this.getListeners()) {
            listener.leftChildAdded(new BSTNodeEvent<K>(source));
        }
    }

    protected void fireRightChildAdded(BSTNode<K> source) {
        for (BSTNodeListener<K> listener : this.getListeners()) {
            listener.rightChildAdded(new BSTNodeEvent<K>(source));
        }
    }

    protected void fireGetLeftChild(BSTNode<K> source) {
        for (BSTNodeListener<K> listener : this.getListeners()) {
            listener.getLeftChild(new BSTNodeEvent<K>(source));
        }
    }

    protected void fireGetRightChild(BSTNode<K> source) {
        for (BSTNodeListener<K> listener : this.getListeners()) {
            listener.getRightChild(new BSTNodeEvent<K>(source));
        }
    }

    protected void fireNodeTraversed(BSTNode<K> source) {
        for (BSTNodeListener<K> listener : this.getListeners()) {
            listener.nodeTraversed(new BSTNodeEvent<K>(source));
        }
    }

    protected void fireRemoved(BSTNode<K> source) {
        for (BSTNodeListener<K> listener : this.getListeners()) {
            listener.removed(new BSTNodeEvent<K>(source));
        }
    }
}
