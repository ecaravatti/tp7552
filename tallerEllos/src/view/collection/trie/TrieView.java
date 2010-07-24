package view.collection.trie;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import model.collection.trie.TrieNode;
import view.animation.common.AnimationSteps;
import view.animation.common.UndoAnimationSteps;
import view.animation.trie.AddTrieNodeAnimationSteps;
import view.animation.trie.CannotRemoveNodeAnimationSteps;
import view.animation.trie.CannotRemoveWordAnimationSteps;
import view.animation.trie.DataTrieNodeFoundAnimationSteps;
import view.animation.trie.RemoveTrieNodeAnimationSteps;
import view.animation.trie.TrieNodeFoundAnimationSteps;
import view.animation.trie.TrieNodeNotFoundAnimationSteps;
import view.command.common.Command;
import view.command.common.CreateAnimationCommand;
import view.command.common.PaintCommand;
import view.command.common.ShowMessageCommand;
import view.command.common.ShowPrimitiveCodeCommand;
import view.command.trie.PreAddTrieNodeCommand;
import view.common.AnimatedPanel;
import view.element.trie.AbstractTrieNodeView;
import view.element.trie.DataTrieNodeView;
import view.element.trie.TrieNodeView;
import view.element.trie.TrieViewPrimitives;
import view.element.trie.WordView;
import view.exception.common.CannotUndoException;
import event.trie.TrieEvent;
import event.trie.TrieListener;

/**
 * Esta clase representa a la vista de un Trie.
 * 
 * 
 */
public class TrieView extends AnimatedPanel implements TrieListener<String>,
        TrieViewPrimitives {

  private static final long serialVersionUID = 1L;
  private final static Point2D DEF_TRIE_POS = new Point2D.Double(70, 80);
  private final static double DEF_WORD_POS_Y = 20;
  private static double DEF_DELTA = 8;
  
  private TrieNodeView root;
  private TrieNodeView current;
  private WordView word;
  private boolean insertion;
  private int wordIndex;
  private int countNodesHor;
  private int countComp;
  private int countNewNodes;
  private int countDelNodes;

  /**
   * Construye un trieView
   */
  public TrieView() {
    super();
    this.root = null;
    this.setDoubleBuffered(true);
    this.word = null;
    this.controller = null;
    this.countNodesHor = 0;
    this.insertion = false;
    this.countComp = 0;
    this.countNewNodes = 0;
    this.countDelNodes = 0;
  }

  @Override
  public void rootAdded(TrieEvent<String> event) {
    TrieNode<String> source = event.getCurrent();
    TrieNodeView aux = this.root;
    this.root = new TrieNodeView(source, DEF_TRIE_POS, null, this);
    this.root.addSibling(aux);
    if (aux != null) {
      this.root.added = TrieNodeView.ADDED_ROOT;
      aux.setParent(root.getSiblingPointerView());
      this.countComp++;
    }
    Command command = new ShowMessageCommand(this.getController(), TrieMessages.getInstance().getMessageTrieNodeAdded(root.getData()));
    this.createAddTrieNodeAnimationSteps(null, getRoot(), command);
  }

  @Override
  public void trieNodeViewChildAdded(TrieNodeView parent, TrieNodeView node) {
    Command command = new ShowMessageCommand(getController(), TrieMessages.getInstance().getMessageTrieNodeAdded(node.getData()));
    this.createAddTrieNodeAnimationSteps(parent, node, command);
  }

  @Override
  public void trieNodeViewSiblingAdded(TrieNodeView parent, TrieNodeView node) {
    Command command = new ShowMessageCommand(getController(), TrieMessages.getInstance().getMessageTrieNodeAdded(node.getData()));
    this.createAddTrieNodeAnimationSteps(parent, node, command);
  }

  @Override
  public void trieNodeViewIsWord(TrieNodeView parent, DataTrieNodeView data) {
    data.calculateWidth((Graphics2D) this.getGraphics());
    Command command = new ShowMessageCommand(getController(), TrieMessages.getInstance().getMessageDataTrieNodeAdded(data.getData()));
    this.createAddTrieNodeAnimationSteps(parent, data, command);
  }

  @Override
  public void trieNodeViewFound(TrieNodeView selected) {
    AnimationSteps animation = new TrieNodeFoundAnimationSteps(this, selected, wordIndex);
    addCommands(animation.getSteps());
    this.current = selected;
    this.wordIndex++;
    this.countComp++;
    setCountAnimations(getCountAnimations() + 1);
  }

  @Override
  public void trieNodeViewNotFound(TrieNodeView selected) {
    TrieNodeNotFoundAnimationSteps animation = new TrieNodeNotFoundAnimationSteps(this, selected);
    addCommands(animation.getSteps());
    //this.addExecutedAnimationSteps(animation);
    this.countComp++;
  }

  @Override
  public void dataTrieNodeViewRemoved(TrieNodeView parent,
          AbstractTrieNodeView node) {
    if (parent.hasChild()) {
      this.incCountNodesHor();
    }

    this.countDelNodes++;
    Command command = new PreAddTrieNodeCommand(this,
            PreAddTrieNodeCommand.AddTrieNode.AddData, parent, node);
    createRemoveTrieNodeAnimationSteps(command, parent, node);
  }

  @Override
  public void dataTrieNodeViewFound(DataTrieNodeView selected,
          Color selectionColor) {
    UndoAnimationSteps animation = new DataTrieNodeFoundAnimationSteps(this,
            selected, selectionColor);
    addCommands(animation.getSteps());
    setCountAnimations(getCountAnimations() + 1);
    this.countComp++;
  }

  @Override
  public void setRoot(TrieNodeView root) {
    this.root = root;
  }

  @Override
  public void firstChildRemoved(TrieNodeView parent, TrieNodeView child) {
    this.wordIndex--;
    this.countDelNodes++;
    if (parent.isEndWord() || child.hasSibling()) {
      incCountNodesHor();
    }

    Command command = new PreAddTrieNodeCommand(this,
            PreAddTrieNodeCommand.AddTrieNode.AddChild, parent, child);
    createRemoveTrieNodeAnimationSteps(command, parent, child);
  }

  @Override
  public void firstSiblingRemoved(TrieNodeView parent, TrieNodeView sibling) {
    this.wordIndex--;
    this.countDelNodes++;
    incCountNodesHor();
    Command command = new PreAddTrieNodeCommand(this,
            PreAddTrieNodeCommand.AddTrieNode.AddSibling, parent, sibling);
    createRemoveTrieNodeAnimationSteps(command, parent, sibling);
  }

  @Override
  public void rootRemoved(TrieEvent<String> event) {
    this.wordIndex--;
    this.countNodesHor++;
    this.countDelNodes++;
    Command command = new PreAddTrieNodeCommand(this,
            PreAddTrieNodeCommand.AddTrieNode.AddRoot, root, root.getSibling());
    createRemoveTrieNodeAnimationSteps(command, getRoot(), getRoot());
  }

  @Override
  public void cannotInsertWord(TrieEvent<String> event) {
    this.dataTrieNodeViewFound(current.getDataNode(), Color.GREEN.brighter());
  }

  @Override
  public void cannotRemoveWord(TrieEvent<String> event) {
    UndoAnimationSteps animation = new CannotRemoveWordAnimationSteps(this, word.getWord(),
            getMaxAnimations());
    getCommandQueue().addCommand(new CreateAnimationCommand(this, animation, getCountAnimations()));
    setCountAnimations(getCountAnimations() + 1);
    if (this.getCountAnimations() >= this.getMaxAnimations()) {
      setMaxAnimations(getMaxAnimations() + 1);
    }
  }

  @Override
  public void cannotRemoveTrieNode(TrieEvent<String> event) {
    this.wordIndex--;
    UndoAnimationSteps animation = new CannotRemoveNodeAnimationSteps(this,
            wordIndex);
    getCommandQueue().addCommand(new CreateAnimationCommand(this, animation,
            getCountAnimations()));
    setCountAnimations(getCountAnimations() + 1);
  }

  @Override
  public void primitiveFinished(TrieEvent<String> event) {
    this.setWait(false);
  }

  /**
   * Agrega una palabra.
   * @param word palabra a agregar
   */
  public void addWord(String word) {
    getCommandQueue().addCommand(new ShowPrimitiveCodeCommand(this, TriePrimitives.insert.getCode()));
    this.initShowWord(word);
    setMaxAnimations(this.word.getSize());
    this.insertion = true;
  }

  /**
   * Elimina una palabra
   * @param word palabra a eliminar
   */
  public void removeWord(String word) {
    getCommandQueue().addCommand(new ShowPrimitiveCodeCommand(this, TriePrimitives.delete.getCode()));
    this.initShowWord(word);
    setMaxAnimations(this.word.getSize() * 2);
    this.insertion = false;
  }

  /**
   * Obtiene la palabra
   * @return la palabra
   */
  public WordView getWord() {
    return this.word;
  }

  @Override
  public TrieNodeView getRoot() {
    return this.root;
  }

  @Override
  public void calculateDepths() {
    this.setDepthSiblingsRec(this.root);
  }

  /**
   * Calcula los pasos para redimensionar todos los nodos.
   * @param delta variacion del paso
   * @return la cantidad de pasos necesarios para redimensionar el nodo
   */
  public int getResizeSteps(double delta) {
    List<TrieNodeView> nodes = new ArrayList<TrieNodeView>();
    return getResizeStepsRec(nodes, this.root, delta, false);
  }

  /**
   * Calcula los pasos para redimensionar todos los nodos sin tener en cuenta
   * los pasos necesarios para redimensionar los nodos pasado como parametro
   * @param nodes node a no incluir en el calculo.
   * @param delta variacion del paso
   * @return la cantidad de pasos necesarios para redimensionar el nodo
   */
  public int getResizeSteps(List<TrieNodeView> nodes, double delta) {
    return getResizeStepsRec(nodes, this.root, delta, false);
  }

  /**
   * Calcula los pasos para redimensionar cada nodo de acuerdo a la profundidad
   * de sus hermanos.
   * @param delta variacion del paso
   * @return la cantidad de pasos necesarios para redimensionar el nodo
   */
  public int getSiblingResizeSteps(double delta) {
    List<TrieNodeView> nodes = new ArrayList<TrieNodeView>();
    return getResizeStepsRec(nodes, this.root, delta, true);
  }

  /**
   * Redimensiona todos los nodos trie menos los pasado como parametro
   * @param nodes nodos a no redimensionar.
   * @param delta variacion del paso.
   */
  public void resize(List<TrieNodeView> nodes, double delta) {
    this.resizeRec(nodes, this.root, delta, false);
  }

  /**
   * Redimensiona todos los nodos trie menos los pasado como parametro de
   * acuerdo a la profundidad de sus hermanos
   * @param delta variacion del paso.
   */
  public void resizeSiblings(double delta) {
    List<TrieNodeView> nodes = new ArrayList<TrieNodeView>();
    this.resizeRec(nodes, this.root, delta, true);
  }

  /**
   * Cambia el color de cada nodo a los establecidos por defecto.
   */
  public void changeTrieColor() {
    this.changeTrieColorRec(this.root);
  }

  @Override
  public double getDelta() {
    return DEF_DELTA;
  }

  /**
   * Mueve todo el trie
   * @param displacement desplazamiento sobre las x.
   */
  public void moveTrie(double displacement){
    moveTrieRecursive(displacement, root);
  }

  @Override
  protected void paintPanel(Graphics2D g){

    if ( root != null && root.isVisible() ) {
      paintTrie(g, root);
    } else if (root != null && root.getSibling() != null) {
      paintTrie(g, root.getSibling());
    }

    if (word != null) {
      word.paint(g);
    }
  }

  /**
   * Obtiene la cantidad de nodos ubicados horizontalmente en el trie
   * @return la cantidad de nodos
   */
  public int countNodesHorizontal() {
    return countNodesHor;
  }

  /**
   * Obtiene la cantidad de nodos ubicados horizontalmente en el trie
   * @return la cantidad de nodos
   */
  public int countNodesVertical() {
    int count = this.countChildren(root);

    if (count != 0) {
      return ++count;
    }

    return count;
  }

  /**
   * Limpia la vista
   */
  public void clear(){
    this.root = null;
    if (this.getWord() != null) {
      this.getWord().clear();
    }
  }

  /**
   * Indica si esta corriendo una primitiva de insercion
   * @return true si esta corriendo una primitiva de insercion, false en caso
   * contrario
   */
  public boolean isRunningInsertion() {
    return this.insertion;
  }

  /**
   * Muestra las estadisticas
   */
  public void showStatistic(){
    List<String> statistics = new ArrayList<String>();
    //this.showStatistic = true;
    getController().showLogMessage("** Operacion finalizada");
    statistics.add("Comparaciones: " + countComp);

    if ( isRunningInsertion() ) {
      statistics.add("Nuevos nodos: " + countNewNodes);
    } else {
      statistics.add("Nodos eliminados: " + countDelNodes);
    }

    for (String stat : statistics) {
      getController().showLogMessage(stat);
    }
  }

  public void restore(){
    this.getCommandQueue().setDefaultCommand(null);
  }

  @Override
  public void changeSpeed(int speed) {
    super.changeSpeed(speed);
    if ( getCommandQueue().getDelay() < 30 ) {
       getCommandQueue().setDelay(30);
    }
  }
  
  @Override
  public void undoLastStep() throws CannotUndoException {
      
    try {
      LinkedList<UndoAnimationSteps> animations = new LinkedList<UndoAnimationSteps>();
      List<Command> list = new ArrayList<Command>();
      List<Command> listUndo = new ArrayList<Command>();
      do {
        animations.addFirst( stack.pop() );
      } while ( !stack.isEmpty() && !stack.peek().getRedoPause() );
      
      if ( animations.size() > 1) {
        animations.getFirst().setUndoPause(true);
        animations.getLast().setUndoPause(false);
      } else {
        animations.getFirst().setUndoPause(true);
      }

      this.setCurrentAnimation(this.getCurrentAnimation() - 1);
      this.setPrimitiveFinished( false );
      this.setExecutingUndo( true );
      
      for (UndoAnimationSteps animation : animations) {
        list.addAll( animation.getSteps() );
      }

      for (int i = animations.size() - 1; i >= 0 ; i-- ) {
        listUndo.addAll( animations.get(i).getUndoSteps() );
      }
      
      Collections.reverse(list);
      Collections.reverse(listUndo);
      list.addAll(listUndo);
      getCommandQueue().executeImmediate(list);
      this.wait(false);
  } catch (java.util.EmptyStackException e) {
      controller.showLogMessage("No hay operaciones para deshacer...");
      throw new CannotUndoException();
  }
  }
  
  
  /**
   * Cuenta los hijos de un nodo
   * @param node nodo a contar los hijos
   * @return la cantidad de hijos
   */
  private int countChildren(TrieNodeView node){

    if (node == null) {
      return 0;
    }

    return Math.max( 1 + countChildren(node.getChild()), countChildren(node.getSibling()));
  }

  /**
   * Permite cambiar el color del trie recursivament.
   * @param trieNode
   */
  private void changeTrieColorRec(TrieNodeView trieNode) {

    if (trieNode == null) {
      return;
    }

    trieNode.restore();
    trieNode.stopFlashing();
    changeTrieColorRec(trieNode.getChild());
    changeTrieColorRec(trieNode.getSibling());

    if ( trieNode.hasDataNode() ) {
      AbstractTrieNodeView data = trieNode.getDataNode();
      data.stopFlashing();
      data.changeNodeColor();
    }
  }

  /**
   * Metodo recursivo para calcular la profundidad de los hermanos para cada
   * nodo
   * @param node el nodo para el cual debe conocerse la profundidad de los
   *        hermanos
   */
  private void setDepthSiblingsRec(TrieNodeView node) {

    double add = 0;

    if (node == null) {
      return;
    }

    if (!node.hasChild() && (node.isEndWord() && !node.getDataNode().defaultSizeChanged())) {
      node.setDepthSibling(0);
    } else {
      if (node.hasChild()) {
        setDepthSiblingsRec(node.getChild());
      }

      if (node.isEndWord()) {
        if (node.hasChild() && !node.getChild().isInvisible() && !node.getDataNode().isInvisible()) {
            add = 1;
        }
        if ( node.getDataNode().defaultSizeChanged() ) {
          add += node.getDataNode().getDepthSibling();
        }
      }

      int countSiblings = 0;

      if (node.hasChild()) {
        countSiblings = node.getChild().countSiblings();
      }

      node.setDepthSibling(add + countSiblings + calculateSumDepthSib(node.getChild()));
    }

    if (node.getSibling() != null) {
      setDepthSiblingsRec(node.getSibling());
    }
  }

  /**
   * Calcula la sumatoria de las profundidades de los hermanos del nodo pasado
   * como parametro.
   * @return la sumatoria de las profundidades
   */
  private double calculateSumDepthSib(TrieNodeView node) {

    if (node == null) {
      return 0;
    }

    double sum = 0;
    for (TrieNodeView ptr = node; ptr != null; ptr = ptr.getSibling()) {
      sum += ptr.getDepthSibling();
    }

    return sum;
  }

  /**
   * Mueve el trie recursivamente.
   */
  private void moveTrieRecursive(double displacement, TrieNodeView node) {

    if (node == null) {
      return;
    }

    Point2D position = node.getPosition();
    node.moveTo( new Point2D.Double(position.getX() + displacement, position.getY()) );
    moveTrieRecursive(displacement, node.getChild() );
    moveTrieRecursive(displacement, node.getSibling() );

    if ( node.hasDataNode() ) {
      AbstractTrieNodeView data = node.getDataNode();
      position = data.getPosition();
      data.moveTo( new Point2D.Double(position.getX() + displacement, position.getY() ) );
    }
  }

  /**
   * Dibuja el trie recursivamente.
   */
  private void paintTrie(Graphics graphics, TrieNodeView root) {
    Graphics2D g2 = (Graphics2D) graphics;

    if (root == null) {
      return;
    }
    
    root.paint(graphics);
    paintTrie(g2, root.getChild());
    paintTrie(g2, root.getSibling());
    
    if (root.getDataNode() != null) {
      root.getDataNode().paint(g2);
    }
  }

  /**
   * Redimensiona el trie recursivamente.
   */
  private void resizeRec(List<TrieNodeView> nodes, TrieNodeView current, double delta, boolean siblings) {

    if (current == null) {
      return;
    }

    if (!nodes.contains(current) && current.isVisible()) {

      if (!siblings) {
        current.resize(delta);
      } else {
        current.resizeSibling(delta);
      }

      resizeRec(nodes, current.getChild(), delta, siblings);
      resizeRec(nodes, current.getSibling(), delta, siblings);
    }
  }

  /**
   * Calcula recursivamente los pasos para redimensionar el nodo.
   */
  private int getResizeStepsRec(List<TrieNodeView> nodes, TrieNodeView current,
		  						double delta, boolean siblings) {

    if (current == null) {
      return 0;
    }

    if (!nodes.contains(current)) {
      int max;

      if (siblings) {
        max = current.getSiblingSteps(delta);
      } else {
        max = current.getSteps(delta);
      }

      max = Math.max(max, getResizeStepsRec(nodes, current.getSibling(), delta, siblings));
      max = Math.max(max, getResizeStepsRec(nodes, current.getChild(), delta, siblings));
      return max;
    }

    return 0;
  }

  /**
   * Crea los pasos de la animacion para agregar un nodo
   */
  private void createAddTrieNodeAnimationSteps(TrieNodeView parent,
      AbstractTrieNodeView node, Command ShowMessageCommand) {

    AnimationSteps animation = new AddTrieNodeAnimationSteps(this, parent, node, 
        ShowMessageCommand, this.wordIndex);
    getCommandQueue().addCommand(new CreateAnimationCommand(this, animation,
        getCountAnimations()));
    this.wordIndex++;
    this.setCountAnimations( getCountAnimations() + 1);
    this.countNewNodes++;
  }

  /**
   * Crea los pasos de animacion para remover un nodo
   */
  private void createRemoveTrieNodeAnimationSteps(Command command,
      TrieNodeView parent, AbstractTrieNodeView node) {
    RemoveTrieNodeAnimationSteps animation = new RemoveTrieNodeAnimationSteps(
        this, parent, command, node, wordIndex);
    getCommandQueue().addCommand(new CreateAnimationCommand(this, animation,
        getCountAnimations()));
    setCountAnimations( getCountAnimations() + 1);
  }

  /**
   * Inicializa la palabra
   */
  private void initShowWord(String word) {
    changeTrieColor();
    this.getCommandQueue().setDefaultCommand(new PaintCommand(this));
    this.word = new WordView(word);
    this.countNodesHor = 0;
    this.countComp = 0;
    this.countNewNodes = 0;
    this.countDelNodes = 0;
    Dimension dim = this.word.getDimension();
    double x = (this.getSize().getWidth() - dim.getWidth()) / 2;
    this.word.initialize(new Point2D.Double(x, DEF_WORD_POS_Y));
    wordIndex = 0;
    this.word.setFlashingLetter(wordIndex, getFlashingDelay());
    prepareAnimation();
    this.setPrimitiveFinished( false );
    this.setWait( true );
  }

  private void incCountNodesHor(){
    if (countNodesHor == 0) {
      this.countNodesHor++;
    }
  }
}
