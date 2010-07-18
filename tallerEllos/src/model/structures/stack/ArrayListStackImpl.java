package model.structures.stack;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;

public class ArrayListStackImpl<T> extends StackObservable<T> {

	private int top = -1;
	private ArrayList<T> stack;

    public ArrayListStackImpl() {
    	stack = new ArrayList<T>();
    }

    public boolean isEmpty() {
    	return (top == -1);
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
		stack.add(item);
		top++;
		this.fireItemPushed(item);
    }

    public T pop() {
		T item = this.peek();
		stack.remove(top);
		top--;

		this.fireItemPopped(item);
		
		return item;
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
