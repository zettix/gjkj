package com.zettix.graphics.gjkj;

/**
 * Created by sean on 11/12/16.
 */
public class GJKIntersect {

    protected MinkowskiHull minkowskiHull;
    private Hull a, b;

    public GJKIntersect(Hull hullA, Hull hullB) {
        a = hullA;
        b = hullB;
        minkowskiHull = new MinkowskiHull(a, b);
    }

    public boolean Intersect() {
        Simplex simplex = new Simplex(minkowskiHull);
        return simplex.ContainsOrigin();
    }
}