package queue;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Queue<T> {
	
	private final static int DEFAULT_CAPACITY = 8;
	private int capacity;
	private ArrayList<T> queue;
	private int fullSize = 0; 
	
	public Queue(int capacity){
		queue = new ArrayList<T>(capacity);
	}
	
	public Queue(){
		queue = new ArrayList<T>(DEFAULT_CAPACITY);
		capacity = DEFAULT_CAPACITY;
	}
	
	/**
	 * Inserts the specified element into this queue, if possible.
	 * */
	public boolean offer(T element){
		boolean result = false;
		
		if(canInsert()){
			this.queue.add(element);
			fullSize++;
			result = true;
		}
		return result;
	}
	
	private boolean canInsert(){
		 return (fullSize < capacity);
	}
	
	/**
	 * Retrieves and removes the head of this queue, or null  if this queue is empty. 
	 * */
	public T poll(){
		T elementToReturn = null;
		
		if(!isEmpty()){
			elementToReturn = this.queue.get(0);
			this.queue.remove(0);
			fullSize --;
		}
		
		return elementToReturn;
	}
	
	private boolean isEmpty(){
		return (fullSize == 0);
	}
	
	/**
	 * Retrieves and removes the head of this queue. 
	 * This method differs from the poll method in that it throws an exception if this queue is empty. 
	 * */
	public T remove() throws NoSuchElementException{
		if(isEmpty()){
			throw new NoSuchElementException();
		}
		
		return poll();
	}
	
	/**
	 * Retrieves, but does not remove, the head of this queue, returning null if this queue is empty. 
	 * */
	public T peek(){
		T elementToReturn = null;
		
		if(!isEmpty()){
			elementToReturn = this.queue.get(0);
		}
		
		return elementToReturn;
	}
	
	/**
	 * Retrieves, but does not remove, the head of this queue. 
	 * This method differs from the peek method only in that it throws an exception if this queue is empty. 
	 * */
	public T element()throws NoSuchElementException{
		if(!isEmpty()){
			throw new NoSuchElementException();
		}
		
		return this.queue.get(0);
	}
	
	public void destroy(){
		this.queue.clear();
	}
	
}
