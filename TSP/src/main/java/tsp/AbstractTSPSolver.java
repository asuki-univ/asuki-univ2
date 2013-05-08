package tsp;

import java.awt.Point;
import java.util.List;

public abstract class AbstractTSPSolver implements TSPSolver {
    protected TSPState currentState;

    protected AbstractTSPSolver(List<Point> points) {
        this.currentState = new TSPState(points);
    }

    @Override
    public final TSPState getBestState() {
        return currentState;
    }

    public final void showFinalResult() {
        System.out.println("Best found score: " + currentState.getScore());

        for (Point p : currentState.getPoints())
            System.out.println(p);
    }

    public abstract void run();
    public abstract boolean step();

}
