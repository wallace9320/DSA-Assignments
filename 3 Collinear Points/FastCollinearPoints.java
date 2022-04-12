import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ArrayList;

public class FastCollinearPoints {

    private int segCount = 0;
    private LineSegment[] storage;

    // finds all line segments containing 4 points
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("Null argument!");
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException("Null point in array!");
        }
        Point[] newPoints = points.clone();
        Arrays.sort(newPoints);
        for (int i = 0; i < points.length-1; i++) {
            if (newPoints[i].compareTo(newPoints[i+1]) == 0) throw new IllegalArgumentException("Repeated point in array!");
        }
        ArrayList<LineSegment> al = new ArrayList<LineSegment>();
        for (int i = 0; i < points.length; i++) {
            Arrays.sort(newPoints, points[i].slopeOrder());
            double[] slopes = new double[points.length];
            for (int j = 0; j < points.length; j++) {
                slopes[j] = points[i].slopeTo(newPoints[j]);
            }
            int k = 0;
            while (k+1 < points.length) {
                int count = 0;
                if (slopes[k] != slopes[k+1]) k++;
                else {
                    int ref = k;
                    while (slopes[k] == slopes[k+1]) {
                        k++;
                        count++;
                        if (k+1 >= points.length) break;
                    }
                    if (count >= 2) {
                        Point[] temp = new Point[count+2];
                        temp[0] = points[i];
                        for (int j = 1; j < count+2; j++) {
                            temp[j] = newPoints[ref];
                            ref++;
                        }
                        Arrays.sort(temp);
                        if (points[i].compareTo(temp[0]) == 0) {
                            LineSegment line = new LineSegment(temp[0], temp[count+1]);
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}