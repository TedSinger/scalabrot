package scalabrot

import Direction._
import java.awt.Graphics2D
import MandelbrotColor.equalWithinTolerance

abstract class Region(val north: Double, val east: Double, val size: Double) {
    def contains(reg: Region): Boolean
    def quadrantContaining(reg: Region): Direction
    def quadrant(dir: Direction): Region
    def neighbor(dir: Direction): Region
    def colocated(reg: Region): Boolean = {
        val northOk = equalWithinTolerance(north, reg.north)
        val eastOk = equalWithinTolerance(east, reg.east)
        val sizeOk = equalWithinTolerance(size, reg.size)
        northOk & eastOk & sizeOk
    }
    def paint(g: Graphics2D, northCenter: Double, eastCenter: Double, resolution: Int, totalWidth: Double)
}