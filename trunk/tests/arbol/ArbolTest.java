package arbol;

import junit.framework.TestCase;
import arbol.altura.BSTree;
import arbol.altura.BTData;

public class ArbolTest extends TestCase {
	
	public void treeTest() {
		BSTree tree = new BSTree();
		
		insertar(tree, "10");
		encontrar(tree, "10");
		encontrar(tree, "09");
		encontrar(tree, "11");
		encontrar(tree, "10");
	}
	
	private void insertar(BSTree tree, String valor) {
		System.out.println(valor + (tree.insertAVL(new BTData(valor)) != null ? " insertado correctamente" : " no fue insertado"));
	}
	
	private void encontrar(BSTree tree, String valor) {
		System.out.println((tree.locate(new BTData(valor)) != null ? "Encontré" : "No encontré") + " el " + valor);
	}

}
