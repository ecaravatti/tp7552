package tree;

import junit.framework.TestCase;
import tree.binary.BTData;
import tree.binary.BTree;
import tree.binary.KeyAlreadyExistsException;
import tree.binary.KeyNotFoundException;
import tree.binary.weight.WeightBTree;

public class WeightBTreeTest extends TestCase {
	
	private BTree tree;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		tree = new WeightBTree();
	}
	
	public void testInsert() {
		tree.insert(new BTData(10));
		tree.insert(new BTData(20));
		tree.insert(new BTData(30));
		tree.insert(new BTData(40));
		tree.insert(new BTData(50));
		tree.insert(new BTData(60));
		tree.insert(new BTData(25));
		tree.insert(new BTData(18));
		tree.insert(new BTData(2));
		tree.insert(new BTData(45));
		
		assertNotNull(tree.locate(new BTData(10)));
		assertNotNull(tree.locate(new BTData(20)));
		assertNotNull(tree.locate(new BTData(30)));
		assertNotNull(tree.locate(new BTData(40)));
		assertNotNull(tree.locate(new BTData(50)));
		assertNotNull(tree.locate(new BTData(60)));
		assertNotNull(tree.locate(new BTData(25)));
		assertNotNull(tree.locate(new BTData(18)));
		assertNotNull(tree.locate(new BTData(2)));
		assertNotNull(tree.locate(new BTData(45)));
		
		System.out.println(tree);
	}

	public void testInsertWithExistentKey() {
		tree.insert(new BTData(10));
		tree.insert(new BTData(20));
		tree.insert(new BTData(30));
		tree.insert(new BTData(40));
		tree.insert(new BTData(50));
		
		try {
			tree.insert(new BTData(20));
			fail("La clave '20' debería existir en la estructura.");
		} catch (KeyAlreadyExistsException e) {
			
		}
	}
	
	public void testDelete() {
		tree.insert(new BTData(10));
		tree.insert(new BTData(20));
		tree.insert(new BTData(30));
		tree.insert(new BTData(40));
		tree.insert(new BTData(50));
		tree.insert(new BTData(60));
		tree.insert(new BTData(25));
		tree.insert(new BTData(18));
		tree.insert(new BTData(2));
		tree.insert(new BTData(45));
		
		tree.delete(new BTData(25), BTree.FINDMIN);
		tree.delete(new BTData(2), BTree.FINDMAX);
		tree.delete(new BTData(50), BTree.FINDMIN);
		tree.delete(new BTData(60), BTree.FINDMAX);
		tree.delete(new BTData(18), BTree.FINDMIN);
		tree.delete(new BTData(40), BTree.FINDMAX);
		tree.delete(new BTData(10), BTree.FINDMIN);
		
		assertNull(tree.locate(new BTData(10)));
		assertNotNull(tree.locate(new BTData(20)));
		assertNotNull(tree.locate(new BTData(30)));
		assertNull(tree.locate(new BTData(40)));
		assertNull(tree.locate(new BTData(50)));
		assertNull(tree.locate(new BTData(60)));
		assertNull(tree.locate(new BTData(25)));
		assertNull(tree.locate(new BTData(18)));
		assertNull(tree.locate(new BTData(2)));
		assertNotNull(tree.locate(new BTData(45)));
	}
	
	public void testDeleteWithNonExistentKey() {
		tree.insert(new BTData(60));
		tree.insert(new BTData(25));
		tree.insert(new BTData(18));
		tree.insert(new BTData(2));
		tree.insert(new BTData(45));
		
		tree.delete(new BTData(25), BTree.FINDMIN);
		tree.delete(new BTData(2), BTree.FINDMAX);
		
		try {
			tree.delete(new BTData(100), BTree.FINDMIN);
			fail("La clave '100' no debería existir en la estructura.");
		} catch (KeyNotFoundException e) {
			
		}
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
