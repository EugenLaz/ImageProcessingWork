package Plagiate;

import Plagiate.Entity.Image;
import Plagiate.Threshold.*;

public class ImgThresholdProcessor {
    public final int AUTO_THRESHOLD_TYPE = 0;
    public final int OTSU_THRESHOLD_TYPE = 1;
    int[] R;
    int[] G;
    int[] B;
    private Image red;
    private Image green;
    private Image blue;

    public void threshold(Image image, int type) {
        AbstactThreshold thresholder = getThresholder(type);

        initRGB(image);

        thresholder.threshold(red);
        thresholder.threshold(green);
        thresholder.threshold(blue);

        saveImage(image);
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

    private void initRGB(Image image) {
        R = new int[image.width * image.height];
        G = new int[image.width * image.height];
        B = new int[image.width * image.height];

        image.getRGB(R, G, B);

        red = new Image(image.getPixels(), image.getWidth(), image.getHeight());
        green = new Image(image.getPixels(), image.getWidth(), image.getHeight());
        blue = new Image(image.getPixels(), image.getWidth(), image.getHeight());

        red.setPixels(R);
        green.setPixels(G);
        blue.setPixels(B);

    }


    private void saveImage(Image image) {
        R = red.getPixels();
        G = green.getPixels();
        B = blue.getPixels();

        image.setRGB(R, G, B);
    }
}
