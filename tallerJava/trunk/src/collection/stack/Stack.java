package collection.stack;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

import command.Command;
import command.HighlightCommand;
import command.stack.PopCommand;
import command.stack.PushCommand;
import common.Element;

public class Stack<T> implements Cloneable {

	private final static int DEFAULT_CAPACITY = 8;
	private int top = -1;
	private int capacity;
	private ArrayList<Element<Integer>> stack;
	private List<Command> commandList;
	private int idGenerator = 0; 
	
	public Stack() {
		stack = new ArrayList<Element<Integer>>(DEFAULT_CAPACITY);
		commandList = new ArrayList<Command>();
	}
	
	public List<Command> getCommands(){
		return commandList;
	}
	
	private int generateId(){
		return idGenerator++;
	}

	
	public void destroy() {
		stack.clear();
		this.top = -1;
	}

	public Stack(int capacity) {
		this.capacity = capacity;
		stack = new ArrayList<Element<Integer>>(capacity);
		commandList = new ArrayList<Command>();
	}

	/**
	 * Pushes an item onto the top of this stack.
	 * */
	public void push(Element<Integer> element) throws Exception {
		if (isFull()) {
			throw new Exception();
		} else {
			stack.add(element);
			top++;
			commandList.clear();
			commandList.add(new PushCommand(generateId(), element.getValue().toString()));
		}
	}

	/**
	 * Looks at the object at the top of this stack without removing it from the
	 * stack.
	 * */
	public Element<Integer> peek() {
		if (isEmpty()) {
			throw new EmptyStackException();
		} else {
			Element<Integer> element = stack.get(top);
			commandList.clear();
			commandList.add(new HighlightCommand(generateId(), element.getValue().toString()));
			return element;
		}
	}

	/**
	 * Removes the object at the top of this stack and returns that object as
	 * the value of this function.
	 * */
	public Element<Integer> pop() throws EmptyStackException {
		if (isEmpty()) {
			throw new EmptyStackException();
		} else {
			Element<Integer> element = stack.get(top);
			stack.remove(top);
			top--;
			commandList.clear();
			commandList.add(new PopCommand(generateId(), element.getValue().toString()));		
			
			return element;
		}
	}

	private boolean isEmpty() {
		return (top == -1);
	}

	private boolean isFull() {
		return (top == (capacity - 1));
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		Stack<Element<Integer>> stackClone = new Stack<Element<Integer>>(this.capacity);
		
		for (Element<Integer> e : stack)
			try {
				stackClone.push(e);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		
		return stackClone;
	}
}
