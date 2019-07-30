import javax.swing.*;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class MyHashMap<K, V> implements Map61B<K, V> {

    private class keyValuePair {
        private K key;
        private V value;

        public keyValuePair(K k, V val) {
            key = k;
            value = val;
        }
    }

    // The hash table with each cell being a bucket of key-value pairs
    // of the same hash code.
    private LinkedList[] ht;

    private int size;

    private final double loadFactorMax;

    // A hash set that stores all the keys in this set. Used for KeySet()
    // and iterator() method (kind of cheating...).
    private HashSet<K> allKeys;

    public MyHashMap() {
        this(16, 0.75);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, 0.75);
    }

    public MyHashMap(int initialSize, double loadFactorMax) {
        ht = new LinkedList[initialSize];
        size = 0;
        this.loadFactorMax = loadFactorMax;
        allKeys = new HashSet<>();
    }


    /**
     * Removes all of the mappings from this map.
     */
    public void clear() {
        // Nullify all entries in ht. Keep the size unchanged so
        // it might not the same as the one ht is initialized, but
        // this is fine in this lab.
        for(int i = 0; i < ht.length; i++) {
            ht[i] = null;
        }
        size = 0;
        allKeys = new HashSet<>();
    }

    /** Compute the hashed index of key in a hash table of length M. */
    private int getHashedIndex(K key, int M) {
        return (key.hashCode() & 0x7FFFFFFF) % M;
    }

    /**
     * Returns true if this map contains a mapping for the specified key.
     */
    public boolean containsKey(K key) {
        LinkedList<keyValuePair> ll = ht[getHashedIndex(key, ht.length)];
        // Scan each and every key-value pair in this bucket, and
        // compare them to key.
        if (ll != null) {
            for (keyValuePair p : ll) {
                if (p.key.equals(key)) {
                    return true;
                }
            }
        }

        // If ls is null or cannot find key after scanning it, then return false.
        return false;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key) {
        LinkedList<keyValuePair> ll = ht[getHashedIndex(key, ht.length)];

        if (ll != null) {
            for (keyValuePair p : ll) {
                if (p.key.equals(key)) {
                    return p.value;
                }
            }
        }

        return null;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    public int size() {
        return size;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    public void put(K key, V value) {
        LinkedList<keyValuePair> ll = ht[getHashedIndex(key, ht.length)];

        if (ll == null) {
            ll = ht[getHashedIndex(key, ht.length)] = new LinkedList<>();
        } else {
            for (keyValuePair p : ll) {
                // key is already in the map: update the value and return.
                if (p.key.equals(key)) {
                    p.value = value;
                    return;
                }
            }
        }

        // key is not in the map: add it to this bucket,
        // increment size by on, resize the hash table (if necessary).
        ll.add(new keyValuePair(key, value));
        size += 1;
        allKeys.add(key);
        resize();
    }

    /** Potentially resize the hash table when the load factor exceeds the loadFactorMax. */
    private void resize() {
        boolean needResize = ((double) size / (double) ht.length) >= loadFactorMax;

        if (needResize) {
            LinkedList[] newht =  new LinkedList[ht.length * 2];

            // Copy all pairs from current hash table to the new (larger) hash table.
            for (LinkedList<keyValuePair> ll : ht) {
                if (ll != null) {
                    for (keyValuePair p : ll) {
                        // Compute hashed index of key in the new hash table.
                        int indexInNewHT = getHashedIndex(p.key, newht.length);

                        // If that bucket is null, instantiate a new linked list.
                        if (newht[indexInNewHT] == null) {
                            newht[indexInNewHT] = new LinkedList();
                        }

                        // Add this pair in this bucket
                        newht[indexInNewHT].add(p);
                    }
                }
            }
            ht = newht;
        }
    }

    /**
     * Returns a Set view of the keys contained in this map.
     */
    public Set<K> keySet() {
        return allKeys;
    }

    public Iterator iterator() {
        return allKeys.iterator();
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    public V remove(K key) {
        LinkedList<keyValuePair> ll = ht[getHashedIndex(key, ht.length)];

        // If ll is not null, iterate over it and compare each
        if (ll != null) {
            for (keyValuePair p : ll) {
                if (p.key.equals(key)) {
                    V res = p.value;
                    ll.remove(p);
                    size -= 1;
                    return res;
                }
            }
        }
        // If ll is null, or did not find key in ll, then return null.
        return null;
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    public V remove(K key, V value) {
        throw new IllegalArgumentException();

    }
}