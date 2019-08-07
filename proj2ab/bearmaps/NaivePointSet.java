package bearmaps;

import java.util.ArrayList;
import java.util.List;

public class NaivePointSet implements PointSet {
    private ArrayList<Point> points;

    public NaivePointSet(List<Point> points) {
        this.points = new ArrayList<>(points);
    }

    public int size() {
        return points.size();
    }

    /** Find the nearest point in points to target point (x,y). */
    public Point nearest(double x, double y) {
        double nearestDistance = Double.MAX_VALUE;
        Point nearestPoint = null;
        Point targetPoint = new Point(x, y);

        // Scan points to find the nearest point.
        for(Point p : points) {
            double distance = Point.distance(p, targetPoint);
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestPoint = p;
            }
        }
        return nearestPoint;
    }
}
