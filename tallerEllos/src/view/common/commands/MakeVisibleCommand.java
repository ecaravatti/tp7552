package view.commons.commands;

import view.commons.PanelAnimated;
import view.commons.elements.ElementView;

/**
 *
 * @author Exe Curia
 */
public class MakeVisibleCommand implements Command {
    private PanelAnimated component;
    private ElementView element;

    public MakeVisibleCommand(PanelAnimated component, ElementView element) {
        this.component = component;
        this.element = element;
    }

    @Override
    public boolean canExecute() {
        return true;
    }

    @Override
    public void execute() {
        element.setVisible(true);
        component.repaint();
    }
}
