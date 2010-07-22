package view.command.common;

import view.common.AnimatedPanel;
import view.element.common.ElementView;

/**
 *
 * 
 */
public class MakeVisibleCommand implements Command {
    private AnimatedPanel component;
    private ElementView element;

    public MakeVisibleCommand(AnimatedPanel component, ElementView element) {
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
