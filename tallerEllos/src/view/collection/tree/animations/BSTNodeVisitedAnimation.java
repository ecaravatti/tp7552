package view.structures.trees.animations;

import view.structures.trees.commands.ChangeSelectableColorCommand;
import java.util.ArrayList;
import view.commons.animations.AbstractUndoAnimationSteps;
import view.commons.commands.Command;
import view.commons.commands.SelectElementViewCommand;
import view.commons.commands.ShowMessageCommand;
import view.commons.commands.StepFinishedCommand;
import view.commons.commands.StopFlashingElementViewCommand;
import view.structures.trees.BSTHeightBalancedView;
import view.structures.trees.BSTNodeView;

/**
 *
 * @author Exe Curia
 */
public class BSTNodeVisitedAnimation extends AbstractUndoAnimationSteps {

    private BSTHeightBalancedView bstView;
    private BSTNodeView node;
    SelectionMode mode;

    public enum SelectionMode {
        VISITED,
        FOUND,
        TRAVERSED,
    }

    public BSTNodeVisitedAnimation(BSTHeightBalancedView bstView, BSTNodeView node, SelectionMode mode) {
        this.bstView = bstView;
        this.node = node;
        this.mode = mode;
        this.setRedoPause(false);
        this.setUndoPause(false);
    }

    @Override
    protected void initializeListUndoSteps() {
      undoSteps = new ArrayList<Command>();
      undoSteps.add(new ShowMessageCommand(bstView, "Deshaciendo nodo visitado..."));
      undoSteps.add( new StopFlashingElementViewCommand(bstView, node) );
      undoSteps.add(new StepFinishedCommand(bstView, false, this));
    }

    @Override
    protected void initializeListSteps() {
        steps = new ArrayList<Command>();

        switch (mode) {
            case VISITED:
                steps.add(new ShowMessageCommand(bstView, "Visitando nodo (" + node.getData().toString() + ")"));

                steps.add(new SelectElementViewCommand(bstView, bstView.getFlashingDelay(), node, BSTNodeView.DEF_COLOR_SELECTION));
                steps.addAll(bstView.getPaintCommands());
                steps.add(new ChangeSelectableColorCommand(bstView, node, BSTNodeView.DEF_COLOR_VISITED));
                //        steps.add(new StopFlashingElementViewCommand(bstView, node));
                steps.addAll(bstView.getPaintCommands());
                steps.add(new SelectElementViewCommand(bstView, bstView.getFlashingDelay(), node, BSTNodeView.DEF_COLOR_SELECTION));
                steps.addAll(bstView.getPaintCommands());
                steps.add(new ChangeSelectableColorCommand(bstView, node, BSTNodeView.DEF_COLOR_VISITED));
                break;

            case FOUND:
                steps.add(new ShowMessageCommand(bstView, "Nodo nodo (" + node.getData().toString() + ") encontrado: no se puede insertar"));

                steps.add(new SelectElementViewCommand(bstView, bstView.getFlashingDelay(), node, BSTNodeView.DEF_COLOR_FOUND));
                steps.addAll(bstView.getPaintCommands());
                break;

            case TRAVERSED:
                steps.add(new ShowMessageCommand(bstView, "Nodo (" + node.getData().toString() + ") recorrido"));

                steps.add(new SelectElementViewCommand(bstView, bstView.getFlashingDelay(), node, BSTNodeView.DEF_COLOR_FOUND));
                steps.addAll(bstView.getPaintCommands());
                steps.addAll(bstView.getPaintCommands());
                steps.add(new StopFlashingElementViewCommand(bstView, node));
                break;
        }

        //steps.add(new StepFinishedCommand(bstView, false));
        //para el deshacer
        steps.add(new StepFinishedCommand(bstView, false, this));
    }
}
