package event.tree;

import java.util.EventObject;

import model.collection.tree.BSTNode;
import model.collection.tree.BinarySearchTree;

/**
 * Representa un evento que se origina en un Binary Search Tree.
 *
 * @author Exe Curia
 */
public class BSTEvent<K extends Comparable<K>> extends EventObject {
    private BSTNode<K> currentNode;
    private int side;

    public BSTEvent(BinarySearchTree<K> source) {
        super(source);
        this.currentNode = null;
    }

    public BSTEvent(BinarySearchTree<K> source, BSTNode<K> node) {
        super(source);
        this.side = 0;
        this.currentNode = node;
    }

    public BSTEvent(BinarySearchTree<K> source, BSTNode<K> node, int side) {
        super(source);
        this.side = side;
        this.currentNode = node;
    }

    @Override
    public BinarySearchTree<K> getSource() {
        return (BinarySearchTree<K>) source;
    }

    public BSTNode<K> getCurrentNode() {
        return currentNode;
    }

    public int getSide() {
        return side;
    }
}
