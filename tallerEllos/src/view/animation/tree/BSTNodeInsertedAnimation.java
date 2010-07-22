package view.animation.tree;

import java.util.ArrayList;

import view.animation.common.AbstractUndoAnimationSteps;
import view.collection.tree.*;
import view.command.common.Command;
import view.command.common.MakeVisibleCommand;
import view.command.common.ShowMessageCommand;
import view.command.common.StepFinishedCommand;
import view.command.tree.NodeInsertedCommand;
import view.command.tree.RestoreBSTCommand;

/**
 *
 * @author Exe Curia
 */
public class BSTNodeInsertedAnimation extends AbstractUndoAnimationSteps {

    private BSTHeightBalancedView bstView;
    private BSTNodeView node;
    private int side;

    public BSTNodeInsertedAnimation(BSTHeightBalancedView bstView, BSTNodeView node, int side) {
        this.bstView = bstView;
        this.node = node;
        this.side = side;
    }

    @Override
    protected void initializeListUndoSteps() {
        this.undoSteps = new ArrayList<Command>();
        undoSteps.add(new ShowMessageCommand(bstView, "Deshaciendo insercion del nodo"));
        this.undoSteps.add( new RestoreBSTCommand(bstView) );
        this.undoSteps.add(new StepFinishedCommand(bstView, false, this));
    }

    //@Override
    protected void initializeListSteps2() {
        steps = new ArrayList<Command>();

        if (node == null) {
            // Se cambio el nodo hijo: sacarlo de la lista de visitados
            
            /*steps.add(new Command() {

                @Override
                public boolean canExecute() {
                    return true;
                }

                @Override
                public void execute() {*/

            if (side == 0) {
                /*if (bstView.getNodeRemoved() != null) {
                    bstView.setLastPolled(bstView.getNodesVisited().pollLast());

                    BSTNodeView parent = bstView.getNodesVisited().peekLast();

                    if (parent != null){
                        if (bstView.sideToInsert == BSTNodeView.LEFT)
                            parent.left = bstView.getLastPolled();
                        else
                            parent.right = bstView.getLastPolled();
                    }
                }*/
                if (bstView.getNodesVisited().isEmpty())
                    steps.add(new ShowMessageCommand(bstView, "========== getfirst es nulo (" + bstView.getLastPolled().getData() + ")"));

                bstView.setRoot(bstView.getNodesVisited().getFirst());
                steps.addAll(bstView.getPaintCommands());
            }
            else if ( bstView.isInserting() ) {
                bstView.setLastPolled(bstView.getNodesVisited().pollLast());
                BSTNodeView parent = bstView.getNodesVisited().peekLast();

                if (parent != null){
                    if (side == BSTNodeView.LEFT)
                        parent.left = bstView.getLastPolled();
                    else
                        parent.right = bstView.getLastPolled();
                }
            }
            else
                bstView.setInserting(true);

            if (bstView.getNodeRemoved() != null) {
                bstView.setNodeRemoved(null);
                bstView.setChangeLocation(true);
            }

/*                }
            });*/
        }
        else {
            // Se agrego un nodo hijo nuevo

            // Establezco el padre
            BSTNodeView parent = bstView.getNodesVisited().peekLast();
            node.setParent(parent);

            String s;
            switch (side) {
                case BSTNodeView.LEFT:
                    s = "como hijo izquierdo";
                    break;
                case BSTNodeView.RIGHT:
                    s = "como hijo derecho";
                    break;
                default:
                    s = "en arbol vacio";
                    break;
            }
            steps.add(new ShowMessageCommand(bstView, "Nodo (" + node.getData() + ") insertado " + s));
            /*if (parent != null) {
            steps.add(new ShowMessageCommand(bstView, "insertado parent" + parent.getData().toString()));
            
            steps.add(new StopFlashingElementViewCommand(bstView, parent));
            //parent.setVisible(true);
            steps.addAll(bstView.getPaintCommands());
            }*/
            steps.add(new MakeVisibleCommand(bstView, node));
            //steps.addAll(bstView.getPaintCommands());
        }
        steps.add(new StepFinishedCommand(bstView, false));
    }

    @Override
      /**
     * Este metodo debe convertirse en el initializeListSteps() de la animacion
     * para poder agregar el deshacer...la logica es la misma pero algunas lineas
     * de codigo (tal cual estan, señaladas a abajo con comentarios) se mueven a
     * un comando NodeInsertedCommand.
     *
     */
    protected void initializeListSteps() {
        steps = new ArrayList<Command>();
        steps.add( new NodeInsertedCommand(node, side, bstView));

        //Todas estas lineas comentadas se mueven al NodeInsertedCommand
        /*if (node == null) {
            if (side == 0) {
                if (bstView.getNodesVisited().isEmpty())
                    steps.add(new ShowMessageCommand(bstView, "========== getfirst es nulo (" + bstView.getLastPolled().getData() + ")"));

                bstView.setRoot(bstView.getNodesVisited().getFirst());
                steps.addAll(bstView.getPaintCommands());
            }
            else if ( bstView.isInserting() ) {
                bstView.setLastPolled(bstView.getNodesVisited().pollLast());
                BSTNodeView parent = bstView.getNodesVisited().peekLast();

                if (parent != null){
                    if (side == BSTNodeView.LEFT)
                        parent.left = bstView.getLastPolled();
                    else
                        parent.right = bstView.getLastPolled();
                }
            }
            else
                bstView.setInserting(true);

            if (bstView.getNodeRemoved() != null) {
                bstView.setNodeRemoved(null);
                bstView.setChangeLocation(true);
            }
        }
        else {
            // Se agrego un nodo hijo nuevo

            // Establezco el padre
            BSTNodeView parent = bstView.getNodesVisited().peekLast();
            node.setParent(parent);

        }*/
        if (node != null){
            String s;
            switch (side) {
                case BSTNodeView.LEFT:
                    s = "como hijo izquierdo";
                    break;
                case BSTNodeView.RIGHT:
                    s = "como hijo derecho";
                    break;
                default:
                    s = "en arbol vacio";
                    break;
            }
            steps.add(new ShowMessageCommand(bstView, "Nodo (" + node.getData() + ") insertado " + s));
            steps.add(new MakeVisibleCommand(bstView, node));
        }
        steps.addAll(bstView.getPaintCommands());
        steps.add(new StepFinishedCommand(bstView, false,this));
    }
}
