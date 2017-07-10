package com.zettix.graphics.gjkj.util;

import java.util.Arrays;

/**
 * Simple 4x4 matrix library.
 *
 * I cannot find a stripped down matrix library so I wrote this.
 *
 * Created by seanbrennan on 11/9/16.
 */
public class M4 {
    public double[] matrix = new double[16];

    // public static final Logger LOG = Logger.getLogger(M4.class.getName());
    public M4() {
        Arrays.fill(matrix, 0.0);
    }

    public M4(M4 in) {
        set(in);
    }

    public M4 set(M4 in) {
        matrix = Arrays.copyOf(in.matrix, 16);
        return this;
    }

    public M4 multiply(M4 in) {
        int i, j, k;
        Double temp;
        double[] c = new double[16];
        // Checking numerical recipes in C....
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                temp = 0.0;
                for (k = 0; k < 4; k++) {
                    temp += matrix[i * 4 + k] * in.matrix[k * 4 + j];
                }
                c[i * 4 + j] = temp;
            }
        }
        matrix = c;
        return this;
    }

    public V3 transform(final V3 in) {
        V3 c = new V3();
        int i, j;
        Double k;
        for (i = 0; i < 3; i++) {
            k = 0.0;
            for (j = 0; j < 3; j++) {
                k += in.coords[j] * matrix[i * 4 + j];
            }
            k += matrix[i * 4 + 3];  // implied times 1.0 for [x y z w=1] V3
            c.coords[i]  = k;
        }
        Double x = 0.0;
        for (i = 0; i < 3; i++) {
            k = matrix[12 + i] * in.coords[i];  // da db dc
            x += k * k;
        }
        k = matrix[15];
        x += k * k;
        Double xx = 1.0 - x;
        if ( xx * xx > 0.001) {
            for (i = 0; i < 3; i++)
                c.coords[i] /=  Math.sqrt(x);  // w must be 1 for V3.
        }
        return c;
    }

    public M4 identity() {
        int i, j;
        double k;
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                k = 0.0;
                if (i == j) {
                    k = 1.0;
                }
                matrix[i * 4 + j] = k;
            }
        }
        return this;
    }

    public M4 scale(Double x, Double y, Double z) {
        M4 xform = new M4().identity();
        xform.matrix[0] = x;
        xform.matrix[5] = y;
        xform.matrix[10] = z;
        this.multiply(xform);
        return this;
    }

    public M4 rotate(Double x, Double y, Double z) {
        // Optimization stolen from:
        // http://openinggl.blogspot.com/2012/02/camera-playing-with-matrices.html
        Double a = Math.cos(x);
        Double b = Math.sin(x);
        Double c = Math.cos(y);
        Double d = Math.sin(y);
        Double e = Math.cos(z);
        Double f = Math.sin(z);
        M4 xform = new M4();
        xform.matrix[0] = c * e;
        xform.matrix[1] = -c * f;
        xform.matrix[2] = -d;
        xform.matrix[3] = 0.0;

        xform.matrix[4] = -b * d * e + a * f;
        xform.matrix[5] = b * d * f + a * e;
        xform.matrix[6] = -b * c;
        xform.matrix[7] = 0.0;

        xform.matrix[8] = a * d * e + b * f;
        xform.matrix[9] = -a * d * f + b * e;
        xform.matrix[10] = a * c;
        xform.matrix[11] = 0.0;

        xform.matrix[12] = 0.0;
        xform.matrix[13] = 0.0;
        xform.matrix[14] = 0.0;
        xform.matrix[15] = 1.0;
        this.multiply(xform);
        return this;
    }

    public M4 move(Double x, Double y, Double z)
    {
        M4 xform = new M4().identity();
        xform.matrix[3] = x;
        xform.matrix[7] = y;
        xform.matrix[11] = z;
        this.multiply(xform);
        return this;
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        //
        sb.append("[[");
        sb.append(matrix[0]);
        sb.append("]");
        sb.append(", ");

        sb.append("[");
        sb.append(matrix[1]);
        sb.append("]");
        sb.append(", ");

        sb.append("[");
        sb.append(matrix[2]);
        sb.append("]");
        sb.append(", ");

        sb.append("[");
        sb.append(matrix[3]);
        sb.append("]");
        sb.append(",\n");
        //
        sb.append(" [");
        sb.append(matrix[4]);
        sb.append("]");
        sb.append(", ");

        sb.append("[");
        sb.append(matrix[5]);
        sb.append("]");
        sb.append(", ");

        sb.append("[");
        sb.append(matrix[6]);
        sb.append("]");
        sb.append(", ");

        sb.append("[");
        sb.append(matrix[7]);
        sb.append("]");
        sb.append(",\n");
        //
        sb.append(" [");
        sb.append(matrix[8]);
        sb.append("]");
        sb.append(", ");

        sb.append("[");
        sb.append(matrix[9]);
        sb.append("]");
        sb.append(", ");

        sb.append("[");
        sb.append(matrix[10]);
        sb.append("]");
        sb.append(", ");

        sb.append("[");
        sb.append(matrix[11]);
        sb.append("]");
        sb.append(",\n");
        //
        sb.append(" [");
        sb.append(matrix[12]);
        sb.append("]");
        sb.append(", ");

        sb.append("[");
        sb.append(matrix[13]);
        sb.append("]");
        sb.append(", ");

        sb.append("[");
        sb.append(matrix[14]);
        sb.append("]");
        sb.append(", ");

        sb.append("[");
        sb.append(matrix[15]);
        sb.append("]");
        sb.append("]\n");
        return sb.toString();
    }
}
