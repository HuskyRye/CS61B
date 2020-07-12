package bearmaps;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArrayHeapMinPQTest {
    @Test
    public void basicTest() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        assertEquals(0, pq.size());
        for (int i = 1; i <= 100; ++i) {
            pq.add(String.valueOf(i), i);
            assertEquals(i, pq.size());
        }
        for (int i = 1; i <= 100; ++i) {
            assertTrue(pq.contains(String.valueOf(i)));
            assertEquals(String.valueOf(i), pq.getSmallest());
            assertEquals(String.valueOf(i), pq.removeSmallest());
        }

        pq = new ArrayHeapMinPQ<>();
        for (int i = 100; i >= 1; --i) {
            pq.add(String.valueOf(i), i);
        }
        for (int i = 1; i <= 100; ++i) {
            assertTrue(pq.contains(String.valueOf(i)));
            assertEquals(String.valueOf(i), pq.getSmallest());
            assertEquals(String.valueOf(i), pq.removeSmallest());
        }
    }

    @Test
    public void changePriorityTest() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        for (int i = 1; i <= 100; ++i) {
            pq.add(String.valueOf(i), 101);
        }
        for (int i = 1; i <= 100; ++i) {
            pq.changePriority(String.valueOf(i), 101 - i);
            assertEquals(String.valueOf(i), pq.getSmallest());
        }
    }

    @Test
    public void timeTest() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        NaiveMinPQ<String> npq = new NaiveMinPQ<>();
        for (int i = 1; i <= 1000000; ++i) {
            pq.add(String.valueOf(i), i);
            npq.add(String.valueOf(i), i);
        }
        int N = 1000;
        long start = System.currentTimeMillis();
        for (int i = 1; i <= N; ++i) {
            pq.removeSmallest();
        }
        long end = System.currentTimeMillis();
        System.out.println("ArrayHeapMinPQ: " + (end - start)/1000.0 +  " seconds.");

        start = System.currentTimeMillis();
        for (int i = 1; i <= N; ++i) {
            npq.removeSmallest();
        }
        end = System.currentTimeMillis();
        System.out.println("NaiveMinPQ: " + (end - start)/1000.0 +  " seconds.");
    }
}
