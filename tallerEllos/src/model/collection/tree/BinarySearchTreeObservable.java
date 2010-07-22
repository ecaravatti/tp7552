package model.collection.tree;

import event.ObservableBase;
import event.tree.BSTEvent;
import event.tree.BSTListener;

/**
 *
 * 
 */
public class BinarySearchTreeObservable<K extends Comparable<K>> extends ObservableBase<BSTListener<K>> {

    public BinarySearchTreeObservable() {
        super();
    }

    protected void fireNodeFound(BinarySearchTree<K> source, BSTNode<K> node) {
        for (BSTListener<K> listener : this.getListeners()) {
            listener.nodeFound(new BSTEvent<K>(source, node));
        }
    }

    protected void fireRootAdded(BinarySearchTree<K> source, BSTNode<K> root) {
        for (BSTListener<K> listener : this.getListeners()) {
            listener.rootAdded(new BSTEvent<K>(source, root));
        }
    }

    protected void fireDobleRotationStarted(BinarySearchTree<K> source, BSTNode<K> node, int side) {
        for (BSTListener<K> listener : this.getListeners()) {
            listener.dobleRotationStarted(new BSTEvent<K>(source, node, side));
        }
    }

    protected void fireNodeRotatedToLeft(BinarySearchTree<K> source, BSTNode<K> node) {
        for (BSTListener<K> listener : this.getListeners()) {
            listener.nodeRotatedToLeft(new BSTEvent<K>(source, node));
        }
    }

    protected void fireNodeRotatedToRight(BinarySearchTree<K> source, BSTNode<K> node) {
        for (BSTListener<K> listener : this.getListeners()) {
            listener.nodeRotatedToRight(new BSTEvent<K>(source, node));
        }
    }

    protected void fireNodeNotFound(BinarySearchTree<K> source) {
        for (BSTListener<K> listener : this.getListeners()) {
            listener.nodeNotFound(new BSTEvent<K>(source));
        }
    }

    protected void fireRootRemoved(BinarySearchTree<K> source, BSTNode<K> node) {
        for (BSTListener<K> listener : this.getListeners()) {
            listener.rootRemoved(new BSTEvent<K>(source, node));
        }
    }

    protected void fireRotateFinishedInDelete(BinarySearchTree<K> source, BSTNode<K> node, int side) {
        for (BSTListener<K> listener : this.getListeners()) {
            listener.rotateFinishedInDelete(new BSTEvent<K>(source, node, side));
        }
    }

    protected void fireTraverseFinished(BinarySearchTree<K> source) {
        for (BSTListener<K> listener : this.getListeners()) {
            listener.traverseFinished(new BSTEvent<K>(source));
        }
    }

}
