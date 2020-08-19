package Plagiate;

import Plagiate.Entity.ColorImage;
import Plagiate.Threshold.*;

public class ImgThresholdProcessor {
    public final int AUTO_THRESHOLD_TYPE = 0;
    public final int OTSU_THRESHOLD_TYPE = 1;
    int[] R;
    int[] G;
    int[] B;
    private ColorImage red;
    private ColorImage green;
    private ColorImage blue;

    public void threshold(ColorImage colorImage, int type) {
        AbstactThreshold thresholder = getThresholder(type);

        initRGB(colorImage);

        thresholder.threshold(red);
        thresholder.threshold(green);
        thresholder.threshold(blue);

        saveImage(colorImage);
    }

    private AbstactThreshold getThresholder(int type) {
        AbstactThreshold result;
        switch (type) {
            case 0:
                result = new OtsuThresholder();
                break;
            case 1:
                result = new QuantileThreshold();
                break;
            case 2:
                result = new MeanThreshold();
                break;
            case 3:
                result = new MinErrorThreshold();
                break;
            case 4:
                result = new IsoDataThreshold();
                break;
            default:
                result = new AutoThreshold();
        }
        return result;
    }

    private void initRGB(ColorImage colorImage) {
        R = new int[colorImage.width * colorImage.height];
        G = new int[colorImage.width * colorImage.height];
        B = new int[colorImage.width * colorImage.height];

        colorImage.getRGB(R, G, B);

        red = new ColorImage(colorImage.getPixels(), colorImage.getWidth(), colorImage.getHeight());
        green = new ColorImage(colorImage.getPixels(), colorImage.getWidth(), colorImage.getHeight());
        blue = new ColorImage(colorImage.getPixels(), colorImage.getWidth(), colorImage.getHeight());

        red.setPixels(R);
        green.setPixels(G);
        blue.setPixels(B);

    }


    private void saveImage(ColorImage colorImage) {
        R = red.getPixels();
        G = green.getPixels();
        B = blue.getPixels();

        colorImage.setRGB(R, G, B);
    }
}
