package stack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import collection.stack.Stack;
import command.Command;
import command.HighlightCommand;
import command.stack.PopCommand;

import junit.framework.TestCase;

public class StackTest extends TestCase {
	private Stack stack;

	@Override
	protected void setUp() throws Exception {
		stack = new Stack(5);
	}

	private void fillStack() {
		stack.push(new Integer(1));
		stack.push(new Integer(2));
		stack.push(new Integer(3));
		stack.push(new Integer(4));
		stack.push(new Integer(5));
	}

	private List<Command> fillStackAndListCommands() {
		List<Command> commandList = new ArrayList<Command>();
		commandList.addAll(stack.push(new Integer(1)));
		commandList.addAll(stack.push(new Integer(2)));
		commandList.addAll(stack.push(new Integer(3)));
		commandList.addAll(stack.push(new Integer(4)));
		commandList.addAll(stack.push(new Integer(5)));
		
		return commandList;
	}

	public void testOverflow() {
		assertEquals(1, stack.push(new Integer(1)).size());
		assertEquals(1, stack.push(new Integer(2)).size());
		assertEquals(1, stack.push(new Integer(3)).size());
		assertEquals(1, stack.push(new Integer(4)).size());
		assertEquals(1, stack.push(new Integer(5)).size());
		// This step should not do anything
		assertEquals(0, stack.push(new Integer(6)).size());
	}

	public void testUnderflow() {
		// The stack is filled with 5 elements
		fillStack();
		for (int i = 0; i < 5; i++) {
			assertEquals(1, stack.pop().size());
		}

		// This step should not do anything
		assertEquals(0, stack.pop().size());

	}

	public void testDestroy() {

		fillStack();
		List<Command> commandList = this.stack.destroy();

		assertEquals(5, commandList.size());
	}

	public void testStackBehavior() {
		List<Command> commandList = null;
		// The top of the stack is 1
		stack.push(new Integer(1));

		commandList = stack.peek();
		assertEquals("1", ((HighlightCommand) commandList.get(0)).getContent());

		// The top of the stack is 2
		stack.push(new Integer(2));
		commandList = stack.peek();
		assertEquals("2", ((HighlightCommand) commandList.get(0)).getContent());

		// The top of the stack is 3
		stack.push(new Integer(3));
		commandList = stack.peek();
		assertEquals("3", ((HighlightCommand) commandList.get(0)).getContent());

		// Pop 3
		commandList = stack.pop();
		assertEquals(1, commandList.size());
		assertEquals("3", ((PopCommand) commandList.get(0)).getContent());

		// The top must be 2
		commandList = stack.pop();
		assertEquals(1, commandList.size());
		assertEquals("2", ((PopCommand) commandList.get(0)).getContent());

		// The top must be 1
		commandList = stack.pop();
		assertEquals(1, commandList.size());
		assertEquals("1", ((PopCommand) commandList.get(0)).getContent());

		// The stack is empty
		commandList = stack.pop();
		assertEquals(0, commandList.size());
	}

	private void printCommands(List<Command> commands, String description) {
		System.out.println("---" + description + "---");
		System.out.println("<EXECUTE>");
		for (Command command : commands) {
			System.out.println(command.execute());
		}
		System.out.println("<UNDO>");
		Collections.reverse(commands);
		for (Command command : commands) {
			System.out.println(command.undo());
		}
		System.out.println("------FIN------\n");
	}

	public void testStackBehaviorForView() {
		List<Command> commandList = new ArrayList<Command>();
		printCommands(commandList, "PILA VACÍA");
		
		// The top of the stack is 1
		printCommands(stack.push(new Integer(1)), "INSERTO EL PRIMER ELEMENTO EN LA PILA");
		commandList = stack.peek();
		
		printCommands(commandList, "CONSULTO EL TOPE DE LA PILA");
		assertEquals("1", ((HighlightCommand) commandList.get(0)).getContent());

		// The top of the stack is 2
		printCommands(stack.push(new Integer(2)), "INSERTO OTRO ELEMENTO EN LA PILA");
		commandList = stack.peek();
		
		printCommands(commandList, "CONSULTO EL TOPE DE LA PILA");
		assertEquals("2", ((HighlightCommand) commandList.get(0)).getContent());

		// The top of the stack is 3
		printCommands(stack.push(new Integer(3)), "INSERTO OTRO ELEMENTO EN LA PILA");
		commandList = stack.peek();
		
		printCommands(commandList, "CONSULTO EL TOPE DE LA PILA");
		assertEquals("3", ((HighlightCommand) commandList.get(0)).getContent());

		// Pop 3
		commandList = stack.pop();
		printCommands(commandList, "QUITO UN ELEMENTO DEL TOPE DE LA PILA");
		assertEquals(1, commandList.size());
		assertEquals("3", ((PopCommand) commandList.get(0)).getContent());

		// The top must be 2
		commandList = stack.pop();
		printCommands(commandList, "QUITO OTRO ELEMENTO DEL TOPE DE LA PILA");
		assertEquals(1, commandList.size());
		assertEquals("2", ((PopCommand) commandList.get(0)).getContent());

		// The top must be 1
		commandList = stack.pop();
		printCommands(commandList, "QUITO OTRO ELEMENTO DEL TOPE DE LA PILA");
		assertEquals(1, commandList.size());
		assertEquals("1", ((PopCommand) commandList.get(0)).getContent());

		// The stack is empty
		commandList = stack.pop();
		printCommands(commandList, "LA PILA ESTA VACÍA");
		assertEquals(0, commandList.size());
		
		printCommands(fillStackAndListCommands(), "CARGO LA PILA CON 5 ELEMENTOS");
		printCommands(stack.destroy(), "DESTRUYO LA PILA");
	}

}
