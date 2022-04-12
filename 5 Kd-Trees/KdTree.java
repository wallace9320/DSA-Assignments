import java.util.ArrayList;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

    private Node root;
    private int sizeOfBST;
    private ArrayList<Point2D> pointsal;

    private class Node {
        Point2D point;
        RectHV rectangle;
        Node left, right;
        int level; // odd = compare x, even = compare y, root level is 1
        public Node(RectHV rec, int lev) {
            rectangle = rec;
            level = lev;
        }
    }

    private class Closest {
        Point2D point;
        double distance;
        public Closest(Point2D p, double d) {
            point = p;
            distance = d;
        }
    }

    // construct an empty set of points
    public KdTree() {
        root = null;
        sizeOfBST = 0;
    }

    // from insert()
    private Node put(Node x, Point2D p) {
        if (x.point == null) {
            sizeOfBST++;
            // 1 = compare x
            x.point = p;
            if (x.level % 2 != 0) {
                x.left = new Node(new RectHV(x.rectangle.xmin(), x.rectangle.ymin(), p.x(), x.rectangle.ymax()), x.level+1);
                x.right = new Node(new RectHV(p.x(), x.rectangle.ymin(), x.rectangle.xmax(), x.rectangle.ymax()), x.level+1);
            } else {
                x.left = new Node(new RectHV(x.rectangle.xmin(), x.rectangle.ymin(), x.rectangle.xmax(), p.y()), x.level+1);
                x.right = new Node(new RectHV(x.rectangle.xmin(), p.y(), x.rectangle.xmax(), x.rectangle.ymax()), x.level+1);
            }
        }
        if (x.point.equals(p)) return x;
        if (x.level % 2 != 0) {
            if (p.x() < x.point.x()) x.left = put(x.left, p);
            else x.right = put(x.right, p);
        } else {
            if (p.y() < x.point.y()) x.left = put(x.left, p);
            else x.right = put(x.right, p);
        }
        return x;
    }

    // from draw()
    private void drawaux(Node x) {
        StdDraw.setPenRadius();
        if (x.level % 2 != 0) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(x.left.rectangle.xmax(), x.left.rectangle.ymin(), x.left.rectangle.xmax(), x.left.rectangle.ymax());
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(x.left.rectangle.xmin(), x.left.rectangle.ymax(), x.left.rectangle.xmax(), x.left.rectangle.ymax());
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        x.point.draw();
        if (x.left.point != null) drawaux(x.left);
        if (x.right.point != null) drawaux(x.right);
    }

    // from contains()
    private boolean cont(Node r, Point2D p) {
        if (isEmpty() || r.point == null) return false;
        if (r.point.equals(p)) return true;
        if (r.level % 2 != 0) {
            if (p.x() < r.point.x()) return cont(r.left, p);
            else return cont(r.right, p);
        } else {
            if (p.y() < r.point.y()) return cont(r.left, p);
            else return cont(r.right, p);
        }
    }

    // from range()
    private void rangeaux(Node x, RectHV rect) {
        if (x.point == null) return;
        if (rect.contains(x.point)) pointsal.add(x.point);
        if (x.left.rectangle.intersects(rect)) rangeaux(x.left, rect);
        if (x.right.rectangle.intersects(rect)) rangeaux(x.right, rect);
    }

    // from nearest()
    private Closest nearestaux(Node x, Point2D p, Closest champ) {
        if (x.point == null) return champ;
        if (x.point.distanceSquaredTo(p) < champ.distance) {
            champ.point = x.point;
            champ.distance = x.point.distanceSquaredTo(p);
        }
        if (x.level % 2 != 0) {
            if (p.x() < x.point.x()) {
                champ = nearestaux(x.left, p, champ);
                if (champ.distance > x.right.rectangle.distanceSquaredTo(p)) {
                    champ = nearestaux(x.right, p, champ);
                }
            }
            else {
                champ = nearestaux(x.right, p, champ);
                if (champ.distance > x.left.rectangle.distanceSquaredTo(p)) {
                    champ = nearestaux(x.left, p, champ);
                }
            }
        } else {
            if (p.y() < x.point.y()) {
                champ = nearestaux(x.left, p, champ);
                if (champ.distance > x.right.rectangle.distanceSquaredTo(p)) {
                    champ = nearestaux(x.right, p, champ);
                }
            }
            else {
                champ = nearestaux(x.right, p, champ);
                if (champ.distance > x.left.rectangle.distanceSquaredTo(p)) {
                    champ = nearestaux(x.left, p, champ);
                }
            }
        }
        return champ;
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }
    
    // number of points in the set
    public int size() {
        return sizeOfBST;
    }
    
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("null argument");
        if (isEmpty()) {
            root = new Node(new RectHV(0, 0, 1, 1), 1);
        }
        root = put(root, p);
    }
    
    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("null argument");
        return cont(root, p);
    }
    
    // draw all points to standard draw
    public void draw() {
        drawaux(root);
    }
    
    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("null argument");
        pointsal = new ArrayList<>();
        if (!isEmpty()) rangeaux(root, rect);
        return pointsal;
    }
    
    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("null argument");
        if (isEmpty() || root.point == null) return null;
        return nearestaux(root, p, new Closest(root.point, root.point.distanceSquaredTo(p))).point;
    }
    
    // unit testing of the methods (optional)
    public static void main(String[] args) {
        KdTree tree = new KdTree();
        System.out.println(tree.size());
        System.out.println(tree.isEmpty());
        tree.insert(new Point2D(0.7, 0.2));
        System.out.println(tree.size());
        tree.insert(new Point2D(0.7, 0.2));
        System.out.println(tree.size());
        tree.insert(new Point2D(0.5, 0.4));
        System.out.println(tree.size());
        tree.insert(new Point2D(0.2, 0.3));
        System.out.println(tree.size());
        tree.insert(new Point2D(0.4, 0.7));
        System.out.println(tree.size());
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
