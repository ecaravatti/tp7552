package view.command.trie;

import view.collection.trie.TrieView;
import view.command.common.Command;
import view.element.trie.AbstractTrieNodeView;
import view.element.trie.TrieNodeView;

/**
 * Este comando debe ejecutarse cuando a un nodo debe eliminarse alguno de sus
 * hijos
 */
public class RemoveTrieNodeCommand implements Command {
	private TrieNodeView parent;
	private TrieView trieView;
	private AbstractTrieNodeView node;
	private int index;

	/**
	 * @param trieView
	 *            vista a la que pertenece el nodo
	 * @param parent
	 *            nodo a eliminar uno de sus hijos
	 * @param node
	 *            nodo a eliminar
	 * @param index
	 *            indice del nodo a eliminar
	 */
	public RemoveTrieNodeCommand(TrieView trieView, TrieNodeView parent,
			AbstractTrieNodeView node, int index) {
		super();
		this.parent = parent;
		this.node = node;
		this.trieView = trieView;
		this.index = index;
	}

	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	public void execute() {

		if (node.hasParent())
			this.parent.eliminate();
		else {
			TrieNodeView newRoot = trieView.getRoot().getSibling();
			if (newRoot != null)
				newRoot.setParent(trieView.getRoot().getParent());
			trieView.setRoot(newRoot);
		}

		this.trieView.getWord().setVisible(index, false);
		this.trieView.repaint();
	}
}
