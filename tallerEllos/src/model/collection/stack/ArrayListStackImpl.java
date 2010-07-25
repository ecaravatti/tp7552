package model.collection.stack;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;

public class ArrayListStackImpl<T> extends StackObservable<T> {

	private int top = -1;
	private int capacity = 1;
	private ArrayList<T> stack;

    public ArrayListStackImpl() {
    	stack = new ArrayList<T>();
    }

    public boolean isEmpty() {
    	return (top == -1);
    }

    public boolean isFull() {
    	return (stack.size() == capacity);
    }
    
    public int getCapacity() {
    	return this.capacity;
    }
    
    public void setCapacity(int capacity) {
    	this.capacity = capacity;
    }

    public int getSize() {
    	return stack.size();
    }

    public T peek() {
    	if (isEmpty()) {
    		this.fireEmptyStackCondition();
			throw new EmptyStackException();
		} else {
			return stack.get(top);
		}
    }

    public void push(T item) {
    	if (!this.isFull()) {
    		stack.add(item);
    		top++;
    		this.fireItemPushed(item);
    	}
    }

    public T pop() {
    	if (!this.isEmpty()) {
    		T item = this.peek();
    		stack.remove(top);
    		top--;
    		this.fireItemPopped(item);
    		return item;
    	}
    	else {
    		return null;
    	}
    }

    @Override
    public String toString() {
        String s = "";
        for (T item : stack) {
        	s += item + ", ";
        }
        return "[ " + s + "]";
    }

    public Iterator<T> iterator() {
        return stack.iterator();
    }
    
}
