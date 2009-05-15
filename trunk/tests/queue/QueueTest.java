package queue;

import java.util.List;
import collection.queue.Queue;
import junit.framework.TestCase;
import command.Command;
import command.HighlightCommand;
import command.queue.PollCommand;

public class QueueTest extends TestCase {

	private Queue queue;

	public QueueTest() {
	}

	public QueueTest(String name) {
		super(name);
	}

	private void printCommands(List<Command> commands, String description) {
		System.out.println("---" + description + "---");
		System.out.println("<EXECUTE>");
		for (Command command : commands) {
			System.out.println(command.execute());
		}
		System.out.println("<UNDO>");
		for (Command command : commands) {
			System.out.println(command.undo());
		}
		System.out.println("------FIN------\n");
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
		List<Command> commandList = null;
		// Insert order is 1,2,3,4,5
		// The queue head at this moment is 1
		commandList = queue.poll();
		assertEquals("1", ((PollCommand) commandList.get(0)).getContent());
		commandList = queue.poll();
		// The queue head at this moment is 2
		assertEquals("2", ((PollCommand) commandList.get(0)).getContent());
		commandList = queue.poll();
		// The queue head at this moment is 3
		assertEquals("3", ((PollCommand) commandList.get(0)).getContent());
		commandList = queue.poll();
		// The queue head at this moment is 4
		assertEquals("4", ((PollCommand) commandList.get(0)).getContent());
		// The queue head at this moment is 5
		commandList = queue.poll();
		assertEquals("5", ((PollCommand) commandList.get(0)).getContent());

		// The queue is empty
		assertEquals(0, queue.poll().size());

		// Insert a single element
		queue.offer(new Integer(1));
		// The queue head at this moment is 1
		commandList = queue.peek();
		assertEquals("1", ((HighlightCommand) commandList.get(0)).getContent());
		// Insert a single element
		queue.offer(new Integer(2));
		// The queue head at this moment is still 1
		commandList = queue.peek();
		assertEquals("1", ((HighlightCommand) commandList.get(0)).getContent());
		// Insert a single element
		queue.offer(new Integer(3));
		// The queue head at this moment is still 1
		commandList = queue.peek();
		assertEquals("1", ((HighlightCommand) commandList.get(0)).getContent());

		// Remove the current head
		commandList = queue.poll();

		// The queue head at this moment is 3
		assertEquals("1", ((PollCommand) commandList.get(0)).getContent());

		commandList = queue.peek();
		assertEquals("2", ((HighlightCommand) commandList.get(0)).getContent());

		commandList = queue.poll();
		commandList = queue.poll();
		assertEquals("3", ((PollCommand) commandList.get(0)).getContent());

		commandList = queue.poll();
		// The queue is empty
		assertEquals(0, commandList.size());
	}

	public void testQueueBehaviorForView() {
		// The queue is filled with 5 elements
		// Insert order is 1,2,3,4,5
		List<Command> commandList = fillQueueAndReturnCommands();
		printCommands(commandList, "--- CARGO LA COLA CON 5 ELEMENTOS ---");

		// The queue head at this moment is 1
		commandList = queue.poll();
		assertEquals("1", ((PollCommand) commandList.get(0)).getContent());
		printCommands(commandList, " QUITO EL ELEMENTO DEL FRENTE DE LA COLA ");

		commandList = queue.poll();
		printCommands(commandList, " QUITO EL ELEMENTO DEL FRENTE DE LA COLA ");
		// The queue head at this moment is 2
		assertEquals("2", ((PollCommand) commandList.get(0)).getContent());
		commandList = queue.poll();
		printCommands(commandList,
				" REMUEVO EL ELEMENTO DEL FRENTE DE LA COLA ");

		// The queue head at this moment is 3
		assertEquals("3", ((PollCommand) commandList.get(0)).getContent());
		commandList = queue.poll();
		printCommands(commandList,
				" REMUEVO EL ELEMENTO DEL FRENTE DE LA COLA ");
		// The queue head at this moment is 4
		assertEquals("4", ((PollCommand) commandList.get(0)).getContent());
		commandList = queue.poll();
		printCommands(commandList,
				" REMUEVO EL ULTIMO ELEMENTO DEL FRENTE DE LA COLA ");
		// The queue head at this moment is 5
		assertEquals("5", ((PollCommand) commandList.get(0)).getContent());
		// The queue is empty
		assertEquals(0, queue.peek().size());
		System.out.println("--- LA COLA ESTA VACIA ---");

		// Insert a single element
		printCommands(queue.offer(new Integer(1)),
				"INSERTO UN NUEVO ELEMENTO A LA COLA");
		// The queue head at this moment is 1
		commandList = queue.peek();
		printCommands(commandList, "CONSULTO EL FRENTE DE LA COLA");
		assertEquals("1", ((HighlightCommand) commandList.get(0)).getContent());
		// Insert a single element
		printCommands(queue.offer(new Integer(2)),
				"INSERTO UN NUEVO ELEMENTO A LA COLA");
		// The queue head at this moment is still 1
		commandList = queue.peek();
		printCommands(commandList, "CONSULTO EL FRENTE DE LA COLA");
		assertEquals("1", ((HighlightCommand) commandList.get(0)).getContent());
		// Insert a single element
		printCommands(queue.offer(new Integer(3)),
				"INSERTO UN NUEVO ELEMENTO A LA COLA");
		// The queue head at this moment is still 1
		commandList = queue.peek();
		printCommands(commandList, "CONSULTO EL FRENTE DE LA COLA");
		assertEquals("1", ((HighlightCommand) commandList.get(0)).getContent());

		// Remove the current head
		commandList = queue.poll();
		printCommands(commandList, "REMUEVO EL ELEMNTO DEL FRENTE DE LA COLA");

		// The queue head at this moment is 3
		assertEquals("1", ((PollCommand) commandList.get(0)).getContent());

		commandList = queue.peek();
		printCommands(commandList, "CONSULTO EL FRENTE DE LA COLA");
		assertEquals("2", ((HighlightCommand) commandList.get(0)).getContent());

		commandList = queue.poll();
		printCommands(commandList, "REMUEVO EL ELEMENTO DEL FRENTE DE LA COLA");
		printCommands(queue.peek(), "CONSULTO EL FRENTE DE LA COLA");
		commandList = queue.poll();
		printCommands(commandList, "REMUEVO EL ELEMENTO DEL FRENTE DE LA COLA");
		printCommands(
				queue.peek(),
				"CONSULTO EL FRENTE DE LA COLA, LA MISMA ESTA VACÍA Y LOS COMANDOS DEBEN SER VACÍOS");
		assertEquals("3", ((PollCommand) commandList.get(0)).getContent());

		System.out.println("LA COLA ESTA VACÍA");
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
		// Fill queue with 5 elements
		List<Command> commandList = fillQueueAndReturnCommands();
		printCommands(commandList, "LA COLA FUE CREADA");
		commandList = queue.destroy();
		printCommands(commandList, "LA COLA FUE DESTRUIDA");
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

	private List<Command> fillQueueAndReturnCommands() {
		List<Command> commandList = null;
		commandList = queue.offer(new Integer(1));
		commandList.addAll(queue.offer(new Integer(2)));
		commandList.addAll(queue.offer(new Integer(3)));
		commandList.addAll(queue.offer(new Integer(4)));
		commandList.addAll(queue.offer(new Integer(5)));

		return commandList;
	}

}
