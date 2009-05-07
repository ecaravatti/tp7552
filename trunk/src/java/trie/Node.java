package trie;

import java.util.HashMap;
import java.util.Map;

public class Node {

	/** Child nodes to this one */
	private Map<Character, Node> children = new HashMap<Character, Node>();

	/** Content of the node if there's any */
	private String content;

//	/** Depth of the node inside the trie */
//	private Integer depth;

	/**
	 * Adds a node for the given character
	 * @param c
	 * 		Character to add to the node
	 * @return
	 * 		The node that correspond to the given character
	 */
	public Node addNode(Character c) {
		Node emptyNode = new Node();
		children.put(c, emptyNode);
		return emptyNode;
	}

	public Node getChildNode(char c) {
		return children.get(c);
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
}
