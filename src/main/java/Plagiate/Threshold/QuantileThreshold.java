package Plagiate.Threshold;

public class QuantileThreshold extends AbstactThreshold{

    private double b = 0.5;	// quantile of expected background pixels


    @Override
    protected int getThresholdLevel(int[] h) {
        int K = h.length;
        int N = sum(h);

        double n = N * b;
        int i = 0;
        int c = h[0];
        while (i < K && c < n) {
            i++;
            c+= h[i];
        }

        int q = (c < N) ? i : -1;
        return q;
    }
}
