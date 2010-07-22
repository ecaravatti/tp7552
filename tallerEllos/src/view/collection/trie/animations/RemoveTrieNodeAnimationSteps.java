package view.structures.trie.animations;

import view.commons.animations.AbstractUndoAnimationSteps;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import view.structures.trie.TrieMessages;
import view.structures.trie.TrieView;
import view.structures.trie.commands.AddTrieNodeCommand;
import view.commons.commands.Command;
import view.structures.trie.commands.EliminateTrieNodeCommand;
import view.structures.trie.commands.MakeInvisibleCommand;
import view.structures.trie.commands.MakeTransparentNodeCommand;
import view.structures.trie.commands.MoveFirstChildCommand;
import view.commons.commands.SelectElementViewCommand;
import view.commons.commands.ShowMessageCommand;
import view.structures.trie.commands.StepFinishedCommand;
import view.commons.commands.StopFlashingElementViewCommand;
import view.structures.trie.commands.TrieViewResizeCommand;
import view.structures.trie.elements.AbstractTrieNodeView;
import view.structures.trie.elements.TrieNodeView;

/**
 * Crea los pasos necesarios para la animacion que elimina un nodo del trie
 * 
 * @author Agustina
 */
public class RemoveTrieNodeAnimationSteps extends AbstractUndoAnimationSteps {
  private TrieView trieView;
  private TrieNodeView parent;
  private AbstractTrieNodeView node;
  private int index;
  private Point2D childPos;
  private Point2D siblingPos;
  private boolean moveFirstChild;
  private Command addNode;
  private int resizeSteps;

  /**
   * @param trieView vista del trie
   * @param parent padre del nodo a eliminar
   * @param addNode comando para agregar el nodo
   * @param node nodo a eliminar
   * @param index indice de la letra a eliminar
   */
  public RemoveTrieNodeAnimationSteps(TrieView trieView, TrieNodeView parent,
      Command addNode, AbstractTrieNodeView node, int index) {
    super();
    this.trieView = trieView;
    this.parent = parent;
    this.node = node;
    this.index = index;
    this.addNode = addNode;
    this.childPos = null;
    this.moveFirstChild = false;
  }

  @Override
  protected void initializeListSteps() {
    steps = new ArrayList<Command>();

    steps.addAll( trieView.getPaintCommands() );

    steps.add(new ShowMessageCommand(trieView.getController(), TrieMessages
        .getInstance().getMessageTrieNodeRemoved(node.getData())));

    // Selecciono el puntero al nodo a eliminar
    if (node.hasParent()) {
      steps.add( new StopFlashingElementViewCommand(trieView, trieView.getWord()) );
      steps.add(new SelectElementViewCommand(trieView, trieView.getFlashingDelay(),
              node.getParent(), node.getParent().getFlashingColor()));
      steps.addAll( trieView.getPaintCommands() );
    }

    // Selecciono el nodo
    if ((!node.hasParent()) || (parent.hasDataNode() && !parent.getDataNode().isToEliminate())
        || !parent.hasDataNode()) {
      steps.add( new StopFlashingElementViewCommand(trieView, trieView.getWord()) );
      steps.add(new SelectElementViewCommand(trieView, trieView.getFlashingDelay(),
              node, AbstractTrieNodeView.Colors.SELECT_NODE_FOUND.getColor()));
      steps.addAll( trieView.getPaintCommands() );
    }

    // El nodo debe ser transparente
    this.steps.add(new MakeTransparentNodeCommand(node, true));
    steps.addAll( trieView.getPaintCommands() );

    // Se muestra la eliminacion del nodo
    steps.add(new EliminateTrieNodeCommand(trieView, parent, node, index));

    // Muevo el primer hijo si es necesario
    if (node.hasParent())
      moveBackwardFirstChild();

    node.setInvisible(true);
    resizeSteps = trieView.getResizeSteps(trieView.getDelta());
    steps.addAll(getListResizeSteps(-trieView.getDelta()));

    if ( parent.hasSibling() )
      siblingPos = parent.getSibling().getPosition();
    
    moveTrie( steps, -trieView.getDelta() );
    
    // Finaliza la eliminacion del nodo
    steps.add(new StepFinishedCommand(trieView, this, index - 1, node, false,
        false));
  }

  @Override
  protected void initializeListUndoSteps() {
    undoSteps = new ArrayList<Command>();

    undoSteps.add(new ShowMessageCommand(trieView.getController(), TrieMessages
        .getInstance().getMessageUndoTrieNodeRemoved(node.getData())));

    List<Command> list = new ArrayList<Command>();
    moveTrie( list, trieView.getDelta() );

    for (Command command : list)
      command.execute();

    // Agrego el nodo
    moveTrie( undoSteps, trieView.getDelta() );
    addNode.execute();

    // Restaura el trie al tamano original
    undoSteps.add(addNode);
    undoSteps.add(new MakeInvisibleCommand(node, false));
    undoSteps.addAll(getListResizeSteps(trieView.getDelta()));

    // Muevo el primer hijo a la posicion original
    moveForwardFirstChild();
    
    // Muestro el nodo agregado
    undoSteps.add(new AddTrieNodeCommand(trieView, node, index));

    this.undoSteps.add(new MakeTransparentNodeCommand(node, false));
    undoSteps.add(new StepFinishedCommand(trieView, this, index + 1, node,
        false, true));
  }

  private void moveBackwardFirstChild() {

    this.moveFirstChild = parent.isMoveFirstChild();
    if (!moveFirstChild)
      return;

    if (parent.hasDataNode() && parent.getDataNode().isToEliminate())
      this.childPos = parent.getChild().getPosition();
    else
      this.childPos = parent.getChild().getSibling().getPosition();

    int stepsMove = parent.getMoveFirstChildSteps(trieView.getDelta());

    for (int i = 0; i < stepsMove; i++)
      steps.add(new MoveFirstChildCommand(trieView, parent, parent
          .getFinalPositonFirstChild(), -trieView.getDelta(), true));
  }

  private void moveForwardFirstChild() {

    if (!moveFirstChild)
      return;

    parent.setFinalPositionFirstChild(childPos);
    int stepsMove = parent.getMoveFirstChildSteps(trieView.getDelta());
    for (int i = 0; i < stepsMove; i++)
      undoSteps.add(new MoveFirstChildCommand(trieView, parent, childPos,
          trieView.getDelta(), false));
  }

  /**
   * Crea la lista con los comandos para redimensionar
   * @param delta variacion del paso
   */
  private List<Command> getListResizeSteps(double delta) {
    List<TrieNodeView> excNodes = new ArrayList<TrieNodeView>();
    List<Command> commands = new ArrayList<Command>();

    for (int i = 0; i < resizeSteps; i++)
      commands.add(new TrieViewResizeCommand(trieView, excNodes, delta));

    return commands;
  }
  
  private void moveTrie(List<Command> list, double delta) {
    
    if ( !node.hasParent() && parent.hasSibling() ){
      
      double dist = parent.getPosition().distance( siblingPos );
      int moveDist = new Double(Math.ceil(dist/trieView.getDelta())).intValue();
      
      for (int i = 0; i < moveDist; i++)
        list.add( new MoveTrieCommand( delta ) );
      
    }
  }
  
  private class MoveTrieCommand implements Command{
    private double delta;
    
    
    public MoveTrieCommand(double delta) {
      super();
      this.delta = delta;
    }

    @Override
    public boolean canExecute() {
      return true;
    }

    @Override
    public void execute() {
      if (trieView.getRoot() != parent){
        trieView.moveTrie( delta );
        trieView.repaint();
      }
    }
  }
}
