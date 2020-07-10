import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private static class Entry<K extends Comparable<K>, V> {
        K key;
        V value;
        Entry<K, V> left;
        Entry<K, V> right;
        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    int size;
    private Entry<K, V> root;

    public BSTMap() {
        clear();
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    private Entry<K, V> getEntry(K key) {
        Entry<K, V> p = root;
        while (p != null) {
            int cmp = key.compareTo(p.key);
            if (cmp < 0) {
                p = p.left;
            } else if (cmp > 0) {
                p = p.right;
            } else {
                return p;
            }
        }
        return null;
    }
    
    /** Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        return getEntry(key) != null;
    }

    /** Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        Entry<K, V> p = getEntry(key);
        return (p == null ? null : p.value);
    }

    /** Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    /** Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        Entry<K, V> p = root;
        if (p == null) {
            root = new Entry<>(key, value);
            size = 1;
            return;
        }
        Entry<K, V> parent;
        int cmp;
        do {
            parent = p;
            cmp = key.compareTo(p.key);
            if (cmp < 0) {
                p = p.left;
            } else if (cmp > 0) {
                p = p.right;
            } else {
                p.value = value;
                return;
            }
        } while (p != null);
        Entry<K, V> e = new Entry<>(key, value);
        size += 1;
        if (cmp < 0) {
            parent.left = e;
        } else {
            parent.right = e;
        }
    }

    /** Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        // throw new UnsupportedOperationException();
        Set<K> keys = new TreeSet<>();
        for (K key : this) {
            keys.add(key);
        }
        return keys;
    }

    private Entry<K, V> successor(Entry<K, V> T) {
        Entry<K, V> p;
        if (T.right != null) {
            p = T.right;
            while (p.left != null) {
                p = p.left;
            }
            return p;
        } else if (T.left != null) {
            p = T.left;
            while (p.right != null) {
                p = p.right;
            }
            return p;
        } else {
            return null;
        }
    }

    private Entry<K, V> remove(Entry<K, V> T, K key) {
        Entry<K, V> p = root;
        Entry<K, V> next;
        if (p == null) {
            return null;
        }
        Entry<K, V> parent = p;
        int prevCmp = 0;
        while (p != null) {
            int cmp = key.compareTo(p.key);
            if (cmp < 0) {
                parent = p;
                p = p.left;
            } else if (cmp > 0) {
                parent = p;
                p = p.right;
            } else {
                if (p.left == null && p.right == null) {
                    next = null;
                } else if (p.left == null) {
                    next = p.right;
                } else if (p.right == null) {
                    next = p.left;
                } else {
                    next = successor(p);
                    if (next != null) {
                        remove(next.key);
                        size += 1;
                        next.left = p.left;
                        next.right = p.right;
                    }
                }
                if (prevCmp < 0) {
                    parent.left = next;
                } else if (prevCmp > 0){
                    parent.right = next;
                } else {
                    root = next;
                }
                size -= 1;
                return p;
            }
            prevCmp = cmp;
        }
        return null;
    }

    /** Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        Entry<K, V> r = remove(root, key);
        return (r == null ? null : r.value);
    }

    /** Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    private class keyIterator implements Iterator<K> {
        Stack<Entry<K, V>> entries;

        keyIterator() {
            entries = new Stack<>();
            inOrder(root);
        }

        private void inOrder(Entry<K, V> p) {
            while (p != null) {
                entries.push(p);
                p = p.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !entries.empty();
        }

        @Override
        public K next() {
            Entry<K, V> p = entries.pop();
            if (p.right != null) {
                inOrder(p.right);
            }
            return p.key;
        }
    }

    @Override
    public Iterator<K> iterator() {
        // throw new UnsupportedOperationException();
        return new keyIterator();
    }

    private void printHelper(Entry<K, V> T) {
        if (T == null) {
            return;
        }
        printHelper(T.left);
        System.out.println(T.key);
        printHelper(T.right);
    }

    public void printInOrder() {
        printHelper(root);
    }
}
