import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.ArrayList;

public class PointSET {

    private final TreeSet<Point2D> tree;
   
    // construct an empty set of points
    public PointSET() {
        tree = new TreeSet<Point2D>();
    }
    // is the set empty? 
    public boolean isEmpty() {
        return tree.isEmpty();
    }
    
    // number of points in the set 
    public int size() {
        return tree.size();
    }
    
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("null argument");
        tree.add(p);
    }
    
    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("null argument");
        return tree.contains(p);
    }
    
    // draw all points to standard draw
    public void draw() {
        Iterator<Point2D> it = tree.iterator();
        while (it.hasNext()) {
            it.next().draw();
        }
    }
    
    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("null argument");
        ArrayList<Point2D> al = new ArrayList<>();
        Iterator<Point2D> it = tree.iterator();
        while (it.hasNext()) {
            Point2D x = it.next();
            if (rect.contains(x)) al.add(x);
        }
        return al;
    }
    
    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("null argument");
        if (isEmpty()) return null;
        ArrayList<Double> distance = new ArrayList<>();
        ArrayList<Point2D> points = new ArrayList<>();
        Iterator<Point2D> it = tree.iterator();
        while (it.hasNext()) {
            Point2D x = it.next();
            distance.add(x.distanceSquaredTo(p));
            points.add(x);
        }
        double min = distance.get(0);
        Point2D temp = points.get(0);
        for (int i = 1; i < distance.size(); i++) {
            if (distance.get(i) < min) {
                min = distance.get(i);
                temp = points.get(i);
            }
        }
        return temp;
    }
    
    // unit testing of the methods (optional)
    public static void main(String[] args) {
        PointSET tree = new PointSET();
        System.out.println(tree.size());
        System.out.println(tree.isEmpty());
        tree.insert(new Point2D(0.7, 0.2));
        tree.insert(new Point2D(0.5, 0.4));
        tree.insert(new Point2D(0.2, 0.3));
        tree.insert(new Point2D(0.4, 0.7));
        tree.insert(new Point2D(0.9, 0.6));
        System.out.println(tree.size());
        System.out.println(tree.isEmpty());
        Point2D p = tree.nearest(new Point2D(0.7, 0.1));
        System.out.println(p.x() + " " + p.y());
        System.out.println(tree.contains(new Point2D(0.5, 0.4)));
        System.out.println(tree.contains(new Point2D(0.5, 0.3)));
        Iterable<Point2D> iter = tree.range(new RectHV(0.2, 0.3, 0.6, 0.7));
        for (Point2D i : iter) {
            System.out.println(i.x() + " " + i.y());
        }
        tree.draw();
    }
}
