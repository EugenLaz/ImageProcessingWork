package Plagiate.Threshold;

public class MeanThreshold extends AbstactThreshold {
    @Override
    protected int getThresholdLevel(int[] h) {
        // calculate mean of entire image:
        int K = h.length;
        int cnt = 0;
        long sum = 0;
        for (int i=0; i<K; i++) {
            cnt += h[i];
            sum += i * h[i];
        }

        int mean = (int) Math.rint((double)sum / cnt);

        // count resulting background pixels:
        int n0 = 0;
        for (int i = 0; i <= mean; i++) {
            n0 += h[i];
        }

        // determine if background or foreground is empty:
        int q = (n0 < cnt) ? mean : -1;
        return q;
    }
}
