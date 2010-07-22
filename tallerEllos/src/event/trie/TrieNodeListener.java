package event.trie;

import java.util.EventListener;

/**
 * La interfaz del Listener para recibir eventos generados por un nodo Trie.
 * 
 * 
 * 
 * @param <Data> El tipo de dato almacenado en los nodos del Trie.
 */
public interface TrieNodeListener<Data> extends EventListener {

  /**
   * Invocado cuando se cambia el modo de seleccion sobre un nodo a "FOUND"
   * @param event un evento
   */
  public void selectionModeChangedFound(TrieNodeEvent<Data> event);

  /**
   * Invocado cuando se cambia el modo de seleccion sobre un nodo a "NOT FOUND"
   * @param event un evento
   */
  public void selectionModeChangedNotFound(TrieNodeEvent<Data> event);

  /**
   * Invocado cuando se agrega un hijo
   * @param event un evento
   */
  public void firstChildAdded(TrieNodeEvent<Data> event);

  /**
   * Invocado cuando se agrega un hermano
   * @param event un evento
   */
  public void firstSiblingAdded(TrieNodeEvent<Data> event);

  /**
   * Invocado cuando se cambia el valor "isWord"
   * @param event un evento
   */
  public void valueIsWordChanged(TrieNodeEvent<Data> event);

  /**
   * Invocado cuando se elimina el primer hijo
   * @param event un evento
   */
  public void firstChildRemoved(TrieNodeEvent<Data> event);

  /**
   * Invocado cuando se elimina un hermano
   * @param event un evento
   */
  public void firstSiblingRemoved(TrieNodeEvent<Data> event);
}
