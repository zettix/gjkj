# GJK Implementation in Java

## Status: ALPHA (Expect bugs)

GJK is the Gilbert Johnson Keerthi Collision Detection Algorithm.
In short, a clever decomposition of the dot product to see if two
convex hulls, when subtracted in a Minkowski Sum, contain the origin.

## Top Bug:
This highlights one numerical issue with GJK as I understand it.  When building the simplex, the base triangle contains the origin.  The dot product of the last triangle vertex to the origin and the normal to the triangle is zero, they being 90 degrees apart.  Numerical instability can mean your next direction is essentially random, up or down from the base triangle.

To be honest I'm working in the dark here.  If you must detect if the triangle contains the origin, and by going directly toward the origin you more or less strive for that outcome, you have to test for this case.  Here is a failure.  The simplex clearly is a triangle containing the origin, the simplex growth is terminated, and no collide is issued, hence green.

There are a few things I dislike: my differently scaled sphere capsule, icecream in the animation, is wrong because I need the normal to the cone to do it right, and I need unit vectors so I need a square root and a divide.  If the spheres are the same size you do not need to square root or divide.  In fact another bug had to test if the simplex, as a line segment, contained the origin.  This is already in the code and I'm doubting my need to do these checks, but the bugs are right there, here it is.

![edge on edge test fail](https://github.com/zettix/gjkj/blob/master/resources/edge_bug_gjkj.png)

Here is how I test outside of an application.  I integrate it with OpenScad and observe what I consider typical pathological conditions that would make this algorithm shine.  To make it robust, it must be complex.  Sphere to sphere testings looks so attractive for its speed and simplicity, one would be nuts not to use it first here.

Here is the test anim as of this commit date, showing how the bug was discovered.
![edge on edge test fail_animation](https://github.com/zettix/gjkj/blob/master/resources/edge-detection-bug.gif)

## Usage:

When you feel like it's time to check if two convex hulls collide, do this:
```
Hull hullA = new BoxHull(x_dimension, y_dimension, z_dimension);
M4 transform = new M4().Identity();
transform.Move(x_delta, y_delta, z_delta);
transform.Rotate(x_axis, y_axis, z_axis);
transform.Scale(x_scale, y_scale, z_scale);
hullA.TransformObjectSpace(transform);
# ... or just provide a transformation matrix, 4x4.
# Your parent object should have some idea of what hull it wants,
# and probably has a transformation matrix you can use.  When ready, transform to world:
M4 world_transform = new M4().Identity().Move(x, y, z);
hullA.TramnsformWorldSpace(world_transform); # Applies transform to hull, to move it into position.
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
   Hull is the interface, you override Support() and anything else in BaseHull() which has convenient defaults.

## Testing Environment:
  Uses TestNG framework. Coverage stats from IntelliJ:
  Classes: 100%
  Methods: 83%
  Lines: 91%
  
  OpenScad to visualize some results.
  In this one, the Icecream Cone test, we see two hulls, cones with hemispherical caps, go from colliding to free of each other.  When colliding, they are red, and the simplex, in yellow, contains the origin.  As they separate, the simplex loses hold of the origin and freaks out a bit trying to get it.  When green, the hulls are not colliding.
  
  ![animated collision test](https://github.com/zettix/gjkj/blob/master/resources/collision_test_icecream.gif)

### Author: Sean Brennan, 2016
