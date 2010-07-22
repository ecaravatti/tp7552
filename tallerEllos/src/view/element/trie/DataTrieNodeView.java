package view.element.trie;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import view.shape.NodeShape;

/**
 * Esta clase representa la vista de un trie node que contiene un dato.
 * 
 * 
 */
public class DataTrieNodeView extends AbstractTrieNodeView {
  private final static Font DEF_FONT = new Font("SansSerif", Font.BOLD, 10);
  private final static int DEF_MARGIN = 5;
  
  private boolean defaultSizeChanged;

  /**
   * Construye un DataTrieNodeView
   * @param data el dato
   * @param position posicion del nodo
   * @param parent padre del nodo
   * @param trieView 
   */
  public DataTrieNodeView(String data, Point2D position, PointerView parent,
          TrieViewPrimitives trieView) {
    super(data, position, parent, trieView);
    this.shape = new NodeShape(data, getPosition(), getWidth(), getHeight(),
        DEF_FONT, DEF_STROKE, true);
    this.defaultSizeChanged = false;
    this.setDepthSibling(1);
  }

  @Override
  public void moveTo(Point2D position) {
    super.moveTo(position);
    shape.moveTo(position);
  }

  /**
   * Determina si el tamano por defecto cambio
   * @return true si el tamano por defecto cambio, false en caso contrario
   */
  public boolean defaultSizeChanged() {
    return defaultSizeChanged;
  }

  /**
   * Calcula el tamano del nodo de acuerdo al ancho del texto que debe ser
   * mostrado
   * @param g2 contexto grafico
   * @param word palabra a dibujar
   */
  public void calculateWidth(Graphics2D g2) {
    g2.setFont(DEF_FONT);
    FontMetrics fontMetrics = g2.getFontMetrics();
    Rectangle stringBounds = fontMetrics.getStringBounds(getData(), g2)
        .getBounds();
    int widthFont = stringBounds.width + 2 * DEF_MARGIN;
    if (widthFont > this.getWidth()) {
      
      if (widthFont > (this.getWidth() + TrieNodeView.DEF_LENGTH - 5)){
        this.setDepthSibling((double) getWidth() / widthFont);
        this.defaultSizeChanged = true;
        
      }
      this.setWidth(widthFont);
    }

    getParent().getSourceNode().changeFirstChildPosition(this);
  }

  @Override
  public void setVisible(boolean visible) {
    if (visible != isVisible())
      getParent().getSourceNode().setVisiblePtrData(visible);

    super.setVisible(visible);
  }
  
  @Override
  protected NodeShape createShape(){
    return new NodeShape(getData(), getPosition(), getWidth(), getHeight(),
        DEF_FONT, DEF_STROKE, true);
  }
  
  @Override
  protected void paintTrieNode(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;

    if (isVisible()) {
      shape.paint(g);

      if (this.isTransparent()) {
        g2.setPaint(this.getTransparentColor());
        g2.fill(shape.getBounds());
      }
    }
  }
}
