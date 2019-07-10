import java.util.Objects;

public class ArrayDeque<T> implements Deque<T> {
    private T[] arr;
    private int size;
    private int nextFirst; // position for the next element to be inserted to the front of the queue
    private int nextLast; // position for the next element to be inserted to the end of the queue
    private double loadFactor; // ratio of size to arr.length

    /** Constructor 1: instantiate an empty array */
    public ArrayDeque() {
        arr = (T[]) new Object[8];
        size = 0;

        // Let the queue grow from the middle of the underlying array container.
        nextFirst = arr.length / 2 - 1;
        nextLast = nextFirst + 1;

        loadFactor = 0.;
    }

    /** Constructor 2: deep copy from other */
    public ArrayDeque(ArrayDeque other) {
        // Instantiate an empty queue
        this();

        // Add each element of other to this queue
        for (int i = 0; i < other.size(); i++) {
            addLast((T) other.get(i));
        }
    }

    /** Extend or shrink the internal array when the array is full or
     * the loadFactor is below 25% respectively. We place the elements
     * in the middle of the new array.
     */
    private void resize() {
        T[] temp;

        if (size() == arr.length - 1) {
            // Array is full. Double its capacity!
            temp = (T[]) new Object[arr.length * 2];
        } else if (loadFactor < 0.25 && arr.length >= 16) {
            // Array is too large. Shrink the capacity by half.
            temp = (T[]) new Object[arr.length / 2];
        } else {
            return;
        }

        // Place the elements to the middle of the newly created array
        int newNextFirst = temp.length / 4;
        int newNextLast = newNextFirst + size() + 1;
        for (int i = 0; i < size(); i++) {
            temp[newNextFirst + 1 + i] = get(i);
        }

        arr = temp;
        nextFirst = newNextFirst;
        nextLast = newNextLast;
        loadFactor = (double) size / arr.length;
    }

    /** Add an element to the front of the queue */
    @Override
    public void addFirst(T item) {
        // Check whether we need to resize (double) the array before adding the element.
        resize();

        arr[nextFirst] = item;
        nextFirst = (nextFirst - 1) % arr.length;
        size += 1;
        loadFactor = (double) size / arr.length;
    }

    /** Add an element to the end of the queue */
    @Override
    public void addLast(T item){
        // Check whether we need to resize (double) the array before adding the element.
        resize();

        arr[nextLast] = item;
        nextLast = (nextLast + 1) % arr.length;
        size += 1;
        loadFactor = (double) size / arr.length;
    }

//    /** Check whether the queue is empty or not */
//    public boolean isEmpty(){
//        return nextFirst == (nextLast - 1) % arr.length;
//    }

    /** Return the number of elements in the queue */
    @Override
    public int size() {
        return size;
    }

    /** Print the elements in the queue from front to end, separated by space */
    @Override
    public void printDeque() {
        for (int i = (nextFirst + 1) % arr.length; i != nextLast; i = (i + 1) % arr.length) {
            System.out.print(arr[i]);
        }
        System.out.print("\n");
    }

    /** Remove the element, if any, in the front of the queue */
    @Override
    public T removeFirst() {
        if (size != 0) {
            T result = arr[(nextFirst + 1) % arr.length];

            // Remove the first element by simply increment nextFirst
            // and nullify arr[nextFirst].
            nextFirst = (nextFirst + 1) % arr.length;
            arr[nextFirst] = null;
            size -= 1;
            loadFactor = (double) size / arr.length;

            // Check whether we need to resize (shrink) the array after removing the element.
            resize();

            return result;
        } else {
            throw new RuntimeException("Cannot remove element from an empty queue.");
        }
    }

    /** Remove the element, if any, in the end of the queue */
    @Override
    public T removeLast() {
        if (size != 0) {
            T result = arr[(nextLast - 1) % arr.length];

            // Remove the last element by simply decrement nextLast
            // and nullify arr[nextLast].
            nextLast = (nextLast - 1) % arr.length;
            arr[nextLast] = null;
            size -= 1;
            loadFactor = (double) size / arr.length;

            // Check whether we need to resize (shrink) the array after removing the element.
            resize();

            return result;
        } else {
            throw new RuntimeException("Cannot remove element from an empty queue.");
        }
    }

    /** Return the index-th element (0-indexing is used) in the queue */
    @Override
    public T get(int index) {
        if (index >= 0 && index < size) {
            return arr[(nextFirst + index + 1) % arr.length];
        }
        else {
            throw new ArrayIndexOutOfBoundsException("Index should be in [0, " + (size-1) + "] " +
                    "since the length of the queue is " + size + ", but given: " + index);
        }
    }
}