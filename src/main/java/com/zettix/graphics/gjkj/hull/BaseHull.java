package com.zettix.graphics.gjkj.hull;

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
    public M4 worldTransform = new M4().Identity();
    public M4 objectTransform = new M4().Identity();

    protected Vector<V3> worldCorners = new Vector<>();
    protected Vector<V3> objectCorners = new Vector<>();


    @Override
    public void TransformObjectSpace(M4 m) {
        Double f = 0.0;
        for (int i = 0; i < objectCorners.size(); i++) {
            V3 result = m.Transform(objectCorners.get(i));
            objectCorners.set(i, result);
        }
    }

    @Override
    public void TransformWorldSpace(M4 m) {
        worldCorners = new Vector<>();
        for (int i = 0; i < objectCorners.size(); i++) {
            V3 result = m.Transform(objectCorners.get(i));
            worldCorners.add(result);
        }
    }


    @Override
    public V3 Support(V3 direction) {
        // Assume unsorted, no caching.
        // Return max(dot(corners[], direction)
        int max_i = 0;
        Double maxdot = vecstuff.dot(direction, worldCorners.get(0));
        Double tmpdot;
        for (int i = 1; i < worldCorners.size(); i++) {
            tmpdot = vecstuff.dot(direction, worldCorners.get(i));
            if (tmpdot > maxdot) {
                maxdot = tmpdot;
                max_i = i;
            }
        }
        V3 result = worldCorners.get(max_i);
        // LOG.warning("Support [dir:" + direction + "] corner: " + result);
        return result;
    }

    @Override
    public V3 GetCorner(int index) {
        return worldCorners.get(index);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < worldCorners.size(); i++) {
            sb.append("Hull: [");
            sb.append(worldCorners.get(i));
            sb.append("]\n");
        }
        return sb.toString();
    }

    @Override
    public String toOpenScad(String module_name, boolean Hit) {
        return "Nope";
    }
}
