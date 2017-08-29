package com.tofti;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RROTest {

    // Experimental Economics
    // November 2003, Volume 6, Issue 3, pp 273–297
    // Nonparametric Tests of Differences in Medians: Comparison of the Wilcoxon–Mann–Whitney and Robust Rank-Order Tests
    // See section 2 - Description of the tests
    // https://link.springer.com/article/10.1023%2FA%3A1026273319211
    @Test
    public void testDuffyFeltovitchWithBreakdown() {
        final double[] x = new double[] {5.025,6.7,6.725,6.75,7.05,7.25,8.375};
        final double[] y = new double[] {4.875,5.125,5.225,5.425,5.55,5.75,5.925,6.125};

        RRO.RROResult rro = RRO.rro(x, y);
        Assertions.assertEquals(rro.u, +3d, 1E-6);

        // placements of y in x
        Assertions.assertEquals(rro.uyxMean, 7, 1E-6);
        // placements of x in y
        Assertions.assertEquals(rro.uxyMean, 0.875, 1E-6);

        Assertions.assertEquals(rro.vx, 0.875, 1E-6);
        Assertions.assertEquals(rro.vy, 42, 1E-6);
    }
}