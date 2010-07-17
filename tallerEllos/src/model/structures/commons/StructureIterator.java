package model.structures.commons;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterator over the data structure.
 * 
 * @author pgorin
 * 
 * @param <T> type of item stored in the data structure.
 */
public class StructureIterator<T> implements Iterator<T> {

    private StructureNode<T> currentNode;

    /**
     * Class constructor.
     */
    public StructureIterator() {
        this.currentNode = null;
    }

    /**
     * Class constructor.
     * 
     * @param currentNode current node to iterate.
     */
    public StructureIterator(StructureNode<T> currentNode) {
        this.currentNode = currentNode;
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasNext() {
        return this.currentNode != null;
    }

    /**
     * {@inheritDoc}
     */
    public void remove() {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public T next() {

        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }

        T item = this.currentNode.getItem();
        this.currentNode = this.currentNode.getNextNode();

        return item;
    }
}
