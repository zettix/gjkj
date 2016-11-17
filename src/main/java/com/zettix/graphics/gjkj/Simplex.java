package com.zettix.graphics.gjkj;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by seanbrennan on 11/9/16.
 * 
 * Usage: Using a Hull
 *   progressively attempt to find if the origion is contained in Hull.
 *   by building a degree 3 simplex, tetrahedron, up from a point, line,
 *   and triangle.  
 *   1) Create Hull object
 *   2) Create Simplex with Hull.
 *   2) Call bool mySimplex.ContainsOrigin();
 */

public class Simplex {
    private static final V3 ZERO = new V3(0.0, 0.0, 0.0);
    private static final double CLOSE = 0.0000001;
    // // private static Logger LOG = Logger.getLogger(Simplex.class.getName());
    public boolean intersecting = false;
    protected ArrayList<V3> vertices = new ArrayList<>();
    protected HashSet<V3> seen = new HashSet<>();
    private Hull hull;

    public Simplex(Hull hin) {
        hull = hin;
        boolean ok = Init();
        if (!ok) {
            // LOG.warning("I could not even get 4 corners...");
        }
    }

    boolean SeenMe(V3 in) {
        boolean seen_it = seen.contains(in);
        if (seen_it) {
            // LOG.warning("Already Saw This One!!!!!!!!!!!!!!!!" + in + " SIMPLEX FAILURE ABORT ABORT ABORT!" + seen);
        }
        if (vecstuff.distanceSquared(in, ZERO) < CLOSE) {
            intersecting = true;
            // LOG.warning("A was actually close to the origin!");
            return false;
        }
        return seen.contains(in);
    }

    public boolean ContainsOrigin() {
        int tries = 0;

        boolean done = false;
        while (!done) {
            int simplex_size = vertices.size();
            // LOG.warning("ZZZZZZZ  CONTAINS ORIGIN OUTER LOOP **************** simplex size: " + simplex_size + "S:" + toString());
            boolean added = false;
            switch (simplex_size) {
                case 0:
                    added = Init();
                    break;
                case 1:
                    added = OnePlex();
                    break;
                case 2:
                    added = TwoPlex();
                    break;
                case 3:
                    added = ThreePlex();
                    break;
                case 4:
                    added = FourPlex();
                    break;
                default:
                    // LOG.warning("unknown simplex size: " + simplex_size);
                    break;
            }
            if (!added || tries++ > 20) {
                done = true;
            }
        }
        return intersecting;
    }

    /////  HEY!!!!  Give me a list of corners!!  So I can do the SeenMe() on object references.
    public boolean Init() {
        // LOG.warning("INIT HULL: " + hull);
        boolean added = false;
        // LOG.warning("NOTHING HAPPENED YET!");
        if (true) {
            V3 up = new V3(0.1f, -0.1f, 1.0);
            added = AddCheck(null, null, up);
            // LOG.warning("OMG HOW MANY SUPPORTS??");
        }
        // LOG.warning("Init simplex: " + this.toString());
        return added;
    }

    public boolean OnePlex() {
        // Makeing a line segment
        V3 a = vertices.get(0);
        V3 direction = new V3(a).ScalarMultiply(-1.0);
        return AddCheck(null, null, direction);
    }

    public boolean TwoPlex() {
        // Making a triangle here
        // Currently a 2-plex [B, A] in vertices.
        // possibilites are A, or above/below AB
        // ab = v[1] - v[0]
        V3 a = vertices.get(1);
        V3 a0 = new V3(a).ScalarMultiply(-1.0);  // 0 - a (from origin to a)
        V3 b = vertices.get(0);
        V3 ab = vecstuff.add(b, a0);  // b - a: ab (from a to b)
        Double ab_a0 = vecstuff.dot(ab, a0);
        // Test is ab dot a0, to see if 0 is on left of a.
        if (ab_a0 > 0.0) {
            V3 direction = vecstuff.cross(vecstuff.cross(ab, a0), ab);
            return AddCheck(a, ab, direction);
        }
        // a is closest so go in direction of 0.
        vertices.remove(0);
        return AddCheck(a, ab, a0);
    }

    public boolean OriginOnLineCheck(V3 start, V3 direction) {
        if (vecstuff.HitOrigin(start, direction)) {
            intersecting = true;
            return true;
        }
        return false;
    }

    public boolean AddCheck(V3 start, V3 ab, V3 support_direction) {
        if (start != null && ab != null && OriginOnLineCheck(start, ab)) {
            return false;
        }
        V3 c = hull.Support(support_direction);
        // LOG.warning("ZZZZZ Addcheck says direction: " + supp + " -> " + c + " corner]");
       if (SeenMe(c)) {
             return false;
        }
        seen.add(c);
        vertices.add(c);
        return true;
    }

    public boolean ThreePlex() {
        // The triangle case.  Something like 5 ifs.
        // Currently a 3-plex [C, B, A] in vertices.
        // possibilities require a diagram.
        /*
            // ............................................................
            // ....................ABCxAC....1.............................
            // .....C......................................................
            // ......##############........................................
            // ......#.............#############...........................
            // .......#.........................#############.A.....5......
            // .......#.......2............................###.............
            // ........#......^.........................###................
            // ........#.......ABC=.................###...................
            // ........#.......ABxAC..............###......................
            // .........#.....V................###.........................
            // .........#.....3..............##............................
            // ..........#................###..............................
            // ...........#............###.................................
            // ...........#.........###....................................
            // ...........#......###..........ABxABC.......................
            // ............#..###..........................................
            // ..............#...............4.............................
            // .............B..............................................
            // ............................................................
				*/
        V3 a = vertices.get(2);
        V3 b = vertices.get(1);
        V3 c = vertices.get(0);
        V3 direction;
        V3 a0 = new V3(a).ScalarMultiply(-1.0);
        V3 ab = vecstuff.add(b, a0);
        V3 ac = vecstuff.add(c, a0);
        V3 abc = vecstuff.cross(ab, ac);
        // abc points straight out of the triangle.
        if (vecstuff.dot(vecstuff.cross(abc, ac), a0) > 0.0) {
          if (vecstuff.dot(ac, a0) > 0.0) {
            // 1 closest to AC:
            // [A, C] : supp->ACxA0xAC
            vertices.remove(1);
            direction = vecstuff.cross(vecstuff.cross(ac, a0), ac);
              return AddCheck(a, ab, direction);
          } else {
            if (vecstuff.dot(ab, a0) > 0.0) {  // STAR FACTOR
               // 4  [A, B] supp->ABxA0xAB
               vertices.remove(0);
               direction = vecstuff.cross(vecstuff.cross(ab, a0), ab);
                return AddCheck(a, ab, direction);
            } else {
               // 5 closest to A.  bummer.
               // [A] : supp->A0
               vertices.remove(1);
               vertices.remove(0);
               direction = a0;
                return AddCheck(a, ab, direction);
            }
          }
        } else {
            if (vecstuff.dot(vecstuff.cross(ab, abc), a0) > 0.0) {
                if (vecstuff.dot(ab, a0) > 0.0) {  // STAR FACTOR
                    // 4  [A, B] supp->ABxA0xAB
                    vertices.remove(0);
                    direction = vecstuff.cross(vecstuff.cross(ab, a0), ab);
                    return AddCheck(a, ab, direction);
                } else {
                    // 5 closest to A.  bummer.
                    // [A] : supp->A0
                    vertices.remove(1);
                    vertices.remove(0);
                    direction = a0;
                    return AddCheck(a, ab, direction);
                }
            } else {
                if (vecstuff.dot(abc, a0) > 0.0) {
                    // 2 [A, B, C] supp -> abc
                    direction = abc;
                    return AddCheck(a, ab, direction);
                } else {
                    // 3 [A, C, B] supp-> -abc
                    vertices.clear();
                    vertices.add(b);
                    vertices.add(c);
                    vertices.add(a);
                    direction = abc.ScalarMultiply(-1.0);
                    return AddCheck(a, ab, direction);
                }
            }
        }
    }

    public boolean FourPlex() {
        // ..This is a tetrahedron.......D.............................
      // ..with corners A B C D .....#.#.............................
      // ..point A is above the....##..###...........................
      // ..screen. regions     ..##....#..##.........................
      // ..are numbers.        ###.....#....#........................
      // ....................##........#.....##......................
      // ..................##..........#.......#.....................
      // ................##............#........##...................
      // ...............##.............6.........##..................
      // .............##...............#...........##................
        // ..........###......ACD...(8)..#.....ADB.....##..............
        // ........##..........2.........#......3.......##.............
      // .......##.....................#................#............
      // .....##.............#########.A7##..............##..........
      // ...##.....######5###..............####............#.........
        // .#########.................ABC........##4#.........##.......
        // C##############.............1.............#####......##.....
      // ...............###############.................####....#....
      // ..............................##############.......####.#...
      // ............................................##############B.
      //  From what I can tell, and I could be wrong, there appear
      //  To be 3 exterior face regions, 3 exterior edge regions,
      //  an A region and a win (all interior) region.
      //  So we have just 3 dots: ABC, ABD and ACD.
      //  Why does Molly Rocket think there are 6 ifs?
        V3 a = vertices.get(3);
        V3 b = vertices.get(2);
        V3 c = vertices.get(1);
        V3 d = vertices.get(0);
        V3 direction;
        V3 a0 = new V3(a).ScalarMultiply(-1.0);
        V3 ab = vecstuff.add(b, a0);
        V3 ac = vecstuff.add(c, a0);
        V3 ad = vecstuff.add(d, a0);
        // All that to make these three:
        V3 abc = vecstuff.cross(ab, ac);
        V3 acd = vecstuff.cross(ac, ad);
        V3 adb = vecstuff.cross(ad, ab);
        boolean over_abc = vecstuff.dot(abc, a0) > 0.0;
        boolean over_acd = vecstuff.dot(acd, a0) > 0.0;
        boolean over_adb = vecstuff.dot(adb, a0) > 0.0;
        // ABC | ACD | ADB
        // LOG.warning("ZZZZ TET ideas: " + over_abc + over_acd + over_adb + " ]]]");
        // LOG.warning("ZZZZ TET a:" + a + " b:" + b + " c:" + c + " d:" + d);
        // LOG.warning("Simplex: " + vertices);
        if (over_abc) {
            if (over_acd) {
                if (over_adb) {  // 123
              // 7 miserable, closest to A.
              // [A] -> a0
               vertices.remove(2); // b
               vertices.remove(1); // c
               vertices.remove(0); // d
               direction = abc;
                    // LOG.warning("Misirable results.  Near A.");
                    return AddCheck(a, ab, direction);
            } else {        // 12x
                    // 4 over AC
                    // [ABCD]-> [AC] -> see above.
                    vertices.remove(2); // b
               vertices.remove(0); // d
                    direction = vecstuff.cross(vecstuff.cross(ac, a0), ac);
                    return AddCheck(a, ab, direction);
            }
          } else {
                if (over_adb) { //1x3
                    // 6 over AB
                    // [AB] -> see above/
                    vertices.remove(1); // c
               vertices.remove(0); // d
                    direction = vecstuff.cross(vecstuff.cross(ab, a0), ab);
                    return AddCheck(a, ab, direction);
            } else {        // 1xx
              // 1 over ABC.
              // [ABC] -> ABC
               vertices.remove(0); // d
               direction = abc;
                    return AddCheck(a, ab, direction);
            }
          }
        } else { // x??
            if (over_acd) { //x1?
                if (over_adb) {  // x23
              // 5 AD
              // [AD] -> see above
               vertices.remove(2); // b
               vertices.remove(1); // c
               direction = vecstuff.cross(vecstuff.cross(ad, a0), ad);
                    return AddCheck(a, ab, direction);
            } else {        // x2x
                    // 2 over ACD
                    // [ACD] -> ACD
                    vertices.remove(2); // b
                    direction = acd;
                    return AddCheck(a, ab, direction);
            }
          } else { // xx?
                if (over_adb) {  // xx3
                    // 3 ADB
                    // [ADB] -> ADB TODO(sean) flip triangle. adb clockwise?
                    vertices.clear();
                    vertices.add(b);
                    vertices.add(d);
                    vertices.add(a); // now adb
                    direction = adb;
                    return AddCheck(a, ab, direction);
                } else {        // xxx
                    // 8 Probably intersecting... A is closer to origin than B, C or D, in fact, A < B < C < D.
                    // Therefore in this region, we are intersecting.
                    intersecting = true;
                    // LOG.warning("INTERSECTING!");
                    return false;
            }
          }
       }  
    }

    public String toString() {
      StringBuilder sb = new StringBuilder();
      
      sb.append("Simplex[");
      sb.append(vertices.size());
      sb.append("] ");
      for (int i = 0; i < vertices.size(); i++) {
        sb.append("[");
        sb.append(vertices.get(i));
        sb.append("]");
      }
      sb.append("]>");
      return sb.toString();
   }

    public String toOpenScad(String module_name) {
        StringBuilder sb = new StringBuilder();
        sb.append("module ");
        sb.append(module_name);
        sb.append("() {\n");
        sb.append("color(\"yellow\") polyhedron( points=[");
        for (Integer i = 0; i < vertices.size(); i++) {
            V3 t = vertices.get(i);
            sb.append("[");
            sb.append(t.get(0));
            sb.append(",");
            sb.append(t.get(1));
            sb.append(",");
            sb.append(t.get(2));
            sb.append("]");
            if (i != 3)
                sb.append(",");
            sb.append("\n");
        }
        sb.append("], faces = [");
        int vsize = vertices.size();
        if (vsize == 3) {
            sb.append("[0,1,2]]);\n");
        } else if (vsize == 4) {
            sb.append("[1,2,3],[0,2,3],[0,1,3],[0,1,2]]);\n");
        } else {
            sb.append("]);\n");
        }
        sb.append("}\n");
        return sb.toString();
    }

}
