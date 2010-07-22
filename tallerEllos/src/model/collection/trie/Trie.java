package model.collection.trie;



/**
 * Esta clase representa un trie.
 * 
 * @author Agustina
 *
 * @param <Data> Tipo de dato almacenado en los nodos del trie.
 */
public class Trie<Data> extends TrieObservable<Data> {
  private TrieNode<Data> head;

  /**
   * Construye un trie
   */
  public Trie() {
    this.head = null;
  }

  /**
   * Obtiene la raiz del trie
   * @return la raiz del trie
   */
  public TrieNode<Data> getRoot() {
    return head;
  }

  /**
   * Inserta un termino en el Trie.
   * @param word termino a agregar al Trie.
   * @param data dato asociado al termino.
   */
  public void insert(String word, Data data) {
    TrieNode<Data> node;
    
    if (head == null || head.getCharacter() > word.charAt(0)) {
      TrieNode<Data> tmp = head; 
      head = new TrieNode<Data>(word.charAt(0), null, null);
      head.setSibling( tmp );
      this.fireRootAdded(this, head);
      node = head;
    } else {
      node = head.addSibling(word.charAt(0));
    }
    
    this.recursiveInsert(node, word, 1, data);
    this.firePrimitiveFinished(this);
  }
  
  /**
   * Busca un termino en el trie.
   * @param word termino a buscar
   * @return dato asociado al termino buscado, en caso de no encontra el termino
   *         retorna null
   */
  public Data search(String word){
    
    if (head == null)
      return null;
    

    TrieNode<Data> node = new TrieNode<Data>('\0', null, head);
    return recursiveSearch(word, 0, node);

    
  }

  /**
   * Busca un termino en el trie.
   * @param word termino a buscar
   * @return dato asociado al termino buscado, en caso de no encontra el termino
   *         retorna null
   */
  private Data recursiveSearch(String word, int index, TrieNode<Data> node) {
    TrieNode<Data> child;
    boolean found;
    
    if ( index >= word.length() ){
      if (node.isWord())
        return node.getData();
      return null;
    }

    found = false;
    child = node.getChild();
    char ch = word.charAt(index);
    
    while ( child != null && child.getCharacter() <= ch && !found ) {
      if ( child.getCharacter() == ch ){ 
        found = true;
        return recursiveSearch(word, ++index, child);
      }
      else
        child = child.getSibling();
    }
    
    return null;
  }
  
  /**
   * Elimina un termino del trie
   * @param word palabra a eliminar del trie
   * @return true si la palabra fue eliminada del trie, false en caso contrario.
   */
  public boolean remove(String word) {

    if (head == null){
      this.fireCannotRemoveWord(this);
      this.firePrimitiveFinished(this);
      return false;
    }

    TrieNode<Data> node = new TrieNode<Data>('\0', null, head);
    boolean ret = recursiveRemove(word, 0, node);
    TrieNode<Data> newRoot = node.getChild();
    
    if (newRoot == null || newRoot != head )
      this.fireRootRemoved(this, newRoot);

    head = newRoot;
   
    if (!ret) this.fireCannotRemoveWord(this);
    this.firePrimitiveFinished(this);
    return ret;
  }

  /**
   * Vacia el trie.
   */
  public void clear(){
    this.head = null;
  }
  
  /**
   * Metodo recursivo para insertar un nodo
   */
  private void recursiveInsert(TrieNode<Data> parent, String word, int index,
      Data data) {

    if (index >= word.length()) {
      if (!parent.isWord())
        parent.setIsWord(data);
      else
        fireCannotInsertWord(this);
      return;
    }

    parent = parent.addChild( word.charAt(index) );
  
    recursiveInsert(parent, word, ++index, data);
  }

  /**
   * Eliminacion recursiva sobre el trie
   */
  private boolean recursiveRemove(String word, int index, TrieNode<Data> node) {
    boolean chFound = true;
    TrieNode<Data> child;

    if (index >= word.length())
      return true;

    child = node.getChild(word.charAt(index));
    if (child == null)
      return false;

    chFound = chFound && recursiveRemove(word, index + 1, child);

    if (!chFound || (index == word.length() - 1 && !child.isWord())) {
      return false;
    }

    if (index == word.length() - 1) {
      child.setIsWord(false);
    }

    if (!child.hasChild() && !child.isWord()) {
      TrieNode<Data> left = node.getChild().getLeftSibling(child);

      if (left == null) {
        if (node.getChild() != head)
          node.removeChild();
        else
          node.setChild( head.getSibling() );
      } else
        left.removeSibling();
    } else
      this.fireCannotRemoveTrieNode(this, child);

    return chFound;
  }
}
