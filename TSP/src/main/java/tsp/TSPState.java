package tsp;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TSPState {
    private final ArrayList<Point> points;
    private boolean scoreCalculated = false;
    private double score;

    public TSPState(List<Point> points) {
        this.points = new ArrayList<Point>(points);
        this.scoreCalculated = false;
        this.score = 0;
    }

    public TSPState(TSPState s) {
        this.points = new ArrayList<Point>(s.getPoints());
        this.scoreCalculated = s.scoreCalculated;
        this.score = s.score;
    }

    public int size() {
        return points.size();
    }

    public List<Point> getPoints() {
        return Collections.unmodifiableList(points);
    }

    public double getScore() {
        if (!scoreCalculated) {
            score = calculateScore();
            scoreCalculated = true;
        }

        return score;
    }

    private double calculateScore() {
        if (points.size() <= 1)
            return 0;

        double sumDistance = 0;
        for (int i = 1; i < points.size(); ++i) {
            Point p1 = points.get(i - 1);
            Point p2 = points.get(i);

            sumDistance += p1.distance(p2);
        }

        sumDistance += points.get(0).distance(points.get(points.size() - 1));
        return sumDistance;
    }

    // 点 P_i と P_j の距離を返す
    public double distance(int i, int j) {
        Point p1 = points.get(i % points.size());
        Point p2 = points.get(j % points.size());
        return p1.distance(p2);
    }

    // 点 Pi, Pj を入れ替える
    public void swap(int i, int j) {
        Point p = points.get(i);
        points.set(i, points.get(j));
        points.set(j, p);

        scoreCalculated = false;
    }
}
