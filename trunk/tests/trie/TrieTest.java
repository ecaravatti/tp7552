package trie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import junit.framework.TestCase;
import collection.KeyAlreadyExistsException;
import collection.KeyNotFoundException;
import collection.trie.Trie;

import command.Command;
import common.export.ExportUtils;

public class TrieTest extends TestCase {

	private Trie trie;

	@Override
	protected void setUp() throws Exception {
		trie = new Trie();
		trie.add("auto");
		trie.add("ala");
		trie.add("alas");
		trie.add("asar");
		trie.add("asado");
		super.setUp();
	}

	public void testContains() {
		assertTrue(trie.containsKey("auto"));
		assertTrue(trie.containsKey("AUTO"));
		assertTrue(trie.containsKey("aLa"));
		assertTrue(trie.containsKey("ALas"));
		assertTrue(trie.containsKey("asaR"));
		assertTrue(trie.containsKey("ASaDo"));
		assertFalse(trie.containsKey("aut"));
		assertFalse(trie.containsKey("auto "));
		assertFalse(trie.containsKey("al"));
		assertFalse(trie.containsKey("rana"));
	}

	public void testAdd() {
		assertFalse(trie.containsKey("año"));
		try {
			trie.add("año");
		} catch (KeyAlreadyExistsException e) {
			fail("The word 'año' was found in the trie when shouldn't have.");
		}
		assertTrue(trie.containsKey("año"));
	}

	public void testAddAlreadyExistentKey() {
		assertTrue(trie.containsKey("asar"));
		try {
			trie.add("asar");
			fail("KeyAlreadyExistsException should have been thrown.");
		} catch (KeyAlreadyExistsException e) {
			// Expected exception
		}
		assertTrue(trie.containsKey("asar"));
	}

	public void testRemove() {
		assertTrue(trie.containsKey("ala"));
		try {
			trie.remove("ala");
		} catch (KeyNotFoundException e) {
			fail("KeyNotFoundException was thrown when shouldn't have.");
		}
		assertFalse(trie.containsKey("ala"));
		assertTrue(trie.containsKey("alas"));
	}

	public void testRemoveUnexistentKey() {
		assertFalse(trie.containsKey("casa"));
		try {
			trie.remove("casa");
			fail("KeyNotFoundException should have been thrown.");
		} catch (KeyNotFoundException e) {
			// Expected exception
		}
		assertFalse(trie.containsKey("casa"));
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

	public static String random(int count) {
		
		Random random = new Random();
		
		if (count == 0) {
			return "";
		} 
		
		int	end = 'f' + 1;
		int	start = 'a';

		StringBuffer buffer = new StringBuffer();
		int gap = end - start;

		while (count-- != 0) {
			char ch = (char) (random.nextInt(gap) + start);
			
			if (Character.isLetter(ch)) {
				buffer.append(ch);
			} else {
				count++;
			}
		}
		return buffer.toString();
	}
	
	public void testTrieBehavior() {
		for (int i = 1; i < 6; i++) {
			testTrieBehaviour(1000, true, true);
		}
	}

	private void testTrieBehaviour(int insertsAmount, boolean printCommands,
			boolean exportFinalTree) {

		Trie trie = new Trie();
		List<String> keysAdded = new ArrayList<String>();

		/*
		 * TEST INSERTION
		 * A quantity of 'insertsAmount' random keys will be
		 * generated and added to the trie.
		 */
		for (int i = 0; i < insertsAmount; i++) {

			// Generates random Strings to use as keys
			String random = random((int) ((2 - Math.random())*5));
			try {
				trie.add(random);
				if (printCommands) {
					printCommands(trie.getCommands(), "INSERT " + random);
				}
			} catch (KeyAlreadyExistsException e) {
				assertTrue(keysAdded.contains(random));
			}
			keysAdded.add(random);
		}


		// Checks that all keys were added to the trie
		for (String key : keysAdded) {
			assertTrue(trie.containsKey(key));
		}

		List<String> deletedKeys = new ArrayList<String>();
		
		/*
		 * TEST DELETION
		 * Not all keys added will be deleted.
		 * Just 4/5 of them.
		 */
		int i = 0;
		for (String key : keysAdded) {
			if (i++ < (insertsAmount * 4 / 5)) {
				try {
					trie.remove(key);
					if (printCommands) {
						printCommands(trie.getCommands(), "DELETE " + key);
					}
					deletedKeys.add(key);
				} catch (KeyNotFoundException e) {
					assertTrue(deletedKeys.contains(key));
				}
			} else {
				break;
			}
		}

		for (String key : deletedKeys) {
			assertFalse(trie.containsKey(key));
		}

		for (String key : keysAdded) {
			if (trie.containsKey(key) && deletedKeys.contains(key)) {
				fail("Encuentra el elemento borrado " + key);
			}
		}

		// Export the final trie to see the result
		if (exportFinalTree) {
			ExportUtils.exportToXML(trie, "trie.xml");
		}

	}
}
