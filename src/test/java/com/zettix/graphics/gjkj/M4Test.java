package com.zettix.graphics.gjkj;

import org.testng.Assert;
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
        V3 expected = new V3(11.0f, 102.0f, 1003.0f);
        V3 start = new V3(1.0f, 2.0f, 3.0f);
        M4 m = new M4().Identity().Move(10.0f, 100.0f, 1000.0f);
        V3 result = m.Transform(start);
        LOG.error("Result: " + result);
        LOG.error("Expect: " + expected);
        Float d = vecstuff.distanceSquared(result, expected);
        LOG.error("Dist: " + d);
        Assert.assertTrue(0.001f > vecstuff.distanceSquared(result, expected));
    }


    @org.testng.annotations.Test
    public void testMultiply() throws Exception {
        float[] expected;
        expected = new float[] {
                        1.0f, 0.0f, 0.0f, 0.0f,
                        0.0f, 1.0f, 0.0f, 0.0f,
                        0.0f, 0.0f, 1.0f, 0.0f,
                        0.0f, 0.0f, 0.0f, 1.0f};
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
        float[] expected;
        expected = new float[] {
                        1.0f, 0.0f, 0.0f, 0.0f,
                        0.0f, 1.0f, 0.0f, 0.0f,
                        0.0f, 0.0f, 1.0f, 0.0f,
                        0.0f, 0.0f, 0.0f, 1.0f};
        M4 m = new M4();
        m.Identity();
        M4 mm = new M4(m);
        m.Multiply(mm);
        for (int i = 0; i < 16; i++) {
            LOG.info("Asserting element " + i);
            Assert.assertEquals(expected[i], m.get(i), "Element: " + i);
        }
    }
}
