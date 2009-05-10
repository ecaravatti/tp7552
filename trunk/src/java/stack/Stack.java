package stack;

import java.util.ArrayList;
import java.util.EmptyStackException;

public class Stack<T> {

	private final static int DEFAULT_CAPACITY = 8;
	private int top = -1;
	private int capacity;
	private ArrayList<T> stack;

	public Stack() {
		stack = new ArrayList<T>(DEFAULT_CAPACITY);
	}

	public void destroy() {
		stack.clear();
		this.top = -1;
	}

	public Stack(int capacity) {
		this.capacity = capacity;
		stack = new ArrayList<T>(capacity);
	}

	/**
	 * Pushes an item onto the top of this stack.
	 * */
	public void push(T element) throws Exception {
		if (isFull()) {
			throw new Exception();
		} else {
			stack.add(element);
			top++;
		}
	}

	/**
	 * Looks at the object at the top of this stack without removing it from the
	 * stack.
	 * */
	public T peek() {
		if (isEmpty()) {
			throw new EmptyStackException();
		} else {
			T element = stack.get(top);

			return element;
		}
	}

	/**
	 * Removes the object at the top of this stack and returns that object as
	 * the value of this function.
	 * */
	public T pop() throws EmptyStackException {
		if (isEmpty()) {
			throw new EmptyStackException();
		} else {
			T element = stack.get(top);
			stack.remove(top);
			top--;

			return element;
		}
	}

	private boolean isEmpty() {
		return (top == -1);
	}

	private boolean isFull() {
		return (top == (capacity - 1));
	}
}
