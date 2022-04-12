import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int n;
    private int[] state;
    private WeightedQuickUnionUF setup;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("Not positive integer!");
        this.n = n;
        setup = new WeightedQuickUnionUF(n*n+2);
        state = new int[n*n+2];
        for (int i = 0; i < n*n; i++) {
            state[i] = 0;
        }
        state[n*n] = 1;
        state[n*n+1] = 1;
        for (int i = 0; i < n; i++) {
            setup.union(n*n, i);
            setup.union(n*n+1, n*(n-1)+i);
        }
        //System.out.println(setup.connected(n*n, n*n+1));
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) throw new IllegalArgumentException("Input error!");
        int index = (row-1)*n + col-1;
        state[index] = 1;
        if (row > 1) {if (isOpen(row-1, col)) setup.union(index, index-n);}
        if (row < n) {if (isOpen(row+1, col)) setup.union(index, index+n);}
        if (col > 1) {if (isOpen(row, col-1)) setup.union(index, index-1);}
        if (col < n) {if (isOpen(row, col+1)) setup.union(index, index+1);}
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) throw new IllegalArgumentException("Input error!");
        return state[(row-1)*n + col-1] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) throw new IllegalArgumentException("Input error!");
        int index = (row-1)*n + col-1;
        if (state[index] == 0) {
            return false;
        }
        // return setup.connected(n*n, index);
        return state[index] == 1 && setup.find(n*n) == setup.find(index);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int openSites = 0;
        for (int i = 0; i < state.length; i++) {
            if (state[i] == 1) {
                openSites++;
            }
        }
        return openSites-2;
    }

    // does the system percolate?
    public boolean percolates() {
        // return setup.connected(n*n, n*n+1);
        return setup.find(n*n) == setup.find(n*n+1);
    }

    // test client (optional)
    public static void main(String[] args) {
    }
}