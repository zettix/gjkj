package com.zettix.graphics.gjkj;

import com.zettix.graphics.gjkj.hull.BoxHull;
import com.zettix.graphics.gjkj.util.M4;
import com.zettix.graphics.gjkj.util.V3;
import com.zettix.graphics.gjkj.util.vecstuff;
import org.testng.Assert;

import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by seanbrennan on 11/8/16.
 */

public class BoxHullTest {

    private static final Logger LOG = Logger.getLogger(BoxHullTest.class.getName());

    @org.testng.annotations.Test
    public void testSupport() throws Exception {
        BoxHull boxHull;
        List<V3> vv = new ArrayList<>();
        Double a = 0.0;
        Double b = 0.0;
        Double c = 0.0;
        V3 direction = new V3(1.0, -1.0, -1.0);
        for (int i = 0; i < 3; i++) {
            V3 f = new V3();
            V3 expected = null;
            switch (i) {
                case 0:
                    a = 1.0;
                    b = 2.0;
                    c = 3.0;
                    expected = new V3(1.0, 0.0, 0.0);
                    break;
                case 1:
                    a = 2.0;
                    b = 3.0;
                    c = 4.0;
                    expected = new V3(2.0, 0.0, 0.0);
                    break;
                case 2:
                    a = 5.0;
                    b = 6.0;
                    c = 7.0;
                    expected = new V3(5.0, 0.0, 0.0);
                    break;
                default:
                    break;
            }
            f.set(a, b, c);
            vv.add(f);
            boxHull = new BoxHull(f);
            M4 m4 = new M4().identity();
            boxHull.TransformWorldSpace(m4);
            V3 result = boxHull.Support(direction);
            Double d = vecstuff.distanceSquared(result, expected);
            LOG.warning("box:" + boxHull);
            LOG.warning("Input to box: " + f);
            Assert.assertTrue(d < 0.0001, "Distance: " + d + "is bad! res: " +
                              result + "Expect: " + expected + " )))");
        }
        //boxHull = BoxHull();
    }

    @org.testng.annotations.Test
    public void testApplyTransform() throws Exception {
        LOG.info("testApplyTransform.");
        V3 v3 = new V3(10.0, 11.0, 12.0);
        BoxHull boxHull = new BoxHull(v3);
        M4 m4 = new M4().identity().move(1.0, 2.0, 3.0);
        boxHull.TransformWorldSpace(m4);
        V3 expected = new V3(1.0, 13.0, 3.0);
        V3 direction = new V3(-1.0, 1.0, -1.0);
        V3 result = boxHull.Support(direction);
                    LOG.warning("box:" + boxHull);
        LOG.info("Result: " + result);
        Assert.assertTrue(vecstuff.distanceSquared(result, expected) < 0.001, "Distance too big.");
    }
}
