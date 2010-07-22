package view.animation.trie;

import java.util.ArrayList;

import view.animation.common.AbstractUndoAnimationSteps;
import view.collection.trie.TrieMessages;
import view.collection.trie.TrieView;
import view.command.common.AddExecutedAnimationCommand;
import view.command.common.Command;
import view.command.common.SelectElementViewCommand;
import view.command.common.ShowMessageCommand;
import view.command.common.StopFlashingElementViewCommand;
import view.element.trie.AbstractTrieNodeView;

/**
 * Crea los pasos necesarios para indicar que un nodo buscado no fue encontrado
 * en el trie.
 * 
 * @author Agustina
 */
public class TrieNodeNotFoundAnimationSteps extends AbstractUndoAnimationSteps {
  private TrieView trieView;
  private AbstractTrieNodeView node;

  /**
   * @param trieView el trieView al que pertenece el nodo
   * @param node nodo no encontrado
   */
  public TrieNodeNotFoundAnimationSteps(TrieView trieView, AbstractTrieNodeView node) {
    super();
    this.trieView = trieView;
    this.node = node;
    this.setRedoPause(false);
    this.setUndoPause(false);
  }

  @Override
  protected void initializeListSteps() {
    steps = new ArrayList<Command>();
    
    if (node.hasParent()) {
      steps.add(new ShowMessageCommand(trieView.getController(), TrieMessages
          .getInstance().getMessageSearchTrieNode(node.getData())));
      steps.add( new StopFlashingElementViewCommand(trieView, trieView.getWord()) );
      steps.add( new SelectElementViewCommand(trieView, trieView.getFlashingDelay(),
              node.getParent(), node.getParent().getFlashingColor()));
      steps.addAll( trieView.getPaintCommands() );;
    }
    
    steps.add(new ShowMessageCommand(trieView.getController(), TrieMessages
        .getInstance().getMessageTrieNodeNotFound()));
    steps.add( new StopFlashingElementViewCommand(trieView, trieView.getWord()) );
    steps.add( new SelectElementViewCommand(trieView, trieView.getFlashingDelay(),
            node, AbstractTrieNodeView.Colors.SELECT_NODE_NOT_FOUND.getColor()));
    steps.addAll( trieView.getPaintCommands() );
    steps.add( new AddExecutedAnimationCommand(trieView, this) );
  }

  @Override
  protected void initializeListUndoSteps() {
    undoSteps = new ArrayList<Command>();
  }
}
