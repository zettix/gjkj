package com.zettix.graphics.gjkj;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by sean on 11/10/16.
 */
public class vecstuffTest {
    @Test
    public void testCross() throws Exception {
        V3 one = new V3(1.0f, 0.0f, 0.0f);
        V3 two = new V3(0.0f, 1.0f, 0.0f);
        V3 cross = vecstuff.cross(one, two);
        V3 expected = new V3(0.0f, 0.0f, 1.0f);
        Float d = vecstuff.distanceSquared(cross, expected);
        Assert.assertTrue(d < 0.0001, "Not small: " + d);
    }

    @Test
    public void testDot() throws Exception {
        V3 one = new V3(1.0f, 0.0f, 0.0f);
        V3 two = new V3(0.0f, 1.0f, 0.0f);
        Float d = vecstuff.dot(one, two);
        Float expected = 0.0f;
        Assert.assertEquals(d, expected);
    }

    @Test
    public void testDistanceSquared() throws Exception {
        V3 one = new V3(1.0f, 0.0f, 0.0f);
        V3 two = new V3(0.0f, 1.0f, 0.0f);
        Float d = vecstuff.distanceSquared(one, two);
        Float expected = 2.0f;
        Assert.assertEquals(d, expected);
    }

}
