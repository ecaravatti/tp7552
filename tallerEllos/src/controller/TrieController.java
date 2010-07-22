package controller;

import java.awt.Dimension;
import java.awt.Toolkit;

import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import model.collection.trie.Trie;
import view.collection.trie.MainPanel;
import view.collection.trie.TrieGenerator;
import view.collection.trie.TrieMessages;
import view.command.common.ShowMessageDialogCommand;
import view.common.InteractivePanel;

/**
 * El controlador del Trie.
 * 
 * @author Agustina
 */
public class TrieController extends InteractiveController {
  private Trie<String> trie;
  private TrieGenerator generator;
  private MainPanel mainPanel;
  private boolean running;
  private int maxlengthHor;
  private int maxlengthVert;
  private int countLengthHor;
  private int countLengthVertical;

  
  /**
   * Construye un TrieController.
   * @param trie un trie
   * @param mainPanel el panel principal de la vista
   * @param operationsLog area de texto donde debe mostrarse los mensajes de las
   *        operaciones
   */
  public TrieController(Trie<String> trie, MainPanel mainPanel, JTextArea operationsLog) {
    super(mainPanel.getTrieView(), operationsLog);
    this.trie = trie;
    this.mainPanel = mainPanel;
    calculateMax();
    trie.addListener(mainPanel.getTrieView());
    this.countLengthHor = 0;
    this.countLengthVertical = 0;
    this.running = false;
    this.generator = new TrieGenerator(this);
    getInteractivePanel().setValueSlider(mainPanel.getTrieView().getDelayValue());
    //this.getInteractivePanel().setMaximumSlider( 190 );
  }

  /**
   * Inserta una palabra
   * @param word palabra a insertar
   * @return true si puede insertarse la palabra, false en caso contrario
   */
  public boolean insertWord(String word) {    
    int newCountHorizontal = countLengthHor;
    int newCountVertical = countLengthVertical;
    
    if ( trie.search(word) == null){
      newCountHorizontal++;
      newCountVertical = word.length() + 1; 
    }
    
    if ( (newCountHorizontal > maxlengthHor) || (newCountVertical > maxlengthVert)){
      new ShowMessageDialogCommand( TrieMessages.getInstance().getMessageFullTrie(word),
          "Error", JOptionPane.ERROR_MESSAGE).execute();
      this.primitiveFinished(); 
      return false;
    }
    else{
      countLengthHor = newCountHorizontal;
      countLengthVertical = Math.max(countLengthVertical, newCountVertical);
      showLogMessage("** Insertando palabra: " + word.toString() );
      //Logger.getLogger("Log").log(Level.INFO, "Horizontal " + countLengthHor);
      //Logger.getLogger("Log").log(Level.INFO, "Vertical " + countLengthVertical);
      this.setRunning( true );
      mainPanel.getMainButtonPanel().setEnabledButtons( false );

      
      mainPanel.getTrieView().addWord(word);
      trie.insert(word, word);
      
     
      return true;
    }
  }
  
  /**
   * Elimina un a palabra
   * @param word palabra a eliminar
   */
  public void removeWord(String word) {
    showLogMessage("** Eliminando palabra: " + word.toString() );
    mainPanel.getTrieView().removeWord(word);
    trie.remove(word);
  }

  @Override
  public void addInteractivePanel(InteractivePanel panel){
    super.addInteractivePanel( panel );
    getInteractivePanel().addInteractiveController(this);
    getInteractivePanel().setValueSlider(mainPanel.getTrieView().getDelayValue());
  }
  
  @Override
  public void primitiveFinished() {
    this.mainPanel.getTrieView().showStatistic();
    this.countLengthVertical = this.mainPanel.getTrieView().countNodesVertical();
    this.countLengthHor -= this.mainPanel.getTrieView().countNodesHorizontal();
    mainPanel.getMainButtonPanel().setEnabledButtons(true);
    mainPanel.getTrieView().restore();
    this.setRunning(false);
    //Logger.getLogger("Log").log(Level.INFO, "********************************************************");
    //Logger.getLogger("Log").log(Level.INFO, "Primitive finished..");
    //Logger.getLogger("Log").log(Level.INFO, "Horizontal " + countLengthHor);
    //Logger.getLogger("Log").log(Level.INFO, "Vertical " + countLengthVertical);
  }
  
  /**
   * Determina si hay alguna primitiva corriendo.
   * @return true si esta corriendo alguna primitiva, false en caso contrario.
   */
  public synchronized boolean running(){
    return running;
  }
  
  /**
   * Indica al contralador si debe comenzar a ejecutar (o no)
   * @param b
   */
  public synchronized void setRunning(boolean b){
    this.running = b;
  }

  /**
   * Genera una palabra para insercion
   */
  public void generateWords(){
    generator.insert();
  }

  /**
   * Limpia todos las vistas
   */
  public void clear() {
    this.trie.clear();
    this.mainPanel.getTrieView().clear();
    this.countLengthHor = 0;
    this.countLengthVertical = 0;
    this.mainPanel.getTrieView().repaint();
  }
  
  private void calculateMax(){
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    maxlengthHor = (dim.width*13)/800;
    maxlengthVert = (dim.height*6)/600;
  }
}
