package tree;

import tree.binary.BTree;
import tree.binary.BTData;
import tree.binary.height.HeightBTree;
import junit.framework.TestCase;

public class TreeTest extends TestCase {
	
	public void testHeightBTree() {
		BTree tree = new HeightBTree();
		
		insertar(tree, 10);
		encontrar(tree, 10);
		insertar(tree, 4);
		insertar(tree, 7);
		encontrar(tree, 9);
		encontrar(tree, 4);
		insertar(tree, 10);
		encontrar(tree, 10);
		borrar(tree, 10);
		encontrar(tree, 7);
		borrar(tree, 7);
		encontrar(tree, 7);
		borrar(tree, 6);
		encontrar(tree, 10);
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

}
