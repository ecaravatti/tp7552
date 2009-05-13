/**
 * 
 */
package heap;

import java.util.NoSuchElementException;

import collection.heap.Heap;
import junit.framework.TestCase;

public class MinHeapIntegerTest extends TestCase {
	
	private Heap<Integer> heap;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		heap = new Heap<Integer>(true);
		heap.insert(new Integer(10));
		heap.insert(new Integer(15));
		heap.insert(new Integer(80));
		heap.insert(new Integer(33));
		heap.insert(new Integer(25));
		heap.insert(new Integer(29));
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		heap.clear();
	}

	/**
	 * Test method for {@link collection.heap.Heap#insert(java.lang.Integer)}.
	 */
	public void testInsert() {
		heap.insert(new Integer(5));		
		for (int i = 0; i < 10000; i++){
			heap.insert(new Integer((int)Math.random()*10000 + 5));
		}
		
		assertEquals(new Integer(5), heap.peek());
		
		heap.insert(new Integer(1));
		assertEquals(new Integer(1), heap.peek());
	}

	/**
	 * Test method for {@link collection.heap.Heap#peek()}.
	 */
	public void testPeek() {
		assertEquals(new Integer(10), heap.peek());
		assertEquals(new Integer(10), heap.peek());
		
		heap.insert(new Integer(8));
		assertEquals(new Integer(8), heap.peek());
		
		heap.remove();
		assertEquals(new Integer(10), heap.peek());
	}

	/**
	 * Test method for {@link collection.heap.Heap#pop()}.
	 */
	public void testPop() {
		assertEquals(new Integer(10), heap.pop());
		assertEquals(new Integer(15), heap.pop());
		assertEquals(new Integer(25), heap.pop());
		assertEquals(new Integer(29), heap.pop());
		assertEquals(new Integer(33), heap.pop());
		assertEquals(new Integer(80), heap.pop());
		
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

}
