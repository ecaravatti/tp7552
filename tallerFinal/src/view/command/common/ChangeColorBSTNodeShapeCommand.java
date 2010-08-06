package view.command.common;

import java.awt.Color;

import view.collection.tree.BSTNodeShape;

/**
 *
 * 
 */
public class ChangeColorBSTNodeShapeCommand implements Command {

	private BSTNodeShape node;
	private Color color;

	public ChangeColorBSTNodeShapeCommand(BSTNodeShape node, Color color) {
		this.node = node;
		this.color = color;
	}

	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	public void execute() {
		node.setSelectionColor(color);
	}
}
