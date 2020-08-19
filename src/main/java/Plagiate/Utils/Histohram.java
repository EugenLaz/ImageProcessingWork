package Plagiate.Utils;

import Plagiate.Entity.ColorImage;

public class Histohram {
    private static double rWeight = 0.3333333333333333D;
    private static double gWeight = 0.3333333333333333D;
    private static double bWeight = 0.3333333333333333D;

    public static int[] getHistogram(ColorImage colorImage) {
        int[] histogram = new int[256];

        for(int y = 0; y < colorImage.height; ++y) {
            int i = y * colorImage.width;

            for(int x = 0; x < colorImage.width; ++x) {
                int v = colorImage.pixels[i++] & 255;
                histogram[v]++;
            }
        }

        return histogram;
    }

}
