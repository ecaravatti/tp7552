package controller;

import javax.swing.JOptionPane;

import model.collection.tree.BinarySearchTree;
import model.exception.tree.BSTEmptyTreeException;
import model.exception.tree.BSTKeyFoundException;
import model.exception.tree.BSTKeyNotFoundException;
import view.collection.tree.BSTPanel;
import view.collection.tree.BSTTraversePrimitives;
import view.collection.tree.BinarySearchTreeView;

/**
 *
 */
public abstract class BSTController extends InteractiveController {

    private static final int MAX_VALUE = 999;

    protected BinarySearchTree<Integer> tree;
    protected BinarySearchTreeView view;
    protected BSTPanel panel;
    private boolean running;

    public BSTController(BinarySearchTree<Integer> tree, BSTPanel panel) {
        super(panel.getView());
        this.tree = tree;
        this.panel = panel;
        this.view = panel.getView();
        this.tree.addListener(view);
        this.running = false;
        panel.addController(this);
    }

    public abstract void changeParameter(int parameter);
    
    public void clear() {
        int res = JOptionPane.showConfirmDialog(null, "Se eliminarán todos los nodos.\n¿Continuar?",
                "Limpiar", JOptionPane.YES_NO_OPTION);

        if (res == 0) {
            tree.clear();
            view.clear();
        }
    }

    public void insertRandom() {
        Integer value = new Double(Math.random() * (MAX_VALUE + 1)).intValue();
        insert(value);
    }

    private Integer readValue(String text) {
        Integer value = null;
        try {
            value = Integer.valueOf(text);

            if (value > MAX_VALUE) {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException e) {
            showLogMessage("ERROR: El valor ingresado (" + text + ") debe ser un Número Entero entre 0 y " + MAX_VALUE + ".");
            return null;
        }
        return value;
    }

    public synchronized void insert(String text) {
        Integer value = readValue(text);
        if (value == null) {
            return;
        }
        insert(value);
    }

    public synchronized void insert(Integer value) {
        this.panel.getButtonsPanel().enableComponents(false);
        //this.setRunning(true);
        try {
            showLogMessage("** Insertando nodo: " + value.toString());
            this.view.prepareAction(false);

            String primitiveCode = getInsertPrimitiveCode();
            showPrimitiveCode(primitiveCode);

            tree.insert(value);

        } catch (BSTKeyFoundException ex) {
            //Logger.getLogger(BSTHeightBalancedController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public abstract String getInsertPrimitiveCode();

    public void remove(String text) {
        Integer value = readValue(text);
        if (value == null) {
            return;
        }

        this.panel.getButtonsPanel().enableComponents(false);
        try {
            this.setRunning(true);
            showLogMessage("** Eliminando nodo: " + value.toString());
            this.view.prepareAction(true);

            String primitiveCode = getDeletePrimitiveCode();
            showPrimitiveCode(primitiveCode);

            tree.delete(value);

        } catch (BSTKeyNotFoundException ex) {
            //Logger.getLogger(BSTHeightBalancedController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BSTEmptyTreeException ex) {
        	
        }
    }
    
    public abstract String getDeletePrimitiveCode();

    public void traversePreOrder() {
        showLogMessage("** Recorriendo en Pre-Order");
        showPrimitiveCode(BSTTraversePrimitives.preorder.getCode());
        this.panel.getButtonsPanel().enableComponents(false);
        view.prepareAction(true);
        tree.traversePreOrder();
    }

    public void traverseInOrder() {
        showLogMessage("** Recorriendo en In-Order");
        showPrimitiveCode(BSTTraversePrimitives.inorder.getCode());
        this.panel.getButtonsPanel().enableComponents(false);
        view.prepareAction(true);
        tree.traverseInOrder();
    }

    public void traversePostOrder() {
        showLogMessage("** Recorriendo en Post-Order");
        showPrimitiveCode(BSTTraversePrimitives.postorder.getCode());
        this.panel.getButtonsPanel().enableComponents(false);
        view.prepareAction(true);
        tree.traversePostOrder();
    }

    /**
     * Este metodo es llamado cuando se finaliza la ejecucion de una primitiva.
     */
    @Override
    public void primitiveFinished() {
        this.panel.getButtonsPanel().enableComponents(true);
        setRunning(false);
        view.setStatistics(tree.getHeight(), tree.getSize());
    }

    public synchronized boolean running() {
        return running;
    }

    public synchronized void setRunning(boolean running) {
        this.running = running;
    }
}
