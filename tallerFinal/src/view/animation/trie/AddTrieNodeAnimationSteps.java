package view.animation.trie;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import view.animation.common.AbstractUndoAnimationSteps;
import view.collection.trie.TrieMessages;
import view.collection.trie.TrieView;
import view.command.common.ChangeColorNodeShapeCommand;
import view.command.common.Command;
import view.command.common.MoveCommand;
import view.command.common.PaintCommand;
import view.command.common.ShowMessageCommand;
import view.command.common.StopFlashingElementViewCommand;
import view.command.trie.InsertNodeCommand;
import view.command.trie.MakeInvisibleCommand;
import view.command.trie.MoveFirstChildCommand;
import view.command.trie.StepFinishedCommand;
import view.command.trie.TrieViewResizeCommand;
import view.element.trie.AbstractTrieNodeView;
import view.element.trie.TrieNodeView;
import view.shape.NodeShape;
import view.shape.Segment;

/**
 * Crea los pasos necesarios para la animacion que añade un nodo al trie.
 * 
 * 
 */
public class AddTrieNodeAnimationSteps extends AbstractUndoAnimationSteps {
//  private static int DEF_INITIAL = 20;
  
  private TrieView trieView;
  private TrieNodeView parent;
  private AbstractTrieNodeView node;
  private int index;
  private List<TrieNodeView> excNodes;
  private Point2D initPosLetter;
  private Command showMessageCommand;
  private Point2D initialPos;
  private boolean moveFirstChild;

  /**
   * @param trieView trie al que pertenece el nodo a agregar
   * @param searchList lista con los comandos de busqueda
   * @param parent padre del nodo a agregar
   * @param node nodo a agregar
   * @param showMessage comando para mostrar mensaje de insercion
   * @param index indice del nodo a agregar
   */
  public AddTrieNodeAnimationSteps(TrieView trieView, TrieNodeView parent,
      AbstractTrieNodeView node, Command showMessage, int index) {
    super();
    this.trieView = trieView;
    this.parent = parent;
    this.node = node;
    this.index = index;
    this.excNodes = new ArrayList<TrieNodeView>();
    this.initPosLetter = null;
    this.showMessageCommand = showMessage;
    this.moveFirstChild = false;
  }

  @Override
  public List<Command> getSteps() {
    return super.getSteps();
  }

  @Override
  protected void initializeListSteps() {
    steps = new ArrayList<Command>();
    this.excNodes.add(parent);

    NodeShape letter = this.trieView.getWord().getLetter(index);
    initPosLetter = letter.getPosition();

    node.setInvisible(false);
    trieView.getWord().associate(index, node);

    // Calculo de profundides debido a la insercion del nodo
    steps.addAll( trieView.getPaintCommands() );
    steps.add(showMessageCommand);

    if (node.hasParent()){
      node.getParent().getSourceNode().stopFlashing();
      if ( parent.added == TrieNodeView.ADDED_CHILD){
        steps.add( new StopFlashingElementViewCommand(trieView, parent.getChild().getSibling()) );
      }
      else if ( parent.added == TrieNodeView.ADDED_SIBL){
        steps.add( new StopFlashingElementViewCommand(trieView, parent.getSibling().getSibling()) );
      }
    }
    
    steps.add(new MakeInvisibleCommand(node, false));

    // Movimiento de la letra hasta la posicion de insercion y
    // redimensionamiento
    // del arbol para hacer lugar al nodo agregado (simultaneamente)
    Point2D p1 = letter.getPosition();
    Point2D p2 = node.getPosition();

    steps.add( new StopFlashingElementViewCommand(trieView, trieView.getWord()) );
    Segment mov1 = new Segment(p1, p2, trieView.getDelta());
    this.initAddNode(steps, mov1.getPoints(), trieView.getDelta());

    this.moveForwardFirstChild();
    

    steps.add(new PaintCommand(trieView));
    steps.add(new PaintCommand(trieView));
    steps.add(new PaintCommand(trieView));
    steps.add(new InsertNodeCommand(trieView, node, index));
    steps.add(new PaintCommand(trieView));
    steps.add(new PaintCommand(trieView));

    // Movimiento de la letra desde el lugar de insercion a la posicion final
//    Point2D finalPos = this.getFinalPosNode();
//
//    AnimationSteps move = new MobileAnimationSteps(trieView, letter, node
//        .getPosition(), new Point2D.Double(DEF_INITIAL, finalPos.getY()),
//        trieView.getDelta());
//    steps.addAll(move.getSteps());

    // Cambio de color de la letra
//    Color color = AbstractTrieNodeView.Colors.NODE_ADDED.getColor();
//    steps.add(new ChangeColorNodeShapeCommand(letter, color));
//    steps.addAll( trieView.getPaintCommands() );

    // Dimensionar el trie al tamaño final
    this.resizeTrie(steps);
    steps.addAll( trieView.getPaintCommands() );

    // Finalizacion de la insercion
//    steps.add(new ChangeColorNodeCommand(node, color) );
    steps.add(new StepFinishedCommand(trieView, this, index + 1, node, true,
        false));

  }

  @Override
  protected void initializeListUndoSteps() {
    undoSteps = new ArrayList<Command>();

    node.setInvisible(true);
    undoSteps.add(new MakeInvisibleCommand(node, true));
    undoSteps.add(new ShowMessageCommand(trieView.getController(), TrieMessages
        .getInstance().getMessageUndoTrieNodeAdded(node.getData())));

    // Mueve la letra a la posicion inicial y vuelve a dimensionar el arbol
    // de acuerdo a la insercion que se deshace
    undoSteps.add( new StopFlashingElementViewCommand(trieView, trieView.getWord()) );
    moveBackwardFirstChild();
    NodeShape letter = trieView.getWord().getLetter(index);
    Segment segment = new Segment(letter.getPosition(), initPosLetter, trieView
        .getDelta());
    initAddNode(undoSteps, segment.getPoints(), -trieView.getDelta());
    
    // Cuando llega a la posicion final cambia el color de la letra y
    // finaliza el deshacer
    undoSteps.add(new ChangeColorNodeShapeCommand(letter, letter
        .getDefaultNodeColor()));
    undoSteps.add(new StepFinishedCommand(trieView, this, index, node, true,
        true));
  }

  /**
   * Agrega los comandos para inicializar el mov. de la letra y el resize del
   * arbol
   * @param commands lista de comandos
   * @param moveLetter los puntos por los que debe pasar la letra
   * @param delta la variacion del paso
   */
  private void initAddNode(List<Command> commands,
    ArrayList<Point2D> moveLetter, double delta) {

    NodeShape letter = this.trieView.getWord().getLetter(index);

    trieView.calculateDepths();
    List<TrieNodeView> list = new ArrayList<TrieNodeView>();
    int resizeSteps = trieView.getSiblingResizeSteps(delta);

    int min = Math.min(resizeSteps, moveLetter.size());

    for (int i = 1; i < min; i++)
      commands.add(new InitResizeCommand(trieView, letter, moveLetter.get(i),
          delta));

    for (int i = min; i < resizeSteps; i++)
      commands.add(new TrieViewResizeCommand(trieView, list, delta));

    if (trieView.getWord().isVisible(index)) {
      for (int i = min; i < moveLetter.size(); i++)
        commands.add(new MoveCommand(trieView, letter, moveLetter.get(i)));
    }

  }

  private void moveForwardFirstChild() {

    moveFirstChild = node.hasParent() && parent.isMoveFirstChild();

    if (!moveFirstChild)
      return;

    this.initialPos = parent.getChild().getPosition();

    int stepsMove = parent.getMoveFirstChildSteps(trieView.getDelta());
    for (int i = 0; i < stepsMove; i++)
      steps.add(new MoveFirstChildCommand(trieView, parent, parent
          .getFinalPositonFirstChild(), trieView.getDelta(), false));
  }

  private void moveBackwardFirstChild() {

    if (!moveFirstChild)
      return;

    parent.setFinalPositionFirstChild(initialPos);
    int stepsMove = parent.getMoveFirstChildSteps(trieView.getDelta());
    for (int i = 0; i < stepsMove; i++)
      undoSteps.add(new MoveFirstChildCommand(trieView, parent, initialPos,
          -trieView.getDelta(), true));

  }

//  private Point2D getFinalPosNode() {
//    if (node.hasParent())
//      return node.getFinalPosition();
//    
//    return node.getPosition();
//  }

  /**
   * Agraga los comandos necesarios para redimensionar el trie
   * @param commands lista de comandos
   */
  private void resizeTrie(List<Command> commands) {
    excNodes.clear();
    int resizeSteps = trieView.getResizeSteps(trieView.getDelta());
    for (int i = 0; i < resizeSteps; i++)
      commands.add(new TrieViewResizeCommand(trieView, excNodes, trieView
          .getDelta()));
  }

  private class InitResizeCommand implements Command {
    private TrieView trieView;
    private NodeShape letter;
    private Point2D point;
    private double delta;

    public InitResizeCommand(TrieView trieView, NodeShape letter,
        Point2D point, double delta) {
      super();
      this.trieView = trieView;
      this.letter = letter;
      this.point = point;
      this.delta = delta;
    }

    @Override
    public boolean canExecute() {
      return true;
    }

    @Override
    public void execute() {
      this.letter.moveTo(point);
      this.trieView.resizeSiblings(delta);
      this.trieView.repaint();
    }
  }
}
