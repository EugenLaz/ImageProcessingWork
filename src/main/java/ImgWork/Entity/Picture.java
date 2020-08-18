package ImgWork.Entity;

import ImgWork.Converter.ImageArrayConverter;
import ImgWork.Converter.RgbHsvConverter;
import ImgWork.Readers.ArrayReader;

import java.awt.image.BufferedImage;

public  class Picture {
    private int[][] rgbMatrix;
    private int[] rgbArray;

    private int[][] red;
    private int[][] green;
    private int[][] blue;

    private float[][] hue;
    private float[][] saturation;
    private float[][] value;

    Picture(BufferedImage image){
        rgbMatrix = ImageArrayConverter.imageToMatrix(image);
        rgbArray = ImageArrayConverter.imageToArray(image);
    }

    Picture(String redPath, String greenPath, String bluePath){
        try {
            red = ArrayReader.readTo2d("C:\\Users\\Евгений\\Downloads\\10_2_Red.txt");
            green = ArrayReader.readTo2d(("C:\\Users\\Евгений\\Downloads\\10_2_Green.txt"));
            blue = ArrayReader.readTo2d(("C:\\Users\\Евгений\\Downloads\\10_2_Blue.txt"));
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private void processFullHSV(){
        float[] sotre = new float[3];
        for (int i =0 ; i< red.length; i++){
            for(int j =0; j<red.length;j++){
                sotre = RgbHsvConverter.RGBtoHSV(red[i][j],green[i][j],blue[i][j]);
                hue[i][j] = sotre[0];
                saturation[i][j] = sotre[1];
                value[i][j] = sotre[2];
            }
        }
    }
    private void processFullRGB(){
        int[] store = new int[3];
        for (int i =0 ; i< red.length; i++){
            for(int j =0; j<red.length;j++){
                store = RgbHsvConverter.HSVtoRGB(hue[i][j],saturation[i][j],value[i][j]);
                red[i][j] = store[0];
                green[i][j] = store[1];
                blue[i][j] = store[2];
            }
        }
    }

    public int[][] getRgbMatrix() {
        return rgbMatrix;
    }

    public void setRgbMatrix(int[][] rgbMatrix) {
        this.rgbMatrix = rgbMatrix;
    }

    public int[] getRgbArray() {
        return rgbArray;
    }

    public void setRgbArray(int[] rgbArray) {
        this.rgbArray = rgbArray;
    }

    public int[][] getRed() {
        return red;
    }

    public void setRed(int[][] red) {
        this.red = red;
    }

    public int[][] getGreen() {
        return green;
    }

    public void setGreen(int[][] green) {
        this.green = green;
    }

    public int[][] getBlue() {
        return blue;
    }

    public void setBlue(int[][] blue) {
        this.blue = blue;
    }

    public float[][] getHue() {
        return hue;
    }

    public void setHue(float[][] hue) {
        this.hue = hue;
    }

    public float[][] getSaturation() {
        return saturation;
    }

    public void setSaturation(float[][] saturation) {
        this.saturation = saturation;
    }

    public float[][] getValue() {
        return value;
    }

    public void setValue(float[][] value) {
        this.value = value;
    }
}
