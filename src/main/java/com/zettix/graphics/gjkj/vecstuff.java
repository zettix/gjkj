package com.zettix.graphics.gjkj;

/**
 * Created by seanbrennan on 11/8/16.
 */
public class vecstuff {
    public static V3 cross(V3 v1, V3 v2) {
        V3 f = new V3();
        f.set(0, v1.get(1) * v2.get(2) - v1.get(2) * v2.get(1));
        f.set(1, v2.get(0) * v1.get(2) - v2.get(2) * v1.get(0));
        f.set(2, v1.get(0) * v2.get(1) - v1.get(1) * v2.get(0));
        return f;
    }
    /**
     * Computes the dot product of v1 and v2.
    *
     * Actually |v1||v2|cosine(v1, v2), so really expect normalized vectors.
     * @param v1 first vector
     * @param v2 second vector
     * @return the dot product of v1 and v2
     */
    public static Double dot(final V3 v1, final V3 v2) {
        return (v1.get(0) * v2.get(0) +  v1.get(1) * v2.get(1)  + v1.get(2) * v2.get(2));
    }

    public static Double distanceSquared(final V3 v1, final V3 v2) {
        Double xx = v1.get(0) - v2.get(0);
        Double yy = v1.get(1) - v2.get(1);
        Double zz = v1.get(2) - v2.get(2);
        return (xx * xx + yy * yy + zz * zz);
    }

    public static V3 add(final V3 v1, final V3 v2) {
        return new V3(v1.get(0) + v2.get(0), v1.get(1) + v2.get(1), v1.get(2) + v2.get(2));
    }
}
