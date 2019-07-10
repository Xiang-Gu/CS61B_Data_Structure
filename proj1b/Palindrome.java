public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> returnDeque = new ArrayDeque<>();

        for (int i = 0; i < word.length(); i++) {
            returnDeque.addLast(word.charAt(i));
        }

        return returnDeque;
    }

    public boolean isPalindrome(String word) {
        // base case: word's length is 0 or 1
        if (word.length() == 0 || word.length() == 1) {
            return true;
        } else {
            // otherwise, compare the first and last character and recursively call
            // isPalindrome on the remaining string.
            char firstChar = word.charAt(0);
            char lastChar = word.charAt(word.length() - 1);
            String remainingWord = word.substring(1, word.length()-1);
            return (firstChar == lastChar) && isPalindrome(remainingWord);
        }
    }

    /** Overload isPalindrome method so that user specific character comparator
     * will be used to compare the characters
     */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word.length() == 0 || word.length() == 1) {
            return true;
        } else {
            char firstChar = word.charAt(0);
            char lastChar = word.charAt(word.length() - 1);
            String remainingWord = word.substring(1, word.length()-1);
            return cc.equalChars(firstChar, lastChar) && isPalindrome(remainingWord, cc);
        }
    }

}
