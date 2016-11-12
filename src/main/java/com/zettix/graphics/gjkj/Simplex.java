package com.zettix.graphics.gjkj;

import java.util.ArrayList;
import java.util.HashSet;
/**
 * Created by seanbrennan on 11/9/16.
 * 
 * Usage: Using a Hull
 *   progressively attempt to find if the origion is contained in Hull.
 *   by building a degree 4 simplex, tetrahedron, up from a point, line,
 *   and triangle.  
 *   1) Create Hull object
 *   2) Create Simplex with Hull.
 *   2) Call bool mySimplex.Hit();
 */

public class Simplex {
    public ArrayList<V3> vertices = new ArrayList<>();
    boolean ContainsOrigin(){
        return false;
    }
    public boolean intersecting = false;

    private Hull hull;

    private HashSet<V3> seen = new HashSet<>();

    public Simplex(Hull hin) {hull = hin;};

    boolean SeenMe(V3 in) {
        return seen.contains(in);
    }

    /////  HEY!!!!  Give me a list of corners!!
    public void Init(final V3 in) {
        vertices.add(new V3(in));
    }

    public boolean OnePlex() {
        // Makeing a line segment
        V3 direction = new V3(vertices.get(0)).ScalarMultiply(-1.0f);
        return AddCheck(direction);
    }

    public boolean TwoPlex() {
        // Making a triangle here
        // Currently a 2-plex [B, A] in vertices.
        // possibilites are A, or above/below AB
        // ab = v[1] - v[0]
        V3 a0 = new V3(vertices.get(1)).ScalarMultiply(-1.0f);  // 0 - a (from origin to a)
        V3 b = vertices.get(0);
        V3 ab = vecstuff.add(b, a0);  // b - a: ab (from a to b)
        Float ab_a0 = vecstuff.dot(ab, a0);
        // Test is ab dot a0, to see if 0 is on left of a.
        if (ab_a0 > 0.0f) {
            V3 direction = vecstuff.cross(vecstuff.cross(ab, a0), ab);
            return AddCheck(direction);
        }
        // a is closest so go in direction of 0.
        vertices.remove(0);
        return AddCheck(a0);
    }

    public boolean AddCheck(V3 supp) {
       V3 c = hull.Support(supp);
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
        V3 a0 = new V3(a).ScalarMultiply(-1.0f);
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
            return AddCheck(direction);
          } else {
            if (vecstuff.dot(ab, a0) > 0.0) {  // STAR FACTOR
               // 4  [A, B] supp->ABxA0xAB
               vertices.remove(0);
               direction = vecstuff.cross(vecstuff.cross(ab, a0), ab);
               return AddCheck(direction);
            } else {
               // 5 closest to A.  bummer.
               // [A] : supp->A0
               vertices.remove(1);
               vertices.remove(0);
               direction = a0;
               return AddCheck(direction);
            }
          }
        } else {
            if (vecstuff.dot(vecstuff.cross(ab, abc), a0) > 0.0) {
                if (vecstuff.dot(ab, a0) > 0.0) {  // STAR FACTOR
                    // 4  [A, B] supp->ABxA0xAB
                    vertices.remove(0);
                    direction = vecstuff.cross(vecstuff.cross(ab, a0), ab);
                    return AddCheck(direction);
                } else {
                    // 5 closest to A.  bummer.
                    // [A] : supp->A0
                    vertices.remove(1);
                    vertices.remove(0);
                    direction = a0;
                    return AddCheck(direction);
                }
            } else {
                if (vecstuff.dot(abc, a0) > 0.0) {
                    // 2 [A, B, C] supp -> abc
                    direction = abc;
                    return AddCheck(direction);
                } else {
                    // 3 [A, C, B] supp-> -abc
                    direction = abc.ScalarMultiply(-1.0f);
                    return AddCheck(direction);
                }
            }
        }
    }

    public boolean FourPlex() {
      // ..This is a tetrahedron.......C.............................
      // ..with corners A B C D .....#.#.............................
      // ..point A is above the....##..###...........................
      // ..screen. regions     ..##....#..##.........................
      // ..are numbers.        ###.....#....#........................
      // ....................##........#.....##......................
      // ..................##..........#.......#.....................
      // ................##............#........##...................
      // ...............##.............6.........##..................
      // .............##...............#...........##................
      // ..........###......ACD...(8)..#.....ABC.....##..............
      // ........##..........3.........#......1.......##.............
      // .......##.....................#................#............
      // .....##.............#########.A7##..............##..........
      // ...##.....######5###..............####............#.........
      // .#########.................ABD........##4#.........##.......
      // D##############.............2.............#####......##.....
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
        V3 a0 = new V3(a).ScalarMultiply(-1.0f);
        V3 b0 = new V3(b).ScalarMultiply(-1.0f);
        V3 c0 = new V3(c).ScalarMultiply(-1.0f);
        V3 ab = vecstuff.add(b, a0);
        V3 ac = vecstuff.add(c, a0);
        V3 ad = vecstuff.add(d, a0);
        V3 bc = vecstuff.add(c, b0);
        V3 bd = vecstuff.add(d, b0);
        V3 cd = vecstuff.add(d, c0);
        // All that to make these three:
        V3 abc = vecstuff.cross(ab, bc);
        V3 abd = vecstuff.cross(ab, bd);
        V3 acd = vecstuff.cross(ac, cd);
        boolean over_abc = vecstuff.dot(abc, a0) > 0.0;
        boolean over_abd = vecstuff.dot(abd, a0) > 0.0;
        boolean over_acd = vecstuff.dot(acd, a0) > 0.0;
        if (over_abc) {
          if (over_abd) {
            if (over_acd) {  // 123
              // 7 miserable, closest to A.
              // [A] -> a0
               vertices.remove(2); // b
               vertices.remove(1); // c
               vertices.remove(0); // d
               direction = abc;
               return AddCheck(direction);
            } else {        // 12x
              // 4 over AB.
              // [ABCD]-> [AB] -> see above.
               vertices.remove(1); // c
               vertices.remove(0); // d
               direction = vecstuff.cross(vecstuff.cross(ab, a0), ab);
               return AddCheck(direction);
            }
          } else {
            if (over_acd) { //1x3
              // 6 over AC
              // [AC] -> see above/
               vertices.remove(2); // b
               vertices.remove(0); // d
               direction = vecstuff.cross(vecstuff.cross(ac, a0), ac);
               return AddCheck(direction);
            } else {        // 1xx
              // 1 over ABC.
              // [ABC] -> ABC
               vertices.remove(0); // d
               direction = abc;
               return AddCheck(direction);
            }
          }
        } else { // x??
          if (over_abd) { //x1?
            if (over_acd) {  // x23
              // 5 AD
              // [AD] -> see above
               vertices.remove(2); // b
               vertices.remove(1); // c
               direction = vecstuff.cross(vecstuff.cross(ad, a0), ad);
               return AddCheck(direction);
            } else {        // x2x
              // 2 over ABD
              // [ABD] -> ABD
               vertices.remove(1); // c
               direction = abd;
               return AddCheck(direction);
            }
          } else { // xx?
            if (over_acd) {  // xx1
              // 3 ACD
              // [ACD] -> ACD
               vertices.remove(2); // b
               direction = acd;
               return AddCheck(direction);
            } else {        // xxx
              // 8 Probably intersecting...
              intersecting = true;
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

}

