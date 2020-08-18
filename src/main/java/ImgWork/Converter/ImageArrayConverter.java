package ImgWork.Converter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.MemoryImageSource;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class ImageArrayConverter {
        final static String saveFilePath = ("C:\\Users\\Евгений\\IdeaProjects\\ImgProcessingWork\\src\\main\\resources\\result.jpg");

    public static int[][] imageToMatrix(BufferedImage image) {
        int height = image.getHeight();
        int width = image.getWidth();

        int[][] matrix = new int[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                matrix[i][j] = image.getRGB(i, j);
            }
        }

        return matrix;
    }

    public static BufferedImage matrixToImage(int[][] pixels) throws IOException {
        int width = pixels[0].length;
        int height = pixels.length;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int row = 0; row < height; row++) {
            image.setRGB(0, row, width, 1, pixels[row], 0, width);
        }

        ImageIO.write(image, "jpg", new File(saveFilePath));

        return image;
    }

    public static int[] imageToArray(BufferedImage image) {
        return ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
    }

    public static BufferedImage getImageFromArray(int[] pixels, int width, int height) throws IOException {
        BufferedImage image = new BufferedImage(width, height,1);
        WritableRaster raster = (WritableRaster) image.getData();
        raster.setPixels(0,0,width,height,pixels);

        ImageIO.write(image, "jpg", new File(saveFilePath));


        return image;
    }

    public static int[][] mergeThree(float[][] red, float[][] green, float[][] blue){
        int[][] mergedArray = new int[red.length][red[0].length];

        for(int i = 0; i <red.length;i++){
            for (int j = 0;j<red[0].length;j++){
                mergedArray[i][j] = ((int)red[i][j] << 16) | ((int)green[i][j] << 8) |  (int)blue[i][j];
            }
        }

        return mergedArray;
    }


}
