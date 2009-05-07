package trie;

public class Trie {

	/** Root node of the trie */
	Node root = new Node();

	/**
	 * Adds a key to this trie.
	 * New {@link Node}s will be added if necessary.
	 * 
	 * @param key
	 * 			Word to add to this trie
	 */
	public void add(String key) {
		Node node = root;
		Node previousNode = null;

		int i = 0;
		while (i < key.length()) {
			previousNode = node;
			node = node.getChildNode(key.charAt(i));
			
			if (node == null)
				// The node doesn't exist
				break;
			i++;
		}

		/* 
		 * If the node doesn't exist yet, new nodes must be 
		 * added for remaining chars in the key (if any).
		 */
		if (i < key.length()) {
			// Start from the parent.
			node = previousNode;
			// Add one node for each remaining char.
			while (i < key.length()) {
				node = node.addNode(key.charAt(i++));
			}
		}
		node.setContent(key);
	}

	/**
	 * Verifies whether the given key is present in this trie
	 * or not.
	 * 
	 * @param key
	 * 			The key to verify			
	 * @return
	 * 			True if key is present, false otherwise
	 */
	public Boolean containsKey(String key) {
		Node node = root;
		for (int i = 0; i < key.length() && node != null; i++) {
			node = node.getChildNode(key.charAt(i));
		}
		return node != null && key.equals(node.getContent()) ? true : false;
	}

//	public Collection<T> getItemsInString(CharSequence str) {
//		Node<T> node = root;
//		int i = 0;
//		Collection<T> res = new ArrayList<T>();
//		while (i < str.length() && node != null) {
//			node = node.getChildNode(str.charAt(i));
//			if (node != null && node.item != null) {
//				res.add(node.getItem());
//			}
//			i++;
//		}
//		return res;
//	}
//
//	public Iterator<T> getItemsWithPrefix(CharSequence prefix) {
//		Node<T> node = root;
//		Node<T> previousNode = root;
//		int i = 0;
//		while (i < prefix.length() && node != null) {
//			previousNode = node;
//			node = node.getChildNode(prefix.charAt(i));
//			i++;
//		}
//		return new SimplePrefixTrieIterator(previousNode);
//	}
//
//	public Iterator<T> iterator() {
//		return new SimplePrefixTrieIterator(root);
//	}

}
