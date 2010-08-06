package model.collection.queue;

import model.exception.queue.QueueFullException;

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
	public void enqueue(T item) throws QueueFullException;

	/**
	 * Remove and return the least recently added item.
	 */
	public T dequeue();

	/**
	 * Empties the queue.
	 */
	public void clear();

	/**
	 * Get the capacity of the queue.
	 */
	public int getCapacity();

	/**
	 * Set the capacity of the queue.
	 */
	public void setCapacity(int capacity);

	/**
	 * Indicate if the queue is full.
	 */
	public boolean isFull();
}
