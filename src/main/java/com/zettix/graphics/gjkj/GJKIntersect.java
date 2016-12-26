package com.zettix.graphics.gjkj;

import com.zettix.graphics.gjkj.hull.Hull;
import com.zettix.graphics.gjkj.hull.MinkowskiHull;

/**
 * This is the main collision routine.  You give it two hulls and then call Intersect()
 * Hulls just conform to the Hull interface.
 *
 * TODO(sean): Add distance estimate per Gilbert, Johnson, and Keerthi's paper.
 *
 * Created by sean on 11/12/16.
 */
public class GJKIntersect {

    protected MinkowskiHull minkowskiHull;
    protected Simplex simplex;
    private Hull a, b;

    public GJKIntersect(Hull hullA, Hull hullB) {
        a = hullA;
        b = hullB;
        minkowskiHull = new MinkowskiHull(a, b);
    }

    public boolean Intersect() {
        simplex = new Simplex(minkowskiHull);
        return simplex.ContainsOrigin();
    }
}
