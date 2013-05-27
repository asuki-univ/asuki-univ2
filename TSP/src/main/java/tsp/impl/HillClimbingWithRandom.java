package tsp.impl;

import java.util.List;
import java.util.Random;

import tsp.Point;

public class HillClimbingWithRandom extends HillClimbing {
    private Random random;

    public HillClimbingWithRandom(List<Point> points) {
        super(points);
        this.random = new Random();
    }

    @Override
    public boolean step() {
        // 適当にランダムに２つ選び、swap した場合にスコアがよくなるならば、swap する。
        // 何度か探してもうまくいかない場合は、全探索してスコアがよくなるものがあるかどうかをチェック。

        for (int t = 0; t < 100; ++t) {
            int i = random.nextInt(currentState.size() - 1) + 1;
            int j = random.nextInt(currentState.size() - 1) + 1;

            if (i == j)
                continue;

            if (j < i) {
                int tmp = i;
                i = j;
                j = tmp;
            }

            double improved = scoreImprovementIfSwapped(i, j);
            if (improved > 0) {
                currentState.swap(i, j);
                return true;
            }
        }

        return super.step();
    }
}
