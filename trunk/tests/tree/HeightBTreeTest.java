package tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import collection.tree.binary.BTNode;
import collection.tree.binary.BTree;
import collection.tree.binary.KeyAlreadyExistsException;
import collection.tree.binary.KeyNotFoundException;
import collection.tree.binary.height.HeightBTree;

import junit.framework.TestCase;

public class HeightBTreeTest extends TestCase {

	public void testHeightTreeBehavior() {
		for (int i = 1; i < 6; i++) {
			testHeightTree(i, 10000);
		}
	}

	private void testHeightTree(int heightVariation, int insertsAmount) {
		BTree tree = new HeightBTree(heightVariation);

		List<Integer> insertados = new ArrayList<Integer>();
		for (int i = 0; i < insertsAmount; i++) {
			int random = (int) (Math.random() * (insertsAmount * 1000));
			try {
				tree.insert(random);
			} catch (KeyAlreadyExistsException e) {
				assertTrue(insertados.contains(random));
			}
			insertados.add(random);
		}

		Collections.shuffle(insertados); //Los mezclo para que varíe el orden
		BTNode node;
		for (Integer codigo : insertados) {
			try {
				node = tree.locate(codigo);
				assertNotNull(node); //No puede encontrar algo null
				//El balance está bien calculado
				assertEquals(node.getBalance(), node.getBalanceTeorico());
				//No está desbalanceado
				assertTrue(Math.abs(node.getBalance()) <= heightVariation);
			} catch (KeyNotFoundException e) {
				fail();
			}
		}

		List<Integer> borrados = new ArrayList<Integer>();
		Collections.shuffle(insertados);
		int i = 0;
		//Borro menos de los que inserté
		for (Integer codigo : insertados) {
			if (i++ < (insertsAmount * 4 / 5)) {
				try {
					tree.delete(codigo);
					borrados.add(codigo);
				} catch (KeyNotFoundException e) {
					assertTrue(borrados.contains(codigo));
				}
			} else {
				break;
			}
		}

		for (Integer codigo : borrados) {
			try {
				node = tree.locate(codigo);
				fail();
			} catch (KeyNotFoundException e) {
				//Efectivamente está borrado
			}
		}

		for (Integer codigo : insertados) {
			try {
				node = tree.locate(codigo);
				if (borrados.contains(codigo)) {
					fail(); //No debería haberlo encontrado
				} else {
					assertNotNull(node); //No puede devolver null
					//El balance está bien calculado
					assertEquals(node.getBalance(), node.getBalanceTeorico());
					//No está desbalanceado
					assertTrue(Math.abs(node.getBalance()) <= heightVariation);
				}
			} catch (KeyNotFoundException e) {
				if (!borrados.contains(codigo)) {
					fail(); //Debería haberlo encontrado
				}
			}
		}

	}

}
