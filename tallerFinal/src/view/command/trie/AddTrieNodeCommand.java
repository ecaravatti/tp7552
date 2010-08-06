package view.command.trie;

import view.collection.trie.TrieView;
import view.command.common.Command;
import view.element.trie.AbstractTrieNodeView;

/**
 * Este comando debe ejecutarse cuando se agrega un nodo al trie.
 * 
 * 
 */
public class AddTrieNodeCommand implements Command {
	private TrieView trieView;
	private AbstractTrieNodeView node;
	private int index;

	/**
	 * @param trieView
	 *            vista a la que pertenecen los nodos
	 * @param node
	 *            nodo a agregar
	 * @param index
	 *            indice del nodo a agregar
	 */
	public AddTrieNodeCommand(TrieView trieView, AbstractTrieNodeView node,
			int index) {
		super();
		this.trieView = trieView;
		this.node = node;
		this.index = index;
	}

	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	public void execute() {
		node.setVisible(true);
		trieView.getWord().setVisible(index, true);
		trieView.repaint();
	}
}
