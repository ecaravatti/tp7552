package view.collection.tree;

/**
 *
 * 
 */
public interface BSTPrimitives {

	public void selectLeftChild();

	public void selectRightChild();

	public void setParentLeftChild(BSTNodeView child);

	public void setParentRightChild(BSTNodeView child);

	public void balanceUpdated(BSTNodeView node, int balance);

	public void nodeTraversed(BSTNodeView node);

	public void nodeRemoved(BSTNodeView node);

	/**
	 * Calcula las posiciones finales para todos los nodos
	 */
	public void setFinalPositions();
}
