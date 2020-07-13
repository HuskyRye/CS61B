import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MyTrieSet implements TrieSet61B {
    private static class Node {
        private boolean isKey = false;
        private HashMap<Character, Node> next = new HashMap<>();
    }

    private Node root;

    public MyTrieSet() {
        clear();
    }

    /** Clears all items out of Trie */
    @Override
    public void clear() {
        root = new Node();
    }

    /** Returns true if the Trie contains KEY, false otherwise */
    @Override
    public boolean contains(String key) {
        Node p = root;
        for (char c : key.toCharArray()) {
            if (!p.next.containsKey(c)) {
                return false;
            }
            p = p.next.get(c);
        }
        return p.isKey;
    }

    /** Inserts string KEY into Trie */
    @Override
    public void add(String key) {
        Node p = root;
        for (char c : key.toCharArray()) {
            if (!p.next.containsKey(c)) {
                p.next.put(c, new Node());
            }
            p = p.next.get(c);
        }
        p.isKey = true;
    }

    private void colHelper(String s, List<String> x, Node n) {
        if (n.isKey) {
            x.add(s);
        }
        for (Map.Entry<Character, Node> e : n.next.entrySet()) {
            colHelper(s + e.getKey(), x, e.getValue());
        }
    }

    /** Returns a list of all words that start with PREFIX */
    @Override
    public List<String> keysWithPrefix(String prefix) {
        Node p = root;
        for (char c : prefix.toCharArray()) {
            if (!p.next.containsKey(c)) {
                return null;
            }
            p = p.next.get(c);
        }
        List<String> list = new LinkedList<>();
        colHelper(prefix, list, p);
        return list;
    }

    /** Returns the longest prefix of KEY that exists in the Trie
     * Not required for Lab 9. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }
}
