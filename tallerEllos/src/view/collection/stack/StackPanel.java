package view.collection.stack;

import javax.swing.BoxLayout;
import javax.swing.JScrollPane;

import view.common.InteractivelyControlled;
import controller.StackController;

/**
 *
 */
public class StackPanel<T> extends javax.swing.JPanel implements InteractivelyControlled {

	private static final long serialVersionUID = 1L;
	private view.collection.stack.StackButtonsPanel buttonsPanel;
	private javax.swing.JPanel jPanelCapacity;
	private StackController<Integer> controller;
	
    private StackView<T> view;

    public StackPanel(StackView<T> view) {
    	this.view = view;
        initComponents();
    }

    public void addController(StackController<Integer> controller) {
        this.view.addController(controller);
        this.buttonsPanel.addController(controller);
        this.controller = controller;
    }

    public StackView<T> getView() {
        return view;
    }

    public StackButtonsPanel getButtonsPanel() {
        return buttonsPanel;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    	buttonsPanel = new StackButtonsPanel();
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.add(buttonsPanel);
        this.add(new JScrollPane(view, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public StackController<Integer> getController() {
    	return controller;
    }
}
