public interface Deque<T> {
    /** Adds an item on type T to the front of the deque. */
    void addFirst(T item);

    /** Adds and item on type T to the back of the deque. */
    void addLast(T item);

    /** Returns if the deque is empty. */
    default boolean isEmpty() {
        return size() == 0;
    }

    /** Returns the number of items in the deque. */
    int size();

    /** Prints the items in the deque. */
    void printDeque();

    /** Removes and returns the item at the front of the deque.
     *  If no such item exists, return null. */
    T removeFirst();

    /** Removes and returns the item at the back of the deque.
     * If no such item exists, returns null. */
    T removeLast();

    /** Gets the item at the given index. If no such item exists, returns null.*/
    T get(int index);
}
