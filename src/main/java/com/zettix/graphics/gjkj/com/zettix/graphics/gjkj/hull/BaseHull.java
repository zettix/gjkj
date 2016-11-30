package com.zettix.graphics.gjkj.com.zettix.graphics.gjkj.hull;

import com.zettix.graphics.gjkj.util.M4;
import com.zettix.graphics.gjkj.util.V3;
import com.zettix.graphics.gjkj.util.vecstuff;

import java.util.Vector;

/**
 * BaseHull is a good starting point for many hulls, often subclassed.
 *
 * Basically shows how the tranformation matrices are used.
 *
 * Created by sean on 11/12/16.
 */
public class BaseHull implements Hull {
    // private final Logger LOG = Logger.getLogger(BoxHull.class.getName());
    public M4 transform = new M4().Identity();
    protected Vector<V3> corners = new Vector<>();

    public void ApplyTransform() {
        Double f = 0.0;
        for (int i = 0; i < corners.size(); i++) {
            V3 result = transform.Transform(corners.get(i));
            corners.set(i, result);
        }
    }

    @Override
    public void UpdateTransform(M4 m4) {
        transform.Multiply(m4);
    }

    @Override
    public V3 Support(V3 direction) {
        // Assume unsorted, no caching.
        // Return max(dot(corners[], direction)
        int max_i = 0;
        Double maxdot = vecstuff.dot(direction, corners.get(0));
        Double tmpdot;
        for (int i = 1; i < corners.size(); i++) {
            tmpdot = vecstuff.dot(direction, corners.get(i));
            if (tmpdot > maxdot) {
                maxdot = tmpdot;
                max_i = i;
            }
        }
        V3 result = corners.get(max_i);
        // LOG.warning("Support [dir:" + direction + "] corner: " + result);
        return result;
    }

    @Override
    public V3 GetCorner(int index) {
        return corners.get(index);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < corners.size(); i++) {
            sb.append("Hull: [");
            sb.append(corners.get(i));
            sb.append("]\n");
        }
        return sb.toString();
    }

    @Override
    public String toOpenScad(String module_name, boolean Hit) {
        return "Nope";
    }
}