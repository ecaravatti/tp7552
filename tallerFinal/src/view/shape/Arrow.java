package view.shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * Esta clase define a una flecha.
 * 
 * 
 */
public class Arrow {
	private final static Color DEF_COLOR = Color.BLACK;
	private final static BasicStroke DEF_STROKE = new BasicStroke(1.0f);
	private final static int START_POINT_DIAMETER = 3; // In pixels

	private Point2D p1;
	private Point2D p2;
	private boolean fillHead;
	private Line2D line;
	private Line2D head1;
	private Line2D head2;
	private GeneralPath path;
	private BasicStroke stroke;
	private Color color;
	private boolean drawStartPoint;

	/**
	 * Construye una flecha.
	 * 
	 * @param p1
	 *            punto en que inicia la flecha
	 * @param p2
	 *            punto en que finaliza la flecha
	 * @param fill
	 *            true si debe pintarse la cabeza de la flecha
	 */
	public Arrow(Point2D p1, Point2D p2, boolean fill) {
		this.p1 = p1;
		this.p2 = p2;
		this.fillHead = fill;
		this.stroke = DEF_STROKE;
		this.color = DEF_COLOR;
		this.drawStartPoint = false;
		if (fillHead) {
			createPath();
		} else {
			createLines();
		}
	}

	/**
	 * Construye una flecha.
	 * 
	 * @param p1
	 *            punto en que inicia la flecha
	 * @param p2
	 *            punto en que finaliza la flecha
	 * @param fill
	 *            true si debe pintarse la cabeza de la flecha
	 * @param stroke
	 *            trazo utilizado para pintar la flecha
	 * @param color
	 *            color de la flecha
	 */
	public Arrow(Point2D p1, Point2D p2, boolean fill, BasicStroke stroke,
			Color color, boolean drawStartPoint) {
		this.p1 = p1;
		this.p2 = p2;
		this.fillHead = fill;
		this.stroke = stroke;
		this.color = color;
		this.drawStartPoint = drawStartPoint;
		if (fillHead) {
			createPath();
		} else {
			createLines();
		}
	}

	/**
	 * Crea las lineas para dibujar una flecha
	 */
	private void createLines() {
		Point2D points[] = this.calculateHeadPoints();
		this.line = new Line2D.Double(p1, p2);
		this.head1 = new Line2D.Double(points[0], p2);
		this.head2 = new Line2D.Double(points[1], p2);
	}

	/**
	 * Dibuja la flecha.
	 * 
	 * @param graphics
	 *            contexto sobre el que se dibuja.
	 */
	public void paint(Graphics graphics) {
		Graphics2D g2 = (Graphics2D) graphics;
		g2.setPaint(this.color);
		g2.setStroke(this.stroke);

		if (drawStartPoint) {
			g2.fill(new Ellipse2D.Double(p1.getX() - START_POINT_DIAMETER / 2,
					p1.getY() - START_POINT_DIAMETER / 2, START_POINT_DIAMETER,
					START_POINT_DIAMETER));
		}

		g2.draw(line);

		if (fillHead) {
			g2.fill(path);
		} else {
			g2.draw(head1);
			g2.draw(head2);
		}
	}

	/**
	 * Crea el path para dibujar la cabeza rellena de la flecha
	 */
	private void createPath() {
		Point2D points[] = this.calculateHeadPoints();

		float x[] = { (float) points[0].getX(), (float) p2.getX(),
				(float) points[1].getX() };
		float y[] = { (float) points[0].getY(), (float) p2.getY(),
				(float) points[1].getY() };

		this.line = new Line2D.Double(p1, p2);
		path = new GeneralPath(GeneralPath.WIND_EVEN_ODD, x.length);

		path.moveTo(x[0], y[0]);
		for (int i = 1; i < x.length; i++) {
			path.lineTo(x[i], y[i]);
		}

		path.closePath();
	}

	/**
	 * Calcula los puntos que definen la cabeza de la flecha.
	 * 
	 * @return un vector que contiene los puntos que definen la cabeza de la
	 *         flecha
	 */
	private Point2D[] calculateHeadPoints() {
		Point2D points[] = new Point2D[2];
		double deltaX = p2.getX() - p1.getX();
		double deltaY = p2.getY() - p1.getY();

		double x1 = p1.getX();
		double y1 = p1.getY();
		double sizeHead = this.getSizeHead();

		points[0] = new Point2D.Double(x1 + (1 - sizeHead) * deltaX + sizeHead
				* deltaY, y1 + (1 - sizeHead) * deltaY - sizeHead * deltaX);

		points[1] = new Point2D.Double(x1 + (1 - sizeHead) * deltaX - sizeHead
				* deltaY, y1 + (1 - sizeHead) * deltaY + sizeHead * deltaX);
		return points;

	}

	/**
	 * Calcula el tamano de la cabeza de la flecha (es proporcional a la
	 * longitud y al ancho del trazo)
	 * 
	 * @return el tamano de la cabeza de la flecha.
	 */
	private double getSizeHead() {
		return (4 + stroke.getLineWidth()) / p1.distance(p2);
	}
}
