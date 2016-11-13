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
        m_hull = new MinkowskiHull(a_hull, b_hull);
        simplex = new Simplex(m_hull);
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test
    public void testIntersect() throws Exception {
        Assert.assertTrue(simplex.ContainsOrigin());
    }

}