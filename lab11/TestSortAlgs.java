import edu.princeton.cs.algs4.Queue;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<Integer> tas = new Queue<Integer>();
        Random random = new Random();
        for (int i = 0; i < 1000000; i++) {
            int next = random.nextInt();
            tas.enqueue(next);
        }
        assertEquals(true, isSorted(QuickSort.quickSort(tas)));
    }

    @Test
    public void testMergeSort() {
        Queue<Integer> tas = new Queue<Integer>();
        Random random = new Random();
        for (int i = 0; i < 1000000; i++) {
            int next = random.nextInt();
            tas.enqueue(next);
        }
        assertEquals(true, isSorted(MergeSort.mergeSort(tas)));
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
