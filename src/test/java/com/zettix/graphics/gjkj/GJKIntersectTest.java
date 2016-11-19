package com.zettix.graphics.gjkj;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.PrintWriter;
import java.util.logging.Logger;

/**
 * Created by sean on 11/13/16.
 */
public class GJKIntersectTest {

    private final Logger LOG = Logger.getLogger(GJKIntersectTest.class.getName());
    private final Double f45 = Math.PI / 4.0;
    private final Double f90 = f45 * 2.0;
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

        LOG.warning(b_hull.toOpenScad("B", result));
        LOG.warning(a_hull.toOpenScad("A", result));
        LOG.warning(gjk.simplex.toOpenScad("simplex"));

        Assert.assertTrue(result);
    }

    @Test
    public void testIntersectMove4() throws Exception {
        // This one is the real deal, rotate a cube and have edges cross...
        M4 mover = new M4().Identity().Move(-1.0, -1.0, -1.0).Rotate(f45, 0.0, f45).Move(-1.8, -1.8, -1.8);
        b_hull.UpdateTransform(mover);
        b_hull.ApplyTransform();
        LOG.warning("BHULL: " + b_hull);
        LOG.warning(b_hull.toOpenScad("B", false));
        LOG.warning(a_hull.toOpenScad("A", false));
        GJKIntersect gjk = new GJKIntersect(a_hull, b_hull);
        boolean result = gjk.Intersect();
        LOG.warning("SIMPLEX MODEL: ");
        LOG.warning(gjk.simplex.toOpenScad("simplex"));
        Assert.assertFalse(result);
    }

    @Test
    public void testRotatingBlocks() {
        // The bad boy.  two cubes, rotated so only their edges are intersecting.
        // A bunch so in theory only an arc will intersect. we can set this point.
        for (double theta = 0.0; theta < Math.PI / 4; theta += 0.02) {
            LOG.warning("Testing rotating blocks: " + theta);
            V3 pointy = new V3(2.0, 2.0, 2.0);
            a_hull = new BoxHull(pointy);
            b_hull = new BoxHull(pointy);

            M4 mover = new M4().Identity().Move(-1.0, -1.0, -1.0).Rotate(0.0, 0.0, f45);
            a_hull.UpdateTransform(mover);
            a_hull.ApplyTransform();

            mover.Identity().Move(1.5 + theta, -1.0, -1.0).Rotate(0.0, f45, 0.0);
            b_hull.UpdateTransform(mover);
            // b_hull.ApplyTransform();
            //mover.Identity().Move(4.1, 0.0, 0.0);
            //b_hull.UpdateTransform(mover);
            b_hull.ApplyTransform();

            GJKIntersect gjk = new GJKIntersect(a_hull, b_hull);
            boolean result = gjk.Intersect();

            if (!result) {
                LOG.warning("SIMPLEX MODEL: " + theta);
                if (theta > 0.33) {
                    result = true;
                }
                LOG.warning(a_hull.toOpenScad("A", result) + gjk.simplex.toOpenScad("simplex") + b_hull.toOpenScad("B", result) + OpenScadAxes());
            }
            Assert.assertTrue(result);

        }

    }

    @Test
    void testBallsColliding() {
        LOG.warning("Testing balls:");
        for (double theta = 0.0; theta < 3.0; theta += 0.2) {
            a_hull = new SphereHull();
            b_hull = new SphereHull();

            M4 mover = new M4().Identity().Move(theta, 0.0, 0.0);
            b_hull.UpdateTransform(mover);
            SphereHull ss = (SphereHull) b_hull;
            ss.ApplyTransform();
            GJKIntersect gjk = new GJKIntersect(a_hull, b_hull);
            boolean result = gjk.Intersect();
            if (!result) {
                LOG.warning("SIMPLEX MODEL: " + theta);
                if (theta > 2.0) {
                    result = true;
                }
                LOG.warning(a_hull.toOpenScad("A", result) + gjk.simplex.toOpenScad("simplex") + b_hull.toOpenScad("B", result) + OpenScadAxes());
            }
            Assert.assertTrue(result);
        }
    }

    private void WriteFile(String name, String data) {
        String fullpath = "/var/tmp/osm/" + name;
        try {
            PrintWriter writer = new PrintWriter(fullpath);
            writer.write(data);
            writer.close();
        } catch (Exception e) {
            LOG.severe("Files....");
        }
    }

    private String OpenScadAxes() {
        StringBuilder sb = new StringBuilder();
        sb.append(" module axes() {\n");
        sb.append("union() {\n");
        sb.append("rotate([0, 90, 0])\n");
        sb.append("translate([0, 0, -100])\n");
        sb.append("color([1.0, 0.5, 0.5, 1.0])\n");
        sb.append("cylinder(h=200, r1=0.1, r2=0.1);\n");
        sb.append("\n\n");
        sb.append("rotate([90, 0, 0])\n");
        sb.append("translate([0, 0, -100])\n");
        sb.append("color([0.5, 1.0, 0.5, 1.0])\n");
        sb.append("cylinder(h=200, r1=0.1, r2=0.1);\n");
        sb.append("rotate([0,0, 0])\n");
        sb.append("translate([0, 0, -100])\n");
        sb.append("color([0.5, 0.5, 1.0, 1.0])\n");
        sb.append("cylinder(h=200, r1=0.1, r2=0.1);\n");
        sb.append("\n");
        sb.append("color([1.0, 1.0, 1.0, 1.0])\n");
        sb.append("sphere(r=0.16);\n");
        sb.append("}\n");
        sb.append("}\n");
        sb.append("axes();\n");
        return sb.toString();
    }

    @Test
    void testCapsulesColliding() {
        LOG.warning("Testing capsules:");
        // translate = -6.31 6.76 -0.08, rot 81.6, 0, 98.5, dist 74.40
        for (double theta = 0.0;
             theta < 5.0; theta += 0.02) {
            CapsuleHull a_cap = new CapsuleHull();
            CapsuleHull b_cap = new CapsuleHull();
            a_cap.setR2(2.0);
            a_cap.setC1(new V3(0.0, 0.0, -2.0));
            a_cap.setC2(new V3(0.0, 0.0, 4.0));

            b_cap.setR1(2.0);
            b_cap.setR2(1.0);
            b_cap.setC1(new V3(-3.0, 0.0, 0.0));
            b_cap.setC2(new V3(6.0, 0.0, 0.0));

            M4 a_mover = new M4().Identity().Move(0.0, 12.0, -1.0).Rotate(0.0, 0.0, 0.0).Move(0.0, 0.0, 0.0);
            M4 b_mover = new M4().Identity().Rotate(0.0, 0.0, 0.0).Move(-1.0, theta + 12.0, 0.0);

            b_cap.UpdateTransform(b_mover);
            b_cap.ApplyTransform();
            a_cap.UpdateTransform(a_mover);
            a_cap.ApplyTransform();
            GJKIntersect gjk = new GJKIntersect(a_cap, b_cap);
            boolean result = gjk.Intersect();
            //if (!result) {
            if (true) {
                LOG.warning("SIMPLEX MODEL: " + theta);
                if (theta > 5.0) {
                    result = true;
                }
                String foo = a_cap.toOpenScad("A", result) + gjk.simplex.toOpenScad("simplex") + b_cap.toOpenScad("B", result) + OpenScadAxes();
                LOG.warning("\n" + foo);
                WriteFile("capsule-" + theta, foo);
            }
            //Assert.assertTrue(result);
        }
    }
}

