package trie;

import trie.Trie;
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
		assertTrue(trie.add("año"));
		assertTrue(trie.containsKey("año"));
	}
	
	public void testAddAlreadyExistentKey() {
		assertTrue(trie.containsKey("asar"));
		assertFalse(trie.add("asar"));
		assertTrue(trie.containsKey("asar"));
	}
	
	public void testRemove() {
		assertTrue(trie.containsKey("ala"));
		assertTrue(trie.remove("ala"));
		assertFalse(trie.containsKey("ala"));
		assertTrue(trie.containsKey("alas"));
	}
	
	public void testRemoveUnexistentKey() {
		assertFalse(trie.containsKey("casa"));
		assertFalse(trie.remove("casa"));
	}
}
