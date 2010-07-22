package view.structures.trees.commands;

import java.awt.Color;
import view.commons.PanelAnimated;
import view.commons.commands.Command;
import view.commons.elements.AbstractElementView;

/**
 *
 * @author Exe Curia
 */
public class ChangeSelectableColorCommand implements Command {
    private PanelAnimated component;
    private AbstractElementView element;
    private Color color;

    public ChangeSelectableColorCommand(PanelAnimated component, AbstractElementView element, Color color) {
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
