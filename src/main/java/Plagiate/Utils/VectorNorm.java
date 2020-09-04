package Plagiate.Utils;

public abstract class VectorNorm {
    static String wrongLengthMessage = "feature vectors must be of same length";

    public VectorNorm() {
    }

    public abstract double magnitude(double[] var1);

    public abstract double magnitude(int[] var1);

    public abstract double distance(double[] var1, double[] var2);

    public abstract double distance(float[] var1, float[] var2);

    public abstract double distance(int[] var1, int[] var2);

    public abstract double distance2(double[] var1, double[] var2);

    public abstract double distance2(float[] var1, float[] var2);

    public abstract double distance2(int[] var1, int[] var2);

    public abstract double getScale(int var1);


    public static class L1 extends VectorNorm {
        public L1() {
        }

        public double magnitude(double[] X) {
            double sum = 0.0D;

            for (int i = 0; i < X.length; ++i) {
                sum += Math.abs(X[i]);
            }

            return sum;
        }

        public double magnitude(int[] X) {
            long sum = 0L;

            for (int i = 0; i < X.length; ++i) {
                sum += (long) Math.abs(X[i]);
            }

            return (double) sum;
        }

        public double distance(double[] X, double[] Y) {
            if (X.length != Y.length) {
                throw new IllegalArgumentException(wrongLengthMessage);
            } else {
                double sum = 0.0D;

                for (int i = 0; i < X.length; ++i) {
                    double d = X[i] - Y[i];
                    sum += Math.abs(d);
                }

                return sum;
            }
        }

        public double distance(int[] X, int[] Y) {
            if (X.length != Y.length) {
                throw new IllegalArgumentException(wrongLengthMessage);
            } else {
                int sum = 0;

                for (int i = 0; i < X.length; ++i) {
                    sum += Math.abs(X[i] - Y[i]);
                }

                return (double) sum;
            }
        }

        public double distance2(double[] a, double[] b) {
            double d = this.distance(a, b);
            return d * d;
        }

        public double distance2(int[] a, int[] b) {
            double d = this.distance(a, b);
            return d * d;
        }

        public double getScale(int n) {
            return 1.0D / (double) n;
        }

        public double distance2(float[] a, float[] b) {
            double d = this.distance(a, b);
            return d * d;
        }

        public double distance(float[] X, float[] Y) {
            if (X.length != Y.length) {
                throw new IllegalArgumentException(wrongLengthMessage);
            } else {
                double sum = 0.0D;

                for (int i = 0; i < X.length; ++i) {
                    sum += (double) Math.abs(X[i] - Y[i]);
                }

                return sum;
            }
        }
    }

    public static class L2 extends VectorNorm {
        public L2() {
        }

        public double magnitude(double[] X) {
            double sum = 0.0D;

            for (int i = 0; i < X.length; ++i) {
                sum += X[i] * X[i];
            }

            return Math.sqrt(sum);
        }

        public double magnitude(int[] X) {
            long sum = 0L;

            for (int i = 0; i < X.length; ++i) {
                sum += (long) (X[i] * X[i]);
            }

            return Math.sqrt((double) sum);
        }

        public double distance(double[] a, double[] b) {
            return Math.sqrt(this.distance2(a, b));
        }

        public double distance2(double[] X, double[] Y) {
            if (X.length != Y.length) {
                throw new IllegalArgumentException(wrongLengthMessage);
            } else {
                double sum = 0.0D;

                for (int i = 0; i < X.length; ++i) {
                    double d = X[i] - Y[i];
                    sum += d * d;
                }

                return sum;
            }
        }

        public double distance(int[] a, int[] b) {
            return Math.sqrt(this.distance2(a, b));
        }

        public double distance2(int[] X, int[] Y) {
            if (X.length != Y.length) {
                throw new IllegalArgumentException(wrongLengthMessage);
            } else {
                int sum = 0;

                for (int i = 0; i < X.length; ++i) {
                    int d = X[i] - Y[i];
                    sum += d * d;
                }

                return (double) sum;
            }
        }

        public double getScale(int n) {
            return Math.sqrt(1.0D / (double) n);
        }

        public double distance(float[] a, float[] b) {
            return Math.sqrt(this.distance2(a, b));
        }

        public double distance2(float[] X, float[] Y) {
            if (X.length != Y.length) {
                throw new IllegalArgumentException(wrongLengthMessage);
            } else {
                double sum = 0.0D;

                for (int i = 0; i < X.length; ++i) {
                    double d = (double) (X[i] - Y[i]);
                    sum += d * d;
                }

                return sum;
            }
        }
    }

    public static class Linf extends VectorNorm {
        public Linf() {
        }

        public double magnitude(double[] X) {
            double dmax = 0.0D;

            for (int i = 0; i < X.length; ++i) {
                dmax = Math.max(dmax, Math.abs(X[i]));
            }

            return dmax;
        }

        public double magnitude(int[] X) {
            int dmax = 0;

            for (int i = 0; i < X.length; ++i) {
                dmax = Math.max(dmax, Math.abs(X[i]));
            }

            return (double) dmax;
        }

        public double distance(double[] X, double[] Y) {
            if (X.length != Y.length) {
                throw new IllegalArgumentException(wrongLengthMessage);
            } else {
                double dmax = 0.0D;

                for (int i = 0; i < X.length; ++i) {
                    double d = X[i] - Y[i];
                    dmax = Math.max(dmax, Math.abs(d));
                }

                return dmax;
            }
        }

        public double distance(int[] X, int[] Y) {
            if (X.length != Y.length) {
                throw new IllegalArgumentException(wrongLengthMessage);
            } else {
                int dmax = 0;

                for (int i = 0; i < X.length; ++i) {
                    int d = Math.abs(X[i] - Y[i]);
                    dmax = Math.max(dmax, d);
                }

                return (double) dmax;
            }
        }

        public double distance2(double[] a, double[] b) {
            double d = this.distance(a, b);
            return d * d;
        }

        public double distance2(int[] a, int[] b) {
            double d = this.distance(a, b);
            return d * d;
        }

        public double getScale(int n) {
            return 1.0D;
        }

        public double distance2(float[] a, float[] b) {
            double d = this.distance(a, b);
            return d * d;
        }

        public double distance(float[] X, float[] Y) {
            if (X.length != Y.length) {
                throw new IllegalArgumentException(wrongLengthMessage);
            } else {
                float dmax = 0.0F;

                for (int i = 0; i < X.length; ++i) {
                    float d = Math.abs(X[i] - Y[i]);
                    dmax = Math.max(dmax, d);
                }

                return (double) dmax;
            }
        }
    }

    protected interface Creator {
        VectorNorm create();
    }

    public static enum NormType implements Creator {
        L1 {
            public VectorNorm create() {
                return new VectorNorm.L1();
            }
        },
        L2 {
            public VectorNorm create() {
                return new VectorNorm.L2();
            }
        },
        Linf {
            public VectorNorm create() {
                return new VectorNorm.Linf();
            }
        };

        private NormType() {
        }
    }

}
