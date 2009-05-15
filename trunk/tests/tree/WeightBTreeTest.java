package tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;
import collection.KeyAlreadyExistsException;
import collection.KeyNotFoundException;
import collection.tree.binary.BTNode;
import collection.tree.binary.BTree;
import collection.tree.binary.weight.WeightBTree;

import command.Command;
import common.export.ExportUtils;

public class WeightBTreeTest extends TestCase {
	
	private BTree tree;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		tree = new WeightBTree();
	}
	
	public void testInsert() throws KeyAlreadyExistsException,
									KeyNotFoundException {
		tree.insert(10);
		tree.insert(20);
		tree.insert(30);
		tree.insert(40);
		tree.insert(50);
		tree.insert(60);
		tree.insert(25);
		tree.insert(18);
		tree.insert(2);
		tree.insert(45);
		
		assertNotNull(tree.locate(10));
		assertNotNull(tree.locate(20));
		assertNotNull(tree.locate(30));
		assertNotNull(tree.locate(40));
		assertNotNull(tree.locate(50));
		assertNotNull(tree.locate(60));
		assertNotNull(tree.locate(25));
		assertNotNull(tree.locate(18));
		assertNotNull(tree.locate(2));
		assertNotNull(tree.locate(45));
		
		System.out.println(tree);
	}

	public void testInsertWithExistentKey() throws KeyAlreadyExistsException,
												   KeyNotFoundException {
		tree.insert(10);
		tree.insert(20);
		tree.insert(30);
		tree.insert(40);
		tree.insert(50);
		
		try {
			tree.insert(20);
			fail("La clave '20' debería existir en la estructura.");
		} catch (KeyAlreadyExistsException e) {
			
		}
	}
	
	public void testDelete() throws KeyAlreadyExistsException,
									KeyNotFoundException {
		tree.insert(10);
		tree.insert(20);
		tree.insert(30);
		tree.insert(40);
		tree.insert(50);
		tree.insert(60);
		tree.insert(25);
		tree.insert(18);
		tree.insert(2);
		tree.insert(45);
		
		tree.delete(25);
		tree.delete(2);
		tree.delete(50);
		tree.delete(60);
		tree.delete(18);
		tree.delete(40);
		tree.delete(10);
		
		assertNotFound(10);
		assertFound(20);
		assertFound(30);
		assertNotFound(40);
		assertNotFound(50);
		assertNotFound(60);
		assertNotFound(25);
		assertNotFound(18);
		assertNotFound(2);
		assertFound(45);
		
		System.out.println(tree);
	}
	
	public void testDeleteWithNonExistentKey() throws KeyAlreadyExistsException,
													  KeyNotFoundException {
		tree.insert(60);
		tree.insert(25);
		tree.insert(18);
		tree.insert(2);
		tree.insert(45);
		
		tree.delete(25);
		tree.delete(2);
		
		try {
			tree.delete(100);
			fail("La clave '100' no debería existir en la estructura.");
		} catch (KeyNotFoundException e) {
			
		}
	}
	
	public void testWeightBTreeBehavior() throws KeyAlreadyExistsException,
												 KeyNotFoundException {
		for (double i = 0; i <= 0.5; i += 0.1) {
			testWeightBTree(i, 1000, false, false);
		}
	}
	
	public void testCommands() throws KeyAlreadyExistsException,
									  KeyNotFoundException {
		testWeightBTree(0.3, 5, true, true);
	}
	
	private void printCommands(List<Command> commands, String description) {
		System.out.println("------" + description + "------");
		System.out.println("// EXECUTE");
		for (Command command : commands) {
			System.out.println(command.execute());
		}
		System.out.println("// UNDO");
		Collections.reverse(commands);
		for (Command command : commands) {
			System.out.println(command.undo());
		}
		System.out.println("-------END-------\n");
	}
	
	private void testWeightBTree(double alpha, int insertsAmount,
								 boolean printCommands, boolean exportFinalTree)
								 throws KeyAlreadyExistsException,
								 		KeyNotFoundException {
		
		BTree tree = new WeightBTree(alpha);
		
		List<Integer> insertados = new ArrayList<Integer>();
		for (int i = 0; i < insertsAmount; i++) {
			int random = (int)(Math.random()*(insertsAmount*1000));
			if (insertados.contains(random)) continue;
			tree.insert(random);
			if (printCommands)
				printCommands(tree.getCommands(), "INSERT " + random);
			insertados.add(random);
		}
		
		Collections.shuffle(insertados); //Los mezclo para que varíe el orden
		BTNode node;
		for (Integer codigo : insertados) {
			try {
				node = tree.locate(codigo);
				if (printCommands)
					printCommands(tree.getCommands(), "LOCATE " + codigo);
				assertNotNull(node); //No puede encontrar algo null
				assertEquals(weight(node.getChild(BTNode.LEFT)) +
							 weight(node.getChild(BTNode.RIGHT)),
							 node.getWeight()); //El peso está bien calculado
			} catch (KeyNotFoundException e) {
				fail();
			}
		}
		
		List<Integer> borrados = new ArrayList<Integer>();
		Collections.shuffle(insertados);
		int i = 0;
		//Borro menos de los que inserté
		for (Integer codigo : insertados) {
			if (i++ < (insertsAmount*4/5)) {
				tree.delete(codigo);
				if (printCommands)
					printCommands(tree.getCommands(), "DELETE " + codigo);
				borrados.add(codigo);
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
			if (printCommands)
				printCommands(tree.getCommands(), "LOCATE " + codigo);
		}
		
		for (Integer codigo : insertados) {
			try {
				node = tree.locate(codigo);
				if (borrados.contains(codigo)) {
					fail(); //No debería haberlo encontrado
				} else {
					assertNotNull(node); //No puede devolver null
					assertEquals(weight(node.getChild(BTNode.LEFT)) +
								 weight(node.getChild(BTNode.RIGHT)),
								 node.getWeight());//El peso está bien calculado
				}
			} catch (KeyNotFoundException e) {
				if (!borrados.contains(codigo)) {
					fail(); //Debería haberlo encontrado
				}
			}
			if (printCommands)
				printCommands(tree.getCommands(), "LOCATE " + codigo);
		}
		
		if (exportFinalTree) {
			ExportUtils.exportToXML(tree, "WeightBTree" + alpha + ".xml");
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
	
	private void assertFound(int value) {
		try {
			assertNotNull(tree.locate(value));
		} catch (KeyNotFoundException e) {
			fail();
		}
	}
	
	private void assertNotFound(int value) {
		try {
			tree.locate(value);
			fail();
		} catch (KeyNotFoundException e) {
			//Todo en orden, no tenía que encontrarla.
		}
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
