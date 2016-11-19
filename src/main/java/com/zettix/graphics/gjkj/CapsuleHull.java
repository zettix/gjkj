package com.zettix.graphics.gjkj;

/**
 * Capsule Hull
 * <p>
 * Inspired by Molly Rocket's mention of translation,
 * and the hull offered in Unity, the game devopment app,
 * the capsule hull is a sphere translated by some distance,
 * and the entire hull is the swept volume.
 * <p>
 * The simplicity of the support function makes this possible.
 * <p>
 * Created by sean on 11/15/16.
 */
public class CapsuleHull extends BaseHull implements Hull {

    protected Double r1, r2;  // radii
    protected V3 c1, c2;      // centers

    protected V3 from_c1_to_c2; // c2 - c1

    public CapsuleHull() {
        r1 = 1.0;
        r2 = 1.0;
        c1 = new V3(0.0, 0.0, 0.0);
        c2 = new V3(0.0, 0.0, 1.0);
        Init();
    }

    public Double getR1() {
        return r1;
    }

    public void setR1(Double r1) {
        this.r1 = r1;
    }

    public Double getR2() {
        return r2;
    }

    public void setR2(Double r2) {
        this.r2 = r2;
    }

    public V3 getC1() {
        return c1;
    }

    public void setC1(V3 c1) {
        this.c1 = c1;
    }

    public V3 getC2() {
        return c2;
    }

    public void setC2(V3 c2) {
        this.c2 = c2;
    }

    private void Init() {
        from_c1_to_c2 = vecstuff.add(c2, new V3(c1).ScalarMultiply(-1.0));
    }

    @Override
    public V3 Support(V3 direction) {
        V3 far = new V3(direction);
        // This is wrong.  If r1 != r2 the dot should be to the angle of the cone.  Cannot get direction w/o unitizing....
        if (vecstuff.dot(direction, from_c1_to_c2) > 0.0) {
            far.ScalarMultiply(r2 / Math.sqrt(vecstuff.dot(direction, direction)));
            return vecstuff.add(c2, far);
        }
        far.ScalarMultiply(r1 / Math.sqrt(vecstuff.dot(direction, direction)));
        return vecstuff.add(c1, far);
    }

    public V3 GetCorner(int index) {
        return Support(new V3(0.0, 0.0, 1.0));
    }

    @Override
    public void ApplyTransform() {
        c1 = super.transform.Transform(c1);
        c2 = super.transform.Transform(c2);
        Init();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("capsule: centers:[");
        sb.append(c1);
        sb.append(" ~ ");
        sb.append(c2);
        sb.append(" ~ ");
        sb.append(" r1:");
        sb.append(r1);
        sb.append(" r2:");
        sb.append(r2);
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public String toOpenScad(String module_name, boolean hit) {
        StringBuilder sb = new StringBuilder();
        sb.append("module ");
        sb.append(module_name);
        sb.append("() {\n");   // open module
        if (hit) {
            sb.append("color(\"red\"");
        } else {
            sb.append("color(\"green\"");
        }
        sb.append(")");
        sb.append("translate(");
        sb.append(c1);
        sb.append(") ");
        sb.append("hull() {");  // open hull
        sb.append("sphere(r =");
        sb.append(r1);
        sb.append(");\n");
        sb.append("translate(");
        sb.append(from_c1_to_c2);
        sb.append(") ");
        sb.append("sphere(r =");
        sb.append(r2);
        sb.append(");\n"); //sphere 2
        sb.append("};\n"); //hull
        sb.append("};\n");  // module
        sb.append(module_name);
        sb.append("();\n");
        return sb.toString();
    }
}
