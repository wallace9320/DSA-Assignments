import java.util.Comparator;
import java.util.Collections;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private final ArrayList<Board> sequence = new ArrayList<Board>();
    private boolean solvable = false;

    private class BoardComparator implements Comparator<Node> {
        @Override
        public int compare(Node a, Node b) {
            if (a.man != b.man) return a.man - b.man;
            return b.movesMade - a.movesMade;
        }
    }

    private class Node {
        Board board;
        int movesMade;
        int man;
        Node prev;
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("Argument to the constructor is null");
        // Construction of two PQs
        MinPQ<Node> pq1 = new MinPQ<Node>(new BoardComparator());
        MinPQ<Node> pq2 = new MinPQ<Node>(new BoardComparator());
        // Initial board
        Node old1 = new Node();
        old1.board = initial;
        old1.movesMade = 0;
        old1.man = initial.manhattan();
        old1.prev = null;
        // Modified initial board
        Node old2 = new Node();
        old2.board = initial.twin();
        old2.movesMade = 0;
        old2.man = old2.board.manhattan();
        old2.prev = null;
        while (!old1.board.isGoal() && !old2.board.isGoal()) {
            // Normal
            for (Board x : old1.board.neighbors()) {
                if (old1.prev == null || !x.equals(old1.prev.board)) {
                    Node temp = new Node();
                    temp.board = x;
                    temp.prev = old1;
                    temp.movesMade = old1.movesMade + 1;
                    temp.man = x.manhattan() + temp.movesMade;
                    pq1.insert(temp);
                }
            }
            old1 = pq1.delMin();
            // Twin
            for (Board x : old2.board.neighbors()) {
                if (old2.prev == null || !x.equals(old2.prev.board)) {
                    Node temp = new Node();
                    temp.board = x;
                    temp.prev = old2;
                    temp.movesMade = old2.movesMade + 1;
                    temp.man = x.manhattan() + temp.movesMade;
                    pq2.insert(temp);
                }
            }
            old2 = pq2.delMin();
        }
        if (old1.board.isGoal()) {
            solvable = true;
            Node iterator = old1;
            while (iterator.prev != null) {
                sequence.add(iterator.board);
                iterator = iterator.prev;
            }
            sequence.add(iterator.board);
            Collections.reverse(sequence);
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) return -1;
        return sequence.size() - 1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        return sequence;
    }

    // test client (see below) 
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves() + "\n");
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}