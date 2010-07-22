package view.memento.tree;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import view.collection.tree.BSTHeightBalancedView;
import view.collection.tree.BSTNodeView;

public class BSTMemento {
  private Point2D rootPosition;
  private List<BSTNodeMemento> nodes;
  private BSTNodeView root;
  private BSTNodeView lastPolled;
  private BSTNodeView nodeRemoved;
  private LinkedList<BSTNodeView> nodesVisited;
  private boolean changeLocation;
  private boolean inserting;
  private int isDobleRotation;
  private boolean modeDelete;
  
  public BSTMemento(BSTHeightBalancedView bstView, BSTNodeView node) {
    this(bstView);
    if (node != null){
      nodes = new ArrayList<BSTNodeMemento>();
      recursiveCopy(node);
    }
  }

  public BSTMemento(BSTHeightBalancedView bstView) {
    super();
    this.root = bstView.getRoot();
    this.nodes = null;
    this.rootPosition = bstView.getRootPosition();
    this.lastPolled = bstView.getLastPolled();
    this.nodeRemoved = bstView.getNodeRemoved();
    this.nodesVisited = new LinkedList<BSTNodeView>(bstView.getNodesVisited());
    this.changeLocation = bstView.isChangeLocation();
    this.inserting = bstView.isInserting();
    this.isDobleRotation = bstView.isDobleRotation();
    this.modeDelete = bstView.isModeDelete();
  }

  public Point2D getRootPosition() {
    return rootPosition;
  }

  public BSTNodeView getLastPolled() {
    return lastPolled;
  }

  public BSTNodeView getNodeRemoved() {
    return nodeRemoved;
  }

  public LinkedList<BSTNodeView> getNodesVisited() {
    return nodesVisited;
  }

  public boolean isChangeLocation() {
    return changeLocation;
  }

  public boolean isInserting() {
    return inserting;
  }

  public int getIsDobleRotation() {
    return isDobleRotation;
  }

  public BSTNodeView getRoot() {
    return root;
  }
  
  public boolean isModeDelete() {
    return modeDelete;
  }

  public void refreshState(BSTNodeView node){
    this.nodes.clear();
    this.recursiveCopy(node);
  }
  
  public void actualizeNodes(){
    boolean first = true;
    
    if (nodes == null) return;
    for (BSTNodeMemento memento: this.nodes){
      BSTNodeView node = memento.getNode();
      BSTNodeView copy = memento.getCopy();
      node.left = copy.left;
      node.right = copy.right;
      node.setParent( copy.getParent() );
      node.setVisible( copy.isVisible() );
      node.moveTo( copy.getPosition() );
      
      if (first){ 
        first = false;
        BSTNodeView parent = node.getParent();
        
        if (parent != null){
          if ( parent.getData().compareTo( node.getData() ) == -1 )
            parent.right = node;
          else
            parent.left = node;
        }
      }
        
    }
  }
  
  private void recursiveCopy(BSTNodeView node) {
    
    if (node == null)
      return;
    
    nodes.add( new BSTNodeMemento(node));
    
    recursiveCopy(node.left);
    recursiveCopy(node.right);
    
  }
}
