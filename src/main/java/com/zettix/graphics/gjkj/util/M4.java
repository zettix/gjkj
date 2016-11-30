package com.zettix.graphics.gjkj.util;

import java.util.Vector;

/**
 * Simple 4x4 matrix library.
 *
 * I cannot find a stripped down matrix library so I wrote this.
 *
 * Created by seanbrennan on 11/9/16.
 */
public class M4 extends Vector<Double> {

    // public static final Logger LOG = Logger.getLogger(M4.class.getName());
    public M4() {
        super(16);
        Init();
    }

    public M4(M4 in) {
        super(16);
        Init();
        Set(in);
    }

    private M4 Init() {
        for (int i = 0; i < 16; i++)
            this.add(0.0);
        return this;
    }

    public M4 Set(M4 in) {
        for (int i = 0; i < in.size(); i++) {
            this.set(i, in.get(i));
        }
        return this;
    }

    public M4 Multiply(M4 in) {
        int i, j, k;
        Double temp;
        M4 c = new M4();
        // Checking numerical recipes in C....
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                temp = 0.0;
                for (k = 0; k < 4; k++) {
                    temp += this.get(i * 4 + k) * in.get(k * 4 + j);
                }
                c.set(i * 4 + j, temp);
            }
        }
        return Set(c);
    }

    public V3 Transform(final V3 in) {
        V3 c = new V3();
        int i, j;
        Double k;
        for (i = 0; i < 3; i++) {
            k = 0.0;
            for (j = 0; j < 3; j++) {
                k += in.get(j) * this.get(i * 4 + j);
            }
            k += this.get(i * 4 + 3);  // implied times 1.0 for [x y z w=1] V3
            c.set(i, k);
        }
        Double x = 0.0;
        for (i = 0; i < 3; i++) {
            k = this.get(12 + i) * in.get(i);  // da db dc
            x += k * k;
        }
        k = this.get(15);
        x += k * k;
        Double xx = 1.0 - x;
        if ( xx * xx > 0.001) {
            for (i = 0; i < 3; i++)
                c.set(i, c.get(i) / Math.sqrt(x));  // w must be 1 for V3.
        }
        return c;
    }

    public M4 Identity() {
        int i, j;
        double k;
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                k = 0.0;
                if (i == j) {
                    k = 1.0;
                }
                this.set(i * 4 + j, k);
            }
        }
        return this;
    }

    public M4 Scale(Double x, Double y, Double z) {
        M4 xform = new M4().Identity();
        xform.set(0, x);
        xform.set(5, y);
        xform.set(10, z);
        this.Multiply(xform);
        return this;
    }

    public M4 Rotate(Double x, Double y, Double z) {
        // Optimization stolen from:
        // http://openinggl.blogspot.com/2012/02/camera-playing-with-matrices.html
        Double a = Math.cos(x);
        Double b = Math.sin(x);
        Double c = Math.cos(y);
        Double d = Math.sin(y);
        Double e = Math.cos(z);
        Double f = Math.sin(z);
        M4 xform = new M4();
        xform.set(0, c * e);
        xform.set(1, -c * f);
        xform.set(2, -d);
        xform.set(3, 0.0);

        xform.set(4, -b * d * e + a * f);
        xform.set(5, b * d * f + a * e);
        xform.set(6, -b * c);
        xform.set(7, 0.0);

        xform.set(8, a * d * e + b * f);
        xform.set(9, -a * d * f + b * e);
        xform.set(10, a * c);
        xform.set(11, 0.0);

        xform.set(12, 0.0);
        xform.set(13, 0.0);
        xform.set(14, 0.0);
        xform.set(15, 1.0);
        this.Multiply(xform);
        return this;
    }

    public M4 Move(Double x, Double y, Double z)
    {
        M4 xform = new M4().Identity();
        xform.set(3, x);
        xform.set(7, y);
        xform.set(11, z);
        this.Multiply(xform);
        return this;
    }
}
