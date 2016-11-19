# GJK Implementation in Java

## Status: ALPHA (Expect bugs)

GJK is the Gilbert Johnson Keerthi Collision Detection Algorithm.
In short, a clever decomposition of the dot product to see if two
convex hulls, when subtracted in Minkowski Sum, contain the origin.

## Usage:

When you feel like it's time to check if two convex hulls collide, do this:
```
Hull hullA = new BoxHull(x_dimension, y_dimension, z_dimension);
M4 transform = new M4().Identity();
transform.Move(x_delta, y_delta, z_delta);
transform.Rotate(x_axis, y_axis, z_axis);
transform.Scale(x_scale, y_scale, z_scale);
# ... or just provide a transformation matrix, 4x4.
# Your parent object should have some idea of what hull it wants,
# and probably has a transformation matrix you can use.
hullA.UpdateTransform(transform);
hullA.ApplyTransform();   # Applies transform to hull, to move it into position.
```

Do the same for hullB

Now you have two hulls, hullA and hullB

`boolean result = new GJKIntersect(hullA, hullB).Intersect();`

And true means they are colliding.
False means they are not.

Actual distance is a harder problem.

## Utility Libraries:
  Vector Math:
    V3: a 3-Vector of doubles.  Has ScalarMultiply()
    M4: a 4x4 matrix for transforming stuff.  Move, Rotate, Scale, Identity, Multiply, and Transform V3's.
        (a V3 is promoted to (x, y, z, 1.0) internally)
    vecstuff: dot product, cross product, distance squared.

## Hulls:
   GJK has great flexibility with Hulls.  More will be added, but box and tetrahedron work.
   Hull is the interface, you override Support() and anything else in BaseHull() which has convenent defaults.

## Testing Environment:
  Uses TestNG framework.  OpenScad to visualize some results.
  In this one, the Icecream Cone test, we see two hulls, cones with hemispherical caps, go from colliding to free of each other.  When colliding, they are red, and the simplex, in yellow, contains the origin.  As they separate, the simplex loses hold of the origin and freaks out a bit trying to get it.  When green, the hulls are not colliding.
  
  ![animated collision test](https://github.com/zettix/gjkj/blob/master/resources/collision_test_icecream.gif)

### Author: Sean Brennan, 2016
