package com.zettix.graphics.gjkj;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.logging.Logger;

/**
 * Created by sean on 11/11/16.
 */
public class SimplexTest {
    private final Logger LOG = Logger.getLogger(SimplexTest.class.getName());
    @Test
    public void testContainsOrigin() throws Exception {

        V3 size = new V3(12.0f, 12.0f, 12.0f);
        Hull hull = new TetHull(size);
        LOG.info("tet hull: " + hull);
        M4 mover = new M4().Identity().Move(-1.2f, -1.3f, -1.4f);
        hull.UpdateTransform(mover);
        hull.ApplyTransform();
        LOG.info("hull: " + hull);
        Simplex simplex = new Simplex(hull);
        Assert.assertTrue(simplex.ContainsOrigin());

        size = new V3(12.0f, 12.0f, 12.0f);
        hull = new TetHull(size);
        LOG.info("tet hull: " + hull);
        mover = new M4().Identity().Move(1.2f, 1.3f, 1.4f);
        hull.UpdateTransform(mover);
        hull.ApplyTransform();
        LOG.info("hull: " + hull);
        simplex = new Simplex(hull);
        Assert.assertFalse(simplex.ContainsOrigin());
    }

    @Test
    public void testSeenMe() throws Exception {

    }

    @Test
    public void testInit() throws Exception {

    }

    @Test
    public void testOnePlex() throws Exception {

    }

    @Test
    public void testTwoPlex() throws Exception {

    }

    @Test
    public void testAddCheck() throws Exception {

    }

    @Test
    public void testThreePlex() throws Exception {
        LOG.warning("XXXXXXXXXXXX testThreePlex() XXXXXXXXXXXXXXXXXXX");
        V3 pointy = new V3(2.0f, 2.0f, 2.0f);
        Hull hull = new TetHull(pointy);
        LOG.info("tet hull: " + hull);
        M4 mover = new M4().Identity().Move(-1.0f, -1.0f, -1.0f);
        hull.UpdateTransform(mover);
        hull.ApplyTransform();
        Simplex simplex = new Simplex(hull);
        simplex.vertices.clear();
        simplex.seen.clear();
        for (int i = 0; i < 3; i++) {
            V3 corner = hull.GetCorner(i);
            simplex.vertices.add(corner);
            simplex.seen.add(corner);
        }
        LOG.warning("SIMPLEX: " + simplex);
        LOG.warning("YYYYYYYYYYYYYYYY About to TEST YYYYYYYYYYYYYYY");
        boolean result = simplex.ThreePlex();
        Assert.assertTrue(result);
    }

    @Test
    public void testFourPlex() throws Exception {

    }

    @Test
    public void testToString() throws Exception {

    }

}
