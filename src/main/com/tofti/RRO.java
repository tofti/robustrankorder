package com.tofti;

import java.util.Arrays;

public class RRO {
    static class RROResult {
        public final int m;
        public final int n;
        public final double uyxMean;
        public final double uxyMean;
        public final double vx;
        public final double vy;
        public final double u;
        public RROResult(int m, int n, double uyxMean, double uxyMean, double vx, double vy, double u) {
            this.m = m;
            this.n = n;
            this.uyxMean = uyxMean;
            this.uxyMean = uxyMean;
            this.vx = vx;
            this.vy = vy;
            this.u = u;
        }
    }
    public static RROResult rro(double[] X, double[] Y) {
        final int m = X.length;
        final int n = Y.length;

        // sort X and Y
        Arrays.sort(X);
        Arrays.sort(Y);

        // position values of x_i in Y
        double[] uyxi = new double[m];
        for(int i = 0; i < m; i++)
            uyxi[i] = getRank(X[i], Y);

        // position values of y_j in X
        double[] uxyj = new double[n];
        for(int j = 0; j < n; j++)
            uxyj[j] = getRank(Y[j], X);

         // determine mean ranks
        double uyx_mean = mean(uyxi);
        double uxy_mean = mean(uxyj);

        // variance in uyx ranks
        double vx = 0.0;
        for(int i = 0; i < n; i++)
            vx += (uxyj[i] - uxy_mean) * (uxyj[i] - uxy_mean);

        // variance in uxy ranks
        double vy = 0.0;
        for(int j = 0; j < m; j++)
            vy += (uyxi[j] - uxy_mean) * (uyxi[j] - uyx_mean);

        // compute U
        double u = (m * uyx_mean - n * uxy_mean) / (2.0 * Math.sqrt(vx + vy + uxy_mean * uyx_mean));
        return new RROResult(m, n,uyx_mean, uxy_mean, vx, vy, u);
    }

    // determine the rank of x in Y, given Y is sorted
    private static double getRank(double x, double[] Y) {
        int whole = 0;
        int half = 0;

        int i = 0;
        while(i < Y.length && Y[i] <= x) {
            if(Y[i] == x) {
                half++;
            }
            else {
                whole++;
            }
            i++;
        }

        // add the halves that make wholes
        whole += half/2;

        // add the single remaining half if necessary
        double rank = (double)whole;
        if(half > 0 && half % 2 == 1) {
            rank += 0.5;
        }
        return rank;
    }

    private static double mean(double[] d) {
        double sum = 0.0;
        for (int i = 0; i < d.length; i++) {
            sum += d[i];
        }
        return sum / d.length;
    }
}