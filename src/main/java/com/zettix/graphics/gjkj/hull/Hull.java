package com.zettix.graphics.gjkj.hull;

import com.zettix.graphics.gjkj.util.M4;
import com.zettix.graphics.gjkj.util.V3;

/**
 * This interface defines a GJK hull with a few benefits:
 * Support(V3): only real part of a GJK hull.
 *
 * ApplyTransform(): Call this to transform hull orientation to internal matrix.
 *    Suggest you start with new M4().Identity();
 * UpdateTransform(m4): just multiplies the internal transform matrix.
 *
 * GetCorner(index) :  actually this sucks, try to remove it.
 * toString() for debugging. not needed for actual collision detection.
 * toOpenScad() for visualization of results. not needed for actual collision detection.
 *
 * Created by seanbrennan on 11/8/16.
 */

public interface Hull {
    V3 Support(V3 direction);
    V3 GetCorner(int index);

    void TransformObjectSpace(M4 m4);

    void TransformWorldSpace(M4 m4);
    String toString();
    String toOpenScad(String module_name, boolean hit);
}
