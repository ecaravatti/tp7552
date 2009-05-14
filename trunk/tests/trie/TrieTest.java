package trie;

import collection.tree.binary.KeyAlreadyExistsException;
import collection.tree.binary.KeyNotFoundException;
import collection.trie.Trie;
import common.export.ExportUtils;

import junit.framework.TestCase;

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
		ExportUtils.exportToXML(trie, "inicialTrie.xml");
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
}
