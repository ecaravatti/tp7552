package event.tree;

import java.util.EventObject;

import model.collection.tree.BSTNode;

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
