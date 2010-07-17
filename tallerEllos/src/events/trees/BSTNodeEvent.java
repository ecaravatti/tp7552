package events.trees;

import java.util.EventObject;
import model.structures.trees.BSTNode;

/**
 *
 * @author Exe Curia
 */
public class BSTNodeEvent<K extends Comparable<K>> extends EventObject {

    public BSTNodeEvent(BSTNode<K> source) {
        super(source);
    }

    @Override
    public BSTNode<K> getSource() {
        return (BSTNode<K>) source;
    }
}
