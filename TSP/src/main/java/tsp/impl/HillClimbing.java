package tsp.impl;

import java.util.List;

import tsp.AbstractTSPSolver;
import tsp.Point;

public class HillClimbing extends AbstractTSPSolver {
    public HillClimbing(List<Point> points) {
        super(points);
    }

    @Override
    public void run() {
        if (this.currentState.getPoints().size() <= 3)
            return;

        while (step()) {
        }
    }

    // 1 step 進め、解が改ざんされたならば true を返す。
    // ２つ選んで良くなるものを交換する、ということを繰り返す。
    // i, j が並んでいる時は、(j == i + 1 とする)
    // i-1, i, j, j+1 が、i-1, j, i, j + 1 になるので、
    //  -d(i-1, i)-d(i+1,i+2)
    //  +d(i-1, i+1)+d(i,i+2)

    // 並んでいないときは、
    // i-1, i, i + 1, ..., j - 1, j, j + 1
    // -d(i-1, i)-d(i, i+1)-d(j-1, j)-d(j, j+1)
    // +d(i-1, j)+d(j, i+1)+d(j-1, i)+d(i, j+1)

    public boolean step() {
        final int N = currentState.size();

        double maxImprove = 0;
        int iMaxImprove = -1, jMaxImprove = -1;

        // points[0] の位置は固定にする。
        for (int i = 1; i < N - 1; ++i) {
            for (int j = i + 1; j < N; ++j) {
                double improvedScore = scoreImprovementIfSwapped(i, j);
                if (maxImprove < improvedScore) {
                    maxImprove = improvedScore;
                    iMaxImprove = i;
                    jMaxImprove = j;
                }
            }
        }

        System.out.printf("maxImprove = %f, i = %d, j = %d\n",  maxImprove, iMaxImprove, jMaxImprove);

        if (iMaxImprove <= 0)
            return false;

        currentState.swap(iMaxImprove, jMaxImprove);
        return true;
    }

    public double scoreImprovementIfSwapped(int i, int j) {
        assert (i < j);
        if (j == i + 1) {
            double removedDist = currentState.distance(i-1,i) + currentState.distance(i+1,i+2);
            double addedDist = currentState.distance(i-1, i+1) + currentState.distance(i, i+2);
            double improvedDist = removedDist - addedDist;
            return improvedDist;
        } else {
            double removedDist = currentState.distance(i-1, i) + currentState.distance(i, i+1) + currentState.distance(j-1, j) + currentState.distance(j, j+1);
            double addedDist = currentState.distance(i-1, j) + currentState.distance(j, i+1) + currentState.distance(j-1, i) + currentState.distance(i, j+1);
            double improvedDist = removedDist - addedDist;
            return improvedDist;
        }
    }
}
