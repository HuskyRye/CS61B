public class BubbleGrid {
    private int[][] grid;
    private final int rows;
    private final int columns;
    private UnionFind uf;

    /* Create new BubbleGrid with bubble/space locations specified by grid.
     * Grid is composed of only 1's and 0's, where 1's denote a bubble, and
     * 0's denote a space. */
    public BubbleGrid(int[][] grid) {
        this.grid = grid.clone();
        rows = grid.length;
        columns = grid[0].length;
    }

    /* Returns an array whose i-th element is the number of bubbles that
     * fall after the i-th dart is thrown. Assume all elements of darts
     * are unique, valid locations in the grid. Must be non-destructive
     * and have no side-effects to grid. */
    public int[] popBubbles(int[][] darts) {
        int[] result = new int[darts.length];
        for (int i = 0; i < darts.length; ++i) {
            int row = darts[i][0];
            int column = darts[i][1];
            if (grid[row][column] == 0) {
                result[i] = 0;
            } else {
                grid[row][column] = 0;
                result[i] = popBubble();
            }
        }
        return result;
    }

    private void connect() {
        for (int i = 1; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                if (grid[i][j] == 1) {
                    if (grid[i - 1][j] == 1) {
                        uf.union(index(i, j), index(i - 1, j));
                    }
                    if (j - 1 >= 0 && grid[i][j - 1] == 1) {
                        uf.union(index(i, j), index(i, j - 1));
                    }
                }
            }
        }
    }

    private int popBubble() {
        uf = new UnionFind(columns * rows);
        connect();
        int result = 0;
        for (int i = 1; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                if (grid[i][j] == 1 && uf.find(index(i, j)) >= columns) {
                    result += 1;
                }
            }
        }
        return result;
    }

    private int index(int row, int column) {
        return row * columns + column;
    }
}
