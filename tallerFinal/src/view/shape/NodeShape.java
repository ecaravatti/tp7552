package view.shape;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.awt.geom.RoundRectangle2D;

import view.element.common.Mobile;
import view.element.common.Selectable;

/**
 * Esta clase define la forma de un nodo.
 * 
 * 
 */
public class NodeShape implements Selectable, Mobile {
	private final static Color DEF_COLOR = new Color(1.0f, 1.0f, 1.0f, 0f);
	private final static Color DEF_BORDER_COLOR = Color.WHITE;
	private final static Color DEF_PRIMARY_NODE_COLOR = new Color(0, 204, 102);
	private final static Color DEF_SECONDARY_NODE_COLOR = new Color(0, 100, 50);
	private final static Color DEF_MODIFIED_NODE_COLOR1 = new Color(196, 0, 0);
	private final static Color DEF_MODIFIED_NODE_COLOR2 = new Color(128, 0, 0);
	private final static Color DEF_SELECTION_COLOR = DefaultShapeSettings.ORANGE_COLOR;
	private final static Color DEF_LINE_COLOR = Color.BLACK;
	private final static Color DEF_TEXT_COLOR = Color.WHITE;
	private final static int DEF_BORDER = 4;
	private final static float DEF_ARC = 15.0f;
	private final static int ALPHA = 100;

	private Rectangle2D rectNode;
	private RectangularShape rectBorder;
	private String data;
	private Color actBorderColor;
	private Color selectionColor;
	private Color primaryNodeColor;
	private Color secondaryNodeColor;
	private Color defNodeColor;
	private Color lineColor;
	private Color textColor;
	private Stroke stroke;
	private Font font;
	private boolean selected;
	private boolean rounded;
	private float arc;
	private boolean useGradientPaint;

	/**
	 * Construye un nodo con los colores por defecto
	 * 
	 * @param data
	 *            dato que contiene el nodo
	 * @param pos
	 *            posicion del nodo
	 * @param width
	 *            ancho del nodo
	 * @param height
	 *            alto del nodo
	 * @param font
	 *            fuente usada para escribir el dato
	 * @param stroke
	 *            trazo usado pora dibujar el nodo
	 * @param rounded
	 *            true si los bordes deben ser redondeados
	 */
	public NodeShape(String data, Point2D pos, int width, int height,
			Font font, Stroke stroke, boolean rounded) {
		super();
		this.data = data;
		this.rectNode = new Rectangle2D.Double(pos.getX(), pos.getY(), width,
				height);
		this.primaryNodeColor = DEF_PRIMARY_NODE_COLOR;
		this.secondaryNodeColor = DEF_SECONDARY_NODE_COLOR;
		this.defNodeColor = DEF_PRIMARY_NODE_COLOR;
		this.actBorderColor = DEF_BORDER_COLOR;
		this.selectionColor = DEF_SELECTION_COLOR;
		this.lineColor = DEF_LINE_COLOR;
		this.textColor = DEF_TEXT_COLOR;
		this.stroke = stroke;
		this.font = font;
		this.selected = false;
		this.rounded = rounded;
		this.arc = DEF_ARC;
		this.rectBorder = this.getBorderRect();
		this.useGradientPaint = true;
	}

	/**
	 * Construye un nodo.
	 * 
	 * @param data
	 *            dato que contiene el nodo
	 * @param pos
	 *            posicion del nodo
	 * @param width
	 *            ancho del nodo
	 * @param height
	 *            alto del nodo
	 * @param font
	 *            fuente usada para escribir el dato
	 * @param stroke
	 *            trazo usado pora dibujar el nodo
	 * @param rounded
	 *            true si los bordes deben ser redondeados
	 * @param primaryNodeColor
	 *            color del nodo
	 * @param lineColor
	 *            color de linea
	 * @param textColor
	 *            color del texto
	 */
	public NodeShape(String data, Point2D pos, int width, int height,
			Font font, Stroke stroke, boolean rounded, Color primaryNodeColor,
			Color secondaryNodeColor, Color lineColor, Color textColor) {
		super();
		this.data = data;
		this.rectNode = new Rectangle2D.Double(pos.getX(), pos.getY(), width,
				height);
		this.primaryNodeColor = primaryNodeColor;
		this.secondaryNodeColor = secondaryNodeColor;
		this.defNodeColor = primaryNodeColor;
		this.actBorderColor = DEF_BORDER_COLOR;
		this.selectionColor = DEF_SELECTION_COLOR;
		this.lineColor = lineColor;
		this.textColor = textColor;
		this.stroke = stroke;
		this.font = font;
		this.selected = false;
		this.rounded = rounded;
		this.arc = DEF_ARC;
		this.rectBorder = this.getBorderRect();
		this.useGradientPaint = true;
	}

	/**
	 * Pinta el nodo en el contexto g
	 * 
	 * @param g
	 *            contexto sobre el que se dibuja
	 */
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		if (selected) {
			g2.setPaint(actBorderColor);
			g2.fill(rectBorder);
		}

		paintNode(g2);

		if (actBorderColor.equals(DEF_COLOR) && selected) {
			Color color = new Color(selectionColor.getRed(),
					selectionColor.getGreen(), selectionColor.getBlue(), ALPHA);
			g2.setPaint(color);
			g2.fill(rectNode);
		}
	}

	/**
	 * Mueve el nodo
	 * 
	 * @param point
	 *            posicion final del nodo
	 */
	public void moveTo(Point2D point) {
		this.rectNode.setRect(point.getX(), point.getY(), rectNode.getWidth(),
				rectNode.getHeight());
		this.rectBorder = this.getBorderRect();
	}

	/**
	 * Obtiene la posicion del nodo
	 * 
	 * @return la posicion del nodo
	 */
	public Point2D getPosition() {
		return new Point2D.Double(rectNode.getX(), rectNode.getY());
	}

	/**
	 * Obtiene los limites del nodo
	 * 
	 * @return el rectangulo que delimita el nodo
	 */
	public Rectangle2D getBounds() {
		return (Rectangle2D) rectNode.clone();
	}

	/**
	 * Cambia los colores a los colores del nodo de acuerdo con el color de
	 * seleccion
	 */
	public void changeColor() {
		if (actBorderColor == DEF_COLOR) {
			this.actBorderColor = this.selectionColor;
			return;
		}
		this.actBorderColor = DEF_COLOR;
	}

	/**
	 * Restaura los colores del nodo a los establecidos por defecto
	 */
	public void restoreColor() {
		this.actBorderColor = DEF_COLOR;
		this.selected = false;
	}

	/**
	 * Restaura los colores primarios y secundarios del fondo del nodo
	 */
	public void restoreBackgroundNodeColors() {
		this.primaryNodeColor = DEF_PRIMARY_NODE_COLOR;
		this.secondaryNodeColor = DEF_SECONDARY_NODE_COLOR;
	}

	/**
	 * Cambia los colores primarios y secundarios del fondo del nodo para
	 * resaltar la modificación
	 */
	public void restoreModifiedNodeColors() {
		this.primaryNodeColor = DEF_MODIFIED_NODE_COLOR1;
		this.secondaryNodeColor = DEF_MODIFIED_NODE_COLOR2;
	}

	/**
	 * Obtiene el color de seleccion
	 * 
	 * @return el color de seleccion
	 */
	public Color getSelectionColor() {
		return this.selectionColor;
	}

	/**
	 * Cambia el color de seleccion
	 * 
	 * @param color
	 *            nuevo color de seleccion
	 */
	public void setSelectionColor(Color color) {
		this.selectionColor = color;
		this.actBorderColor = this.selectionColor;
		this.selected = true;
	}

	/**
	 * Obtiene el color del nodo
	 * 
	 * @return el color del nodo
	 */
	public Color getNodeColor() {
		return primaryNodeColor;
	}

	/**
	 * Cambia el color del nodo
	 * 
	 * @param nodeColor
	 *            nuevo color del nodo
	 */
	public void setNodeColor(Color nodeColor) {
		this.primaryNodeColor = nodeColor;
	}

	/**
	 * Cambia el color del nodo
	 * 
	 * @param nodeColor
	 *            nuevo color del nodo
	 */
	public void setGradientBackground(Color primaryNodeColor,
			Color secondaryNodeColor) {
		this.primaryNodeColor = primaryNodeColor;
		this.secondaryNodeColor = secondaryNodeColor;
	}

	/**
	 * Obtiene el color de linea
	 * 
	 * @return el color de linea
	 */
	public Color getLineColor() {
		return lineColor;
	}

	/**
	 * Cambia el color de linea
	 * 
	 * @param lineColor
	 *            nuevo color de linea
	 */
	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	/**
	 * Cambia el color del nodo al establecido por defecto
	 */
	public void changeNodeColor() {
		this.primaryNodeColor = this.defNodeColor;
	}

	/**
	 * Cambia el color por defecto del nodo
	 * 
	 * @param color
	 *            nuevo color por defecto
	 */
	public void setDefaultNodeColor(Color color) {
		this.defNodeColor = color;
	}

	/**
	 * Obtiene el color por del nodo
	 * 
	 * @return el color por defecto del nodo
	 */
	public Color getDefaultNodeColor() {
		return defNodeColor;
	}

	/**
	 * Determina si el nodo esta seleccionado
	 * 
	 * @return true si el nodo esta seleccionado
	 */
	public boolean isSelected() {
		return this.selected;
	}

	/**
	 * @return el trazo utilizado para dibujar
	 */
	public Stroke getStroke() {
		return stroke;
	}

	/**
	 * @param stroke
	 *            nuevo stroke
	 */
	public void setStroke(Stroke stroke) {
		this.stroke = stroke;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	/**
	 * Determina si de usarse gradiente para rellenar el nodo
	 * 
	 * @param useGradientPaint
	 *            true si debe usarse gradiente, false en caso contrario.
	 */
	public void setUseGradientPaint(boolean useGradientPaint) {
		this.useGradientPaint = useGradientPaint;
	}

	public boolean getUseGradientPaint() {
		return useGradientPaint;
	}

	/**
	 * Pinta el nodo
	 * 
	 * @param graphics
	 *            contexto sobre el que se pinta
	 */
	protected void paintNode(Graphics graphics) {
		Graphics2D g2 = (Graphics2D) graphics;

		g2.setStroke(stroke);
		paintRectNode(g2);
		Text.paintCenterString(g2, data, font, getTextRect(), textColor);
	}

	protected RectangularShape getBorderRect() {
		double x = rectNode.getX() - DEF_BORDER;
		double y = rectNode.getY() - DEF_BORDER;
		double width = rectNode.getWidth() + 2 * DEF_BORDER;
		double height = rectNode.getHeight() + 2 * DEF_BORDER;

		if (rounded) {
			return new RoundRectangle2D.Double(x, y, width, height, DEF_ARC,
					DEF_ARC);
		}

		return new Rectangle2D.Double(x, y, width, height);
	}

	/**
	 * Pinta el rectangulo para contener al nodo.
	 * 
	 * @param graphics
	 *            contexto sobre el que se dibuja.
	 */
	protected void paintRectNode(Graphics graphics) {
		Graphics2D g2 = (Graphics2D) graphics;
		Rectangle2D bounds = this.rectNode;
		Paint paint = primaryNodeColor;

		if (useGradientPaint) {
			paint = new GradientPaint((float) bounds.getCenterX(),
					(float) bounds.getY(), primaryNodeColor,
					(float) bounds.getCenterX(), (float) bounds.getMaxY(),
					secondaryNodeColor, true);
		}

		paintRectGrad(g2, paint, bounds);
	}

	/**
	 * @param graphics
	 *            contexto grafico sobre el que se dibuja
	 * @param grad
	 *            gradiente con que se pinta
	 * @param rect
	 *            rectangulo que se dibuja
	 */
	protected void paintRectGrad(Graphics graphics, Paint grad, Rectangle2D rect) {
		Graphics2D g2 = (Graphics2D) graphics;
		RectangularShape paint = rect;

		if (rounded) {
			paint = new RoundRectangle2D.Double(rect.getX(), rect.getY(),
					rect.getWidth(), rect.getHeight(), arc, arc);
		}

		g2.setPaint(grad);
		g2.fill(paint);
		g2.setPaint(lineColor);
		g2.draw(paint);
	}

	/**
	 * @return rectangulo donde debe centrarse el texto
	 */
	protected Rectangle2D getTextRect() {
		return this.rectNode;
	}

	/**
	 * @return el rectangulo que representa el nodo
	 */
	protected Rectangle2D getRectNode() {
		return this.rectNode;
	}

	/**
	 * @return texto del nodo
	 */
	public String getData() {
		return data;
	}
}
