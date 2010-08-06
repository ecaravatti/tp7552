package view.element.trie;

import java.awt.Color;

/**
 * Representa a las primitivas que pueden aplicarse sobre una vista de un
 * TrieView
 * 
 * 
 */
public interface TrieViewPrimitives {

	/**
	 * Cambia la raiz.
	 * 
	 * @param root
	 *            nueva raiz
	 */
	public void setRoot(TrieNodeView root);

	/**
	 * Obtiene la raiz
	 * 
	 * @return la raiz
	 */
	public TrieNodeView getRoot();

	/**
	 * Debe invocarse cuando a un nodo se le anadio un nodo hijo
	 * 
	 * @param parent
	 *            nodo padre
	 * @param node
	 *            nodo anadido al padre
	 */
	public void trieNodeViewChildAdded(TrieNodeView parent, TrieNodeView node);

	/**
	 * Debe invocarse cuando a un nodo se le anadio un nodo hijo
	 * 
	 * @param parent
	 *            nodo padre
	 * @param node
	 *            nodo anadido al padre
	 */
	public void trieNodeViewSiblingAdded(TrieNodeView parent, TrieNodeView node);

	/**
	 * Debe invocarse cuando a un nodo se le elimino un dato.
	 * 
	 * @param parent
	 *            nodo padre
	 * @param node
	 *            nodo que se elimino del padre
	 */
	public void dataTrieNodeViewRemoved(TrieNodeView parent,
			AbstractTrieNodeView node);

	/**
	 * Debe invocarse cuando a un nodo se le agrego o elimino un dato.
	 * 
	 * @param parent
	 *            padre
	 * @param data
	 *            nodo dato.
	 */
	public void trieNodeViewIsWord(TrieNodeView parent, DataTrieNodeView data);

	/**
	 * Debe invocarse cuando un nodo ha sido encontrado
	 * 
	 * @param selected
	 *            nodo encontrado
	 */
	public void trieNodeViewFound(TrieNodeView selected);

	/**
	 * Debe invocarse cuando un nodo no ha sido encontrado
	 * 
	 * @param selected
	 *            nodo que no ha sido encontrado
	 */
	public void trieNodeViewNotFound(TrieNodeView selected);

	/**
	 * Debe invocarse cuando un nodo dato encontrado debe ser seleccionado
	 * 
	 * @param selected
	 *            nodo dato seleccionado
	 * @param selectionColor
	 *            color de seleccion
	 */
	public void dataTrieNodeViewFound(DataTrieNodeView selected,
			Color selectionColor);

	/**
	 * Debe invocarse cuando a un nodo se le elimina un hijo
	 * 
	 * @param parent
	 *            nodo al que se le elimina un hijo
	 * @param child
	 *            nodo a eliminar
	 */
	public void firstChildRemoved(TrieNodeView parent, TrieNodeView child);

	/**
	 * Debe invocarse cuando a un nodo se le elimina un hermano
	 * 
	 * @param parent
	 *            nodo al que se elimina un hermano
	 * @param sibling
	 *            nodo a eliminar
	 */
	public void firstSiblingRemoved(TrieNodeView parent, TrieNodeView sibling);

	/**
	 * Calcula la profundidad de los hermanos.
	 */
	public void calculateDepths();
}
