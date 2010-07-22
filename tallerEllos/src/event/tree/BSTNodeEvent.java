package event.tree;

import java.util.EventObject;

import model.collection.tree.BSTNode;

/**
 *
 */
public class BSTNodeEvent<K extends Comparable<K>> extends EventObject {

	private static final long serialVersionUID = 1L;

	public BSTNodeEvent(BSTNode<K> source) {
        super(source);
    }

    @SuppressWarnings("unchecked")
	@Override
    public BSTNode<K> getSource() {
        return (BSTNode<K>) source;
    }
}
