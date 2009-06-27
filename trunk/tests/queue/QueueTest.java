//package queue;
//
//import java.util.NoSuchElementException;
//
//import junit.framework.TestCase;
//import collection.queue.Queue;
//
//import common.Element;
//
//public class QueueTest extends TestCase {
//
//	private Queue<Element<Integer>> queue;
//
//	public QueueTest() {
//		// TODO Auto-generated constructor stub
//	}
//
//	public QueueTest(String name) {
//		super(name);
//		// TODO Auto-generated constructor stub
//	}
//
//	public void testQueue() {
//		queue.offer(new Element<Integer>(1,1));
//		queue.offer(new Element<Integer>(2,2));
//		queue.offer(new Integer(3));
//		queue.offer(new Integer(4));
//
//	}
//
//	public void testQueueBehavior() {
//		// The queue is filled with 5 elements
//		fillQueue();
//
//		// Insert order is 1,2,3,4,5
//		// The queue head at this moment is 1
//		assertEquals(1, queue.poll().intValue());
//		// The queue head at this moment is 2
//		assertEquals(2, queue.poll().intValue());
//		// The queue head at this moment is 3
//		assertEquals(3, queue.poll().intValue());
//		// The queue head at this moment is 4
//		assertEquals(4, queue.poll().intValue());
//		// The queue head at this moment is 5
//		assertEquals(5, queue.poll().intValue());
//
//		// The queue is empty
//		assertEquals(null, queue.poll());
//
//		// Insert a single element
//		queue.offer(new Integer(1));
//		// The queue head at this moment is 1
//		assertEquals(1, queue.peek().intValue());
//		// Insert a single element
//		queue.offer(new Integer(2));
//		// The queue head at this moment is still 1
//		assertEquals(1, queue.peek().intValue());
//		// Insert a single element
//		queue.offer(new Integer(3));
//		// The queue head at this moment is still 1
//		assertEquals(1, queue.peek().intValue());
//
//		// Remove the current head
//		queue.remove();
//
//		// The queue head at this moment is 3
//		assertEquals(2, queue.poll().intValue());
//		// Remove the current head
//		queue.remove();
//
//		// The queue is empty
//		assertEquals(null, queue.poll());
//
//	}
//
//	public void testOverflow() {
//		assertTrue(queue.offer(new Integer(1)));
//		assertTrue(queue.offer(new Integer(2)));
//		assertTrue(queue.offer(new Integer(3)));
//		assertTrue(queue.offer(new Integer(4)));
//		assertTrue(queue.offer(new Integer(5)));
//
//		// This step should fail
//		assertFalse(queue.offer(new Integer(6)));
//
//	}
//
//	public void testUnderflow() {
//		// The queue is filled with 5 elements
//		fillQueue();
//		for (int i = 0; i < 5; i++) {
//			queue.remove();
//		}
//		try {
//			queue.remove();
//			fail("The NoSuchElementException was not thrown");
//		} catch (NoSuchElementException e) {
//			// Expected exception
//		}
//
//	}
//
//	public void testDestroy() {
//		fillQueue();
//		this.queue.destroy();
//		assertEquals(null, this.queue.peek());
//
//	}
//
//	@Override
//	protected void setUp() throws Exception {
//		queue = new Queue<Integer>(5);
//	}
//
//	private void fillQueue() {
//		queue.offer(new Integer(1));
//		queue.offer(new Integer(2));
//		queue.offer(new Integer(3));
//		queue.offer(new Integer(4));
//		queue.offer(new Integer(5));
//	}
//
//}
