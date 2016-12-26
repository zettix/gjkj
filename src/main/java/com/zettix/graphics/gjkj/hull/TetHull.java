package com.zettix.graphics.gjkj.com.zettix.graphics.gjkj.hull;

import com.zettix.graphics.gjkj.util.M4;
import com.zettix.graphics.gjkj.util.V3;

/**
 * Tetrahedron hull, rather basic, from [0, 0, 0] to [x, y, z] (apex)
 *
 * Created by sean on 11/12/16.
 */
public class TetHull extends BaseHull implements Hull {
    // private final Logger LOG = Logger.getLogger(TetHull.class.getName());
    public M4 transform = new M4().Identity();

    /**
     * @param in Vector to create [0,0,0] [x,0,0] [0,y,0] [0, 0, z] tetrahedron.
     */
    public TetHull(V3 in) {
        corners.add(new V3(0.0, 0.0, 0.0));
        corners.add(new V3(in.get(0), 0.0, 0.0));
        corners.add(new V3(0.0, in.get(1), 0.0));
        corners.add(new V3(0.0, 0.0, in.get(2)));
        // LOG.warning("TET!!!!: " + this.toString());
        // LOG.warning("TET SIZE: " + corners.size());
    }

    @Override
    public String toOpenScad(String module_name, boolean hit) {
        StringBuilder sb = new StringBuilder();
        sb.append("module ");
        sb.append(module_name);
        sb.append("() { \n");
        if (hit) {
            sb.append("color(\"lightblue\"");
        } else {
            sb.append("color(\"blue\"");
        }
        sb.append("polyhedron( points=[");
        for (Integer i = 0; i < 4; i++) {
            sb.append(corners.get(i));
            if (i != 3)
                sb.append(",");
            sb.append("\n");
        }
        sb.append("], faces = [[1,2,3],[0,2,3],[0,1,3],[0,1,2]]);\n");
        sb.append("} \n");
        sb.append(module_name);
        sb.append("();\n");
        return sb.toString();
    }
}
