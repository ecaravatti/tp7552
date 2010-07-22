package view.command.common;

import view.command.common.Command;

import javax.swing.JComponent;

/**
 * Este comando debe ejecutarse cuando se desea re-pintar el trie
 * @author Agustina Freije
 */
public class PaintCommand implements Command {
  private JComponent component;

  /**
   * 
   * @param component el trie a re-pintar.
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
