package tsprunner;

import java.awt.Point;
import java.util.List;

import tsp.AbstractTSPSolver;
import tsp.impl.HillClimbing;

public class ConsoleRunner extends AbstractRunner {
	public static void main(String[] args) {
		try {
			final List<Point> points = loadProblem("problem/att48.tsp");

			//AbstractTSPSolver solver = new GA(points);
			AbstractTSPSolver solver = new HillClimbing(points);

			solver.run();
			solver.showFinalResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
