package model.collection.heap;

import java.util.List;

import model.exception.heap.EmptyHeapException;

import junit.framework.TestCase;


public class HeapTest extends TestCase {

  public void testInsertion(){
    Integer value;
    Heap<Integer> heap = new Heap<Integer>();
    List<Integer> result;
   
    
    value = new Integer(90);
    heap.insert( value );
    result = heap.toList();
    assertEquals( new Integer(90), result.get(0) );
    assertEquals( 1, heap.size() );
    
    value = new Integer(50);
    heap.insert( value );
    result = heap.toList();
    assertEquals( new Integer(90), result.get(0) );
    assertEquals( new Integer(50), result.get(1) );
    assertEquals( 2, heap.size() );
    
    value = new Integer(100);
    heap.insert( value );
    result = heap.toList();
    assertEquals( new Integer(100), result.get(0) );
    assertEquals( new Integer(50), result.get(1) );
    assertEquals( new Integer(90), result.get(2) );
    assertEquals( 3, heap.size() );
    
    value = new Integer(70);
    heap.insert( value );
    result = heap.toList();
    assertEquals( new Integer(100), result.get(0) );
    assertEquals( new Integer(70), result.get(1) );
    assertEquals( new Integer(90), result.get(2) );
    assertEquals( new Integer(50), result.get(3) );
    assertEquals( 4, heap.size() );
    
    value = new Integer(95);
    heap.insert( value );
    result = heap.toList();
    assertEquals( new Integer(100), result.get(0) );
    assertEquals( new Integer(95), result.get(1) );
    assertEquals( new Integer(90), result.get(2) );
    assertEquals( new Integer(50), result.get(3) );
    assertEquals( new Integer(70), result.get(4) );
    assertEquals( 5, heap.size() );
    
    value = new Integer(20);
    heap.insert( value );
    result = heap.toList();
    assertEquals( new Integer(100), result.get(0) );
    assertEquals( new Integer(95), result.get(1) );
    assertEquals( new Integer(90), result.get(2) );
    assertEquals( new Integer(50), result.get(3) );
    assertEquals( new Integer(70), result.get(4) );
    assertEquals( new Integer(20), result.get(5) );
    assertEquals( 6, heap.size() );
    
    value = new Integer(98);
    heap.insert( value );
    result = heap.toList();
    assertEquals( new Integer(100), result.get(0) );
    assertEquals( new Integer(95), result.get(1) );
    assertEquals( new Integer(98), result.get(2) );
    assertEquals( new Integer(50), result.get(3) );
    assertEquals( new Integer(70), result.get(4) );
    assertEquals( new Integer(20), result.get(5) );
    assertEquals( new Integer(90), result.get(6) );
    assertEquals( 7, heap.size() );
    
    value = new Integer(65);
    heap.insert( value );
    result = heap.toList();
    assertEquals( new Integer(100), result.get(0) );
    assertEquals( new Integer(95), result.get(1) );
    assertEquals( new Integer(98), result.get(2) );
    assertEquals( new Integer(65), result.get(3) );
    assertEquals( new Integer(70), result.get(4) );
    assertEquals( new Integer(20), result.get(5) );
    assertEquals( new Integer(90), result.get(6) );
    assertEquals( new Integer(50), result.get(7) );
    assertEquals( 8, heap.size() );
    
    value = new Integer(91);
    heap.insert( value );
    result = heap.toList();
    assertEquals( new Integer(100), result.get(0) );
    assertEquals( new Integer(95), result.get(1) );
    assertEquals( new Integer(98), result.get(2) );
    assertEquals( new Integer(91), result.get(3) );
    assertEquals( new Integer(70), result.get(4) );
    assertEquals( new Integer(20), result.get(5) );
    assertEquals( new Integer(90), result.get(6) );
    assertEquals( new Integer(50), result.get(7) );
    assertEquals( new Integer(65), result.get(8) );
    assertEquals( 9, heap.size() );
    
    value = new Integer(96);
    heap.insert( value );
    result = heap.toList();
    assertEquals( new Integer(100), result.get(0) );
    assertEquals( new Integer(96), result.get(1) );
    assertEquals( new Integer(98), result.get(2) );
    assertEquals( new Integer(91), result.get(3) );
    assertEquals( new Integer(95), result.get(4) );
    assertEquals( new Integer(20), result.get(5) );
    assertEquals( new Integer(90), result.get(6) );
    assertEquals( new Integer(50), result.get(7) );
    assertEquals( new Integer(65), result.get(8) );
    assertEquals( new Integer(70), result.get(9) );
    assertEquals( 10, heap.size() );
    
  }
  
  public void testRemove() throws EmptyHeapException {
    Heap<Integer> heap = new Heap<Integer>();
    List<Integer> result;
    Integer ret;
    
    heap.insert( new Integer(20) );
    heap.insert( new Integer(50) );
    heap.insert( new Integer(99) );
    heap.insert( new Integer(60) );
    heap.insert( new Integer(70) );
    heap.insert( new Integer(65) );
    heap.insert( new Integer(77) );
    heap.insert( new Integer(80) );
    
    ret = heap.remove();
    result = heap.toList();
    assertEquals( new Integer(80), result.get(0) );
    assertEquals( new Integer(70), result.get(1) );
    assertEquals( new Integer(77), result.get(2) );
    assertEquals( new Integer(20), result.get(3) );
    assertEquals( new Integer(60), result.get(4) );
    assertEquals( new Integer(50), result.get(5) );
    assertEquals( new Integer(65), result.get(6) );
    assertEquals( 7, heap.size() );
    assertEquals( new Integer(99), ret );
    
    ret = heap.remove();
    result = heap.toList();
    assertEquals( new Integer(77), result.get(0) );
    assertEquals( new Integer(70), result.get(1) );
    assertEquals( new Integer(65), result.get(2) );
    assertEquals( new Integer(20), result.get(3) );
    assertEquals( new Integer(60), result.get(4) );
    assertEquals( new Integer(50), result.get(5) );
    assertEquals( 6, heap.size() );
    assertEquals( new Integer(80), ret );
    
    ret = heap.remove();
    result = heap.toList();
    assertEquals( new Integer(70), result.get(0) );
    assertEquals( new Integer(60), result.get(1) );
    assertEquals( new Integer(65), result.get(2) );
    assertEquals( new Integer(20), result.get(3) );
    assertEquals( new Integer(50), result.get(4) );
    assertEquals( 5, heap.size() );
    assertEquals( new Integer(77), ret );
    
    ret = heap.remove();
    result = heap.toList();
    assertEquals( new Integer(65), result.get(0) );
    assertEquals( new Integer(60), result.get(1) );
    assertEquals( new Integer(50), result.get(2) );
    assertEquals( new Integer(20), result.get(3) );
    assertEquals( 4, heap.size() );
    assertEquals( new Integer(70), ret );
    
    ret = heap.remove();
    result = heap.toList();
    assertEquals( new Integer(60), result.get(0) );
    assertEquals( new Integer(20), result.get(1) );
    assertEquals( new Integer(50), result.get(2) );
    assertEquals( 3, heap.size() );
    assertEquals( new Integer(65), ret );
    
    ret = heap.remove();
    result = heap.toList();
    assertEquals( new Integer(50), result.get(0) );
    assertEquals( new Integer(20), result.get(1) );
    assertEquals( 2, heap.size() );
    assertEquals( new Integer(60), ret );
    
    ret = heap.remove();
    result = heap.toList();
    assertEquals( new Integer(20), result.get(0) );
    assertEquals( 1, heap.size() );
    assertEquals( new Integer(50), ret );
    
    ret = heap.remove();
    result = heap.toList();
    assertEquals( 0, heap.size() );
    assertEquals( new Integer(20), ret );
    
    try {
    	ret = heap.remove();
    	fail("El heap debería estar vacío.");
    } catch (EmptyHeapException e) {
    	
    }
    
  }
}
