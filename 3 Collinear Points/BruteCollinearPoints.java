import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import java.util.Arrays;

public class BruteCollinearPoints {

    private int segCount = 0;
    private LineSegment[] storage;


    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("null argument!");
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException("null point in array!");
        }
        Point[] newPoints = points.clone();
        Arrays.sort(newPoints);
        for (int i = 0; i < points.length-1; i++) {
            if (newPoints[i].compareTo(newPoints[i+1]) == 0) throw new IllegalArgumentException("repeated point in array!");
        }
        ArrayList<LineSegment> al = new ArrayList<LineSegment>();
        for (int i = 0; i < points.length; i++) {
            for (int j = i+1; j < points.length; j++) {
                for (int k = j+1; k < points.length; k++) {
                    for (int l = k+1; l < points.length; l++) {
                        if (points[i].slopeTo(points[j]) == points[j].slopeTo(points[k]) && points[j].slopeTo(points[k]) == points[k].slopeTo(points[l])) {
                            Point[] temp = new Point[4];
                            temp[0] = points[i];
                            temp[1] = points[j];
                            temp[2] = points[k];
                            temp[3] = points[l];
                            Arrays.sort(temp);
                            LineSegment line = new LineSegment(temp[0], temp[3]);
                            al.add(line);
                            segCount++;
                        }
                    }
                }
            }
        }
        storage = new LineSegment[segCount];
        for (int i = 0; i < segCount; i++) {
            storage[i] = al.get(i);
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segCount;
    }
    
    // the line segments
    public LineSegment[] segments() {
        return storage;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
    
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
    
        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}