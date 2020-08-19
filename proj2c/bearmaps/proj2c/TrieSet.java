package bearmaps.proj2c;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TrieSet {
    private static class Node {
        private boolean isKey = false;
        private HashMap<Character, Node> next = new HashMap<>();
    }

    private Node root;

    public TrieSet() {
        root = new Node();
    }

    /** Inserts string KEY into Trie */
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
    public List<String> keysWithPrefix(String prefix) {
        Node p = root;
        for (char c : prefix.toCharArray()) {
            if (!p.next.containsKey(c)) {
                return new LinkedList<>();
            }
            p = p.next.get(c);
        }
        List<String> list = new LinkedList<>();
        colHelper(prefix, list, p);
        return list;
    }

}
