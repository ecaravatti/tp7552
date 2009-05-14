package collection.trie;

import java.util.ArrayList;
import java.util.List;

import collection.tree.binary.KeyAlreadyExistsException;
import collection.tree.binary.KeyNotFoundException;

import command.Command;
import command.HighlightCommand;
import command.trie.AddCommand;
import command.trie.RemoveCommand;

public class Trie {

	/** 
	 * Root node of the trie.
	 */
	private Node root;

	/** 
	 * This counter increments each time a node is added
	 * to the tree. Is is used to give the nodes an ID. 
	 */
	private Integer nodesCounter = 0;
	
	/**
	 * This list of commands is used to record the steps the trie
	 * makes every time it has to add, remove or look up for a node.
	 */
	private List<Command> commands = new ArrayList<Command>();

	public Trie() {
		this.root = new Node(this.nodesCounter);
		this.nodesCounter++;
	}

	/**
	 * Adds a key to this trie. New {@link Node}s will be added if necessary.
	 * 
	 * @param key
	 * 		Word to add to this trie
	 * @throws KeyAlreadyExistsException 
	 * 		When the key to add was already added to this trie
	 */
	public void add(String key) throws KeyAlreadyExistsException {

		// List of commands performed to add this key.
		this.initCommands();

		Node node = this.root;
		Node previousNode = null;

		int i = 0;
		while (i < key.length()) {
			previousNode = node;
			node = node.getChildNode(key.charAt(i));

			if (node == null) {
				// The node doesn't exist.
				break;
			} else {
				// The node already exists, it will only be highlighted.
				this.commands.add(new HighlightCommand(node.getId(), 
						String.valueOf(key.charAt(i))));
			}
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
				previousNode = node;
				node = node.addChildNode(key.charAt(i++), this.nodesCounter);

				/*
				 * When a node is being added, it will only have
				 * content if it is the last one to add.
				 */
				this.commands.add(new AddCommand(this.nodesCounter, 
						String.valueOf(key.charAt(i - 1)), 
						previousNode.getId(),
						i < key.length() - 1 ? false : true));
				this.nodesCounter++;
			}
		}
		
		if (key.equalsIgnoreCase(node.getKey())) {
			// The given key was already in the trie.
			throw new KeyAlreadyExistsException();
		}
		node.setKey(key);
	}

	/**
	 * Verifies whether the given key is present in this trie or not.
	 * 
	 * @param key
	 * 		The key to verify
	 * @return True if key is present, false otherwise
	 */
	public Boolean containsKey(String key) {
		Node node = this.root;
		for (int i = 0; i < key.length() && node != null; i++) {
			node = node.getChildNode(key.charAt(i));

		}
		return node != null && 
			key.equalsIgnoreCase(node.getKey()) ? true : false;
	}

	/**
	 * Removes the given key from the trie.
	 * 
	 * @param key
	 * 		The key to remove
	 * @throws KeyNotFoundException 
	 * 		If the key to remove isn't in the trie
	 */
	public void remove(String key) throws KeyNotFoundException {
		this.initCommands();
		if (Boolean.FALSE.equals(
				this.removeNode(null, this.root, key, 0, this.commands))) {
			throw new KeyNotFoundException();
		}
	}

	/**
	 * Recursive method that looks if the child node contains the
	 * given key, and, if it does, removes the child node from
	 * its parent.
	 * 
	 * @param parentNode
	 * @param childNode
	 * @param key
	 * @param depth
	 * 		Char index in the key. The char in this index will
	 * 		be looked for in the childNode's children.
	 * @param commandList
	 * 		List of commands to add the actions performed by
	 * 		this method.
	 * @return
	 * 		True if the key was found in the child node or its
	 * 		children; false if it was not.
	 */
	private Boolean removeNode(Node parentNode, Node childNode, String key,
			Integer depth, List<Command> commandList) {

		// Indicates whether the key is in the trie or not
		Boolean hasKey = false;

		if (depth < key.length()
				&& childNode.containsChildNode(key.charAt(depth))) {

			hasKey = removeNode(childNode, childNode.getChildNode(
					key.charAt(depth)), key, depth + 1, commandList)
					? true
					: hasKey;
		}
		if (key.equalsIgnoreCase(childNode.getKey())) {
			childNode.setKey(null);
			hasKey = true;
		}
		if (!childNode.hasChildren() && childNode.getKey() == null) {
			parentNode.removeChildNode(key.charAt(depth - 1));
			
			commandList.add(new RemoveCommand(childNode.getId(), 
					String.valueOf(key.charAt(depth - 1)),
					parentNode.getId(), hasKey));
		}
		return hasKey;
	}

	/**
	 * Clears the commands list. 
	 * This method must be called before adding or removing 
	 * keys from the trie, so that the list only stores the 
	 * actions performed by the last method called.
	 */
	private void initCommands() {
		this.commands.clear();
	}

	public List<Command> getCommands() {
		return commands;
	}

	public Node getRoot() {
		return root;
	}
}
