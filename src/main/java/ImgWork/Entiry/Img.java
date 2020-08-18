package ImgWork.Entiry;

import ImgWork.Converter.ImageArrayConverter;

import java.awt.image.BufferedImage;

public class Img {
    public BufferedImage image;
    public int[] array;
    public int[][] matrix;
    public int width;
    public int heigth;

    public Img(BufferedImage image) {
        this.image = image;
        this.array = ImageArrayConverter.imageToArray(image);
        this.matrix = ImageArrayConverter.imageToMatrix(image);
        this.width = image.getWidth();
        this.heigth = image.getHeight();
    }



}
