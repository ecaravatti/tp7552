package controller;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JOptionPane;

import model.collection.trie.Trie;
import view.collection.trie.TrieGenerator;
import view.collection.trie.TrieMessages;
import view.collection.trie.TriePanel;
import view.command.common.ShowMessageDialogCommand;
import view.common.InteractivePanel;

/**
 * El controlador del Trie.
 * 
 */
public class TrieController extends InteractiveController {
	private Trie<String> trie;
	private TrieGenerator generator;
	private TriePanel triePanel;
	private boolean running;
	private int maxlengthHor;
	private int maxlengthVert;
	private int countLengthHor;
	private int countLengthVertical;

	/**
	 * Construye un TrieController.
	 * 
	 * @param trie
	 *            un trie
	 * @param triePanel
	 *            el panel principal de la vista
	 * @param operationsLog
	 *            Panel donde deben loggearse los mensajes de las operaciones
	 * @param pseudoCodePane
	 *            Panel donde se muestra el pseudo código de las operaciones
	 *            realizadas
	 */
	public TrieController(Trie<String> trie, TriePanel triePanel) {
		super(triePanel.getTrieView());
		this.trie = trie;
		this.triePanel = triePanel;
		calculateMax();
		trie.addListener(triePanel.getTrieView());
		this.countLengthHor = 0;
		this.countLengthVertical = 0;
		this.running = false;
		this.generator = new TrieGenerator(this);
		getInteractivePanel().setValueSlider(
				triePanel.getTrieView().getDelayValue());
		triePanel.addController(this);
	}

	/**
	 * Inserta una palabra
	 * 
	 * @param word
	 *            palabra a insertar
	 * @return true si puede insertarse la palabra, false en caso contrario
	 */
	public boolean insertWord(String word) {
		int newCountHorizontal = countLengthHor;
		int newCountVertical = countLengthVertical;

		if (trie.search(word) == null) {
			newCountHorizontal++;
			newCountVertical = word.length() + 1;
		}

		if ((newCountHorizontal > maxlengthHor)
				|| (newCountVertical > maxlengthVert)) {
			new ShowMessageDialogCommand(TrieMessages.getInstance()
					.getMessageFullTrie(word), "Error",
					JOptionPane.ERROR_MESSAGE).execute();
			this.primitiveFinished();
			return false;
		} else {
			countLengthHor = newCountHorizontal;
			countLengthVertical = Math.max(countLengthVertical,
					newCountVertical);
			showLogMessage("** Insertando palabra: " + word.toString());
			// Logger.getLogger("Log").log(Level.INFO, "Horizontal " +
			// countLengthHor);
			// Logger.getLogger("Log").log(Level.INFO, "Vertical " +
			// countLengthVertical);
			this.setRunning(true);
			triePanel.getMainButtonPanel().setEnabledButtons(false);
			triePanel.getTrieView().addWord(word);
			trie.insert(word, word);
			return true;
		}
	}

	/**
	 * Elimina un a palabra
	 * 
	 * @param word
	 *            palabra a eliminar
	 */
	public void removeWord(String word) {
		showLogMessage("** Eliminando palabra: " + word.toString());
		if (isTrieEmpty()) {
			showLogMessage("Ningún nodo para eliminar.");
		} else {
			triePanel.getMainButtonPanel().setEnabledButtons(false);
			triePanel.getTrieView().removeWord(word);
			trie.remove(word);
		}
	}

	@Override
	public void addInteractivePanel(InteractivePanel panel) {
		super.addInteractivePanel(panel);
		getInteractivePanel().addInteractiveController(this);
		getInteractivePanel().setValueSlider(
				triePanel.getTrieView().getDelayValue());
	}

	@Override
	public void primitiveFinished() {
		this.triePanel.getTrieView().showStatistic();
		this.countLengthVertical = this.triePanel.getTrieView()
				.countNodesVertical();
		this.countLengthHor -= this.triePanel.getTrieView()
				.countNodesHorizontal();
		triePanel.getMainButtonPanel().setEnabledButtons(true);
		triePanel.getTrieView().restore();
		this.setRunning(false);
	}

	public boolean isTrieEmpty() {
		return (trie.getRoot() == null);
	}

	/**
	 * Determina si hay alguna primitiva corriendo.
	 * 
	 * @return true si esta corriendo alguna primitiva, false en caso contrario.
	 */
	public synchronized boolean running() {
		return running;
	}

	/**
	 * Indica al contralador si debe comenzar a ejecutar (o no)
	 * 
	 * @param b
	 */
	public synchronized void setRunning(boolean b) {
		this.running = b;
	}

	/**
	 * Genera una palabra para insercion
	 */
	public void generateWords() {
		generator.insert();
	}

	/**
	 * Limpia todos las vistas
	 */
	public void clear() {
		if (isTrieEmpty()) {
			showLogMessage("El trie ya se encuentra vacío.");
		} else {
			this.trie.clear();
			this.triePanel.getTrieView().clear();
			this.countLengthHor = 0;
			this.countLengthVertical = 0;
			this.triePanel.getTrieView().repaint();
		}
	}

	private void calculateMax() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		maxlengthHor = (dim.width * 13) / 800;
		maxlengthVert = (dim.height * 6) / 600;
	}
}
