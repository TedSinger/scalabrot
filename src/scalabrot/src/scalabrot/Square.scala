package scalabrot

import Direction._
import java.awt.Graphics2D

class Square(override val north: Double,
                 override val east: Double, 
                 val halfWidth: Double) extends Region(north, east, halfWidth) {
        def contains(reg: Region): Boolean = {
            (north - halfWidth <= reg.north) & 
             (reg.north < halfWidth + north) & 
              (east - halfWidth <= reg.east) & 
               (reg.east < halfWidth + east)
        }

        def quadrantContaining(reg: Region): Direction = {
            val northDir = reg.north.compare(north)
            val eastDir = reg.east.compare(east)
            new Direction(northDir, eastDir) 
        }

        def quadrant(dir: Direction): Square = {
            val newHalfWidth = halfWidth / 2
            new Square(north + newHalfWidth * dir.north,
                       east  + newHalfWidth * dir.east,
                       newHalfWidth)
        }

        def neighbor(dir: Direction): Square = {
            new Square(north + halfWidth * dir.north * 2,
                       east  + halfWidth * dir.east * 2,
                       halfWidth)
        }

        override def toString(): String = {
            val eastPart = "(%f" format east
            val northPart = ", %f): " format north
            val widthPart = "%f" format halfWidth
            eastPart ++ northPart ++ widthPart
        }

        def paint(g: Graphics2D, northCenter: Double, eastCenter: Double, resolution: Int, totalWidth: Double) {
            val width = (halfWidth * 2 * resolution / totalWidth).toInt
            val x = (east - eastCenter + totalWidth / 2.0 - halfWidth) * resolution / totalWidth
            val y = (north - northCenter + totalWidth / 2.0 - halfWidth) * resolution / totalWidth
            g.fillRect( x.toInt,
                        y.toInt,
                         width,
                         width)
            g.drawRect( x.toInt,
                        y.toInt,
                         width,
                         width)
        }
    }