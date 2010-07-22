package event.queue;

import java.util.EventListener;

/**
 * Queue data structure listener.
 * @author pgorin
 */
public interface QueueListener<T> extends EventListener {

    /**
     * Event fired when a dequeue operation is performed and the queue is empty.
     */
    public void emptyQueueCondition();

    /**
     * Event fired when a new item is enqueued into the queue.
     * @param item enqueued item.
     */
    public void itemEnqueued(T item);

    /**
     * Event fired when the first item in the queue is dequeued.
     * @param item deuqued item.
     */
    public void itemDequeued(T item);
}
