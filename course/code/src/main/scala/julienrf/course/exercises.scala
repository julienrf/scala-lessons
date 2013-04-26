package julienrf.course

package object exercises {

  class Vector3D(val x: Int, val y: Int, val z: Int) {

    def crossProduct(that: Vector3D) =
      new Vector3D(
        this.y * that.z - this.z * that.y,
        this.z * that.x - this.x * that.z,
        this.x * that.y - this.y * that.x
      )

    def plus(that: Vector3D): Vector3D = ???

  }

  val u = new Vector3D(1, 0, 0)
  val v = new Vector3D(0, 1, 0)
  val w = u.crossProduct(v)

}
