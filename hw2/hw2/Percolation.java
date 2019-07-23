package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // Length of each side of the world.
    private int N;
    // A unionFind data structure used to model connected cells.
    // Cells are indexed from upper-left corner (index 0) to bottom-right (index N * N - 1)
    // in a left-to-right, top-to-bottom manner.
    private WeightedQuickUnionUF uf;
    // Used to solve the "backwash" problem. uf2 does not have the virtual bottom node.
    private WeightedQuickUnionUF uf2;

    // A flattened 1-D array that represents whether a certain cell at location [x * N + y]
    // is open or not.
    private boolean[] isOpen;
    private int numberOfOpenSites;
    private int indexOfVirtualTopNode;
    private int indexOfVirtualBottomNode;


    /** Create N-by-N grid, with all sites initially blocked. */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N must be larger than 0.");
        }

        this.N = N;
        uf = new WeightedQuickUnionUF(N * N + 2);
        uf2 = new WeightedQuickUnionUF(N * N + 1);
        indexOfVirtualTopNode = N * N;
        indexOfVirtualBottomNode = N * N + 1;
        isOpen = new boolean[N * N];
        numberOfOpenSites = 0;
    }

    /** Open the site (row, col) if it is not open already */
    public void open(int row, int col) {
        if (!isValidPos(row, col)) {
            throw new IllegalArgumentException();
        }

        if (!isOpen[xyTo1D(row, col)]) {
            // Open location (row, col)
            isOpen[xyTo1D(row, col)] = true;
            numberOfOpenSites += 1;

            // Check neighboring four cells around this cell
            // and connect current cell to them if they are also open.
            // Above:
            if (isValidPos(row-1, col) && isOpen(row-1, col)) {
                uf.union(xyTo1D(row, col), xyTo1D(row-1, col));
                uf2.union(xyTo1D(row, col), xyTo1D(row-1, col));
            }
            // Below:
            if (isValidPos(row+1, col) && isOpen(row+1, col)) {
                uf.union(xyTo1D(row, col), xyTo1D(row+1, col));
                uf2.union(xyTo1D(row, col), xyTo1D(row+1, col));
            }
            // Left:
            if (isValidPos(row, col-1) && isOpen(row, col-1)) {
                uf.union(xyTo1D(row, col), xyTo1D(row, col-1));
                uf2.union(xyTo1D(row, col), xyTo1D(row, col-1));
            }
            // Right:
            if (isValidPos(row, col+1) && isOpen(row, col+1)) {
                uf.union(xyTo1D(row, col), xyTo1D(row, col+1));
                uf2.union(xyTo1D(row, col), xyTo1D(row, col+1));
            }

            // If the cell is at the top row or bottom row, connect it
            // to the virtual top node or virtual bottom node respectively.
            if (row == 0) {
                uf.union(xyTo1D(row, col), indexOfVirtualTopNode);
                uf2.union(xyTo1D(row, col), indexOfVirtualTopNode);
            } else if (row == N - 1) {
                uf.union(xyTo1D(row, col), indexOfVirtualBottomNode);
                // Since uf2 does not have virtual bottom node so does not need to
                // connect uf2 to it
            }
        }


    }

    /** Is the site (row, col) open? */
    public boolean isOpen(int row, int col) {
        if (!isValidPos(row, col)) {
            throw new IllegalArgumentException();
        }
        return isOpen[xyTo1D(row, col)];
    }

    /** is the site (row, col) full? */
    public boolean isFull(int row, int col) {
        if (!isValidPos(row, col)) {
            throw new IllegalArgumentException();
        }

        // Return true if it is connected to the virtual top node.
        // We use uf2 to prevent the "backwash" problem
        if (uf2.connected(xyTo1D(row, col), indexOfVirtualTopNode)) {
            return true;
        } else {
            return false;
        }
    }

    /** number of open sites */
    public int numberOfOpenSites()  {
        return numberOfOpenSites;
    }

    /** does the system percolate? */
    public boolean percolates() {
        // we use uf to efficiently check whether the world percolates or not.
        if (uf.connected(indexOfVirtualTopNode, indexOfVirtualBottomNode)) {
            return true;
        } else {
            return false;
        }
    }

    /** Determine whether (r, c) is a valid location or not. */
    private boolean isValidPos(int r, int c) {
        if (r < 0 || r >= N || c < 0 || c >= N) {
            return false;
        }
        return true;
    }

    /** Convert/flatten a 2-D location to a 1-D position. */
    private int xyTo1D(int r, int c) {
        return r * N + c;
    }

    /** use for unit testing (not required, but keep this here for the autograder) */
    public static void main(String[] args) {

    }



}
