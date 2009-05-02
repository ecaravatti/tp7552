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
	
	public Stack(int capacity){
		this.capacity = capacity;
		stack = new ArrayList<T>(capacity);
	}

	public void push(T element) throws Exception{
		if(isFull()){
			throw new Exception();
		}
		else{
			stack.add(element);
			top++;
		}		
	}
	
	public T pop() throws EmptyStackException{
		if(isEmpty()){
			throw new EmptyStackException();
		}
		else{		
			T element = stack.get(top);
			stack.remove(top);
			top--;
			
			return element;
		}
	}
	
	private boolean isEmpty(){
		return(top == -1);
	}
	
	private boolean isFull(){
		return(top == capacity);
	}
}
