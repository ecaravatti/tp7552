package collection.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import command.Command;
import command.HighlightCommand;
import command.queue.OfferCommand;
import command.queue.PollCommand;
import common.Element;

public class Queue<T> implements Cloneable {

	private final static int DEFAULT_CAPACITY = 8;
	private int capacity;
	private ArrayList<Element<Integer>> queue;
	private int fullSize = 0;
	private List<Command> commandList;
	private int idGenerator = 0; 
	
	public Queue(int capacity) {
		this.capacity = capacity;
		queue = new ArrayList<Element<Integer>>(capacity);
		commandList = new ArrayList<Command>();
	}

	public Queue() {
		queue = new ArrayList<Element<Integer>>(DEFAULT_CAPACITY);
		capacity = DEFAULT_CAPACITY;
		commandList = new ArrayList<Command>();
	}

	public List<Command> getCommands(){
		return commandList;
	} 
	
	private int generateId(){
		return idGenerator++;
	}
	
	/**
	 * Inserts the specified element into this queue, if possible.
	 * */
	public boolean offer(Element<Integer> element) {
		boolean result = false; 
		if (canInsert()) {
			this.queue.add(element);
			fullSize++;
			result = true;
			commandList.clear();
			commandList.add(new OfferCommand(generateId(), element.getValue().toString()));
		}
		return result;
	}

	private boolean canInsert() {
		return (fullSize < capacity);
	}

	/**
	 * Retrieves and removes the head of this queue, or null if this queue is
	 * empty.
	 * */
	public Element<Integer> poll() {
		Element<Integer> elementToReturn = null;

		if (!isEmpty()) {
			elementToReturn = this.queue.get(0);
			this.queue.remove(0);
			fullSize--;
			commandList.clear();
			commandList.add(new PollCommand(generateId(), elementToReturn.getValue().toString()));
		}

		return elementToReturn;
	}

	private boolean isEmpty() {
		return (fullSize == 0);
	}

	/**
	 * Retrieves and removes the head of this queue. This method differs from
	 * the poll method in that it throws an exception if this queue is empty.
	 * */
	public Element<Integer> remove() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}

		return poll();
	}

	/**
	 * Retrieves, but does not remove, the head of this queue, returning null if
	 * this queue is empty.
	 * */
	public Element<Integer> peek() {
		Element<Integer> elementToReturn = null;

		if (!isEmpty()) {
			elementToReturn = this.queue.get(0);
			commandList.clear();
			commandList.add(new HighlightCommand(generateId(), elementToReturn.getValue().toString()));
		}

		return elementToReturn;
	}

	/**
	 * Retrieves, but does not remove, the head of this queue. This method
	 * differs from the peek method only in that it throws an exception if this
	 * queue is empty.
	 * */
	public Element<Integer> element() throws NoSuchElementException {
		if (!isEmpty()) {
			throw new NoSuchElementException();
		}

		return this.queue.get(0);
	}

	public void destroy() {
		this.queue.clear();
		this.fullSize = 0;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		Queue<Element<Integer>> queueClone = new Queue<Element<Integer>>(this.capacity);
		
		for (Element<Integer> e : queue)
			queueClone.offer(e);
		
		return queueClone;
	}

}
