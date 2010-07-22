package view.command.trie;

import view.animation.common.UndoAnimationSteps;
import view.collection.trie.TrieMessages;
import view.collection.trie.TrieView;
import view.command.common.Command;
import view.element.trie.AbstractTrieNodeView;
import controller.InteractiveController;

/**
 * Este comando debe ejecutarse cuando se finaliza un paso (modo interactivo)
 * 
 * @author Agustina Freije
 */
public class StepFinishedCommand implements Command {
  private TrieView trieView;
  private UndoAnimationSteps animation;
  private int nextIndex;
  private boolean nextLetter;
  private boolean undo;
  private AbstractTrieNodeView node;

  /**
   * @param trieView trie sobre el que se ha finalizado un paso
   * @param animation animationStep que ha finalizado de ejecutarse
   * @param nextIndex siguiente indice a ser ejecutado
   * @param node nodo que se proceso
   * @param selectNextLetter true si se debe seleccionar la siguente letra
   * @param undo true si el comando se ejecuta cuando se finaliza un paso
   *        deshacer, en caso contrario false.
   */
  public StepFinishedCommand(TrieView trieView, UndoAnimationSteps animation,
      int nextIndex, AbstractTrieNodeView node, boolean selectNextLetter,
      boolean undo) {
    super();
    this.trieView = trieView;
    this.animation = animation;
    this.nextIndex = nextIndex;
    this.undo = undo;
    this.nextLetter = selectNextLetter;
    this.node = node;
  }

  @Override
  public boolean canExecute() {
    return true;
  }

  @Override
  public void execute() {
    InteractiveController controller = trieView.getController();

    if (!undo) {
      trieView.nextAnimation();
      trieView.addExecutedAnimationSteps(animation);
      if (node != null)
        node.stopFlashing();
    }

    if (nextIndex < trieView.getWord().getSize() && nextLetter)
      trieView.getWord().setFlashingLetter(nextIndex,
          trieView.getFlashingDelay());

    if (controller.isInteractive()) {
      trieView.wait(true);
      controller.stepFinished();
      controller.showLogMessage(TrieMessages.getInstance().getMessageNextStep());
    }

    if ((!undo) && (nextIndex >= trieView.getMaxAnimations() || nextIndex < 0)) {
      if (!controller.isInteractive())
        trieView.getController().primitiveFinished();
      else
        trieView.setPrimitiveFinished(true);
    }
    trieView.repaint();
  }

}
