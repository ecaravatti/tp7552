package view.shape;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import view.element.common.Mobile;

public class Text implements Mobile {

    private Point2D position = null;
    private String title = null;
    private Color color = Color.black;
    private Font font = null;

    public Text(String title, Font font, Point2D position) {
        this.title = title;
        this.position = position;
        this.font = font;
    }

    /**
     * Dibuja un string centrado en el rectangulo que contiene al texto
     * 
     * @param graphics contexto sobre el que se dibuja el string.
     * @param text texto a pintar
     * @param font fuente con que debe dibujarse el texto
     * @param rect rectangulo en que debe centrarse el texto
     */
    public static void paintCenterString(Graphics2D graphics, String text,
            Font font, Rectangle2D rect) {

        Font prevFnt = graphics.getFont();
        graphics.setFont(font);
        FontMetrics fontMetrics = graphics.getFontMetrics();
        Rectangle stringBounds = fontMetrics.getStringBounds(text, graphics).getBounds();
        FontRenderContext renderContext = graphics.getFontRenderContext();
        GlyphVector glyphVector = font.createGlyphVector(renderContext, text);
        Rectangle visualBounds = glyphVector.getVisualBounds().getBounds();

        int centX = new Double(rect.getCenterX() - stringBounds.width / 2).intValue();
        int centY = new Double(rect.getCenterY() - visualBounds.height / 2 - visualBounds.y).intValue();

        graphics.drawString(text, centX, centY);
        graphics.setFont(prevFnt);
    }

    public void paint(Graphics graphics) {
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setPaint(color);
        g2.setFont(font);
        g2.drawString(title, (int) position.getX(), (int) position.getY());
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void moveTo(Point2D position) {
        setPosition(position);
    }
}
