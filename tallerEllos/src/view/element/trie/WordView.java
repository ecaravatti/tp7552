package view.element.trie;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import view.element.common.AbstractElementView;
import view.element.common.Selectable;
import view.shape.NodeShape;

/**
 * Esta define como se representa visualmente una palabra.
 * 
 * @author Agustina
 */
public class WordView extends AbstractElementView{
  private final static Color LETTER_SELECTION_COLOR = Color.blue.brighter();
  private final static int DEF_SPACING = 10;
  private final static int DEF_LETTER_WIDTH = 30;
  private final static int DEF_LETTER_HEIGHT = 30;
  private final static Color DEF_LETTER_COLOR = new Color(70, 130, 180);
  private final static Stroke DEF_LETTER_STROKE = new BasicStroke(3.0f);
  private final static Font DEF_FONT = new Font("SansSerif", Font.BOLD, 14);

  private List<Letter> letters;
  private List<AbstractTrieNodeView> nodes;
  private String word;
  private int letterWidth;
  private int letterHeight;
  private Font font;
  private Stroke stroke;
  private Color fillLetter;
  private int index;
  private int spacing;

  /**
   * Construye un WordView
   * 
   * @param word palabra
   * @param letterWidth ancho de la letra
   * @param letterHeight alto de la letra
   * @param font fuente usada para dibujar la palabra
   * @param stroke trazo usado para dibujar la palabra
   * @param colorFillLetter color de relleno de las letras
   * @param colorBorderLetter color de borde de las letras
   */
  public WordView(String word, int letterWidth, int letterHeight, Font font,
      Stroke stroke, Color colorFillLetter, Color colorBorderLetter) {
    super(null, true);
    this.letters = null;
    this.word = word;
    this.letterWidth = letterWidth;
    this.letterHeight = letterHeight;
    this.font = font;
    this.stroke = stroke;
    this.fillLetter = colorFillLetter;
    this.index = 0;
    this.spacing = DEF_SPACING;
    this.setFlashingColor( LETTER_SELECTION_COLOR );
  }

  /**
   * Construye un WordView.
   * 
   * @param word palabra
   */
  public WordView(String word) {
    super(null, true);
    this.letters = null;
    this.word = word;
    this.letterWidth = DEF_LETTER_WIDTH;
    this.letterHeight = DEF_LETTER_HEIGHT;
    this.font = DEF_FONT;
    this.stroke = DEF_LETTER_STROKE;
    this.fillLetter = DEF_LETTER_COLOR;
    this.index = 0;
    this.spacing = DEF_SPACING;
    this.setFlashingColor(LETTER_SELECTION_COLOR);
  }



  @Override
  public Dimension getDimension() {
    int height = this.letterHeight;
    int width = 0;

    for (int i = 0; i < word.length() ; i++)
      width += this.letterWidth + this.spacing;

    width += letterWidth;
    return new Dimension(width, height);
  }

  /**
   * Inicializa la palabra en la posicion deseada 
   * @param pos posicion de la palabra
   */
  public void initialize(Point2D pos) {
    this.moveTo(pos);
    initList();
  }

  /**
   * Obtiene la letra cuyo indice es i
   * @param index i indice de la letra
   * @return la letra cuyo indice es i
   */
  public NodeShape getLetter(int index){
   
    if (index >= this.letters.size()) return null;
    
    return this.letters.get(index).getNodeShape();
  }

  /**
   * La letra seleccionada parpadea
   * @param index indice de la letra 
   * @param delay pausa en el parpadeo
   */
  public void setFlashingLetter(int index, int delay) {
    this.index = index;
    super.setFlashing(delay);
  }
  
  /**
   * Obtiene el tamano de la palabra
   * @return el tamano de la palabra en elementos
   */
  public int getSize(){
    return this.letters.size();  
  }
  
  /**
   * Asocia una letra con un nodo
   * @param i el indice de la letra a asociar
   * @param node el nodo a asociar
   */
  public void associate(int i, AbstractTrieNodeView node ){
    this.nodes.set(i, node);
  }
  
  /**
   * Obtiene el nodo asociado a letra que se encuentra en la posicion
   * i de la palabra
   * @param i posicion de la letra en la palabra
   * @return el nodo asociado
   */
  public AbstractTrieNodeView getAssociateNode(int i){
    return this.nodes.get(i);
  }
  
  /**
   * Obtiene la letra asociada al nodo
   * @param node nodo a buscar letra asociado
   * @return la letra asociada al nodo
   */
  public NodeShape getAssociateLetter(AbstractTrieNodeView node){
    int i = 0;
    
    for (AbstractTrieNodeView current : nodes ){
      if (node == current) return this.letters.get(i).getNodeShape(); 
      i++;
    }
    
    return null;
    
  }
  
  /**
   * Determina que una letra debe ser visible (o no).
   * @param index indice de la letra a cambiar su visibilidad
   * @param visible true si debe ser visible, false en caso contrario
   */
  public void setVisible(int index, boolean visible){
    if ( index < this.getSize() - 1 )
      letters.get(index).setVisible(visible);
  }
  
  /**
   * Indica si una letra el visible o no
   * @param index indice asociado a la letra
   * @return true si la letra es visible, false en caso contrario.
   */
  public boolean isVisible(int index){
    if ( index < this.getSize() )
      return this.letters.get(index).isVisible();
    return false;
  }
  
  /**
   * Mueve todas las letras de acuerdo a la posicion del nodo asociado con ellas.
   */
  public void move(){
    int i = 0;
    
    for (Letter letter : letters){
      AbstractTrieNodeView node = nodes.get(i);
      if ( node != null && node.isVisible() ){
        Point2D posNode = nodes.get(i).getPosition();
        Point2D posLetter = letter.getNodeShape().getPosition();
        letter.getNodeShape().moveTo( new Point2D.Double( posLetter.getX(), posNode.getY()) );
      }
      i++;
    }
  }
  
  @Override
  public Selectable getSelectable(){
    return getLetter(index);
  }

  @Override
  protected void paintElement(Graphics graphics) {
    Graphics2D g = (Graphics2D) graphics;

    for (Letter letter : letters) {
      NodeShape node = letter.getNodeShape();
        
      if ( letter.isVisible() ){
        node.paint(g);

        if (!node.isSelected() )  {
          Color fill = node.getNodeColor();
          g.setPaint(new Color(fill.getRed(), fill.getGreen(), fill.getBlue(), 100));
          g.fill(node.getBounds());
        }
      }
    }
  }
  
  /**
   * Obtiene la palabra
   * @return la palabra
   */
  public String getWord(){
    return word;
  }
  
  /**
   * Vacia la palabre
   */
  public void clear() {
    if (letters != null)
      this.letters.clear();
  }

  /**
   * Inicializa la lista
   */
  private void initList() {
    letters = new ArrayList<Letter>();
    nodes = new ArrayList<AbstractTrieNodeView>();
    Point2D lettPos = this.getPosition();

    for (int i = 0; i < word.length(); i++) {
      NodeShape nodeShape = new NodeShape(word.substring(i, i + 1), lettPos,
          letterWidth, letterHeight, font, stroke, false);
      nodeShape.setNodeColor(fillLetter);
      nodeShape.setDefaultNodeColor(fillLetter);
      nodeShape.setLineColor( Color.BLUE.darker() );
      Letter letter = new Letter(nodeShape, true);
      this.letters.add(letter);
      this.nodes.add(null);
      lettPos = new Point2D.Double(lettPos.getX() + letterWidth + spacing,
          lettPos.getY());
    }
    
    NodeShape nodeShape = new NodeShape(word.substring(0, 1), lettPos, letterWidth, letterHeight, font,
        stroke, true);
    Letter letter = new Letter(nodeShape, true);
    letter.setVisible(false);
    this.letters.add(letter);
    this.nodes.add(null);
    nodeShape.setNodeColor(fillLetter);
    nodeShape.setDefaultNodeColor(fillLetter);
    nodeShape.setLineColor( Color.BLUE.darker() );
  }
  
  private class Letter{
    private NodeShape node;
    private boolean visible;
    
    public Letter(NodeShape node, boolean visible) {
      super();
      this.node = node;
      this.visible = visible;
    }

    protected NodeShape getNodeShape() {
      return node;
    }

    protected void setNode(NodeShape node) {
      this.node = node;
    }

    protected boolean isVisible() {
      return visible;
    }

    protected void setVisible(boolean visible) {
      this.visible = visible;
    }
  }
}
