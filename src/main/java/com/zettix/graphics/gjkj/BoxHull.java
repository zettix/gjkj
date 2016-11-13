package com.zettix.graphics.gjkj;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

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

public class BoxHull extends BaseHull implements Hull {
    private final Logger LOG = Logger.getLogger(BoxHull.class.getName());
    public M4 transform = new M4().Identity();

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
                    SetCorner(corner_index, p);
                    corner_index++;
                }
            }
        }
    }

    private void Init() {
        V3 v = new V3(0., 0., 0.);
        int i;
        for (i = 0; i < 8; i++) {
            corners.add(new V3(v));
        }
        LOG.warning("Corner size: " + corners.size());
    }

    private void SetCorner(int idx, V3 p) {
        super.corners.set(idx, p);
    }

    @Override
    public V3 Support(V3 direction) {
        // Assume unsorted, no caching.
        // Return max(dot(corners[], direction)
        // Also note that hill climbing would be an optimization.
        // TODO(sean): Add hill climbing with edge and corner graph.
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
        LOG.warning("Support box [Direction: " + direction + "] corner: " + result);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append("Box: [");
            sb.append(corners.get(i));
            sb.append("]\n");
        }
        return sb.toString();
    }

    @Override
    public String toOpenScad() {
        // 000 00z 0y0 0yz x00 x0z xy0 xyz
        //  0   1   2   3   4   5   6  7
        //  0   4   3   7   1   5   2  6
        Map<Integer, Integer> perm = new HashMap<>();
        perm.put(0, 0);
        perm.put(4, 1);
        perm.put(3, 2);
        perm.put(7, 3);
        perm.put(1, 4);
        perm.put(5, 5);
        perm.put(2, 6);
        perm.put(6, 7);

        StringBuilder sb = new StringBuilder();
        sb.append("CubePoints = [\n");
        for (Integer i = 0; i < 8; i++) {
            V3 t = corners.get(perm.get(i));
            sb.append("[");
            sb.append(t.get(0));
            sb.append(",");
            sb.append(t.get(1));
            sb.append(",");
            sb.append(t.get(2));
            sb.append("]");
            if (i != 7)
                sb.append(",");
            sb.append("\n");
        }
        sb.append("];");
        sb.append("CubeFaces = [\n");
        sb.append("[0,1,2,3],  // bottom\n");
        sb.append("[4,5,1,0],  // front\n");
        sb.append("[7,6,5,4],  // top\n");
        sb.append("[5,6,2,1],  // right\n");
        sb.append("[6,7,3,2],  // back\n");
        sb.append("[7,4,0,3]]; // left\n");
        sb.append("polyhedron( CubePoints, CubeFaces );\n");
        return sb.toString();
    }
}
