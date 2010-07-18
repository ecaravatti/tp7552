/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.commons.commands;

import java.awt.Color;
import view.structures.trees.BSTNodeShape;

/**
 *
 * @author Duilio
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