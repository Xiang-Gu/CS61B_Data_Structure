import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome(" "));
        assertTrue(palindrome.isPalindrome("x"));
        assertTrue(palindrome.isPalindrome("$"));
        assertTrue(palindrome.isPalindrome("noon"));
        assertTrue(palindrome.isPalindrome("mbabm"));
        assertFalse(palindrome.isPalindrome("ab"));
        assertFalse(palindrome.isPalindrome("carbon"));
        assertFalse(palindrome.isPalindrome("ten$cent"));
        assertFalse(palindrome.isPalindrome("Noon"));
    }

    @Test
    public void testOverloadedIsPalindrome() {
        CharacterComparator cc = new OffByOne();
        assertTrue(palindrome.isPalindrome("", cc));
        assertTrue(palindrome.isPalindrome("$", cc));
        assertTrue(palindrome.isPalindrome("ab", cc));
        assertTrue(palindrome.isPalindrome("xyy", cc));
        assertTrue(palindrome.isPalindrome("xb%*&ay", cc));
        assertFalse(palindrome.isPalindrome("noon", cc));
        assertFalse(palindrome.isPalindrome("aba", cc));
        assertFalse(palindrome.isPalindrome("((", cc));
        assertFalse(palindrome.isPalindrome("adfzaecb", cc));
    }

}