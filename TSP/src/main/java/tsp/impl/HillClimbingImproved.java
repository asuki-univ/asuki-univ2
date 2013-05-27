package tsp.impl;

import java.util.ArrayList;
import java.util.List;

import tsp.AbstractTSPSolver;
import tsp.Point;
import tsp.TSPState;

public class HillClimbingImproved extends AbstractTSPSolver {
    public HillClimbingImproved(List<Point> points) {
        super(points);
    }

    @Override
    public void run() {
        if (this.currentState.getPoints().size() <= 3)
            return;

        while (step()) {
        }
    }

    public boolean step() {
        List<TSPState> newStates = new ArrayList<TSPState>();
        newStates.addAll(makeStatesForSwap());
        newStates.addAll(makeStatesForReverse());
        newStates.addAll(makeStatesForMove());

        boolean changed = false;
        TSPState nextState = currentState;
        for (int i = 0; i < newStates.size(); ++i) {
            if (newStates.get(i).getScore() < nextState.getScore()) {
                nextState = newStates.get(i);
                changed = true;
            }
        }

        this.currentState = nextState;
        return changed;
    }

    //
    List<TSPState> makeStatesForSwap() {
        List<TSPState> states = new ArrayList<TSPState>();

        List<Point> points = currentState.getPoints();
        for (int i = 0; i < points.size(); ++i) {
            for (int j = i + 1; j < points.size(); ++j) {
                TSPState s = new TSPState(currentState);
                s.swap(i, j);
                states.add(s);
            }
        }

        return states;
    }

    List<TSPState> makeStatesForReverse() {
        List<TSPState> states = new ArrayList<TSPState>();

        List<Point> points = currentState.getPoints();
        for (int i = 0; i < points.size(); ++i) {
            for (int j = i + 1; j < points.size(); ++j) {
                TSPState s = new TSPState(currentState);
                for (int a = i, b = j; a < b; ++a, --b) {
                    s.swap(a, b);
                }
                states.add(s);
            }
        }

        return states;
    }

    // 1, ..., i, j, ..., j-1, j+1, ...
    // j を i の後に持っていく
    List<TSPState> makeStatesForMove() {
        List<TSPState> states = new ArrayList<TSPState>();

        List<Point> points = currentState.getPoints();
        for (int i = 0; i < points.size(); ++i) {
            for (int j = 0; j < points.size(); ++j) {
                if (i == j)
                    continue;

                List<Point> newPoints = new ArrayList<Point>();
                for (int k = 0; k < points.size(); ++k) {
                    if (k == i) {
                        newPoints.add(points.get(k));
                        newPoints.add(points.get(j));
                    } else if (k == j) {
                        // skip
                    } else {
                        newPoints.add(points.get(k));
                    }
                }

                TSPState s = new TSPState(newPoints);
                states.add(s);
            }
        }

        return states;
    }
}
