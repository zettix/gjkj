package com.zettix.graphics.gjkj;

import com.zettix.graphics.gjkj.hull.Hull;
import com.zettix.graphics.gjkj.hull.MinkowskiHull;

/**
 * 3D collision detector.
 * Sample use:
 * GJKIntersetc i = new GKLIntersect(hullA, hullB);
 * Boolean isIntersecting =  i.Intersect()
 * 
 * @see Hull
 *
 * TODO(sean): Add distance estimate per Gilbert, Johnson, and Keerthi's paper.
 *
 * Created by sean on 11/12/16.
 */
public class GJKIntersect {

    protected MinkowskiHull minkowskiHull;
    protected Simplex simplex;
    private Hull a, b;

    /**
     * Create a GJKIntersect object.
     */
    public GJKIntersect(Hull hullA, Hull hullB) {
        a = hullA;
        b = hullB;
        minkowskiHull = new MinkowskiHull(a, b);
    }

    /**
     * Detect if two internal hulls intersect.
     */
    public boolean Intersect() {
        simplex = new Simplex(minkowskiHull);
        return simplex.ContainsOrigin();
    }
}
