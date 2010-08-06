package view.command.trie;

import java.util.ArrayList;
import java.util.List;

import view.collection.trie.TrieView;
import view.command.common.Command;
import view.element.trie.TrieNodeView;

/**
 * Este comando debe ejecutarse cuando se debe redimensionar cada nodo del trie
 */
public class TrieViewResizeCommand implements Command {
	private TrieView trie;
	private double delta;
	private List<TrieNodeView> excNodes;

	/**
	 * @param trie
	 *            trie a redimensionar
	 * @param excNodes
	 *            nodos a que no deben variar.
	 * @param delta
	 *            paso utilizado para redimensionar.
	 */
	public TrieViewResizeCommand(TrieView trie, List<TrieNodeView> excNodes,
			double delta) {
		super();
		this.trie = trie;
		this.delta = delta;
		this.excNodes = new ArrayList<TrieNodeView>(excNodes);
	}

	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	public void execute() {
		trie.resize(excNodes, delta);
		trie.repaint();
	}
}
