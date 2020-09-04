
import Plagiate.Edges.CannyEdgeDetector;
import Plagiate.Edges.MonochromaticEdgeDetector;
import Plagiate.Entity.BinaryImage;
import Plagiate.Entity.ColorImage;
import Plagiate.Entity.FloatImage;
import Plagiate.ImgThresholdProcessor;

import Plagiate.Threshold.AutoThreshold;
import ij.plugin.Thresholder;
import ij.process.ByteProcessor;
import ij.process.ColorProcessor;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;


import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.nio.file.Paths;

public class App {

    final static String filePath = ("/home/yevhen/Documents/IdeaProjects/ImageProcessingWork/src/main/resources/unnamed2.jpg");
    final static String saveFilePath = ("/home/yevhen/Documents/IdeaProjects/ImageProcessingWork/src/main/resources/result.jpg");

    final static String redPath = "C:\\Users\\Евгений\\Downloads\\10_2_Red.txt";
    final static String greenPath = "C:\\Users\\Евгений\\Downloads\\10_2_Green.txt";
    final static String bluePath = "C:\\Users\\Евгений\\Downloads\\10_2_Blue.txt";

    public static void main(String[] args) throws IOException {
        ColorProcessor processor = new ColorProcessor(ImageIO.read(new File(filePath)));
        ColorImage colorImage = new ColorImage((int[])processor.getPixels(),processor.getWidth(),processor.getHeight());
        ImgThresholdProcessor thresholdProcessor = new ImgThresholdProcessor();
        thresholdProcessor.threshold(colorImage,3);
        CannyEdgeDetector edgeDetector = new CannyEdgeDetector(colorImage);
        FloatImage img = edgeDetector.getEdgeOrientation();

        FloatProcessor result = new FloatProcessor(colorImage.getWidth(),colorImage.getHeight(),img.getPixels());

        ImageIO.write(result.getBufferedImage(),"jpg", new File(saveFilePath));

    }


}
