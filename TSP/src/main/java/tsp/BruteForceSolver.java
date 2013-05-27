package tsp;

import java.util.List;

public class BruteForceSolver {
    private List<Point> points;
    private final int N;

    public BruteForceSolver(List<Point> points) {
        this.points = points;
        this.N = points.size();
    }

    public double solve() {
        boolean[] visited = new boolean[N];
        visited[0] = true;
        return iter(1, 0, visited);
    }

    private double iter(int nth, int lastIdx, boolean[] visited) {
        if (nth == N) {
            return d(0, lastIdx);
        }

        double result = Double.POSITIVE_INFINITY;
        for (int i = 0; i < N; ++i) {
            if (visited[i])
                continue;

            visited[i] = true;
            double r = iter(nth + 1, i, visited) + d(lastIdx, i);
            if (r < result)
                result = r;
            visited[i] = false;
        }

        return result;
    }

    private double d(int s, int t) {
        Point p1 = points.get(s);
        Point p2 = points.get(t);
        return p1.distance(p2);
    }
}
