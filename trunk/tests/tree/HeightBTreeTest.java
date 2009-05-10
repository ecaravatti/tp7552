package tree;

import java.util.ArrayList;
import java.util.List;

import tree.binary.BTNode;
import tree.binary.BTree;
import tree.binary.BTData;
import tree.binary.height.HeightBTree;
import junit.framework.TestCase;

public class HeightBTreeTest extends TestCase {
	
	public void testHeightBTree() {
		BTree tree = new HeightBTree(4);
		
		List<Integer> insertados = new ArrayList<Integer>();
	/*	insertados.add(498);
		insertados.add(521);
		insertados.add(368);
		insertados.add(843);
		insertados.add(975);
		insertados.add(731);
		insertados.add(671);
		insertados.add(777);
		insertados.add(241);
		insertados.add(174);*/
	/*	insertados.add(5);
		insertados.add(10);
		insertados.add(74);
		insertados.add(93);
		insertados.add(86);*/
		for (Integer codigo : insertados) {
			insertar(tree, codigo);
		}
		for (int i = 0; i < 1000; i++) {
			int random = (int)(Math.random()*99999);
			insertar(tree, random);
			insertados.add(random);
		}
		int i = 0;
		for (Integer codigo : insertados) {
			if (i++ < 980) borrar(tree, codigo);
		}
		System.out.println(tree);
	}
	
	private void insertar(BTree tree, int valor) {
		System.out.println(valor + (tree.insert(new BTData(valor)) != null ? " insertado correctamente" : " no fue insertado"));
	}
	
	private void encontrar(BTree tree, int valor) {
		System.out.println((tree.locate(new BTData(valor)) != null ? "Encontré" : "No encontré") + " el " + valor);
	}
	
	private void borrar(BTree tree, int valor) {
		tree.delete(new BTData(valor), 1);
		System.out.println("Borro el " + valor + " si existe");
	}

	public void testBalances() {
		BTree tree = new HeightBTree(3);
		
		List<Integer> insertados = new ArrayList<Integer>();
		for (int i = 0; i < 10000; i++) {
			int random = (int)(Math.random()*999999);
			tree.insert(new BTData(random));
			insertados.add(random);
		}
		BTNode node;
		//System.out.println(tree);
		for (Integer codigo : insertados) {
			node = tree.locate(new BTData(codigo));
		//	System.out.println(node);
			assertEquals(node.getBalance(), node.getBalanceTeorico());
		}
		
		int i = 0;
		for (Integer codigo : insertados) {
			if (i++ < 9980) tree.delete(new BTData(codigo), 1);
		}
		
		for (Integer codigo : insertados) {
			node = tree.locate(new BTData(codigo));
			if (node != null) {
		//		System.out.println(node);
				assertEquals(node.getBalance(), node.getBalanceTeorico());
			}
		}
		
	//	System.out.println(tree);
		
	}

}
