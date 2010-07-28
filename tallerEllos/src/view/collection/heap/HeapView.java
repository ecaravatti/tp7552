package view.collection.heap;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import model.collection.queue.ArrayListQueueImpl;
import model.exception.queue.QueueFullException;
import view.animation.heap.DeleteItemHeapAnimation;
import view.animation.heap.EmptyAnimation;
import view.animation.heap.EndAnimation;
import view.animation.heap.InsertItemHeapAnimation;
import view.animation.heap.SwapElementsAnimation;
import view.collection.tree.BSTNodeShape;
import view.common.AnimatedPanel;
import view.common.InteractivelyControlled;
import view.common.JTextFieldLimit;
import view.shape.Arrow;
import view.shape.NodeShape;
import view.shape.Text;
import controller.HeapController;
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
    private static final int DIAMETER = 35;
    
    // Elemento actual del heap
    private Integer current = 0;
    
    // Figuras para dibujar
    private List<NodeShape> rectangles = new ArrayList<NodeShape>();
    private List<Text> labels = new ArrayList<Text>();
    private List<BSTNodeShape> nodes = new ArrayList<BSTNodeShape>();
    private List<Arrow> arrows = new ArrayList<Arrow>();
    
    // Posiciones de las figuras
    private int nodePosX[] = new int[]{450, 250, 650, 150, 350, 550, 750, 100, 200, 300, 400, 500, 600, 700, 800,
        75, 125, 175, 225, 275, 325, 375, 425, 475, 525, 575, 625, 675, 725, 775, 825
    };
    private int nodePosY[] = new int[]{150, 220, 220, 290, 290, 290, 290, 360, 360, 360, 360, 360, 360, 360, 360,
        430, 430, 430, 430, 430, 430, 430, 430, 430, 430, 430, 430, 430, 430, 430, 430
    };
    private int labelPosX[] = new int[]{78, 103, 128, 153, 178, 203, 228, 253, 278, 303, 328, 353, 378, 403,
        428, 453, 478, 503, 528, 553, 578, 603, 628, 653, 678, 703, 728, 753, 778, 803, 828
    };
    private int labelPosY = 118;
    
    // Generador de números aleatorios
    private Random generator = new Random();
    private ArrayListQueueImpl<Text> currentLabelQueue = new ArrayListQueueImpl<Text>();
    private ArrayListQueueImpl<Text> parentLabelQueue = new ArrayListQueueImpl<Text>();
    private ArrayListQueueImpl<BSTNodeShape> currentNodeQueue = new ArrayListQueueImpl<BSTNodeShape>();
    private ArrayListQueueImpl<BSTNodeShape> parentNodeQueue = new ArrayListQueueImpl<BSTNodeShape>();
    private Integer swapCount = 0;

    public HeapView() {
        super();
        initComponents();
        createHeap();
    }

    public void setEnabledButtons(boolean b) {
        insertBtn.setEnabled(b);
        deleteBtn.setEnabled(b);
        randomBtn.setEnabled(b);
        cleanBtn.setEnabled(b);
        heapSizeComboBox.setEnabled(b);
    }

    private void createHeap() {
        rectangles.clear();
        labels.clear();
        nodes.clear();
        arrows.clear();

        int heapSize = Integer.valueOf(heapSizeComboBox.getSelectedItem().toString()).intValue();

        rectangles.add(createRect(50, 100, SQUARESIZE, SQUARESIZE, "ini"));
        for (int i = 1; i < heapSize + 1; i++) {
            rectangles.add(createRect(i*SQUARESIZE + 50, 100, SQUARESIZE, SQUARESIZE, ""));
        }

        clearHeap();
    }

    private void clearHeap() {
        labels.clear();
        nodes.clear();
        arrows.clear();
        if (controller != null) {
        	HeapController.class.cast(controller).clear();
        }
        current = 0;
        repaint();
    }

    private Text createLabel(String title) {
        Point2D currentPosition = new Point2D.Double(labelPosX[current], labelPosY);
        return new Text(title, FONT, currentPosition);
    }

    private BSTNodeShape createNode(String title) {
        BSTNodeShape node = new BSTNodeShape(title, null, DIAMETER, FONT, DEF_FONT_BALANCE, STROKE);
        Point2D currentPosition = new Point2D.Double((double) nodePosX[current], (double) nodePosY[current]);
        node.moveTo(currentPosition);

        return node;
    }

    private Arrow createArrow(Point2D parentPosition, Point2D currentPosition) {
        Point2D p1 = new Point2D.Double(parentPosition.getX() + DIAMETER / 2, parentPosition.getY() + DIAMETER / 2);
        Point2D p2 = new Point2D.Double(currentPosition.getX() + DIAMETER / 2, currentPosition.getY());
        return new Arrow(p1, p2, true, ARROW_STROKE, DEF_COLOR_ARROW, false);
    }

    private NodeShape createRect(int x, int y, int width, int height, String label) {
        Point2D position = new Point2D.Double(x, y);

        //Nodo rectangular con colores por defecto
        return new NodeShape(label, position, width, height, FONT, STROKE, false);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    	setBackground(Color.WHITE);
        setOpaque(true);
        
        //jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1 = new JPanel();
        jPanel1.setBorder(BorderFactory.createTitledBorder("Control: Heap"));
        
        Icon addIcon = new ImageIcon(getClass().getClassLoader().getResource("Button-Add-icon.png").getPath());
        insertBtn = new JButton("Insertar", addIcon);
        insertBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        insertBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        insertBtn.setToolTipText("Insertar elemento en el heap");
        insertBtn.setMargin(new java.awt.Insets(2, 5, 2, 5));
        insertBtn.setMnemonic(KeyEvent.VK_I);
        insertBtn.putClientProperty("JComponent.sizeVariant", "small");
        insertBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertBtnActionPerformed(evt);
            }
        });
        
        Icon deleteIcon = new ImageIcon(getClass().getClassLoader().getResource("Button-Delete-icon.png").getPath());
        deleteBtn = new JButton("Eliminar", deleteIcon);
        deleteBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        deleteBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        deleteBtn.setMnemonic(KeyEvent.VK_E);
        deleteBtn.setToolTipText("Eliminar la raíz del heap");
        deleteBtn.setMargin(new java.awt.Insets(2, 5, 2, 5));
        deleteBtn.putClientProperty("JComponent.sizeVariant", "small");
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });
        
        jLabel1 = new JLabel("Ingresá un número de 0 a 999");
        integerTextField = new JTextField();
        integerTextField.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        integerTextField.setDocument(new JTextFieldLimit(3));
        integerTextField.putClientProperty("JComponent.sizeVariant", "large");
        
        Icon randomIcon = new ImageIcon(getClass().getClassLoader().getResource("Button-Help-icon.png").getPath());
        randomBtn = new JButton("Random", randomIcon);
        randomBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        randomBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        randomBtn.setToolTipText("Insertar elemento aleatorio");
        randomBtn.setMargin(new java.awt.Insets(2, 5, 2, 5));
        randomBtn.putClientProperty("JComponent.sizeVariant", "small");
        randomBtn.setMnemonic(KeyEvent.VK_R);
        randomBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                randomBtnActionPerformed(evt);
            }
        });
        
        Icon cleanIcon = new ImageIcon(getClass().getClassLoader().getResource("Button-Refresh-icon.png").getPath());
        cleanBtn = new JButton("Limpiar", cleanIcon);
        cleanBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        cleanBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        cleanBtn.setToolTipText("Comenzar de nuevo");
        cleanBtn.setMargin(new java.awt.Insets(2, 5, 2, 5));
        cleanBtn.putClientProperty("JComponent.sizeVariant", "small");
        cleanBtn.setMnemonic(KeyEvent.VK_L);
        cleanBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cleanBtnActionPerformed(evt);
            }
        });
        
        jLabel2 = new JLabel("Tamaño del heap", SwingConstants.RIGHT);
        heapSizeComboBox = new JComboBox();
        heapSizeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        heapSizeComboBox.setSelectedIndex(30);
        heapSizeComboBox.setKeySelectionManager(null);
        heapSizeComboBox.setToolTipText("Tamaño del heap");
        heapSizeComboBox.putClientProperty("JComponent.sizeVariant", "large");
        heapSizeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                heapSizeComboBoxActionPerformed(evt);
            }
        });
        
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(integerTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(insertBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(randomBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(cleanBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(heapSizeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(integerTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel1)
                .addComponent(insertBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(randomBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(cleanBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(heapSizeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel2))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(278, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

@SuppressWarnings("unchecked")
private void insertBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertBtnActionPerformed

    try {
        if (current == Integer.valueOf(heapSizeComboBox.getSelectedItem().toString()).intValue()) {
            return;
        }
        Integer valueToInsert = Integer.valueOf(integerTextField.getText());

        if (valueToInsert < 0 || valueToInsert > 999) {
            return;
        }
        
        prepareAnimation();
        this.setPrimitiveFinished(false);
        ((HeapController<Integer>) controller).addItem(valueToInsert);
    } catch (Exception e) {
        setEnabled(true);
    } finally {
        integerTextField.setText("");
    }
}//GEN-LAST:event_insertBtnActionPerformed

private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed

    prepareAnimation();
    this.setPrimitiveFinished(false);
    HeapController.class.cast(controller).deleteItem();
}//GEN-LAST:event_deleteBtnActionPerformed

private void randomBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_randomBtnActionPerformed
    int intGenerated = generator.nextInt(999) + 1;
    integerTextField.setText(String.valueOf(intGenerated));
    insertBtnActionPerformed(evt);
}//GEN-LAST:event_randomBtnActionPerformed

private void cleanBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cleanBtnActionPerformed
    int res = JOptionPane.showConfirmDialog(null, "Se eliminarán todos los elementos.\n¿Continuar?",
            "Limpiar", JOptionPane.YES_NO_OPTION);

    if (res == 0) {
        clearHeap();
    }
}//GEN-LAST:event_cleanBtnActionPerformed

private void heapSizeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_heapSizeComboBoxActionPerformed
    createHeap();
}//GEN-LAST:event_heapSizeComboBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cleanBtn;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JComboBox heapSizeComboBox;
    private javax.swing.JButton insertBtn;
    private javax.swing.JTextField integerTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton randomBtn;
    // End of variables declaration//GEN-END:variables

    @Override
    public void itemAdded(T item) {
        this.addAnimationToQueue(new EndAnimation<T>(this, "Fin de inserción del elemento " + item.toString() + "\n"));
        current++;
    }

    @Override
    public void itemDeleted(T item) {
        this.addAnimationToQueue(new EndAnimation<T>(this, "Fin de eliminación del elemento " + item.toString() + "\n"));
        current--;
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
        setEnabledButtons(false);

        swapCount = 0;

        labels.add(createLabel(String.valueOf(item)));
        nodes.add(createNode(String.valueOf(item)));

        this.addAnimationToQueue(new InsertItemHeapAnimation<T>(this, item));

        // Si no es el primer elemento, agregar una flecha
        if (current != 0) {
            int parent = getParentIndex(current);

            Point2D parentPosition = nodes.get(parent).getPosition();
            Point2D currentPosition = nodes.get(current).getPosition();

            arrows.add(createArrow(parentPosition, currentPosition));
        }
        
        rerender();
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

    @Override
    public void deletingItem(T item) {
        if (item == null) {
            return;
        }

        setEnabledButtons(false);
        swapCount = 0;

        labels.get(0).setTitle("");

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
        } finally {
        	rerender();
        }
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
		// TODO Implement!!
		
	}
}
