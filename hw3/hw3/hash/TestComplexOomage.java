package hw3.hash;

import edu.princeton.cs.algs4.Complex;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class TestComplexOomage {

    @Test
    public void testHashCodeDeterministic() {
        ComplexOomage so = ComplexOomage.randomComplexOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    /* This should pass if your OomageTestUtility.haveNiceHashCodeSpread
       is correct. This is true even though our given ComplexOomage class
       has a flawed hashCode. */
    @Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(ComplexOomage.randomComplexOomage());
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }

    /* TODO: Create a list of Complex Oomages called deadlyList
     * that shows the flaw in the hashCode function.
     */

    @Test
    public void testWithDeadlyParams() {
        List<Oomage> deadlyList = new ArrayList<>();

        // Create 100 complexOomages using this special construction
        // which ensures all these complexOomages always ends with the same
        // four integer numbers.
        for (int i = 0; i < 100; i++) {
            List<Integer> arr = new ArrayList<>();
            // Insert i random integers to arr.
            for (int j = 0; j < i; j++) {
                arr.add((int)Math.random() * 255);
            }
            // Make sure arr always ends with these four same numbers: 13, 17, 8, 1.
            // Those four numbers are just arbitrary choices. Any four numbers between
            // 0 and 255, inclusive, will do. The key insight of the flaw of the hascode
            // of the complexOomage class is that it essentially only computed the hascode
            // of the last four numbers because of integer (32 bits) overflow. Thus, if
            // we pass in a bunch of different complexOomage with the same last four numbers,
            // then we will get the same hashcode! Same hashcode corresponds to same bucket
            // number so all the complexOomages will be thrown into the same bucket. Hence it
            // will fail the niceSpread test.
            arr.add(13);
            arr.add(17);
            arr.add(8);
            arr.add(1);

            // Instantiate a new complex oomage with this arr.
            ComplexOomage co = new ComplexOomage(arr);

            deadlyList.add(co);
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(deadlyList, 10));
    }

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestComplexOomage.class);
    }
}
