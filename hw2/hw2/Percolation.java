package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF uf2;
    private static final int[] dx = new int[]{0, 1, 0, -1};
    private static final int[] dy = new int[]{-1, 0, 1, 0};
    private final int N;
    private final int top;
    private final int bottom;
    private int openSites = 0;

    /** Create N-by-N grid, with all sites initially blocked. */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        grid = new boolean[N][N];
        uf = new WeightedQuickUnionUF(N * N + 2);
        uf2 = new WeightedQuickUnionUF(N * N + 1);
        this.N = N;
        top = N * N;
        bottom = N * N + 1;
        for (int i = 0; i < N; ++i) {
            uf.union(top, i);
            uf2.union(top, i);
            uf.union(bottom, N * N - N + i);
        }
    }

    private void validate(int n) {
        if (n < 0 || n >= N) {
            throw new IndexOutOfBoundsException();
        }
    }

    private int index(int row, int col) {
        return row * N + col;
    }

    /** Open the site (row, col) if it is not open already. */
    public void open(int row, int col) {
        validate(row);
        validate(col);
        if (!isOpen(row, col)) {
            grid[row][col] = true;
            openSites += 1;
            int index1 = index(row, col);
            for (int i = 0; i < 4; ++i) {
                int r = row + dy[i];
                int c = col + dx[i];
                if (0 <= r && r < N && 0 <= c && c < N && isOpen(r, c)) {
                    int index2 = index(r, c);
                    uf.union(index1, index2);
                    uf2.union(index1, index2);
                }
            }
        }
    }

    /** Is the site (row, col) open? */
    public boolean isOpen(int row, int col) {
        validate(row);
        validate(col);
        return grid[row][col];
    }

    /** Is the site (row, col) full? */
    public boolean isFull(int row, int col) {
        validate(row);
        validate(col);
        return isOpen(row, col) && uf2.connected(index(row, col), top);
    }

    /** Number of open sites. */
    public int numberOfOpenSites() {
        return openSites;
    }

    /** Does the system percolate? */
    public boolean percolates() {
        return uf.connected(top, bottom);
    }
    /** Use for unit testing. */
    public static void main(String[] args) {

    }
}
