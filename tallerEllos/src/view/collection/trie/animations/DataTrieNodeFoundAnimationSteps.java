package view.structures.trie.animations;

import view.commons.animations.AbstractUndoAnimationSteps;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import view.structures.trie.TrieMessages;
import view.structures.trie.TrieView;
import view.structures.trie.commands.ChangeColorNodeCommand;
import view.commons.commands.Command;
import view.commons.commands.SelectElementViewCommand;
import view.commons.commands.ShowMessageCommand;
import view.commons.commands.ShowMessageDialogCommand;
import view.structures.trie.commands.StepFinishedCommand;
import view.commons.commands.StopFlashingElementViewCommand;
import view.structures.trie.elements.AbstractTrieNodeView;

/**
 * Crea los pasos necesarios para indicar que un nodo dato buscado fue
 * encontrado en trie.
 * 
 * @author Agustina
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

    if (trieView.isRunningInsertion()){
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
