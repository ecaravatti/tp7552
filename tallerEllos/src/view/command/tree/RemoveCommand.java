package view.command.tree;

import view.collection.tree.BSTHeightBalancedView;
import view.collection.tree.BSTNodeView;
import view.command.common.Command;

public class RemoveCommand implements Command {
    private BSTHeightBalancedView bstView;
    private BSTNodeView node;

    public RemoveCommand(BSTHeightBalancedView bstView, BSTNodeView node) {
        this.bstView = bstView;
        this.node = node;
    }

    @Override
    public boolean canExecute() {
        return true;
    }

    @Override
    public void execute() {
        BSTNodeView parent = node.getParent();
        bstView.saveState(node);

        bstView.setChangeLocation(false);

        // Saco las flechas relacionadas con el nodo a borrar
        node.setParent(null);
        bstView.setNodeRemoved(node);

        BSTNodeView child = null;
        if (node.left != null) {
            child = node.left;
            child.setParent(parent);
        }

        if (node.right != null) {
            child = node.right;
            child.setParent(parent);
        }

        BSTNodeView last = bstView.getLastPolled();

        // El ultimo visitado es el que se quiere eliminar. Lo saco
        bstView.getNodesVisited().pollLast();

        if (last == null) {
            // El eliminado tiene un solo sucesor
            // Lo cambio por su hijo
            bstView.getNodesVisited().addLast(child);
        }
        else {
            // Hubo una rotacion, asi que el eliminado esta en el medio

            parent = bstView.getLastPolled();
            if (parent != null) {
                if (parent.left == node) {
                    parent.left = node.left;
                    node.left = null;
                }

                if (parent.right == node) {
                    parent.right = node.right;
                    node.right = null;
                }
            }
        }
        
        bstView.setFinalPositions();
        bstView.setModeDelete(false);
    }
}  
