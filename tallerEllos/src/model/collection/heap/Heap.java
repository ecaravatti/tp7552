package model.collection.heap;

import java.util.List;
import java.util.Vector;

import model.exception.heap.EmptyHeapException;

public class Heap<T extends Comparable<T>> extends HeapObservable<T> {

    private List<T> elements;
    private int capacity = 1000000;

    /**
     * Construye un heap vacio.
     */
    public Heap() {
        elements = new Vector<T>();
    }

    public void clear() {
        elements.clear();
    }

    public int size() {
        return elements.size();
    }

    public void insert(T element) {
        elements.add(element);
        fireAddingItem(element);
        filterUp(size() - 1);
        fireItemAdded(element);
    }

    public T remove() throws EmptyHeapException {
        T root;

        if (size() == 0) {
        	fireEmptyHeap();
            throw new EmptyHeapException();
        }
        if (size() == 1) {
            root = elements.remove(0);
            fireDeletingItem(root);
            fireItemDeleted(root);
            return root;
        }
        root = elements.get(0);
        elements.set(0, elements.remove(size() - 1));

        // intercambiar el último elemento con el primero
        fireDeletingItem(root);        

        filterDown(0);
        fireItemDeleted(root);

        return root;
    }

    public List<T> toList() {
        List<T> heap = new Vector<T>(elements);
        return heap;
    }

    /**
     * Obtiene el indice del padre del elemento que se encuentra en la posicion
     * dada por el index
     * 
     * @param index indice del elemento a obtener el padre
     * @return el indice del padre del elemento.
     */
    protected int getParent(int index) {
        return (index - 1) / 2;
    }

    /**
     * Obtiene el indice del hijo derecho del elemento que se encuentra en la
     * posicion dada por el indice
     * 
     * @param index indice del elemento a obtener el hijo derecho
     * @return el indice del hermano derecho del elemento.
     */
    protected int getRightChild(int index) {
        return 2 * (index + 1);
    }

    /**
     * Obtiene el indice del hijo izquierdo del elemento que se encuentra en la
     * posicion dada por el indice
     * 
     * @param index indice del elemento a obtener el hijo izquierdo
     * @return el indice del hermano izquierdo del elemento.
     */
    protected int getLeftChild(int index) {
        return 2 * index + 1;
    }

    /**
     * Restaura la propiedad de heap desplazandose hacia arriba por el heap.
     * 
     * @param startIndex indice a partir del cual debe comenzar a restaurar la propiedad de
     * heap
     */
    protected void filterUp(int startIndex) {
        int current = startIndex;
        int parent = getParent(current);
        T elemStartInd = elements.get(startIndex);

        while (current != 0) {

            if (elements.get(parent).compareTo(elemStartInd) > 0) {
                break; // propiedad restaurada

            }
            // Intercambia el valor del padre con el del hijo
            elements.set(current, elements.get(parent));
            fireItemsSwapped(current, parent);
            // Desplazarse hacia arriba por el heap
            current = parent;
            parent = getParent(current);
        }

        elements.set(current, elemStartInd);
    }

    /**
     * Restaura la propiedad de heap desplazandose hacia abajo por el heap.
     * 
     * @param startIndex startIndex indice a partir del cual debe comenzar a restaurar la
     * propiedad de heap
     */
    protected void filterDown(int startIndex) {
        int current = startIndex;
        int child = getLeftChild(current);
        T element = elements.get(current);


        while (child < size()) {
            int rightChild = child + 1;

            // Busco el hijo mas chico
            if (rightChild < size() &&
                    elements.get(rightChild).compareTo(elements.get(child)) > 0) {
                child = rightChild;
            }
            if (element.compareTo(elements.get(child)) > 0) {
                break;
            }
            // Intercambia el hijo mas chico con el padre
            elements.set(current, elements.get(child));
            fireItemsSwapped(child, current);

            current = child;
            child = getLeftChild(current);
        }


        elements.set(current, element);
    }
    
    public boolean isEmpty() {
    	return elements.isEmpty();
    }

    public boolean isFull() {
    	return (elements.size() == capacity);
    }
    
    public int getCapacity() {
    	return this.capacity;
    }
    
    public void setCapacity(int capacity) {
    	this.capacity = capacity;
    }
}
