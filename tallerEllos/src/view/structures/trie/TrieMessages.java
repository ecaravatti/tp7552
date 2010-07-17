package view.structures.trie;

/**
 * 
 * @author Agustina
 */
public class TrieMessages {
  private static TrieMessages instance = null;

  /**
   * Obtiene la instancia.
   * @return la instancia
   */
  static public TrieMessages getInstance() {
    if (instance == null)
      instance = new TrieMessages();
    return instance;
  }

  public String getMessageFullTrie(String word) {
    return Messages.FULL_TRIE1.getMessage() + getFormattedData(word)
            + Messages.FULL_TRIE2.getMessage();
  }

  public String getMessageTrieNodeAdded(String dataNode) {
    return Messages.TRIE_NODE_ADDED.getMessage() + getFormattedData(dataNode);
  }

  public String getMessageUndoTrieNodeAdded(String dataNode) {
    return Messages.UNDO_TRIE_NODE_ADDED.getMessage()
        + getFormattedData(dataNode);
  }

  public String getMessageDataTrieNodeAdded(String dataNode) {
    return Messages.DATA_TRIE_NODE_ADDED.getMessage()
        + getFormattedData(dataNode.toUpperCase());
  }

  public String getMessageSearchTrieNode(String dataNode) {
    return Messages.SEARCH_TRIE_NODE.getMessage() + getFormattedData(dataNode);
  }

  public String getMessageTrieNodeFound() {
    return Messages.TRIE_NODE_FOUND.getMessage();
  }

  public String getMessageUndoTrieNodeFound(String dataNode) {
    return Messages.UNDO_TRIE_NODE_FOUND.getMessage();
  }

  public String getMessageTrieNodeNotFound() {
    return Messages.TRIE_NODE_NOT_FOUND.getMessage();
  }

  public String getMessageCannotRemoveNode(String data) {
    return Messages.CANNOT_REMOVE_TRIE_NODE.getMessage()
        + getFormattedData(data)
        + Messages.CANNOT_REMOVE_TRIE_NODE_1.getMessage();
  }

  public String getMessageUndoCannotRemoveNode(String data) {
    return Messages.UNDO_CANNOT_REMOVE_TRIE_NODE.getMessage()
        + getFormattedData(data);
  }

  public String getMessageDataTrieNodeFound() {
    return Messages.DATA_TRIE_NODE_FOUND.getMessage();
  }

  public String getMessageUndoDataTrieNodeFound() {
    return Messages.UNDO_DATA_TRIE_NODE_FOUND.getMessage();
  }

  public String getMessageTrieNodeRemoved(String data) {
    return Messages.TRIE_NODE_REMOVED.getMessage() + getFormattedData(data);
  }

  public String getMessageUndoTrieNodeRemoved(String data) {
    return Messages.UNDO_TRIE_NODE_REMOVED.getMessage()
        + getFormattedData(data);
  }

  public String getMessageNextStep() {
    return Messages.NEXT_STEP.getMessage();
  }

  public String getMessageCannotInsertWord(String data) {
    return Messages.CANNOT_INSERT_WORD.getMessage() + getFormattedData(data)
        + Messages.CANNOT_INSERT_WORD_1.getMessage();
  }

  public String getMessageCannotRemoveWord(String data) {
    return Messages.CANNOT_REMOVE_WORD.getMessage() + getFormattedData(data)
        + Messages.CANNOT_REMOVE_WORD_1.getMessage();
  }
  
  private String getFormattedData(String data) {
    return "' " + data + "' ";
  }

  private enum Messages {
    FULL_TRIE1("No es posible insertar "),
    FULL_TRIE2("la palabra es demasiado larga o no es posible visualizarla"),
    TRIE_NODE_FOUND("Nodo encontrado..."), 
    UNDO_TRIE_NODE_FOUND("Deshaciendo busqueda del nodo "), 
    DATA_TRIE_NODE_FOUND("La palabra ha eliminar ha sido encontrada..."), 
    UNDO_DATA_TRIE_NODE_FOUND("Deshaciendo busqueda de la palabra "), 
    TRIE_NODE_NOT_FOUND("Comparando letra a insertar con letra en el nodo...Nodo NO encontrado..."), 
    TRIE_NODE_ADDED("Insertando Nodo..."), 
    UNDO_TRIE_NODE_ADDED("Deshaciendo insercion del Nodo..."), 
    SEARCH_TRIE_NODE("Buscando nodo "), 
    DATA_TRIE_NODE_ADDED("Insertando Dato..."), 
    CANNOT_REMOVE_TRIE_NODE("No se puede eliminar el nodo "), 
    CANNOT_REMOVE_TRIE_NODE_1(", ya que forma parte de otra palabra"), 
    UNDO_CANNOT_REMOVE_TRIE_NODE("Deshaciendo eliminar nodo "), 
    TRIE_NODE_REMOVED("Eliminando Nodo "), 
    UNDO_TRIE_NODE_REMOVED("Eliminando Nodo "), 
    NEXT_STEP("Presione siguiente para continuar..."), 
    CANNOT_INSERT_WORD("No se puede insertar la palabra "), 
    CANNOT_INSERT_WORD_1(", esta palabra ya existe en el trie "),
    CANNOT_REMOVE_WORD("No se puede eliminar la palabra "), 
    CANNOT_REMOVE_WORD_1(", esta palabra NO existe en el trie ");

    private String message;

    private Messages(String message) {
      this.message = message;
    }

    public String getMessage() {
      return this.message;
    }

    public String getMessage(String aditionalInfo) {
      return this.message + aditionalInfo;
    }
  }

}
