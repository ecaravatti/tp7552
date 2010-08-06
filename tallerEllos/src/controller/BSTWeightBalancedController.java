package controller;

import javax.swing.JOptionPane;

import model.collection.tree.BSTWeightBalanced;
import view.collection.tree.BSTPanel;
import view.collection.tree.BSTWeightBalancedPrimitives;

public class BSTWeightBalancedController extends BSTController {

	public BSTWeightBalancedController(BSTWeightBalanced<Integer> tree, BSTPanel panel) {
		super(tree, panel);
	}
	
    public void changeWeight(int weight) {
    	int res = JOptionPane.OK_OPTION;
    	if (!isTreeEmpty()) {
    		res = JOptionPane.showConfirmDialog(null, "Al cambiar el alpha del árbol, se eliminarán todos sus nodos.\n¿Continuar?",
                "Cambiar Alpha", JOptionPane.YES_NO_OPTION);
    	}

        if (res == JOptionPane.OK_OPTION) {
            tree.clear();
            tree.removeListener(view);
            view.clear();

            tree = new BSTWeightBalanced<Integer>(weight);
            tree.addListener(view);

            showLogMessage("Se cambió el alpha del árbol a: " + weight);
        } else {
        	panel.getButtonsPanel().setParameter(((BSTWeightBalanced<Integer>)tree).getAlpha());
        }
    }
    
    @Override
    public String getInsertPrimitiveCode() {
    	return BSTWeightBalancedPrimitives.insert.getCode();
    }
    
    @Override
    public String getDeletePrimitiveCode() {
    	return BSTWeightBalancedPrimitives.delete.getCode();
    }
    
    @Override
    public void changeParameter(int parameter) {
    	changeWeight(parameter);
    }
}
