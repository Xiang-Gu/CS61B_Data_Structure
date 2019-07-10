/** This class outputs all palindromes in the words file in the current directory. */
public class PalindromeFinder {
    public static void main(String[] args) {
        for (int N = 0; N < 50; N++) {
            In in = new In("../library-sp19/data/words.txt");
            Deque<String> palindromesWithCharComparatorN = new ArrayDeque<>();
            CharacterComparator cc = new OffByN(N);
            String longestPalindrome = "";
            Palindrome palindrome = new Palindrome();

            while (!in.isEmpty()) {
                String word = in.readString();
                if (palindrome.isPalindrome(word, cc)) {
                    palindromesWithCharComparatorN.addLast(word);
                    if (word.length() > longestPalindrome.length()) {
                        longestPalindrome = word;
                    }
                }
            }
            System.out.println("# of palindromes with N = " + N + " : " + palindromesWithCharComparatorN.size());
            System.out.println("\t longest (might have ties) palindrome: " + longestPalindrome);
        }
    }
}
