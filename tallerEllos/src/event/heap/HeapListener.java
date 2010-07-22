/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package event.heap;

import java.util.EventListener;

/**
 *
 * 
 */
public interface HeapListener<T> extends EventListener {

    /**
     * Event fired when a new item is added.
     * @param item added item.
     */
    public void itemAdded(T item);

    /**
     * Event fired when an item is deleted.
     * @param item deleted item.
     */
    public void itemDeleted(T item);

    /**
     * Event fired when an item is being added.
     * @param item to add
     */
    public void addingItem(T item);

    /**
     * Event fired when an item is being deleted.
     * @param item to delete
     */
    public void deletingItem(T item);

    /**
     * Event fired when two items are swapped.
     * @param item1
     * @param item2
     */
    public void itemsSwapped(Integer item1, Integer item2);
}
