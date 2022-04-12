import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.lang.Math;

public class PercolationStats {

    private double[] x;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException("value is non-positive");
        x = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation crap = new Percolation(n);
            while (!crap.percolates()) {
                int row = StdRandom.uniform(1,n+1);
                int col = StdRandom.uniform(1,n+1);
                crap.open(row,col);
            }
            x[i] = (double)crap.numberOfOpenSites()/(n*n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(x);
        //double temp = 0;
        //for (int i = 0; i < x.length; i++) {
        //    temp += x[i];
        //}
        //return temp/(double)x.length;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(x);
        //double temp = 0;
        //for (int i = 0; i < x.length; i++) {
        //    temp += (x[i]-mean()) * (x[i]-mean());
        //}
        //temp /= (double)x.length-1;
        //return Math.sqrt(temp);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(x.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(x.length);
    }

   // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats test = new PercolationStats(n, t);
        System.out.println("mean                    = " + test.mean());
        System.out.println("stddev                  = " + test.stddev());
        System.out.println("95% confidence interval = [" + test.confidenceLo() + ", " + test.confidenceHi() + "]");
    }
}
