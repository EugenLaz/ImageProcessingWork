package Plagiate.Utils;

public class Matrix {
    public static double[] multiply(double s, double[] a) {
        double[] b = (double[])a.clone();
        multiplyD(s, b);
        return b;
    }

    public static void multiplyD(double s, double[] a) {
        for(int i = 0; i < a.length; ++i) {
            a[i] *= s;
        }

    }

    public static double[][] multiply(double s, double[][] A) {
        double[][] B = duplicate(A);
        multiplyD(s, B);
        return B;
    }

    public static void multiplyD(double s, double[][] A) {
        for(int i = 0; i < A.length; ++i) {
            for(int j = 0; j < A[i].length; ++j) {
                A[i][j] *= s;
            }
        }

    }

    public static float[] multiply(float s, float[] A) {
        float[] B = duplicate(A);
        multiplyD(s, B);
        return B;
    }

    public static void multiplyD(float s, float[] A) {
        for(int i = 0; i < A.length; ++i) {
            A[i] *= s;
        }

    }

    public static float[][] multiply(float s, float[][] A) {
        float[][] B = duplicate(A);
        multiplyD(s, B);
        return B;
    }

    public static void multiplyD(float s, float[][] A) {
        for(int i = 0; i < A.length; ++i) {
            for(int j = 0; j < A[i].length; ++j) {
                A[i][j] *= s;
            }
        }

    }

    public static double[] multiply(double[] x, double[][] A) {
        double[] y = new double[getNumberOfColumns(A)];
        multiplyD(x, A, y);
        return y;
    }

    public static void multiplyD(double[] x, double[][] A, double[] y) {
        if (x == y) {
            throw new imagingbook.lib.math.Matrix.SameSourceTargetException();
        } else {
            int m = getNumberOfRows(A);
            int n = getNumberOfColumns(A);
            if (x.length == m && y.length == n) {
                for(int i = 0; i < n; ++i) {
                    double s = 0.0D;

                    for(int j = 0; j < m; ++j) {
                        s += x[j] * A[j][i];
                    }

                    y[i] = s;
                }

            } else {
                throw new imagingbook.lib.math.Matrix.IncompatibleDimensionsException();
            }
        }
    }

    public static double[] multiply(double[][] A, double[] x) {
        double[] y = new double[getNumberOfRows(A)];
        multiplyD(A, x, y);
        return y;
    }

    public static void multiplyD(double[][] A, double[] x, double[] y) {
        if (x == y) {
            throw new imagingbook.lib.math.Matrix.SameSourceTargetException();
        } else {
            int m = getNumberOfRows(A);
            int n = getNumberOfColumns(A);
            if (x.length == n && y.length == m) {
                for(int i = 0; i < m; ++i) {
                    double s = 0.0D;

                    for(int j = 0; j < n; ++j) {
                        s += A[i][j] * x[j];
                    }

                    y[i] = s;
                }

            } else {
                throw new imagingbook.lib.math.Matrix.IncompatibleDimensionsException();
            }
        }
    }

    public static float[] multiply(float[][] A, float[] x) {
        float[] y = new float[getNumberOfRows(A)];
        multiplyD(A, x, y);
        return y;
    }

    public static void multiplyD(float[][] A, float[] x, float[] y) {
        if (x == y) {
            throw new imagingbook.lib.math.Matrix.SameSourceTargetException();
        } else {
            int m = getNumberOfRows(A);
            int n = getNumberOfColumns(A);
            if (x.length == n && y.length == m) {
                for(int i = 0; i < m; ++i) {
                    double s = 0.0D;

                    for(int j = 0; j < n; ++j) {
                        s += (double)(A[i][j] * x[j]);
                    }

                    y[i] = (float)s;
                }

            } else {
                throw new imagingbook.lib.math.Matrix.IncompatibleDimensionsException();
            }
        }
    }

    public static double[][] multiply(double[][] A, double[][] B) {
        int m = getNumberOfRows(A);
        int q = getNumberOfColumns(B);
        double[][] C = createDoubleMatrix(m, q);
        multiplyD(A, B, C);
        return C;
    }

    public static void multiplyD(double[][] A, double[][] B, double[][] C) {
        if (A != C && B != C) {
            int mA = getNumberOfRows(A);
            int nA = getNumberOfColumns(A);
            int mB = getNumberOfRows(B);
            int nB = getNumberOfColumns(B);
            if (nA != mB) {
                throw new imagingbook.lib.math.Matrix.IncompatibleDimensionsException();
            } else {
                for(int i = 0; i < mA; ++i) {
                    for(int j = 0; j < nB; ++j) {
                        double s = 0.0D;

                        for(int k = 0; k < nA; ++k) {
                            s += A[i][k] * B[k][j];
                        }

                        C[i][j] = s;
                    }
                }

            }
        } else {
            throw new imagingbook.lib.math.Matrix.SameSourceTargetException();
        }
    }

    public static float[][] multiply(float[][] A, float[][] B) {
        int mA = getNumberOfRows(A);
        int nB = getNumberOfColumns(B);
        float[][] C = createFloatMatrix(mA, nB);
        multiplyD(A, B, C);
        return C;
    }

    public static void multiplyD(float[][] A, float[][] B, float[][] C) {
        if (A != C && B != C) {
            int mA = getNumberOfRows(A);
            int nA = getNumberOfColumns(A);
            int mB = getNumberOfRows(B);
            int nB = getNumberOfColumns(B);
            if (nA != mB) {
                throw new imagingbook.lib.math.Matrix.IncompatibleDimensionsException();
            } else {
                for(int i = 0; i < mA; ++i) {
                    for(int j = 0; j < nB; ++j) {
                        float s = 0.0F;

                        for(int k = 0; k < nA; ++k) {
                            s += A[i][k] * B[k][j];
                        }

                        C[i][j] = s;
                    }
                }

            }
        } else {
            throw new imagingbook.lib.math.Matrix.SameSourceTargetException();
        }
    }
    public static int getNumberOfRows(double[][] A) {
        return A.length;
    }

    public static int getNumberOfColumns(double[][] A) {
        return A[0].length;
    }

    public static int getNumberOfRows(float[][] A) {
        return A.length;
    }

    public static int getNumberOfColumns(float[][] A) {
        return A[0].length;
    }

    public static float[][] createFloatMatrix(int rows, int columns) {
        return new float[rows][columns];
    }
    public static double[][] createDoubleMatrix(int rows, int columns) {
        return new double[rows][columns];
    }
    public static double[] duplicate(double[] A) {
        return (double[])A.clone();
    }

    public static float[] duplicate(float[] A) {
        return (float[])A.clone();
    }

    public static double[][] duplicate(double[][] A) {
        int m = A.length;
        double[][] B = new double[m][];

        for(int i = 0; i < m; ++i) {
            B[i] = (double[])A[i].clone();
        }

        return B;
    }

    public static float[][] duplicate(float[][] A) {
        int m = A.length;
        float[][] B = new float[m][];

        for(int i = 0; i < m; ++i) {
            B[i] = (float[])A[i].clone();
        }

        return B;
    }
}
