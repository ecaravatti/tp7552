package view.structures.trie.animations;

import view.commons.animations.AbstractUndoAnimationSteps;
import java.awt.Color;
import java.util.ArrayList;

import view.structures.trie.TrieMessages;
import view.structures.trie.TrieView;
import view.structures.trie.commands.ChangeColorNodeCommand;
import view.commons.commands.ChangeColorNodeShapeCommand;
import view.commons.commands.Command;
import view.commons.commands.PaintCommand;
import view.commons.commands.SelectElementViewCommand;
import view.commons.commands.ShowMessageCommand;
import view.structures.trie.commands.StepFinishedCommand;
import view.commons.commands.StopFlashingElementViewCommand;
import view.structures.trie.elements.AbstractTrieNodeView;
import view.structures.trie.elements.WordView;

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
