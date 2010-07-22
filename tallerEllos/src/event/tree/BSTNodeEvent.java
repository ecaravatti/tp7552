package event.tree;

import java.util.EventObject;

import model.collection.tree.BSTNode;

/**
 *
 */
@SuppressWarnings("serial")
public class BSTNodeEvent<K extends Comparable<K>> extends EventObject {

    public BSTNodeEvent(BSTNode<K> source) {
        super(source);
    }

    @SuppressWarnings("unchecked")
	@Override
    public BSTNode<K> getSource() {
        return (BSTNode<K>) source;
    }
}
