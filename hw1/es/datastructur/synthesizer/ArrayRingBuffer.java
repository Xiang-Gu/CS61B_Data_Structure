package es.datastructur.synthesizer;
import java.util.Iterator;

public class ArrayRingBuffer<T> implements BoundedQueue<T>  {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update
        //       last.
        if (capacity() == fillCount()) {
            throw new RuntimeException("Ring buffer overflow");
        }

        rb[last] = x;
        last = (last + 1) % capacity();
        fillCount += 1;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and
        //       update first.
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }

        T res = rb[first];
        rb[first] = null;
        first = (first + 1) % capacity();
        fillCount -= 1;
        return res;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        // TODO: Return the first item. None of your instance variables should
        //       change.
        return rb[first];
    }

    /** return size of the buffer */
    @Override
    public int capacity() {
        return rb.length;

    }

    /** return number of items currently in the buffer */
    @Override
    public int fillCount() {
        return fillCount;

    }

    @Override
    public boolean equals(Object o) {
        ArrayRingBuffer other = (ArrayRingBuffer) o;

        // Iterate over both arrays and compare items one by one
        Iterator<T> it = iterator();
        Iterator<T> itOther = other.iterator();
        while (it.hasNext() && itOther.hasNext()) {
            T item = it.next();
            T itemOther = itOther.next();
            if (!item.equals(itemOther)) {
                return false;
            }
        }

        // If both reached the end of the array, return true;
        if (!it.hasNext() && !itOther.hasNext()) {
            return true;
        } else {
            // Otherwise, one array is longer than the other.
            return false;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    /** A private class that implements the Iterator interface so it can be used
     * as an Iterator type object to enable for-each loop syntax.
     */
    private class ArrayRingBufferIterator implements Iterator<T> {
        private int currentIdx;

        public ArrayRingBufferIterator() {
            // Initialize wizPos to the font of the queue.
            currentIdx = first;
        }

        @Override
        public boolean hasNext() {
            return currentIdx != last;
        }

        @Override
        public T next() {
            T res = rb[currentIdx];
            currentIdx = (currentIdx + 1) % capacity();
            return res;
        }
    }

    // TODO: When you get to part 4, implement the needed code to support
    //       iteration and equals.
}
    // TODO: Remove all comments that say TODO when you're done.
