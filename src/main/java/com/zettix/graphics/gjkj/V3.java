package com.zettix.graphics.gjkj;

import java.util.Vector;

/**
 * I'm not happy with 3-scalar vectors, so I wrote this.
 *
 * As a class, I can change it I think, to something better.
 *
 * Created by seanbrennan on 11/9/16.
 */
public class V3 extends Vector<Double> {
    public V3() {
        Init();
    }

    public V3(V3 in) {
        for (int i = 0; i < in.size(); i++) {
            this.add(i, in.get(i));
        }
    }

    public V3(double x, double y, double z) {
        Init();
        set(x, y, z);
    }

    public void Init() {
        for (int i = 0; i < 3; i++) {
            this.add(i, 0.0);
        }
    }

    public V3 set(double x, double y, double z) {
        this.set(0, x);
        this.set(1, y);
        this.set(2, z);
        return this;
    }

    public V3 ScalarMultiply(Double f) {
        for (int i = 0; i < 3; i++) {
            set(i, this.get(i) * f);
        }
        return this;
    }
}
