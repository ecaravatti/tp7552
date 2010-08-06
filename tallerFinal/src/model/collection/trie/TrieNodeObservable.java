package model.collection.trie;

import event.ObservableBase;
import event.trie.TrieNodeEvent;
import event.trie.TrieNodeListener;

/**
 * 
 * 
 * @param <Data> Tipo de dato almacenado en el nodo
 */
public class TrieNodeObservable<Data> extends ObservableBase<TrieNodeListener<Data> > {

  public TrieNodeObservable() {
    super();
  }

  /**
   * Notifica que el modo de seleccion ha cambiado a found.
   * @param source objeto que produjo el evento
   */
  protected void fireSelectionModeChangedFound(TrieNode<Data> source) {
    for (TrieNodeListener<Data> list : getListeners())
      list.selectionModeChangedFound(new TrieNodeEvent<Data>(source,
          TrieNodeEvent.SelectionMode.FOUND));
  }

  /**
   * Notifica que el modo de seleccion ha cambiado a "NotFound".
   * @param source objeto que produjo el evento
   */
  protected void fireSelectionModeChangedNotFound(TrieNode<Data> source) {
    for (TrieNodeListener<Data> list : getListeners())
      list.selectionModeChangedNotFound(new TrieNodeEvent<Data>(source,
          TrieNodeEvent.SelectionMode.NOT_FOUND));
  }

  /**
   * Notifica que se ha anadido el primer hijo al nodo que disparo el evento
   * @param source objeto que produjo el evento
   */
  protected void fireFirstChildAdded(TrieNode<Data> source) {
    for (TrieNodeListener<Data> list : getListeners())
      list.firstChildAdded(new TrieNodeEvent<Data>(source));
  }

  /**
   * Notifica que se ha anadido el primer hermano al nodo que disparo el evento
   * @param source objeto que produjo el evento
   */
  protected void fireFirstSiblingAdded(TrieNode<Data> source) {
    for (TrieNodeListener<Data> list : getListeners())
      list.firstSiblingAdded(new TrieNodeEvent<Data>(source));
  }

  /**
   * Notifica que en el nodo que disparo el evento finaliza (o no) una palabra
   * @param source objeto que produjo el evento
   */
  protected void fireNodeIsWord(TrieNode<Data> source) {
    for (TrieNodeListener<Data> list : getListeners())
      list.valueIsWordChanged(new TrieNodeEvent<Data>(source));
  }

  /**
   * Notifica que se elimino un hijo del nodo que disparo el evento.
   * @param source objeto que produjo el evento
   */
  protected void fireFirstChildRemoved(TrieNode<Data> source) {
    for (TrieNodeListener<Data> list : getListeners())
      list.firstChildRemoved(new TrieNodeEvent<Data>(source));
  }

  /**
   * Notifica que se elimino un hermano del nodo que disparo el evento
   * @param source objeto que produjo el evento
   */
  protected void fireFirstSiblingRemoved(TrieNode<Data> source) {
    for (TrieNodeListener<Data> list : getListeners())
      list.firstSiblingRemoved(new TrieNodeEvent<Data>(source));
  }
}
