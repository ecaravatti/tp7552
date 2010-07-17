package model.structures.queues;

/**
 * Queue data structure. Each queue item is of type T.
 *
 * @author pgorin
 *
 * @param <T> type of item stored in the queue.
 */
public interface Queue<T> {

    /**
     * Indicate if the queue is empty.
     */
    public boolean isEmpty();

    /**
     * Get the length of the queue.
     */
    public int getLength();

    /**
     * Add an item to the queue.
     */
    public void enqueue(T item);

    /**
     * Remove and return the least recently added item.
     */
    public T dequeue();

}
