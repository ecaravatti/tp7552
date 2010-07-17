package model.structures.stacks;

import java.util.Iterator;

import model.structures.commons.StructureIterator;
import model.structures.commons.StructureNode;

/**
 * Stack data structure implemented using a linked list. Each stack element is of type T.
 * 
 * @author pgorin
 * 
 * @param <T> type of item stored in the queue.
 */
public class LinkedListStackImpl<T> extends StackObservable<T> {

    // Size of the stack.
    private int size;
    // Top node of the stack.
    private StructureNode<T> topNode;

    /**
     * Class constructor. Create an empty stack.
     */
    public LinkedListStackImpl() {
        this.topNode = null;
        this.size = 0;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isEmpty() {
        return this.topNode == null;
    }

    /**
     * {@inheritDoc}
     */
    public int getSize() {
        return this.size;
    }

    /**
     * {@inheritDoc}
     */
    public T peek() {

        if (this.isEmpty()) {
            this.fireEmptyStackCondition();
            throw new EmptyStackException();
        }

        return this.topNode.getItem();
    }

    /**
     * {@inheritDoc}
     */
    public void push(T item) {
        StructureNode<T> oldfirst = this.topNode;

        this.topNode = new StructureNode<T>();
        this.topNode.setItem(item);
        this.topNode.setNextNode(oldfirst);
        this.size++;

        this.fireItemPushed(item);
    }

    /**
     * {@inheritDoc}
     */
    public T pop() {
        T item = this.peek();
        this.topNode = this.topNode.getNextNode();
        this.size--;

        this.fireItemPopped(item);

        return item;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        String s = "";
        for (StructureNode<T> node = this.topNode; node != null; node = node.getNextNode()) {
            s += node.getItem() + ", ";
        }
        return "[ " + s + "]";
    }

    /**
     * {@inheritDoc}
     */
    public Iterator<T> iterator() {
        return new StructureIterator<T>(this.topNode);
    }
}
