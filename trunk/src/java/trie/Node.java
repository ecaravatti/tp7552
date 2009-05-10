package trie;

import java.util.HashMap;
import java.util.Map;

public class Node {

	/** Unique identifier */
	private Integer id;
	
	/** Child nodes to this one */
	private Map<Character, Node> children = new HashMap<Character, Node>();

	/** Content of the node if there's any */
	private String content;

	public Node(Integer id) {
		this.id = id;
	}
	
	/**
	 * Adds a node for the given character
	 * @param c
	 * 		Character to add to the node
	 * @return
	 * 		The node that correspond to the given character
	 */
	public Node addChildNode(Character c, Integer id) {
		Node emptyNode = new Node(id);
		children.put(toLowerCase(c), emptyNode);
		return emptyNode;
	}

	/**
	 * Obtains the child node associated with the given {@link Character}.
	 * 
	 * @param c
	 * 		Character whose node is going to be looked for
	 * @return
	 * 		The child node associated
	 */
	public Node getChildNode(Character c) {
		return children.get(toLowerCase(c));
	}
	
	public Map<Character, Node> getChildren() {
		return children;
	}
	
	/**
	 * Removes the child node for the given {@link Character} if it is present.
	 * 
	 * @param c
	 * 		Character whose node is going to be removed
	 */
	public Node removeChildNode(Character c) {
		return children.remove(toLowerCase(c));
	}
	
	/**
	 * Returns true if this node contains a child node for the given 
	 * {@link Character}.
	 * 
	 * @param c
	 * 		Character to verify
	 * @return
	 * 		True if the requested child node exists
	 */
	public Boolean containsChildNode(Character c) {
		return children.containsKey(c);
	}
	
	public Boolean hasChildren() {
		return children.size() > 0;
	}
	
	private Character toLowerCase(Character c) {
		return String.valueOf(c).toLowerCase().charAt(0);
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public Integer getId() {
		return id;
	}
}
