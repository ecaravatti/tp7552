package view.command.trie;

import view.collection.trie.TrieView;
import view.command.common.Command;
import view.element.trie.TrieNodeView;

import java.awt.geom.Point2D;


/**
 * Este comando debe ejecutarse cuando se desea mover el primer hijo del nodo
 * 
 * @author Agustina
 */
public class MoveFirstChildCommand implements Command {
  private TrieView trieView;
  private TrieNodeView node;
  private double delta;
  private boolean back;
  private Point2D finalPos;

  /**
   * @param trieView vista a la que pertenece el nodo
   * @param node nodo al que se debe mover su hijo
   * @param finalPosChild posicion final del hijo.
   * @param delta variacion en el moviento del hijo
   * @param back true si debe moverse atras, false en caso contrario
   */
  public MoveFirstChildCommand(TrieView trieView, TrieNodeView node,
      Point2D finalPosChild, double delta, boolean back) {
    super();
    this.trieView = trieView;
    this.node = node;
    this.delta = delta;
    this.back = back;
    this.finalPos = finalPosChild;
  }

  @Override
  public boolean canExecute() {
    return true;
  }

  @Override
  public void execute() {
    node.setFinalPositionFirstChild(finalPos);
    
    if (back)
      node.moveBackwardFirstChild(delta);
    else
      node.moveForwardFirstChild(delta);
    
    trieView.repaint();
  }
}
