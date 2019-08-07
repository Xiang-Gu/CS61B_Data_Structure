package bearmaps;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;


public class ArrayHeapMinPQTest {
    public LinkedList<Integer> listWithDistinctRandomNumber(int size, int uppperBound) {
        Random random = new Random();
        // Generate max distinct random numbers from the range of [0, upperBound).
        LinkedList<Integer> list = new LinkedList<>();
        while (list.size() < size) {
            int randomNumber = random.nextInt(uppperBound);
            if (!list.contains(randomNumber)) {
                list.add(randomNumber);
            }
        }
        return list;
    }

    @Test
    public void testChangePriority() {
        Random random = new Random();
        NaiveMinPQ<Integer> nPQ = new NaiveMinPQ<>();
        ArrayHeapMinPQ<Integer> PQ = new ArrayHeapMinPQ<>();
        int size = 10000;
        int upperBound = 99999999;
        LinkedList<Integer> list = listWithDistinctRandomNumber(size, upperBound);

        for (int i = 0; i < size; i++) {
            // Generate a random priority in the range of [0, 1000).
            double randomPriority = random.nextDouble() * 1000;
            nPQ.add(list.get(i), randomPriority);
            PQ.add(list.get(i), randomPriority);

            // Change the newly added item's priority after every five insertions
            // and check the smallest item in both PQs are the same.
            if (i != 0 && i % 5 == 0) {
                randomPriority = random.nextDouble() * 300;
                nPQ.changePriority(list.get(i), randomPriority);
                PQ.changePriority(list.get(i), randomPriority);
                assertEquals(nPQ.getSmallest(), PQ.getSmallest());
            }
        }
    }

    @Test
    public void randomizedTestWithNaiveMinPQ() {
        Random random = new Random();
        NaiveMinPQ<Integer> nPQ = new NaiveMinPQ<>();
        ArrayHeapMinPQ<Integer> PQ = new ArrayHeapMinPQ<>();

        int size = 50000;
        int upperBound = 99999999;
        LinkedList<Integer> list = listWithDistinctRandomNumber(size, upperBound);
        for (int i = 0; i < size; i++) {
            // Generate a random priority in the range of [0, 1000).
            double randomPriority = random.nextDouble() * 1000;
            nPQ.add(list.get(i), randomPriority);
            PQ.add(list.get(i), randomPriority);
            assertEquals(nPQ.getSmallest(), PQ.getSmallest());

            // Remove one element after every 10 insertions.
            if (i != 0 && i % 10 == 0) {
                nPQ.removeSmallest();
                PQ.removeSmallest();
                assertEquals(nPQ.getSmallest(), PQ.getSmallest());
            }
        }
    }

}



