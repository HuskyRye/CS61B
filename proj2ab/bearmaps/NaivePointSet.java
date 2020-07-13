package bearmaps;

import java.util.List;

public class NaivePointSet implements PointSet {
    private List<Point> points;
    public NaivePointSet(List<Point> points) {
        this.points = points;
    }

    @Override
    public Point nearest(double x, double y) {
        double min = 1.0 / 0.0;
        Point target = new Point(x, y);
        Point result = null;
        double distance;
        for (Point p : points) {
            distance = Point.distance(p, target);
            if (distance < min) {
                min = distance;
                result = p;
            }
        }
        return result;
    }
}
