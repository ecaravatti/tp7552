/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.collection.stack;

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
 * 
 */
public class StackNodeShape extends NodeShape {

	private final static Font DEF_FONT_NODE = new Font("SansSerif", Font.BOLD,
			12);
	private final static BasicStroke DEF_STROKE = new BasicStroke(1.5f);
	private final static boolean DEF_NODE_ROUNDED = true;
	public final static int DEF_WIDTH_NODE = 50;
	public final static int DEF_HEIGHT_NODE = 40;
	public final static double DEF_PTR_ZONE_FRACTION = 0.15;

	private Line2D ptrZoneDelimiter;
	private Integer index;
	private StackNodeRoles role;

	public StackNodeShape(String data, Point2D pos, Integer index) {
		super(data, pos, DEF_WIDTH_NODE, DEF_HEIGHT_NODE, DEF_FONT_NODE,
				DEF_STROKE, DEF_NODE_ROUNDED);
		Rectangle2D bounds = getRectNode();
		this.ptrZoneDelimiter = new Line2D.Double(bounds.getX(), bounds.getY()
				+ DEF_HEIGHT_NODE * (1 - DEF_PTR_ZONE_FRACTION), bounds.getX()
				+ DEF_WIDTH_NODE, bounds.getY() + DEF_HEIGHT_NODE
				* (1 - DEF_PTR_ZONE_FRACTION));
		this.index = index;
	}

	public void setRole(StackNodeRoles role) {
		this.role = role;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		((Graphics2D) g).draw(ptrZoneDelimiter);

		double horizontalRoleOffset = -StackNodeShape.DEF_WIDTH_NODE;
		double verticalRoleOffset = this.getRectNode().getHeight() / 2;

		g.drawString((this.role != null) ? role.toString() : "", (int) (this
				.getPosition().getX() + horizontalRoleOffset), (int) (this
				.getPosition().getY() + verticalRoleOffset));

		// index element draw.
		double horizontalIndexOffset = this.getRectNode().getWidth() * (1.4);
		double verticalIndexOffset = 0;
		g.drawString(index.toString(),
				(int) (this.getPosition().getX() + horizontalIndexOffset),
				(int) (this.getPosition().getY() + verticalIndexOffset));

	}

	@Override
	public void moveTo(Point2D point) {
		super.moveTo(point);
		ptrZoneDelimiter.setLine(point.getX(), point.getY() + DEF_HEIGHT_NODE
				* (1 - DEF_PTR_ZONE_FRACTION), point.getX() + DEF_WIDTH_NODE,
				point.getY() + DEF_HEIGHT_NODE * (1 - DEF_PTR_ZONE_FRACTION));
	}
}
