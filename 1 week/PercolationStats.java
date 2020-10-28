/* *****************************************************************************
 *  Name:              Vladyslav Tverdokhlib
 *  Coursera User ID:
 *  Last modified:     27/08/2020
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final double[] threshold;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("there is no data for computational");
        // thresholds array initialization
        threshold = new double[trials];
        // creating of trials
        Percolation grid;
        for (int i = 0; i < trials; i++) {
            grid = new Percolation(n);
            while (!grid.percolates()) {
                grid.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
            }
            threshold[i] = grid.numberOfOpenSites() / (double) (n * n);
        }
    }

    public double mean() {
        return StdStats.mean(threshold);
    }

    public double stddev() {
        return StdStats.stddev(threshold);
    }

    public double confidenceLo() {
        double x = mean();
        double s = stddev();
        int t = threshold.length;
        return x - ((CONFIDENCE_95 * s) / Math.sqrt(t));
    }

    public double confidenceHi() {
        double x = mean();
        double s = stddev();
        int t = threshold.length;
        return x + ((CONFIDENCE_95 * s) / Math.sqrt(t));
    }

    public static void main(String[] args) {
        PercolationStats test = new PercolationStats(Integer.parseInt(args[0]),
                                                     Integer.parseInt(args[1]));
        System.out.println("mean = " + test.mean());
        System.out.println("stddev = " + test.stddev());
        System.out.println(
                "95% confidence interval = [" + test.confidenceLo() + ", " + test.confidenceHi()
                        + "]");
    }
}
