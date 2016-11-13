package com.zettix.graphics.gjkj;

import java.util.logging.Logger;

/**
 * Created by sean on 11/12/16.
 */
public class TetHull extends BaseHull implements Hull {
    private final Logger LOG = Logger.getLogger(TetHull.class.getName());
    public M4 transform = new M4().Identity();

    /**
     * @param in Vector to create [0,0,0] [x,0,0] [0,y,0] [0, 0, z] tetrahedron.
     */
    public TetHull(V3 in) {
        corners.add(new V3(0.0, 0.0, 0.0));
        corners.add(new V3(in.get(0), 0.0, 0.0));
        corners.add(new V3(0.0, in.get(1), 0.0));
        corners.add(new V3(0.0, 0.0, in.get(2)));
        LOG.warning("TET!!!!: " + this.toString());
        LOG.warning("TET SIZE: " + corners.size());
    }

    @Override
    public String toOpenScad() {
        StringBuilder sb = new StringBuilder();
        sb.append("polyhedron( points=[");
        for (Integer i = 0; i < 4; i++) {
            V3 t = corners.get(i);
            sb.append("[");
            sb.append(t.get(0));
            sb.append(",");
            sb.append(t.get(1));
            sb.append(",");
            sb.append(t.get(2));
            sb.append("]");
            if (i != 3)
                sb.append(",");
            sb.append("\n");
        }
        sb.append("], faces = [[1,2,3],[0,2,3],[0,1,3],[0,1,2]]);\n");
        return sb.toString();
    }
}
