package view.command.common;

import java.awt.Color;

import view.shape.NodeShape;

/**
 * Este comando debe ejecutarse cuando se desea cambiar el color de un nodo.
 * 
 * 
 */
public class ChangeColorNodeShapeCommand implements Command {
  private NodeShape node;
  private Color color;

  /**
   * Construye un comando que permite cambiar el color de un nodo
   * @param node figura que representa al nodo al cual debe cambiar de color
   * @param color color que tendra el nodo.
   */
  public ChangeColorNodeShapeCommand(NodeShape node, Color color) {
    super();
    this.node = node;
    this.color = color;
  }

  @Override
  public boolean canExecute() {
    return true;
  }

  @Override
  public void execute() {
    node.setNodeColor(color);
    node.setLineColor(color);
  }

}
