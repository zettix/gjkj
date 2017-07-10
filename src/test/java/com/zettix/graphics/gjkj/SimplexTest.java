package com.zettix.graphics.gjkj;

import com.zettix.graphics.gjkj.hull.Hull;
import com.zettix.graphics.gjkj.hull.TetHull;
import com.zettix.graphics.gjkj.util.M4;
import com.zettix.graphics.gjkj.util.V3;
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

        V3 size = new V3(12.0, 12.0, 12.0);
        Hull hull = new TetHull(size);
        LOG.info("tet hull: " + hull);
        M4 mover = new M4().identity().move(-1.2, -1.3, -1.4);
        hull.TransformWorldSpace(mover);
        LOG.info("hull: " + hull);
        Simplex simplex = new Simplex(hull);
        Assert.assertTrue(simplex.ContainsOrigin());

        size = new V3(12.0, 12.0, 12.0);
        hull = new TetHull(size);
        LOG.info("tet hull: " + hull);
        mover = new M4().identity().move(1.2, 1.3, 1.4);
        hull.TransformWorldSpace(mover);
        LOG.info("hull: " + hull);
        simplex = new Simplex(hull);
        Assert.assertFalse(simplex.ContainsOrigin());
    }

    @Test
    public void testSeenMe() throws Exception {
        V3 v1 = new V3(1.9999999999999998, 0.5431457505076196, -2.0);
        V3 v2 = new V3(1.9999999999999998, 3.3715728752538094, 4.82842712474619);
        V3 v3 = new V3(1.9999999999999998, 0.5431457505076196, -2.0);
        V3 size = new V3(12.0, 12.0, 12.0);
        Hull hull = new TetHull(size);
        // hulls don't have world coords until you do a TransformWorldSpace(m4), once.
        hull.TransformWorldSpace(new M4().identity());
        Simplex simplex = new Simplex(hull);
        Assert.assertFalse(simplex.SeenMe(v1));
        simplex.seen.put(v1.hash(), v1);
        Assert.assertFalse(simplex.SeenMe(v2));
        simplex.seen.put(v2.hash(), v2);
        Assert.assertTrue(simplex.SeenMe(v3));
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

    //@Test
    public void testThreePlex() throws Exception {
        LOG.warning("XXXXXXXXXXXX testThreePlex() XXXXXXXXXXXXXXXXXXX");
        V3 pointy = new V3(2.0, 2.0, 2.0);
        Hull hull = new TetHull(pointy);
        LOG.info("tet hull: " + hull);
        M4 mover = new M4().identity().move(-1.0, -1.0, -1.0);
        hull.TransformWorldSpace(mover);
        Simplex simplex = new Simplex(hull);
        simplex.vertices.clear();
        simplex.seen.clear();
        for (int i = 0; i < 3; i++) {
            V3 corner = hull.GetCorner(i);
            simplex.vertices.add(corner);
            simplex.seen.put(corner.hash(), corner);
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
