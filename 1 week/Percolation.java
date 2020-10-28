/* *****************************************************************************
 *  Name:              Vladyslav Tverdokhlib
 *  Coursera User ID:
 *  Last modified:     27/08/2020
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF grid;
    private final int number;
    private boolean[] status; // close (FALSE) or open (TRUE) site
    private int numberOfOpenSites = 0;
    private final int highVirtualID; // open sites embracing high node
    private final int lowVirtualID; // open sites embracing low node

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("n <= 0");
        number = n;
        grid = new WeightedQuickUnionUF(n * n + 2); // + 2 virtual nodes
        status = new boolean[n * n + 2];
        for (int i = 0; i < n * n; i++) {
            status[i] = false;
        }
        highVirtualID = n * n;
        lowVirtualID = n * n + 1;
        status[highVirtualID] = true; // highVirtualRoot = true. Other will join him
        status[lowVirtualID] = true; // lowVirtualRoot = true. Other will join him
    }

    public void open(int row, int col) {
        if (row < 1 || row > number || col < 1 || col > number)
            throw new IllegalArgumentException("outside of the range");
        int ind = indexDefinition(row, col);
        if (!status[ind]) {
            status[ind] = true;
            numberOfOpenSites++;

            if (!rightSide(col) && status[ind + 1])
                grid.union(ind, ind + 1);
            if (!leftSide(col) && status[ind - 1])
                grid.union(ind, ind - 1);
            if (!topSide(row) && status[ind - number])
                grid.union(ind, ind - number);
            if (!bottomSide(row) && status[ind + number])
                grid.union(ind, ind + number);

            if (topSide(row))
                grid.union(ind, highVirtualID);
            if (bottomSide(row))
                grid.union(ind, lowVirtualID);
        }
    }

    private boolean rightSide(int col) {
        if (col == number) return true;
        return false;
    }

    private boolean leftSide(int col) {
        if (col == 1) return true;
        return false;
    }

    private boolean topSide(int row) {
        if (row == 1) return true;
        return false;
    }

    private boolean bottomSide(int row) {
        if (row == number) return true;
        return false;
    }

    private int indexDefinition(int row, int col) {
        return (row - 1) * number + (col - 1);
    }

    public boolean isOpen(int row, int col) {
        if (row < 1 || row > number || col < 1 || col > number)
            throw new IllegalArgumentException("outside of the range");
        int ind = indexDefinition(row, col);
        if (status[ind])
            return true;
        return false;
    }

    public boolean isFull(int row, int col) {
        if (row < 1 || row > number || col < 1 || col > number)
            throw new IllegalArgumentException("outside of the range");
        int ind = indexDefinition(row, col);
        if (status[ind]) {
            if (grid.find(ind) == grid.find(highVirtualID))
                return true;
        }
        return false;
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    public boolean percolates() {
        if (grid.find(highVirtualID) == grid.find(lowVirtualID))
            return true;
        return false;
    }

    /* public static void main(String[] args) {

    } */
}
