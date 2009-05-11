package tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import collection.tree.binary.BTData;
import collection.tree.binary.BTNode;
import collection.tree.binary.BTree;
import collection.tree.binary.height.HeightBTree;

import junit.framework.TestCase;

public class HeightBTreeTest extends TestCase {
	
	public void testHeightTreeBehavior() {
		for (int i = 1; i < 5; i++) {
			testHeightTree(i, 10000);
		}
	}
	
	private void testHeightTree(int heightVariation, int insertsAmount) {
		BTree tree = new HeightBTree(heightVariation);
		
		List<Integer> insertados = new ArrayList<Integer>();
		for (int i = 0; i < insertsAmount; i++) {
			int random = (int)(Math.random()*(insertsAmount*1000));
			tree.insert(new BTData(random));
			insertados.add(random);
		}
		
		Collections.shuffle(insertados); //Los mezclo para que varíe el orden
		BTNode node;
		for (Integer codigo : insertados) {
			node = tree.locate(new BTData(codigo));
			assertNotNull(node); //Lo encontré
			assertEquals(node.getBalance(), node.getBalanceTeorico()); //El balance está bien calculado
			assertTrue(Math.abs(node.getBalance()) <= heightVariation); //No está desbalanceado
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
				assertEquals(node.getBalance(), node.getBalanceTeorico()); //El balance está bien calculado
				assertTrue(Math.abs(node.getBalance()) <= heightVariation); //No está desbalanceado
			}
		}
		
	}

}
