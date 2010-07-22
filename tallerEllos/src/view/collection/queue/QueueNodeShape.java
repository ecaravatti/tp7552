/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.collection.queue;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Point2D;

import view.shape.NodeShape;

/**
 *
 */
public class QueueNodeShape extends NodeShape {

    private final static Font DEF_FONT_NODE = new Font("SansSerif", Font.BOLD, 12);
    private final static BasicStroke DEF_STROKE = new BasicStroke(1.5f);
    private final static boolean DEF_NODE_ROUNDED = true;
    private final static int DEF_WIDTH_NODE = 50;
    private final static int DEF_HEIGTH_NODE = 50;
    private Integer index;
    private QueueNodeRoles role;

    public QueueNodeShape(String data, Point2D pos, Integer index) {
        super(data, pos, DEF_WIDTH_NODE, DEF_HEIGTH_NODE, DEF_FONT_NODE, DEF_STROKE, DEF_NODE_ROUNDED);
        this.index = index;
    }

    public void setRole(QueueNodeRoles role) {
        this.role = role;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        double horizontalRoleOffset = 0;
        double verticalRoleOffset = this.getRectNode().getHeight() * (1.4);

        g.drawString((this.role != null) ? role.toString() : "", (int) (this.getPosition().getX() + horizontalRoleOffset), (int) (this.getPosition().getY() + verticalRoleOffset));

        //index element draw.
        double horizontalIndexOffset = this.getRectNode().getWidth() * (1.4);
        double verticalIndexOffset = 0;
        g.drawString((index != null) ? index.toString() : "", (int) (this.getPosition().getX() + horizontalIndexOffset), (int) (this.getPosition().getY() + verticalIndexOffset));
    }
}
