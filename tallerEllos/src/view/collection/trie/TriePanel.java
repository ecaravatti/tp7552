package view.collection.trie;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;

import view.common.InteractivePanel;
import view.common.InteractivelyControlled;
import controller.TrieController;

/**
 * 
 * 
 */
public class TriePanel extends javax.swing.JPanel implements InteractivelyControlled {
	private static final long serialVersionUID = 7509567922969971430L;
	
	private TrieView trieView;
	private TrieController controller;

	/**
	 * Creates new form TriePanel
	 * 
	 * @param trieView
	 *            una vista del trie.
	 */
	public TriePanel(TrieView trieView) {
		this.trieView = trieView;
		initComponents();
	}

	/**
	 * Agrega un controlador
	 * 
	 * @param controller
	 *            controlador a agregar
	 */
	public void addController(TrieController controller) {
		this.controller = controller;
		this.trieView.addController(controller);
		this.trieButtonsPanel.addController(controller);
	}

	/**
	 * Obtiene el trieView
	 * 
	 * @return el trie.
	 */
	public TrieView getTrieView() {
		return this.trieView;
	}

	/**
	 * Obtiene el panel principal que contiene los botones
	 * 
	 * @return el panel principal que contiene los botones
	 */
	public TrieButtonsPanel getMainButtonPanel() {
		return this.trieButtonsPanel;
	}

	/**
	 * Agrega el panel interactivo
	 * 
	 * @param interactivePanel
	 *            el panel interactivo
	 */
	public void addInteractivePanel(InteractivePanel interactivePanel) {
		this.add(interactivePanel, BorderLayout.PAGE_END);
	}

	/**
	 * Obtiene el contralador
	 * 
	 * @return el controlador
	 */
	public TrieController getController() {
		return controller;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc="Generated
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		trieButtonsPanel = new TrieButtonsPanel();
		//setBackground(new java.awt.Color(255, 255, 255));
		setLayout(new BorderLayout(5, 5));
		add(trieButtonsPanel, BorderLayout.PAGE_START);
		add(new JScrollPane(trieView), BorderLayout.CENTER);
	}// </editor-fold>//GEN-END:initComponents
	

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private TrieButtonsPanel trieButtonsPanel;
	// End of variables declaration//GEN-END:variables

}
