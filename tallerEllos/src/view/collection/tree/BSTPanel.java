package view.collection.tree;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;

import controller.BSTController;

/**
 *
 */
public class BSTPanel extends javax.swing.JPanel {

	private static final long serialVersionUID = 1L;
	
    private BinarySearchTreeView view;

    /** Creates new form BSTPanel */
    public BSTPanel(BinarySearchTreeView view, boolean withChangeParameter, String parameterName) {
        initComponents();
		buttonsPanel.setWithChangeParameter(withChangeParameter, parameterName);
        this.view = view;
        this.add(new JScrollPane(this.view), BorderLayout.CENTER);
    }

    public void addController(BSTController controller) {
        this.view.addController(controller);
        this.buttonsPanel.addController(controller);
    }

    public BinarySearchTreeView getView() {
        return view;
    }

    public BSTButtonsPanel getButtonsPanel() {
        return buttonsPanel;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonsPanel = new view.collection.tree.BSTButtonsPanel();

        setBackground(new java.awt.Color(255, 255, 255));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        setLayout(new java.awt.BorderLayout());
        add(buttonsPanel, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        view.componentResized(evt);
    }//GEN-LAST:event_formComponentResized


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private view.collection.tree.BSTButtonsPanel buttonsPanel;
    // End of variables declaration//GEN-END:variables

}
