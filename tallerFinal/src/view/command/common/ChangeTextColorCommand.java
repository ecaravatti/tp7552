package view.command.common;

import java.awt.Color;

import view.shape.Text;

/**
 * Comando para cambiar el color del texto.
 */
public class ChangeTextColorCommand implements Command {

	private Text text;
	private Color color;

	public ChangeTextColorCommand(Text text, Color color) {
		this.text = text;
		this.color = color;
	}

	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	public void execute() {
		text.setColor(color);
	}
}
