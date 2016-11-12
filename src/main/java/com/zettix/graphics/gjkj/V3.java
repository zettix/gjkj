package com.zettix.graphics.gjkj;

import java.util.Vector;

/**
 * Created by seanbrennan on 11/9/16.
 */
public class V3 extends Vector<Float> {
    public V3() {
        Init();
    }

    public void Init() {
        for (int i = 0; i < 3; i++) {
            this.add(i, 0.0f);
        }
    }

    public V3(V3 in) {
        for (int i = 0; i < in.size(); i++) {
            this.add(i, in.get(i));
        }
    }

    public V3 set(float x, float y, float z) {
        this.set(0, x);
        this.set(1, y);
        this.set(2, z);
        return this;
    }

    public V3(float x, float y, float z) {
        Init();
        set(x, y, z);
    }

    public V3 ScalarMultiply(Float f) {
        for (int i = 0; i < 3; i++) {
            set(i, this.get(i) * f);
        }
        return this;
    }
}
