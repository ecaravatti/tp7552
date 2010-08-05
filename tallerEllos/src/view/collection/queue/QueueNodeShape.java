/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.collection.queue;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import view.shape.DefaultShapeSettings;
import view.shape.NodeShape;
import view.shape.Text;

/**
 *
 */
public class QueueNodeShape extends NodeShape {

    private final static Font DEF_FONT_NODE = new Font("SansSerif", Font.BOLD, 12);
    private final static BasicStroke DEF_STROKE = new BasicStroke(1.5f);
    private final static boolean DEF_NODE_ROUNDED = true;
    public final static int DEF_WIDTH_NODE = 50;
    public final static int DEF_HEIGHT_NODE = 50;
    public final static double DEF_PTR_ZONE_FRACTION = 0.15;
    
    private Line2D ptrZoneDelimiter;
    private Integer index;
    private Integer circularQueueIndex; // For Circular Queue
    private double arcAngle; // For Circular Queue
    private QueueNodeRoles role;

    public QueueNodeShape(String data, Point2D pos, Integer index,
    					  Integer circularQueueIndex, double arcAngle) {
        super(data, pos, DEF_WIDTH_NODE, DEF_HEIGHT_NODE, DEF_FONT_NODE, DEF_STROKE, DEF_NODE_ROUNDED);
        Rectangle2D bounds = getRectNode();
        this.ptrZoneDelimiter = new Line2D.Double(bounds.getX() + DEF_WIDTH_NODE * (1-DEF_PTR_ZONE_FRACTION),
        										  bounds.getY(),
        										  bounds.getX() + DEF_WIDTH_NODE * (1-DEF_PTR_ZONE_FRACTION),
        										  bounds.getY() + DEF_HEIGHT_NODE);
        this.index = index;
        this.circularQueueIndex = circularQueueIndex;
        this.arcAngle = arcAngle;
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

        if (this.role != null) {
        	double horizontalRoleOffset = 0;
        	double verticalRoleOffset = this.getRectNode().getHeight() * (1.4);
        	// Para la cola lineal
        	g.drawString(role.toString(), (int) (this.getPosition().getX() + horizontalRoleOffset),
        				(int) (this.getPosition().getY() + verticalRoleOffset));
        	// Para la cola circular
        	Text.paintCenterString((Graphics2D)g, role.toString(), g.getFont(),
        							getTextRectForCircularNode(false));
        }

        // Index para la cola lineal
        if (index != null) {
        	double horizontalIndexOffset = this.getRectNode().getWidth() * (1.4);
        	double verticalIndexOffset = 0;
        	g.drawString(index.toString(), (int) (this.getPosition().getX() + horizontalIndexOffset),
        				(int) (this.getPosition().getY() + verticalIndexOffset));
        }
        
        // Contenido del nodo para la cola circular
    	Text.paintCenterString((Graphics2D)g, getData(), DEF_FONT_NODE, getTextRectForCircularNode(true));
    }
    
    @Override
    public void moveTo(Point2D point) {
    	super.moveTo(point);
    	ptrZoneDelimiter.setLine(point.getX() + DEF_WIDTH_NODE * (1-DEF_PTR_ZONE_FRACTION),
    							 point.getY(),
    							 point.getX() + DEF_WIDTH_NODE * (1-DEF_PTR_ZONE_FRACTION),
    							 point.getY() + DEF_HEIGHT_NODE);
    }
    
    private Rectangle2D getTextRectForCircularNode(boolean insideTheNode) {
    	
    	int max_radius;
		int min_radius;
		
    	if (insideTheNode) {
			max_radius = DefaultShapeSettings.CIRCULAR_QUEUE_MAX_RADIUS;
			min_radius = DefaultShapeSettings.CIRCULAR_QUEUE_MIN_RADIUS;
    	} else {
    		max_radius = DefaultShapeSettings.CIRCULAR_QUEUE_MAX_RADIUS + 30;
    		min_radius = DefaultShapeSettings.CIRCULAR_QUEUE_MAX_RADIUS;
    	}
		
    	int centerX = DefaultShapeSettings.INITIAL_CIRCULAR_QUEUE_VERTICAL
    				  + DefaultShapeSettings.CIRCULAR_QUEUE_MAX_RADIUS;
		int centerY = centerX;
		
		int x1 = (int) (centerX - min_radius * Math.cos((circularQueueIndex + 0.5) * arcAngle));
    	int x2 = (int) (centerX - max_radius * Math.cos((circularQueueIndex + 0.5) * arcAngle));
    	
    	int y1 = (int) (centerY - min_radius * Math.sin((circularQueueIndex + 0.5) * arcAngle));
		int y2 = (int) (centerY - max_radius * Math.sin((circularQueueIndex + 0.5) * arcAngle));
		
		int minX = Math.min(x1, x2);
		int minY = Math.min(y1, y2);
		int maxX = Math.max(x1, x2);
		int maxY = Math.max(y1, y2);
    	
    	return new Rectangle(minX, minY, maxX - minX, maxY - minY);
    }
}
