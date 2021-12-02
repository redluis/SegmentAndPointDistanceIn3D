import java.util.Scanner;

class Vector {
    double length;
    double x;
    double y;
    double z;

    Vector (Point a, Point b) {
        x = b.x - a.x;
        y = b.y - a.y;
        z = b.z - a.z;
        length = DistanceBetweenPointAndSegmentIn3D.segmentLength(a, b);
    }
}

class Point {
    double x;
    double y;
    double z;

    Point() {}

    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}

public class DistanceBetweenPointAndSegmentIn3D {
    static double segmentLength(Point a, Point b) {
        return Math.sqrt(
                          (b.x - a.x) * (b.x - a.x)
                        + (b.y - a.y) * (b.y - a.y)
                        + (b.z - a.z) * (b.z - a.z)
        );
    }

    static boolean isOn(Point f, Point b, Point e) {
        double BE = segmentLength(b, e);
        double FE = segmentLength(f, e);
        double BF = segmentLength(b, f);

        return BF + FE == BE;
    }

    static double findCos(Vector a, Vector b) {
        return (a.x * b.x + a.y * b.y + a.z * b.z) / (a.length + b.length);
    }

    static double dist(Point p, Point b, Point e) {
        // point is on a segment
        if (isOn(p, b, e)) {
            return 0;
        }

        // begin == end
        if (b.x == e.x && b.y == e.y && b.z == e.z) {
            return segmentLength(p, b);
        }

        double cosPEB = findCos(
                new Vector(e, p),
                new Vector(e, b)
        );

        double cosPBE = findCos(
                new Vector(b, p),
                new Vector(b, e)
        );

        if (cosPBE == -1 || cosPEB == -1) {
            return Math.min(
              segmentLength(p, e),
              segmentLength(p, b)
            );
        }

        if (cosPBE <= 0) {
            return segmentLength(p, b);
        }

        if (cosPEB <= 0) {
            return segmentLength(p, e);
        }


        double ta = segmentLength(b, e);
        double tb = segmentLength(b, p);
        double tc = segmentLength(e, p);
        double tp = (ta + tb + tc) / 2;

        return 2 / ta * Math.sqrt(tp * (tp - ta) * (tp - tb) * (tp - tc));
    }

    public static void main(String[] args) {
        Scanner inputStream = new Scanner(System.in);

        Point point = new Point(
                inputStream.nextDouble(),
                inputStream.nextDouble(),
                inputStream.nextDouble()
        );

        Point begin = new Point(
                inputStream.nextDouble(),
                inputStream.nextDouble(),
                inputStream.nextDouble()
        );

        Point end = new Point(
                inputStream.nextDouble(),
                inputStream.nextDouble(),
                inputStream.nextDouble()
        );

        double result = dist(point, begin, end);
        System.out.print(result);
    }
}
