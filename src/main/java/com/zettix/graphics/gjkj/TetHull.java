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
}
