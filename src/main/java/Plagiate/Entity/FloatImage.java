package Plagiate.Entity;

import Plagiate.Utils.Convolver;
import ij.IJ;
import ij.process.*;

import java.awt.*;
import java.awt.image.*;
import java.util.Hashtable;
import java.util.Random;

public class FloatImage {
    private float min;
    private float max;
    private boolean minMaxSet;
    int width;
    int height;
    private boolean fixedScale;
    float fillColor;
    float[] pixels;
    private float[] snapshotPixels;
    private float snapshotMin;
    private float snapshotMax;
    private int snapshotWidth;
    private int snapshotHeight;

    public FloatImage(int width, int height) {
        this(width, height, new float[width * height]);
    }

    public FloatImage(int width, int height, float[] pixels) {
        this.snapshotPixels = null;
        this.fillColor = 3.4028235E38F;
        minMaxSet = false;
        this.fixedScale = false;
        if (pixels != null && width * height != pixels.length) {
            throw new IllegalArgumentException("width*height!=pixels.length");
        } else {
            this.width = width;
            this.height = height;
            this.pixels = pixels;
        }
    }

    public FloatImage(int width, int height, int[] pixels) {
        this(width, height);

        for (int i = 0; i < pixels.length; ++i) {
            this.pixels[i] = (float) pixels[i];
        }
    }

    public float[] getPixels() {
        return this.pixels;
    }

    public FloatImage duplicate() {
        FloatImage ip2 = new FloatImage(this.width, this.height);
//        ip2.setMinAndMax(this.getMin(), this.getMax());
//        ip2.setInterpolationMethod(this.interpolationMethod);
        float[] pixels2 = (float[]) ip2.getPixels();
        System.arraycopy(this.pixels, 0, pixels2, 0, this.width * this.height);
        return ip2;
    }

    public final float getf(int x, int y) {
        return this.pixels[y * this.width + x];
    }

    public final void setf(int x, int y, float value) {
        this.pixels[y * this.width + x] = value;
    }


    public void multiply(double value) {
        this.process(3, value);
    }

    private void process(int op, double value) {
        float c = (float) value;
        float min2 = 0.0F;
        float max2 = 0.0F;
        if (op == 0) {
            min2 = (float) this.getMin();
            max2 = (float) this.getMax();
        }

        for (int y = 0; y < this.height; ++y) {
            int i = y * this.width;

            for (int x = 0; x < this.width; ++x) {
                float v1 = this.pixels[i];
                float v2;
                switch (op) {
                    case 0:
                        v2 = max2 - (v1 - min2);
                        break;
                    case 1:
                        v2 = this.fillColor;
                        break;
                    case 2:
                        v2 = v1 + c;
                        break;
                    case 3:
                        v2 = v1 * c;
                        break;
                    case 4:
                    case 5:
                    case 6:
                    default:
                        v2 = v1;
                        break;
                    case 7:
                        if (v1 <= 0.0F) {
                            v2 = 0.0F;
                        } else {
                            v2 = (float) Math.exp((double) c * Math.log((double) v1));
                        }
                        break;
                    case 8:
                        v2 = (float) Math.log((double) v1);
                        break;
                    case 9:
                        if ((double) v1 < value) {
                            v2 = (float) value;
                        } else {
                            v2 = v1;
                        }
                        break;
                    case 10:
                        if ((double) v1 > value) {
                            v2 = (float) value;
                        } else {
                            v2 = v1;
                        }
                        break;
                    case 11:
                        v2 = v1 * v1;
                        break;
                    case 12:
                        if (v1 <= 0.0F) {
                            v2 = 0.0F;
                        } else {
                            v2 = (float) Math.sqrt((double) v1);
                        }
                        break;
                    case 13:
                        v2 = (float) Math.exp((double) v1);
                        break;
                    case 14:
                        v2 = Math.abs(v1);
                        break;
                    case 15:
                        v2 = c;
                }

                this.pixels[i++] = v2;
            }
        }

    }

    public double getMin() {
        if (!this.minMaxSet) {
            this.findMinAndMax();
        }

        return (double) this.min;
    }

    public double getMax() {
        if (!this.minMaxSet) {
            this.findMinAndMax();
        }

        return (double) this.max;
    }

    public void findMinAndMax() {
        if (!this.fixedScale) {
            this.min = 3.4028235E38F;
            this.max = -3.4028235E38F;

            for (int i = 0; i < this.width * this.height; ++i) {
                float value = this.pixels[i];
                if (!Float.isInfinite(value)) {
                    if (value < this.min) {
                        this.min = value;
                    }

                    if (value > this.max) {
                        this.max = value;
                    }
                }
            }

            this.minMaxSet = true;
        }
    }

    public float getPixelValue(int x, int y) {
        return x >= 0 && x < this.width && y >= 0 && y < this.height ? this.pixels[y * this.width + x] : (float) (0.0F / 0.0);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Object getSnapshotPixels() {
        return this.snapshotPixels;
    }

    public Object getPixelsCopy() {
        float[] pixels2 = new float[this.width * this.height];
        System.arraycopy(this.pixels, 0, pixels2, 0, this.width * this.height);
        return pixels2;
    }
    public void reset() {
        if (this.snapshotPixels != null) {
            this.min = this.snapshotMin;
            this.max = this.snapshotMax;
            this.minMaxSet = true;
            System.arraycopy(this.snapshotPixels, 0, this.pixels, 0, this.width * this.height);
        }
    }
    public void snapshot() {
        this.snapshotWidth = this.width;
        this.snapshotHeight = this.height;
        this.snapshotMin = (float)this.getMin();
        this.snapshotMax = (float)this.getMax();
        if (this.snapshotPixels == null || this.snapshotPixels != null && this.snapshotPixels.length != this.pixels.length) {
            this.snapshotPixels = new float[this.width * this.height];
        }

        System.arraycopy(this.pixels, 0, this.snapshotPixels, 0, this.width * this.height);
    }

    public void setPixels(float[] pixels) {
        this.pixels = pixels;
    }

    public int[] getIntArr(){
        int[] result = new int[height*width];
        for(int i =0;i<pixels.length;i++){
            result[i] = (int)pixels[i];
        }
        return result;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public void convolve(float[] kernel, int kernelWidth, int kernelHeight) {
        this.snapshot();
        (new Convolver()).convolveFloat(this, kernel, kernelWidth, kernelHeight);
    }

}
