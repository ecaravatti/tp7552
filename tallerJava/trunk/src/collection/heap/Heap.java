package collection.heap;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.lang.NullPointerException;
import command.Command;
import command.heap.InsertCommand;
import command.heap.RemoveRootCommand;
import command.heap.SwapCommand;
import common.Element;

public class Heap<T extends Comparable<T>> extends AbstractCollection<T>{

	/**
	 * La capacidad por defecto del Heap.
	 */
	private final static int DEFAULT_CAPACITY = 13;

	/**
	 * Lista de comandos a ser interpretados por la vista para la animación de
	 * los eventos.
	 */
	private Queue<Command> commandsQueue;

	/**
	 * La cantidad de elementos actual del Heap.
	 */
	private int actualSize;

	/**
	 * La cantidad de elementos insertados desde la creacion del Heap.
	 */
	private int elementCount;

	/**
	 * Elementos en el Heap.
	 */
	List<Element<T>> elements;

	/**
	 * Si es true, el primer elemento determinado por el orden del HeapSort será
	 * devuelto. Si es false, el último elemento será devuelto.
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
	 * @param capacity
	 *            La capacidad inicial del Heap. Debe ser mayor a cero.
	 * @throws IllegalArgumentException
	 *             si <code>capacity</code> es <= <code>0</code>
	 */
	public Heap(int capacity) {
		this(capacity, false);
	}

	/**
	 * Construye un nuevo min o max Heap.
	 * 
	 * @param isMinHeap
	 *            Si es true, el Heap será creado como min Heap; si es false,
	 *            será max Heap.
	 */
	public Heap(boolean isMinHeap) {
		this(DEFAULT_CAPACITY, isMinHeap);
	}

	/**
	 * Construye un min o max Heap con la capacidad inicial indicada.
	 * 
	 * @param capacity
	 *            La capacidad inicial del Heap. Debe ser mayor a cero.
	 * @param isMinHeap
	 *            Si es true, el Heap será creado como min Heap; si es false,
	 *            será max Heap.
	 * @throws IllegalArgumentException
	 *             Si <code>capacity</code> es <code><= 0</code>
	 */
	public Heap(int capacity, boolean isMinHeap) {
		
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		
		if (capacity <= 0) {
			throw new IllegalArgumentException("Capacidad inválida");
		}

		this.isMinHeap = isMinHeap;
		actualSize = 0;
		elementCount = 0;
		elements = new ArrayList<Element<T>>(capacity);
		commandsQueue = new LinkedList<Command>();
	}
	
	/**
	 * Construye un min o max Heap con el contenido de la colección
	 * pasada.
	 * 
	 * @param elements
	 *            La capacidad inicial del Heap. Debe ser mayor a cero.
	 * @param isMinHeap
	 *            Si es true, el Heap será creado como min Heap; si es false,
	 *            será max Heap.
	 * @throws IllegalArgumentException
	 *             Si <code>capacity</code> es <code><= 0</code>
	 *             @throws NullPointerException
	 *             Si la colección especificada es <code>null</code>
	 */
	public Heap(Collection<T> elements, boolean isMinHeap){
		this(isMinHeap);
		
		if (elements == null){
			throw new NullPointerException("Colección especificada null");
		}
		
		this.addAll(elements);
	}
	
	/**
	 * Construye un  max Heap con el contenido de la colección
	 * pasada.
	 * 
	 * @param elements
	 *            La capacidad inicial del Heap. Debe ser mayor a cero.

	 * @throws IllegalArgumentException
	 *             Si <code>capacity</code> es <code><= 0</code>
	 */
	public Heap(Collection<T> elements){
		this(elements, false);
	}

	// -----------------------------------------------------------------------

	/**
	 * Limpia los elementos en el heap y la cola de comandos.
	 */
	public void clear() {
		elements.clear();
		commandsQueue.clear();
		actualSize = 0;
	}

	/**
	 * Testea si la cola está vacía.
	 * 
	 * @return <code>true</code> si la cola esta vacía; false en caso contrario.
	 */
	public boolean isEmpty() {
		return (actualSize == 0);
	}

	/**
	 * Testea si la cola está llena.
	 * 
	 * @return <code>true</code> if queue is full; <code>false</code> otherwise.
	 */
	public boolean isFull() {
		return (elements.size() == actualSize);
	}

	/**
	 * Inserta un elemento en la cola.
	 * 
	 * @param element
	 *            El elemento a ser insertado.
	 * @throws NoSuchElementException
	 *           Si <code>value == null</code>
	 */
	public void insert(T value) {
		
		if (value == null){
			throw new NullPointerException();
		}
		
		Element<T> element = new Element<T>(value, elementCount++);

		// Ubicar elemento en su lugar del árbol.
		if (isMinHeap) {
			this.swapUpMinHeap(element);
		} else {
			this.swapUpMaxHeap(element);
		}
	}

	/**
	 * Devuelve el elemento tope del Heap, sin removerlo.
	 * 
	 * @return El elemento tope del Heap.
	 * @throws NoSuchElementException
	 *             Si <code>isEmpty() == true</code>
	 */
	public T peek() throws NoSuchElementException {
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
	 * @throws NoSuchElementException
	 *             si <code>isEmpty() == true</code>
	 */
	public T pop() throws NoSuchElementException {
		final T result = peek();
		actualSize--;

		// Emisión de comando para eliminación de la raíz
		int newRootParentIndex = (int) (actualSize - 1) / 2;
		Element<T> root = elements.get(0);
		Element<T> newRoot = elements.get(actualSize);
		Element<T> newRootParent = elements.get(newRootParentIndex);
		commandsQueue.offer(new RemoveRootCommand<T>(root, newRoot,
				newRootParent, newRootParentIndex % 2 != 0));

		elements.set(0, elements.get(actualSize));
		elements.remove(actualSize);

		if (!isEmpty()) {
			// Ubicar al nuevo elemento raíz en su lugar en el árbol.
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
	 * @param index
	 *            Índice del elemento.
	 */
	protected void swapDownMinHeap(final int index) {
		final Element<T> element = elements.get(index);
		int hole = index;
		int child = (hole << 1) + 1;
		boolean continueSwap = child < actualSize;

		while (continueSwap) {
			// Si se tiene un hijo derecho y no puede ser movido hacia
			// arriba, moverse al otro hijo.
			if (child + 1 < actualSize
					&& compare(elements.get(child + 1), elements.get(child)) < 0) {
				child++;
			}

			// Se termina la búsqueda si el elemento en movimiento encuentra
			// su lugar en el árbol.
			continueSwap = compare(elements.get(child), element) < 0;

			if (continueSwap) {
				// Emisión del comando de intercambio de dos elementos.
				commandsQueue.offer(new SwapCommand<T>(element,
						elements.get(child), true));

				elements.set(hole, elements.get(child));
				hole = child;
				child = (hole << 1) + 1;
				continueSwap = child < actualSize;
			}
		}

		elements.set(hole, element);
	}

	/**
	 * Intercambia un elemento hacia abajo, desde la posición indicada.
	 * <p>
	 * Asume max Heap construido.
	 * 
	 * @param index
	 *            Índice del elemento.
	 */
	protected void swapDownMaxHeap(final int index) {
		final Element<T> element = elements.get(index);
		int hole = index;
		int child = (hole << 1) + 1;
		boolean continueSwap = child < actualSize;

		while (continueSwap) {
			// Si se tiene un hijo derecho y no puede ser movido hacia
			// arriba, moverse al otro hijo.
			if (child + 1 < actualSize
					&& compare(elements.get(child + 1), elements.get(child)) > 0) {
				child++;
			}

			// Se termina la búsqueda si el elemento en movimiento encuentra
			// su lugar en el árbol.
			continueSwap = compare(elements.get(child), element) > 0;

			if (continueSwap) {
				// Emisión del comando de intercambio de dos elementos.
				commandsQueue.offer(new SwapCommand<T>(element,
						elements.get(child), true));

				elements.set(hole, elements.get(child));

				hole = child;
				child = (hole << 1) + 1;
				continueSwap = child < actualSize;
			}
		}

		elements.set(hole, element);
	}

	/**
	 * Intercambia un elemento hacia arriba, desde la posición indicada.
	 * <p>
	 * Asume min Heap construido.
	 * 
	 * @param index
	 *            Índice del elemento.
	 */
	protected void swapUpMinHeap(final int index) {
		int hole = index;
		Element<T> element = elements.get(hole);
		int next = (hole - 1) >> 1;

		while (hole > 0 && compare(element, elements.get(next)) < 0) {

			// Emisión de comando de intercambio de elementos.
			commandsQueue.offer(new SwapCommand<T>(element, elements
					.get(next), false));

			// Salvar elemento que esta siendo enviado hacia abajo
			// mientras el elemento a reemplazar sube un nivel.
			elements.set(hole, elements.get(next));
			hole = next;
			next = (hole - 1) >> 1;
		}

		elements.set(hole, element);
	}

	/**
	 * Intercambia un nuevo elemento desde las hojas, hacia arriba.
	 * <p>
	 * Asume min Heap creado.
	 * 
	 * @param element
	 *            El elemento a intercambiar.
	 */
	protected void swapUpMinHeap(final Element<T> element) {
		// Emisión de comando para inserción de nuevo elemento.
		commandsQueue.offer(new InsertCommand<T>(element, isEmpty() ? null
				: elements.get((actualSize - 1) / 2), actualSize % 2 != 0));

		elements.add(element);
		swapUpMinHeap(actualSize++);
	}

	/**
	 * Intercambia un elemento hacia arriba, desde la posición indicada.
	 * <p>
	 * Asume max Heap construido.
	 * 
	 * @param index
	 *            Índice del elemento.
	 */
	protected void swapUpMaxHeap(final int index) {
		int hole = index;
		Element<T> element = elements.get(hole);
		int next = (hole - 1) >> 1;

		while (hole > 0 && compare(element, elements.get(next)) > 0) {
			// Emisión de comando para intercambio de dos elementos.
			commandsQueue.offer(new SwapCommand<T>(element, elements
					.get(next), false));

			// Salvar elemento que esta siendo enviado hacia abajo
			// mientras el elemento a reemplazar sube un nivel.
			elements.set(hole, elements.get(next));
			hole = next;
			next = (hole - 1) >> 1;
		}

		elements.set(hole, element);
	}

	/**
	 * Intercambia un nuevo elemento desde las hojas, hacia arriba.
	 * <p>
	 * Asume max Heap creado.
	 * 
	 * @param element
	 *            El elemento a intercambiar.
	 */
	protected void swapUpMaxHeap(final Element<T> element) {
		// Emisión de comando para inserción de nuevo elemento.
		commandsQueue.offer(new InsertCommand<T>(element, isEmpty() ? null
				: elements.get((actualSize - 1) / 2), actualSize % 2 != 0));

		elements.add(element);
		this.swapUpMaxHeap(actualSize++);
	}

	/**
	 * Compara dos elementos.
	 * 
	 * @param e1
	 *            El primer elemento.
	 * @param e2
	 *            El segundo elemento.
	 * @return -1 si e1 menor a e2, 0 si son iguales, +1 si mayor a e2.
	 */
	private int compare(Element<T> e1, Element<T> e2) {
		T value1 = e1.getValue();
		T value2 = e2.getValue();

		return value1.compareTo(value2);
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
	 * Devuelve un iterador de los elementos en el Heap.
	 * 
	 * @return Un iterador por sobre los elementos del Heap.
	 */
	public Iterator<T> iterator() {
		return new Iterator<T>() {

			private int index = 0;
			private int lastReturnedIndex = -1;

			public boolean hasNext() {
				return index <= actualSize - 1;
			}

			public T next() {
				if (!hasNext()){
					throw new NoSuchElementException();
				}
				lastReturnedIndex = index;
				index++;
				return elements.get(lastReturnedIndex).getValue();
			}

			public void remove() {
				if (lastReturnedIndex == -1) {
					throw new IllegalStateException();
				}
				elements.set(lastReturnedIndex, elements.get(actualSize));
				elements.set(actualSize, null);
				actualSize--;
				if (actualSize != 0 && lastReturnedIndex <= actualSize) {
					int compareToParent = 0;
					if (lastReturnedIndex > 0) {
						compareToParent = compare(elements
								.get(lastReturnedIndex), elements
								.get((lastReturnedIndex - 1) / 2));
					}
					if (isMinHeap) {
						if (lastReturnedIndex > 0 && compareToParent < 0) {
							swapUpMinHeap(lastReturnedIndex);
						} else {
							swapDownMinHeap(lastReturnedIndex);
						}
					} else { // max heap
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
	 * @param element
	 *            El elemento a insertar
	 * @return true, siempre
	 */
	public boolean add(T value) {
		this.insert(value);
		return true;
	}

	/**
	 * Devuelve el elemento tope. Igual a peek().
	 * 
	 * @return El elemento prioritario en la cola.
	 */
	public T get() {
		return this.peek();
	}

	/**
	 * Remueve el elemento tope. Igual a pop().
	 * 
	 * @return El elemento removido.
	 */
	public T remove() {
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

	/**
	 * Devuelve una copia de la cola de comandos del último evento de inserción
	 * o eliminación, y la limpia.
	 * 
	 * @return Una copia de la cola de eventos actual.
	 */
	public Queue<Command> getCommandQueue() {
		Queue<Command> currentCommands = new LinkedList<Command>(commandsQueue);
		commandsQueue.clear();

		return currentCommands;
	}
	
	/**
	 * Devuelve una copia de de la lista de elementos
	 * del Heap, conteniendo ids internos y los valores almacenados.
	 * 
	 * @return Una copia de la lista de elementos.
	 */
	public List<Element<T>> getElements() {
		return new ArrayList<Element<T>>(elements);
	}
	
	
}