public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    /** Creates an empty array deque. */
    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 7;
        nextLast = 0;
    }

    /** Creates a deep copy of other. */
    public ArrayDeque(ArrayDeque other) {
        items = (T[]) new Object[other.items.length];
        System.arraycopy(other.items, 0, items, 0, other.items.length);
        size = other.size;
        nextFirst = other.nextFirst;
        nextLast = other.nextLast;
    }

    /** Returns the index immediately before the given index. */
    private int minusOne(int index) {
        return index == 0 ? items.length - 1 : index - 1;
    }

    /** Returns the index immediately after the given index. */
    private int plusOne(int index) {
        return index == items.length - 1 ? 0 : index + 1;
    }

    /** Resizes the underlying array to the target capacity. */
    private void resize() {
        int capacity;
        if (size == items.length) {
            capacity = size * 2;
        } else if (items.length >= 16 && (double) size / items.length < 0.25) {
            capacity = items.length / 2;
        } else {
            return;
        }
        T[] arr = (T[]) new Object[capacity];
        for (int i = 0; i < size; ++i) {
            arr[i] = get(i);
        }
        nextLast = size;
        nextFirst = capacity - 1;
        items = arr;
    }

    /** Adds an item on type T to the front of the deque. */
    @Override
    public void addFirst(T item) {
        resize();
        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size += 1;
    }

    /** Adds and item on type T to the back of the deque. */
    @Override
    public void addLast(T item) {
        resize();
        items[nextLast] = item;
        nextLast = plusOne(nextLast);
        size += 1;
    }

    /** Returns the number of items in the deque. */
    @Override
    public int size() {
        return size;
    }

    /** Prints the items in the deque. */
    @Override
    public void printDeque() {
        for (int i = 0; i < size; ++i) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque.
     *  If no such item exists, return null. */
    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        nextFirst = plusOne(nextFirst);
        T temp = items[nextFirst];
        items[nextFirst] = null;
        size -= 1;
        resize();
        return temp;
    }

    /** Removes and returns the item at the back of the deque.
     * If no such item exists, returns null. */
    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        nextLast = minusOne(nextLast);
        T temp = items[nextLast];
        items[nextLast] = null;
        size -= 1;
        resize();
        return temp;
    }

    /** Gets the item at the given index. If no such item exists, returns null.*/
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return items[(nextFirst + 1 + index) % items.length];
    }
}
