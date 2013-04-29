package tsp;

import java.awt.Point;
import java.util.List;
import java.util.Random;

public class SimulatedAnnealing extends HillClimbing {
    private double currentTemperature;
    private Random random;

    public SimulatedAnnealing(List<Point> points) {
        super(points);
        currentTemperature = 10000;
        this.random = new Random();
    }

    @Override
    public void run() {
        if (points.size() <= 3)
            return;

        while (currentTemperature > 0) {
            step();
        }
    }

    @Override
    public List<Point> getResult() {
        return points;
    }

    public boolean step() {
        if (currentTemperature < 1) {
            currentTemperature = 0;
        } else {
            currentTemperature *= 0.998;
        }

        System.out.printf("Current temp = %f\n", currentTemperature);

        if (currentTemperature <= 0)
            return super.step();

        final int N = points.size();

        // 適当に近傍を選ぶ。
        int i = random.nextInt(N - 1) + 1;
        int j = random.nextInt(N - 1) + 1;
        if (i == j)
            return false;

        if (j < i) {
            int t = i;
            i = j;
            j = t;
        }

        double d = scoreImprovementIfSwapped(i, j);
        if (d > 0 || random.nextDouble() < Math.exp(d / currentTemperature)) {
            swap(i, j);
            return true;
        }

        return false;
    }
}
