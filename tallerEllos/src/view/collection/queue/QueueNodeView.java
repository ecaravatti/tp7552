package view.collection.queue;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import view.element.common.AbstractElementView;
import view.element.common.LinkableMobile;
import view.element.common.Selectable;
import view.shape.Arrow;


public class QueueNodeView<T> extends AbstractElementView implements LinkableMobile {

    private final static BasicStroke DEF_STROKE = new BasicStroke(1.5f);
    private final static Color DEF_COLOR_ARROW = Color.BLACK;
    private T item;
    private QueueNodeView<T> parent;
    private boolean linked = false;
    private QueueNodeShape shape;

    public QueueNodeView(T item, int index, int x, int y, QueueNodeView<T> parent) {
        super(new Point2D.Double(x, y), true);
        this.item = item;
        this.parent = parent;
        this.shape = new QueueNodeShape(item.toString(), this.getPosition(), index);
        setVisible(false);
    }

    public QueueNodeView<T> getParent() {
        return this.parent;
    }

    public T getItem() {
        return this.item;
    }

    public void assignRole(QueueNodeRoles role) {
        this.shape.setRole(role);
    }

    @Override
    public void markAsLinked() {
        this.linked = true;
    }

    @Override
    public void markAsUnlinked() {
        this.linked = false;
    }

    public void setIndex(Integer index) {
        this.shape.setIndex(index);
    }

    @Override
    protected void paintElement(Graphics g) {
        if (!isVisible()) {
            return;
        }

        if (this.linked) {
            Point2D p1 = new Point2D.Double(parent.getPosition().getX(), parent.getPosition().getY() + shape.getBounds().getHeight() / 2);
            Point2D p2 = new Point2D.Double(getPosition().getX() + shape.getBounds().getWidth(), parent.getPosition().getY() + shape.getBounds().getHeight() / 2);
            Arrow arrow = new Arrow(p1, p2, true, DEF_STROKE, DEF_COLOR_ARROW);
            arrow.paint(g);
        }

        shape.moveTo(getPosition());
        shape.paint((Graphics2D) g);
    }

    @Override
    protected Selectable getSelectable() {
        return shape;
    }
}
