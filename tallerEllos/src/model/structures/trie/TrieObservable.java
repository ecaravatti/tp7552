package model.structures.trie;

import events.ObservableBase;
import events.trie.TrieEvent;
import events.trie.TrieListener;

/**
 * @author Agustina
 * 
 * @param <Data> Tipo de dato almacenado en el nodo
 */
public class TrieObservable<Data> extends ObservableBase< TrieListener<Data> > {

  public TrieObservable() {
    super();
  }

  /**
   * Notifica que se ha agregado la raiz al trie
   * @param trie trie al que se le agrego la raiz
   * @param root la raiz
   */
  protected void fireRootAdded(Trie<Data> trie, TrieNode<Data> root) {
    for (TrieListener<Data> list : getListeners())
      list.rootAdded(new TrieEvent<Data>(trie, root));
  }

  /**
   * Notifica que no se puede eliminar un nodo del trie
   * @param trie trie al que se quiere eliminar un nodo
   * @param current nodo actualmente en proceso
   */
  protected void fireCannotRemoveTrieNode(Trie<Data> trie,
      TrieNode<Data> current) {
    for (TrieListener<Data> list : getListeners())
      list.cannotRemoveTrieNode(new TrieEvent<Data>(trie, current));
  }

  /**
   * Notifica que la raiz fue eliminada
   * @param trie el trie cuya raiz fue eliminada
   * @param root el nodo eliminado
   */
  protected void fireRootRemoved(Trie<Data> trie, TrieNode<Data> root) {
    for (TrieListener<Data> list : getListeners())
      list.rootRemoved(new TrieEvent<Data>(trie, root));
  }

  /**
   * Notifica que no se puede insertar una palabra
   * @param trie en el que se quiere inserta la palabra
   */
  protected void fireCannotInsertWord(Trie<Data> trie) {
    for (TrieListener<Data> list : getListeners())
      list.cannotInsertWord(new TrieEvent<Data>(trie));
  }

  /**
   * Notifica que no se puede eliminar una palabra
   * @param trie en el que se quiere eliminar la palabra
   */
  protected void fireCannotRemoveWord(Trie<Data> trie) {
    for (TrieListener<Data> list : getListeners())
      list.cannotRemoveWord(new TrieEvent<Data>(trie));
  }
  
  // TODO: metodo temporal
  protected void firePrimitiveFinished(Trie<Data> trie) {
    for (TrieListener<Data> list : getListeners())
      list.primitiveFinished(new TrieEvent<Data>(trie));
  }
}
