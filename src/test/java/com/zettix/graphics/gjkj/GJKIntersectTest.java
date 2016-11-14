package com.zettix.graphics.gjkj;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.logging.Logger;

/**
 * Created by sean on 11/13/16.
 */
public class GJKIntersectTest {

    private final Logger LOG = Logger.getLogger(GJKIntersectTest.class.getName());
    private Hull a_hull, b_hull, m_hull;
    private Simplex simplex;

    @BeforeMethod
    public void setUp() throws Exception {
        V3 pointy = new V3(2.0, 2.0, 2.0);
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
        M4 mover = new M4().Identity().Move(1.0, 1.0, 1.0);
        a_hull.UpdateTransform(mover);
        a_hull.ApplyTransform();
        boolean result = new GJKIntersect(a_hull, b_hull).Intersect();
        Assert.assertTrue(result);
    }

    @Test
    public void testIntersectMove2() throws Exception {
        M4 mover = new M4().Identity().Move(3.0, 3.0, 3.0);
        a_hull.UpdateTransform(mover);
        a_hull.ApplyTransform();
        boolean result = new GJKIntersect(a_hull, b_hull).Intersect();
        Assert.assertFalse(result);
    }

    @Test
    public void testIntersectMove3() throws Exception {
        Double f45 = Math.PI / 4.0;
        // This one is the real deal, rotate a cube and have edges cross...
        M4 mover = new M4().Identity().Move(-1.0, -1.0, -1.0).Rotate(f45, 0.0, f45);
        b_hull.UpdateTransform(mover);
        b_hull.ApplyTransform();
        GJKIntersect gjk = new GJKIntersect(a_hull, b_hull);
        boolean result = gjk.Intersect();

        LOG.warning(b_hull.toOpenScad());
        LOG.warning(a_hull.toOpenScad());
        LOG.warning(gjk.simplex.toOpenScad());

        Assert.assertTrue(result);
    }

    @Test
    public void testIntersectMove4() throws Exception {
        Double f45 = Math.PI / 4.0;
        // This one is the real deal, rotate a cube and have edges cross...
        M4 mover = new M4().Identity().Move(-1.0, -1.0, -1.0).Rotate(f45, 0.0, f45).Move(-1.8, -1.8, -1.8);
        b_hull.UpdateTransform(mover);
        b_hull.ApplyTransform();
        LOG.warning("BHULL: " + b_hull);
        LOG.warning(b_hull.toOpenScad());
        LOG.warning(a_hull.toOpenScad());
        GJKIntersect gjk = new GJKIntersect(a_hull, b_hull);
        boolean result = gjk.Intersect();
        LOG.warning("SIMPLEX MODEL: ");
        LOG.warning(gjk.simplex.toOpenScad());
        Assert.assertFalse(result);
    }
}
