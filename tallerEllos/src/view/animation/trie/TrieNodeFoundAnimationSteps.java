package view.animation.trie;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import view.animation.common.AbstractUndoAnimationSteps;
import view.animation.common.AnimationSteps;
import view.animation.common.MobileAnimationSteps;
import view.collection.trie.TrieMessages;
import view.collection.trie.TrieView;
import view.command.common.ChangeColorNodeShapeCommand;
import view.command.common.Command;
import view.command.common.SelectElementViewCommand;
import view.command.common.ShowMessageCommand;
import view.command.common.StopFlashingElementViewCommand;
import view.command.trie.ChangeColorNodeCommand;
import view.command.trie.StepFinishedCommand;
import view.element.trie.AbstractTrieNodeView;
import view.shape.NodeShape;

/**
 * Crea los pasos necesarios para indicar que un nodo buscado fue encontrado en
 * trie.
 * 
 * 
 */
public class TrieNodeFoundAnimationSteps extends AbstractUndoAnimationSteps {
  private static int DEF_INITIAL = 20;
  
  private AbstractTrieNodeView node;
  private TrieView trieView;
  private int index;
  private Point2D initPosLetter;


  /**
   * @param trieView vista a la que pertenece el nodo
   * @param searchList lista con los comandos de busqueda
   * @param node nodo encontrado
   * @param selectionColor color de seleccion para el nodo encontrado
   * @param index indice de la palabra que represente a el nodo
   */
  public TrieNodeFoundAnimationSteps(TrieView trieView, AbstractTrieNodeView node, int index) {
    super();
    this.trieView = trieView;
    this.node = node;
    this.index = index;
    this.initPosLetter = null;
  }

  @Override
  public List<Command> getSteps() {
    return super.getSteps();
  }

  @Override
  protected void initializeListSteps() {
    steps = new ArrayList<Command>();

    // Seleccion del nodo buscado
    steps.addAll( trieView.getPaintCommands() );
    if (node.hasParent()) {
      steps.add(new ShowMessageCommand(trieView.getController(), TrieMessages
          .getInstance().getMessageSearchTrieNode(node.getData())));
      steps.add( new StopFlashingElementViewCommand(trieView, trieView.getWord()) );
      steps.add( new SelectElementViewCommand(trieView, trieView.getFlashingDelay(),
              node.getParent(), node.getParent().getFlashingColor()));
      steps.addAll( trieView.getPaintCommands() );
    }
    steps.add( new StopFlashingElementViewCommand(trieView, trieView.getWord()) );
    steps.add(new SelectElementViewCommand(trieView, trieView.getFlashingDelay(),
            node, AbstractTrieNodeView.Colors.SELECT_NODE_FOUND.getColor()));
    steps.add(new ShowMessageCommand(trieView.getController(), TrieMessages
        .getInstance().getMessageTrieNodeFound()));

    trieView.getWord().associate(index, node);

    // Muevo la letra a la posicion final
    NodeShape letter = trieView.getWord().getLetter(index);
    initPosLetter = letter.getPosition();
    Point2D initPos = initPosLetter;
    Point2D finPos = new Point2D.Double(DEF_INITIAL, letter.getPosition()
        .getY());
    
    steps.add( new StopFlashingElementViewCommand(trieView, trieView.getWord()) );
    this.moveLetter(steps, letter, initPos, finPos, trieView.getDelta());

    initPos = finPos;
    finPos = new Point2D.Double(initPos.getX(), node.getPosition().getY());
    this.moveLetter(steps, letter, initPos, finPos, trieView.getDelta() + 2);

    Color color;
    // Finalizacion de la insercion
    if (trieView.isRunningInsertion()) 
      color = AbstractTrieNodeView.Colors.NODE_ADDED.getColor();
    else 
      color = AbstractTrieNodeView.Colors.NODE_FOUND.getColor();

    steps.add(new ChangeColorNodeShapeCommand(letter, color));
    steps.add(new ChangeColorNodeCommand(node, color) );
    steps.add(new StepFinishedCommand(trieView, this, index + 1, node, true,
        false));
  }

  @Override
  protected void initializeListUndoSteps() {
    undoSteps = new ArrayList<Command>();

    undoSteps.add(new ShowMessageCommand(trieView.getController(), TrieMessages
        .getInstance().getMessageUndoTrieNodeFound(node.getData())));
    NodeShape letter = trieView.getWord().getLetter(index);
    AnimationSteps move = new MobileAnimationSteps(trieView, letter, letter
        .getPosition(), initPosLetter, trieView.getDelta());
    undoSteps.add( new StopFlashingElementViewCommand(trieView, trieView.getWord()) );
    undoSteps.addAll(move.getSteps());
    undoSteps.add(new ChangeColorNodeCommand(node, node.getDefaultColor()));
    undoSteps.add(new ChangeColorNodeShapeCommand(letter, letter
        .getDefaultNodeColor()));
    undoSteps.add(new StepFinishedCommand(trieView, this, index, node, true,
        true));

  }

  private void moveLetter(List<Command> list, NodeShape letter,
      Point2D initPos, Point2D finPos, double delta) {
    AnimationSteps move = new MobileAnimationSteps(trieView, letter, initPos,
        finPos, delta);
    list.addAll(move.getSteps());
    steps.addAll( trieView.getPaintCommands() );
  }

}
