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
import view.collection.tree.BSTHeightBalancedView;
import view.collection.tree.BSTPanel;
import view.collection.trie.MainPanel;
import view.collection.trie.TrieView;
import view.common.InteractivePanel;
import ar.uba.fi.structuresAnimator.doc.HelpPanel;
import controller.BSTHeightBalancedController;
import controller.HeapController;
import controller.InteractiveController;
import controller.QueueController;
import controller.StackController;
import controller.TrieController;

/**
 *
 * @author Exe Curia
 */
@SuppressWarnings("serial")
public class StructuresAnimator extends JApplet implements ComponentListener {
    private final static Font DEF_FONT = new Font("Courier", Font.PLAIN, 12);

    /**
     * Componentes Generales
     */
    private JTabbedPane tabbedPane;
    private JSplitPane splitPane;
    private JSplitPane horizontalSplitPane;
    private JScrollPane scrollPane;
    private JScrollPane codePane;
    private JTextArea operationsLog;
    private JTextArea primitivesCodeArea;
    private JPanel bottomPanel;
    private InteractivePanel interactivePanel;
    /**
     * Componentes del Trie
     */
    private MainPanel mainPanel;
    private Trie<String> trie;
    private TrieView trieView;
    private TrieController trieController;
    /**
     * Componentes del BST
     */
    private BSTPanel treeHeightPanel;
    private BSTHeightBalanced<Integer> treeHeightBalanced;
    private BSTHeightBalancedView treeHeightBalancedView;
    private BSTHeightBalancedController treeHeightBalancedController;
    private BSTPanel treeWeightPanel;
    private BSTWeightBalanced<Integer> treeWeightBalanced;
    private BSTHeightBalancedView treeWeightBalancedView;
    private BSTHeightBalancedController treeWeightBalancedController;
    /**
     * Componentes de Queue.
     */
    private QueuePanel queuePanel;
    private QueueObservable<?> queue;
    private QueueView<?> queueView;
    private QueueController<?> queueController;
    /**
     * Componentes de Stack.
     */
    private StackPanel stackPanel;
    private StackObservable<?> stack;
    private StackView<?> stackView;
    private StackController<?> stackController;
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
        mainPanel = new MainPanel(trieView);
        trieController = new TrieController(trie, mainPanel, operationsLog);
        trieController.setPrimitivesCodeArea(primitivesCodeArea);
        this.mainPanel.addController(trieController);

        /**
         * Componentes de BST
         */
        treeHeightBalanced = new BSTHeightBalanced<Integer>(1);
        treeHeightBalancedView = new BSTHeightBalancedView();
        treeHeightPanel = new BSTPanel(treeHeightBalancedView, true);
        treeHeightBalancedController = new BSTHeightBalancedController(treeHeightBalanced, treeHeightPanel, operationsLog);
        treeHeightBalancedController.setPrimitivesCodeArea(primitivesCodeArea);
        treeHeightPanel.addController(treeHeightBalancedController);

        treeWeightBalanced = new BSTWeightBalanced<Integer>(1);
        treeWeightBalancedView = new BSTHeightBalancedView();
        treeWeightPanel = new BSTPanel(treeWeightBalancedView, false);
        treeWeightBalancedController = new BSTHeightBalancedController(treeWeightBalanced, treeWeightPanel, operationsLog);
        treeWeightBalancedController.setPrimitivesCodeArea(primitivesCodeArea);
        treeWeightPanel.addController(treeWeightBalancedController);

        /**
         * Componentes de Queue.
         */
        queue = new ArrayListQueueImpl();
        queueView = new QueueView();
        queuePanel = new QueuePanel(queueView);
        queueController = new QueueController(queue, queuePanel, operationsLog);
        queueController.setPrimitivesCodeArea(primitivesCodeArea);
        queuePanel.addController(queueController);

        /**
         * Componentes de Stack.
         */
        stack = new ArrayListStackImpl();
        stackView = new StackView();
        stackPanel = new StackPanel(stackView);
        stackController = new StackController(stack, stackPanel, operationsLog);
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
        tabbedPane.addTab("Trie", mainPanel);
        tabbedPane.addTab("Stack", stackPanel);
        tabbedPane.addTab("Queue", queuePanel);
        tabbedPane.addTab("Heap", heapView);
        tabbedPane.addTab("Arbol Binario de Busqueda Balanceado por Altura", treeHeightPanel);
        tabbedPane.addTab("Arbol Binario de Busqueda Balanceado por Peso", treeWeightPanel);

        operationsLog.setBorder(BorderFactory.createTitledBorder("Operaciones realizadas"));
        operationsLog.setEditable(false);

        scrollPane = new JScrollPane(operationsLog, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        primitivesCodeArea.setBorder(BorderFactory.createTitledBorder("Pseudocódigo"));
        primitivesCodeArea.setEditable(false);
        primitivesCodeArea.setFont(DEF_FONT);

        codePane = new JScrollPane(primitivesCodeArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        horizontalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        horizontalSplitPane.setLeftComponent(scrollPane);
        horizontalSplitPane.setRightComponent(codePane);

        bottomPanel = new JPanel(new BorderLayout());
        //bottomPanel.add(interactivePanel, BorderLayout.SOUTH);
        //bottomPanel.add(scrollPane, BorderLayout.CENTER);
        //bottomPanel.add(codePane, BorderLayout.EAST);
        bottomPanel.add(horizontalSplitPane);

        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(tabbedPane);
        splitPane.setBottomComponent(bottomPanel);

        tabbedPane.addChangeListener(new ChangeListener() {

          @Override
          public void stateChanged(ChangeEvent evt) {
            JTabbedPane pane = (JTabbedPane)evt.getSource();

            int sel = pane.getSelectedIndex();

            if (sel == 0) return;

            if (interactivePanel != null)
              bottomPanel.remove(interactivePanel);

            interactivePanel = controllers.get(sel - 1).getInteractivePanel();
            bottomPanel.add(interactivePanel, BorderLayout.SOUTH);
            controllers.get(sel - 1).setWait(false);

            for (int i = 0; i < controllers.size(); i++){
              if ( controllers.get(i) != controllers.get(sel - 1) )
                controllers.get(i).setWait(true);
            }
            bottomPanel.repaint();
          }
        });

        this.setSize(800, 600);
        this.add(splitPane);
        this.addComponentListener(this);
    }

    @Override
    public void start() {
        horizontalSplitPane.setDividerLocation(0.5);
        splitPane.setDividerLocation(0.7);
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
        horizontalSplitPane.setDividerLocation(0.5);
        splitPane.setDividerLocation(0.7);
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
