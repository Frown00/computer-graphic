import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

public class DrawingPolygon extends Polygon {

    ArrayList<Point2D> points;
    private ArrayList<Line2D> lines;
    private final double ENDING_AREA = 20.0;

    DrawingPolygon() {
        points = new ArrayList<>();
        lines = new ArrayList<>();
    }

    public void addPoint(Point2D p) {
        if(points.size() >= 1) {
            Point2D startPoint = points.get(points.size() - 1);
            System.out.println(startPoint);
            Line2D line = new Line2D.Double(startPoint.getX(), startPoint.getY(), p.getX(), p.getY());
            lines.add(line);
        }
        if( points.size() == 0 || !isLastPoint(p)) {
            points.add(p);
        }
    }



    public ArrayList<Line2D> getLines() {
        return lines;
    }

    public boolean isLastPoint(Point2D point) {
        boolean isLast = false;
        Point2D startPoint = points.get(0);
        double d = Math.sqrt((point.getX() - startPoint.getX()) * (point.getX() - startPoint.getX())
                             + (point.getY() - startPoint.getY()) * (point.getY() - startPoint.getY()));

        if(d < ENDING_AREA) {
            isLast = true;
        }

        return isLast;
    }

    public Polygon createPolygon() {
        Polygon p = new Polygon();

        for (Point2D point : points) {
            p.addPoint((int) point.getX(), (int) point.getY());
        }

        Rectangle2D frame = p.getBounds2D();

        if(frame.getWidth() <= 0 && frame.getHeight() <= 0) {
            p = null;
        }
        return  p;
    }

}
