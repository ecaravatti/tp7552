package heap;

//import common.export.ExportUtils;
import java.util.NoSuchElementException;

import collection.heap.Heap;
import junit.framework.TestCase;

public class MaxHeapTest extends TestCase {
	
	private Heap heap;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		heap = new Heap(false);
		heap.insert(new Integer(10));
		heap.insert(new Integer(15));
		heap.insert(new Integer(80));
		heap.insert(new Integer(33));
		heap.insert(new Integer(25));
		heap.insert(new Integer(29));
		//ExportUtils.exportToXML(heap, "inicialHeap.xml");
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
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		heap.clear();
	}

}
