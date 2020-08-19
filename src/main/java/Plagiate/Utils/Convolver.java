package Plagiate.Utils;

import Plagiate.Entity.FloatImage;
import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageProcessor;


public class Convolver {
    private ImagePlus imp;

    private boolean canceled;
    private boolean normalize = true;

    public void setNormalize(boolean normalize) {
        this.normalize = normalize;
    }

    private int nSlices;
    private int flags = 16867423;
    private int nPasses = 1;

    private Thread mainThread;
    private int pass;
    private static String defaultKernelText = "-1 -1 -1 -1 -1\n-1 -1 -1 -1 -1\n-1 -1 24 -1 -1\n-1 -1 -1 -1 -1\n-1 -1 -1 -1 -1\n";
    private static boolean defaultNormalizeFlag = true;
    private static String lastKernelText;
    private static boolean lastNormalizeFlag;
    private String kernelText;
    private boolean normalizeFlag;


    public boolean convolveFloat(FloatImage ip, float[] kernel, int kw, int kh) {
            int width = ip.getWidth();
            int height = ip.getHeight();
            int x1 = 0;
            int y1 =0;
            int x2 = x1 + width;
            int y2 = y1 + height;
            int uc = kw / 2;
            int vc = kh / 2;
            float[] pixels = (float[])((float[])ip.getPixels());
            float[] pixels2 = (float[])((float[])ip.getSnapshotPixels());
            if (pixels2 == null) {
                pixels2 = (float[])((float[])ip.getPixelsCopy());
            }

            double scale = this.normalize ? getScale(kernel) : 1.0D;
            Thread thread = Thread.currentThread();
            boolean isMainThread = thread == this.mainThread || thread.getName().indexOf("Preview") != -1;
            if (isMainThread) {
                ++this.pass;
            }

            int xedge = width - uc;
            int yedge = height - vc;
            long lastTime = System.currentTimeMillis();

            for(int y = y1; y < y2; ++y) {
                long time = System.currentTimeMillis();
                if (time - lastTime > 100L) {
                    lastTime = time;
                    if (thread.isInterrupted()) {
                        return false;
                    }

                    if (isMainThread) {
                        if (IJ.escapePressed()) {
                            this.canceled = true;
                            ip.reset();
                            ImageProcessor originalIp = this.imp.getProcessor();
                            if (originalIp.getNChannels() > 1) {
                                originalIp.reset();
                            }

                            return false;
                        }

                    }
                }

                for(int x = x1; x < x2; ++x) {
                    if (this.canceled) {
                        return false;
                    }

                    double sum = 0.0D;
                    int i = 0;
                    boolean edgePixel = y < vc || y >= yedge || x < uc || x >= xedge;

                    for(int v = -vc; v <= vc; ++v) {
                        int offset = x + (y + v) * width;

                        for(int u = -uc; u <= uc; ++u) {
                            if (edgePixel) {
                                if (i >= kernel.length) {
                                    IJ.log("kernel index error: " + i);
                                }

                                sum += (double)(this.getPixel(x + u, y + v, pixels2, width, height) * kernel[i++]);
                            } else {
                                sum += (double)(pixels2[offset + u] * kernel[i++]);
                            }
                        }
                    }

                    pixels[x + y * width] = (float)(sum * scale);
                }
            }

            return true;
    }


    public void convolveFloat1D(FloatImage ip, float[] kernel, int kw, int kh) {
        this.convolveFloat1D(ip, kernel, kw, kh, this.normalize ? getScale(kernel) : 1.0D);
    }

    public void convolveFloat1D(FloatImage ip, float[] kernel, int kw, int kh, double scale) {
        int width = ip.getWidth();
        int height = ip.getHeight();
        int x1 = 0;
        int y1 = 0;
        int x2 = x1 + width;
        int y2 = y1 + height;
        int uc = kw / 2;
        int vc = kh / 2;
        float[] pixels = (float[])((float[])ip.getPixels());
        float[] pixels2 = (float[])((float[])ip.getSnapshotPixels());
        if (pixels2 == null) {
            pixels2 = (float[])((float[])ip.getPixelsCopy());
        }

        boolean vertical = kw == 1;
        int xedge = width - uc;
        int yedge = height - vc;

        for(int y = y1; y < y2; ++y) {
            for(int x = x1; x < x2; ++x) {
                double sum = 0.0D;
                int i = 0;
                int offset;
                boolean edgePixel;
                int u;
                if (vertical) {
                    edgePixel = y < vc || y >= yedge;
                    offset = x + (y - vc) * width;

                    for(u = -vc; u <= vc; ++u) {
                        if (edgePixel) {
                            sum += (double)(this.getPixel(x + uc, y + u, pixels2, width, height) * kernel[i++]);
                        } else {
                            sum += (double)(pixels2[offset + uc] * kernel[i++]);
                        }

                        offset += width;
                    }
                } else {
                    edgePixel = x < uc || x >= xedge;
                    offset = x + (y - vc) * width;

                    for(u = -uc; u <= uc; ++u) {
                        if (edgePixel) {
                            sum += (double)(this.getPixel(x + u, y + vc, pixels2, width, height) * kernel[i++]);
                        } else {
                            sum += (double)(pixels2[offset + u] * kernel[i++]);
                        }
                    }
                }

                pixels[x + y * width] = (float)(sum * scale);
            }
        }

    }
    private float getPixel(int x, int y, float[] pixels, int width, int height) {
        if (x <= 0) {
            x = 0;
        }

        if (x >= width) {
            x = width - 1;
        }

        if (y <= 0) {
            y = 0;
        }

        if (y >= height) {
            y = height - 1;
        }

        return pixels[x + y * width];
    }
    public static double getScale(float[] kernel) {
        double scale = 1.0D;
        double sum = 0.0D;

        for(int i = 0; i < kernel.length; ++i) {
            sum += (double)kernel[i];
        }

        if (sum != 0.0D) {
            scale = 1.0D / sum;
        }

        return scale;
    }

}
