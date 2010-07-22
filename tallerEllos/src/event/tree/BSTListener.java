package event.tree;

import java.util.EventListener;

/**
 *
 * @author Exe Curia
 */
public interface BSTListener<K extends Comparable<K>> extends EventListener {

    public void rootAdded(BSTEvent<K> event);
    public void nodeRotatedToLeft(BSTEvent<K> event);
    public void nodeRotatedToRight(BSTEvent<K> event);
    public void dobleRotationStarted(BSTEvent<K> event);
    public void nodeFound(BSTEvent<K> event);
    public void nodeNotFound(BSTEvent<K> event);
    public void rootRemoved(BSTEvent<K> event);
    public void rotateFinishedInDelete(BSTEvent<K> event);
    public void traverseFinished(BSTEvent<K> event);
}
