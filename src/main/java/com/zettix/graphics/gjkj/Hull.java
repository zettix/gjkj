package com.zettix.graphics.gjkj;

/**
 * Created by seanbrennan on 11/8/16.
 */

public interface Hull {
    public M4 transform = null;
    V3 Support(V3 direction);
    public void UpdateTransform(M4 m4);
}
