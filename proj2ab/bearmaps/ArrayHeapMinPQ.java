package bearmaps;

import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private class HeapNode {
        private T item;
        private double priority;

        private HeapNode(T i, double p) {
            item = i;
            priority = p;
        }
    }

    private HeapNode[] heap;
    private int size;
    private HashMap<T, Integer> indices;

    /** Constructor */
    public ArrayHeapMinPQ() {
        heap = new ArrayHeapMinPQ.HeapNode[10]; // Use an array to store a complete tree.
        size = 0; // Number of items in the PQ. Also the index of the next to-be-inserted heap node.
        indices = new HashMap<>(); // Used to quickly find the index of certain item in heap.
    }

    /** Swap heap node in index i and j. It also includes updates to
     * the indices map.*/
    private void swap(int i, int j) {
        // Swap i and j in heap.
        HeapNode temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;

        // Update indices for those two nodes as well.
        indices.put(heap[i].item, i);
        indices.put(heap[j].item, j);
    }

    /** Compare priority of heap node in index i and j.
     * Return a positive number if priority of node in index i is "larger than" that in index j;
     * zero if the same;
     * a negative number if "smaller than".
     */
    private double compare(int i, int j) { return heap[i].priority - heap[j].priority; }

    /** Resize the array heap if it is full (double the capacity), or
     * (approximately) 3/4 of it are empty (halve the capacity). If the
     * capacity is less than 100, then we won't shrink the capacity no matter what.*/
    private void resize() {
        HeapNode[] newHeap;

        if (size == heap.length) {
            newHeap = new ArrayHeapMinPQ.HeapNode[heap.length * 2];
        } else if (heap.length >= 100 && ((double) size / (double) heap.length <= 0.25)) {
            newHeap = new ArrayHeapMinPQ.HeapNode[heap.length / 2];
        } else {
            return;
        }

        // Copy all heap node from old heap to new size-adapted heap.
        for (int i = 0; i < size; i++) {
            newHeap[i] = heap[i];
        }
        heap = newHeap;
    }

    private int parentIndex(int i) { return (i - 1) / 2; }

    private int leftChildIndex(int i) { return i * 2 + 1; }

    private int rightChildIndex(int i) { return i * 2 + 2; }

    /** Repeatedly swap heap node in index i upwards to an appropriate place. */
    private void swimUp(int i) {
        while (i != 0 && compare(i, parentIndex(i)) < 0) {
            swap(i, parentIndex(i));
            i = parentIndex(i);
        }
    }

    /** Repeatedly swap heap node in index i downwards to an appropriate place. */
    private void sinkDown(int i) {
        while (leftChildIndex(i) <= size - 1) {
            // Find the smaller child of node in index i.
            int smallerChild = leftChildIndex(i);
            if (rightChildIndex(i) <= size - 1 && compare(leftChildIndex(i), rightChildIndex(i)) > 0) {
                smallerChild = rightChildIndex(i);
            }

            // Return if node in index i is "smaller than or equal to" the smaller child.
            if (compare(i, smallerChild) <= 0) {
                return;
            } else {
                swap(i, smallerChild);
                i = smallerChild;
            }
        }
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }

        // Append a new node to heap.
        heap[size] = new HeapNode(item, priority);
        size += 1;
        indices.put(item, size - 1);

        // Swim up this new node to the appropriate place, and resize (potentially).
        swimUp(size - 1);
        resize();
    }

    /* Returns true if the PQ contains the given item. */
    @Override
    public boolean contains(T item) {
        return indices.containsKey(item);
    }

    /* Returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    @Override
    public T getSmallest() {
        return heap[0].item;
    }

    /* Removes and returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    @Override
    public T removeSmallest() {
        T result = getSmallest();

        // Swap the root node with the last node, and remove
        // the last node after the swap.
        swap(0, size - 1);
        indices.remove(heap[size - 1].item);
        size -= 1;

        // Sink down new root node to the appropriate place, and resize (potentially).
        sinkDown(0);
        resize();

        return result;
    }

    /* Returns the number of items in the PQ. */
    @Override
    public int size() {
        return size;
    }

    /* Changes the priority of the given item. Throws NoSuchElementException if the item
     * doesn't exist. */
    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        }

        int idx = indices.get(item);
        double oldPriority = heap[idx].priority;

        if (oldPriority != priority) {
            heap[idx].priority = priority;
            // Swim up or sink down depending on whether new priority
            // is smaller or larger than old priority respectively.
            if (priority > oldPriority) {
                sinkDown(idx);
            } else {
                swimUp(idx);
            }
        }
    }
}
