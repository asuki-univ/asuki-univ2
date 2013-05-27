package algorithm.secondseason.lightsout;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import algorithm.secondseason.lightsout.GaussianElim;

public class GaussianElimTest {

    @Test
    public void test() {
        GaussianElim ge = new GaussianElim(new double[][] {
                {1, 1, 1},
                {1, 0, 0},
                {0, 1, 1}
        });
        assert(ge != null);
        assertTrue(true);
    }

}
