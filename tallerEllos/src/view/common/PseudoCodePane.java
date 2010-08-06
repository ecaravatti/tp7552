package view.common;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import dataStructure.DataStructureApplet;


/**
 * Panel que muestra el pseudocodigo de las operaciones realizadas sobre las
 * estructuras.
 * 
 * @author estefania
 */
public class PseudoCodePane extends JScrollPane {

	private static final long serialVersionUID = -5675265468948906706L;
	private static final String PANE_TITLE = "Pseudo-Código";

	private JTextArea pseudoCodeTextArea;

	public PseudoCodePane() {
		pseudoCodeTextArea = new JTextArea();
		pseudoCodeTextArea.setBorder(BorderFactory
				.createTitledBorder(PANE_TITLE));
		pseudoCodeTextArea.setEditable(Boolean.FALSE);
		pseudoCodeTextArea.setFont(DataStructureApplet.DEF_FONT);

		setViewportView(pseudoCodeTextArea);
	}

	/**
	 * Muestra el pseudo código enviado como parámetro en el panel.
	 * 
	 * @param pseudoCode
	 *            El pseudo código a mostrar.
	 */
	public void showPseudoCode(String pseudoCode) {
		pseudoCodeTextArea.setText(pseudoCode);
		pseudoCodeTextArea.setCaretPosition(0);
	}
}
