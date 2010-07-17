package model.structures.trees;

import events.ObservableBase;
import events.trees.BSTEvent;
import events.trees.BSTListener;

/**
 *
 * @author Exe Curia
 */
public class BinarySearchTreeObservable<K extends Comparable<K>> extends ObservableBase<BSTListener<K>> {

    public BinarySearchTreeObservable() {
        super();
    }

    protected void fireNodeFound(BinarySearchTree<K> source, BSTNode<K> node) {
        for (BSTListener<K> listener : this.cloneListeners()) {
            listener.nodeFound(new BSTEvent(source, node));
        }
    }

    protected void fireRootAdded(BinarySearchTree<K> source, BSTNode<K> root) {
        for (BSTListener<K> listener : this.cloneListeners()) {
            listener.rootAdded(new BSTEvent(source, root));
        }
    }

    protected void fireDobleRotationStarted(BinarySearchTree<K> source, BSTNode<K> node, int side) {
        for (BSTListener<K> listener : this.cloneListeners()) {
            listener.dobleRotationStarted(new BSTEvent(source, node, side));
        }
    }

    protected void fireNodeRotatedToLeft(BinarySearchTree<K> source, BSTNode<K> node) {
        for (BSTListener<K> listener : this.cloneListeners()) {
            listener.nodeRotatedToLeft(new BSTEvent(source, node));
        }
    }

    protected void fireNodeRotatedToRight(BinarySearchTree<K> source, BSTNode<K> node) {
        for (BSTListener<K> listener : this.cloneListeners()) {
            listener.nodeRotatedToRight(new BSTEvent(source, node));
        }
    }

    protected void fireNodeNotFound(BinarySearchTree<K> source) {
        for (BSTListener<K> listener : this.cloneListeners()) {
            listener.nodeNotFound(new BSTEvent(source));
        }
    }

    protected void fireRootRemoved(BinarySearchTree<K> source, BSTNode<K> node) {
        for (BSTListener<K> listener : this.cloneListeners()) {
            listener.rootRemoved(new BSTEvent(source, node));
        }
    }

    protected void fireRotateFinishedInDelete(BinarySearchTree<K> source, BSTNode<K> node, int side) {
        for (BSTListener<K> listener : this.cloneListeners()) {
            listener.rotateFinishedInDelete(new BSTEvent(source, node, side));
        }
    }

    protected void fireTraverseFinished(BinarySearchTree<K> source) {
        for (BSTListener<K> listener : this.cloneListeners()) {
            listener.traverseFinished(new BSTEvent(source));
        }
    }

}
