package model.collection.trie;

/**
 * Esta clase representa a un nodo de un trie.
 * 
 * 
 * 
 * @param <Data>
 *            Tipo de dato almacenado en el nodo
 */
public class TrieNode<Data> extends TrieNodeObservable<Data> {
	private Data data;
	private boolean endWord;
	private char character;
	private TrieNode<Data> sibling;
	private TrieNode<Data> child;

	/**
	 * Construye un nodo que contiene un caracter de un termino.
	 * 
	 * @param character
	 *            caracter que contiene el nodo
	 * @param sibling
	 *            hermano del nodo
	 * @param child
	 *            hijo del nodo
	 */
	public TrieNode(char character, TrieNode<Data> sibling, TrieNode<Data> child) {
		super();
		this.endWord = false;
		this.character = character;
		this.sibling = sibling;
		this.child = child;
		this.data = null;
	}

	/**
	 * Obtiene el caracter almacenado en el nodo
	 * 
	 * @return el caracter
	 */
	public char getCharacter() {
		return this.character;
	}

	/**
	 * Cambia el hermano del hermano
	 * 
	 * @param sibling
	 *            nuevo hermano
	 */
	public void setSibling(TrieNode<Data> sibling) {
		this.sibling = sibling;
	}

	/**
	 * Elimina el hermano del nodo. Asignandole al nodo los hermanos del nodo
	 * eliminado
	 */
	public void removeSibling() {

		if (this.getSibling() != null) {
			this.sibling = this.getSibling().getSibling();
			this.fireFirstSiblingRemoved(this);
		}

	}

	/**
	 * Obtiene el hermano del nodo
	 * 
	 * @return el hermano
	 */
	public TrieNode<Data> getSibling() {
		return this.sibling;
	}

	/**
	 * Determina si el nodo tiene hermanos
	 * 
	 * @return true si tiene hermanos, false en caso contrario.
	 */
	public boolean hasSibling() {
		return (getSibling() != null);
	}

	/**
	 * Obtiene el hermano del nodo que contiene el caracter pasado como
	 * parametro.
	 * 
	 * @param chr
	 *            caracter a buscar en uno de los hermano.
	 * @return null, si este nodo contiene el caracter o si ninguno de los
	 *         hermanos lo contiene. En caso contrario, retorna el hermano que
	 *         contiene el caracter buscado.
	 */
	public TrieNode<Data> getSibling(char chr) {

		for (TrieNode<Data> node = this; node != null; node = node.getSibling()) {
			if (node.getCharacter() == chr) {
				node.fireSelectionModeChangedFound(node);
				return node;
			} else if (node.getCharacter() > chr) {
				return null;
			} else {
				node.fireSelectionModeChangedNotFound(node);
			}
		}

		return null;
	}

	/**
	 * Agrega un hermano.
	 * 
	 * @param chr
	 *            caracter que contendra el hermano
	 * @return el nodo hermano agregado.
	 */
	public TrieNode<Data> addSibling(char chr) {
		TrieNode<Data> sibl = new TrieNode<Data>(chr, null, null);
		TrieNode<Data> left = null;
		TrieNode<Data> node;

		for (node = this; node != null && node.getCharacter() <= chr; node = node
				.getSibling()) {
			if (node.getCharacter() != chr)
				node.fireSelectionModeChangedNotFound(node);
			left = node;
		}

		if (left.getCharacter() == chr) {
			left.fireSelectionModeChangedFound(left);
			return left;
		} else if (node != null && node != this)
			node.fireSelectionModeChangedNotFound(left);

		TrieNode<Data> tmp = left.sibling;
		left.setSibling(sibl);
		sibl.setSibling(tmp);
		left.fireFirstSiblingAdded(left);
		return sibl;
	}

	/**
	 * Cambia el hijo del nodo
	 * 
	 * @param child
	 *            nuevo hijo
	 */
	public void setChild(TrieNode<Data> child) {
		this.child = child;
	}

	/**
	 * Obtiene el hijo del nodo
	 * 
	 * @return el hijo
	 */
	public TrieNode<Data> getChild() {
		return child;
	}

	/**
	 * Determina si el nodo tiene hijos
	 * 
	 * @return true si el nodo tiene hijos, false en caso contrario.
	 */
	public boolean hasChild() {
		return this.getChild() != null;
	}

	/**
	 * @param chr
	 *            caracter que debe contener el hijo buscado.
	 * @return el hijo que contiene el dato, o null si ning�n hijo contiene al
	 *         dato
	 */
	public TrieNode<Data> getChild(char chr) {

		if (this.getChild() == null) {
			return null;
		}
		if (this.getChild().getCharacter() == chr) {
			this.getChild().fireSelectionModeChangedFound(this);
			return this.getChild();
		}
		if (this.getChild().getCharacter() > chr) {
			return null;
		}
		return getChild().getSibling(chr);

	}

	/**
	 * Agrega un hijo.
	 * 
	 * @param chr
	 *            caracter que contendra el hijo
	 * @return el hijo recien creado
	 */
	public TrieNode<Data> addChild(char chr) {

		/*
		 * if (getChild() == null) { TrieNode<Data> ch = new TrieNode<Data>(chr,
		 * null, null); this.child = ch; this.fireFirstChildAdded(this); return
		 * this.getChild(); } else if (this.child.getCharacter() > chr){
		 * this.getChild().fireSelectionModeChangedNotFound(this);
		 * TrieNode<Data> ch = new TrieNode<Data>(chr, this.child, null);
		 * this.child = ch; this.fireFirstChildAdded(this); return
		 * this.getChild(); } else if (this.child.getCharacter() == chr){
		 * this.getChild().fireSelectionModeChangedFound(this); return
		 * this.getChild(); } return this.getChild().addSibling(chr);
		 */

		if (getChild() == null) {
			TrieNode<Data> ch = new TrieNode<Data>(chr, null, null);
			this.child = ch;
			this.fireFirstChildAdded(this);
			return this.getChild();
		} else if (this.child.getCharacter() > chr) {
			this.getChild().fireSelectionModeChangedNotFound(this);
			TrieNode<Data> ch = new TrieNode<Data>(chr, this.child, null);
			this.child = ch;
			this.fireFirstChildAdded(this);
			return this.getChild();
		} else if (this.child.getCharacter() == chr) {
			this.getChild().fireSelectionModeChangedFound(this);
			return this.getChild();
		}
		/*
		 * else this.getChild().fireSelectionModeChangedNotFound(this);
		 */
		return this.getChild().addSibling(chr);
	}

	/**
	 * Elimina el hijo del nodo. Asignandole al nodo los hermanos del hijo del
	 * nodo eliminado
	 */
	public void removeChild() {
		this.setChild(getChild().getSibling());
		this.fireFirstChildRemoved(this);
	}

	/**
	 * Indica si el nodo contiene un termino.
	 * 
	 * @return true si el nodo contiene una termino, false en caso contrario.
	 */
	public boolean isWord() {
		return this.endWord;
	}

	/**
	 * Determina si en el nodo finaliza (o no) una palabra
	 * 
	 * @param word
	 *            true si en nodo finaliza una palabra, false en caso contrario.
	 */
	public void setIsWord(boolean word) {
		this.endWord = word;
		if (!word)
			this.data = null;
		this.fireNodeIsWord(this);
	}

	/**
	 * Obtiene el dato
	 * 
	 * @return el dato
	 */
	public Data getData() {
		return data;
	}

	/**
	 * Determina que en el nodo finaliza una palabra y cambia el dato asociado a
	 * la palabra
	 * 
	 * @param data
	 *            nuevo dato
	 */
	public void setIsWord(Data data) {
		this.endWord = true;
		this.data = data;
		this.fireNodeIsWord(this);
	}

	/**
	 * Busca en los hijos el hermano izquierdo del nodo pasado como parametro.
	 * 
	 * @param node
	 *            nodo hijo
	 * @return el hermano izquierdo
	 */
	public TrieNode<Data> getLeftSibling(TrieNode<Data> node) {
		TrieNode<Data> leftSib = null;

		for (TrieNode<Data> current = this; current != null;) {
			if (current.getCharacter() == node.getCharacter()) {
				break;
			} else {
				leftSib = current;
				current = current.getSibling();
			}
		}

		return leftSib;
	}
}
