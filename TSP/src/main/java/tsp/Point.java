package tsp;

public class Point {
    public double x, y;
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Point p) {
        double dx = x - p.x;
        double dy = y - p.y;

        double r = Math.sqrt((dx * dx + dy * dy) / 10);
        double t = Math.round(r);
        if (t < r)
            return t + 1;
        else
            return t;
    }
}