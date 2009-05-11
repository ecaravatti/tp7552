package collection.trie;

import java.util.ArrayList;
import java.util.List;

import command.Command;
import command.HighlightCommand;
import command.trie.AddCommand;
import command.trie.RemoveCommand;

public class Trie {

	/** Root node of the trie */
	Node root;

	private Integer nodesCounter = 0;

	public Trie() {
		root = new Node(nodesCounter);
		nodesCounter++;
	}

	/**
	 * Adds a key to this trie. New {@link Node}s will be added if necessary.
	 * 
	 * @param key
	 *            Word to add to this trie
	 */
	public List<Command> add(String key) {

		// This list will contain a list of commands performed to add this key
		List<Command> commandList = new ArrayList<Command>();

		Node node = root;
		Node previousNode = null;

		int i = 0;
		while (i < key.length()) {
			previousNode = node;
			node = node.getChildNode(key.charAt(i));

			if (node == null) {
				// The node doesn't exist
				break;
			} else {
				commandList.add(new HighlightCommand(node.getId(), String
						.valueOf(key.charAt(i))));
			}
			i++;
		}

		/*
		 * If the node doesn't exist yet, new nodes must be added for remaining
		 * chars in the key (if any).
		 */
		if (i < key.length()) {
			// Start from the parent.
			node = previousNode;
			// Add one node for each remaining char.
			while (i < key.length()) {
				previousNode = node;
				node = node.addChildNode(key.charAt(i++), nodesCounter);

				/*
				 * When a node is being added, it will only have content if it
				 * is the last one to add.
				 */
				commandList.add(new AddCommand(nodesCounter, String.valueOf(key
						.charAt(i - 1)), previousNode.getId(),
						i < key.length() - 1 ? false : true));
				nodesCounter++;
			}
		}
		node.setContent(key);

		return commandList;
	}

	/**
	 * Verifies whether the given key is present in this trie or not.
	 * 
	 * @param key
	 *            The key to verify
	 * @return True if key is present, false otherwise
	 */
	public Boolean containsKey(String key) {
		Node node = root;
		for (int i = 0; i < key.length() && node != null; i++) {
			node = node.getChildNode(key.charAt(i));

		}
		return node != null && key.equalsIgnoreCase(node.getContent()) ? true
				: false;
	}

	public List<Command> remove(String key) {
		List<Command> commandList = new ArrayList<Command>();
		removeNode(null, root, key, 0, commandList);
		return commandList;
	}

	private void removeNode(Node parentNode, Node childNode, String key,
			Integer depth, List<Command> commandList) {

		Boolean hasKey = false;

		if (depth < key.length()
				&& childNode.containsChildNode(key.charAt(depth))) {

			removeNode(childNode, childNode.getChildNode(key.charAt(depth)),
					key, depth + 1, commandList);
		}
		if (key.equalsIgnoreCase(childNode.getContent())) {
			childNode.setContent(null);
			hasKey = true;
		}
		if (!childNode.hasChildren() && childNode.getContent() == null) {
			parentNode.removeChildNode(key.charAt(depth - 1));
			commandList
					.add(new RemoveCommand(childNode.getId(), String
							.valueOf(key.charAt(depth - 1)),
							parentNode.getId(), hasKey));

		}
	}

	public Node getRoot() {
		return root;
	}

	// public Collection<T> getItemsInString(CharSequence str) {
	// Node<T> node = root;
	// int i = 0;
	// Collection<T> res = new ArrayList<T>();
	// while (i < str.length() && node != null) {
	// node = node.getChildNode(str.charAt(i));
	// if (node != null && node.item != null) {
	// res.add(node.getItem());
	// }
	// i++;
	// }
	// return res;
	// }
	//
	// public Iterator<T> getItemsWithPrefix(CharSequence prefix) {
	// Node<T> node = root;
	// Node<T> previousNode = root;
	// int i = 0;
	// while (i < prefix.length() && node != null) {
	// previousNode = node;
	// node = node.getChildNode(prefix.charAt(i));
	// i++;
	// }
	// return new SimplePrefixTrieIterator(previousNode);
	// }
	//
	// public Iterator<T> iterator() {
	// return new SimplePrefixTrieIterator(root);
	// }

}
