package julienrf.course.exercises

import org.specs2.{ScalaCheck, Specification}
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Arbitrary

object Vector3DSpec extends Specification with ScalaCheck { def is =

  "'plus' should" ^
    "add each coordinate" ! prop { (u: Vector3D, v: Vector3D) =>
        val w = u.plus(v)
        (w.x == u.x + v.x) && (w.y == u.y + v.y) && (w.z == u.z + v.z)
    }^
    "be commutative" ! prop { (u: Vector3D, v: Vector3D) =>
      val uv = u.plus(v)
      val vu = v.plus(u)
      uv.x == vu.x && uv.y == vu.y && uv.z == vu.z
    }

  implicit val arbitraryVector3D = Arbitrary {
    for {
      x <- arbitrary[Int]
      y <- arbitrary[Int]
      z <- arbitrary[Int]
    } yield new Vector3D(x, y, z)
  }

}
