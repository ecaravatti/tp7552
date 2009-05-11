package queue;

import java.util.List;

import collection.queue.Queue;

import junit.framework.TestCase;

import command.Command;
import command.HighlightCommand;
import command.queue.PollCommand;
import common.export.ExportUtils;

public class QueueTest extends TestCase {

	private Queue queue;

	public QueueTest() {
		// TODO Auto-generated constructor stub
	}

	public QueueTest(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public void testQueue() {
		queue.offer(new Integer(1));
		queue.offer(new Integer(2));
		queue.offer(new Integer(3));
		queue.offer(new Integer(4));

	}

	public void testQueueBehavior() {
		// The queue is filled with 5 elements
		fillQueue();
		ExportUtils.exportToXML(queue, "queue.xml");
		List<Command> commandList = null;
		// Insert order is 1,2,3,4,5
		// The queue head at this moment is 1
		commandList = queue.poll();
		assertEquals("1", ((PollCommand)commandList.get(0)).getContent());
		commandList = queue.poll();
		// The queue head at this moment is 2
		assertEquals("2", ((PollCommand)commandList.get(0)).getContent());
		commandList = queue.poll();
		// The queue head at this moment is 3
		assertEquals("3", ((PollCommand)commandList.get(0)).getContent());
		commandList = queue.poll();
		// The queue head at this moment is 4
		assertEquals("4", ((PollCommand)commandList.get(0)).getContent());
		// The queue head at this moment is 5
		commandList = queue.poll();
		assertEquals("5", ((PollCommand)commandList.get(0)).getContent());

		// The queue is empty
		assertEquals(0, queue.poll().size());

		// Insert a single element
		queue.offer(new Integer(1));
		// The queue head at this moment is 1
		commandList = queue.peek();
		assertEquals("1", ((HighlightCommand)commandList.get(0)).getContent());
		// Insert a single element
		queue.offer(new Integer(2));
		// The queue head at this moment is still 1
		commandList = queue.peek();
		assertEquals("1", ((HighlightCommand)commandList.get(0)).getContent());
		// Insert a single element
		queue.offer(new Integer(3));
		// The queue head at this moment is still 1
		commandList = queue.peek();
		assertEquals("1", ((HighlightCommand)commandList.get(0)).getContent());

		// Remove the current head
		commandList = queue.poll();

		// The queue head at this moment is 3
		assertEquals("1", ((PollCommand)commandList.get(0)).getContent());
		
		commandList = queue.peek();
		assertEquals("2", ((HighlightCommand)commandList.get(0)).getContent());
		
		commandList = queue.poll();
		commandList = queue.poll();
		assertEquals("3", ((PollCommand)commandList.get(0)).getContent());
		
		commandList = queue.poll();
		// The queue is empty
		assertEquals(0, commandList.size());
	}

	public void testOverflow() {
		
		assertEquals(1, queue.offer(new Integer(1)).size());
		assertEquals(1, queue.offer(new Integer(2)).size());
		assertEquals(1, queue.offer(new Integer(3)).size());
		assertEquals(1, queue.offer(new Integer(4)).size());
		assertEquals(1, queue.offer(new Integer(5)).size());
		// This step should not do anything
		assertEquals(0, queue.offer(new Integer(6)).size());

	}

	public void testUnderflow() {
		// The queue is filled with 5 elements
		fillQueue();
		for (int i = 0; i < 5; i++) {
			assertEquals(1, queue.poll().size());
		}
		
		// This step should not do anything
		assertEquals(0, queue.poll().size());

	}

	public void testDestroy() {
		fillQueue();
		List<Command> commandList = this.queue.destroy();
		
		assertEquals(5, commandList.size());

	}

	@Override
	protected void setUp() throws Exception {
		queue = new Queue(5);
	}

	private void fillQueue() {
		queue.offer(new Integer(1));
		queue.offer(new Integer(2));
		queue.offer(new Integer(3));
		queue.offer(new Integer(4));
		queue.offer(new Integer(5));
	}

}
