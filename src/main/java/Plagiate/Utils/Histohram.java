package Plagiate.Utils;

import Plagiate.Entity.Image;

public class Histohram {
    private static double rWeight = 0.3333333333333333D;
    private static double gWeight = 0.3333333333333333D;
    private static double bWeight = 0.3333333333333333D;

    public static int[] getHistogram(Image image) {
        int[] histogram = new int[256];

        for(int y = 0; y < image.height; ++y) {
            int i = y * image.width;

            for(int x = 0; x <image.width; ++x) {
                int v = image.pixels[i++] & 255;
                histogram[v]++;
            }
        }

        return histogram;
    }

}
