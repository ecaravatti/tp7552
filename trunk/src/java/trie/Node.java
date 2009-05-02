package trie;

import java.util.ArrayList;
import java.util.List;

public class Node {

	/** Child nodes to this one */
	List<Node> childs = new ArrayList<Node>();
	
	/** Value of the node */
	Character value;
	
	/** Content of the node if there's any */
	String content;
	
	/** Depth of the node inside the trie */
	Integer depth;
	
}
