package view.command.tree;

import java.awt.Color;

import view.command.common.Command;
import view.common.AnimatedPanel;
import view.element.common.AbstractElementView;

/**
 *
 * 
 */
public class ChangeSelectableColorCommand implements Command {
	private AnimatedPanel component;
	private AbstractElementView element;
	private Color color;

	public ChangeSelectableColorCommand(AnimatedPanel component,
			AbstractElementView element, Color color) {
		this.component = component;
		this.element = element;
		this.color = color;
	}

	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	public void execute() {
		element.changeSelectionColor(color);
		component.repaint();
	}

}
