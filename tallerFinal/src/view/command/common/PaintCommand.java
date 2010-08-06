package view.command.common;

import javax.swing.JComponent;

/**
 * Este comando debe ejecutarse cuando se desea re-pintar el trie
 */
public class PaintCommand implements Command {
	private JComponent component;

	/**
	 * 
	 * @param component
	 *            el trie a re-pintar.
	 */
	public PaintCommand(JComponent component) {
		super();
		this.component = component;
	}

	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	public void execute() {
		component.repaint();
	}
}
