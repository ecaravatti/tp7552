package model.collection.stack;

public interface Stack<T> {

	/**
	 * Indicate if the stack is empty.
	 */
	public boolean isEmpty();

	/**
	 * Indicate if the stack is full.
	 */
	public boolean isFull();

	/**
	 * Get the capacity of the stack.
	 */
	public int getCapacity();

	/**
	 * Set the capacity of the stack.
	 */
	public void setCapacity(int capacity);

	/**
	 * Get the actual size of the stack.
	 */
	public int getSize();

	/**
	 * Return the most recently added item.
	 */
	public T peek();

	/**
	 * Add an item to the stack.
	 */
	public void push(T item);

	/**
	 * Delete and return the most recently added item.
	 */
	public T pop();

	/**
	 * Delete all items.
	 */
	public void clear();
}
