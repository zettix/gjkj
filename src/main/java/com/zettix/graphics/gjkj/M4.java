package com.zettix.graphics.gjkj;

import java.util.Vector;
import java.util.logging.Logger;

/**
 * Created by seanbrennan on 11/9/16.
 */
public class M4 extends Vector<Float> {

    public static final Logger LOG = Logger.getLogger(M4.class.getName());
    public M4() {
        super(16);
        Init();
    };

    public M4(M4 in) {
        super(16);
        Init();
        Set(in);
    }

    private M4 Init() {
        for (int i = 0; i < 16; i++)
            this.add(0.0f);
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
        Float temp;
        M4 c = new M4();
        // Checking numerical recipes in C....
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                temp = 0.0f;
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
        Float k;
        for (i = 0; i < 3; i++) {
            k = 0.0f;
            for (j = 0; j < 3; j++) {
                k += in.get(j) * this.get(i * 4 + j);
            }
            k += this.get(i * 4 + 3);  // implied times 1.0 for [x y z w=1] V3
            c.set(i, k);
        }
        Float x = 0.0f;
        for (i = 0; i < 3; i++) {
            k = this.get(12 + i) * in.get(i);  // da db dc
            x += k;
        }
        k = this.get(15);
        x += k;
        Float xx = 1.0f - x;
        if ( xx * xx > 0.001) {
            for (i = 0; i < 3; i++)
                c.set(i, c.get(i) / x);
        }
        return c;
    }

    public M4 Identity() {
        int i, j;
        float k;
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                k = 0.0f;
                if (i == j) {
                    k = 1.0f;
                }
                this.set(i * 4 + j, k);
            }
        }
        return this;
    }

    public M4 Move(Float x, Float y, Float z)
    {
        M4 xform = new M4().Identity();
        xform.set(3, x);
        xform.set(7, y);
        xform.set(11, z);
        this.Multiply(xform);
        return this;
    }
}
