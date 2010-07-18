/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.Iterator;
import javax.swing.JTextArea;

import model.structures.queue.QueueObservable;
import view.structures.queues.QueuePanel;
import view.structures.queues.QueueView;

/**
 *
 * @author pgorin
 */
public class QueueController<T> extends InteractiveController {

    private QueueObservable<T> queue;
    private QueueView<T> view;
    private QueuePanel panel;
    private JTextArea operationsLog;

    public QueueController(QueueObservable<T> queue, QueuePanel panel, JTextArea operationsLog) {
        super(panel.getView(), operationsLog);
        this.queue = queue;
        this.panel = panel;
        this.view = panel.getView();
        this.queue.addListener(view);
        this.operationsLog = operationsLog;
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
