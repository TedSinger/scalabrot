package scalabrot

object Direction {
    case class Direction(val north: Int, val east: Int)

    val NE  = new Direction( 1, 1)
    val E   = new Direction( 0, 1)
    val SE  = new Direction(-1, 1)
    val S   = new Direction(-1, 0)
    val SW  = new Direction(-1,-1)
    val W   = new Direction( 0,-1)
    val NW  = new Direction( 1,-1)
    val N   = new Direction( 1, 0)
    val Stationary = new Direction(0,0)
}
