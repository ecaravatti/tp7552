package tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import command.Command;
import common.export.ExportUtils;

import collection.KeyAlreadyExistsException;
import collection.KeyNotFoundException;
import collection.tree.binary.BTNode;
import collection.tree.binary.BTree;
import collection.tree.binary.height.HeightBTree;

import junit.framework.TestCase;

public class HeightBTreeTest extends TestCase {

	private BTree tree;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		tree = new HeightBTree(1);

		tree.insert(20);
		tree.insert(30);
		tree.insert(25);
	}

	public void testInsert() {
		try {
			tree.insert(47);
		} catch (KeyAlreadyExistsException e) {
			fail("Dice que ya existe una clave no insertada");
		}

		try {
			tree.locate(47);
		} catch (KeyNotFoundException e) {
			fail("No encuentra algo insertado");
		}
	}

	public void testDelete() {
		try {
			tree.delete(30);
		} catch (KeyNotFoundException e) {
			fail("No encuentra algo insertado");
		}

		try {
			tree.locate(30);
			fail("Encuentra algo borrado");
		} catch (KeyNotFoundException e) {
			//Todo en orden, no lo tenía que encontrar
		}
	}

	public void testLocate() {
		try {
			tree.locate(30);
		} catch (KeyNotFoundException e) {
			fail("No encuentra algo que fue insertado en el arbol");
		}
	}

	public void testHeightTreeBehavior() {
		for (int i = 1; i < 6; i++) {
			testHeightTree(i, 10000, false, false);
		}
	}

	public void testCommands() {
		testHeightTree(1, 5, true, true);
	}

	private void printCommands(List<Command> commands, String descripcion) {
		System.out.println("------" + descripcion + "------");
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

	private void testHeightTree(int heightVariation, int insertsAmount,
			boolean printCommands, boolean exportFinalTree) {
		BTree tree = new HeightBTree(heightVariation);

		List<Integer> insertados = new ArrayList<Integer>();
		for (int i = 0; i < insertsAmount; i++) {
			int random = (int) (Math.random() * (insertsAmount * 100));
			try {
				tree.insert(random);
				if (printCommands)
					printCommands(tree.getCommands(), "INSERT " + random);
			} catch (KeyAlreadyExistsException e) {
				assertTrue(insertados.contains(random));
			}
			insertados.add(random);
		}

		Collections.shuffle(insertados); // Los mezclo para que varíe el
		// orden
		BTNode node;
		for (Integer codigo : insertados) {
			try {
				node = tree.locate(codigo);
				if (printCommands)
					printCommands(tree.getCommands(), "LOCATE " + codigo);
				assertNotNull(node); // No puede encontrar algo null
				// El balance está bien calculado
				assertEquals(node.getBalance(), node.getBalanceTeorico());
				// No está desbalanceado
				assertTrue(Math.abs(node.getBalance()) <= heightVariation);
			} catch (KeyNotFoundException e) {
				fail("No encuentra el elemento insertado " + codigo);
			}
		}

		List<Integer> borrados = new ArrayList<Integer>();
		Collections.shuffle(insertados);
		int i = 0;
		// Borro menos de los que inserté
		for (Integer codigo : insertados) {
			if (i++ < (insertsAmount * 4 / 5)) {
				try {
					tree.delete(codigo);
					if (printCommands)
						printCommands(tree.getCommands(), "DELETE " + codigo);
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
				fail("Encuentra el elemento borrado " + codigo);
			} catch (KeyNotFoundException e) {
				// Efectivamente está borrado
			}
			if (printCommands)
				printCommands(tree.getCommands(), "LOCATE " + codigo);
		}

		for (Integer codigo : insertados) {
			try {
				node = tree.locate(codigo);
				if (borrados.contains(codigo)) {
					fail("Encuentra el elemento borrado " + codigo);
				} else {
					assertNotNull(node); // No puede devolver null
					// El balance está bien calculado
					assertEquals(node.getBalance(), node.getBalanceTeorico());
					// No está desbalanceado
					assertTrue(Math.abs(node.getBalance()) <= heightVariation);
				}
			} catch (KeyNotFoundException e) {
				if (!borrados.contains(codigo)) {
					fail("No encuentra el elemento insertado " + codigo);
				}
			}
			if (printCommands)
				printCommands(tree.getCommands(), "LOCATE " + codigo);
		}

		if (exportFinalTree) {
			ExportUtils.exportToXML(tree, "HeightTree" + heightVariation
					+ ".xml");
		}

	}

}
