/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.collection.queue;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

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
    public final static double DEF_PTR_ZONE_FRACTION = 0.15;
    private Line2D ptrZoneDelimiter;
    private Integer index;
    private QueueNodeRoles role;

    public QueueNodeShape(String data, Point2D pos, Integer index) {
        super(data, pos, DEF_WIDTH_NODE, DEF_HEIGTH_NODE, DEF_FONT_NODE, DEF_STROKE, DEF_NODE_ROUNDED);
        Rectangle2D bounds = getRectNode();
        this.ptrZoneDelimiter = new Line2D.Double(bounds.getX() + DEF_WIDTH_NODE * (1-DEF_PTR_ZONE_FRACTION),
        										  bounds.getY(),
        										  bounds.getX() + DEF_WIDTH_NODE * (1-DEF_PTR_ZONE_FRACTION),
        										  bounds.getY() + DEF_HEIGTH_NODE);
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
        
        ((Graphics2D)g).draw(ptrZoneDelimiter);

        double horizontalRoleOffset = 0;
        double verticalRoleOffset = this.getRectNode().getHeight() * (1.4);

        g.drawString((this.role != null) ? role.toString() : "", (int) (this.getPosition().getX() + horizontalRoleOffset), (int) (this.getPosition().getY() + verticalRoleOffset));

        //index element draw.
        double horizontalIndexOffset = this.getRectNode().getWidth() * (1.4);
        double verticalIndexOffset = 0;
        g.drawString((index != null) ? index.toString() : "", (int) (this.getPosition().getX() + horizontalIndexOffset), (int) (this.getPosition().getY() + verticalIndexOffset));
    }
    
    @Override
    public void moveTo(Point2D point) {
    	super.moveTo(point);
    	ptrZoneDelimiter.setLine(point.getX() + DEF_WIDTH_NODE * (1-DEF_PTR_ZONE_FRACTION),
    							 point.getY(),
    							 point.getX() + DEF_WIDTH_NODE * (1-DEF_PTR_ZONE_FRACTION),
    							 point.getY() + DEF_HEIGTH_NODE);
    }
}
