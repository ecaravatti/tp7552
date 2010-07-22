/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.Iterator;

import javax.swing.JTextArea;

import model.collection.queue.QueueObservable;
import view.collection.queue.QueuePanel;
import view.collection.queue.QueueView;

/**
 *
 * @author pgorin
 */
public class QueueController<T> extends InteractiveController {

    private QueueObservable<T> queue;
    private QueueView<T> view;
    private QueuePanel<T> panel;

    public QueueController(QueueObservable<T> queue, QueuePanel<T> panel, JTextArea operationsLog) {
        super(panel.getView(), operationsLog);
        this.queue = queue;
        this.panel = panel;
        this.view = panel.getView();
        this.queue.addListener(view);
    }

    public void dequeueAllItem() {
        boolean showEmptyMessage = true;
        this.panel.getButtonsPanel().enableComponents(false);

        try {
            this.view.prepareAnimation();

            Iterator<T> iterator = queue.iterator();
            while (iterator.hasNext()) {
                showEmptyMessage = false;
                queue.dequeue();
            }
        } catch (Exception e) {
        }

        if (showEmptyMessage) {
            this.showLogMessage("La cola se encuentra vacía.");
        }

        this.panel.getButtonsPanel().enableComponents(true);
    }

    public void enqueueItem(T item) {
        this.panel.getButtonsPanel().enableComponents(false);
        this.view.prepareAnimation();
        this.queue.enqueue(item);
    }

    public void dequeueItem() {
        this.panel.getButtonsPanel().enableComponents(false);
        try {
            this.view.prepareAnimation();
            this.queue.dequeue();
        } catch (Exception e) {
        }
    }

    @Override
    public void primitiveFinished() {
        this.panel.getButtonsPanel().enableComponents(true);
    }
}
