package event.trie;

import java.util.EventObject;

import model.collection.trie.TrieNode;

/**
 * Representa un evento que se origina en un nodo del trie.
 * 
 * 
 * 
 * @param <Data> Tipo de dato almacenado en los nodos del trie.
 */
public class TrieNodeEvent<Data> extends EventObject {
  private static final long serialVersionUID = 1L;

  private TrieNode<Data> trieNode;
  private SelectionMode nextMode;

  public enum SelectionMode {
    NONE, FOUND, NOT_FOUND
  };

  /**
   * Construye un TrieNodeEvent
   * @param source nodo en que se origino el evento
   * @param nextMode siguiente modo de seleccion
   */
  public TrieNodeEvent(TrieNode<Data> source, SelectionMode nextMode) {
    super(source);
    this.nextMode = nextMode;
    this.trieNode = source;
  }

  /**
   * Construye un TrieNodeEvent. El proximo modo de seleccion es NONE.
   * @param source nodo en que se origino el evento
   */
  public TrieNodeEvent(TrieNode<Data> source) {
    super(source);
    this.nextMode = SelectionMode.NONE;
    this.trieNode = source;
  }

  /**
   * Obtiene el siguiente modo de seleccion.
   * @return el siguiente modo de seleccion.
   */
  public SelectionMode getNextSelectionMode() {
    return nextMode;
  }

  @Override
  public TrieNode<Data> getSource() {
    return trieNode;
  }
}
