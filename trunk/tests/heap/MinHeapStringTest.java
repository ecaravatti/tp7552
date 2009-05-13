package heap;

import java.util.NoSuchElementException;

import collection.heap.Heap;
import junit.framework.TestCase;

public class MinHeapStringTest extends TestCase {
	private Heap<String> heap;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		heap = new Heap<String>(true);
		heap.insert(new String("AA"));
		heap.insert(new String("AB"));
		heap.insert(new String("CA"));
		heap.insert(new String("AC"));
		heap.insert(new String("BB"));
		heap.insert(new String("BA"));
		//ExportUtils.exportToXML(heap, "inicialHeap.xml");
	}
	
	public void testInsert() {		
		heap.insert(new String("A"));
		Integer random;
		
		for (int i = 0; i < 10000; i++){
			random = (int)(Math.random()*10000);
			heap.insert(new String("B" + random.toString()));
		}
		
		assertEquals(new String("A"), heap.peek());
		
		heap.insert(new String("0"));
		assertEquals(new String("0"), heap.peek());
	}

	public void testPeek() {
		assertEquals(new String("AA"), heap.peek());
		assertEquals(new String("AA"), heap.peek());
		
		heap.insert(new String("0A"));
		assertEquals(new String("0A"), heap.peek());
		
		heap.remove();
		assertEquals(new String("AA"), heap.peek());
	}

	public void testRemove() {
		assertEquals(new String("AA"), heap.remove());
		assertEquals(new String("AB"), heap.remove());
		assertEquals(new String("AC"), heap.remove());
		assertEquals(new String("BA"), heap.remove());
		assertEquals(new String("BB"), heap.remove());
		assertEquals(new String("CA"), heap.remove());
		
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
