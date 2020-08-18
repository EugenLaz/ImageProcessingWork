
import Plagiate.ImgThresholdProcessor;

import ij.process.ByteProcessor;
import ij.process.ColorProcessor;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;
import imagingbook.pub.color.edge.CannyEdgeDetector;
import imagingbook.pub.color.edge.ColorEdgeDetector;
import imagingbook.pub.color.edge.DiZenzoCumaniEdgeDetector;


import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

public class App {

    final static String filePath = ("C:\\Users\\Евгений\\IdeaProjects\\ImgProcessingWork\\src\\main\\resources\\unnamed.jpg");
    final static String saveFilePath = ("C:\\Users\\Евгений\\IdeaProjects\\ImgProcessingWork\\src\\main\\resources\\result.jpg");

    final static String redPath = "C:\\Users\\Евгений\\Downloads\\10_2_Red.txt";
    final static String greenPath = "C:\\Users\\Евгений\\Downloads\\10_2_Green.txt";
    final static String bluePath = "C:\\Users\\Евгений\\Downloads\\10_2_Blue.txt";

    public static void main(String[] args) throws IOException {
        ColorProcessor processor = new ColorProcessor(ImageIO.read(new File(filePath)));
        Plagiate.Entity.Image image = new Plagiate.Entity.Image((int[])processor.getPixels(),processor.getWidth(),processor.getHeight());

        ImgThresholdProcessor thresholdProcessor = new ImgThresholdProcessor();
        thresholdProcessor.threshold(image, 3);

        ImageProcessor result = new ColorProcessor(image.getWidth(),image.getHeight(),image.getPixels());

//        processor.autoThreshold();
        CannyEdgeDetector detector = new CannyEdgeDetector(result);
//        ImageProcessor image = detector.getEdgeOrientation();


        ByteProcessor detected = detector.getEdgeBinary();

        ImageIO.write(detected.getBufferedImage(),"jpg",new File(saveFilePath));
    }


}
