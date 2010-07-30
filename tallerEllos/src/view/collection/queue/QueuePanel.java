package view.collection.queue;

import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import view.common.InteractivelyControlled;

import controller.QueueController;

/**
 *
 */
public class QueuePanel<T> extends javax.swing.JPanel implements InteractivelyControlled {

	private static final long serialVersionUID = 1L;
	
	private QueueView<T> view;

	private QueueController<Integer> controller;
	
    public QueuePanel(QueueView<T> view) {
    	this.view = view;
        initComponents();
    }

    public void addController(QueueController<Integer> controller) {
    	this.controller = controller;
        this.view.addController(controller);
        this.buttonsPanel.addController(controller);
    }

    public QueueView<T> getView() {
        return view;
    }

    public QueueButtonsPanel getButtonsPanel() {
        return buttonsPanel;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        buttonsPanel = new QueueButtonsPanel();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(buttonsPanel);
        add(new JScrollPane(view));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private QueueButtonsPanel buttonsPanel;
    // End of variables declaration//GEN-END:variables
    
    @Override
    public QueueController<Integer> getController() {
    	return controller;
    }
}
