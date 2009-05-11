package tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;
import tree.binary.BTData;
import tree.binary.BTNode;
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
		
		tree.delete(new BTData(25));
		tree.delete(new BTData(2));
		tree.delete(new BTData(50));
		tree.delete(new BTData(60));
		tree.delete(new BTData(18));
		tree.delete(new BTData(40));
		tree.delete(new BTData(10));
		
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
		
		System.out.println(tree);
	}
	
	public void testDeleteWithNonExistentKey() {
		tree.insert(new BTData(60));
		tree.insert(new BTData(25));
		tree.insert(new BTData(18));
		tree.insert(new BTData(2));
		tree.insert(new BTData(45));
		
		tree.delete(new BTData(25));
		tree.delete(new BTData(2));
		
		try {
			tree.delete(new BTData(100));
			fail("La clave '100' no debería existir en la estructura.");
		} catch (KeyNotFoundException e) {
			
		}
	}
	
//	public void testWeightBTreeBehavior() {
//		for (double i = 0.1; i <= 0.5; i += 0.1) {
//			testWeightBTree(i, 10000);
//		}
//	}
	
	private void testWeightBTree(double alpha, int insertsAmount) {
		BTree tree = new WeightBTree(alpha);
		
		List<Integer> insertados = new ArrayList<Integer>();
		for (int i = 0; i < insertsAmount; i++) {
			int random = (int)(Math.random()*(insertsAmount*1000));
			if (insertados.contains(random)) continue;
			tree.insert(new BTData(random));
			insertados.add(random);
		}
		
		Collections.shuffle(insertados); //Los mezclo para que varíe el orden
		BTNode node;
		for (Integer codigo : insertados) {
			node = tree.locate(new BTData(codigo));
			assertNotNull(node); //Lo encontré
			if (node.isLeaf()) {
				assertEquals(2, node.getWeight());
			} else {
				assertEquals(weight(node.getChild(BTNode.LEFT)) + weight(node.getChild(BTNode.RIGHT)), node.getWeight()); //El peso está bien calculado
			}
			if (node.getParent() != null) { // No es la raíz
				double balance = (double)weight(node.getChild(BTNode.LEFT)) / (double)node.getWeight();
				assertTrue((balance > alpha) && (balance < 1-alpha) || (balance == 0.5)); //No está desbalanceado
			}
		}
		
		List<Integer> borrados = new ArrayList<Integer>();
		Collections.shuffle(insertados);
		int i = 0;
		//Borro menos de los que inserté
		for (Integer codigo : insertados) {
			if (i++ < (insertsAmount*4/5)) {
				borrados.add(codigo);
				tree.delete(new BTData(codigo));
			} else {
				break;
			}
		}
		
		for (Integer codigo : borrados) {
			node = tree.locate(new BTData(codigo));
			assertNull(node); //Efectivamente está borrado
		}
		
		for (Integer codigo : insertados) {
			node = tree.locate(new BTData(codigo));
			if (borrados.contains(codigo)) {
				assertNull(node); //Efectivamente está borrado
			} else {
				assertNotNull(node); //Lo encontré
				if (node.isLeaf()) {
					assertEquals(2, node.getWeight());
				} else {
					assertEquals(weight(node.getChild(BTNode.LEFT)) + weight(node.getChild(BTNode.RIGHT)), node.getWeight()); //El peso está bien calculado
				}
				if (node.getParent() != null) {
					double balance = (double)weight(node.getChild(BTNode.LEFT)) / (double)node.getWeight();
					assertTrue((balance > alpha) && (balance < 1-alpha)); //No está desbalanceado
				}
			}
		}
		
	}
	
	private int weight(BTNode node) {
		if (node == null)
			return 1;
		else
			return nodesCount(node);
	}
	
	private int nodesCount(BTNode node) {
		if (node == null) {
			return 0;
		} else {
			return 	1 + nodesCount(node.getChild(BTNode.LEFT)) +
					nodesCount(node.getChild(BTNode.RIGHT));
		}
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
