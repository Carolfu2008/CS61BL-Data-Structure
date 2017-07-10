import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;

public class PointUtils {

<<<<<<< HEAD
    /**
     * Returns the point with the largest Y value. If there are multiple such
     * points, just chooses one arbitrarily.
     */
    public static Point highestPoint(List<Point> points) {
        Iterator<Point> pointIterator = points.iterator();

        // TODO use the iterator to complete this method!
        Point top = new Point(0, 0);
        while (pointIterator.hasNext()) {
            Point tmp = pointIterator.next();
            if (tmp.getY() > top.getY())
                top = tmp;
        }
        return top;
    }

    /**
     * Returns a new point that is the centroid of all the given points. A
     * centroid has an X value that is the average of all the given points' X
     * values, and a Y value that is the average of all the given points' Y
     * values.
     */
    public static Point centroid(List<Point> points) {
        Iterator<Point> pointIterator = points.iterator();
        // TODO use the iterator to complete this method!
        int sumx = 0;
        int sumy = 0;
        int cnt = 0;
        while (pointIterator.hasNext()) {
            Point tmp = pointIterator.next();
            sumx += tmp.getX();
            sumy += tmp.getY();
            cnt++;
        }
        return new Point(sumx / cnt, sumy / cnt);
    }

    public static void main(String[] args) {
        List<Point> points = new LinkedList<>();
        points.add(new Point(1, 1));
        points.add(new Point(1, 3));
        points.add(new Point(3, 1));
        points.add(new Point(3, 3));

		/* Should be java.awt.Point[x=3,y=3] or java.awt.Point[x=1,y=3] */
        System.out.println(highestPoint(points));

        // Should be java.awt.Point[x=2,y=2]
        System.out.println(centroid(points));

        points = new LinkedList<>();
        points.add(new Point(1, 1));
        points.add(new Point(1, -1));
        points.add(new Point(-1, 1));
        points.add(new Point(-1, -1));

		/* Should be java.awt.Point[x=1,y=1] or java.awt.Point[x=-1,y=1] */
        System.out.println(highestPoint(points));

        // Should be java.awt.Point[x=0,y=0]
        System.out.println(centroid(points));
    }
=======
	/**
	 * Returns the point with the largest Y value. If there are multiple such
	 * points, just chooses one arbitrarily.
	 */
	public static Point highestPoint(List<Point> points) {
		Iterator<Point> pointIterator = points.iterator();

		// TODO use the iterator to complete this method!
		return null;
	}

	/**
	 * Returns a new point that is the centroid of all the given points. A
	 * centroid has an X value that is the average of all the given points' X
	 * values, and a Y value that is the average of all the given points' Y
	 * values.
	 */
	public static Point centroid(List<Point> points) {
		Iterator<Point> pointIterator = points.iterator();
		// TODO use the iterator to complete this method!
		return null;
	}

	public static void main(String[] args) {
		List<Point> points = new LinkedList<>();
		points.add(new Point(1, 1));
		points.add(new Point(1, 3));
		points.add(new Point(3, 1));
		points.add(new Point(3, 3));

		/* Should be java.awt.Point[x=3,y=3] or java.awt.Point[x=1,y=3] */
		System.out.println(highestPoint(points));

		// Should be java.awt.Point[x=2,y=2]
		System.out.println(centroid(points));

		points = new LinkedList<>();
		points.add(new Point(1, 1));
		points.add(new Point(1, -1));
		points.add(new Point(-1, 1));
		points.add(new Point(-1, -1));

		/* Should be java.awt.Point[x=1,y=1] or java.awt.Point[x=-1,y=1] */
		System.out.println(highestPoint(points));

		// Should be java.awt.Point[x=0,y=0]
		System.out.println(centroid(points));
	}
>>>>>>> 9e92597fba06a07d2c16cda60bf07cb3963b985c
}
