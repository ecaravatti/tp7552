package view.collection.tree;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import view.animation.common.UndoAnimationSteps;
import view.animation.tree.BSTDoubleRotationStartedAnimation;
import view.animation.tree.BSTEndAnimation;
import view.animation.tree.BSTNodeInsertedAnimation;
import view.animation.tree.BSTNodeRemovedAnimation;
import view.animation.tree.BSTNodeRotatedAnimation;
import view.animation.tree.BSTNodeUpdatedAnimation;
import view.animation.tree.BSTNodeVisitedAnimation;
import view.command.common.Command;
import view.command.common.ShowMessageCommand;
import view.command.common.ShowMessageDialogCommand;
import view.common.AnimatedPanel;
import view.exception.common.CannotUndoException;
import view.memento.tree.BSTCaretaker;
import view.memento.tree.BSTMemento;
import view.shape.Segment;
import event.tree.BSTEvent;
import event.tree.BSTListener;

/**
 *
 */
public class BinarySearchTreeView extends AnimatedPanel implements BSTListener<Integer>, BSTPrimitives {
	private static final long serialVersionUID = 1L;
	private static final int DEF_ROOT_POSITION_Y = 20;
    private final static Font DEF_FONT = new Font("SansSerif", Font.PLAIN, 13);
    private final static Color DEF_COLOR_TEXT = Color.BLACK;
    private final static Color DEF_COLOR_STATISTICS = Color.BLUE;
    private static final Point2D DEF_STATS_POSITION = new Point2D.Double(DEF_FONT.getSize()*7, DEF_ROOT_POSITION_Y);

    private Point2D rootPosition;
    private BSTNodeView root;
    private BSTNodeView lastPolled;
    private BSTNodeView nodeRemoved;
    private LinkedList<BSTNodeView> nodesVisited;
    private boolean changeLocation;
    private boolean inserting;
    private int isDobleRotation;
    private int treeHeight;
    private int treeSize;
    private int countRotations;
    private int countSeeks;
    private boolean modeDelete;
	private BSTCaretaker caretaker;
    
    public BinarySearchTreeView() {
        super();

        caretaker = new BSTCaretaker();
        nodesVisited = new LinkedList<BSTNodeView>();
        rootPosition = new Point2D.Double(0, DEF_ROOT_POSITION_Y);
        root = null;
        nodeRemoved = null;
        this.changeLocation = true;
        this.inserting = false;
    }

    public void clear() {
        root = null;
        prepareAction(true);
        repaint();

        this.addCommandToQueue(new ShowMessageCommand(this, "Nodos eliminados"));
        primitiveFinished();
    }

    @Override
    public void componentResized(ComponentEvent e) {
        super.componentResized(e);
        rootPosition.setLocation(e.getComponent().getWidth() / 2, DEF_ROOT_POSITION_Y);
        if (root != null)
            setFinalPositions();
        repaint();
    }


    public void prepareAction(boolean deleting) {
        nodesVisited.clear();
        caretaker = new BSTCaretaker();
        lastPolled = null;
        isDobleRotation = 0;
        inserting = true;
        countRotations = 0;
        countSeeks = 0;
        modeDelete = deleting;

        prepareAnimation();

        if (root != null) {
            nodesVisited.addLast(root);
            this.addAnimationToQueue(new BSTNodeVisitedAnimation(this, root, BSTNodeVisitedAnimation.SelectionMode.VISITED));
            //commandQueue.addCommand(new SelectElementViewCommand(this, DEF_DELAY_SELECTION, root, null));
            countSeeks++;
        }
    }

    public void setStatistics(int height, int size) {
        treeHeight = height;
        treeSize = size;
    }

    public LinkedList<BSTNodeView> getNodesVisited() {
        return nodesVisited;
    }

    public BSTNodeView getLastPolled() {
        return lastPolled;
    }

    public void setLastPolled(BSTNodeView lastPolled) {
        this.lastPolled = lastPolled;
    }

    public int isDobleRotation() {
        return isDobleRotation;
    }

    public void setDobleRotation(int value) {
        isDobleRotation = value;
    }

    public void setRoot(BSTNodeView root) {
        this.root = root;
    }

    public BSTNodeView getNodeRemoved() {
        return nodeRemoved;
    }

    public void setNodeRemoved(BSTNodeView nodeRemoved) {
        this.nodeRemoved = nodeRemoved;
    }

    public boolean isModeDelete() {
        return modeDelete;
    }

    public void setModeDelete(boolean modeDelete) {
        this.modeDelete = modeDelete;
    }

    @Override
    public void paintPanel(Graphics2D graphics) {
        if (root != null) {
            if (changeLocation) {
                root.setLocation(rootPosition);
            }
            
            root.paintElement(graphics);
        }
        if (nodeRemoved != null)
            nodeRemoved.paintElement(graphics);

        int x = DEF_ROOT_POSITION_Y;
        int y = DEF_FONT.getSize();
        graphics.setFont(DEF_FONT);
        graphics.setColor(DEF_COLOR_TEXT);
        graphics.drawString("Altura:", x, x);
        graphics.drawString("Tamaño:", x, x + y);
        graphics.setColor(DEF_COLOR_STATISTICS);
        x = (int) DEF_STATS_POSITION.getX();
        graphics.drawString(new Integer(treeHeight).toString(), x, DEF_ROOT_POSITION_Y);
        graphics.drawString(new Integer(treeSize).toString(), x, DEF_ROOT_POSITION_Y + y);
    }

    @Override
    public void rootAdded(BSTEvent<Integer> event) {
        if (this.root == null) {
            this.root = new BSTNodeView(event.getCurrentNode(), this);
            this.addAnimationToQueue(new BSTNodeInsertedAnimation(this, root, 0));
        }
        primitiveFinished();
    }

    @Override
    public void nodeRotatedToLeft(BSTEvent<Integer> event) {
        this.addAnimationToQueue(new BSTNodeRotatedAnimation(this, BSTNodeView.LEFT));
        countRotations++;
    }

    @Override
    public void nodeRotatedToRight(BSTEvent<Integer> event) {
        this.addAnimationToQueue(new BSTNodeRotatedAnimation(this, BSTNodeView.RIGHT));
        countRotations++;
    }

    @Override
    public void nodeFound(BSTEvent<Integer> event) {
        BSTNodeView v = nodesVisited.peekLast();
        if (v == null)
            return;
        
        this.addAnimationToQueue(new BSTNodeVisitedAnimation(this, v, BSTNodeVisitedAnimation.SelectionMode.FOUND));

        while (! nodesVisited.isEmpty())
            this.addAnimationToQueue(new BSTNodeUpdatedAnimation(this, nodesVisited.pollLast(), event.getCurrentNode().getBalance()));

        primitiveFinished();
    }

    @Override
    public void selectLeftChild() {
        BSTNodeView v = nodesVisited.peekLast().left;
        if (v == null)
            return;
        nodesVisited.addLast(v);

        //v.getSelectable().changeColor();
        //commandQueue.addCommand();
        this.addAnimationToQueue(new BSTNodeVisitedAnimation(this, v, BSTNodeVisitedAnimation.SelectionMode.VISITED));
        countSeeks++;
    }

    @Override
    public void selectRightChild() {
        BSTNodeView v = nodesVisited.peekLast().right;
        if (v == null)
            return;
        nodesVisited.addLast(v);

        this.addAnimationToQueue(new BSTNodeVisitedAnimation(this, v, BSTNodeVisitedAnimation.SelectionMode.VISITED));
        countSeeks++;
    }

    @Override
    public void setParentLeftChild(BSTNodeView child) {
        this.addAnimationToQueue(new BSTNodeInsertedAnimation(this, child, BSTNodeView.LEFT));
    }

    @Override
    public void setParentRightChild(BSTNodeView child) {
        this.addAnimationToQueue(new BSTNodeInsertedAnimation(this, child, BSTNodeView.RIGHT));
    }

    @Override
    public void dobleRotationStarted(BSTEvent<Integer> event) {
        this.addAnimationToQueue(new BSTDoubleRotationStartedAnimation(this, event.getSide()));
    }

    @Override
    public void balanceUpdated(BSTNodeView node, int balance) {
        this.addAnimationToQueue(new BSTNodeUpdatedAnimation(this, node, balance));
    }

    @Override
    public void nodeTraversed(BSTNodeView node) {
        this.addAnimationToQueue(new BSTNodeVisitedAnimation(this, node, BSTNodeVisitedAnimation.SelectionMode.TRAVERSED));
    }

    @Override
    public void traverseFinished(BSTEvent<Integer> event) {
        primitiveFinished();
    }

    @Override
    public void rootRemoved(BSTEvent<Integer> event) {
        this.addAnimationToQueue(new BSTNodeInsertedAnimation(this, null, 0));

        primitiveFinished();
    }

    @Override
    public void nodeRemoved(BSTNodeView node) {
        this.addAnimationToQueue(new BSTNodeRemovedAnimation(this, node));
    }

    @Override
    public void nodeNotFound(BSTEvent<Integer> event) {
        this.addCommandToQueue(new ShowMessageCommand(this, "No se encontró la clave."));
        while (! nodesVisited.isEmpty())
            this.addAnimationToQueue(new BSTNodeUpdatedAnimation(this, nodesVisited.pollLast()));

        primitiveFinished();
    }

    @Override
    public void rotateFinishedInDelete(BSTEvent<Integer> event) {
        this.addAnimationToQueue(new BSTNodeInsertedAnimation(this, null, event.getSide()));
    }
    
    @Override
    public void emptyTree(BSTEvent<Integer> event) {
    	this.addCommandToQueue(new ShowMessageCommand(this, "El árbol se encuentra vacío."));
    	
    	this.addCommandToQueue(new ShowMessageDialogCommand("El árbol se encuentra vacío.",
        		"Warning", JOptionPane.WARNING_MESSAGE));
    	
    	primitiveFinished();
    }

    private void primitiveFinished() {
        String s;
        s = "[Operaciones: "+ (countRotations + countSeeks) +
                " // Rotaciones: " + countRotations +
                " y Comparaciones: " + countSeeks + "]";
        this.addCommandToQueue(new ShowMessageCommand(this, "Listo. " + s + "\n"));
        //this.addCommandToQueue(new StepFinishedCommand(this, true));
        this.addAnimationToQueue(new BSTEndAnimation(this));
    }

    @Override
    public void setFinalPositions() {
        double x = this.getWidth() / 2;
        x += (root.getLeftWidth() - root.getRightWidth()) / 4;
        rootPosition.setLocation(x, DEF_ROOT_POSITION_Y);
        root.setFinalPositions(rootPosition);
    }

    public boolean moveAllNodes() {
      boolean ret = moveAllNodesRecursive(root);
      if (!ret) this.setChangeLocation(true);
      return ret;
    }
    
    public boolean moveAllNodesRecursive(BSTNodeView node){
      boolean ret = true;
      
      if (node == null) 
        return false;
      
      //Estoy pidiendo todos los puntos del segmento en cada iteracion para 
      //moverme en linea recta - esto puede arreglarse 
      
      Segment segment = new Segment( node.getPosition(), node.getFinalPosition(), 
          this.getDelta() );
      
      List<Point2D> points = segment.getPoints(2);   // Calculo solo los primeros 2 puntos
      if (points.size() > 1)
        node.moveTo( points.get(1) );

      if ( node.getPosition().equals( node.getFinalPosition() ) )
        ret = false;
      
      ret =  moveAllNodesRecursive(node.left) || ret;
      ret =  moveAllNodesRecursive(node.right) || ret;

      return ret;
    }

    public boolean isChangeLocation() {
      return changeLocation;
    }

    public void setChangeLocation(boolean changeLocation) {
      this.changeLocation = changeLocation;
    }

    public boolean isInserting() {
      return inserting;
    }

    public void setInserting(boolean inserting) {
      this.inserting = inserting;
    }

    public BSTMemento restoreFromLastState() {
      BSTMemento memento = caretaker.getMemento();
      this.rootPosition = memento.getRootPosition();
      this.lastPolled = memento.getLastPolled();
      this.nodeRemoved = memento.getNodeRemoved();
      this.nodesVisited = memento.getNodesVisited();
      this.changeLocation = memento.isChangeLocation();
      this.inserting = memento.isInserting();
      this.isDobleRotation = memento.getIsDobleRotation();
      this.modeDelete = memento.isModeDelete();
      
      memento.actualizeNodes();
      this.setRoot( memento.getRoot() );
      return memento;
    }

    public void saveState() {
      BSTMemento memento = new BSTMemento(this);
      caretaker.addMemento(memento);
      
    }
    
    public BSTMemento saveState(BSTNodeView node) {
      BSTMemento memento = new BSTMemento(this, node);
      caretaker.addMemento(memento);
      return memento;
    }

    public BSTNodeView getRoot() {
      return root;
    }

    public Point2D getRootPosition() {
      return rootPosition;
    }
    
    @Override
    public void undoLastStep() throws CannotUndoException {
        
      try {
        LinkedList<UndoAnimationSteps> animations = new LinkedList<UndoAnimationSteps>();
        List<Command> list = new ArrayList<Command>();
        List<Command> listUndo = new ArrayList<Command>();
        do{
          animations.addFirst( stack.pop() );
          this.setCurrentAnimation( this.getCurrentAnimation() - 1);
        } while ( !stack.isEmpty() && !stack.peek().getRedoPause() );
        
        if ( animations.size() > 1){
          animations.getFirst().setUndoPause(true);
          animations.getLast().setUndoPause(false);
        }
        else
          animations.getFirst().setUndoPause(true);
          
        this.setPrimitiveFinished( false );
        this.setExecutingUndo( true );
        
        for (UndoAnimationSteps animation : animations){
          list.addAll( animation.getSteps() );
        }

        for (int i = animations.size() - 1; i >= 0 ; i-- ){
          listUndo.addAll( animations.get(i).getUndoSteps() );
        }
        
        Collections.reverse(list);
        Collections.reverse(listUndo);
        list.addAll(listUndo);
        getCommandQueue().executeImmediate(list);
        this.wait(false);
    } catch (java.util.EmptyStackException e) {
        controller.showLogMessage("No hay nada para deshacer...");
        throw new CannotUndoException();
    }
    }
    
}
