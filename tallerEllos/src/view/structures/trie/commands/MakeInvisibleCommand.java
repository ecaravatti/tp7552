package view.structures.trie.commands;

import view.commons.commands.Command;
import view.structures.trie.elements.AbstractTrieNodeView;

/**
 * Este comando debe ejecutarse cuando se desea que un elemento sea invisible (o
 * no)
 * 
 * @author Agustina
 */
public class MakeInvisibleCommand implements Command {
  private AbstractTrieNodeView node;
  private boolean invisible;

  /**
   * @param node nodo a determina si debe ser o no visible.
   * @param invisible true si debe ser invisible, en caso contrario false.
   */
  public MakeInvisibleCommand(AbstractTrieNodeView node, boolean invisible) {
    super();
    this.node = node;
    this.invisible = invisible;
  }

  @Override
  public boolean canExecute() {
    return true;
  }

  @Override
  public void execute() {

    node.setInvisible(invisible);

    if (node.hasParent())
      node.getParent().getSourceNode().stopFlashing();

    if (invisible) {
      node.setVisible(false);
      node.setDefaultDistanceParent();
    }
  }

}
