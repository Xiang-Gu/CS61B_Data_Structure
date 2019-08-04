import java.util.*;

public class MyTrieSet implements TrieSet61B {
    private class Node {
        private boolean isKey;
        private Map<Character, Node> children;

        public Node(boolean b) {
            isKey = b;
            children = new HashMap<>();
        }

        /** Return the node corresponding to character c */
        public Node childAt(char c) {
            return children.get(c);
        }

        /** Set child node corresponding to c to node n. */
        public void setChildToNode(char c, Node n) {
            children.put(c, n);
        }

        /** Return a set of character children of this node. */
        public Set<Character> keySet() {
            return children.keySet();
        }
    }

    private int size;
    private Node root;

    public MyTrieSet() {
        size = 0;
        root = new Node(false);
    }


    /** Clears all items out of Trie */
    public void clear() {
        size = 0;
        root = new Node(false);
    }

    /** Returns true if the Trie contains KEY, false otherwise */
    public boolean contains(String key) {
        Node n = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            n = n.childAt(c);
            // If it falls off the tree, return false.
            if (n == null) {
                return false;
            }
        }
        // There is a node along the tree for even the last character
        // of key. Return true if it is colored; false if it is not.
        return n.isKey;
    }

    /** Inserts string KEY into Trie */
    public void add(String key) {
        Node n = root;

        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (n.childAt(c) == null) {
                n.setChildToNode(c, new Node(false));
            }
            n = n.childAt(c);
        }
        // If the node for the last character is not colored (a newly created one
        // or an already existing one), set it to a colored node!
        if (!n.isKey) {
            n.isKey = true;
            size += 1;
        }
    }

    /** Returns a list of all words that start with PREFIX */
    public List<String> keysWithPrefix(String prefix) {
        List<String> res = new LinkedList<>();
        Node n = root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            n = n.childAt(c);
            // If prefix (or part of prefix) already falls off
            // the tree, result res (an empty one).
            if (n == null) {
                return res;
            }
        }
        // Collect all the keys in the tree rooted in n but
        // append prefix to all of the results.
        collectHelper(prefix, n, res);

        return res;
    }

    /** Collect all the keys in the (sub)tree rooted in n, append prefix s
     * to all of them, and store them in result.
     */
    private void collectHelper(String s, Node n, List<String> result) {
        if (n != null) {
            if (n.isKey) {
                result.add(s);
            }

            for (char c : n.keySet()) {
                collectHelper(s + c, n.childAt(c), result);
            }
        }

    }

    /** Returns the longest prefix of KEY that exists in the Trie
     * Not required for Lab 9. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }
}