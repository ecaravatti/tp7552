package heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import command.Command;
import common.export.ExportUtils;
import collection.heap.Heap;
import junit.framework.TestCase;

public class MaxHeapIntegerTest extends TestCase {
	
	private Heap<Integer> heap;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		heap = new Heap<Integer>(false);
		heap.insert(new Integer(10));
		heap.insert(new Integer(15));
		heap.insert(new Integer(80));
		heap.insert(new Integer(33));
		heap.insert(new Integer(25));
		heap.insert(new Integer(29));
		ExportUtils.exportToXML(heap, "maxHeapIntegerheap.xml");
	}
	
	public void testExport(){
		heap.insert(new Integer(9));	
		heap.insert(new Integer(88));
		
		ExportUtils.exportToXML(heap, "maxHeapInteger.xml");
	}
	
	public void testInsert() {		
		heap.insert(new Integer(12600));		
		for (int i = 0; i < 10000; i++){
			heap.insert(new Integer((int)Math.random()*10000));
		}
		
		assertEquals(new Integer(12600), heap.peek());
		
		heap.insert(new Integer(15000));
		assertEquals(new Integer(15000), heap.peek());
	}

	public void testPeek() {
		assertEquals(new Integer(80), heap.peek());
		assertEquals(new Integer(80), heap.peek());
		
		heap.insert(new Integer(81));
		assertEquals(new Integer(81), heap.peek());
		
		heap.remove();
		assertEquals(new Integer(80), heap.peek());
	}

	public void testRemove() {
		assertEquals(new Integer(80), heap.remove());
		assertEquals(new Integer(33), heap.remove());
		assertEquals(new Integer(29), heap.remove());
		assertEquals(new Integer(25), heap.remove());
		assertEquals(new Integer(15), heap.remove());
		assertEquals(new Integer(10), heap.remove());
		
		try{
			heap.remove();
			fail("remove() debio haber lanzado NoSuchElementException");
		} catch (NoSuchElementException e){
			// Test correcto. Debe lanzar excepcion.
		}
		
		for (int i = 0; i < 10000; i++){
			heap.insert(new Integer((int)Math.random()*10000));
		}
		
		for (int i = 0; i < 9999; i++){
			heap.pop();
		}
		
		assertEquals(1, heap.size());		
	}
	
	public void testCommand(){
		heap.clear();
		heap.insert(new Integer(10));
		Queue<Command> commands = heap.getCommandQueue();
		printCommands(commands, "INSERT 10");
		
		heap.insert(new Integer(20));
		commands = heap.getCommandQueue();		
		printCommands(commands, "INSERT 20");
		
		heap.insert(new Integer(35));
		commands = heap.getCommandQueue();
		printCommands(commands, "INSERT 35");
		
		heap.pop();
		commands = heap.getCommandQueue();
		printCommands(commands, "REMOVE ROOT");	
		
		heap.insert(new Integer(22));
		commands = heap.getCommandQueue();
		printCommands(commands, "INSERT 22");
		
		heap.insert(new Integer(33));
		commands = heap.getCommandQueue();
		printCommands(commands, "INSERT 33");
		
		heap.pop();
		commands = heap.getCommandQueue();
		printCommands(commands, "REMOVE ROOT");	

	}
	
	private void printCommands(Queue<Command> commands, String description){
		List<Command> commandsList = new ArrayList<Command>(commands); 
		
		System.out.println("------" + description + "------");
		System.out.println("// EXECUTE");
		for (Command command : commandsList) {
			System.out.println(command.execute());
		}
		System.out.println("// UNDO");
		
		Collections.reverse(commandsList);
		for (Command command : commands) {
			System.out.println(command.undo());
		}
		System.out.println("-------END-------\n");
	}
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		heap.clear();
	}

}
