package dataStructure;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
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
import view.collection.heap.HeapPanel;
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
import view.common.StructurePane;
import controller.BSTHeightBalancedController;
import controller.BSTWeightBalancedController;
import controller.HeapController;
import controller.InteractiveController;
import controller.QueueController;
import controller.StackController;
import controller.TrieController;
import dataStructure.doc.NewHelpPanel;

/**
 *
 */
public class DataStructureApplet extends JApplet implements ComponentListener {
	private static final long serialVersionUID = -4834432122881887095L;

	public final static Font DEF_FONT = new Font("Courier", Font.PLAIN, 12);
	public final static String DEF_LAF = "Nimbus";

	public static Image HELP_IMAGE;
	public static Image BACK_IMAGE;
	public static Image HEADER_IMAGE;

	public static Image BUTTON_ADD_IMAGE;
	public static Image BUTTON_DELETE_IMAGE;
	public static Image BUTTON_RANDOM_IMAGE;
	public static Image BUTTON_REFRESH_IMAGE;
	public static Image BUTTON_TRAVERSE_IMAGE;

	public static Image BUTTON_PLAY_IMAGE;
	public static Image BUTTON_PAUSE_IMAGE;
	public static Image BUTTON_FAST_FORWARD_IMAGE;
	public static Image BUTTON_REWIND_IMAGE;

	/**
	 * Componentes Generales
	 */
	private JTabbedPane tabbedPane;
	private JPanel mainPanel;
	private JPanel bottomPanel;
	private InteractivePanel interactivePanel;
	private JPanel header;
	private Boolean helpVisible = Boolean.FALSE;

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
	private HeapPanel<Integer> heapPanel;
	private Heap<Integer> heap;
	private HeapView<Integer> heapView;
	private HeapController<Integer> heapController;

	private List<InteractiveController> controllers;

	/**
	 * Initialization method that will be called after the applet is loaded into
	 * the browser.
	 */
	@Override
	public void init() {
		initImages();
		controllers = new ArrayList<InteractiveController>();
		interactivePanel = null;

		UIManager.put("control", new Color(220, 224, 235));
		UIManager.put("textForeground", new Color(59, 59, 59));

		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if (DEF_LAF.equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (UnsupportedLookAndFeelException e) {
			// Si Nimbus no está instalado, se utiliza el default Look and Feel
			// (Metal)
		} catch (Exception e) {
			// No debe ocurrir.
		}

		/**
		 * Header y logo
		 */
		JLabel headerLabel = new JLabel(new ImageIcon(HEADER_IMAGE),
				SwingConstants.CENTER);
		headerLabel.setBackground(new Color(220, 224, 235));

		header = new JPanel();
		header.add(Box.createHorizontalStrut(10));
		header.setLayout(new BoxLayout(header, BoxLayout.LINE_AXIS));
		header.setBackground(new Color(220, 224, 235));
		header.add(headerLabel);

		final NewHelpPanel helpPanel = new NewHelpPanel();
		final JButton helpButton = new JButton(new ImageIcon(HELP_IMAGE));
		helpButton.setToolTipText("Mostrar la Ayuda");
		helpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				helpVisible = !helpVisible;
				if (helpVisible) {
					mainPanel.remove(tabbedPane);
					mainPanel.remove(bottomPanel);
					mainPanel.add(helpPanel);
				} else {
					mainPanel.remove(helpPanel);
					mainPanel.add(tabbedPane);
					mainPanel.add(bottomPanel, BorderLayout.SOUTH);
				}

				// Update the button text and icon
				helpButton.setIcon(helpVisible ? new ImageIcon(BACK_IMAGE)
						: new ImageIcon(HELP_IMAGE));
				// helpButton.setText(helpVisible ? "Volver" : "Ayuda");
				helpButton
						.setToolTipText(helpVisible ? "Volver a la aplicación"
								: "Mostrar la ayuda");

				// Repaint the whole applet
				getRootPane().revalidate();
				getRootPane().repaint();
			}
		});
		header.add(Box.createHorizontalGlue());
		// helpButton.setAlignmentY(BOTTOM_ALIGNMENT);
		header.add(helpButton);
		header.add(Box.createHorizontalStrut(20));

		/**
		 * Componentes del Trie
		 */
		trie = new Trie<String>();
		trieView = new TrieView();
		triePanel = new TriePanel(trieView);
		trieController = new TrieController(trie, triePanel);
		triePanel.getButtonsPanel().setEnabledButtons(true);
		StructurePane<TriePanel> trieStructurePanel = new StructurePane<TriePanel>(
				triePanel);

		/**
		 * Componentes de BST
		 */
		treeHeightBalanced = new BSTHeightBalanced<Integer>(1);
		treeHeightBalancedView = new BinarySearchTreeView();
		treeHeightPanel = new BSTPanel(treeHeightBalancedView, true,
				"Altura máxima");
		treeHeightBalancedController = new BSTHeightBalancedController(
				treeHeightBalanced, treeHeightPanel);
		StructurePane<BSTPanel> treeHeightBalancedStructurePanel = new StructurePane<BSTPanel>(
				treeHeightPanel);
		treeHeightPanel.getButtonsPanel().enableComponents(true);

		treeWeightBalanced = new BSTWeightBalanced<Integer>(1);
		treeWeightBalancedView = new BinarySearchTreeView();
		treeWeightPanel = new BSTPanel(treeWeightBalancedView, false, "Alpha");
		treeWeightBalancedController = new BSTWeightBalancedController(
				treeWeightBalanced, treeWeightPanel);
		StructurePane<BSTPanel> treeWeightBalancedStructurePanel = new StructurePane<BSTPanel>(
				treeWeightPanel);
		treeWeightPanel.getButtonsPanel().enableComponents(true);

		/**
		 * Componentes de Queue.
		 */
		queue = new ArrayListQueueImpl<Integer>();
		queueView = new QueueView<Integer>();
		queuePanel = new QueuePanel<Integer>(queueView);
		queueController = new QueueController<Integer>(queue, queuePanel);
		queuePanel.addController(queueController);
		queuePanel.getButtonsPanel().enableComponents(true);
		StructurePane<QueuePanel<Integer>> queueStructurePanel = new StructurePane<QueuePanel<Integer>>(
				queuePanel);
		SwingUtilities.updateComponentTreeUI(queuePanel);

		/**
		 * Componentes de Stack.
		 */
		stack = new ArrayListStackImpl<Integer>();
		stackView = new StackView<Integer>();
		stackPanel = new StackPanel<Integer>(stackView);
		stackController = new StackController<Integer>(stack, stackPanel);
		stackPanel.addController(stackController);
		StructurePane<StackPanel<Integer>> stackStructurePanel = new StructurePane<StackPanel<Integer>>(
				stackPanel);

		/**
		 * Componentes de Heap.
		 */
		heap = new Heap<Integer>();
		heapView = new HeapView<Integer>();
		heapPanel = new HeapPanel<Integer>(heapView);
		heapController = new HeapController<Integer>(heap, heapPanel);
		heapPanel.addController(heapController);
		heapPanel.getButtonsPanel().enableComponents(true);
		StructurePane<HeapPanel<Integer>> heapStructurePanel = new StructurePane<HeapPanel<Integer>>(
				heapPanel);
		SwingUtilities.updateComponentTreeUI(heapPanel);

		controllers.add(trieController);
		controllers.add(stackController);
		controllers.add(queueController);
		controllers.add(heapController);
		controllers.add(treeHeightBalancedController);
		controllers.add(treeWeightBalancedController);

		/**
		 * Componentes Generales
		 */
		tabbedPane = new JTabbedPane(SwingConstants.TOP);
		tabbedPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		tabbedPane.putClientProperty("JComponent.sizeVariant", "large");
		tabbedPane.addTab("Trie", trieStructurePanel);
		tabbedPane.addTab("Pila", stackStructurePanel);
		tabbedPane.addTab("Cola", queueStructurePanel);
		tabbedPane.addTab("Heap", heapStructurePanel);
		tabbedPane.addTab("ABB por altura", treeHeightBalancedStructurePanel);
		tabbedPane.addTab("ABB por peso", treeWeightBalancedStructurePanel);
		SwingUtilities.updateComponentTreeUI(tabbedPane);

		mainPanel = new JPanel(new BorderLayout());
		bottomPanel = new JPanel(new BorderLayout());
		mainPanel.add(tabbedPane);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);

		tabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent evt) {
				JTabbedPane pane = (JTabbedPane) evt.getSource();

				int selectedTabIndex = pane.getSelectedIndex();

				StructurePane.class.cast(pane.getSelectedComponent()).update();

				updateInteractivePanel(selectedTabIndex);
				bottomPanel.repaint();
			}

		});
		tabbedPane.setSelectedIndex(5);
		updateInteractivePanel(5);

		Dimension screenResolution = this.getToolkit().getScreenSize();
		this.setSize(new Double(screenResolution.getWidth()).intValue() - 15,
				new Double(screenResolution.getHeight()).intValue() - 125);
		this.add(header, BorderLayout.NORTH);
		this.add(mainPanel);
		this.addComponentListener(this);
	}

	private void updateInteractivePanel(int selectedTabIndex) {
		if (interactivePanel != null) {
			bottomPanel.remove(interactivePanel);
		}

		interactivePanel = controllers.get(selectedTabIndex)
				.getInteractivePanel();
		SwingUtilities.updateComponentTreeUI(interactivePanel);
		bottomPanel.add(interactivePanel);
		controllers.get(selectedTabIndex).setWait(Boolean.FALSE);

		for (int i = 0; i < controllers.size(); i++) {
			if (controllers.get(i) != controllers.get(selectedTabIndex)) {
				controllers.get(i).setWait(Boolean.TRUE);
			}
		}
	}

	@Override
	public void start() {
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
		StructurePane.class.cast(tabbedPane.getSelectedComponent()).update();
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

	public void initImages() {
		HELP_IMAGE = getImage(getCodeBase(), "Info-icon-big.png");
		BACK_IMAGE = getImage(getCodeBase(), "Return-icon.png");
		HEADER_IMAGE = getImage(getCodeBase(), "head-taller-small.png");

		BUTTON_ADD_IMAGE = getImage(getCodeBase(), "Add-icon-big.png");
		BUTTON_DELETE_IMAGE = getImage(getCodeBase(), "Delete-icon-big.png");
		BUTTON_RANDOM_IMAGE = getImage(getCodeBase(), "Random-icon-big.png");
		BUTTON_REFRESH_IMAGE = getImage(getCodeBase(), "Refresh-icon-big.png");
		BUTTON_TRAVERSE_IMAGE = getImage(getCodeBase(),
				"Configure-icon-big.png");

		BUTTON_PLAY_IMAGE = getImage(getCodeBase(), "Play-Enabled-icon-big.png");
		BUTTON_PAUSE_IMAGE = getImage(getCodeBase(),
				"Play-Disabled-icon-big.png");
		BUTTON_FAST_FORWARD_IMAGE = getImage(getCodeBase(),
				"Forward-icon-big.png");
		BUTTON_REWIND_IMAGE = getImage(getCodeBase(), "Back-icon-big.png");
	}
}
