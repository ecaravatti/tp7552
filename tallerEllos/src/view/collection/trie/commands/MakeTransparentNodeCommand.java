package view.structures.trie.commands;

import view.commons.commands.Command;
import view.structures.trie.elements.AbstractTrieNodeView;

/**
 * Este comando debe invocarse cuando se desea que un nodo sea trasparente (o
 * no)
 * 
 * @author Agustina
 */
public class MakeTransparentNodeCommand implements Command {
  private AbstractTrieNodeView node;
  private boolean transparent;

  /**
   * @param node nodo que debe ser transparente (o no)
   * @param transparent true si debe ser transparente, false en caso contrario.
   */
  public MakeTransparentNodeCommand(AbstractTrieNodeView node,
      boolean transparent) {
    super();
    this.node = node;
    this.transparent = transparent;
  }

  /*
   * (non-Javadoc)
   * @see view.trie.commands.Command#canExecute()
   */
  public boolean canExecute() {
    return true;
  }

  /*
   * (non-Javadoc)
   * @see view.trie.commands.Command#execute()
   */
  public void execute() {
    node.stopFlashing();
    node.setTransparent(transparent);
  }

}
