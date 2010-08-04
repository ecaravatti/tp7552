package view.shape;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Esta clase representa a un segmento.
 * 
 * 
 */
public class Segment {
  private Point2D p1;
  private Point2D p2;
  private double delta;

  /**
   * Construye un segmento.
   * 
   * @param p1 punto inicial
   * @param p2 punto final
   * @param delta variacion del paso para ir de p1 a p2.
   */
  public Segment(Point2D p1, Point2D p2, double delta) {
    super();
    this.p1 = p1;
    this.p2 = p2;
    this.delta = delta;
  }

  /**
   * Obtiene los puntos que definen el segmento que va de P1 a P2.
   * 
   * @return una lista de puntos.
   */
  public ArrayList<Point2D> getPoints() {
    ArrayList<Point2D> array = new ArrayList<Point2D>();
    Point2D point = new Point2D.Double(p1.getX(), p1.getY());
    array.add((Point2D) point.clone());
    double sX = getSign(p1.getX(), p2.getX());
    double sY = getSign(p1.getY(), p2.getY());

    if (point.equals(p2)) {
      return array;
    }

    double angle = getAngle();
    double incX = sX * delta * Math.cos(angle);
    double incY = sY * delta * Math.sin(angle);

    while (sX * point.getX() < sX * p2.getX()
        || sY * point.getY() < sY * p2.getY()) {
      point.setLocation(point.getX() + incX, point.getY() + incY);
      array.add((Point2D) point.clone());
    }
    array.remove(array.size() - 1);
    array.add((Point2D) p2.clone());
    return array;
  }

  /**
   * Obtiene los puntos que definen el segmento que va de P1 a P2.
   * @param count la cantidad maxima de puntos
   * @return una lista de puntos.
   */
  public ArrayList<Point2D> getPoints(int count) {
    ArrayList<Point2D> array = new ArrayList<Point2D>(count);
    Point2D point = new Point2D.Double(p1.getX(), p1.getY());
    array.add((Point2D) point.clone());
    double sX = getSign(p1.getX(), p2.getX());
    double sY = getSign(p1.getY(), p2.getY());

    if (point.equals(p2)) {
      return array;
    }

    double angle = getAngle();
    double incX = sX * delta * Math.cos(angle);
    double incY = sY * delta * Math.sin(angle);

    while (sX * point.getX() < sX * p2.getX()
        || sY * point.getY() < sY * p2.getY()) {
      point.setLocation(point.getX() + incX, point.getY() + incY);
      array.add((Point2D) point.clone());
      if (array.size() > count)
          break;
    }
    array.remove(array.size() - 1);
    array.add((Point2D) p2.clone());
    return array;
  }

  /**
   * Calcula el angulo
   * @return el angulo
   */
  private double getAngle() {

    if (p2.getX() - p1.getX() == 0)
      return Math.PI / 2;

    double m = (p2.getY() - p1.getY()) / (p2.getX() - p1.getX());
    return Math.abs(Math.atan(m));

  }

  private double getSign(double init, double fin) {
    return Math.signum(fin - init);
  }
}
