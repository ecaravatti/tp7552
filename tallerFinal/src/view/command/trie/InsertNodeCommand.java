package view.command.trie;

import view.collection.trie.TrieView;
import view.command.common.Command;
import view.element.trie.AbstractTrieNodeView;
import view.shape.DefaultShapeSettings;

/**
 * Este comando debe ejecutarse cuando se ha insertado un nodo.
 * 
 * 
 */
public class InsertNodeCommand implements Command {
	private TrieView trie;
	private AbstractTrieNodeView node;
	private int letterIndex;

	/**
	 * @param trie
	 *            el trie en el que se ha insertado el nodo.
	 * @param node
	 *            el nodo que se ha insertado.
	 */
	public InsertNodeCommand(TrieView trie, AbstractTrieNodeView node,
			int letterIndex) {
		super();
		this.trie = trie;
		this.node = node;
		this.letterIndex = letterIndex;
	}

	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	public void execute() {
		trie.getWord().setVisible(letterIndex, false);
		node.setVisible(true);
		node.setInvisible(false);
		node.setFlashingColor(DefaultShapeSettings.ORANGE_COLOR);
		node.setFlashing(trie.getFlashingDelay());
		trie.repaint();
	}
}
