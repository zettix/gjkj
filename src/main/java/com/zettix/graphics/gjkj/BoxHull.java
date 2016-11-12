package com.zettix.graphics.gjkj;

import java.util.Vector;

/**
 * Created by seanbrennan on 11/8/16.
 *
 * unwound, a cube is:
 * 000, 00z, 0y0, 0yz, x00, x0z, xy0, xyz
 *    0,  1,   2,   3,   4,   5,   6,   7
 * 12 edges.  needed?  maybe not, maybe for
 * hill climbing for a faster support funcition.
 *
 */

public class BoxHull implements Hull {
    private Vector<V3> corners = new Vector<>();
    public M4 transform = new M4().Identity();

    private void Init() {
        V3 v = new V3(0.f, 0.f, 0.f);
        int i;
        for (i=0; i < 8; i++) {
           corners.add(new V3(v));
        }
    }

    public void AppyTransform() {
        Float f = 0.0f;
        for (int i = 0; i < 8; i++) {
            V3 result = transform.Transform(corners.get(i));
            corners.set(i, result);
        }
    }

    public void UpdateTransform(M4 m4) {
        transform.Multiply(m4);
    }


    private void SetCorner(int index, V3 v) {
            corners.set(index, v);
    }

    public BoxHull() throws Exception{
        throw new Exception("No empty boxes!");
    }

    public BoxHull(V3 dimensions) {
        int info_len = dimensions.size();
        int corner_index = 0;
        Init();
        for (int ix = 0; ix < 2; ix++) {
            for (int iy = 0; iy < 2; iy++) {
                for (int iz = 0; iz < 2; iz++) {
                    V3 p = new V3(ix * dimensions.get(0), iy * dimensions.get(1), iz * dimensions.get(2));
                    this.SetCorner(corner_index, p);
                    corner_index++;
                }
            }
        }
    }

    public V3 Support(V3 direction) {
        // Assume unsorted, no caching.
        // Return max(dot(corners[], direction)
        // Also note that hill climbing would be an optimization.
        // TODO(sean): Add hill climbing with edge and corner graph.
        int max_i = 0;
        Float maxdot = vecstuff.dot(direction, corners.get(0));
        Float tmpdot;
        for (int i = 1; i < corners.size(); i++) {
            tmpdot = vecstuff.dot(direction, corners.get(i));
            if (tmpdot > maxdot) {
                maxdot = tmpdot;
                max_i = i;
            }
        }
        return new V3(corners.get(max_i));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append("Box: [");
            sb.append(corners.get(i));
            sb.append("]\n");
        }
        return sb.toString();
    }
}
