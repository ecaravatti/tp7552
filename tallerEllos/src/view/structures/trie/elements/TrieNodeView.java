package view.structures.trie.elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import model.structures.trie.TrieNode;
import view.commons.shapes.Arrow;
import view.commons.shapes.NodeShape;
import view.commons.shapes.TrieNodeShape;
import events.trie.TrieNodeEvent;
import events.trie.TrieNodeListener;


/**
 * Esta clase representa la vista de un nodo de un trie. 
 *  
 * @author Agustina
 */
public class TrieNodeView extends AbstractTrieNodeView implements TrieNodeListener<String>{
  public final static int DEF_LENGTH = 20;
  private final static Font DEF_FONT = new Font("SansSerif", Font.BOLD, 14);
  private final static int DEF_SEP_CHILD = 20;
  private final static int DEF_DIST_FIRST_CHILD = 10;
  public final static int ADDED_CHILD = 1;
  public final static int ADDED_SIBL = 2;
  public final static int ADDED_ROOT = -1;
  
  private PointerView ptrSiblView;
  private PointerView ptrChildView;
  private PointerView ptrDataView;
  private PointerView firstSiblVisible;
  private PointerView firstChildVisible;
  private boolean endWord;
  private boolean visiblePtrData;
  private Point2D firstChildPos;
  private Point2D firstChildFinalPos;
  private boolean insertData;
  private boolean moveFirstChild;
  private double lengthSibling;
  public int added;
  
  /**
   * Construye un TrieNodeView
   * @param node el nodo que debe asociarse con esta vista
   * @param position la posicion del nodo
   * @param ptrParent puntero al padre
   * @param trieView la vista a la que pertenece el nodo
   */
   public TrieNodeView(TrieNode<String> node, Point2D position, PointerView ptrParent,
          TrieViewPrimitives trieView) {
    
    super(new Character( node.getCharacter() ).toString(), position, ptrParent, trieView);
    node.addListener( this );
    this.shape =  new TrieNodeShape(getData(), getPosition(), getWidth(), getHeight(),
        DEF_FONT, DEF_STROKE);
    this.trieView = trieView;
    this.ptrSiblView = new SiblingPointerView(this, null, getShape().getPositionSiblingPtr(), DEF_LENGTH);
    this.ptrChildView = null;
    this.ptrDataView = null;
    this.firstChildVisible = null;
    this.firstSiblVisible = null;
    this.visiblePtrData = false;
    this.firstChildPos = null;
    this.firstChildFinalPos = null;
    this.insertData = false;
    this.moveFirstChild = false;
    this.lengthSibling = 0;
    this.added = 0;
  }
  
  @Override
  public void firstSiblingAdded(TrieNodeEvent<String> event) {
    TrieNode<String> node = event.getSource();
    boolean ins = false;
    
    if (this.hasSibling())
      ins = true;

    setSibling( new TrieNodeView( node.getSibling(), ptrSiblView.getTargetNodePosition(),
        this.ptrSiblView, this.trieView ) );
    if (ins){
      setSiblingDefaultDistance();
      added = ADDED_SIBL;
    }
   
    trieView.trieNodeViewSiblingAdded(this, this.getSibling());
  }
  
  @Override
  public void firstChildAdded(TrieNodeEvent<String> event) {
    TrieNode<String> node = event.getSource();
    
    if ( this.hasChild() )
      firstChildVisible = ptrChildView;
    
    if ( this.hasDataNode() ){
      this.calculateFirstChildPosition( this.getDataNode() );
      this.ptrChildView = new DiagonalPointerView(this, null, getShape().getPositionChildPtr(), DEF_LENGTH);
    }
    else
      this.ptrChildView = new ChildPointerView(this, null, getShape().getPositionChildPtr() , DEF_LENGTH);
    
    TrieNodeView nodeView = new TrieNodeView( node.getChild(), ptrChildView.getTargetNodePosition(),
        this.ptrChildView, this.trieView );
    
    this.setChild( nodeView );
    
    if ( firstChildVisible != null ){
      getChild().addSibling( (TrieNodeView)firstChildVisible.getTargetNode() );
      getChild().getSibling().setParent( getChild().ptrSiblView );
      ptrChildView.setDistanceTargetNode(
        getChild().getPosition().distance( getChild().getSibling().getPosition()) );
      added = ADDED_CHILD;
      firstChildVisible = null;
    }
    else 
      this.setChildDefaultDistance();
    
    this.trieView.trieNodeViewChildAdded(this, this.getChild());
  }

  @Override
  public void valueIsWordChanged(TrieNodeEvent<String> event) {
    TrieNode<String> node = event.getSource();
    
    if (node.isWord()){
      this.endWord = true;
      this.ptrDataView = new ChildPointerView(this, null, getShape().getPositionChildPtr(), DEF_LENGTH);
      DataTrieNodeView dataTrieNode = new DataTrieNodeView( node.getData(), 
          this.ptrDataView.getTargetNodePosition(), ptrDataView, trieView );
      this.ptrDataView.setTargetNode( dataTrieNode );
      this.ptrDataView.setDistanceTargetNode(DEF_DIST);
      this.trieView.trieNodeViewIsWord(this, dataTrieNode);
      if ( this.hasChild() ){
        this.moveFirstChild = true;
        this.insertData = true;
      }
    }
    else{
      this.endWord = false;
      this.getDataNode().setToEliminate(true);
      this.trieView.dataTrieNodeViewFound(this.getDataNode(), Color.green.brighter() );
      if ( this.hasChild() ){
        this.moveFirstChild = true;
        this.firstChildFinalPos = this.getDataNode().getPosition();
      }
      this.trieView.dataTrieNodeViewRemoved(this, this.getDataNode() );
    }
  }
  
  @Override
  public void selectionModeChangedFound(TrieNodeEvent<String> event) {
    trieView.trieNodeViewFound(this); 
  }

  @Override
  public void selectionModeChangedNotFound(TrieNodeEvent<String> event) {
    trieView.trieNodeViewNotFound(this); 
  }

  @Override
  public void firstChildRemoved(TrieNodeEvent<String> event) {
    TrieNodeView child = this.getChild();
    
    child.setToEliminate(true);
    if ( child.hasSibling() ){
      this.firstChildFinalPos = child.getPosition();
      this.moveFirstChild = true;
    }
    trieView.firstChildRemoved(this, this.getChild());
  }
  
  @Override
  public void firstSiblingRemoved(TrieNodeEvent<String> event) {
    TrieNodeView sibling = this.getSibling();
    
    sibling.setToEliminate(true);
    trieView.firstSiblingRemoved(this, this.getSibling() );
  }

  /**
   * Permite indicar que el puntero a los datos debe ser visible (o no).
   * @param visible true si debe ser visible, false en caso contrario.
   */
  public void setVisiblePtrData(boolean visible){
    
    if (visible == this.visiblePtrData) 
      return;
    
    getShape().setVisiblePtrData(visible);
    visiblePtrData = visible;
    if (visible ){ 
      if ( hasChild() )
        this.ptrChildView.setInitialPosition( getShape().getPositionChildPtr() );
      this.ptrDataView.setInitialPosition( getShape().getPositionDataPtr() );
    }
  }
 
  /**
   * Cambia la distancia del hermano al nodo a la distancia por defecto
   */
  public void setSiblingDefaultDistance(){
    this.ptrSiblView.setDistanceTargetNode(DEF_DIST);
  }

  /**
   * Cambia la distancia del hijo al nodo a la distancia por defecto
   */
  public void setChildDefaultDistance(){
    if (this.ptrDataView == null) 
      this.ptrChildView.setDistanceTargetNode(DEF_DIST);
    else this.ptrChildView.setDistanceTargetNode(DEF_DIST_FIRST_CHILD);
  }
  
  @Override
  public void moveTo(Point2D position) {
    super.moveTo(position);
    shape.moveTo(position);
    TrieNodeShape nodeShape = getShape();
    
    setPtrInitPosition( ptrChildView, nodeShape.getPositionChildPtr() );
    setPtrInitPosition( ptrSiblView, nodeShape.getPositionSiblingPtr() );
    
    if ( nodeShape.isPtrDataVisible() )
      setPtrInitPosition( ptrDataView, nodeShape.getPositionDataPtr() );
    else 
      setPtrInitPosition( ptrDataView, nodeShape.getPositionChildPtr() );
  }
  
  /**
   * Calcula la cantidad de pasos necesarios para redimensionar el nodo
   * de acuerdo con el delta dado como parametro. 
   * @param delta variacion del paso
   * @return la cantidad de pasos
   */
  public int getSteps(double delta){
    int stepsSiblPtr; 
    int stepsChildPtr;
    
    if ( this.calculateDistanceFirstSibling() )
      stepsSiblPtr = new Double( Math.ceil( this.lengthSibling / Math.abs(delta ) ) ).intValue();
    else
      stepsSiblPtr = this.calculateSteps(delta, this.ptrSiblView);
    
    //Pasos para redimensionar el puntero hijo
    if (!insertData)
      stepsChildPtr = this.calculateSteps(delta, this.ptrChildView);
    else{
      insertData = false;
      PointerView pointer = new DiagonalPointerView(this, this.getChild(), getShape().getPositionChildPtr(), DEF_LENGTH);
      stepsChildPtr = this.calculateSteps(delta, pointer);
    }
      
    //Pasos para redimensionar el puntero a los datos
    int stepsDataPtr = this.calculateSteps(delta, this.ptrDataView);
    int steps = Math.max( stepsSiblPtr, stepsChildPtr);
    steps = Math.max( steps, stepsDataPtr);
    return steps;
  }
  
  /**
   * Calcula la cantidad de pasos necesarios para redimensionar el nodo
   * de acuerdo con la profundidad de sus hermanos, teniendo en cuenta
   * el delta dado como parametro. 
   * @param delta variacion del paso
   * @return la cantidad de pasos
   */
  public int getSiblingSteps(double delta){
    return this.calculateSteps(delta, this.ptrSiblView);
  }
  
  /**
   * Obtiene los pasos necesarios para mover el hijo a la posicion en que
   * debe estar
   * @param delta variacion del paso
   * @return la cantidad de pasos.
   */
  public int getMoveFirstChildSteps(double delta){
    if ( !hasChild() ) 
      return 0;
    double dist = this.firstChildFinalPos.distance( getFirstChildToMove().getPosition() );
    return new Double( Math.ceil(dist/Math.abs(delta)) ).intValue();
  }
  
  /**
   * Mueve el primer hijo del nodo.
   * @param delta variacion del paso para mover el hijo.
   */
  public void moveBackwardFirstChild(double delta){
    
    if (! hasChild() ) 
      return;
    
    TrieNodeView move = this.getChild();
    Point2D posChild = move.getPosition();
    Point2D posFinal = new Point2D.Double(posChild.getX() + delta, posChild.getY());
    double distance;
   
    if ( hasDataNode() && !getDataNode().isVisible() )
      this.setVisiblePtrData(true);

    if ( posFinal.getX() <= firstChildFinalPos.getX() ){
      posFinal.setLocation( firstChildFinalPos.getX(), posFinal.getY() );
      
      if (!this.hasDataNode() || !getDataNode().isVisible()){
        ptrChildView = new ChildPointerView(this, move, getShape().getPositionChildPtr(),
            DEF_LENGTH);
        this.firstChildPos = null;
      }
      else{
        this.firstChildPos = posFinal;
        ptrChildView = new DiagonalPointerView(this, move, getShape().getPositionChildPtr(),
            DEF_LENGTH); 
      }
      
      this.moveNodeFinalPosition( ptrChildView );
      this.moveAllRec( move );
      this.firstChildFinalPos = null;
      this.moveFirstChild = false;
      move.setParent(ptrChildView);
    }
    else{
      this.firstChildPos = posFinal; 
      
      if (posChild.getX() > getShape().getPositionChildPtr().getX() ){
        this.ptrChildView = new DiagonalPointerView(this, move, getShape().getPositionChildPtr(), 
            DEF_LENGTH);
        distance = posFinal.distance( getShape().getPositionChildPtr() );
        ptrChildView.setDistanceTargetNode(distance);
        this.moveAllRec( move );
      }
      else{
        move.moveTo( posFinal );
        this.moveAllRec( move );
      }
    }
  }
  
  /**
   * Mueve el primer hijo del nodo.
   * @param delta variacion del paso para mover el hijo.
   */
  public void moveForwardFirstChild(double delta){
    TrieNodeView child;
    boolean moveSibling = false;
    boolean moveFinished = false;
    
    child = getChild();
   
    if ( !child.isVisible() ){
      child = this.getChild().getSibling();
      moveSibling = true;
    }
    else{
      if ( hasDataNode() && !getDataNode().isVisible() )
        this.setVisiblePtrData(true);
    }
    
    Point2D posChild = child.getPosition();
    Point2D posFinal = new Point2D.Double(posChild.getX() + delta, posChild.getY());
    double distance;
    this.firstChildPos = posFinal;
    
    if ( posFinal.getX() >= firstChildFinalPos.getX() ){
      posFinal.setLocation( firstChildFinalPos.getX(), posFinal.getY() );
      moveFirstChild = false;
      moveFinished = true; 
      if (moveSibling) 
        this.firstChildVisible = null;
    }
   
    PointerView ptr = new DiagonalPointerView(this, child, getShape().getPositionChildPtr(), 
        DEF_LENGTH);
    distance = posFinal.distance( getShape().getPositionChildPtr() );
    ptr.setDistanceTargetNode(distance);
    
    if (moveSibling && !moveFinished) 
      this.firstChildVisible = ptr;

    if (!moveSibling)
      this.ptrChildView = ptr;

    if (moveFinished)
        child.setParent( ptr );
    
    this.moveAllRec( child );  
  }
  
  /**
   * Redimensiona el nodo utilando el delta pasado como parametro
   * @param delta paso utilizado para redimensionar
   */
  public void resize(double delta){
    
    resizePointer( ptrSiblView, delta );
    resizeFirstSiblingVisible( delta );
    
    resizePointer( ptrDataView, delta );
    if (firstChildPos != null && this.hasDataNode() &&
        ptrDataView.getTargetNodeFinalPosition().equals( getDataNode().getPosition() ))
      this.calculateFirstChildPosition( getDataNode() );
      
    resizePointer( ptrChildView, delta );
  }

  /**
   * Redimensiona el nodo de acuerdo a la profunidad de sus hermanos utilizando el 
   * delta
   * @param delta paso utilizado para redimensionar
   */
  public void resizeSibling(double delta) {
    resizePointer( ptrSiblView, delta );
    resizePointer( ptrDataView, 0 );
    resizePointer( ptrChildView, 0 );
  }
  
  /**
   * Indica si el nodo finaliza una palabra (o no)
   * @return true si finaliza palabra, false en caso contrario
   */
  public boolean isEndWord() {
    return endWord;
  }

  /**
   * @param endWord nuevo valor de endWord
   */
  public void setEndWord(boolean endWord) {
    this.endWord = endWord;
  }
  
  /**
   * Obtiene el hermano del nodo
   * @return el hermano
   */
  public TrieNodeView getSibling(){
    return (TrieNodeView)ptrSiblView.getTargetNode(); 
  }
  
  /**
   * Cambia el hermano del nodo
   * @param sibling el nuevo hermano del nodo
   */
  public void setSibling(TrieNodeView sibling){
    AbstractTrieNodeView sibl = this.getSibling();
    ptrSiblView.setTargetNode( sibling );
    sibling.ptrSiblView.setTargetNode( sibl );
    if (sibl != null)
      sibl.setParent(sibling.ptrSiblView);

  }
  
  /**
   * Cambia el hermano del nodo
   * @param sibling el nuevo hermano del nodo
   */
  public void addSibling(TrieNodeView sibling){
    ptrSiblView.setTargetNode( sibling );
  }
  
  /**
   * Obtiene el hijo del nodo
   * @return el hijo del nodo
   */
  public TrieNodeView getChild(){
    if (this.ptrChildView != null)
      return (TrieNodeView)this.ptrChildView.getTargetNode();
    return null;
  }
 
  /**
   * Cambia el hijo del nodo
   * @param trieNode el nuevo hijo
   */
  public void setChild(TrieNodeView trieNode){
    this.ptrChildView.setTargetNode(trieNode);
  }
  
  /**
   * Obtiene el nodo que contiene el dato
   * @return el nodo que contiene el dato
   */
  public DataTrieNodeView getDataNode(){
    if (ptrDataView != null)
      return (DataTrieNodeView)ptrDataView.getTargetNode();
    
    return null;
  }
  
  /**
   * Cuenta la cantidad de hermanos del nodo.
   * @return la cantidad de hermanos del nodo.
   */
  public int countSiblings(){    
    int count = 0;

    
    for (TrieNodeView ptr = this.getSibling(); ptr != null; ptr = ptr.getSibling() ){ 
      if ( !ptr.isInvisible() ) 
        count++;
    }
   
    if ( this.isInvisible() && this.hasSibling() ) 
      count--;
    
    if ( this.isInvisible() && this.hasParent() && this.getParent().getSourceNode().hasDataNode() 
        && this.getParent().getSourceNode().added == TrieNodeView.ADDED_CHILD)
      count++;
  
    return count;
  }
  
  /**
   * Obtiene la posicion del primer hijo
   * @return la posicion del primer hijo
   */
  public Point2D getFirstChildPosition(){
    
    if (firstChildPos != null)
      return this.firstChildPos;

    if ( hasChild() )
      return getChild().getPosition();
    
    return getDataNode().getPosition();
  }

  /**
   * Elimina uno de los hijos del nodo que esta marcado para eliminar
   */
  public void eliminate(){
    
    if ( hasSibling() && getSibling().isToEliminate() )
      this.removeSibling();

    if ( hasChild() && getChild().isToEliminate() )
      this.removeChild();
    
    if ( hasDataNode() && getDataNode().isToEliminate() )
      this.removeDataNode();
  }
  
  /**
   * Determina si es necesario mover (o no) el primer hijo
   * @return true si es necesario mover el hijo, false en caso contrario
   */
  public boolean isMoveFirstChild() {
    return moveFirstChild;
  }

  /**
   * Agrega un nodo dato
   * @param node nodo a agregar
   */
  public void addDataNode(AbstractTrieNodeView node){
    getShape().setVisiblePtrData(true);
    this.visiblePtrData = true;
    this.ptrDataView = node.getParent();
    moveNodeFinalPosition( ptrDataView );
    if ( this.hasChild() ) 
      this.ptrChildView.setInitialPosition( getShape().getPositionChildPtr());
  }
  
  /**
   * Agrega un nodo hijo
   * @param node nodo a agregar como hijo
   */
  public void addChild(AbstractTrieNodeView node){
    
    if ( hasChild() && !getChild().isToEliminate() ) 
      firstChildVisible = ptrChildView;
    
    if ( hasDataNode() )
        calculateFirstChildPosition( getDataNode() );
    else if ( hasChild() ) 
        calculateFirstChildPosition( getChild() );
      
    this.ptrChildView = node.getParent();
    moveNodeFinalPosition( ptrChildView );
    node.setParent(ptrChildView);
  }
  
  /**
   * Agrega un nodo hermano
   * @param node nodo a agregar como hermano
   */
  public void addSibling(AbstractTrieNodeView node){
    ptrSiblView = new SiblingPointerView(this, node, getShape().getPositionSiblingPtr(), 
        DEF_LENGTH);
    moveNodeFinalPosition( ptrSiblView );
    node.setParent(ptrSiblView);
  }
 
  /**
   * Obtiene la posicion final del primer hijo
   * @return
   */
  public Point2D getFinalPositonFirstChild() {
    return firstChildFinalPos;
  }

  /**
   * Cambia la posicion final del primer hijo
   * @param firstChildFinalPos la nueva posicion final
   */
  public void setFinalPositionFirstChild(Point2D firstChildFinalPos) {
    this.firstChildFinalPos = firstChildFinalPos;
  }

  @Override
  public void setVisible(boolean visible){
    
    if ( hasParent() ) 
      getParent().getSourceNode().firstSiblVisible = null;
    
    if ( (getModeAdded() != 0) || (this.added ==ADDED_ROOT && this == trieView.getRoot() ) ){
      if (!visible){
        getSibling().moveTo( getPosition());
        getSibling().moveAllRec( getSibling() );
      }
      else{
        this.ptrSiblView.changeDistanceTargetNode();
      }
    }
    
    if (this.added ==ADDED_ROOT && this == trieView.getRoot()){
      if (visible){
        this.ptrSiblView.setVisible(true);
        this.ptrSiblView.changeDistanceTargetNode();
      }
    }
    
    super.setVisible(visible);
  }
  
  /**
   * Determina si el nodo tiene un hijo
   * @return true si tiene hijo, false en caso contrario.
   */
  public boolean hasChild(){
    return (ptrChildView != null && getChild() != null);
  }
  
  /**
   * Determina si el nodo tiene un nodo dato
   * @return true si tiene un nodo dato, false en caso contrario
   */
  public boolean hasDataNode(){
    return (ptrDataView != null && getDataNode() != null);
  }
  
  /**
   * Determina si el nodo tiene un hermano
   * @return true si tiene un hermano, false en caso contrario
   */
  public boolean hasSibling(){
    return (ptrSiblView != null && getSibling() != null);
  }
  
  /**
   * Calcula la posicion del primer hijo
   * @param node el nodo dato
   */
  public void calculateFirstChildPosition(AbstractTrieNodeView node){
    Point2D position = node.getPosition(); 
    this.firstChildPos = new Point2D.Double( position.getX() + node.getWidth() +
          DEF_SEP_CHILD, position.getY() );
  }
  
  /**
   * Cambia la posicion del primer hijo
   * @param node nodo a cambiar la posicion
   */
  public void changeFirstChildPosition(AbstractTrieNodeView node){
    
    if ( !this.hasChild() ) 
      return;
    
    Point2D position = getDataNode().getFinalPosition();
    
    firstChildPos = getChild().getPosition();
    firstChildFinalPos = new Point2D.Double( position.getX() + node.getWidth() +
        DEF_SEP_CHILD, position.getY() );
  }
  
  /**
   * Restaura el estado del nodo
   */
  public void restore(){
    this.changeNodeColor();
    this.added = 0;
  }

  /**
   * Obtiene el puntero al hermano
   * @return el puntero al hermano
   */
  public PointerView getSiblingPointerView(){
    return this.ptrSiblView;
  }

  @Override
  public void setDefaultDistanceParent() {
    if (this.getModeAdded() == 0)
      super.setDefaultDistanceParent();
  }
  
  @Override
  protected void paintTrieNode(Graphics g) {
    Graphics2D g2 = (Graphics2D)g;
    
    if ( drawShape() )
      shape.paint(g);
    
    if ( this.isTransparent() ){
      g2.setPaint(this.getTransparentColor());
      g2.fill(shape.getBounds());
      g2.draw(shape.getBounds());
    }
    
    if ( this.hasSibling() ) ptrSiblView.paint(g);
    if (firstSiblVisible != null) firstSiblVisible.paint(g);
    if (firstChildVisible != null) firstChildVisible.paint(g);
    if ( this.hasChild() ) ptrChildView.paint(g);
    if ( this.hasDataNode() && visiblePtrData ) 
      ptrDataView.paint(g);
    
    drawVisible(g2);
  }
 
  @Override
  protected NodeShape createShape(){
    return new TrieNodeShape(getData(), getPosition(), getWidth(), getHeight(),
        DEF_FONT, DEF_STROKE);
  }
  
  /**
   * Obtiene la forma en que fue agregado el nodo al padre
   * @return el modo
   */
  private int getModeAdded(){
    
    if ( hasParent() && getParent().getSourceNode().added == ADDED_ROOT)
      return 0;
    
    if ( hasParent() )
      return getParent().getSourceNode().added;
    
    return 0;
  }
 
  /**
   * Determina si debe dibujarse el nodo
   * @return true si debe dibujarse, false en caso contrario.
   */
  private boolean drawShape(){
    
    if ( hasParent() && getParent().getSourceNode().isVisible() && getParent().getSourceNode().
        getPosition().equals(getPosition()) )
      return false;
    
    return true;
  }
  
  
  /**
   * Calcula los pasos necesarios para redimensionar el nodo
   * @param delta variacion en el paso
   * @param ptr puntero a redimensionar
   * @return los pasos
   */
  private int calculateSteps(double delta, PointerView ptr){
    
    if (ptr == null || ptr.getTargetNode() == null) return 0;
    
    Point2D p1 = ptr.getTargetNodePosition();
    Point2D p2 = ptr.getTargetNodeFinalPosition();
    
    double dist = p1.distance( p2 );
    
    double steps = Math.ceil( (dist)/Math.abs(delta) );
    return new Double(steps).intValue();
  }
  
  /**
   * Redimensiona un puntero
   * @param pointer puntero a redimensionar.
   * @param delta variacion en el paso
   */
  private void resizePointer(PointerView pointer, double delta){
    if (pointer != null){
      AbstractTrieNodeView node = pointer.getTargetNode();
      
      if ( node != null && node.isVisible() )
        pointer.setDistanceTargetNode( delta );
      else if ( node != null)
        node.moveTo( pointer.getTargetNodePosition() );

    }
  }
  
  /** 
   * Mueve todos los hijos del nodo pasado como parametro 
   */
  private void moveAllRec(TrieNodeView node){
    
    if (node == null) return;
    
    if ( node.hasSibling() ) 
      node.getSibling().moveTo( node.ptrSiblView.getTargetNodePosition() );
    if ( node.hasChild() )
      node.getChild().moveTo( node.ptrChildView.getTargetNodePosition() );
    if ( node.hasDataNode() )
      node.getDataNode().moveTo( node.ptrDataView.getTargetNodePosition() );
    
    moveAllRec( node.getChild() );
    moveAllRec( node.getSibling() );
  }
  
  /**
   * Cambia la posicion inicial del puntero
   * @param pointer puntero a cambiar posicion inicial
   * @param pos posicion inicial del puntero
   */
  private void setPtrInitPosition(PointerView pointer, Point2D pos){
    if (pointer != null) pointer.setInitialPosition(pos);
  }
  
  /**
   * Elimina el nodo dato
   */
  private void removeDataNode(){
    this.ptrDataView = null;
    this.getShape().setVisiblePtrData(false);
    this.visiblePtrData = false;
    this.setEndWord(false);
    this.trieView.calculateDepths();
  }
  
  /**
   * Elimina el nodo hermano
   */
  private void removeSibling(){
    TrieNodeView node = this.getSibling().getSibling();
    
    if ( this.calculateDistanceFirstSibling()  )
      this.ptrSiblView.setLength(this.lengthSibling);
    
    if (node == null) 
      this.ptrSiblView.changeDistanceTargetNode();
    else
      node.setParent( ptrSiblView );
    
    this.ptrSiblView.setTargetNode( node );
    this.trieView.calculateDepths();
  }
  
  /**
   * Calcula la distancia al primer hermano no eliminado
   */
  private boolean calculateDistanceFirstSibling(){
    TrieNodeView node = this.getSibling();
    
    if (node == null) return false;
    
    node = node.getSibling();
    
    if (node != null && this.getSibling().isToEliminate() ){
      Point2D siblingPos = node.getPosition();
      this.lengthSibling = getShape().getPositionSiblingPtr().distance( siblingPos );
      return true;
    }
    return false;
  }
  
  /**
   * Elimina el nodo hijo
   */
  private void removeChild(){
    TrieNodeView node = this.getChild().getSibling();
    
    if (node != null){
      this.firstChildPos = node.getPosition();
      this.ptrChildView = new DiagonalPointerView(this, node, getShape().getPositionChildPtr(),
            DEF_LENGTH);
      double length = node.getPosition().distance( getShape().getPositionChildPtr() );
      ptrChildView.setDistanceTargetNode( length );
      node.setParent( ptrChildView );
    }
    else this.ptrChildView = null;
    
    this.trieView.calculateDepths();
  }
  
  /**
   * Obtiene el primer hijo a mover
   * @return el primer hijo a mover
   */
  private TrieNodeView getFirstChildToMove(){
    AbstractTrieNodeView node = this.getDataNode();
    if ( node != null && node.isToEliminate() )
      return this.getChild();
    
    node = this.getChild();
    if ( node != null && node.isToEliminate() )
      return this.getChild().getSibling();
    
    return this.getChild();
  }
  
  /**
   * Redimensiona el puntero al primer hermano visible
   * @param delta paso 
   */
  private void resizeFirstSiblingVisible(double delta){
    TrieNodeView sibling = this.getSibling();
    if (hasSibling() && !sibling.isVisible() && sibling.hasSibling() ){
      TrieNodeView siblingVisible = sibling.getSibling();
      
      if (firstSiblVisible == null){
        this.firstSiblVisible = new SiblingPointerView(this, siblingVisible, 
            getShape().getPositionSiblingPtr(), DEF_LENGTH );
        this.firstSiblVisible.setDistanceTargetNode(this.lengthSibling);
      }
      
      double depth = this.getDepthSibling();
      this.setDepthSibling( depth + 1 );
      resizePointer( firstSiblVisible, delta);
      this.setDepthSibling( depth );
      this.moveAllRec( siblingVisible );
    }
  }
  
  private void moveNodeFinalPosition(PointerView pointer){
    double distance = pointer.getTargetNodeFinalPosition().distance( pointer.getInitialPosition() );
    pointer.setDistanceTargetNode(distance);
  }

  private void drawVisible(Graphics2D g2){

    if ( ADDED_CHILD == added){
      if ( !this.getChild().isVisible() ){
        Point2D finalPos = this.getChild().getSibling().getPosition();

        if ( finalPos.getX() == this.getPosition().getX() )
          finalPos.setLocation( finalPos.getX() + getWidth()/2, finalPos.getY());

         drawPointer(g2,  getShape().getPositionChildPtr(), finalPos, ptrChildView);
       }
      else if ( this.getChild().getPosition().equals(this.ptrChildView.getTargetNodeFinalPosition()) ){
        this.getChild().ptrSiblView.setVisible(true);
      }
    }
    else if ( added == ADDED_SIBL ){
      if ( !this.getSibling().isVisible() ){
        TrieNodeView sibl = this.getSibling().getSibling();
        Point2D finalPos = sibl.shape.getPosition();
        finalPos.setLocation( finalPos.getX(), getShape().getPositionSiblingPtr().getY() );
        drawPointer(g2, getShape().getPositionSiblingPtr(), finalPos, ptrSiblView);
      }
      else if ( this.getSibling().getPosition().equals(this.ptrSiblView.getTargetNodeFinalPosition()) ){
        this.getSibling().ptrSiblView.setVisible(true);
      }
    }
  }

  private void drawPointer(Graphics2D g2, Point2D initPos, Point2D finPos, PointerView pointer){
    Color color = Color.black;
    BasicStroke stroke = new BasicStroke(1.0f);

    if (pointer.isFlashing()){
      color = Color.ORANGE;
      float DEF_DASH[] = { 0.5f };
      stroke = new BasicStroke(1.4f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
              10.0f, DEF_DASH, 0.0f);
    }

    new Arrow(initPos, finPos , true, stroke, color).paint(g2);
  }
  
  private TrieNodeShape getShape(){
    return (TrieNodeShape) shape;
  }
}
