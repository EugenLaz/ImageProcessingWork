package Plagiate.Entity;

import java.awt.*;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.IndexColorModel;
import java.awt.image.PixelGrabber;

public class BinaryImage {
    static final int ERODE = 10;
    static final int DILATE = 11;
    protected byte[] pixels;
    protected byte[] snapshotPixels;
    private int bgColor;
    private boolean bgColorSet;
    private int min;
    private int max;
    private int binaryCount;
    private int binaryBackground;
    static double oldx;
    static double oldy;
    private static int width;
    private static int height;


    public BinaryImage(int width, int height) {
        this(width, height, new byte[width * height], (ColorModel)null);
    }

    public BinaryImage(int width, int height, byte[] pixels) {
        this(width, height, pixels, (ColorModel)null);
    }

    public BinaryImage(int width, int height, byte[] pixels, ColorModel cm) {
        this.bgColor = 255;
        this.min = 0;
        this.max = 255;
        if (pixels != null && width * height != pixels.length) {
            throw new IllegalArgumentException("width*height!=pixels.length");
        } else {
            this.width = width;
            this.height = height;
            this.pixels = pixels;
        }
    }

    public final int getPixel(int x, int y) {
        return this.pixels[y * this.width + x] & 255;
    }

    public void putPixelValue(int x, int y, double value) {
        if (x >= 0 && x < this.width && y >= 0 && y < this.height) {
            if (value > 255.0D) {
                value = 255.0D;
            } else if (value < 0.0D) {
                value = 0.0D;
            }

            this.pixels[y * this.width + x] = (byte)((int)(value + 0.5D));
        }

    }

    public final void putPixel(int x, int y, int value) {
        if (x >= 0 && x < this.width && y >= 0 && y < this.height) {
            if (value > 255) {
                value = 255;
            }

            if (value < 0) {
                value = 0;
            }

            this.pixels[y * this.width + x] = (byte)value;
        }

    }

    public byte[] getPixels() {
        return pixels;
    }

    public void setPixels(byte[] pixels) {
        this.pixels = pixels;
    }

    public static int getWidth() {
        return width;
    }

    public static void setWidth(int width) {
        BinaryImage.width = width;
    }

    public static int getHeight() {
        return height;
    }

    public static void setHeight(int height) {
        BinaryImage.height = height;
    }
}
