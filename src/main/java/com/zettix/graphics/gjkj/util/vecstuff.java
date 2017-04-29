package com.zettix.graphics.gjkj.util;

/*
 * This is an ad-hoc vector library with dot product, cross product, and a few others.
 *
 * Created by seanbrennan on 11/8/16.
 */

import java.util.logging.Logger;

public class vecstuff {
    public static final Logger LOG = Logger.getLogger(vecstuff.class.getName());

    public static V3 cross(V3 v1, V3 v2) {
        V3 f = new V3();
        f.coords[0] = v1.coords[1] * v2.coords[2] - v1.coords[2] * v2.coords[1];
        f.coords[1] = v2.coords[0] * v1.coords[2] - v2.coords[2] * v1.coords[0];
        f.coords[2] = v1.coords[0] * v2.coords[1] - v1.coords[1] * v2.coords[0];
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
        return (v1.coords[0] * v2.coords[0] +  v1.coords[1] * v2.coords[1]  + v1.coords[2] * v2.coords[2]);
    }

    public static Double distanceSquared(final V3 v1, final V3 v2) {
        Double xx = v1.coords[0] - v2.coords[0];
        Double yy = v1.coords[1] - v2.coords[1];
        Double zz = v1.coords[2] - v2.coords[2];
        return (xx * xx + yy * yy + zz * zz);
    }

    public static V3 add(final V3 v1, final V3 v2) {
        return new V3(v1.coords[0] + v2.coords[0], v1.coords[1] + v2.coords[1], v1.coords[2] + v2.coords[2]);
    }

    public static boolean HitOrigin(final V3 start, final V3 direction) {
        // Given line from start to target, find if origin was hit.
        //
        // p = t * direction + start.  p should be zero, 0=<t<=1
        // therefore 0 = t * dir + start
        double d1 = direction.coords[0];
        double d2 = direction.coords[1];
        double d3 = direction.coords[2];
        d1 *= d1;
        d2 *= d2;
        d3 *= d3;
        double t = 0.0;
        double max = 0.0;
        double div = 1.0;

        if (d1 > max) {
            t = -start.coords[0];
            div = direction.coords[0];
            max = d1;
        }
        if (d2 > max) {
            t = -start.coords[1];
            div = direction.coords[1];
            max = d2;
        }
        if (d3 > max) {
            t = -start.coords[2];
            div = direction.coords[2];
        }
        t /= div;
        if ((t > 1.0001) || (t <= -0.0001)) {
            if (false) {
                StringBuilder sb = new StringBuilder();
                sb.append("Origin not on :(");
                sb.append(start.coords[0]);
                sb.append(",");
                sb.append(start.coords[1]);
                sb.append(",");
                sb.append(start.coords[2]);
                sb.append(") : (");
                sb.append(direction.coords[0]);
                sb.append(",");
                sb.append(direction.coords[1]);
                sb.append(",");
                sb.append(direction.coords[2]);
                sb.append(") T: ");
                sb.append(t);
                LOG.warning(sb.toString());
            }
            return false;
        }
        V3 testPoint = vecstuff.add(new V3(direction).ScalarMultiply(t), start);
        return vecstuff.dot(testPoint, testPoint) <= 0.0001;
    }
}
