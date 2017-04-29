package com.zettix.graphics.gjkj.util;

import java.util.Arrays;

/**
 * I'm not happy with 3-scalar vectors, so I wrote this.
 *
 * As a class, I can change it I think, to something better.
 *
 * Created by seanbrennan on 11/9/16.
 */
public class V3 {
    public double[]  coords = new double[3];

    public V3() {
        Arrays.fill(coords, 0.0);
    }

    public V3(V3 in) {
        coords = Arrays.copyOf(in.coords, 3);
    }

    public V3(double x, double y, double z) {
        set(x, y, z);
    }

    public V3 set(double x, double y, double z) {
        coords[0] = x;
        coords[1] = y;
        coords[2] = z;
        return this;
    }

    public V3 ScalarMultiply(Double f) {
        for (int i = 0; i < 3; i++) {
            coords[i] *= f;
        }
        return this;
    }

    public String toString() {
        return "[" + coords[0] + ", " + coords[1] + ", "+ coords[2] + "]";
    }
}
