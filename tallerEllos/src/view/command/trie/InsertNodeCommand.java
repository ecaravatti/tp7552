package view.command.trie;

import view.collection.trie.TrieView;
import view.command.common.Command;
import view.element.trie.AbstractTrieNodeView;

import java.awt.Color;


/**
 * Este comando debe ejecutarse cuando se ha insertado un nodo.
 * 
 * @author Agustina Freije
 */
public class InsertNodeCommand implements Command {
  private TrieView trie;
  private AbstractTrieNodeView node;

  /**
   * @param trie el trie en el que se ha insertado el nodo.
   * @param node el nodo que se ha insertado.
   */
  public InsertNodeCommand(TrieView trie, AbstractTrieNodeView node) {
    super();
    this.trie = trie;
    this.node = node;
  }

  @Override
  public boolean canExecute() {
    return true;
  }

  @Override
  public void execute() {
    node.setVisible(true);
    node.setInvisible(false);
    node.setFlashingColor(Color.yellow.brighter());
    node.setFlashing(trie.getFlashingDelay());
    trie.repaint();
  }
}
