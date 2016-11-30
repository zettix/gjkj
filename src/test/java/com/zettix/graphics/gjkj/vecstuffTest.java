package com.zettix.graphics.gjkj;

import com.zettix.graphics.gjkj.util.V3;
import com.zettix.graphics.gjkj.util.vecstuff;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by sean on 11/10/16.
 */
public class vecstuffTest {
    @Test
    public void testCross() throws Exception {
        V3 one = new V3(1.0, 0.0, 0.0);
        V3 two = new V3(0.0, 1.0, 0.0);
        V3 cross = vecstuff.cross(one, two);
        V3 expected = new V3(0.0, 0.0, 1.0);
        Double d = vecstuff.distanceSquared(cross, expected);
        Assert.assertTrue(d < 0.0001, "Not small: " + d);
    }

    @Test
    public void testDot() throws Exception {
        V3 one = new V3(1.0, 0.0, 0.0);
        V3 two = new V3(0.0, 1.0, 0.0);
        Double d = vecstuff.dot(one, two);
        Double expected = 0.0;
        Assert.assertEquals(d, expected);
    }

    @Test
    public void testDistanceSquared() throws Exception {
        V3 one = new V3(1.0, 0.0, 0.0);
        V3 two = new V3(0.0, 1.0, 0.0);
        Double d = vecstuff.distanceSquared(one, two);
        Double expected = 2.0;
        Assert.assertEquals(d, expected);
    }

    @Test
    void testAdd() {
        V3 one = new V3(1.0, 0.0, 3.0);
        V3 two = new V3(0.0, 1.0, 2.0);
        V3 out = vecstuff.add(one, two);
        Assert.assertTrue(vecstuff.distanceSquared(out, new V3(1.0, 1.0, 5.0)) < 0.0001, "too far");
    }


    @Test
    public void testHitOrigin() {
        V3 start = new V3(-1.0, -1.0, -1.0);
        V3 direction = new V3(2.0, 2.0, 2.0);
        Assert.assertTrue(vecstuff.HitOrigin(start, direction));

        start = new V3(1.0, 1.0, 1.0);
        direction = new V3(2.0, 2.0, 2.0);
        Assert.assertFalse(vecstuff.HitOrigin(start, direction));

        start = new V3(-1.0, -1.0, -1.5);
        direction = new V3(2.0, 2.0, 2.0);
        Assert.assertFalse(vecstuff.HitOrigin(start, direction));

        start = new V3(-1.0, -1.0, -1.0);
        direction = new V3(2.0, 2.1, 2.0);
        Assert.assertFalse(vecstuff.HitOrigin(start, direction));

        start = new V3(-1.0, -1.0, -1.0);
        direction = new V3(2.0, 2.0, 2.1);
        Assert.assertFalse(vecstuff.HitOrigin(start, direction));

        start = new V3(-0.19802951148834433, 0.19802951148834433, -1.980295085374747);
        direction = new V3(0.19802951148834433, -0.19802951148834433, 1.980295085374747);
        Assert.assertTrue(vecstuff.HitOrigin(start, direction));
    }
}
