package model.structures.queues;

import java.util.Iterator;

import model.structures.commons.StructureIterator;
import model.structures.commons.StructureNode;

/**
 * Queue data structure implemented using a linked list. Each queue item is of type T.
 * 
 * @author pgorin
 * 
 * @param <T> type of item stored in the queue.
 */
public class LinkedListQueueImpl<T> extends QueueObservable<T> {

    // Number of elements on queue.
    private int length;
    // Beginning node of queue.
    private StructureNode<T> firstNode;
    // End node of queue.
    private StructureNode<T> lastNode;

    /**
     * Class constructor. Create an empty queue.
     */
    public LinkedListQueueImpl() {
        this.firstNode = null;
        this.lastNode = null;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isEmpty() {
        return this.firstNode == null;
    }

    /**
     * {@inheritDoc}
     */
    public int getLength() {
        return this.length;
    }

    /**
     * {@inheritDoc}
     */
    public void enqueue(T item) {
        StructureNode<T> node = new StructureNode<T>();
        node.setItem(item);

        if (this.isEmpty()) {
            this.firstNode = node;
            this.lastNode = node;
        } else {
            this.lastNode.setNextNode(node);
            this.lastNode = node;
        }

        this.length++;
        this.fireItemEnqueued(item);
    }

    /**
     * {@inheritDoc}
     */
    public T dequeue() {

        if (this.isEmpty()) {
            this.fireEmptyQueueCondition();
            throw new EmptyQueueException();
        }

        T item = this.firstNode.getItem();
        this.firstNode = this.firstNode.getNextNode();
        this.length--;

        this.fireItemDequeued(item);

        return item;
    }

    public Iterator<T> iterator() {
        return new StructureIterator<T>(this.firstNode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        String s = "";

        for (StructureNode<T> node = this.firstNode; node != null; node = node.getNextNode()) {
            s += node.getItem() + " ";
        }

        return s;
    }

}
