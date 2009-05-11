package collection.queue;

import java.util.ArrayList;
import java.util.List;

import command.Command;
import command.HighlightCommand;
import command.queue.OfferCommand;
import command.queue.PollCommand;
import common.Element;

public class Queue implements Cloneable {

	private final static int DEFAULT_CAPACITY = 8;
	private int capacity;
	private List<Element<Integer>> queue;
	private int fullSize = 0;
	private int elementsCount = 0;
	
	public Queue(int capacity) {
		this.capacity = capacity;
		queue = new ArrayList<Element<Integer>>(capacity);
	}

	public Queue() {
		queue = new ArrayList<Element<Integer>>(DEFAULT_CAPACITY);
		capacity = DEFAULT_CAPACITY;
	}

	/**
	 * Inserts the specified element into this queue, if possible.
	 * */
	public List<Command> offer(Integer value) {
		
		List<Command> commandList = new ArrayList<Command>();
		
		if (canInsert()) {
			Element<Integer> element = new Element<Integer>(value, elementsCount);
			elementsCount++;
			this.queue.add(element);
			commandList.add(new OfferCommand(element.getId(), element.getValue().toString()));
			fullSize++;
		}
		
		return commandList;
	}

	private boolean canInsert() {
		return (fullSize < capacity);
	}

	/**
	 * Retrieves and removes the head of this queue, or null if this queue is
	 * empty.
	 * */
	public List<Command> poll() {
		List<Command> commandList = new ArrayList<Command>();

		if (!isEmpty()) {
			Element<Integer> element = this.queue.get(0);
			this.queue.remove(0);
			Command pollCommand = new PollCommand(element.getId(), element.getValue().toString());
			commandList.add(pollCommand);
			fullSize--;
		}

		return commandList;
	}

	private boolean isEmpty() {
		return (fullSize == 0);
	}

	 /**
	 * Retrieves, but does not remove, the head of this queue, returning null if
	 * this queue is empty.
	 * */
	public List<Command> peek() {
		List<Command> commandList = new ArrayList<Command>();

		if (!isEmpty()) {
			Element<Integer> element = this.queue.get(0);
			Command highlightCommand = new HighlightCommand(element.getId(), element.getValue().toString());
			commandList.add(highlightCommand);
		}

		return commandList;
	}


	public List<Command> destroy() {
		List<Command> commandList = new ArrayList<Command>();
		for(int i = 0; i < capacity; i++){
			commandList.addAll(this.poll());
		}
		
		this.fullSize = 0;
		
		return commandList;
	}
	
	public int size() {
		return queue.size();
	}
	
	@Override
	public Queue clone() throws CloneNotSupportedException {
		Queue clone = new Queue(this.capacity);
		
		for (Element<Integer> element : this.queue) {
			clone.queue.add(element);
		}
		
		clone.elementsCount = this.elementsCount;
		clone.fullSize = this.fullSize;
		
		return clone;
	}

}
