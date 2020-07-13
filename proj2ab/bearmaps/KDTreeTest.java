package bearmaps;

import edu.princeton.cs.introcs.StdRandom;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class KDTreeTest {
    @Test
    public void addTest() {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 5);
        Point p4 = new Point(3, 3);
        Point p5 = new Point(1, 5);
        Point p6 = new Point(4, 4);
        KDTree kdt = new KDTree(List.of(p1, p2, p3, p4, p5, p6));
    }

    @Test
    public void simpleTest() {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 5);
        Point p4 = new Point(3, 3);
        Point p5 = new Point(1, 5);
        Point p6 = new Point(4, 4);
        KDTree kdt = new KDTree(List.of(p1, p2, p3, p4, p5, p6));
        Point ret = kdt.nearest(0, 7);
    }

    @Test
    public void randomTest() {
        List<Point> points = new ArrayList<Point>(1000);
        for (int i = 0; i < 1000; ++i) {
            points.add(new Point(StdRandom.uniform(), StdRandom.uniform()));
        }
        NaivePointSet nps = new NaivePointSet(points);
        KDTree kdt = new KDTree(points);
        double x, y;
        for (int i = 0; i < 100; ++i) {
            x = StdRandom.uniform();
            y = StdRandom.uniform();
            assertEquals(nps.nearest(x, y), kdt.nearest(x, y));
        }
    }

    @Test
    public void timeTest() {
        List<Point> points = new ArrayList<Point>(1000);
        for (int i = 0; i < 1000000; ++i) {
            points.add(new Point(StdRandom.uniform(), StdRandom.uniform()));
        }
        NaivePointSet nps = new NaivePointSet(points);
        KDTree kdt = new KDTree(points);
        double x, y;

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; ++i) {
            x = StdRandom.uniform();
            y = StdRandom.uniform();
            nps.nearest(x, y);
        }
        long end = System.currentTimeMillis();
        System.out.println("NaivePointSet: " + (end - start) / 1000.0 +  " seconds.");

        start = System.currentTimeMillis();
        for (int i = 0; i < 100; ++i) {
            x = StdRandom.uniform();
            y = StdRandom.uniform();
            kdt.nearest(x, y);
        }
        end = System.currentTimeMillis();
        System.out.println("KDTree: " + (end - start) / 1000.0 +  " seconds.");

    }
}
