package controller;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import model.collection.tree.BSTHeightBalanced;
import view.collection.tree.BSTHeightBalancedPrimitives;
import view.collection.tree.BSTPanel;

public class BSTHeightBalancedController extends BSTController {

	public BSTHeightBalancedController(BSTHeightBalanced<Integer> tree, BSTPanel panel, JTextArea operationsLog) {
		super(tree, panel, operationsLog);
	}
	
    public void changeMaxHeight(int maxHeight) {
        int res = JOptionPane.showConfirmDialog(null, "Al cambiar la altura máxima del árbol, se eliminarán todos sus nodos.\n¿Continuar?",
                "Cambiar Altura Máxima", JOptionPane.YES_NO_OPTION);

        if (res == 0) {
            tree.clear();
            tree.removeListener(view);
            view.clear();

            tree = new BSTHeightBalanced<Integer>(maxHeight);
            tree.addListener(view);

            showLogMessage("Se cambió la altura máxima del árbol a: " + maxHeight);
        } else {
        	panel.getButtonsPanel().setParameter(((BSTHeightBalanced<Integer>)tree).getMaxHeight());
        }
    }
    
    @Override
    public String getInsertPrimitiveCode() {
    	return BSTHeightBalancedPrimitives.insert.getCode();
    }
    
    @Override
    public String getDeletePrimitiveCode() {
    	return BSTHeightBalancedPrimitives.delete.getCode();
    }
    
    @Override
    public void changeParameter(int parameter) {
    	changeMaxHeight(parameter);
    }
}
