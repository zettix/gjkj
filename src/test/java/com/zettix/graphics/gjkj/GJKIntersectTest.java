package com.zettix.graphics.gjkj;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by sean on 11/13/16.
 */
public class GJKIntersectTest {

    private Hull a_hull, b_hull, m_hull;

    private Simplex simplex;

    @BeforeMethod
    public void setUp() throws Exception {
        V3 pointy = new V3(2.0f, 2.0f, 2.0f);
        a_hull = new TetHull(pointy);
        b_hull = new BoxHull(pointy);
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test
    public void testIntersect() throws Exception {
        boolean result = new GJKIntersect(a_hull, b_hull).Intersect();
        Assert.assertTrue(result);
    }

    @Test
    public void testIntersectMove1() throws Exception {
        M4 mover = new M4().Identity().Move(1.0f, 1.0f, 1.0f);
        a_hull.UpdateTransform(mover);
        a_hull.ApplyTransform();
        boolean result = new GJKIntersect(a_hull, b_hull).Intersect();
        Assert.assertTrue(result);
    }

    @Test
    public void testIntersectMove2() throws Exception {
        M4 mover = new M4().Identity().Move(3.0f, 3.0f, 3.0f);
        a_hull.UpdateTransform(mover);
        a_hull.ApplyTransform();
        boolean result = new GJKIntersect(a_hull, b_hull).Intersect();
        Assert.assertFalse(result);
    }
}