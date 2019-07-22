import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/** A binary search tree (BST) based implementation of
 * the Map data structure.
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    /** A class that implements the nodes in a BST. */
    private class Node {
        private K key;
        private V value;
        private Node leftChild;
        private Node rightChild;


        public Node(K k, V v, Node left, Node right) {
            key = k;
            value = v;
            leftChild = left;
            rightChild = right;
        }

        public Node(K k, V v) {
            this(k, v, null, null);
        }
    }


    private Node root; // Reference to the root node of the BST.
    private int size; // Total number of key-value pairs in the map.


    /** Constructor: create an empty map. */
    public BSTMap() {
        root = null;
        size = 0;
    }


    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }


    /** Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        return containsKeyHelper(root, key);
    }

    /** A helper method to containsKey that recursively compare key with
     * current node's key and call the same method on either its left child
     * or right child accordingly.
     */
    private boolean containsKeyHelper(Node n, K key) {
        // base case
        if (n == null) {
            return false;
        }

        int compareResult = key.compareTo(n.key);
        if (compareResult == 0) {
            return true;
        } else if (compareResult < 0) {
            return containsKeyHelper(n.leftChild, key);
        } else {
            return containsKeyHelper(n.rightChild, key);
        }
    }


    /** Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(root, key);
    }

    /** A helper method of get method that did something very similar to containsKeyHelper
     * to find and return the value of a specified key, or null if that key is not contained.
     */
    private V getHelper(Node n, K key) {
        // base case
        if (n == null) {
            return null;
        }

        int compareResult = key.compareTo(n.key);
        if (compareResult == 0) {
            return n.value;
        } else if (compareResult < 0) {
            return getHelper(n.leftChild, key);
        } else {
            return getHelper(n.rightChild, key);
        }

    }


    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }


    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        // Note that the putHelper method considers the case when key is already
        // contained in the set which will effectively do nothing.
        root = putHelper(root, key, value);
        size += 1;
    }

    /** A helper method to put method. It uses an elegant recursive way to implement it. */
    private Node putHelper(Node n, K key, V value) {
        if (n == null) {
            n = new Node(key, value);
        } else if (key.compareTo(n.key) < 0) {
            n.leftChild = putHelper(n.leftChild, key, value);
        } else if (key.compareTo(n.key) > 0) {
            n.rightChild = putHelper(n.rightChild, key, value);
        }

        return n;
    }


    /** Print out the BST in order to increasing Key. A handy method used for debugging or visualization. */
    public void printInOrder() {
        printInOrderHelper(root);
    }

    /** A helper method to printInOrder method that recursively print out element
     * with in-order traversal so that output is sorted with increasing key.
     */
    private void printInOrderHelper(Node n) {
        if (n != null) {
            printInOrderHelper(n.leftChild);
            System.out.println(n.key + ": " + n.value + "\t");
            printInOrderHelper(n.rightChild);
        }
    }


    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> ks = new HashSet<>();
        keySetHelper(root, ks);
        return ks;
//        throw new UnsupportedOperationException("Unsupported operation, sorry!");
    }

    /** A helper method to keySet method that in-order traverse the nodes in
     * this BST rooted in n and stores the nodes' keys in ks.
     */
    private void keySetHelper(Node n, Set<K> ks) {
        if (n != null) {
            keySetHelper(n.leftChild, ks);
            ks.add(n.key);
            keySetHelper(n.rightChild, ks);
        }
    }


    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        V val = get(key);
        if (val != null) {
            root = removeHelper(root, key);
            size -= 1;
        }
        return val;
//        throw new UnsupportedOperationException("Unsupported operation, sorry!");
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        V val = get(key);
        if (val != null && value != null && val.equals(value)) {
            root = removeHelper(root, key);
            size -= 1;
        }
        return val;
//        throw new UnsupportedOperationException("Unsupported operation, sorry!");
    }

    /** A helper method to remove method that removes a node with
     * key from the (sub)tree rooted in n.
     */
    private Node removeHelper(Node n, K key) {
        if (n == null) {
            return n;
        } else if (key.compareTo(n.key) < 0) {
            n.leftChild = removeHelper(n.leftChild, key);
            return n;
        } else if (key.compareTo(n.key) > 0) {
            n.rightChild = removeHelper(n.rightChild, key);
            return n;
        } else {
            if (n.leftChild == null && n.rightChild == null) {
                // It is a leaf node.
                return null;
            } else if (n.leftChild == null) {
                // It has only right child.
                return n.rightChild;
            } else if (n.rightChild == null) {
                // It has only left child.
                return n.leftChild;
            } else {
                // Swap the predecessor with root n and delete predecessor.
                Node predecessor = findLargestNodeIn(n.leftChild);
                n.key = predecessor.key;
                n.value = predecessor.value;
                n.leftChild = removeHelper(n.leftChild, predecessor.key);
                return n;
            }
        }
    }

    /** A helper method to removeHelper method that finds the node with largest key in
     * the (sub)tree rooted in n (keep going right until there is none).
     */
    private Node findLargestNodeIn(Node n) {
        if (n != null) {
            while (n.rightChild != null) {
                n = n.rightChild;
            }
        }
        return n;
    }


    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException("Unsupported operation, sorry!");
    }
}
