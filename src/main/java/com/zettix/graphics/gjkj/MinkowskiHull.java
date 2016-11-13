package com.zettix.graphics.gjkj;

/**
 * Created by sean on 11/13/16.
 */
public class MinkowskiHull extends BaseHull implements Hull {

    protected Hull a, b;

    public MinkowskiHull(Hull a_in, Hull b_in) {
        a = a_in;
        b = b_in;
    }

    @Override
    public V3 Support(V3 direction) {  // a(dir) - b(-dir)
        V3 result = vecstuff.add(a.Support(direction),
                b.Support(new V3(direction).ScalarMultiply(-1.0f)).ScalarMultiply(-1.0f));
        return result;
    }
}