package events.trees;

import java.util.EventListener;

/**
 *
 * @author Exe Curia
 */
public interface BSTNodeListener<K extends Comparable<K>> extends EventListener {

    public void balanceUpdated(BSTNodeEvent<K> event);
    public void getLeftChild(BSTNodeEvent<K> event);
    public void getRightChild(BSTNodeEvent<K> event);
    public void leftChildAdded(BSTNodeEvent<K> event);
    public void rightChildAdded(BSTNodeEvent<K> event);
    public void nodeTraversed(BSTNodeEvent<K> event);
    public void removed(BSTNodeEvent<K> event);
}
