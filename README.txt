GJK Implementation in Java

Status: INCOMPLETE

GJK is the Gilbert Johnson Keerthi Collision Detection Algorithm.
In short, a clever decomposition of the dot product to see if two
convex hulls, when combined in Minkowski Sum, contain the origin.

Usage:

When you feel like it's time to check if two convex hulls collide, do this:

Hull hullA = new BoxHull(x_dimension, y_dimension, z_dimension);
M4 transform = new M4().Identity();
transform.Move(x_delta, y_delta, z_delta);
transform.Rotate(x_axis, y_axis, z_axis);
transform.Scale(x_scale, y_scale, z_scale);
... or just provide a transformation matrix, 4x4.
# Your parent object should have some idea of what hull it wants,
# and probably has a transformation matrix you can use.
hullA.transform = transform;
hullA.ApplyTransform();   # Applies transform to hull, to move it into position.

Do the same for hullB

Now you have two hulls, hullA and hullB

boolean GJKIntersect(hullA, hullB).Intersect();

And true means they are colliding.
False means they are not.

Actual distance is a harder problem.
