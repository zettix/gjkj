package com.zettix.graphics.gjkj.hull;

import com.zettix.graphics.gjkj.util.V3;
import com.zettix.graphics.gjkj.util.vecstuff;

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
        V3 d2 = new V3(direction).ScalarMultiply(-1.0);
        V3 tmp = new V3(b.Support(d2)).ScalarMultiply(-1.0);
        V3 result = vecstuff.add(a.Support(direction), tmp);
        return result;
    }
}