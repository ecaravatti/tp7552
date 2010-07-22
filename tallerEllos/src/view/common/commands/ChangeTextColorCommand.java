/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.commons.commands;

import java.awt.Color;
import view.commons.shapes.Text;

/**
 *
 * @author Duilio
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
