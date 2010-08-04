package view.collection.heap;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import model.collection.queue.ArrayListQueueImpl;
import model.exception.queue.QueueFullException;
import view.animation.heap.DeleteItemHeapAnimation;
import view.animation.heap.EmptyAnimation;
import view.animation.heap.EndAnimation;
import view.animation.heap.InsertItemHeapAnimation;
import view.animation.heap.SwapElementsAnimation;
import view.collection.tree.BSTNodeShape;
import view.command.common.ShowPrimitiveCodeCommand;
import view.common.AnimatedPanel;
import view.common.InteractivelyControlled;
import view.shape.Arrow;
import view.shape.DefaultShapeSettings;
import view.shape.NodeShape;
import view.shape.Text;
import event.heap.HeapListener;

/**
 *
 */
public class HeapView<T> extends AnimatedPanel implements HeapListener<T>, InteractivelyControlled {
	
	private static final long serialVersionUID = 1L;
	
	// Constantes
    private static final Font FONT = new Font(Font.SANS_SERIF, Font.BOLD, 14);
    private static final Stroke STROKE = new BasicStroke(1.0f);
    private final static BasicStroke ARROW_STROKE = new BasicStroke(2.0f);
    private static final Integer SQUARESIZE = 25;
    private static final Font DEF_FONT_BALANCE = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
    private static final Color DEF_COLOR_ARROW = Color.GRAY;
    private static final int INITIAL_HORIZONTAL = 50;
    private static final int INITIAL_VERTICAL = 30;
    
    // Elemento actual del heap
    private Integer current = 0;
    
    // Figuras para dibujar
    private List<NodeShape> rectangles = new ArrayList<NodeShape>();
    private List<Text> labels = new ArrayList<Text>();
    private List<BSTNodeShape> nodes = new ArrayList<BSTNodeShape>();
    private List<Arrow> arrows = new ArrayList<Arrow>();
    
    // Posiciones de las figuras
    private int nodePosX[] = new int[] { 450, 250, 650, 150, 350, 550, 750, 100, 200, 300, 400, 500, 600,
    									 700, 800, 75, 125, 175, 225, 275, 325, 375, 425, 475, 525, 575,
    									 625, 675, 725, 775 };
    private int nodePosY[] = new int[] { 100, 170, 170, 240, 240, 240, 240, 290, 290, 290, 290, 290, 290,
    									 290, 290, 340, 340, 340, 340, 340, 340, 340, 340, 340, 340, 340,
    									 340, 340, 340, 340 };
    private int labelPosX[] = new int[] { 53, 78, 103, 128, 153, 178, 203, 228, 253, 278, 303, 328, 353,
    									  378, 403, 428, 453, 478, 503, 528, 553, 578, 603, 628, 653, 678,
    									  703, 728, 753, 778 };
    private int labelPosY = 48;
    
    private ArrayListQueueImpl<Text> currentLabelQueue = new ArrayListQueueImpl<Text>();
    private ArrayListQueueImpl<Text> parentLabelQueue = new ArrayListQueueImpl<Text>();
    private ArrayListQueueImpl<BSTNodeShape> currentNodeQueue = new ArrayListQueueImpl<BSTNodeShape>();
    private ArrayListQueueImpl<BSTNodeShape> parentNodeQueue = new ArrayListQueueImpl<BSTNodeShape>();
    private Integer swapCount = 0;

    public HeapView() {
        super();
    }

    public void initCapacity(int capacity) {
        rectangles.clear();
        labels.clear();
        nodes.clear();
        arrows.clear();
        current = 0;

        for (int i = 0; i < capacity; i++) {
            rectangles.add(createRect(i*SQUARESIZE + INITIAL_HORIZONTAL, INITIAL_VERTICAL,
            							SQUARESIZE, SQUARESIZE, ""));
        }
        
        setStructureCapacity(capacity);
    }

    private Text createLabel(String title) {
        Point2D currentPosition = new Point2D.Double(labelPosX[current], labelPosY);
        return new Text(title, DEF_FONT_BALANCE, currentPosition);
    }

    private BSTNodeShape createNode(String title) {
        BSTNodeShape node = new BSTNodeShape(title, null, DefaultShapeSettings.TREE_NODE_DIAMETER,
        									 FONT, DEF_FONT_BALANCE, STROKE);
        Point2D currentPosition = new Point2D.Double((double) nodePosX[current], (double) nodePosY[current]);
        node.moveTo(currentPosition);

        return node;
    }

    private Arrow createArrow(Point2D parentPosition, Point2D currentPosition) {
        Point2D p1 = new Point2D.Double(parentPosition.getX() + DefaultShapeSettings.TREE_NODE_DIAMETER / 2,
        								parentPosition.getY() + DefaultShapeSettings.TREE_NODE_DIAMETER / 2);
        Point2D p2 = new Point2D.Double(currentPosition.getX() + DefaultShapeSettings.TREE_NODE_DIAMETER / 2,
        								currentPosition.getY());
        return new Arrow(p1, p2, true, ARROW_STROKE, DEF_COLOR_ARROW, false);
    }

    private NodeShape createRect(int x, int y, int width, int height, String label) {
        Point2D position = new Point2D.Double(x, y);

        //Nodo rectangular con colores por defecto
        return new NodeShape(label, position, width, height, FONT, STROKE, false);
    }

    @Override
    public void itemAdded(T item) {
        this.addAnimationToQueue(new EndAnimation<T>(this, "Fin de inserción del elemento " + item.toString() + "\n"));
        current++;
        rerender();
    }

    @Override
    public void itemDeleted(T item) {
        this.addAnimationToQueue(new EndAnimation<T>(this, "Fin de eliminación del elemento " + item.toString() + "\n"));
        current--;
        rerender();
    }

    @Override
    public void itemsSwapped(Integer current, Integer parent) {
        swapCount++;

        Text parentLabel = labels.get(parent);
        Text currentLabel = labels.get(current);

        BSTNodeShape parentNode = nodes.get(parent);
        BSTNodeShape currentNode = nodes.get(current);

        // Encolar las figuras para luego utilizarlas en la animacion
        // (se encola para cuando se ejecute la animación se obtenga la posición
        // real de la figura)
        try {
	        parentLabelQueue.enqueue(parentLabel);
	        currentLabelQueue.enqueue(currentLabel);
	        parentNodeQueue.enqueue(parentNode);
	        currentNodeQueue.enqueue(currentNode);
        } catch (QueueFullException e) {
        	//Hacer algo?
        }

        this.addAnimationToQueue(new SwapElementsAnimation<T>(this));

        // Intercambiar elementos
        labels.set(current, parentLabel);
        labels.set(parent, currentLabel);
        nodes.set(current, parentNode);
        nodes.set(parent, currentNode);
        
        rerender();
    }

    @Override
    protected void paintPanel(Graphics2D g) {
        for (NodeShape nodeShape : rectangles) {
            nodeShape.paint(g);
        }

        for (Text label : labels) {
            label.paint(g);
        }

        for (Arrow arrow : arrows) {
            arrow.paint(g);
        }

        for (BSTNodeShape node : nodes) {
            node.paint((Graphics2D) g, 1);
        }
    }

    @Override
    public void addingItem(T item) {
        swapCount = 0;

        labels.add(createLabel(String.valueOf(item)));
        nodes.add(createNode(String.valueOf(item)));

        this.addCommandToQueue(new ShowPrimitiveCodeCommand(this, HeapPrimitives.insert.getCode()));
        this.addAnimationToQueue(new InsertItemHeapAnimation<T>(this, item));

        // Si no es el primer elemento, agregar una flecha
        if (current != 0) {
            int parent = getParentIndex(current);

            Point2D parentPosition = nodes.get(parent).getPosition();
            Point2D currentPosition = nodes.get(current).getPosition();

            arrows.add(createArrow(parentPosition, currentPosition));
        }
    }
    
    @Override
    public void deletingItem(T item) {
        if (item == null) {
            return;
        }

        swapCount = 0;

        labels.get(0).setTitle("");

        this.addCommandToQueue(new ShowPrimitiveCodeCommand(this, HeapPrimitives.delete.getCode()));
        this.addAnimationToQueue(new DeleteItemHeapAnimation<T>(this, item));

        // Si hay más de un elemento en el heap
        // mover el último elemento a la raíz
        if (current > 1) {
            itemsSwapped(current - 1, 0);
        }

        try {
            labels.remove(labels.size() - 1);
            nodes.remove(nodes.size() - 1);
            arrows.remove(arrows.size() - 1);
        } catch (IndexOutOfBoundsException e) {
        	
        }
    }

    public final Text getCurrentLabel() {
        return currentLabelQueue.dequeue();
    }

    public final Text getParentLabel() {
        return parentLabelQueue.dequeue();
    }

    public final BSTNodeShape getCurrentNode() {
        return currentNodeQueue.dequeue();
    }

    public final BSTNodeShape getParentNode() {
        return parentNodeQueue.dequeue();
    }

    private int getParentIndex(int index) {
        return (index - 1) / 2;
    }

    public Integer getSwapCount() {
        return swapCount;
    }
    
    @Override
    public void emptyHeap() {
    	this.addAnimationToQueue(new EmptyAnimation<T>(this));
    }

	@Override
	protected void adjustGraphicDimensionForScrolling() {
		int minWidth = 2*INITIAL_HORIZONTAL + rectangles.size()*SQUARESIZE;
		int minHeight = 2*INITIAL_VERTICAL + SQUARESIZE;
		int width, height;
		
		if (!nodes.isEmpty()) {
			width = new Double(Math.max(graphicDimension.getWidth(), nodePosX[current-1]
								+ DefaultShapeSettings.TREE_NODE_DIAMETER + INITIAL_HORIZONTAL)).intValue();
			height = new Double(Math.max(graphicDimension.getHeight(), nodePosY[current-1]
									+ DefaultShapeSettings.TREE_NODE_DIAMETER + INITIAL_VERTICAL)).intValue();
		} else {
			width = minWidth;
			height = minHeight;
		}
		
		this.graphicDimension = new Dimension(width, height);
	}
}
