package Plagiate.Edges;

import Plagiate.Entity.BinaryImage;
import Plagiate.Entity.ColorImage;
import Plagiate.Entity.FloatImage;
import Plagiate.Utils.Matrix;
import Plagiate.Utils.VectorNorm;


public class MonochromaticEdgeDetector {

    final ColorImage I;
    final int M;	// image width
    final int N;	// image height
    final Parameters params;

    private FloatImage Emag;	// edge magnitude map
    private FloatImage Eort;	// edge orientation map

    public static class Parameters {
        /** Specify which color distance to use */
        public VectorNorm.NormType norm = VectorNorm.NormType.L2;
    }


    // Sobel-kernels for x/y-derivatives:
    final float[] HxS = Matrix.multiply(1.0f/8, new float[] {
            -1, 0, 1,
            -2, 0, 2,
            -1, 0, 1
    });

    final float[] HyS = Matrix.multiply(1.0f/8, new float[] {
            -1, -2, -1,
            0,  0,  0,
            1,  2,  1
    });

    final int R = 0, G = 1, B = 2;		// RGB channel indexes

    FloatImage[] Ix;
    FloatImage[] Iy;

    public MonochromaticEdgeDetector(ColorImage I) {
        this(I, new Parameters());
    }

    public MonochromaticEdgeDetector(ColorImage I, Parameters params) {
        this.params = params;
        this.I = I;
        this.M = this.I.getWidth();
        this.N = this.I.getHeight();
        setup();
        findEdges();
    }

    protected void setup() {
        Emag = new FloatImage(M, N);
        Eort = new FloatImage(M, N);
        Ix = new FloatImage[3];
        Iy = new FloatImage[3];
    }

    void findEdges() {
        for (int c = R; c <= B; c++) {
            Ix[c] =  getRgbFloatChannel(I, c);
            Iy[c] =  getRgbFloatChannel(I, c);
            Ix[c].convolve(HxS, 3, 3);
            Iy[c].convolve(HyS, 3, 3);
        }

        //VectorNorm vNorm = VectorNorm.create(params.norm);
        VectorNorm vNorm = params.norm.create();
        for (int v = 0; v < N; v++) {
            for (int u = 0; u < M; u++) {
                // extract the gradients of the R, G, B channels:
                final float Rx = Ix[R].getf(u, v);	float Ry = Iy[R].getf(u, v);
                final float Gx = Ix[G].getf(u, v);	float Gy = Iy[G].getf(u, v);
                final float Bx = Ix[B].getf(u, v);	float By = Iy[B].getf(u, v);

                final float Er2 = Rx * Rx + Ry * Ry;
                final float Eg2 = Gx * Gx + Gy * Gy;
                final float Eb2 = Bx * Bx + By * By;

                // calculate local edge magnitude:
                double[] Ergb = {Math.sqrt(Er2), Math.sqrt(Eg2), Math.sqrt(Eb2)};
                float eMag = (float) vNorm.magnitude(Ergb);
//				float eMag = (float) Math.sqrt(Er2 + Eg2 + Eb2);	// special case of L2 norm
                Emag.setf(u, v, eMag);

                // find the maximum gradient channel:
                float e2max = Er2, cx = Rx, cy = Ry;
                if (Eg2 > e2max) {
                    e2max = Eg2; cx = Gx; cy = Gy;
                }
                if (Eb2 > e2max) {
                    e2max = Eb2; cx = Bx; cy = By;
                }

                // calculate edge orientation for the maximum channel:
                float eOrt = (float) Math.atan2(cy, cx);
                Eort.setf(u, v, eOrt);
            }
        }
    }


    public FloatImage getEdgeMagnitude() {
        return Emag;
    }

    public FloatImage getEdgeOrientation() {
        return Eort;
    }

    FloatImage getRgbFloatChannel(ColorImage cp, int c) {
        int w = cp.getWidth();
        int h = cp.getHeight();
        BinaryImage bp = new BinaryImage(w, h, cp.getChannel(c + 1));
        return bp.toFloat();
    }
}
