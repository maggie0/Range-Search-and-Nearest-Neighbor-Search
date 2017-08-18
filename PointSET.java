import java.util.TreeSet; 
import java.util.Stack;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;

public class PointSET { 
    private TreeSet<Point2D> pointSet;
    
    public PointSET() { // construct an empty set of points 
        pointSet = new TreeSet<>();
    }
    
    public boolean isEmpty() { // is the set empty? 
        return pointSet.isEmpty();
    }
    
    public int size() { // number of points in the set 
        return pointSet.size();
    }
    
    public void insert(Point2D p) { // add the point to the set (if it is not already in the set) 
        validation(p);
        pointSet.add(p);
    }
    
    public boolean contains(Point2D p) { // does the set contain point p? 
        validation(p);
        return pointSet.contains(p);
    }
    
    public void draw() { // draw all points to standard draw 
        StdDraw.setXscale(-0.05, 1.05);
        StdDraw.setYscale(-0.05, 1.05);
        for (Point2D p : pointSet) {
            StdDraw.point(p.x(), p.y());
        }
    }
    
    public Iterable<Point2D> range(RectHV rect) { // all points that are inside the rectangle 
        validation(rect);
        Stack<Point2D> insidePoints = new Stack<>();
        for (Point2D p : pointSet) {
            if (rect.contains(p))
                insidePoints.push(p);
        }
        return insidePoints;
    }
    
    public Point2D nearest(Point2D p) { // a nearest neighbor in the set to point p; null if the set is empty 
        validation(p);
        if (isEmpty())
            return null;
        double minSqrDist = Double.POSITIVE_INFINITY;
        Point2D nearestPoint = null;
        for (Point2D point : pointSet) {
            if (Double.compare(point.distanceSquaredTo(p), minSqrDist) < 0) {
                minSqrDist = point.distanceSquaredTo(p);
                nearestPoint = point;
            }
        }
        return nearestPoint;
    }
    
    private void validation(Object that) {
        if (that == null)
            throw new NullPointerException("Null argument");
    }
    
    public static void main(String[] args) { // unit testing of the methods (optional) 
        String filename = args[0];
        In in = new In(filename);

        StdDraw.enableDoubleBuffering();

        // initialize the data structures with N points from standard input
        PointSET brute = new PointSET();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            brute.insert(p);
        }

        double x0 = 0.0, y0 = 0.0;      // initial endpoint of rectangle
        double x1 = 0.0, y1 = 0.0;      // current location of mouse
        boolean isDragging = false;     // is the user dragging a rectangle

        // draw the points
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        brute.draw();
        StdDraw.show();
    }
}