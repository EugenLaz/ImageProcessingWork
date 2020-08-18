package Plagiate.Threshold;

public class AutoThreshold extends AbstactThreshold{

    @Override
    protected int getThresholdLevel(int[] histogram) {
        int maxValue = histogram.length - 1;
        int count0 = histogram[0];
        histogram[0] = 0;
        int countMax = histogram[maxValue];
        histogram[maxValue] = 0;

        int min;
        for (min = 0; histogram[min] == 0 && min < maxValue; ++min) {
        }

        int max;
        for (max = maxValue; histogram[max] == 0 && max > 0; --max) {
        }

        int level;
        if (min >= max) {
            histogram[0] = count0;
            histogram[maxValue] = countMax;
            level = histogram.length / 2;
            return level;
        } else {
            int movingIndex = min;
            int var19 = Math.max(max / 40, 1);

            double result;
            do {
                double sum4 = 0.0D;
                double sum3 = 0.0D;
                double sum2 = 0.0D;
                double sum1 = 0.0D;

                int i;
                for (i = min; i <= movingIndex; ++i) {
                    sum1 += (double) i * (double) histogram[i];
                    sum2 += (double) histogram[i];
                }

                for (i = movingIndex + 1; i <= max; ++i) {
                    sum3 += (double) i * (double) histogram[i];
                    sum4 += (double) histogram[i];
                }

                result = (sum1 / sum2 + sum3 / sum4) / 2.0D;
                ++movingIndex;
            } while ((double) (movingIndex + 1) <= result && movingIndex < max - 1);

            histogram[0] = count0;
            histogram[maxValue] = countMax;
            level = (int) Math.round(result);
            return level;
        }
    }

}
