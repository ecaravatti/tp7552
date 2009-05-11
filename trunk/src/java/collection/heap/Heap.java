package collection.heap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import common.Element;

public class Heap {

    /**
     * La capacidad por defecto del Heap.
     */
    private final static int DEFAULT_CAPACITY = 13;
    
    /**
     * La cantidad de elementos actual del Heap.
     */
    private int actualSize;  
    
    /**
     * La cantidad de elementos insertados desde
     * la creacion del Heap.
     */
    private int elementCount;
    
    /**
     * Elementos en el Heap.
     */
    List<Element<Integer>> elements; 
    
    /**
     * Si es true, el primer elemento determinado por el orden
     * del HeapSort será devuelto. Si es false, el último elemento
     * será devuelto. 
     */
    boolean isMinHeap; 
   

    /**
     * Contruye un nuevo max Heap con la capacidad por defecto.
     */
    public Heap() {
        this(DEFAULT_CAPACITY, false);
    }  
    
    /**
     * Contruye un nuevo max Heap con la capacidad inicial indicada.
     *  
     * @param capacity  La capacidad inicial del Heap. Debe ser mayor a cero.
     * @throws IllegalArgumentException  
     *  si <code>capacity</code> es <= <code>0</code>
     */
    public Heap(int capacity) {
        this(capacity, false);
    }

    /**
     * Construye un nuevo min o max Heap.
     *
     * @param isMinHeap  Si es true, el Heap será creado como min Heap;
     * si es false, será max Heap.
     */
    public Heap(boolean isMinHeap) {
        this(DEFAULT_CAPACITY, isMinHeap);
    }

    /**
     * Construye un min o max Heap con la capacidad inicial indicada.
     *
     * @param capacity La capacidad inicial del Heap. Debe ser mayor a cero.
     * @param isMinHeap Si es true, el Heap será creado como min Heap;
     * si es false, será max Heap.
     * @throws IllegalArgumentException 
     *  Si <code>capacity</code> es <code><= 0</code>
     */
    public Heap(int capacity, boolean isMinHeap) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacidad inválida");
        }
        this.isMinHeap = isMinHeap;
        elementCount = 0;
        elements = new ArrayList<Element<Integer>>(capacity);        
    }
   

    //-----------------------------------------------------------------------
    
    /**
     * Limpia los elementos en la cola.
     */
    public void clear() {
        elements.clear();
        actualSize = 0;
    }

    /**
     * Testea si la cola está vacía.
     *
     * @return <code>true</code> si la cola esta vacía; false en caso 
     * contrario.
     */
    public boolean isEmpty() {
        return (actualSize == 0);
    }

    /**
     * Testea si la cola está llena.
     *
     * @return <code>true</code> if queue is full; <code>false</code>
     *  otherwise.
     */
    public boolean isFull() {
        return (elements.size() == actualSize);
    }

    /**
     * Inserta un elemento en la cola.
     *
     * @param element  El elemento a ser insertado.
     */
    public void insert(Integer value) {
        Element<Integer> element = new Element<Integer>(value, elementCount++);
    	
        //Ubicar elemento en su lugar del árbol.
        if (isMinHeap) {
            swapUpMinHeap(element);
        } else {
            swapUpMaxHeap(element);
        }
    }

    /**
     * Devuelve el elemento tope del Heap, sin removerlo.
     *
     * @return El elemento tope del Heap.
     * @throws NoSuchElementException  Si <code>isEmpty() == true</code>
     */
    public Integer peek() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            return elements.get(0).getValue();
        }
    }

    /**
     * Remueve el elemento tope de la cola y lo remueve.
     *
     * @return El elemento tope del Heap.
     * @throws NoSuchElementException  si <code>isEmpty() == true</code>
     */
    public Integer pop() throws NoSuchElementException {
        final Integer result = peek();
        elements.set(0, elements.get(--actualSize));

        if (actualSize != 0) {
            // Ubicar al elemento raíz en su lugar en el árbol.
            if (isMinHeap) {
                swapDownMinHeap(0);
            } else {
                swapDownMaxHeap(0);
            }
        }

        return result;
    }

    /**
     * Intercambia un elemento hacia abajo, desde la posición indicada.
     * <p>
     * Asume min Heap construido.
     *
     * @param index Índice del elemento.
     */
    protected void swapDownMinHeap(final int index) {
        final Element<Integer> element = elements.get(index);
        int hole = index;
        int child = 0;
        
        while ((2 * hole + 1) <= actualSize) {
            child = (2 * hole) + 1;

            // Si se tiene un hijo derecho y no puede ser movido hacia
            // arriba, moverse al otro hijo.
            if (child != actualSize && compare(elements.get(child + 1), elements.get(child)) < 0) {
                child++;
            }

            // Se termina la búsqueda si el elemento en movimiento encuentra
            // su lugar en el arbol
            if (compare(elements.get(child), element) >= 0) {
                break;
            }

            elements.set(hole, elements.get(child));
            hole = child;
        }

        elements.set(hole, element);
    }

    /**
     * Intercambia un elemento hacia abajo, desde la posición indicada.
     * <p>
     * Asume max Heap construido.
     *
     * @param index Índice del elemento.
     */
    protected void swapDownMaxHeap(final int index) {
        final Element<Integer> element = elements.get(index);
        int hole = index;
        int child = 0;

        while ((hole * 2 + 1) <= actualSize) {
            child = (2 * hole)  + 1;

            // Si se tiene un hijo derecho y no puede ser movido hacia
            // arriba, moverse al otro hijo.   
            if (child != actualSize && compare(elements.get(child + 1), elements.get(child)) > 0) {
                child++;
            }

            // Se termina la búsqueda si el elemento en movimiento encuentra
            // su lugar en el arbol
            if (compare(elements.get(child), element) <= 0) {
                break;
            }

            elements.set(hole, elements.get(child));
            hole = child;
        }

        elements.set(hole, element);
    }

    /**
     * Intercambia un elemento hacia arriba, desde la posición indicada.
     * <p>
     * Asume min Heap construido.
     *
     * @param index Índice del elemento.
     */
    protected void swapUpMinHeap(final int index) {
        int hole = index;
        Element<Integer> element = elements.get(hole);
        int next = 0;
        
        while (hole > 0 && compare(element, elements.get((hole - 1)/ 2)) < 0) {
            // Salvar elemento que esta siendo enviado hacia abajo
            // mientras el elemento a reemplazar sube un nivel.
            next = (hole - 1)/ 2;
            elements.set(hole, elements.get(next));
            hole = next;
        }
        elements.set(hole, element);
    }

    /**
     * Intercambia un nuevo elemento desde las hojas, hacia arriba.
     * <p>
     * Asume min Heap creado.
     *
     * @param element El elemento a intercambiar.
     */
    protected void swapUpMinHeap(final Element<Integer> element) {
        elements.add(element);
        swapUpMinHeap(actualSize++);
    }

    /**
     * Intercambia un elemento hacia arriba, desde la posición indicada.
     * <p>
     * Asume max Heap construido.
     *
     * @param index Índice del elemento.
     */
    protected void swapUpMaxHeap(final int index) {
        int hole = index;
        Element<Integer> element = elements.get(hole);
        int next = 0;
        
        while (hole > 0 && compare(element, elements.get((hole - 1)/ 2)) > 0) {
        	// Salvar elemento que esta siendo enviado hacia abajo
            // mientras el elemento a reemplazar sube un nivel.
            next = (hole - 1)/ 2;
            elements.set(hole, elements.get(next));
            hole = next;
        }
        
        elements.set(hole, element);
    }
    
    /**
     * Intercambia un nuevo elemento desde las hojas, hacia arriba.
     * <p>
     * Asume max Heap creado.
     *
     * @param element El elemento a intercambiar.
     */
    protected void swapUpMaxHeap(/*final*/ Element<Integer> element) {
    	elements.add(element);
        this.swapUpMaxHeap(actualSize++);
    }
    
    /**
     * Compara dos elementos.
     * 
     * @param e1  El primer elemento.
     * @param e2  El segundo elemento.
     * @return -1 si e1 menor a e2, 0 si son iguales, +1 si mayor a e2
     */
    private int compare(Element<Integer> e1, Element<Integer> e2) {
    	Integer int1 = e1.getValue();
    	Integer int2 = e2.getValue();

    	return int1.compareTo(int2);
    }

   
    /**
     * Devuelve un string con la representacion del Heap.
     *
     * @return Un string representativo del contenido del Heap.
     */
    public String toString() {
        final StringBuffer sb = new StringBuffer();

        sb.append("[ ");

        for (int i = 0; i < actualSize; i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append(elements.get(i).getValue());
        }

        sb.append(" ]");

        return sb.toString();
    }


    /**
     * Returns an iterator over this heap's elements.
     *
     * @return an iterator over this heap's elements
     */
    public Iterator<Element<Integer>> iterator() {
        return new Iterator<Element<Integer>>() {

            private int index = 0;
            private int lastReturnedIndex = -1;

            public boolean hasNext() {
                return index <= actualSize;
            }

            public Element<Integer> next() {
                if (!hasNext()) throw new NoSuchElementException();
                lastReturnedIndex = index;
                index++;
                return elements.get(lastReturnedIndex);
            }

            public void remove() {
                if (lastReturnedIndex == -1) {
                    throw new IllegalStateException();
                }
                elements.set(lastReturnedIndex, elements.get(actualSize));
                elements.set(actualSize, null);
                actualSize--;  
                if( actualSize != 0 && lastReturnedIndex <= actualSize) {
                    int compareToParent = 0;
                    if (lastReturnedIndex > 0) {
                        compareToParent = compare(elements.get(lastReturnedIndex), 
                            elements.get((lastReturnedIndex - 1)/ 2));  
                    }
                    if (isMinHeap) {
                        if (lastReturnedIndex > 0 && compareToParent < 0) {
                            swapUpMinHeap(lastReturnedIndex); 
                        } else {
                            swapDownMinHeap(lastReturnedIndex);
                        }
                    } else {  // max heap
                        if (lastReturnedIndex > 0 && compareToParent > 0) {
                            swapUpMaxHeap(lastReturnedIndex); 
                        } else {
                            swapDownMaxHeap(lastReturnedIndex);
                        }
                    }          
                }
                index--;
                lastReturnedIndex = -1; 
            }

        };
    }


    /**
     * Agrega un elemento al Heap. Igual a insert(Element<Integer>)}.
     *
     * @param element El elemento a insertar
     * @return true, siempre
     */
    public boolean add(Integer value) {
        this.insert(value);
        return true;
    }

    /**
     * Devuelve el elemento tope. Igual a peek().
     *
     * @return El elemento prioritario en la cola.
     */
    public Integer get() {
    	return this.peek();
    }

    /**
     * Remueve el elemento tope. Igual a pop().
     *
     * @return El elemento removido.
     */
    public Integer remove() {
    	return this.pop();
    }

    /**
     * Devuelve el numero de elementos en el Heap.
     *
     * @return El numero de elementos en el Heap. 
     */
    public int size() {
        return actualSize;
    }
}