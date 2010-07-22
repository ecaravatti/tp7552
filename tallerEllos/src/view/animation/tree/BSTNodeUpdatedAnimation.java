package view.animation.tree;

import java.util.ArrayList;

import view.animation.common.AbstractUndoAnimationSteps;
import view.collection.tree.BSTHeightBalancedView;
import view.collection.tree.BSTNodeView;
import view.command.common.Command;
import view.command.common.PaintCommand;
import view.command.common.ShowMessageCommand;
import view.command.common.StepFinishedCommand;
import view.command.common.StopFlashingElementViewCommand;
import view.command.tree.UpdateBalanceCommand;

/**
 *
 * 
 */
public class BSTNodeUpdatedAnimation extends AbstractUndoAnimationSteps {
    private BSTHeightBalancedView bstView;
    private BSTNodeView node;
    private Integer balance;
    private Integer oldBalance;

    /**
     * Crea una animacion para un nodo al que se le actualizo el balance
     * @param bstView
     * @param node
     * @param balance
     */
    public BSTNodeUpdatedAnimation(BSTHeightBalancedView bstView, BSTNodeView node, int balance) {
        this.bstView = bstView;
        this.node = node;
        this.balance = balance;
        this.oldBalance = node.getBalance();
        this.setRedoPause(false);
        this.setUndoPause(false);
    }

    /**
     * Crea una animacion para un nodo que ya fue visitado
     * @param bstView
     * @param node
     */
    public BSTNodeUpdatedAnimation(BSTHeightBalancedView bstView, BSTNodeView node) {
        this.bstView = bstView;
        this.node = node;
        this.balance = null;
        this.oldBalance = null;
        this.setRedoPause(false);
        this.setUndoPause(false);
    }

    @Override
    protected void initializeListUndoSteps() {
        undoSteps = new ArrayList<Command>();
        undoSteps.add(new ShowMessageCommand(bstView, "Deshaciendo actualizacion de balanceo..."));
        undoSteps.add( new UpdateBalanceCommand(node, oldBalance) );
        undoSteps.add( new PaintCommand(bstView) );
        undoSteps.add( new PaintCommand(bstView) );
        undoSteps.add(new StepFinishedCommand(bstView, false,this));
    }

     /**
     * Este metodo debe convertirse en el initializeListSteps() de la animacion
     * para poder agregar el deshacer...la logica es la misma pero algunas lineas
     * de codigo (tal cual estan, señaladas a abajo con comentarios) se mueven a
     * un comando UpdateBalanceCommand.
     *
     */
    @Override
    protected void initializeListSteps() {
        steps = new ArrayList<Command>();

        if (balance != null)
            //Esta linea se movio a comando UpdateBalanceCommand
            //node.updateBalance(balance);
            steps.add( new UpdateBalanceCommand(node, balance) );
            
        steps.add( new PaintCommand(bstView) );
        if (! bstView.isModeDelete()) {
            steps.add( new PaintCommand(bstView) );
            steps.add(new StopFlashingElementViewCommand(bstView, node));
        }
        steps.add(new StepFinishedCommand(bstView, false, this));
    }
}
