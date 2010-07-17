package model.structures.queues;

import java.util.Iterator;

import model.structures.stacks.EmptyStackException;
import org.junit.Assert;
import org.junit.Test;

public class LinkedListQueueImplTest {

    private static final Integer QUEUE_ITEM_1 = 1;
    private static final Integer QUEUE_ITEM_2 = 2;

    @Test
    public void testIsEmptyUsingTrueCondition() {
        LinkedListQueueImpl<Integer> queue = new LinkedListQueueImpl<Integer>();
        Assert.assertTrue(queue.isEmpty());
    }

    @Test
    public void testIsEmptyUsingFalseCondition() {
        LinkedListQueueImpl<Integer> queue = new LinkedListQueueImpl<Integer>();
        queue.enqueue(QUEUE_ITEM_1);

        Assert.assertFalse(queue.isEmpty());
    }

    @Test
    public void testGetLength() {
        LinkedListQueueImpl<Integer> queue = new LinkedListQueueImpl<Integer>();
        queue.enqueue(QUEUE_ITEM_1);

        Assert.assertEquals(1, queue.getLength());
    }

    @Test
    public void testEnqueue() {
        LinkedListQueueImpl<Integer> queue = new LinkedListQueueImpl<Integer>();

        queue.enqueue(QUEUE_ITEM_1);
        Assert.assertEquals(1, queue.getLength());

        queue.enqueue(QUEUE_ITEM_2);
        Assert.assertEquals(2, queue.getLength());
    }

    @Test
    public void testDequeueUsingEmptyQueue() {
        LinkedListQueueImpl<Integer> queue = new LinkedListQueueImpl<Integer>();

        try {
            queue.dequeue();
            Assert.fail();
        } catch (EmptyQueueException e) {
        } catch (Exception e) {
            Assert.fail(e.getClass().getName());
        }
    }

    @Test
    public void testDequeue() {
        LinkedListQueueImpl<Integer> queue = new LinkedListQueueImpl<Integer>();
        queue.enqueue(QUEUE_ITEM_1);
        queue.enqueue(QUEUE_ITEM_2);

        Assert.assertEquals(QUEUE_ITEM_1, queue.dequeue());
        Assert.assertEquals(1, queue.getLength());
        Assert.assertEquals(QUEUE_ITEM_2, queue.dequeue());
        Assert.assertEquals(0, queue.getLength());
    }

    @Test
    public void testIterator() {
        LinkedListQueueImpl<Integer> queue = new LinkedListQueueImpl<Integer>();

        Iterator<Integer> iterator = queue.iterator();

        Assert.assertNotNull(iterator);
    }

}
