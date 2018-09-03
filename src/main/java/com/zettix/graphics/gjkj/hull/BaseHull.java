package com.zettix.graphics.gjkj.hull;

import com.zettix.graphics.gjkj.util.M4;
import com.zettix.graphics.gjkj.util.V3;
import com.zettix.graphics.gjkj.util.vecutil;

import java.util.ArrayList;
import java.util.List;

/**
 * BaseHull is a good starting point for many hulls, often subclassed.
 *
 * Basically shows how the tranformation matrices are used.
 *
 * Created by sean on 11/12/16.
 */
public class BaseHull implements Hull {
    // private final Logger LOG = Logger.getLogger(BoxHull.class.getName());
    protected List<V3> worldCorners = new ArrayList<>();
    protected List<V3> objectCorners = new ArrayList<>();

    public void TransformObjectSpace(M4 m) {
        Double f = 0.0;
        for (int i = 0; i < objectCorners.size(); i++) {
            V3 result = m.transform(objectCorners.get(i));
            objectCorners.set(i, result);
        }
    }

    public void TransformWorldSpace(M4 m) {
        worldCorners = new ArrayList<>();
        for (V3 corner : objectCorners)  {
            V3 result = m.transform(corner);
            worldCorners.add(result);
        }
    }

    public V3 Support(V3 direction) {
        // Assume unsorted, no caching.
        // Return max(dot_unsafe(corners[], direction)
        V3 max_v = null;
        Double maxdot = -Double.MAX_VALUE;
        Double tmpdot;
        for (V3 corner : worldCorners) {
            tmpdot = vecutil.dot_unsafe(direction, corner);
            if (tmpdot > maxdot) {
                maxdot = tmpdot;
                max_v = corner;
            }
        }
        // LOG.warning("Support [dir:" + direction + "] corner: " + result);
        return max_v;
    }

    public V3 GetCorner(int index) {
        return worldCorners.get(index);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (V3 corner : worldCorners) {
            sb.append("Hull: [");
            sb.append(corner);
            sb.append("]\n");
        }
        return sb.toString();
    }

    public String toOpenScad(String module_name, boolean Hit) {
        return "Nope";
    }
}
