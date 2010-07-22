package view.animation.trie;

import view.animation.common.AbstractUndoAnimationSteps;

import java.awt.Color;
import java.util.ArrayList;

import view.collection.trie.TrieMessages;
import view.collection.trie.TrieView;
import view.command.common.ChangeColorNodeShapeCommand;
import view.command.common.Command;
import view.command.common.PaintCommand;
import view.command.common.SelectElementViewCommand;
import view.command.common.ShowMessageCommand;
import view.command.common.StopFlashingElementViewCommand;
import view.command.trie.ChangeColorNodeCommand;
import view.command.trie.StepFinishedCommand;
import view.element.trie.AbstractTrieNodeView;
import view.element.trie.WordView;

/**
 * Crea los pasos necesarios para mostrar que no puede eliminarse un nodo del
 * trie.
 * 
 * @author Agustina
 */
public class CannotRemoveNodeAnimationSteps extends AbstractUndoAnimationSteps {
  private TrieView trieView;
  private int index;

  /**
   * @param trieView vista del trie.
   * @param index indice le letra a eliminar
   */
  public CannotRemoveNodeAnimationSteps(TrieView trieView, int index) {
    super();
    this.trieView = trieView;
    this.index = index;
  }

  @Override
  protected void initializeListSteps() {
    steps = new ArrayList<Command>();

    // Seleccion del nodo que no puede ser eliminado
    WordView word = this.trieView.getWord();
    AbstractTrieNodeView node = word.getAssociateNode(index);

    Color finalColor = AbstractTrieNodeView.Colors.NODE_CANNOT_REMOVE.getColor();

    steps.add(new StopFlashingElementViewCommand(trieView, trieView.getWord()));
    steps.add(new SelectElementViewCommand(trieView, trieView.getFlashingDelay(), node, finalColor));
    steps.add(new ShowMessageCommand(trieView.getController(), TrieMessages
        .getInstance().getMessageCannotRemoveNode(node.getData())));
    steps.addAll( trieView.getPaintCommands() );
    steps.add(new ChangeColorNodeShapeCommand(word.getLetter(index), finalColor));
    steps.add(new ChangeColorNodeCommand(node, finalColor));
    steps.add(new PaintCommand(trieView));
    steps.add(new StepFinishedCommand(trieView, this, index - 1, node, false,
        false));

  }

  @Override
  protected void initializeListUndoSteps() {
    undoSteps = new ArrayList<Command>();

    Color color = AbstractTrieNodeView.Colors.NODE_FOUND.getColor();
    WordView word = this.trieView.getWord();
    AbstractTrieNodeView node = word.getAssociateNode(index);

    undoSteps.add(new ShowMessageCommand(trieView.getController(), TrieMessages
        .getInstance().getMessageUndoCannotRemoveNode(node.getData())));
    undoSteps.add(new ChangeColorNodeShapeCommand(word.getLetter(index), color));
    undoSteps.add(new ChangeColorNodeCommand(node, color));
    undoSteps.add(new PaintCommand(trieView));
    undoSteps.add(new StepFinishedCommand(trieView, this, index + 1, node,
        false, true));

  }

}
