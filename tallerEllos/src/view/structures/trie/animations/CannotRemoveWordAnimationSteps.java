package view.structures.trie.animations;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import view.commons.animations.AbstractUndoAnimationSteps;
import view.commons.commands.Command;
import view.commons.commands.ShowMessageCommand;
import view.commons.commands.ShowMessageDialogCommand;
import view.commons.commands.StopFlashingElementViewCommand;
import view.structures.trie.TrieMessages;
import view.structures.trie.TrieView;
import view.structures.trie.commands.StepFinishedCommand;

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