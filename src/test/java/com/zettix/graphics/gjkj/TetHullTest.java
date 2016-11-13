package com.zettix.graphics.gjkj;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by sean on 11/12/16.
 */
public class TetHullTest {

    @Test
    public void ConstructorTest() {
        TetHull tetHull = new TetHull(new V3(1.0f, 1.0f, 1.0f));
        V3 expected = new V3(0.0f, 0.0f, 0.0f);
        V3 direction = new V3(-1.0f, -1.0f, -1.0f);
        V3 result = tetHull.Support(direction);
        Assert.assertTrue(vecstuff.distanceSquared(result, expected) < 0.00001);

        expected = new V3(1.0f, 0.0f, 0.0f);
        direction = new V3(1.0f, .10f, -.10f);
        result = tetHull.Support(direction);
        Assert.assertTrue(vecstuff.distanceSquared(result, expected) < 0.00001, "too far...");

        expected = new V3(0.0f, 1.0f, 0.0f);
        direction = new V3(0.1f, 1.0f, -.10f);
        result = tetHull.Support(direction);
        Assert.assertTrue(vecstuff.distanceSquared(result, expected) < 0.00001, "too far...");


        expected = new V3(0.0f, 0.0f, 1.0f);
        direction = new V3(0.1f, -.10f, 1.0f);
        result = tetHull.Support(direction);
        Assert.assertTrue(vecstuff.distanceSquared(result, expected) < 0.00001, "too far...");

    }
}