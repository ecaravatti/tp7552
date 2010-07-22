package view.animation.trie;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import view.animation.common.AbstractUndoAnimationSteps;
import view.collection.trie.TrieMessages;
import view.collection.trie.TrieView;
import view.command.common.Command;
import view.command.common.ShowMessageCommand;
import view.command.common.ShowMessageDialogCommand;
import view.command.common.StopFlashingElementViewCommand;
import view.command.trie.StepFinishedCommand;

public class CannotRemoveWordAnimationSteps extends AbstractUndoAnimationSteps {
  private static String TITLE = "Warning";
  private static int MESSAGE_TYPE = JOptionPane.WARNING_MESSAGE;
  
  private String word;
  private TrieView trieView;
  private int index;
  
  public CannotRemoveWordAnimationSteps(TrieView trieView, String word, int index) {
    super();
    this.word = word;
    this.trieView = trieView;
    this.index = index;
  }

  @Override
  protected void initializeListSteps() {
    steps = new ArrayList<Command>();
    steps.add( new ShowMessageCommand(trieView.getController(), TrieMessages.getInstance()
        .getMessageCannotRemoveWord(word) ) );
    trieView.changeTrieColor();
    steps.add( new StopFlashingElementViewCommand(trieView, trieView.getWord()));
    steps.add( new ShowMessageDialogCommand( TrieMessages.getInstance()
        .getMessageCannotRemoveWord(word), TITLE, MESSAGE_TYPE) );
    steps.add(new StepFinishedCommand(trieView, this, index + 1, null, false,
        false));
  }

  @Override
  protected void initializeListUndoSteps() {
    undoSteps = new ArrayList<Command>();
  }
}