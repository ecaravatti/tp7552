package view.animation.trie;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import view.animation.common.AbstractUndoAnimationSteps;
import view.collection.trie.TrieMessages;
import view.collection.trie.TrieView;
import view.command.common.Command;
import view.command.common.SelectElementViewCommand;
import view.command.common.ShowMessageCommand;
import view.command.common.ShowMessageDialogCommand;
import view.command.common.StopFlashingElementViewCommand;
import view.command.trie.ChangeColorNodeCommand;
import view.command.trie.StepFinishedCommand;
import view.element.trie.AbstractTrieNodeView;

/**
 * Crea los pasos necesarios para indicar que un nodo dato buscado fue
 * encontrado en trie.
 * 
 * 
 */
public class DataTrieNodeFoundAnimationSteps extends AbstractUndoAnimationSteps {
  private static String TITLE = "Warning";
  private static int MESSAGE_TYPE = JOptionPane.WARNING_MESSAGE;
  
  private TrieView trieView;
  private AbstractTrieNodeView node;
  private Color selectionColor;
  
  public DataTrieNodeFoundAnimationSteps(TrieView trieView, AbstractTrieNodeView node, 
      Color selectionColor) {
    super();
    this.trieView = trieView;
    this.node = node;
    this.selectionColor = selectionColor;
  }

  @Override
  protected void initializeListSteps() {
    steps = new ArrayList<Command>();

    steps.add(new ShowMessageCommand(trieView.getController(), TrieMessages
        .getInstance().getMessageDataTrieNodeFound()));

    steps.add( new StopFlashingElementViewCommand(trieView, trieView.getWord()) );
    steps.add(new SelectElementViewCommand(trieView, trieView.getFlashingDelay(),
            node.getParent(), node.getParent().getFlashingColor()));

    steps.addAll( trieView.getPaintCommands() );
    steps.add( new StopFlashingElementViewCommand(trieView, trieView.getWord()) );
    steps.add(new SelectElementViewCommand(trieView, trieView.getFlashingDelay(),
            node, selectionColor));
    steps.addAll( trieView.getPaintCommands() );

    int index = this.trieView.getWord().getSize();

    if (trieView.isRunningInsertion()) {
      steps.add(new ShowMessageDialogCommand(TrieMessages.getInstance()
          .getMessageCannotInsertWord(node.getData()), TITLE, MESSAGE_TYPE));
      trieView.changeTrieColor();
    }  
    
    steps.add(new ChangeColorNodeCommand(node, AbstractTrieNodeView.Colors.NODE_FOUND.getColor()) );
    steps.add(new StepFinishedCommand(trieView, this, index + 1, node, false,
        false));
  }

  @Override
  protected void initializeListUndoSteps() {
    undoSteps = new ArrayList<Command>();

    undoSteps.add(new ChangeColorNodeCommand(node, node.getDefaultColor()));
    undoSteps.add(new ShowMessageCommand(trieView.getController(), TrieMessages
        .getInstance().getMessageUndoDataTrieNodeFound()));
    int index = this.trieView.getWord().getSize();
    undoSteps.add(new StepFinishedCommand(trieView, this, index + 1, node,
        false, true));
  }
}
