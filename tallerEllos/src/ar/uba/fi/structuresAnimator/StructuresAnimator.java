package ar.uba.fi.structuresAnimator;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.collection.heap.Heap;
import model.collection.queue.ArrayListQueueImpl;
import model.collection.queue.QueueObservable;
import model.collection.stack.ArrayListStackImpl;
import model.collection.stack.StackObservable;
import model.collection.tree.BSTHeightBalanced;
import model.collection.tree.BSTWeightBalanced;
import model.collection.trie.Trie;
import view.collection.heap.HeapView;
import view.collection.queue.QueuePanel;
import view.collection.queue.QueueView;
import view.collection.stack.StackPanel;
import view.collection.stack.StackView;
import view.collection.tree.BSTPanel;
import view.collection.tree.BinarySearchTreeView;
import view.collection.trie.TriePanel;
import view.collection.trie.TrieView;
import view.common.InteractivePanel;
import ar.uba.fi.structuresAnimator.doc.HelpPanel;
import controller.BSTHeightBalancedController;
import controller.BSTWeightBalancedController;
import controller.HeapController;
import controller.InteractiveController;
import controller.QueueController;
import controller.StackController;
import controller.TrieController;

/**
 *
 */
public class StructuresAnimator extends JApplet implements ComponentListener {

	private static final long serialVersionUID = 1L;

	private final static Font DEF_FONT = new Font("Courier", Font.PLAIN, 12);

    /**
     * Componentes Generales
     */
    private JTabbedPane tabbedPane;
    private JSplitPane verticalSplitPane;
    private JSplitPane horizontalSplitPane;
    private JScrollPane logPane;
    private JScrollPane codePane;
    private JTextArea operationsLog;
    private JTextArea primitivesCodeArea;
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private InteractivePanel interactivePanel;
    /**
     * Componentes del Trie
     */
    private TriePanel triePanel;
    private Trie<String> trie;
    private TrieView trieView;
    private TrieController trieController;
    /**
     * Componentes del BST
     */
    private BSTPanel treeHeightPanel;
    private BSTHeightBalanced<Integer> treeHeightBalanced;
    private BinarySearchTreeView treeHeightBalancedView;
    private BSTHeightBalancedController treeHeightBalancedController;
    private BSTPanel treeWeightPanel;
    private BSTWeightBalanced<Integer> treeWeightBalanced;
    private BinarySearchTreeView treeWeightBalancedView;
    private BSTWeightBalancedController treeWeightBalancedController;
    /**
     * Componentes de Queue.
     */
    private QueuePanel<Integer> queuePanel;
    private QueueObservable<Integer> queue;
    private QueueView<Integer> queueView;
    private QueueController<Integer> queueController;
    /**
     * Componentes de Stack.
     */
    private StackPanel<Integer> stackPanel;
    private StackObservable<Integer> stack;
    private StackView<Integer> stackView;
    private StackController<Integer> stackController;
    /**
     * Componentes de Heap.
     */
    private Heap<Integer> heap;
    private HeapView<Integer> heapView;
    private HeapController<Integer> heapController;

    private List<InteractiveController> controllers;
    /**
     * Initialization method that will be called after the applet is loaded
     * into the browser.
     */
    @Override
    public void init() {
        controllers = new ArrayList<InteractiveController>();
        operationsLog = new JTextArea();
        primitivesCodeArea = new JTextArea();
        //interactivePanel = new InteractivePanel();
        interactivePanel = null;

        /**
         * Componentes del Trie
         */
        trie = new Trie<String>();
        trieView = new TrieView();
        triePanel = new TriePanel(trieView);
        trieController = new TrieController(trie, triePanel, operationsLog);
        trieController.setPrimitivesCodeArea(primitivesCodeArea);

        /**
         * Componentes de BST
         */
        treeHeightBalanced = new BSTHeightBalanced<Integer>(1);
        treeHeightBalancedView = new BinarySearchTreeView();
        treeHeightPanel = new BSTPanel(treeHeightBalancedView, true, "Altura Máxima");
        treeHeightBalancedController = new BSTHeightBalancedController(treeHeightBalanced, treeHeightPanel, operationsLog);
        treeHeightBalancedController.setPrimitivesCodeArea(primitivesCodeArea);
        treeHeightPanel.addController(treeHeightBalancedController);

        treeWeightBalanced = new BSTWeightBalanced<Integer>(1);
        treeWeightBalancedView = new BinarySearchTreeView();
        treeWeightPanel = new BSTPanel(treeWeightBalancedView, false, "Alpha");
        treeWeightBalancedController = new BSTWeightBalancedController(treeWeightBalanced, treeWeightPanel, operationsLog);
        treeWeightBalancedController.setPrimitivesCodeArea(primitivesCodeArea);
        treeWeightPanel.addController(treeWeightBalancedController);

        /**
         * Componentes de Queue.
         */
        queue = new ArrayListQueueImpl<Integer>();
        queueView = new QueueView<Integer>();
        queuePanel = new QueuePanel<Integer>(queueView);
        queueController = new QueueController<Integer>(queue, queuePanel, operationsLog);
        queueController.setPrimitivesCodeArea(primitivesCodeArea);
        queuePanel.addController(queueController);

        /**
         * Componentes de Stack.
         */
        stack = new ArrayListStackImpl<Integer>();
        stackView = new StackView<Integer>();
        stackPanel = new StackPanel<Integer>(stackView);
        stackController = new StackController<Integer>(stack, stackPanel, operationsLog);
        stackController.setPrimitivesCodeArea(primitivesCodeArea);
        stackPanel.addController(stackController);

        /**
         * Componentes de Heap.
         */
        heap = new Heap<Integer>();
        heapView = new HeapView<Integer>();
        heapController = new HeapController<Integer>(heap, heapView, operationsLog);
        heapController.setPrimitivesCodeArea(primitivesCodeArea);
        heapView.addController(heapController);

        controllers.add(trieController);
        controllers.add(stackController);
        controllers.add(queueController);
        controllers.add(heapController);
        controllers.add(treeHeightBalancedController);
        controllers.add(treeWeightBalancedController);

        /**
         * Componentes Generales
         */
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("AYUDA", new HelpPanel());
        tabbedPane.addTab("Trie", triePanel);
        tabbedPane.addTab("Stack", stackPanel);
        tabbedPane.addTab("Queue", queuePanel);
        tabbedPane.addTab("Heap", heapView);
        tabbedPane.addTab("Arbol Binario de Busqueda Balanceado por Altura", treeHeightPanel);
        tabbedPane.addTab("Arbol Binario de Busqueda Balanceado por Peso", treeWeightPanel);

        operationsLog.setBorder(BorderFactory.createTitledBorder("Log de Operaciones"));
        operationsLog.setEditable(Boolean.FALSE);

        logPane = new JScrollPane(operationsLog, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        primitivesCodeArea.setBorder(BorderFactory.createTitledBorder("Pseudocódigo"));
        primitivesCodeArea.setEditable(Boolean.FALSE);
        primitivesCodeArea.setFont(DEF_FONT);

        codePane = new JScrollPane(primitivesCodeArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        mainPanel = new JPanel(new BorderLayout());
        bottomPanel = new JPanel(new BorderLayout());
        mainPanel.add(tabbedPane);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        verticalSplitPane.setTopComponent(codePane);
        verticalSplitPane.setBottomComponent(logPane);

        tabbedPane.addChangeListener(new ChangeListener() {

          @Override
          public void stateChanged(ChangeEvent evt) {
            JTabbedPane pane = (JTabbedPane)evt.getSource();

            int selectedTabIndex = pane.getSelectedIndex();

            if (selectedTabIndex == 0) {
            	if (interactivePanel != null) {
            		bottomPanel.remove(interactivePanel);
            	}
            	horizontalSplitPane.remove(verticalSplitPane);
            	return;
            } else if (horizontalSplitPane.getRightComponent() == null) {
            	horizontalSplitPane.add(verticalSplitPane);
            	horizontalSplitPane.setDividerLocation(0.7);
            	verticalSplitPane.setDividerLocation(0.5);
            	horizontalSplitPane.repaint();
            }

            if (interactivePanel != null) {
              bottomPanel.remove(interactivePanel);
            }

            interactivePanel = controllers.get(selectedTabIndex - 1).getInteractivePanel();
            bottomPanel.add(interactivePanel);
            controllers.get(selectedTabIndex - 1).setWait(Boolean.FALSE);

            for (int i = 0; i < controllers.size(); i++) {
              if ( controllers.get(i) != controllers.get(selectedTabIndex - 1) ) {
                controllers.get(i).setWait(Boolean.TRUE);
              }
            }
            bottomPanel.repaint();
          }
        });
        
        horizontalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        horizontalSplitPane.setLeftComponent(mainPanel);
        // El componente de la derecha es agregado cuando se selecciones un tab de una estructura.

        this.setSize(800, 600);
        this.add(horizontalSplitPane);
        this.addComponentListener(this);
    }

    @Override
    public void start() {
    	horizontalSplitPane.setDividerLocation(0.7);
    	verticalSplitPane.setDividerLocation(0.5);
        trieView.start();
        treeHeightBalancedView.start();
        treeWeightBalancedView.start();
        queueView.start();
        stackView.start();
        heapView.start();
    }

    @Override
    public void componentResized(ComponentEvent e) {
        // Para cuando se invoca desde un browser
        horizontalSplitPane.setDividerLocation(0.7);
        verticalSplitPane.setDividerLocation(0.5);
        getRootPane().revalidate();
        getRootPane().repaint();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    // TODO solo esta para hacer mas facil la depuracion
    /*public static void main(String s[]) {
        JFrame f = new JFrame("StructuresAnimator");
        f.setSize(new Dimension(1024, 800));
        f.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        JApplet applet = new StructuresAnimator();
        f.getContentPane().add("Center", applet);
        applet.init();
        f.setVisible(true);
        //f.pack();
        applet.start();
    }*/
}
