package model.structures.stack;


public interface Stack<T> {

    /**
     * Indicate if the stack is empty.
     */
    public boolean isEmpty();

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
}