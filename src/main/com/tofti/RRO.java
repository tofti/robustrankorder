package main;

public class RRO {
    private static double rro(double[] X, double[] Y) {
        final int m = X.length;
        final int n = Y.length;

        System.out.println("m=" + m);
        System.out.println("n=" + n);


        // sort X and Y
        Arrays.sort(X);
        Arrays.sort(Y);

        printDoubleArr("X",X);
        printDoubleArr("Y",Y);

        // position values of x_i in Y
        double[] uyxi = new double[m];
        for(int i = 0; i < m; i++)
            uyxi[i] = getRank(X[i], Y);

        // position values of y_j in X
        double[] uxyj = new double[n];
        for(int j = 0; j < n; j++)
            uxyj[j] = getRank(Y[j], X);

        printDoubleArr("uyxi",uyxi);
        printDoubleArr("uxyj",uxyj);

        // determine mean ranks
        double uyx_mean = mean(uyxi);
        double uxy_mean = mean(uxyj);

        System.out.println("uyx_mean=" + uyx_mean);
        System.out.println("uxy_mean=" + uxy_mean);

        // variance in uyx ranks
        double Vx = 0.0;
        for(int i = 0; i < m; i++)
            Vx += (uyxi[i] - uyx_mean) * (uyxi[i] - uyx_mean);

        // variance in uxy ranks
        double Vy = 0.0;
        for(int j = 0; j < n; j++)
            Vy += (uxyj[j] - uxy_mean) * (uxyj[j] - uxy_mean);

        System.out.println("Vx=" + Vx);
        System.out.println("Vy=" + Vy);

        // compute U
        double U = (m * uyx_mean - n * uxy_mean) / (2.0 * Math.sqrt(Vx + Vy + uxy_mean * uyx_mean));
        return U;
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
        for (int i = 0; i < d.length; i++)
            sum += d[i];
        return sum / d.length;
    }

}
