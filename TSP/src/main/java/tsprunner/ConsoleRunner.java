package tsprunner;

import java.awt.Point;
import java.util.List;

import tsp.GA;

public class ConsoleRunner extends AbstractRunner {
	public static void main(String[] args) {
		try {
			final List<Point> points = loadProblem("problem/att48.tsp");

			GA ga = new GA(points);
			ga.run();
			ga.showFinalResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
