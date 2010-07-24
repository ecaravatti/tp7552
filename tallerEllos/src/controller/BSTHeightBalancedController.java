package controller;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import model.collection.tree.BSTHeightBalanced;
import model.collection.tree.BinarySearchTree;
import model.exception.tree.BSTEmptyTreeException;
import model.exception.tree.BSTKeyFoundException;
import model.exception.tree.BSTKeyNotFoundException;
import view.collection.tree.BSTHeightBalancedPrimitives;
import view.collection.tree.BSTHeightBalancedView;
import view.collection.tree.BSTPanel;
import view.collection.tree.BSTTraversePrimitives;
import view.collection.tree.BSTWeightBalancedPrimitives;

/**
 *
 */
public class BSTHeightBalancedController extends InteractiveController {

    private static final int MAX_VALUE = 999;

    //private BSTHeightBalanced<Integer> tree;
    private BinarySearchTree<Integer> tree;
    private BSTHeightBalancedView view;
    private BSTPanel panel;
    private boolean running;

//    public BSTHeightBalancedController(BSTHeightBalanced<Integer> tree, BSTHeightBalancedView view) {
    public BSTHeightBalancedController(BinarySearchTree<Integer> tree, BSTPanel panel, JTextArea operationsLog) {
        super(panel.getView(), operationsLog);
        this.tree = tree;
        this.panel = panel;
        this.view = panel.getView();
        this.tree.addListener(view);
        this.running = false;
    }

    public void changeParameter(int parameter) {
        int res = JOptionPane.showConfirmDialog(null, "Al cambiar el parámetro del árbol, se eliminarán todos sus nodos.\n¿Continuar?",
                "Cambiar parámetro", JOptionPane.YES_NO_OPTION);

        if (res == 0) {
            tree.clear();
            tree.removeListener(view);
            view.clear();

            tree = new BSTHeightBalanced<Integer>(parameter);
            tree.addListener(view);

            showLogMessage("Se cambió el parámetro (máxima altura) del árbol a: " + parameter);
        }
    }

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

    /*public synchronized void insert(String text) {

    if (!create){
    if (gen == null){
    //new TreeGenerator(this, TreeGenerator.LEFT).start();

    //Secuencias con dobles rotaciones
    //new TreeGenerator(this, 3).start();
    new TreeGenerator(this, 4).start();
    this.setRunning(false);
    create = true;
    }
    return;
    }
    else{
    try {
    showLogMessage("** Insertando nodo: " + text.toString());
    this.panel.getButtonsPanel().enableComponents(false);
    this.setRunning(true);
    this.view.prepareAction(false);
    tree.insert( new Integer(text) );

    } catch (NumberFormatException e) {
    e.printStackTrace();
    } catch (BSTKeyFoundException e) {
    e.printStackTrace();
    }
    }
    }*/
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

            String primitiveCode = this.tree.getClass().equals(BSTHeightBalanced.class) ? BSTHeightBalancedPrimitives.insert.getCode() : BSTWeightBalancedPrimitives.insert.getCode();
            showPrimitiveCode(primitiveCode);

            tree.insert(value);

        } catch (BSTKeyFoundException ex) {
            //Logger.getLogger(BSTHeightBalancedController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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

            String primitiveCode = this.tree.getClass().equals(BSTHeightBalanced.class) ? BSTHeightBalancedPrimitives.delete.getCode() : BSTWeightBalancedPrimitives.delete.getCode();
            showPrimitiveCode(primitiveCode);

            tree.delete(value);

        } catch (BSTKeyNotFoundException ex) {
            //Logger.getLogger(BSTHeightBalancedController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BSTEmptyTreeException ex) {
        	
        }
    }

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
