public class UnionFind {
    /* Parents of index. */
    private int[] parent;

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        parent = new int[n];
        for (int i = 0; i < n; ++i) {
            parent[i] = -1;
        }
    }

    /* Throws an exception if vertex is not a valid index. */
    private void validate(int vertex) {
        if (vertex < 0 || vertex >= parent.length) {
            throw new IllegalArgumentException();
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        validate(v1);
        int root = find(v1);
        return -parent[root];
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        validate(v1);
        return parent[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        validate(v1);
        validate(v2);
        return find(v1) == find(v2);
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a
       vertex with itself or vertices that are already connected should not
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        validate(v1);
        validate(v2);
        int root1 = find(v1);
        int root2 = find(v2);
        if (root1 == root2) {
            return;
        }
        int size1 = -parent[root1];
        int size2 = -parent[root2];
        if (size1 <= size2) {
            parent[root2] -= size1;
            parent[root1] = root2;
        } else {
            parent[root1] -= size2;
            parent[root2] = root1;
        }
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {
        validate(vertex);
        int p = vertex;
        while (parent[p] >= 0) {
            p = parent[p];
        }
        while (parent[vertex] >= 0 && parent[vertex] != p) {
            int temp = parent[vertex];
            parent[vertex] = p;
            vertex = temp;
        }
        return p;
    }
}
