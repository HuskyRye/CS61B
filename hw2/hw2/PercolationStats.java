package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    Percolation percolation;
    double[] x;
    double mean;
    double stddev;
    double sqrtT;

    /** Perform T independent experiments on an N-by-N grid. */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        x = new double[T];
        int square = N * N;
        int row;
        int col;
        for (int i = 0; i < T; ++i) {
            percolation = pf.make(N);
            while (!percolation.percolates()) {
                do {
                    row = StdRandom.uniform(N);
                    col = StdRandom.uniform(N);
                } while (percolation.isOpen(row, col));
                percolation.open(row, col);
                x[i] += 1;
            }
            x[i] /= square;
        }
        mean = StdStats.mean(x);
        stddev = StdStats.stddev(x);
        sqrtT = Math.sqrt(x.length);
    }

    /** Sample mean of percolation threshold. */
    public double mean() {
        return mean;
    }

    /** Sample standard deviation of percolation threshold. */
    public double stddev() {
        return stddev;
    }

    /** Low endpoint of 95% confidence interval. */
    public double confidenceLow() {
        return mean - 1.96 * stddev / sqrtT;
    }

    /** High endpoint of 95% confidence interval. */
    public double confidenceHigh() {
        return mean + 1.96 * stddev / sqrtT;
    }

    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(1000, 1000, new PercolationFactory());
        System.out.println(ps.confidenceLow() + ", " + ps.confidenceHigh());
    }
}
