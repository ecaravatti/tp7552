package heap;

import java.util.NoSuchElementException;
import common.export.ExportUtils;
import collection.heap.Heap;
import junit.framework.TestCase;

public class MaxHeapStringTest extends TestCase {
	private Heap<String> heap;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		heap = new Heap<String>(false);
		heap.insert(new String("AA"));
		heap.insert(new String("AB"));
		heap.insert(new String("CA"));
		heap.insert(new String("AC"));
		heap.insert(new String("BB"));
		heap.insert(new String("BA"));
		ExportUtils.exportToXML(heap, "maxHeapString.xml");
	}
	
	public void testExport(){
		heap.insert(new String("OO"));	
		heap.insert(new String("PO"));	
		
		ExportUtils.exportToXML(heap, "maxHeapString.xml");
	}
	
	public void testInsert() {		
		heap.insert(new String("CC"));
		Integer random;
		
		for (int i = 0; i < 10000; i++){
			random = (int)(Math.random()*10000);
			heap.insert(new String("A" + random.toString()));
		}
		
		assertEquals(new String("CC"), heap.peek());
		
		heap.insert(new String("DC"));
		assertEquals(new String("DC"), heap.peek());
	}

	public void testPeek() {
		assertEquals(new String("CA"), heap.peek());
		assertEquals(new String("CA"), heap.peek());
		
		heap.insert(new String("X"));
		assertEquals(new String("X"), heap.peek());
		
		heap.remove();
		assertEquals(new String("CA"), heap.peek());
	}

	public void testRemove() {
		assertEquals(new String("CA"), heap.remove());
		assertEquals(new String("BB"), heap.remove());
		assertEquals(new String("BA"), heap.remove());
		assertEquals(new String("AC"), heap.remove());
		assertEquals(new String("AB"), heap.remove());
		assertEquals(new String("AA"), heap.remove());
		
		try{
			heap.remove();
			fail("remove() debio haber lanzado NoSuchElementException");
		} catch (NoSuchElementException e){
			// Test correcto. Debe lanzar excepcion.
		}
		
		Integer random;
		for (int i = 0; i < 10000; i++){
			random = (int)(Math.random()*10000);
			heap.insert(new String(random.toString()));
		}
		
		for (int i = 0; i < 9999; i++){
			heap.pop();
		}
		
		assertEquals(1, heap.size());		
	}
	
//	public void testCommand(){
//		heap.clear();
//		heap.insert(new String(10));
//		List<Command> commandList = heap.getCommandList();
//		
//		System.out.println(commandList.get(0).execute());
//		
//		heap.insert(new String(20));
//		commandList = heap.getCommandList();
//		
//		for (Command c : commandList){
//			System.out.println(c.execute());
//		}
//		
//		heap.insert(new String(35));
//		commandList = heap.getCommandList();
//		
//		for (Command c : commandList){
//			System.out.println(c.execute());
//		}
//		
//		heap.remove();
//		commandList = heap.getCommandList();
//		
//		for (Command c : commandList){
//			System.out.println(c.execute());
//		}
//	}
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		heap.clear();
	}

}
