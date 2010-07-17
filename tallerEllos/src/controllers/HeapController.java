/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javax.swing.JTextArea;
import model.structures.heap.Heap;
import view.commons.commands.ShowPrimitiveCodeCommand;
import view.structures.heap.HeapPrimitives;
import view.structures.heap.HeapView;

/**
 *
 * @author Duilio
 */
public class HeapController<T extends Comparable<T>> extends InteractiveController {

    private Heap heap;
    private HeapView view;
    private JTextArea operationsLog;

    public HeapController(Heap heap, HeapView view, JTextArea operationsLog) {
        super(view, operationsLog);

        this.heap = heap;
        this.view = view;
        this.operationsLog = operationsLog;

        heap.addListener(view);
        view.addController(this);
    }

    public void addItem(T item) {
      new ShowPrimitiveCodeCommand(this, HeapPrimitives.insert.getCode()).execute();
      this.heap.insert(item);
    }

    public void deleteItem() {
      new ShowPrimitiveCodeCommand(this, HeapPrimitives.delete.getCode()).execute();
      this.heap.remove();
    }
    
    public void clear() {
        this.heap.clear();
    }

    @Override
    public void primitiveFinished() {
        view.setEnabledButtons(true);
    }
}
