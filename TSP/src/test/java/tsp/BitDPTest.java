package tsp;

import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class BitDPTest {

    @Test
    public void testBitDP() {
        List<Point> ps = Arrays.asList(new Point[] {
                new Point(38.24, 20.42),
                new Point(39.57, 26.15),
                new Point(40.56, 25.32),
                new Point(36.26, 23.12),
                new Point(33.48, 10.54),
                new Point(37.56, 12.19),
                new Point(38.42, 13.11),
                new Point(37.52, 20.44),
                new Point(41.23, 9.10),
                new Point(41.17, 13.05),
        });

        double bitDpSolution = new BitDPSolver(ps).solve();
        double bruteForceSolution = new BruteForceSolver(ps).solve();

        assertThat(bitDpSolution, closeTo(bruteForceSolution, 1E-9));
    }
}



