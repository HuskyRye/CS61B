package es.datastructur.synthesizer;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
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
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
    }

    /** Return size of the ring buffer. */
    @Override
    public int capacity() {
        return rb.length;
    }

    @Override
    /** Return number of items currently in the ring buffer. */
    public int fillCount() {
        return fillCount;
    }

    /** Returns the index immediately after the given index. */
    private int plusOne(int index) {
        return index == rb.length - 1 ? 0 : index + 1;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[last] = x;
        last = plusOne(last);
        fillCount += 1;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T item = rb[first];
        first = plusOne(first);
        fillCount -= 1;
        return item;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        return rb[first];
    }

    private class ArrayRingBufferIterator implements Iterator<T> {
        private int index;
        public ArrayRingBufferIterator() {
            index = 0;
        }

        @Override
        public boolean hasNext() {
            return index < fillCount;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("The iteration has no next element.");
            }
            T item = rb[(first + index) % rb.length];
            index += 1;
            return item;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        ArrayRingBuffer<T> other = (ArrayRingBuffer<T>) obj;
        if (fillCount != other.fillCount) {
            return false;
        }
        for (int i = 0; i < fillCount; ++i) {
            if (rb[(first + i) % rb.length] != other.rb[(first + i) % rb.length]) {
                return false;
            }
        }
        return true;
    }
}
