package trie;

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
		trie.add("año");
		assertTrue(trie.containsKey("año"));
	}

	public void testAddAlreadyExistentKey() {
		assertTrue(trie.containsKey("asar"));
		trie.add("asar");
		assertTrue(trie.containsKey("asar"));
	}

	public void testRemove() {
		assertTrue(trie.containsKey("ala"));
		trie.remove("ala");
		assertFalse(trie.containsKey("ala"));
		assertTrue(trie.containsKey("alas"));
	}

	public void testRemoveUnexistentKey() {
		assertFalse(trie.containsKey("casa"));
		trie.remove("casa");
		assertFalse(trie.containsKey("casa"));
	}
}
