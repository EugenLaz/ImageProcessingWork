package Plagiate.Entity;

public class Image {
    private static double rWeight = 0.3333333333333333D;
    private static double gWeight = 0.3333333333333333D;
    private static double bWeight = 0.3333333333333333D;
    public  int bgColor = -1;

    public Image(int[] pixels, int width, int height) {
        this.pixels = pixels;
        this.width = width;
        this.height = height;
    }

   public int[] pixels;
   public int width;
   public int height;

    public int[] getPixels() {
        return pixels;
    }

    public void setPixels(int[] pixels) {
        this.pixels = pixels;
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
    public void setBackgroundValue(double value) {
        bgColor = (int)value;
        if (this.bgColor < 0) {
            this.bgColor = 0;
        }

        if (this.bgColor > 255) {
            this.bgColor = 255;
        }

    }

    public void setRGB(int[] R, int[] G, int[] B) {
        for(int i = 0; i < this.width * this.height; ++i) {
            this.pixels[i] = -16777216 | (R[i] & 255) << 16 | (G[i] & 255) << 8 | B[i] & 255;
        }
    }
    public void getRGB(int[] R, int[] G, int[] B) {
        for(int i = 0; i < this.width * this.height; ++i) {
            int c = this.pixels[i];
            int r = (c & 16711680) >> 16;
            int g = (c & '\uff00') >> 8;
            int b = c & 255;
            R[i] = r;
            G[i] = g;
            B[i] = b;
        }

    }

}
