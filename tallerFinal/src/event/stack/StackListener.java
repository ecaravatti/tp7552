package event.stack;

import java.util.EventListener;

/**
 * Stack data structure listener.
 * 
 */
public interface StackListener<T> extends EventListener {

	/**
	 * Event fired when a pop operation is performed and the stack is empty.
	 */
	public void emptyStackCondition();

	/**
	 * Event fired when a new item is pushed into the stack.
	 * 
	 * @param item
	 *            pushed item.
	 */
	public void itemPushed(T item);

	/**
	 * Event fired when the top item of the stack is popped.
	 * 
	 * @param item
	 *            popped item.
	 */
	public void itemPopped(T item);
}
