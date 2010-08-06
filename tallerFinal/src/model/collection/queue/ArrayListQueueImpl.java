package model.collection.queue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import model.exception.queue.QueueFullException;

public class ArrayListQueueImpl<T> extends QueueObservable<T> {

	private ArrayList<T> queue;
	private int fullSize = 0;
	private int capacity = 1000000;

	public ArrayListQueueImpl() {
		queue = new ArrayList<T>();
	}

	public boolean isEmpty() {
		return (fullSize == 0);
	}

	public int getLength() {
		return queue.size();
	}

	public void enqueue(T item) throws QueueFullException {
		if (!isFull()) {
			this.queue.add(item);
			fullSize++;

			this.fireItemEnqueued(item);
		} else {
			throw new QueueFullException();
		}
	}

	public T dequeue() {
		if (this.isEmpty()) {
			this.fireEmptyQueueCondition();
			throw new NoSuchElementException();
		}

		T itemToReturn = this.queue.get(0);
		this.queue.remove(0);
		fullSize--;

		this.fireItemDequeued(itemToReturn);

		return itemToReturn;
	}

	public Iterator<T> iterator() {
		return queue.iterator();
	}

	@Override
	public String toString() {
		String s = "";

		for (T item : queue) {
			s += item + " ";
		}

		return s;
	}

	@Override
	public void clear() {
		queue = new ArrayList<T>();
		fullSize = 0;
	}

	@Override
	public int getCapacity() {
		return capacity;
	}

	@Override
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	@Override
	public boolean isFull() {
		return (queue.size() == capacity);
	}

}
