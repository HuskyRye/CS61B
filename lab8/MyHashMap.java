import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MyHashMap<K, V> implements Map61B<K, V> {
    private static class Node<K, V> {
        final K key;
        V value;
        Node<K, V> next;

        Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private int initialSize;
    private double loadFactor;

    private Node<K, V>[] buckets;
    private HashSet<K> keys;
    private int size;

    public MyHashMap() {
        this(16, 0.75);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, 0.75);
    }

    public MyHashMap(int initialSize, double loadFactor) {
        this.initialSize = initialSize;
        this.loadFactor = loadFactor;
        clear();
    }

    @Override
    public void clear() {
        buckets = (Node<K, V>[]) new Node[initialSize];
        keys = new HashSet<>();
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        return keys.contains(key);
    }


    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        int hashCode = key.hashCode();
        int index = hashCode & (buckets.length - 1);
        Node<K, V> p = buckets[index];
        while (p != null) {
            if (p.key.equals(key)) {
                return p.value;
            }
            p = p.next;
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    private void resize() {
        int newLength = buckets.length * 2;
        Node<K, V>[] newbuckets = (Node<K, V>[]) new Node[newLength];
        int hashCode;
        int index;
        Node<K, V> next;
        for (Node<K, V> p : buckets) {
            while (p != null) {
                hashCode = p.key.hashCode();
                index = hashCode & (newLength - 1);
                next = p.next;
                p.next = newbuckets[index];
                newbuckets[index] = p;
                p = next;
            }
        }
        buckets = newbuckets;
    }

    @Override
    public void put(K key, V value) {
        int hashCode = key.hashCode();
        int index = hashCode & (buckets.length - 1);
        Node<K, V> p = buckets[index];
        while (p != null) {
            if (p.key.equals(key)) {
                p.value = value;
                return;
            }
            p = p.next;
        }
        buckets[index] = new Node<>(key, value, buckets[index]);
        keys.add(key);
        size += 1;
        if ((double) size / buckets.length >= loadFactor) {
            resize();
        }
    }

    @Override
    public Set<K> keySet() {
        return keys;
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return keys.iterator();
    }
}
