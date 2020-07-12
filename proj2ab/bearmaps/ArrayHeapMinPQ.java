package bearmaps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private class Node {
        private T item;
        private double priority;
        Node(T item, double priority) {
            this.item = item;
            this.priority = priority;
        }
    }

    private ArrayList<Node> heap;
    private HashMap<T, Integer> items;

    public ArrayHeapMinPQ() {
        heap = new ArrayList<>();
        heap.add(new Node(null, -1.0 / 0.0));
        items = new HashMap<>();
    }

    private void swim(int k) {
        Node parent = heap.get(k / 2);
        Node self = heap.get(k);
        if (parent.priority > self.priority) {
            heap.set(k / 2, self);
            items.put(self.item, k / 2);
            heap.set(k, parent);
            items.put(parent.item, k);
            swim(k / 2);
        }
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        heap.add(new Node(item, priority));
        items.put(item, items.size() + 1);
        swim(items.size());
    }

    @Override
    public boolean contains(T item) {
        return items.containsKey(item);
    }

    @Override
    public T getSmallest() {
        if (items.isEmpty()) {
            throw new NoSuchElementException();
        }
        return heap.get(1).item;
    }

    private void sink(int k) {
        Node self = heap.get(k);
        int index1 = 2 * k;
        int index2 = 2 * k + 1;
        Node min;
        int index;
        if (index2 <= items.size()) {
            Node left = heap.get(index1);
            Node right = heap.get(index2);
            if (left.priority < right.priority) {
                min = left;
                index = index1;
            } else {
                min = right;
                index = index2;
            }
        } else if (index1 <= items.size()) {
            min = heap.get(index1);
            index = index1;
        } else {
            return;
        }

        if (self.priority > min.priority) {
            heap.set(k, min);
            items.put(min.item, k);
            heap.set(index, self);
            items.put(self.item, index);
            sink(index);
        }
    }

    @Override
    public T removeSmallest() {
        if (items.isEmpty()) {
            throw new NoSuchElementException();
        }
        Node min = heap.get(1);
        Node last = heap.get(items.size());
        if (min == last) {
            heap.remove(1);
            items.remove(min.item);
            return min.item;
        }
        heap.remove(items.size());
        heap.set(1, last);
        items.put(last.item, 1);
        items.remove(min.item);
        sink(1);
        return min.item;
    }

    @Override
    public int size() {
        return items.size();
    }


    /* Changes the priority of the given item. Throws NoSuchElementException if the item
     * doesn't exist. */
    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        }
        int index = items.get(item);
        Node self = heap.get(index);
        double oldpriority = self.priority;
        self.priority = priority;
        if (oldpriority > priority) {
            swim(index);
        } else {
            sink(index);
        }
    }
}
