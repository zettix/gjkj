package com.zettix.graphics.gjkj.hull;

import com.zettix.graphics.gjkj.util.V3;
import com.zettix.graphics.gjkj.util.vecutil;

import java.util.logging.Logger;
// import java.util.logging.L//

import java.util.HashMap;
import java.util.Map;

/**
 * Created by seanbrennan on 11/8/16.
 *
 * This is the box hull, [0,0,0] - [x, y, z] initialization.
 *
 * unwound, a cube is:
 * 000, 00z, 0y0, 0yz, x00, x0z, xy0, xyz
 *    0,  1,   2,   3,   4,   5,   6,   7
 * 12 edges.  needed?  maybe not, maybe for
 * hill climbing for a faster support function.
 *
 */

public class BoxHull extends BaseHull implements Hull {
    // private final Logger LOG = Logger.getLogger(BoxHull.class.getName());


    private final Logger LOGGER = Logger.getLogger(BoxHull.class.getName());

    public BoxHull() throws Exception{
        throw new Exception("No empty boxes!");
    }

    public BoxHull(final V3 dimensions) {
        int corner_index = 0;
        for (int ix = 0; ix < 2; ix++) {
            for (int iy = 0; iy < 2; iy++) {
                for (int iz = 0; iz < 2; iz++) {
                    V3 p = new V3(ix * dimensions.coords[0], iy * dimensions.coords[1], iz * dimensions.coords[2]);
                    objectCorners.add(p);
                    corner_index++;
                }
            }
        }
    }

    @Override
    public V3 Support(V3 direction) {
        // Assume unsorted, no caching.
        // Return max(dot_unsafe(corners[], direction)
        // Also note that hill climbing would be an optimization.
        // TODO(sean): Add hill climbing with edge and corner graph.
        V3 max_v = null;
        Double maxdot = -Double.MAX_VALUE;
        Double tmpdot;
        assert worldCorners != null : "World Corners Can Not Be Null!!";
        assert worldCorners.size() != 0 : "World Must Have Coreners!";
        LOGGER.warning("WORLD CORMERS" + worldCorners);
        for (V3 corner : worldCorners) {
            //falsfalseeLOGGER.warning("ZZZZZZZZZZZZZZZZZZZZZZZ corener:" + max_v);
            tmpdot = vecutil.dot_unsafe(direction, corner);
            if (tmpdot > maxdot) {
                maxdot = tmpdot;
                max_v = corner;
            }
        }
        //LOGGER.warning("Support [dir:" + direction + "] corner: " + max_v);
        assert max_v != null : "MAX_V IS NULL???";
        return max_v;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Box ");
        sb.append(super.toString());
        return sb.toString();
    }

    @Override
    public String toOpenScad(String module_name, boolean hit) {
        // 000 00z 0y0 0yz x00 x0z xy0 xyz  This is here because
        //  0   1   2   3   4   5   6  7    my map and a cube map
        //  0   4   3   7   1   5   2  6    I'm using are different.  Plus I'm lazy. This one works.
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
        sb.append("module ");
        sb.append(module_name);
        sb.append("() {\n");
        sb.append("CubePoints = [\n");
        for (Integer i = 0; i < 8; i++) {
            sb.append(worldCorners.get(perm.get(i)));
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
        if (hit) {
            sb.append("color(\"red\")");
        } else {
            sb.append("color(\"green\")");
        }
        sb.append("polyhedron( CubePoints, CubeFaces );\n");
        sb.append("}\n");
        sb.append(module_name);
        sb.append("();\n");
        return sb.toString();
    }
}
