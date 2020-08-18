package Plagiate.Edges;

import Plagiate.Entity.Image;

public class CannyEdgeDetector {
//
//    public static class Parameters {
//        public double gSigma = 2.0D;
//        public double hiThr = 20.0D;
//        public double loThr = 5.0D;
//        public boolean normGradMag = true;
//
//        public Parameters() {
//        }
//
//        public boolean isInValid() {
//            return this.gSigma < 0.10000000149011612D || this.loThr > this.hiThr;
//        }
//    }
//
//    private final CannyEdgeDetector.Parameters params;
//    private final int M;
//    private final int N;
//    private Image Emag;
//    private Image Enms;
//    private Image Ex;
//    private Image Ey;
//    private Image Ebin;
//
//    public CannyEdgeDetector(Image I, CannyEdgeDetector.Parameters params) {
//        this.Emag = null;
//        this.Enms = null;
//        this.Ex = null;
//        this.Ey = null;
//        this.Ebin = null;
//        this.traceList = null;
//        this.cosPi8 = (float)Math.cos(0.39269908169872414D);
//        this.sinPi8 = (float)Math.sin(0.39269908169872414D);
//        if (params.isInValid()) {
//            throw new IllegalArgumentException();
//        } else {
//            this.params = params;
//            this.M = I.getWidth();
//            this.N = I.getHeight();
//            this.makeGradientsAndMagnitudeColor(I);
//        }
//    }
//
//
//    private List<List<Point>> traceList;
//    private final float cosPi8;
//    private final float sinPi8;
//
//    public Image getEdgeOrientation() {
//        FloatProcessor E_theta = new FloatProcessor(this.M, this.N);
//
//        for(int u = 0; u < this.M; ++u) {
//            for(int v = 0; v < this.N; ++v) {
//                double ex = (double)this.Ex.getf(u, v);
//                double ey = (double)this.Ey.getf(u, v);
//                float theta = (float)Math.atan2(ey, ex);
//                E_theta.setf(u, v, theta);
//            }
//        }
//
//        return E_theta;
//    }
//    private void makeGradientsAndMagnitudeColor(ColorProcessor I) {
//        FloatProcessor[] Irgb = this.rgbToFloatChannels(I);
//        FloatProcessor[] Ixrgb = new FloatProcessor[3];
//        FloatProcessor[] Iyrgb = new FloatProcessor[3];
//        float[] gaussKernel = this.makeGaussKernel1d(this.params.gSigma);
//        Convolver conv = new Convolver();
//        conv.setNormalize(true);
//
//        for(int i = 0; i < Irgb.length; ++i) {
//            FloatProcessor If = Irgb[i];
//            conv.convolve(If, gaussKernel, gaussKernel.length, 1);
//            conv.convolve(If, gaussKernel, 1, gaussKernel.length);
//            Ixrgb[i] = If;
//            Iyrgb[i] = (FloatProcessor)If.duplicate();
//        }
//
//        float[] gradKernel = new float[]{-0.5F, 0.0F, 0.5F};
//        conv.setNormalize(false);
//
//        for(int i = 0; i < Irgb.length; ++i) {
//            FloatProcessor Ix = Ixrgb[i];
//            FloatProcessor Iy = Iyrgb[i];
//            conv.convolve(Ix, gradKernel, gradKernel.length, 1);
//            conv.convolve(Iy, gradKernel, 1, gradKernel.length);
//        }
//
//        this.Ex = new FloatProcessor(this.M, this.N);
//        this.Ey = new FloatProcessor(this.M, this.N);
//        this.Emag = new FloatProcessor(this.M, this.N);
//        float emax = 0.0F;
//
//        for(int v = 0; v < this.N; ++v) {
//            for(int u = 0; u < this.M; ++u) {
//                float rx = Ixrgb[0].getf(u, v);
//                float ry = Iyrgb[0].getf(u, v);
//                float gx = Ixrgb[1].getf(u, v);
//                float gy = Iyrgb[1].getf(u, v);
//                float bx = Ixrgb[2].getf(u, v);
//                float by = Iyrgb[2].getf(u, v);
//                float A = rx * rx + gx * gx + bx * bx;
//                float B = ry * ry + gy * gy + by * by;
//                float C = rx * ry + gx * gy + bx * by;
//                float D = (float)Math.sqrt((double)((A - B) * (A - B) + 4.0F * C * C));
//                float mag = (float)Math.sqrt(0.5D * (double)(A + B + D));
//                if (mag > emax) {
//                    emax = mag;
//                }
//
//                this.Emag.setf(u, v, mag);
//                this.Ex.setf(u, v, A - B + D);
//                this.Ey.setf(u, v, 2.0F * C);
//            }
//        }
//
//        if (this.params.normGradMag && (double)emax > 0.001D) {
//            this.Emag.multiply(100.0D / (double)emax);
//        }
//
//    }
}
