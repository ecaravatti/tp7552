package model.structures.commons;

/**
 * Data structure node representation. Each data structure item is of type T.
 * 
 * @author pgorin
 * 
 * @param <T> type of item stored in the data structure.
 */
public class StructureNode<T> {

    // Item stored in the data structure node.
    private T item;
    // Next node in the data structure.
    private StructureNode<T> nextNode;

    /**
     * Get the item of the data structure node.
     */
    public T getItem() {
        return this.item;
    }

    /**
     * Set the item of the data structure node.
     */
    public void setItem(T item) {
        this.item = item;
    }

    /**
     * Get the next node of the data structure.
     */
    public StructureNode<T> getNextNode() {
        return this.nextNode;
    }

    /**
     * Set the next node of the data structure.
     */
    public void setNextNode(StructureNode<T> nextNode) {
        this.nextNode = nextNode;
    }

}
