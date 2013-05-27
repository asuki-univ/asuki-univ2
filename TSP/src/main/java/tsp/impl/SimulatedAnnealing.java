package tsp.impl;

import java.util.List;
import java.util.Random;

import tsp.Point;

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
            currentTemperature *= 0.998;
        }

        System.out.printf("Current temp = %f\n", currentTemperature);

        if (currentTemperature <= 0)
            return super.step();

        final int N = currentState.size();

        // 適当に近傍を選ぶ。
        // 1. (i, j) を入れ替える
        // 2. (i, j) の間をreverseする
        // 3. i を j の後に動かす or j を i の後に動かす、など。

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
        // TODO: 符号が多分違うのと、オーダーが微妙なのとの両方。
        if (d > 0 || random.nextDouble() < Math.exp(d / currentTemperature)) {
            currentState.swap(i, j);
            return true;
        }

        return false;
    }
}
