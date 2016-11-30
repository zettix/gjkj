package com.zettix.graphics.gjkj.util;

/**
 * This is an ad-hoc vector library with dot product, cross product, and a few others.
 *
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

    public static boolean HitOrigin(final V3 start, final V3 direction) {
        // Given line from start to target, find if origin was hit.
        //
        // p = t * direction + start.  p should be zero, 0=<t<=1
        // therefore 0 = t * dir + start
        double d1 = direction.get(0);
        double d2 = direction.get(1);
        double d3 = direction.get(2);
        d1 *= d1;
        d2 *= d2;
        d3 *= d3;
        double t = 0.0;
        double max = 0.0;
        double div = 1.0;

        if (d1 > max) {
            t = -start.get(0);
            div = direction.get(0);
            max = d1;
        }
        if (d2 > max) {
            t = -start.get(1);
            div = direction.get(1);
            max = d2;
        }
        if (d3 > max) {
            t = -start.get(2);
            div = direction.get(2);
        }
        t /= div;
        if ((t > 1.0001) || (t <= 0.0001)) {
            return false;
        }
        V3 testPoint = vecstuff.add(new V3(direction).ScalarMultiply(t), start);
        return vecstuff.dot(testPoint, testPoint) <= 0.0001;
    }
}
