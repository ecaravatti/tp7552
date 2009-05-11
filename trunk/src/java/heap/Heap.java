//package heap;
//
//import java.util.AbstractCollection;
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.Iterator;
//import java.util.List;
//import java.util.NoSuchElementException;
//import common.Element;
//
//public class Heap extends AbstractCollection<Element<Integer>>{
//
//    /**
//     * La capacidad por defecto del Heap.
//     */
//    private final static int DEFAULT_CAPACITY = 13;
//    /**
//     * La cantidad de elementos actual del Heap.
//     */
//    int actualSize;  
//    /**
//     * Elementos en el Heap.
//     */
//    List<Element<Integer>> elements; 
//    /**
//     * Si es true, el primer elemento determinado por el orden
//     * del HeapSort será devuelto. Si es false, el último elemento
//     * será devuelto. 
//     */
//    boolean isMinHeap; 
//   
//
//    /**
//     * Contruye un nuevo max Heap con la capacidad por defecto.
//     */
//    public Heap() {
//        this(DEFAULT_CAPACITY, false);
//    }  
//    
//    /**
//     * Contruye un nuevo max Heap con la capacidad inicial indicada.
//     *  
//     * @param capacity  La capacidad inicial del Heap. Debe ser mayor a cero.
//     * @throws IllegalArgumentException  
//     *  si <code>capacity</code> es <= <code>0</code>
//     */
//    public Heap(int capacity) {
//        this(capacity, false);
//    }
//
//    /**
//     * Construye un nuevo min o max Heap.
//     *
//     * @param isMinHeap  Si es true, el Heap será creado como min Heap;
//     * si es false, será max Heap.
//     */
//    public Heap(boolean isMinHeap) {
//        this(DEFAULT_CAPACITY, isMinHeap);
//    }
//
//    /**
//     * Construye un min o max Heap con la capacidad inicial indicada.
//     *
//     * @param capacity La capacidad inicial del Heap. Debe ser mayor a cero.
//     * @param isMinHeap Si es true, el Heap será creado como min Heap;
//     * si es false, será max Heap.
//     * @throws IllegalArgumentException 
//     *  Si <code>capacity</code> es <code><= 0</code>
//     */
//    public Heap(int capacity, boolean isMinHeap) {
//        if (capacity <= 0) {
//            throw new IllegalArgumentException("Capacidad inválida");
//        }
//        this.isMinHeap = isMinHeap;
//
//        elements = new ArrayList<Element<Integer>>(capacity);        
//    }
//   
//
//    //-----------------------------------------------------------------------
//    
//    /**
//     * Limpia los elementos en la cola.
//     */
//    public void clear() {
//        elements.clear();
//        actualSize = 0;
//    }
//
//    /**
//     * Testea si la cola está vacía.
//     *
//     * @return <code>true</code> si la cola esta vacía; false en caso 
//     * contrario.
//     */
//    public boolean isEmpty() {
//        return actualSize == 0;
//    }
//
//    /**
//     * Testea si la cola está llena.
//     *
//     * @return <code>true</code> if queue is full; <code>false</code>
//     *  otherwise.
//     */
//    public boolean isFull() {
//        return elements.size() == actualSize;
//    }
//
//    /**
//     * Inserta un elemento en la cola.
//     *
//     * @param element  El elemento a ser insertado.
//     */
//    public void insert(Element<Integer> element) {
//        //Ubicar elemento en su lugar del árbol.
//        if (isMinHeap) {
//            swapUpMinHeap(element);
//        } else {
//            swapUpMaxHeap(element);
//        }
//    }
//
//    /**
//     * Devuelve el elemento tope del Heap, sin removerlo.
//     *
//     * @return El elemento tope del Heap.
//     * @throws NoSuchElementException  SI <code>isEmpty() == true</code>
//     */
//    public Element<Integer> peek() throws NoSuchElementException {
//        if (isEmpty()) {
//            throw new NoSuchElementException();
//        } else {
//            return elements.get(0);
//        }
//    }
//
//    /**
//     * Remueve el elemento tope de la cola y lo remueve.
//     *
//     * @return El elemento tope del Heap.
//     * @throws NoSuchElementException  si <code>isEmpty() == true</code>
//     */
//    public Element<Integer> pop() throws NoSuchElementException {
//        final Element<Integer> result = peek();
//        elements.set(0, elements.get(actualSize--));
//
//        // Declarar como null el elemento eliminado
//        elements.set(actualSize + 1, null);
//
//        if (actualSize != 0) {
//            // Ubicar al elemento raíz en su lugar en el árbol.
//            if (isMinHeap) {
//                swapDownMinHeap(0);
//            } else {
//                swapDownMaxHeap(0);
//            }
//        }
//
//        return result;
//    }
//
//    /**
//     * Intercambia un elemento hacia abajo, desde la posición indicada.
//     * <p>
//     * Asume que es un min Heap.
//     *
//     * @param index Índice del elemento.
//     */
//    protected void swapDownMinHeap(final int index) {
//        final Element<Integer> element = elements.get(index);
//        int hole = index;
//        int child = 0;
//        
//        while ((hole * 2) <= actualSize) {
//            child = hole * 2;
//
//            // Si se tiene un hijo derecho y no puede ser movido hacia
//            // arriba, moverse al otro hijo.
//            if (child != actualSize && compare(elements.get(child + 1), elements.get(child)) < 0) {
//                child++;
//            }
//
//            // Se termina la búsqueda si el elemento en movimiento
//            if (compare(elements.get(child), element) >= 0) {
//                break;
//            }
//
//            elements.set(hole, elements.get(child));
//            hole = child;
//        }
//
//        elements.set(hole, element);
//    }
//
//    /**
//     * Percolates element down heap from the position given by the index.
//     * <p>
//     * Assumes it is a maximum heap.
//     *
//     * @param index the index of the element
//     */
//    protected void swapDownMaxHeap(final int index) {
//        final Object element = elements[index];
//        int hole = index;
//
//        while ((hole * 2) <= actualSize) {
//            int child = hole * 2;
//
//            // if we have a right child and that child can not be percolated
//            // up then move onto other child
//            if (child != actualSize && compare(elements[child + 1], elements[child]) > 0) {
//                child++;
//            }
//
//            // if we found resting place of bubble then terminate search
//            if (compare(elements[child], element) <= 0) {
//                break;
//            }
//
//            elements[hole] = elements[child];
//            hole = child;
//        }
//
//        elements[hole] = element;
//    }
//
//    /**
//     * Percolates element up heap from the position given by the index.
//     * <p>
//     * Assumes it is a minimum heap.
//     *
//     * @param index the index of the element to be percolated up
//     */
//    protected void percolateUpMinHeap(final int index) {
//        int hole = index;
//        Object element = elements[hole];
//        while (hole > 1 && compare(element, elements[hole / 2]) < 0) {
//            // save element that is being pushed down
//            // as the element "bubble" is percolated up
//            final int next = hole / 2;
//            elements[hole] = elements[next];
//            hole = next;
//        }
//        elements[hole] = element;
//    }
//
//    /**
//     * Percolates a new element up heap from the bottom.
//     * <p>
//     * Assumes it is a minimum heap.
//     *
//     * @param element the element
//     */
//    protected void swapUpMinHeap(final Object element) {
//        elements[++actualSize] = element;
//        percolateUpMinHeap(actualSize);
//    }
//
//    /**
//     * Percolates element up heap from from the position given by the index.
//     * <p>
//     * Assume it is a maximum heap.
//     *
//     * @param index the index of the element to be percolated up
//     */
//    protected void percolateUpMaxHeap(final int index) {
//        int hole = index;
//        Object element = elements[hole];
//        
//        while (hole > 1 && compare(element, elements[hole / 2]) > 0) {
//            // save element that is being pushed down
//            // as the element "bubble" is percolated up
//            final int next = hole / 2;
//            elements[hole] = elements[next];
//            hole = next;
//        }
//
//        elements[hole] = element;
//    }
//    
//    /**
//     * Percolates a new element up heap from the bottom.
//     * <p>
//     * Assume it is a maximum heap.
//     *
//     * @param element the element
//     */
//    protected void swapUpMaxHeap(final Object element) {
//        elements[++actualSize] = element;
//        percolateUpMaxHeap(actualSize);
//    }
//    
//    /**
//     * Compares two objects using the comparator if specified, or the
//     * natural order otherwise.
//     * 
//     * @param a  the first object
//     * @param b  the second object
//     * @return -ve if a less than b, 0 if they are equal, +ve if a greater than b
//     */
//    private int compare(Object a, Object b) {
//        if (m_comparator != null) {
//            return m_comparator.compare(a, b);
//        } else {
//            return ((Comparable) a).compareTo(b);
//        }
//    }
//
//    /**
//     * Increases the size of the heap to support additional elements
//     */
//    protected void grow() {
//        final Object[] elements = new Object[elements.length * 2];
//        System.arraycopy(elements, 0, elements, 0, elements.length);
//        elements = elements;
//    }
//
//    /**
//     * Returns a string representation of this heap.  The returned string
//     * is similar to those produced by standard JDK collections.
//     *
//     * @return a string representation of this heap
//     */
//    public String toString() {
//        final StringBuffer sb = new StringBuffer();
//
//        sb.append("[ ");
//
//        for (int i = 1; i < actualSize + 1; i++) {
//            if (i != 1) {
//                sb.append(", ");
//            }
//            sb.append(elements[i]);
//        }
//
//        sb.append(" ]");
//
//        return sb.toString();
//    }
//
//
//    /**
//     * Returns an iterator over this heap's elements.
//     *
//     * @return an iterator over this heap's elements
//     */
//    public Iterator iterator() {
//        return new Iterator() {
//
//            private int index = 1;
//            private int lastReturnedIndex = -1;
//
//            public boolean hasNext() {
//                return index <= actualSize;
//            }
//
//            public Object next() {
//                if (!hasNext()) throw new NoSuchElementException();
//                lastReturnedIndex = index;
//                index++;
//                return elements[lastReturnedIndex];
//            }
//
//            public void remove() {
//                if (lastReturnedIndex == -1) {
//                    throw new IllegalStateException();
//                }
//                elements[ lastReturnedIndex ] = elements[ actualSize ];
//                elements[ actualSize ] = null;
//                actualSize--;  
//                if( actualSize != 0 && lastReturnedIndex <= actualSize) {
//                    int compareToParent = 0;
//                    if (lastReturnedIndex > 1) {
//                        compareToParent = compare(elements[lastReturnedIndex], 
//                            elements[lastReturnedIndex / 2]);  
//                    }
//                    if (isMinHeap) {
//                        if (lastReturnedIndex > 1 && compareToParent < 0) {
//                            percolateUpMinHeap(lastReturnedIndex); 
//                        } else {
//                            swapDownMinHeap(lastReturnedIndex);
//                        }
//                    } else {  // max heap
//                        if (lastReturnedIndex > 1 && compareToParent > 0) {
//                            percolateUpMaxHeap(lastReturnedIndex); 
//                        } else {
//                            swapDownMaxHeap(lastReturnedIndex);
//                        }
//                    }          
//                }
//                index--;
//                lastReturnedIndex = -1; 
//            }
//
//        };
//    }
//
//
//    /**
//     * Adds an object to this heap. Same as {@link #insert(Object)}.
//     *
//     * @param object  the object to add
//     * @return true, always
//     */
//    public boolean add(Object object) {
//        insert(object);
//        return true;
//    }
//
//    /**
//     * Returns the priority element. Same as {@link #peek()}.
//     *
//     * @return the priority element
//     * @throws BufferUnderflowException if this heap is empty
//     */
//    public Object get() {
//        try {
//            return peek();
//        } catch (NoSuchElementException e) {
//            throw new BufferUnderflowException();
//        }
//    }
//
//    /**
//     * Removes the priority element. Same as {@link #pop()}.
//     *
//     * @return the removed priority element
//     * @throws BufferUnderflowException if this heap is empty
//     */
//    public Object remove() {
//        try {
//            return pop();
//        } catch (NoSuchElementException e) {
//            throw new BufferUnderflowException();
//        }
//    }
//
//    /**
//     * Returns the number of elements in this heap.
//     *
//     * @return the number of elements in this heap
//     */
//    public int size() {
//        return actualSize;
//    }
//
//}
