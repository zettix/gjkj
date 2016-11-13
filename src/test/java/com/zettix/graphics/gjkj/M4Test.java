package com.zettix.graphics.gjkj;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

/**
 * Created by sean on 11/9/16.
 */
public class M4Test {
    private static final Logger LOG = Logger.getLogger(M4Test.class);

    @org.testng.annotations.BeforeMethod
    public void setUp() throws Exception {

    }

    @org.testng.annotations.AfterMethod
    public void tearDown() throws Exception {

    }

    @org.testng.annotations.Test
    public void testSet() throws Exception {
        M4 m1 = new M4().Identity();
        M4 m2 = new M4();
        m2.Set(m1);
        for (int i = 0; i < 16; i++) {
            Assert.assertEquals(m2.get(i), m1.get(i), "Item " + i + "different");
        }

    }

    @org.testng.annotations.Test
    public void testMove() throws Exception {

        LOG.error("Test Move!!!");
        V3 expected = new V3(11.0, 102.0, 1003.0);
        V3 start = new V3(1.0, 2.0, 3.0);
        M4 m = new M4().Identity().Move(10.0, 100.0, 1000.0);
        V3 result = m.Transform(start);
        LOG.error("Result: " + result);
        LOG.error("Expect: " + expected);
        Double d = vecstuff.distanceSquared(result, expected);
        LOG.error("Dist: " + d);
        Assert.assertTrue(0.001f > vecstuff.distanceSquared(result, expected));
    }


    @org.testng.annotations.Test
    public void testMultiply() throws Exception {
        double[] expected;
        expected = new double[]{
                1.0, 0.0, 0.0, 0.0,
                0.0, 1.0, 0.0, 0.0,
                0.0, 0.0, 1.0, 0.0,
                0.0, 0.0, 0.0, 1.0};
        M4 m = new M4();
        m.Identity();
        M4 n = new M4();
        n.Identity();
        m.Multiply(n);
        for (int i = 0; i < 16; i++) {
            LOG.info("Asserting element " + i);
            Assert.assertEquals(expected[i], m.get(i));
        }
    }


    @org.testng.annotations.Test
    public void testIdentity() throws Exception {
        double[] expected;
        expected = new double[]{
                1.0, 0.0, 0.0, 0.0,
                0.0, 1.0, 0.0, 0.0,
                0.0, 0.0, 1.0, 0.0,
                0.0, 0.0, 0.0, 1.0};
        M4 m = new M4();
        m.Identity();
        M4 mm = new M4(m);
        m.Multiply(mm);
        for (int i = 0; i < 16; i++) {
            LOG.info("Asserting element " + i);
            Assert.assertEquals(expected[i], m.get(i), "Element: " + i);
        }
    }

    @Test
    public void testRotate() {
        V3 x = new V3(1.0, 0.0, 0.0);
        V3 z = new V3(0.0, 0.0, 1.0);
        M4 rot = new M4().Identity().Rotate(0.0, Math.PI / 2.0, 0.0);
        V3 res = rot.Transform(x);
        Assert.assertTrue(vecstuff.distanceSquared(res, z) < 0.001);
    }

    @Test
    public void testScale() {
        V3 x = new V3(1.0, 2.0, 3.0);
        V3 z = new V3(-1.0, 20.0, -15.0);
        M4 rot = new M4().Identity().Scale(-1.0, 10.0, -5.0);
        V3 res = rot.Transform(x);
        Assert.assertTrue(vecstuff.distanceSquared(res, z) < 0.001);
    }
}
