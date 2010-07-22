package event.trie;

import java.util.EventListener;


/**
 * La interfaz del Listener para recibir eventos generados por el Trie.
 * 
 * @param <Data> El tipos de dato almacenado en los nodos del Trie.
 * 
 */
public interface TrieListener<Data> extends EventListener {

  /**
   * Invocado cuando se agrega la raiz
   * @param event el evento.
   */
  public void rootAdded(TrieEvent<Data> event);

  /**
   * Invocado cuando se elimina la raiz
   * @param event el evento
   */
  public void rootRemoved(TrieEvent<Data> event);

  /**
   * Invocado cuando no se puede eliminar un nodo.
   * @param event el evento.
   */
  public void cannotRemoveTrieNode(TrieEvent<Data> event);

  /**
   * Invocado cuando no se puede eliminar una palabra
   * @param event el evento.
   */
  public void cannotInsertWord(TrieEvent<Data> event);

  /**
   * Invocado cuando no puede eliminarse una palabra.I
   * @param event el evento
   */
  public void cannotRemoveWord(TrieEvent<Data> event);

  /**
   * Invocado cuando se finaliza la primitiva
   * @param event el evento
   */
  public void primitiveFinished(TrieEvent<Data> event);
}
