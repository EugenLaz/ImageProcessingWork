
import Plagiate.Edges.CannyEdgeDetector;
import Plagiate.Entity.BinaryImage;
import Plagiate.Entity.ColorImage;
import Plagiate.Entity.FloatImage;
import Plagiate.ImgThresholdProcessor;

import Plagiate.Threshold.AutoThreshold;
import ij.plugin.Thresholder;
import ij.process.ByteProcessor;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;
//import imagingbook.pub.color.edge.CannyEdgeDetector;


import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.nio.file.Paths;

public class App {

    final static String filePath = ("/home/yevhen/Documents/IdeaProjects/ImageProcessingWork/src/main/resources/unnamed.jpg");
    final static String saveFilePath = ("/home/yevhen/Documents/IdeaProjects/ImageProcessingWork/src/main/resources/result.jpg");

    final static String redPath = "C:\\Users\\Евгений\\Downloads\\10_2_Red.txt";
    final static String greenPath = "C:\\Users\\Евгений\\Downloads\\10_2_Green.txt";
    final static String bluePath = "C:\\Users\\Евгений\\Downloads\\10_2_Blue.txt";

    public static void main(String[] args) throws IOException {
        ColorProcessor processor = new ColorProcessor(ImageIO.read(new File(filePath)));
        ColorImage colorImage = new ColorImage((int[])processor.getPixels(),processor.getWidth(),processor.getHeight());
//        ImgThresholdProcessor thresholdProcessor = new ImgThresholdProcessor();
//        thresholdProcessor.threshold(colorImage, 3);
//
//        ImageProcessor result = new ColorProcessor(colorImage.getWidth(), colorImage.getHeight(), colorImage.getPixels());
//
//        processor.autoThreshold();
//        CannyEdgeDetector detector = new CannyEdgeDetector(result);
//        ImageProcessor image = detector.getEdgeBinary();
//
//
//        ByteProcessor detected = detector.getEdgeBinary();

//        ImageProcessor image = new ColorProcessor(processor.getWidth(),processor.getHeight(), (int[])processor.getPixels() );
        ImgThresholdProcessor thresholdProcessor = new ImgThresholdProcessor();
        thresholdProcessor.threshold(colorImage,3);
        CannyEdgeDetector detector = new CannyEdgeDetector(colorImage);
//        System.out.println(colorImage.getPixel(0, 0));
        BinaryImage img = detector.getEdgeBinary();
        ByteProcessor result = new ByteProcessor(img.getWidth(), img.getHeight(), img.getPixels());


        ImageIO.write(result.getBufferedImage(),"jpg", new File(saveFilePath));
    }


}
