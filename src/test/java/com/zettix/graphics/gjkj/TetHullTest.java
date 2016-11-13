package com.zettix.graphics.gjkj;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by sean on 11/12/16.
 */
public class TetHullTest {

    @Test
    public void ConstructorTest() {
        TetHull tetHull = new TetHull(new V3(1.0, 1.0, 1.0));
        V3 expected = new V3(0.0, 0.0, 0.0);
        V3 direction = new V3(-1.0, -1.0, -1.0);
        V3 result = tetHull.Support(direction);
        Assert.assertTrue(vecstuff.distanceSquared(result, expected) < 0.00001);

        expected = new V3(1.0, 0.0, 0.0);
        direction = new V3(1.0, .10, -.10);
        result = tetHull.Support(direction);
        Assert.assertTrue(vecstuff.distanceSquared(result, expected) < 0.00001, "too far...");

        expected = new V3(0.0, 1.0, 0.0);
        direction = new V3(0.1f, 1.0, -.10);
        result = tetHull.Support(direction);
        Assert.assertTrue(vecstuff.distanceSquared(result, expected) < 0.00001, "too far...");


        expected = new V3(0.0, 0.0, 1.0);
        direction = new V3(0.1f, -.10, 1.0);
        result = tetHull.Support(direction);
        Assert.assertTrue(vecstuff.distanceSquared(result, expected) < 0.00001, "too far...");

    }
}