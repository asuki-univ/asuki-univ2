package tsp.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tsp.AbstractTSPSolver;
import tsp.Point;
import tsp.TSPState;

public class KOptHillClimbing extends AbstractTSPSolver {
    private final int K;
    private final int N;

    public KOptHillClimbing(List<Point> points, int K) {
        super(points);
        this.N = points.size();
        this.K = K;
    }

    @Override
    public void run() {
        if (this.currentState.getPoints().size() <= 3)
            return;

        while (step()) {
            // do nothing.
        }
    }

    public boolean step() {
        TSPState nextState = null;

        System.out.println(K);

        // まず K 個の選び方を全て列挙して、その選び方に対して、
        int[] chosen = new int[N];
        for (int i = 0; i < K; ++i)
            chosen[N - i - 1] = 1;

        do {
            int[] idx = new int[K];
            Point[] ps = new Point[K];
            int p = 0;
            for (int i = 0; i < N; ++i) {
                if (chosen[i] == 1) {
                    idx[p] = i;
                    ps[p] = currentState.getPoints().get(i);
                    ++p;
                }
            }

            int[] trans = new int[K];
            for (int i = 0; i < K; ++i)
                trans[i] = i;

            do {
                TSPState state = new TSPState(currentState);
                // System.out.println("***");
                for (int i = 0; i < K; ++i) {
                    // System.out.printf("%d -> %d\n", idx[i], trans[i]);
                    state.getMutablePoints().set(idx[i], ps[trans[i]]);
                }

                TSPState comparingState = nextState == null ? currentState : nextState;
                // System.out.println("state score = " + state.getScore());
                if (state.getScore() < comparingState.getScore())
                    nextState = state;
            } while (nextPermutation(trans));

        } while (nextPermutation(chosen));

        if (nextState != null)
            currentState = nextState;

        return nextState != null;
    }

    public static boolean nextPermutation(int[] vs) {
        int a = vs.length - 2;
        while (0 <= a && vs[a + 1] <= vs[a])
            --a;
        if (a < 0)
            return false;

        int b = vs.length - 1;
        while (vs[b] <= vs[a])
            --b;

        swap(vs, a, b);

        for (int i = a + 1, j = vs.length - 1; i < j; i++, j--)
            swap(vs, i, j);

        return true;
    }

    public static void swap(int[] vs, int x, int y) {
        int t = vs[x];
        vs[x] = vs[y];
        vs[y] = t;
    }
}
