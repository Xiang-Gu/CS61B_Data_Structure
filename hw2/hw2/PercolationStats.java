package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {

    // Stores percolation rate for each simulation.
    private double[] percolationRates;

    /** perform T independent experiments on an N-by-N grid */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N and T must be larger than 0.");
        }

        percolationRates = new double[T];

        for (int t = 0; t < T; t++) {
            Percolation p = pf.make(N);

            // Keep open up cells until it percolates.
            while (!p.percolates()) {
                // Generate random cells to open.
                int x = StdRandom.uniform(N), y = StdRandom.uniform(N);
                // Open it. (If it is already open, open() will do nothing)
                p.open(x, y);
            }
            percolationRates[t] = (double) p.numberOfOpenSites() / (double) (N * N);
        }
    }

    /** sample mean of percolation threshold */
    public double mean() {
        return StdStats.mean(percolationRates);
    }

    /** sample standard deviation of percolation threshold */
    public double stddev() {
        return StdStats.stddev(percolationRates);
    }

    /** low endpoint of 95% confidence interval */
    public double confidenceLow() {
        double mean = mean();
        double stddev = stddev();
        return mean - 1.96 * stddev / Math.pow(percolationRates.length, 0.5);
    }

    /** high endpoint of 95% confidence interval */
    public double confidenceHigh() {
        double mean = mean();
        double stddev = stddev();
        return mean + 1.96 * stddev / Math.pow(percolationRates.length, 0.5);
    }

    public static void main(String[] args) {
        PercolationFactory pf = new PercolationFactory();
        int N = 300;
        int T = 2000;
        PercolationStats ps = new PercolationStats(N, T, pf);
        System.out.println("N = " + N + ", T = " + T + ":");
        System.out.println("Estimation percolation rate = " + ps.mean() + "\n Standard deviation " +
                "of the estimation = " + ps.stddev() + ", 95% confidence interval = [" +
                ps.confidenceLow() + ", " + ps.confidenceHigh() + "].\n");

    }


}
