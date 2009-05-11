package stack;

import java.util.EmptyStackException;

import collection.stack.Stack;

import common.export.ExportUtils;

import junit.framework.TestCase;

public class StackTest extends TestCase {
	private Stack stack;

	@Override
	protected void setUp() throws Exception {
		stack = new Stack(5);
	}

	private void fillStack() throws Exception {
		stack.push(new Integer(1));
		stack.push(new Integer(2));
		stack.push(new Integer(3));
		stack.push(new Integer(4));
		stack.push(new Integer(5));
	}

	public void testOverflow() {
		try {
			// The stack size is 5
			fillStack();
			ExportUtils.exportToXML(stack, "stack.xml");
		} catch (Exception e) {
		}
//		try {
//			stack.push(new Integer(6));
//			fail("The Exception was not thrown");
//		} catch (Exception e) {
//			// The expected exception was thrown
//		}
	}

	public void testUnderflow() {
		try {
			fillStack();
		} catch (Exception e) {
		}
		// The stack is filled with 5 elements
		for (int i = 0; i < 5; i++) {
			stack.pop();
		}
		try {
			stack.pop();
			fail("The EmptyStackException was not thrown");
		} catch (EmptyStackException e) {
			// The expected exception was thrown
		}
	}

	public void testDestroy(){
		try {
			fillStack();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.stack.destroy();
		try{
			this.stack.pop();
			fail("The expected exception was not thrown");
		}catch(EmptyStackException e){
			// Expected exception
		}
	}
	
	public void testStackBehavior() {
//		// The top of the stack is 1
//		try {
//			stack.push(new Integer(1));
//		} catch (Exception e) {
//		}
//
//		assertEquals(1, stack.peek().intValue());
//
//		// The top of the stack is 2
//		try {
//			stack.push(new Integer(2));
//		} catch (Exception e) {
//		}
//
//		assertEquals(2, stack.peek().intValue());
//
//		// The top of the stack is 3
//		try {
//			stack.push(new Integer(3));
//		} catch (Exception e) {
//		}
//
//		assertEquals(3, stack.peek().intValue());
//
//		// Pop 3
//		assertEquals(3, stack.pop().intValue());
//		// The top must be 2
//		assertEquals(2, stack.pop().intValue());
//		// The top must be 1
//		assertEquals(1, stack.pop().intValue());
//
//		// The stack is empty
//		try {
//			stack.pop();
//			fail("The EmptyStackException was not thrown");
//		} catch (EmptyStackException e) {
//			// EmptyStackException was thrown
//		}

	}

}
