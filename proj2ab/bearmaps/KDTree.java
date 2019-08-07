package bearmaps;

import java.util.List;

public class KDTree implements PointSet {
    private static final int XBASED = 0;
    private static final int YBASED = 1;

    private class Node {
        private Point p;
        private int pivotDimension;
        private Node leftChild;
        private Node rightChild;

        public Node(Point p, int pd, Node lc, Node rc) {
            this.p = new Point(p.getX(), p.getY());
            pivotDimension = pd;
            leftChild = lc;
            rightChild = rc;
        }
    }

    private Node root;
    private int size;

    public KDTree(List<Point> points) {
        root = null;
        // Call add method on each and every point in points
        for (Point p : points) {
            add(p);
        }
        size = points.size();
    }

    /** Compare two points p1 and p2 based on pivotDimension.
     * Return a positive number if p1 is "larger than" p2 in terms of pivotDimension;
     * zero if "equal to";
     * negative number if "smaller than".
     */
    private double comparePoints(Point p1, Point p2, int pivotDimension) {
        if (pivotDimension == XBASED) {
            return p1.getX() - p2.getX();
        } else {
            return p1.getY() - p2.getY();
        }
    }

    /** Add a new point p to this point set implemented with a KDTree. */
    private void add(Point p) {
        root = addHelper(p, root, XBASED);
    }

    /** Add a point p to a KDTree rooted in n with pivot dimension being pivotDimension. */
    private Node addHelper(Point p, Node n, int pivotDimension) {
        // Base case: n is null or n.p is equal to p.
        if (n == null) {
            return new Node(p, pivotDimension, null, null);
        } else if (n.p.equals(p)) {
            return n;
        } else {
            // Recursively add p to n's left or right subtree based on
            // comparison of p and n.p in terms of pivotDimension.
            double cmp = comparePoints(p, n.p, n.pivotDimension);
            if (cmp < 0) {
                n.leftChild = addHelper(p, n.leftChild, (pivotDimension+1)%2);
            } else {
                n.rightChild = addHelper(p, n.rightChild, (pivotDimension+1)%2);
            }
            return n;
        }
    }

    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
        Node best = nearestHelper(root, goal, root);
        return best.p;
    }

    /** A helper method that returns whichever is closer to goal from
     * the following two choices:
     * 1. best
     * 2. all items in the (sub)tree rooted in n
     */
    private Node nearestHelper(Node n, Point goal, Node best) {
        // Base case
        if (n == null) {
            return best;
        }

        // Check whether node n is a better point (i.e. if it is closer to goal compared with best).
        if (Point.distance(n.p, goal) < Point.distance(best.p, goal)) {
            best = n;
        }

        // Good side is the side that goal is to n w.r.t. n.pivotDimension.
        Node goodSide, badSide;
        double cmp = comparePoints(goal, n.p, n.pivotDimension);
        if (cmp < 0) {
            goodSide = n.leftChild;
            badSide = n.rightChild;
        } else {
            goodSide = n.rightChild;
            badSide = n.leftChild;
        }

        // Explore good side (subtree) first since it is more likely
        // the nearest point to goal is in this subtree
        best = nearestHelper(goodSide, goal, best);

        // Explore the bad side if there might still have something useful,
        // i.e., when the shortest distance b/t goal and the bad side is smaller than
        // the distance b/t goal and (current) best.
        if (comparePoints(n.p, goal, n.pivotDimension) < Point.distance(best.p, goal)) {
            best = nearestHelper(badSide, goal, best);
        }

        return best;
    }

    public int size() {
        return size;
    }
}
