package com.zettix.graphics.gjkj;

import org.testng.Assert;

import java.util.Vector;
import java.util.logging.Logger;

/**
 * Created by seanbrennan on 11/8/16.
 */

public class BoxHullTest {

    private static final Logger LOG = Logger.getLogger(BoxHullTest.class.getName());

    @org.testng.annotations.Test
    public void testSupport() throws Exception {
        BoxHull boxHull;
        Vector<V3> vv = new Vector<>();
        Float a = 0.0f;
        Float b = 0.0f;
        Float c = 0.0f;
        V3 direction = new V3(1.0f, -1.0f, -1.0f);
        for (int i = 0; i < 3; i++) {
            V3 f = new V3();
            V3 expected = null;
            switch (i) {
                case 0:
                    a = 1.0f;
                    b = 2.0f;
                    c = 3.0f;
                    expected = new V3(1.0f, 0.0f, 0.0f);
                    break;
                case 1:
                    a = 2.0f;
                    b = 3.0f;
                    c = 4.0f;
                    expected = new V3(2.0f, 0.0f, 0.0f);
                    break;
                case 2:
                    a = 5.0f;
                    b = 6.0f;
                    c = 7.0f;
                    expected = new V3(5.0f, 0.0f, 0.0f);
                    break;
                default:
                    break;
            }
            f.set(a, b, c);
            vv.add(f);
            boxHull = new BoxHull(f);
            V3 result = boxHull.Support(direction);
            Float d = vecstuff.distanceSquared(result, expected);
            LOG.warning("box:" + boxHull);
            LOG.warning("Input to box: " + f);
            Assert.assertTrue(d < 0.0001, "Distance: " + d + "is bad! res: " + result + "Expect: " + expected + " )))");
        }
        //boxHull = BoxHull();
    }

    @org.testng.annotations.Test
    public void testApplyTransform() throws Exception {
        LOG.info("testApplyTransform.");
        V3 v3 = new V3(10.0f, 11.0f, 12.0f);
        BoxHull boxHull = new BoxHull(v3);
        M4 m4 = new M4().Identity().Move(1.0f, 2.0f, 3.0f);
        boxHull.UpdateTransform(m4);
        boxHull.ApplyTransform();
        V3 expected = new V3(1.0f, 13.0f, 3.0f);
        V3 direction = new V3(-1.0f, 1.0f, -1.0f);
        V3 result = boxHull.Support(direction);
                    LOG.warning("box:" + boxHull);
        LOG.info("Result: " + result);
        Assert.assertTrue(vecstuff.distanceSquared(result, expected) < 0.001, "Distance too big.");
    }
}
