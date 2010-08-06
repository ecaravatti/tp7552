package view.common;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import dataStructure.DataStructureApplet;


/**
 * Panel que logea las operaciones realizadas sobre una estructura.
 * 
 * @author estefania
 */
public class OperationsLogPane extends JScrollPane {

	private static final long serialVersionUID = 1L;
	private static final String PANE_TITLE = "Log de Operaciones";

	private JTextArea logTextArea;

	public OperationsLogPane() {
		logTextArea = new JTextArea();
		logTextArea.setBorder(BorderFactory.createTitledBorder(PANE_TITLE));
		logTextArea.setEditable(Boolean.FALSE);
		logTextArea.setFont(DataStructureApplet.DEF_FONT);

		this.setViewportView(logTextArea);
	}

	/**
	 * Logs a message into this pane.
	 * Area
	 * @param message
	 *            The message to log.
	 */
	public void logMessage(String message) {
		logTextArea.append("\n" + message);
		logTextArea.setCaretPosition(logTextArea.getText().length());
	}
}
