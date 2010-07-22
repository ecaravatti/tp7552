/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.structures.heap.animations;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import view.commons.animations.AbstractUndoAnimationSteps;
import view.commons.animations.AnimationSteps;
import view.commons.animations.MobileAnimationSteps;
import view.commons.commands.ChangeColorBSTNodeShapeCommand;
import view.commons.commands.ChangeTextColorCommand;
import view.commons.commands.Command;
import view.commons.commands.ShowMessageCommand;
import view.commons.commands.StepFinishedCommand;
import view.commons.shapes.Text;
import view.structures.heap.HeapView;
import view.structures.trees.BSTNodeShape;

/**
 *
 * @author Duilio
 */
public class SwapElementsAnimation extends AbstractUndoAnimationSteps {

    private static final int FACT = 3;
    private static final int PATH_LENGTH = 40;
    private HeapView view;
    private Text parentLabel;
    private Text currentLabel;
    private BSTNodeShape parentNode;
    private BSTNodeShape currentNode;
    private double deltaLabel;
    private double deltaNode;

    public SwapElementsAnimation(HeapView view) {
        this.view = view;
    }
    
    private void moveElements(List<Command> commands) {
        
        String text = "";
        if (parentLabel.getTitle().equals("")) {
            text = "Moviendo elemento " + currentLabel.getTitle() + " a la raíz ...";
        } else {
            text = "Moviendo elementos " + currentLabel.getTitle() + " y " + parentLabel.getTitle() + " ...";
        }
        commands.add(new ShowMessageCommand(view, text));
        
        AnimationSteps moveParent = new MobileAnimationSteps(view, parentLabel, parentLabel.getPosition(), currentLabel.getPosition(), deltaLabel * FACT);
        AnimationSteps moveCurrent = new MobileAnimationSteps(view, currentLabel, currentLabel.getPosition(), parentLabel.getPosition(), deltaLabel * FACT);

        AnimationSteps moveParentNode = new MobileAnimationSteps(view, parentNode, parentNode.getPosition(), currentNode.getPosition(), deltaNode * FACT);
        AnimationSteps moveCurrentNode = new MobileAnimationSteps(view, currentNode, currentNode.getPosition(), parentNode.getPosition(), deltaNode * FACT);

        List<Command> parentMovements = moveParent.getSteps();
        List<Command> currentMovements = moveCurrent.getSteps();

        List<Command> parentNodeMovements = moveParentNode.getSteps();
        List<Command> currentNodeMovements = moveCurrentNode.getSteps();

        int i = 0;
        for (i = 0; i < parentMovements.size() && i < parentNodeMovements.size(); i++) {
            commands.add(parentMovements.get(i));
            commands.add(currentMovements.get(i));
            commands.add(parentNodeMovements.get(i));
            commands.add(currentNodeMovements.get(i));
        }

        for (int j = i; j < parentMovements.size(); j++) {
            commands.add(parentMovements.get(j));
            commands.add(currentMovements.get(j));
        }

        for (int j = i; j < parentNodeMovements.size(); j++) {
            commands.add(parentNodeMovements.get(j));
            commands.add(currentNodeMovements.get(j));
        }
        
        if (parentLabel.getTitle().equals("")) {
            text = "Se movió el elemento " + currentLabel.getTitle() + " a la raíz";
        } else {
            text = "Se intercambió el elemento " + currentLabel.getTitle() + " con el elemento " + parentLabel.getTitle();
        }
        commands.add(new ShowMessageCommand(view, text));
    }

    @Override
    protected void initializeListUndoSteps() {
        this.undoSteps = new ArrayList<Command>();
        
        changeLabelColor(Color.BLUE, undoSteps);
        changeNodeColor(Color.BLUE, undoSteps);
        
        moveElements(undoSteps);
        
        changeLabelColor(Color.BLACK, undoSteps);
        changeNodeColor(Color.YELLOW, undoSteps);
        
        this.undoSteps.add(new StepFinishedCommand(view, false, this));
    }

    @Override
    protected void initializeListSteps() {
        parentLabel = view.getParentLabel();
        currentLabel = view.getCurrentLabel();

        parentNode = view.getParentNode();
        currentNode = view.getCurrentNode();

        calculateDelta();

        steps = new ArrayList<Command>();

        changeLabelColor(Color.RED, steps);
        changeNodeColor(Color.RED, steps);
        
        moveElements(steps);

        changeLabelColor(Color.BLACK, steps);
        changeNodeColor(Color.YELLOW, steps);

        steps.add(new StepFinishedCommand(view, false, this));
    }

    private void changeLabelColor(Color color, List<Command> commands) {
        commands.add(new ChangeTextColorCommand(parentLabel, color));
        commands.add(new ChangeTextColorCommand(currentLabel, color));
    }

    private void changeNodeColor(Color color, List<Command> commands) {
        commands.add(new ChangeColorBSTNodeShapeCommand(parentNode, color));
        commands.add(new ChangeColorBSTNodeShapeCommand(currentNode, color));
    }

    private void calculateDelta() {
        deltaLabel = currentLabel.getPosition().distance(parentLabel.getPosition()) / PATH_LENGTH;
        deltaNode = currentNode.getPosition().distance(parentNode.getPosition()) / PATH_LENGTH;
    }
}
