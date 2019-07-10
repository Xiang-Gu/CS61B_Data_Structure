public class LinkedListDeque<T> implements Deque<T> {

    /** A private class that implements the node structure */
    private class LinkedNode {
        private T val;
        private LinkedNode prev;
        private LinkedNode next;

        LinkedNode(T val, LinkedNode prev, LinkedNode next) {
            this.val = val;
            this.prev = prev;
            this.next = next;
        }
    }

    private int size;
    private LinkedNode sentinel;

    /** constructor 1: instantiate an empty queue */
    public LinkedListDeque() {
        size = 0;
        sentinel = new LinkedNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

    /** constructor 2: deep copy of other */
    public LinkedListDeque(LinkedListDeque other) {
        size = 0;
        sentinel = new LinkedNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;

        for (int i = 0; i < other.size(); i++) {
            addLast((T) other.get(i));
        }
    }

    /** Add to the front of the queue */
    @Override
    public void addFirst(T item) {
        sentinel.next = new LinkedNode(item, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }

    /** Add to the end of the queue */
    @Override
    public void addLast(T item) {
        sentinel.prev = new LinkedNode(item, sentinel.prev, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size += 1;
    }

//    /** Check whether the queue is empty or not */
//    public boolean isEmpty() {
//        return size == 0;
//    }

    /** Return the current size of the queue */
    @Override
    public int size() {
        return size;
    }

    /** Print out elements in the queue from front to end,
     *  separated by a space
     */
    @Override
    public void printDeque() {
        LinkedNode ptr = sentinel.next;
        while (ptr != sentinel) {
            System.out.print(ptr.val);
            ptr = ptr.next;
        }
        System.out.print("\n");
    }

    /** Remove the element, if any, in the front of the queue */
    @Override
    public T removeFirst() {
        if (size != 0) {
            T result = sentinel.next.val;

            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size -= 1;

            return result;
        } else {
            // Error!!! Cannot remove from an empty queue
            System.out.println("Cannot remove anything from an empty queue");
            return null;
        }

    }

    /** Remove the element, if any, in the end of the queue */
    @Override
    public T removeLast() {
        if (size != 0) {
            T result = sentinel.prev.val;

            sentinel.prev.prev.next = sentinel;
            sentinel.prev = sentinel.prev.prev;
            size -= 1;

            return result;
        } else {
            // Error!!! Cannot remove from an empty queue
            System.out.println("Cannot remove anything from an empty queue");
            return null;
        }

    }

    /** Get the index-th element in the queue (we use 0-indexing) */
    @Override
    public T get(int index) {
        if (index >= 0 && index < size) {
            LinkedNode ptr = sentinel.next;
            while (index > 0) {
                ptr = ptr.next;
                index -= 1;
            }
            return ptr.val;
        } else {
            // Error!!! index should be [0, size-1]!
            System.out.println("Index should be in [0, " + (size - 1) + "] since there are "
                    + size + "elements in the queue now");
            return null;
        }

    }

    /** Get the index-th element in the queue (0-indexing is used), recursively */
    public T getRecursive(int index) {
        if (index >= 0 && index < size) {
            return getRucursiveHelper(sentinel.next, index);
        } else {
            // Error!!! index should be [0, size-1]!
            System.out.println("Index should be in [0, " + (size - 1) + "] since there are "
                    + size + "elements in the queue now");
            return null;
        }

    }

    /** Helper method of getRecursive that get the element at index position
     * of the queue starting from startOfQueue.
     */
    private T getRucursiveHelper(LinkedNode startOfQueue, int index) {
        if (index == 0) {
            return startOfQueue.val;
        } else {
            return getRucursiveHelper(startOfQueue.next, index - 1);
        }
    }

}