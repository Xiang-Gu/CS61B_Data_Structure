package bearmaps;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;


public class ArrayHeapMinPQTest {
    @Test
    public void randomizedTestWithNaiveMinPQ() {
        Random random = new Random();
        NaiveMinPQ<Integer> nPQ = new NaiveMinPQ<>();
        ArrayHeapMinPQ<Integer> PQ = new ArrayHeapMinPQ<>();

        // Generate max distinct random numbers from the range of [0, upperBound).
        int max = 50000;
        int upperBound = 99999999;
        List<Integer> list = new LinkedList<>();
        while (list.size() < max) {
            int randomNumber = random.nextInt(upperBound);
            if (!list.contains(randomNumber)) {
                list.add(randomNumber);
            }
        }

        for (int i = 0; i < max; i++) {
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



