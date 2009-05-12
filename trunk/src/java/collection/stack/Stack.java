package collection.stack;

import java.util.ArrayList;
import java.util.List;

import command.Command;
import command.HighlightCommand;
import command.stack.PopCommand;
import command.stack.PushCommand;
import common.Element;

public class Stack implements Cloneable {

	private final static int DEFAULT_CAPACITY = 8;
	private int top = -1;
	private int capacity;
	private List<Element<Integer>> stack;
	private int elementsCount = 0;

	public Stack() {
		stack = new ArrayList<Element<Integer>>(DEFAULT_CAPACITY);
	}

	public List<Command> destroy() {
		List<Command> commandList = new ArrayList<Command>();
		for(int i = 0; i < capacity; i++){
			commandList.addAll(this.pop());
		}
		this.top = -1;
		
		return commandList;
	}

	public Stack(int capacity) {
		this.capacity = capacity;
		stack = new ArrayList<Element<Integer>>(capacity);
	}

	/**
	 * Pushes an item onto the top of this stack.
	 * */
	public List<Command> push(Integer value) {
		List<Command> commandList = new ArrayList<Command>();
		
		if (!isFull()) {
			Element<Integer> element = new Element<Integer>(value, elementsCount);
			elementsCount++;
			stack.add(element);
			top++;
			Command pushCommand = new PushCommand(element.getId(), element.getValue().toString());
			commandList.add(pushCommand);
		}
		
		return commandList;
	}

	/**
	 * Looks at the object at the top of this stack without removing it from the
	 * stack.
	 * */
	public List<Command> peek() {
		List<Command> commandList = new ArrayList<Command>();

		if (!isEmpty()) {
			Element<Integer> element = stack.get(top);
			Command highlightCommand = new HighlightCommand(element.getId(),
					element.getValue().toString());
			commandList.add(highlightCommand);
		}

		return commandList;
	}

	/**
	 * Removes the object at the top of this stack and returns that object as
	 * the value of this function.
	 * */
	public List<Command> pop() {
		List<Command> commandList = new ArrayList<Command>();

		if (!isEmpty()) {
			Element<Integer> element = stack.get(top);
			stack.remove(top);
			top--;
			Command popCommand = new PopCommand(element.getId(), element
					.getValue().toString());
			commandList.add(popCommand);
		}

		return commandList;
	}

	private boolean isEmpty() {
		return (top == -1);
	}

	private boolean isFull() {
		return (top == (capacity - 1));
	}
	
	@Override
	public Stack clone() throws CloneNotSupportedException {
		Stack clone = new Stack(this.capacity);
		for (Element<Integer> element : this.stack) {
			clone.stack.add(element);
		}
		clone.top = this.top;
		clone.elementsCount = this.elementsCount;
		return clone;
	}
	
	public int size() {
		return stack.size();
	}
}
