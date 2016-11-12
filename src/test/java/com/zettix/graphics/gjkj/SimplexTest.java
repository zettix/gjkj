package com.zettix.graphics.gjkj;

import org.testng.annotations.Test;

/**
 * Created by sean on 11/11/16.
 */
public class SimplexTest {
    @Test
    public void testContainsOrigin() throws Exception {

        V3 size = new V3(2.0f, 2.0f, 2.0f);
        Hull hull = new BoxHull(size);
        M4 mover = new M4().Identity().Move(-1.0f, -1.0f, -1.0f);
        hull.UpdateTransform(mover);
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

    }

    @Test
    public void testFourPlex() throws Exception {

    }

    @Test
    public void testToString() throws Exception {

    }

}
