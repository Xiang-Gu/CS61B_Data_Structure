import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test
    public void testOffByOne() {
        assertTrue(offByOne.equalChars('x', 'y'));
        assertTrue(offByOne.equalChars('y', 'x'));
        assertTrue(offByOne.equalChars('%', '&'));
        assertTrue(offByOne.equalChars('2', '3'));
        assertFalse(offByOne.equalChars('a', 'a'));
        assertFalse(offByOne.equalChars('a', 'B'));
        assertFalse(offByOne.equalChars('z', 'a'));
    }
}