package com.zettix.graphics.gjkj;

/**
 * Created by seanbrennan on 11/8/16.
 */

public interface Hull {
    M4 transform = null;
    V3 Support(V3 direction);

    V3 GetCorner(int index);

    void ApplyTransform();

    void UpdateTransform(M4 m4);

    String toOpenScad();
}
