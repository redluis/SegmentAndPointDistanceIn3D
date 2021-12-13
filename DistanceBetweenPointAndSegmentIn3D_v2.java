import java.util.Scanner;

class Vector {
    double x;
    double y;
    double z;

    Vector (Point3D a, Point3D b) {
        this.x = b.x - a.x;
        this.y = b.y - a.y;
        this.z = b.z - a.z;
    }

    public double vectorLength() {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }
}

class Point3D {
    public double x;
    public double y;
    public double z;

    Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point3D point3D = (Point3D) o;

        if (Double.compare(point3D.x, x) != 0) return false;
        if (Double.compare(point3D.y, y) != 0) return false;
        return Double.compare(point3D.z, z) == 0;
    }
}

enum PointDispositionCase {
    ON_SEGMENT,
    PERPENDICULAR_POSSIBLE,
    PERPENDICULAR_IMPOSSIBLE,
    BEGIN_AND_END_ARE_SAME,
}

class DistanceBetweenPointAndSegmentIn3DT {
    static public double findDistance(Point3D P, Point3D B, Point3D E) {
        PointDispositionCase pointDispositionCase =
                findPointDispositionCase(P, B , E);

        switch (pointDispositionCase) {
            case ON_SEGMENT:
                return 0;
            case BEGIN_AND_END_ARE_SAME:
                return segmentLength(P, B);
            case PERPENDICULAR_IMPOSSIBLE:
                return Math.min(
                        segmentLength(P, E),
                        segmentLength(P, B)
                );
            case PERPENDICULAR_POSSIBLE:
                return findDistanceByTriangleSquare(P, B, E);
        }

        return -1;
    }

    static private PointDispositionCase findPointDispositionCase(Point3D P, Point3D B, Point3D E) {
        double BE = segmentLength(B, E);
        double PE = segmentLength(P, E);
        double BP = segmentLength(B, P);

        if (BP + PE == BE) {
            return PointDispositionCase.ON_SEGMENT;
        }

        if (B.equals(E)) {
            return PointDispositionCase.BEGIN_AND_END_ARE_SAME;
        }

        double cosPEB = findCos(
                new Vector(E, P),
                new Vector(E, B)
        );

        double cosPBE = findCos(
                new Vector(B, P),
                new Vector(B, E)
        );

        if (cosPBE <= 0 || cosPEB <= 0) {
            return PointDispositionCase.PERPENDICULAR_IMPOSSIBLE;
        }

        return PointDispositionCase.PERPENDICULAR_POSSIBLE;
    }

    static private double segmentLength(Point3D a, Point3D b) {
        return Math.sqrt(
                (b.x - a.x) * (b.x - a.x)
                        + (b.y - a.y) * (b.y - a.y)
                        + (b.z - a.z) * (b.z - a.z)
        );
    }

    static private double findCos(Vector a, Vector b) {
        return (a.x * b.x + a.y * b.y + a.z * b.z) /
                (a.vectorLength() + b.vectorLength());
    }

    static private double findDistanceByTriangleSquare(Point3D P, Point3D B, Point3D E) {
        double a = segmentLength(B, E);
        double b = segmentLength(B, P);
        double c = segmentLength(E, P);
        double p = (a + b + c) / 2;

        return 2 / a * Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }
}

class DistanceBetweenPointAndSegmentIn3D {
    public static void main(String[] args) {
        Scanner inputStream = new Scanner(System.in);

        Point3D point = new Point3D(
                inputStream.nextDouble(),
                inputStream.nextDouble(),
                inputStream.nextDouble()
        );

        Point3D begin = new Point3D(
                inputStream.nextDouble(),
                inputStream.nextDouble(),
                inputStream.nextDouble()
        );

        Point3D end = new Point3D(
                inputStream.nextDouble(),
                inputStream.nextDouble(),
                inputStream.nextDouble()
        );

        double result = DistanceBetweenPointAndSegmentIn3DT.findDistance(point, begin, end);
        System.out.print(result);
    }
}
