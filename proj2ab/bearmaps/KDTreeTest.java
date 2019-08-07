package bearmaps;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class KDTreeTest {
    public ArrayList<Point> randomPoints(int size, int boundary) {
        // Generate max distinct random (2D) points
        ArrayList<Point> list = new ArrayList<>();

        Random random = new Random();
        while (list.size() < size) {
            double x = random.nextDouble() * boundary;
            double y = random.nextDouble() * boundary;
            if (!list.contains(new Point(x,y))) {
                list.add(new Point(x, y));
            }
        }
        return list;
    }

    @Test
    public void testWithNaivePointSet() {
        int size = 20000;
        int boundary = Integer.MAX_VALUE;
        ArrayList<Point> list = randomPoints(size, boundary);

        NaivePointSet nps = new NaivePointSet(list);
        KDTree kdTree = new KDTree(list);
        System.out.println("Size of nps = " + nps.size());
        System.out.println("Size of kdTree = "  + kdTree.size());

        // Query nps and kdTree the nearest point of
        // size random points
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            double x = random.nextDouble() * boundary;
            double y = random.nextDouble() * boundary;
            assertEquals(nps.nearest(x,y), kdTree.nearest(x,y));
        }
    }
}
