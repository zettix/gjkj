package com.zettix.graphics.gjkj.hull;

import com.zettix.graphics.gjkj.util.M4;
import com.zettix.graphics.gjkj.util.V3;

/**
 * This interface defines a GJK hull with a few benefits:
 * Support(V3): only real part of a GJK hull.
 *
 * TransformObjectSpace(): Call this to set up the hull with scaling/positioning,rotating to match model.
 *    Suggest you start with new M4().Identity();
 * TransformWorldSpace(): Call this when positioning the hull in the world, should be copy of model transform.
 *
 * GetCorner(index) :  For polytopes, returns vertex at index.
 * toString() for debugging. not needed for actual collision detection.
 * toOpenScad() for visualization of results. not needed for actual collision detection.
 *
 * Created by sean on 11/8/16.
 */

public interface Hull {
    V3 Support(V3 direction);
    V3 GetCorner(int index);

    void TransformObjectSpace(M4 m4);
    void TransformWorldSpace(M4 m4);
    String toString();
    String toOpenScad(String module_name, boolean hit);
}
