package bearmaps;

import java.util.List;

public class KDTree implements PointSet {
    private static class Node {
        Point point;
        boolean x;
        Node left;
        Node right;
        Node(Point point, boolean x) {
            this.point = point;
            this.x = x;
            left = null;
            right = null;
        }
    }
    private Node root;
    public KDTree(List<Point> points) {
        for (Point p : points) {
            add(p);
        }
    }

    private void add(Point point) {
        if (root == null) {
            root = new Node(point, true);
            return;
        }
        Node p = root;
        Node parent;
        double cmp;
        do {
            parent = p;
            cmp = (p.x ? point.getX() - p.point.getX() : point.getY() - p.point.getY());
            if (cmp < 0) {
                p = p.left;
            } else {
                p = p.right;
            }
        } while (p != null);
        Node n = new Node(point, !parent.x);
        if (cmp < 0) {
            parent.left = n;
        } else {
            parent.right = n;
        }
    }

    private Node nearest(Node n, Point goal, Node best) {
        if (n == null) {
            return best;
        }
        if (Point.distance(n.point, goal) < Point.distance(best.point, goal)) {
            best = n;
        }
        double cmp = (n.x ? goal.getX() - n.point.getX() : goal.getY() - n.point.getY());
        Node goodSide;
        Node badSide;
        if (cmp < 0) {
            goodSide = n.left;
            badSide = n.right;
        } else {
            goodSide = n.right;
            badSide = n.left;
        }
        best = nearest(goodSide, goal, best);
        double badBest;
        if (n.x) {
            badBest = Math.abs(goal.getX() - n.point.getX());
        } else {
            badBest = Math.abs(goal.getY() - n.point.getY());
        }
        if (badBest * badBest < Point.distance(best.point, goal)) {
            best = nearest(badSide, goal, best);
        }
        return best;
    }

    @Override
    public Point nearest(double x, double y) {
        return nearest(root, new Point(x, y), root).point;
    }
}
