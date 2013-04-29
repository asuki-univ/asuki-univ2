package tsp;

import java.awt.Point;
import java.util.List;

public abstract class AbstractTSPSolver {
	protected final List<Point> points;

	protected AbstractTSPSolver(List<Point> points) {
		this.points = points;
	}

	public void showFinalResult() {
	    System.out.println("Best found score: " + getFinalScore());

	    for (Point p : getResult())
	        System.out.println(p);
	}

	public abstract void run();
	public abstract boolean step();
	public abstract List<Point> getResult();

	// Utility functions

    public double getFinalScore() {
        double sumDist = 0;

        List<Point> resultPoints = getResult();
        for (int i = 0; i < resultPoints.size(); ++i)
            sumDist += distance(i, i + 1);

        return sumDist;
    }

	// 点 P_i と P_j の距離を返す
    protected double distance(int i, int j) {
        Point p1 = points.get(i % points.size());
        Point p2 = points.get(j % points.size());
        return p1.distance(p2);
    }

    // 点 Pi, Pj を入れ替える
    protected void swap(int i, int j) {
        Point p = points.get(i);
        points.set(i, points.get(j));
        points.set(j, p);
    }
}
