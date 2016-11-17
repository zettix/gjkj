package com.zettix.graphics.gjkj;

/**
 * Created by sean on 11/14/16.
 */
public class SphereHull extends BaseHull implements Hull {

    protected V3 center = new V3(0.0, 0.0, 0.0);
    protected double radius = 1.0;

    public SphereHull() {
    }

    public V3 getCenter() {
        return center;
    }

    public void setCenter(V3 center) {
        this.center = center;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public V3 Support(V3 direction) {
        V3 far = new V3(direction);
        far.ScalarMultiply(radius / Math.sqrt(vecstuff.dot(direction, direction)));
        return vecstuff.add(center, far);
    }

    public V3 GetCorner(int index) {
        return Support(new V3(0.0, 0.0, 1.0));
    }

    @Override
    public void ApplyTransform() {
        center = super.transform.Transform(center);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("sphere: center:");
        sb.append(center);
        sb.append(" radius:");
        sb.append(radius);
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public String toOpenScad(String module_name, boolean hit) {
        StringBuilder sb = new StringBuilder();
        sb.append("module ");
        sb.append(module_name);
        sb.append("() {\n");
        if (hit) {
            sb.append("color(\"lightblue\"");
        } else {
            sb.append("color(\"blue\"");
        }
        sb.append("translate(");
        sb.append(center);
        sb.append(") ");
        sb.append("sphere([");
        sb.append("], r =");
        sb.append(radius);
        sb.append(");}\n");
        return sb.toString();
    }
}
