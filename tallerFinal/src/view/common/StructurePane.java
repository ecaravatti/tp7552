package view.common;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class StructurePane<T extends JPanel & InteractivelyControlled> extends
		JSplitPane {

	private static final long serialVersionUID = 1L;

	private JSplitPane verticalSplitPane;

	public StructurePane(T strucurePanel) {
		super(JSplitPane.HORIZONTAL_SPLIT);

		PseudoCodePane pseudoCodePane = new PseudoCodePane();
		strucurePanel.getController().setPseudoCodePane(pseudoCodePane);

		OperationsLogPane operationsLogPane = new OperationsLogPane();
		strucurePanel.getController().setOperationsLogPane(operationsLogPane);

		verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		verticalSplitPane.setDividerLocation(0.5);
		verticalSplitPane.setTopComponent(pseudoCodePane);
		verticalSplitPane.setBottomComponent(operationsLogPane);

		setLeftComponent(strucurePanel);
		setDividerLocation(0.7);
		setRightComponent(verticalSplitPane);
	}

	public void update() {
		setDividerLocation(0.7);
		verticalSplitPane.setDividerLocation(0.5);
	}
}
