package tsp;

import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class BruteForceSolverTest {
    @Test
    public void testBitDP() {
        List<Point> ps = Arrays.asList(new Point[] {
                new Point(0, 0),
                new Point(0, 1),
                new Point(0, 2),
                new Point(1, 0),
                new Point(1, 1),
                new Point(1, 2),
                new Point(2, 0),
                new Point(2, 1),
                new Point(2, 2),
        });

        double bruteForceSolution = new BruteForceSolver(ps).solve();
        assertThat(bruteForceSolution, closeTo(8.0 + Math.sqrt(2), 1E-9));
    }
}
