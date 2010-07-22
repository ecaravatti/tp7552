package view.collection.tree;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import model.collection.tree.BSTNode;
import view.element.common.AbstractElementView;
import view.element.common.Selectable;
import view.shape.Arrow;
import event.tree.BSTNodeEvent;
import event.tree.BSTNodeListener;

/**
 * 
 * 
 */
public class BSTNodeView extends AbstractElementView implements
        BSTNodeListener<Integer>, Cloneable{

    private final static BasicStroke DEF_STROKE = new BasicStroke(1.5f);
    private final static Font DEF_FONT_NODE = new Font("SansSerif", Font.BOLD, 12);
    private final static Font DEF_FONT_BALANCE = new Font("SansSerif", Font.PLAIN, 11);
    private final static Color DEF_COLOR_ARROW = new Color(100, 100, 200);
    public final static Color DEF_COLOR_VISITED = Color.ORANGE;
    public final static Color DEF_COLOR_SELECTION = Color.GREEN;
    public final static Color DEF_COLOR_FOUND = Color.RED;
    public static final int LEFT = -1;
    public static final int RIGHT = 1;
    private static final int DELTA_VERTICAL = 50;
    private static final int DELTA_HORIZONTAL = 60;
    private static final int DIAMETER = 35;

    private BSTNodeView parent;
    private Point2D finalPosition;
    private Integer data;
    private double leftWidth;
    private double rightWidth;
    public BSTNodeView left;
    public BSTNodeView right;
    private BSTPrimitives bstView;
    private BSTNodeShape shape;

    public BSTNodeView(BSTNode<Integer> node, BSTPrimitives view) {
        super(new Point2D.Double(), DIAMETER, DIAMETER, true);
        node.addListener(this);
        this.data = node.getData();
        this.bstView = view;
        this.shape = new BSTNodeShape(data.toString(), String.valueOf(node.getBalance()), DIAMETER, DEF_FONT_NODE, DEF_FONT_BALANCE, DEF_STROKE);
        setVisible(false);
        this.parent = null;
        this.finalPosition = null;
    }

    public Integer getData() {
        return data;
    }

    public BSTNodeView getLeft() {
        return left;
    }

    public BSTNodeView getRight() {
        return right;
    }

    public BSTNodeView getParent() {
        return parent;
    }

    public void setParent(BSTNodeView parent) {
        this.parent = parent;
    }

    public BSTNodeView rotateLeft(BSTNodeView node) {
        BSTNodeView temp = node;
        node = node.right;
        temp.right = node.left;
        node.left = temp;

        node.parent = temp.parent;
        temp.parent = node;
        if (temp.right != null)
            temp.right.parent = temp;
        return node;
    }

    public BSTNodeView rotateRight(BSTNodeView node) {
        BSTNodeView temp = node;
        node = node.left;
        temp.left = node.right;
        node.right = temp;

        node.parent = temp.parent;
        temp.parent = node;
        if (temp.left != null)
            temp.left.parent = temp;
        return node;
    }

    public void updateBalance(int balance) {
        this.shape.setBalance(String.valueOf(balance));
    }

    public int getBalance(){
      return new Integer(this.shape.getBalance()).intValue();
    }
     
 

    @Override
    public void balanceUpdated(BSTNodeEvent<Integer> event) {
        bstView.balanceUpdated(this, event.getSource().getBalance());
    }

    @Override
    public void leftChildAdded(BSTNodeEvent<Integer> event) {
        if (left == null) {
            left = new BSTNodeView(event.getSource().getLeft(), bstView);
            bstView.setParentLeftChild(left);
        } else {
            bstView.setParentLeftChild(null);
        }
    }

    @Override
    public void rightChildAdded(BSTNodeEvent<Integer> event) {
        if (right == null) {
            right = new BSTNodeView(event.getSource().getRight(), bstView);
            bstView.setParentRightChild(right);
        } else {
            bstView.setParentRightChild(null);
        }
    }

    @Override
    public void getLeftChild(BSTNodeEvent<Integer> event) {
        bstView.selectLeftChild();
    }

    @Override
    public void getRightChild(BSTNodeEvent<Integer> event) {
        bstView.selectRightChild();
    }

    @Override
    public void nodeTraversed(BSTNodeEvent<Integer> event) {
        bstView.nodeTraversed(this);
    }

    @Override
    public void removed(BSTNodeEvent<Integer> event) {
        bstView.nodeRemoved(this);
    }


    @Override
    protected void paintElement(Graphics g) {
        if (! isVisible())
            return;

        int side = RIGHT;

        if (parent != null) {
            if (parent.left == this)
                side = LEFT;

            Point2D previous = parent.getPosition();
            Point2D p1 = new Point2D.Double(previous.getX()+DIAMETER/2, previous.getY()+DIAMETER/2);
            Point2D p2 = new Point2D.Double(getPosition().getX()+DIAMETER*(2-side)/4, getPosition().getY());
            Arrow arrow = new Arrow(p1, p2, true, DEF_STROKE, DEF_COLOR_ARROW);
            arrow.paint(g);
        }

        if (left != null)
            left.paintElement(g);
        if (right != null)
            right.paintElement(g);

        shape.moveTo(getPosition());
        shape.paint((Graphics2D) g, side);
    }

    private double resetWidths(BSTNodeView node) {
        if ((node == null) || (!node.isVisible())) {
            return 0;
        }
        node.leftWidth = Math.max(resetWidths(node.left), DELTA_HORIZONTAL / 2);
        node.rightWidth = Math.max(resetWidths(node.right), DELTA_HORIZONTAL / 2);
        return node.leftWidth + node.rightWidth;
    }

    /**
     * Establece las posiciones recursivamente
     * @param node
     * @param position
     * @param side 0 si es la raiz, -1 si es lado izquierdo o 1 si es lado derecho
     */
    private void setNewPositions(BSTNodeView node, Point2D position, int side,
            boolean setFinalLocation) {
        Point2D pos = null;

        if ((node == null) || (!node.isVisible())) {
            return;
        }

        double x = position.getX();
        double y = position.getY();

        if (side != 0)
            y += DELTA_VERTICAL;

        if (side == LEFT)
            x -= node.rightWidth;
        else if (side == RIGHT)
            x += node.leftWidth;

        if (! setFinalLocation) {
            node.getPosition().setLocation(x, y);
            pos = node.getPosition();
        }
        else {
            node.finalPosition = new Point2D.Double(x, y);
            pos = node.getFinalPosition();
        }

        setNewPositions(node.left, pos, LEFT, setFinalLocation);
        setNewPositions(node.right, pos, RIGHT, setFinalLocation);
    }

    public void setLocation(Point2D position) {
        resetWidths(this);
        setNewPositions(this, position, 0, false);
    }

    public void setFinalPositions(Point2D position) {
        resetWidths(this);
        setNewPositions(this, position, 0, true);
    }

    public Point2D getFinalPosition() {
        return finalPosition;
    }
    
    @Override
    public Object clone() {
      return super.clone();
    }

    @Override
    protected Selectable getSelectable() {
        return shape;
    }

    public double getLeftWidth() {
        double w = 0;
        if (left != null)
            w = left.getLeftWidth();
        return leftWidth + w;
    }

    public double getRightWidth() {
        double w = 0;
        if (right != null)
            w = right.getRightWidth();
        return rightWidth + w;
    }
}
