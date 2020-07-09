package es.datastructur.synthesizer;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(4);
        assertTrue(arb.isEmpty());
        assertFalse(arb.isFull());
        assertEquals(4, arb.capacity());

        arb.enqueue(11);
        assertEquals((Integer) 11, arb.peek());
        assertEquals(1, arb.fillCount());
        assertFalse(arb.isEmpty());

        arb.enqueue(12);
        arb.enqueue(13);
        assertEquals((Integer) 11, arb.peek());
        assertEquals(3, arb.fillCount());
        assertFalse(arb.isFull());

        arb.enqueue(14);
        assertTrue(arb.isFull());
        assertEquals(4, arb.fillCount());
        assertEquals((Integer) 11, arb.peek());

        assertEquals((Integer) 11, arb.dequeue());
        assertEquals((Integer) 12, arb.peek());
        assertFalse(arb.isFull());
        assertEquals(3, arb.fillCount());
    }

    @Test
    // @Source: https://github.com/siqiaof/CS61B-Spring-2019/blob/master/homeworks/hw1/es/datastructur/synthesizer/TestArrayRingBuffer.java
    public void someTest2() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(3);
        assertTrue(arb.isEmpty());
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        assertEquals(1, (int) arb.dequeue());
        arb.enqueue(4);
        assertEquals(2, (int) arb.peek());

        ArrayRingBuffer<Integer> arb2 = new ArrayRingBuffer<>(3);
        arb2.enqueue(2);
        arb2.enqueue(3);
        assertFalse(arb2.equals(arb));
        arb2.enqueue(4);
    }

    @Test
    public void iteratorTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(4);
        arb.enqueue(0);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        Iterator<Integer> iterator = arb.iterator();
        for (int i = 0; i < 4; ++i) {
            assertEquals((Integer) i, iterator.next());
        }
        assertFalse(iterator.hasNext());
    }

    @Test
    public void equalsTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(4);
        arb.enqueue(0);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        assertTrue(arb.equals(arb));

        ArrayRingBuffer<Integer> equal = new ArrayRingBuffer<>(4);
        equal.enqueue(0);
        equal.enqueue(1);
        equal.enqueue(2);
        equal.enqueue(3);
        assertTrue(arb.equals(equal));

        ArrayRingBuffer<Integer> different = new ArrayRingBuffer<>(4);
        different.enqueue(0);
        different.enqueue(1);
        different.enqueue(2);
        assertFalse(arb.equals(different));
    }
}
