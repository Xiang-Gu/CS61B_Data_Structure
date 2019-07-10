import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByN = new OffByN(5);

    // Your tests go here.
    @Test
    public void testOffByN() {
        assertTrue(offByN.equalChars('a', 'f'));
        assertTrue(offByN.equalChars('1', '6'));
        assertTrue(offByN.equalChars('A', 'F'));
        assertFalse(offByN.equalChars('a', 'a'));
        assertFalse(offByN.equalChars('a', 'b'));
        assertFalse(offByN.equalChars('1', '2'));
    }
}