import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;

public class Board {

    private int[][] bd;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        bd = new int[tiles.length][];
        for (int i = 0; i < tiles.length; i++) {
            bd[i] = Arrays.copyOf(tiles[i], tiles[i].length);
        }
    }
                                           
    // string representation of this board
    public String toString() {
        StringBuilder temp = new StringBuilder();
        temp.append(bd.length);
        temp.append("\n");
        for (int i = 0; i < bd.length; i++) {
            for (int j = 0; j < bd.length; j++) {
                temp.append(" ");
                temp.append(bd[i][j]);
                temp.append(" ");
            }
            temp.append("\n");
        }
        return temp.toString();
    }

    // board dimension n
    public int dimension() {
        return bd.length;
    }

    // number of tiles out of place
    public int hamming() {
        int count = 0;
        int k = 1;
        for (int i = 0; i < bd.length; i++) {
            for (int j = 0; j < bd.length; j++) {
                if (bd[i][j] != k && k != bd.length*bd.length) count++;
                k++;
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sum = 0;
        for (int i = 0; i < bd.length; i++) {
            for (int j = 0; j < bd.length; j++) {
                if (bd[i][j] != 0) {
                    sum += Math.abs(i - (bd[i][j]-1) / bd.length) + Math.abs(j - (bd[i][j]-1) % bd.length);
                }
            }
        }
        return sum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int k = 1;
        for (int i = 0; i < bd.length; i++) {
            for (int j = 0; j < bd.length; j++) {
                if (bd[i][j] != k % (bd.length*bd.length)) return false;
                k++;
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null || y.getClass() != this.getClass()) return false;
        return Arrays.deepEquals(bd, ((Board) y).bd);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> boards = new ArrayList<Board>();
        int x = 0;
        int y = 0;
        for (int i = 0; i < bd.length; i++) {
            for (int j = 0; j < bd.length; j++) {
                if (bd[i][j] == 0) {
                    x = i;
                    y = j;
                    break;
                }
            }
        }
        if (x > 0) {
            Board neighbor = new Board(bd);
            neighbor.bd[x][y] = neighbor.bd[x-1][y];
            neighbor.bd[x-1][y] = 0;
            boards.add(neighbor);
        }
        if (x < bd.length-1) {
            Board neighbor = new Board(bd);
            neighbor.bd[x][y] = neighbor.bd[x+1][y];
            neighbor.bd[x+1][y] = 0;
            boards.add(neighbor);
        }
        if (y > 0) {
            Board neighbor = new Board(bd);
            neighbor.bd[x][y] = neighbor.bd[x][y-1];
            neighbor.bd[x][y-1] = 0;
            boards.add(neighbor);
        }
        if (y < bd.length-1) {
            Board neighbor = new Board(bd);
            neighbor.bd[x][y] = neighbor.bd[x][y+1];
            neighbor.bd[x][y+1] = 0;
            boards.add(neighbor);
        }
        return boards;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board twin = new Board(bd);
        if (bd[0][0] != 0 && bd[0][1] != 0) {
            twin.bd[0][0] = bd[0][1];
            twin.bd[0][1] = bd[0][0];
        } else {
            twin.bd[1][0] = bd[1][1];
            twin.bd[1][1] = bd[1][0];
        }
        return twin;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        System.out.println(initial);
        System.out.println(initial.isGoal());
        Board flipped = initial.twin();
        Board flippedflipped = flipped.twin();
        String temp = "testing";
        System.out.println(flippedflipped);
        System.out.println("Equal? " + initial.equals(flippedflipped));
        for (Board board : initial.neighbors()) {
            System.out.println(board);
            System.out.println(board.isGoal());
        }
        System.out.println(initial.hamming());
        System.out.println(initial.manhattan());
        System.out.println(initial.equals(temp));
    }

}