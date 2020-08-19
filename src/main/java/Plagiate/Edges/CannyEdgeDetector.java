package Plagiate.Edges;

import Plagiate.Entity.BinaryImage;
import Plagiate.Entity.ColorImage;
import Plagiate.Entity.FloatImage;
import Plagiate.Utils.Convolver;



import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class CannyEdgeDetector {

    public static class Parameters {
        public double gSigma = 2.0D;
        public double hiThr = 20.0D;
        public double loThr = 5.0D;
        public boolean normGradMag = true;

        public Parameters() {
        }

        public boolean isInValid() {
            return this.gSigma < 0.10000000149011612D || this.loThr > this.hiThr;
        }
    }

    private final Parameters params;
    private final int M;
    private final int N;
    private FloatImage Emag;
    private FloatImage Enms;
    private FloatImage Ex;
    private FloatImage Ey;
    private BinaryImage Ebin;
    private List<List<Point>> traceList;
    private final float cosPi8;
    private final float sinPi8;


    public CannyEdgeDetector(ColorImage ip) {
        this(ip, new Parameters());
    }
    public CannyEdgeDetector(ColorImage I, Parameters params) {
        this.Emag = null;
        this.Enms = null;
        this.Ex = null;
        this.Ey = null;
        this.Ebin = null;
        this.traceList = null;
        this.cosPi8 = (float)Math.cos(0.39269908169872414D);
        this.sinPi8 = (float)Math.sin(0.39269908169872414D);
        if (params.isInValid()) {
            throw new IllegalArgumentException();
        } else {
            this.params = params;
            this.M = I.getWidth();
            this.N = I.getHeight();
            this.makeGradientsAndMagnitudeColor(I);
        }
    }

    private void makeGradientsAndMagnitudeColor(ColorImage I) {
        FloatImage[] Irgb = this.rgbToFloatChannels(I);
        FloatImage[] Ixrgb = new FloatImage[3];
        FloatImage[] Iyrgb = new FloatImage[3];
        float[] gaussKernel = this.makeGaussKernel1d(this.params.gSigma);
        Convolver conv = new Convolver();

        for(int i = 0; i < Irgb.length; ++i) {
            FloatImage If = Irgb[i];
            conv.convolveFloat(If, gaussKernel, gaussKernel.length, 1);
            conv.convolveFloat(If, gaussKernel, 1, gaussKernel.length);
            Ixrgb[i] = If;
            Iyrgb[i] = (FloatImage)If.duplicate();
        }

        float[] gradKernel = new float[]{-0.5F, 0.0F, 0.5F};
        conv.setNormalize(false);

        for(int i = 0; i < Irgb.length; ++i) {
            FloatImage Ix = Ixrgb[i];
            FloatImage Iy = Iyrgb[i];
            conv.convolveFloat(Ix, gradKernel, gradKernel.length, 1);
            conv.convolveFloat(Iy, gradKernel, 1, gradKernel.length);
        }

        this.Ex = new FloatImage(this.M, this.N);
        this.Ey = new FloatImage(this.M, this.N);
        this.Emag = new FloatImage(this.M, this.N);
        float emax = 0.0F;

        for(int v = 0; v < this.N; ++v) {
            for(int u = 0; u < this.M; ++u) {
                float rx = Ixrgb[0].getf(u, v);
                float ry = Iyrgb[0].getf(u, v);
                float gx = Ixrgb[1].getf(u, v);
                float gy = Iyrgb[1].getf(u, v);
                float bx = Ixrgb[2].getf(u, v);
                float by = Iyrgb[2].getf(u, v);
                float A = rx * rx + gx * gx + bx * bx;
                float B = ry * ry + gy * gy + by * by;
                float C = rx * ry + gx * gy + bx * by;
                float D = (float)Math.sqrt((double)((A - B) * (A - B) + 4.0F * C * C));
                float mag = (float)Math.sqrt(0.5D * (double)(A + B + D));
                if (mag > emax) {
                    emax = mag;
                }

                this.Emag.setf(u, v, mag);
                this.Ex.setf(u, v, A - B + D);
                this.Ey.setf(u, v, 2.0F * C);
            }
        }

        if (this.params.normGradMag && (double)emax > 0.001D) {
            this.Emag.multiply(100.0D / (double)emax);
        }

    }

    private FloatImage[] rgbToFloatChannels(ColorImage cp) {
        int w = cp.getWidth();
        int h = cp.getHeight();
        FloatImage rp = new FloatImage(w, h);
        FloatImage gp = new FloatImage(w, h);
        FloatImage bp = new FloatImage(w, h);
        int[] pixels = cp.getPixels();
        float[] rpix = (float[])rp.getPixels();
        float[] gpix = (float[])gp.getPixels();
        float[] bpix = (float[])bp.getPixels();

        for(int i = 0; i < pixels.length; ++i) {
            int c = pixels[i];
            rpix[i] = (float)((c & 16711680) >> 16);
            gpix[i] = (float)((c & '\uff00') >> 8);
            bpix[i] = (float)(c & 255);
        }

        return new FloatImage[]{rp, gp, bp};
    }


    public BinaryImage getEdgeBinary() {
        if (this.Ebin == null) {
            this.detectAndTraceEdges();
        }

        return this.Ebin;
    }

    private void detectAndTraceEdges() {
        if (this.Enms == null) {
            this.nonMaxSuppression();
        }

        this.Ebin = new BinaryImage(this.M, this.N);
        int color = 255;
        this.traceList = new LinkedList();

        for(int v = 0; v < this.N; ++v) {
            for(int u = 0; u < this.M; ++u) {
                if ((double)this.Enms.getf(u, v) >= this.params.hiThr && this.Ebin.getPixel(u, v) == 0) {
                    List<Point> trace = this.traceAndThreshold(u, v, (float)this.params.loThr, color);
                    this.traceList.add(trace);
                }
            }
        }

    }

    private void nonMaxSuppression() {
        this.Enms = new FloatImage(this.M, this.N);

        for(int v = 1; v < this.N - 1; ++v) {
            for(int u = 1; u < this.M - 1; ++u) {
                int s_theta = this.getOrientationSector(this.Ex.getf(u, v), this.Ey.getf(u, v));
                if (this.isLocalMaximum(this.Emag, u, v, s_theta, (float)this.params.loThr)) {
                    this.Enms.setf(u, v, this.Emag.getf(u, v));
                }
            }
        }

    }


    private int getOrientationSector(float dx, float dy) {
            float dxR = this.cosPi8 * dx - this.sinPi8 * dy;
            float dyR = this.sinPi8 * dx + this.cosPi8 * dy;
            if (dyR < 0.0F) {
                dxR = -dxR;
                dyR = -dyR;
            }

            byte s_theta;
            if (dxR >= 0.0F) {
                if (dxR >= dyR) {
                    s_theta = 0;
                } else {
                    s_theta = 1;
                }
            } else if (-dxR < dyR) {
                s_theta = 2;
            } else {
                s_theta = 3;
            }

            return s_theta;
        }


    private boolean isLocalMaximum(FloatImage gradMagnitude, int u, int v, int s_theta, float mMin) {
            float mC = gradMagnitude.getf(u, v);
            if (mC < mMin) {
                return false;
            } else {
                float mL = 0.0F;
                float mR = 0.0F;
                switch(s_theta) {
                case 0:
                    mL = gradMagnitude.getf(u - 1, v);
                    mR = gradMagnitude.getf(u + 1, v);
                    break;
                case 1:
                    mL = gradMagnitude.getf(u - 1, v - 1);
                    mR = gradMagnitude.getPixelValue(u + 1, v + 1);
                    break;
                case 2:
                    mL = gradMagnitude.getf(u, v - 1);
                    mR = gradMagnitude.getf(u, v + 1);
                    break;
                case 3:
                    mL = gradMagnitude.getf(u - 1, v + 1);
                    mR = gradMagnitude.getf(u + 1, v - 1);
                }

                return mL <= mC && mC >= mR;
            }
        }

    private float[] makeGaussKernel1d(double sigma) {
        int rad = (int)(3.5D * sigma);
        int size = rad + rad + 1;
        float[] kernel = new float[size];
        double sigma2 = sigma * sigma;
        double scale = 1.0D / Math.sqrt(6.283185307179586D * sigma2);

        for(int i = 0; i < size; ++i) {
            double x = (double)(rad - i);
            kernel[i] = (float)(scale * Math.exp(-0.5D * x * x / sigma2));
        }

        return kernel;
    }

    private List<Point> traceAndThreshold(int u0, int v0, float loThr, int markColor) {
        Stack<Point> pointStack = new Stack();
        List<Point> pointList = new LinkedList();
        pointStack.push(new Point(u0, v0));

        while(!pointStack.isEmpty()) {
            Point p = (Point)pointStack.pop();
            int up = p.x;
            int vp = p.y;
            this.Ebin.putPixel(up, vp, markColor);
            pointList.add(p);
            int uL = Math.max(up - 1, 0);
            int uR = Math.min(up + 1, this.M - 1);
            int vT = Math.max(vp - 1, 0);
            int vB = Math.min(vp + 1, this.N - 1);

            for(int u = uL; u <= uR; ++u) {
                for(int v = vT; v <= vB; ++v) {
                    if (this.Ebin.getPixel(u, v) == 0 && this.Enms.getf(u, v) >= loThr) {
                        pointStack.push(new Point(u, v));
                    }
                }
            }
        }

        return pointList;
    }




}
