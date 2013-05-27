package tsp.impl;

import java.util.List;
import java.util.Random;

import tsp.Point;
import tsp.TSPState;

public class SimulatedAnnealingImproved extends HillClimbingImproved {
    private double currentTemperature;
    private Random random;

    public SimulatedAnnealingImproved(List<Point> points) {
        super(points);
        currentTemperature = 10000;
        this.random = new Random();
    }

    @Override
    public void run() {
        if (currentState.size() <= 3)
            return;

        while (currentTemperature > 0) {
            step();
        }
    }

    public boolean step() {
        if (currentTemperature < 1) {
            currentTemperature = 0;
        } else {
            currentTemperature *= 0.9992;
        }

        System.out.printf("Current temp = %f\n", currentTemperature);

        if (currentTemperature <= 0)
            return super.step();

        double currentScore = currentState.getScore();

        TSPState neighborState = makeNeighborState();
        double neighborScore = neighborState.getScore();

        double scoreDiff = neighborScore - currentScore;
        System.out.println("scoreDiff = " + scoreDiff);
        if (scoreDiff >= 0)
            System.out.println("exp = " + Math.exp(-scoreDiff / currentTemperature * 10));

        if (scoreDiff < 0 || random.nextDouble() < Math.exp(-scoreDiff / currentTemperature * 10))
            currentState = neighborState;

        return true;
    }

    private TSPState makeNeighborState() {
        final List<TSPState> newStates;
        switch (random.nextInt(3)) {
        case 0:
            newStates = makeStatesForSwap();
            break;
        case 1:
            newStates = makeStatesForReverse();
            break;
        case 2:
            newStates = makeStatesForMove();
            break;
        default:
            throw new RuntimeException("Shouldn't happen");
        }

        return newStates.get(random.nextInt(newStates.size()));
    }
}
