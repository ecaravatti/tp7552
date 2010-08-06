/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.command.common;

import java.awt.Color;

import view.shape.Text;

/**
 *
 * 
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
