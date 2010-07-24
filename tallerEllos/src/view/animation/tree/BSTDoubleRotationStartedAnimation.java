package view.animation.tree;

import java.util.ArrayList;

import view.animation.common.AbstractUndoAnimationSteps;
import view.collection.tree.BinarySearchTreeView;
import view.collection.tree.BSTNodeView;
import view.command.common.Command;
import view.command.common.SelectElementViewCommand;
import view.command.common.ShowMessageCommand;
import view.command.common.StepFinishedCommand;
import view.command.common.StopFlashingElementViewCommand;
import view.command.tree.ChangeSelectableColorCommand;
import view.command.tree.DoubleRotationStartedCommand;
import view.command.tree.RestoreBSTCommand;
import view.command.tree.SaveStateBST;

/**
 *
 * 
 */
public class BSTDoubleRotationStartedAnimation extends AbstractUndoAnimationSteps {
    private BinarySearchTreeView bstView;
    private int side;
    private BSTNodeView node;
    
    public BSTDoubleRotationStartedAnimation(BinarySearchTreeView bstView, int side) {
        this.bstView = bstView;
        this.side = side;
        this.setRedoPause(false);
        this.setUndoPause(false);
    }

    @Override
    protected void initializeListUndoSteps() {
        undoSteps = new ArrayList<Command>();
        undoSteps.add(new ShowMessageCommand(bstView, "Deshaciendo doble rotacion nodo"));
        undoSteps.add( new RestoreBSTCommand(bstView) );
        undoSteps.add( new StopFlashingElementViewCommand(bstView, node) );
        undoSteps.add( new StepFinishedCommand(bstView, false, this) );
    }

    //@Override
    protected void initializeListSteps1() {
        steps = new ArrayList<Command>();

        BSTNodeView node = bstView.getNodesVisited().peekLast();

        if (node == null) {
            // parche
            node = bstView.getLastPolled();
            steps.add(new ShowMessageCommand(bstView, "======= peek last = null"));
            //bstView.getNodesVisited().addLast(node);
        }
        steps.add(new ShowMessageCommand(bstView, "Nodo (" + node.getData() + ") desbalanceado: necesita doble rotacion"));
        
        steps.add(new SelectElementViewCommand(bstView, bstView.getFlashingDelay(), node, BSTNodeView.DEF_COLOR_SELECTION));
        steps.addAll(bstView.getPaintCommands());
        steps.add(new ChangeSelectableColorCommand(bstView, node, BSTNodeView.DEF_COLOR_VISITED));
        steps.addAll(bstView.getPaintCommands());
        steps.add(new SelectElementViewCommand(bstView, bstView.getFlashingDelay(), node, BSTNodeView.DEF_COLOR_SELECTION));
        steps.addAll(bstView.getPaintCommands());
        steps.add(new ChangeSelectableColorCommand(bstView, node, BSTNodeView.DEF_COLOR_VISITED));

        BSTNodeView temp;
        if (side == BSTNodeView.LEFT)
            temp = node.left;
        else if (side == BSTNodeView.RIGHT)
            temp = node.right;
        else
            temp = bstView.getLastPolled();
        bstView.getNodesVisited().addLast(temp);
        //bstView.getNodesVisited().addLast(bstView.getLastPolled());
        bstView.setDobleRotation(2);
        
        /* BSTNodeView node = bstView.getNodesVisited().pollLast();
        steps.add(new ShowMessageCommand(bstView, "Nodo rotado derecha: " + node.getData().toString()));
        node = node.rotateRight(node);
        bstView.getNodesVisited().addLast(node);

        if (bstView.isDobleRotation()) {
            bstView.finishDobleRotation();
            //isDobleRotation = false;
            bstView.setParentRightChild(null);
        }*/
        steps.addAll(bstView.getPaintCommands());

        steps.add(new StepFinishedCommand(bstView, false));
    }
   
    @Override
     /**
     * Este metodo debe convertirse en el initializeListSteps() de la animacion
     * para poder agregar el deshacer...la logica es la misma pero algunas lineas
     * de codigo (tal cual estan, señaladas a abajo con comentarios) se mueven a
     * un comando DoubleRotationStartedCommand.
     *
     */
    protected void initializeListSteps() {
        steps = new ArrayList<Command>();

        //Salvo el estado para poder restaurarlo en el deshacer
        steps.add( new SaveStateBST(bstView, bstView.getNodesVisited().peekLast()) );
        //node pasa a ser atributo de la clase para el deshacer
        node = bstView.getNodesVisited().peekLast();
        if (node == null) {
            node = bstView.getLastPolled();
            steps.add(new ShowMessageCommand(bstView, "======= peek last = null"));
        }
        steps.add(new ShowMessageCommand(bstView, "Nodo (" + node.getData() + ") desbalanceado: necesita doble rotacion"));

        steps.add(new SelectElementViewCommand(bstView, bstView.getFlashingDelay(), node, BSTNodeView.DEF_COLOR_SELECTION));
        steps.addAll(bstView.getPaintCommands());
        steps.add(new ChangeSelectableColorCommand(bstView, node, BSTNodeView.DEF_COLOR_VISITED));
        steps.addAll(bstView.getPaintCommands());
        steps.add(new SelectElementViewCommand(bstView, bstView.getFlashingDelay(), node, BSTNodeView.DEF_COLOR_SELECTION));
        steps.addAll(bstView.getPaintCommands());
        steps.add(new ChangeSelectableColorCommand(bstView, node, BSTNodeView.DEF_COLOR_VISITED));

        //Todas estas lineas comentadas se mueven al DoubleRotationStartedCommand
        /*
        BSTNodeView temp;
        if (side == BSTNodeView.LEFT)
            temp = node.left;
        else if (side == BSTNodeView.RIGHT)
            temp = node.right;
        else
            temp = bstView.getLastPolled();

        bstView.getNodesVisited().addLast(temp);
        bstView.setDobleRotation(2);*/

        steps.add( new DoubleRotationStartedCommand(bstView, node, side) );
        
        steps.addAll(bstView.getPaintCommands());
        steps.add(new StepFinishedCommand(bstView, false, this));
    }

}
