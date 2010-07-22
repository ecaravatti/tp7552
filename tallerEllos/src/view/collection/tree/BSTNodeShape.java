package view.collection.tree;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Dimension2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import view.element.common.Mobile;
import view.element.common.Selectable;
import view.shape.Text;

/**
 *
 * @author Exe Curia
 */
public class BSTNodeShape implements Selectable, Mobile {

    private Ellipse2D circle;
    private Font fontData;
    private Font fontBalance;
    private Stroke stroke;
    private String data;
    private String balance;
    private Dimension2D dimension;
    private Color color;
    private Point2D position;

    private static final Color DEF_COLOR = Color.YELLOW;
    private static final Color DEF_COLOR_BORDER = Color.BLACK;
    private static final Color DEF_COLOR_BALANCE = Color.RED;
    private static final Color DEF_COLOR_SHADOW = Color.LIGHT_GRAY;
    private static final int DEF_SHADOW_WIDTH = 3;

    public BSTNodeShape(String data, String balance, int diameter, Font fontData, Font fontBalance, Stroke stroke) {
        this.data = data;
        this.balance = balance;
        this.fontData = fontData;
        this.fontBalance = fontBalance;
        this.stroke = stroke;
        this.circle = new Ellipse2D.Double(0, 0, diameter, diameter);
        this.dimension = new Dimension(diameter, diameter);
        this.color = DEF_COLOR;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    /**
     * Pinta el nodo en el contexto g
     * @param g contexto sobre el que se dibuja
     */
    public void paint(Graphics2D g2, int side) {
        // Sombra
        g2.translate(DEF_SHADOW_WIDTH, DEF_SHADOW_WIDTH);
        g2.setPaint(DEF_COLOR_SHADOW);
        g2.fill(circle);
        g2.translate(-DEF_SHADOW_WIDTH, -DEF_SHADOW_WIDTH);

        // Interior
        g2.setStroke(this.stroke);
        g2.setColor(color);
        g2.fill(circle);

        // Borde y texto
        g2.setColor(DEF_COLOR_BORDER);
        g2.draw(circle);
        Text.paintCenterString(g2, data, fontData, circle.getFrame());

        if (this.balance != null) {
            Font previous = g2.getFont();
            g2.setFont(fontBalance);
            g2.setColor(DEF_COLOR_BALANCE);
            g2.drawString(balance, (int)circle.getX() + (side == 1 ? (int)dimension.getHeight() : -fontBalance.getSize()/2), (int)circle.getY());
            g2.setFont(previous);
        }
    }

    @Override
    public void setSelectionColor(Color color) {
        if (color == null)
            restoreColor();
        else
            this.color = color;
    }

    @Override
    public void changeColor() {
/*        if (color == DEF_COLOR)
            color = DEF_SELECTION_COLOR;
        else
            color = DEF_COLOR;*/
    }

    @Override
    public void restoreColor() {
        color = DEF_COLOR;
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public void moveTo(Point2D position) {
        this.position = position;
        this.circle.setFrame(position, this.dimension);
    }

    public String getBalance() {
      return balance;
    }

}
