package model.collection.queue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class ArrayListQueueImpl<T> extends QueueObservable<T> {

	private ArrayList<T> queue;
	private int fullSize = 0;

    public ArrayListQueueImpl() {
		queue = new ArrayList<T>();
    }

    public boolean isEmpty() {
    	return (fullSize == 0);
    }

    public int getLength() {
        return queue.size();
    }

    public void enqueue(T item) {
		this.queue.add(item);
		fullSize++;

        this.fireItemEnqueued(item);
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

}
