package scalabrot

import scala.collection.mutable.ArrayBuffer
import Direction._
import MandelbrotColor.itersToEscape

abstract class RegionTree[regionType<:Region](val region: regionType, val stem: Option[RegionTree[regionType]]) {
        var leaf = true
        var branches: Array[RegionTree[regionType]] = Array()
        val iters = itersToEscape(region.east, region.north)

        def ownerOfRegion(reg: regionType): RegionTree[regionType] = {
            if (region.contains(reg)) {
                descendUntilLeaf(reg)
            } else if (!stem.isEmpty) {
                stem.get.ownerOfRegion(reg)
            } else {
                println("Warning: Region outside the top-level region", reg)
                this
            }
        }

        override def toString(): String = {
            val eastPart = "(%1.4f" format region.east.toDouble
            val northPart = ", %1.4f): " format region.north.toDouble
            val itersPart = "%d" format iters
            eastPart ++ northPart ++ itersPart
        }

        def descendUntilLeaf(reg: regionType): RegionTree[regionType] = {
            if (region.contains(reg)) {
                if (leaf | region.colocated(reg)) {
                    this
                } else {
                    val nextDir = region.quadrantContaining(reg)
                    getBranch(nextDir).descendUntilLeaf(reg)
                }
            } else {
                println("Warning: Floating-Point errors led to the wrong region for this region", region, reg) 
                this
            }
        }

        def appendExtremesToArray(left: Direction, right: Direction, target: ArrayBuffer[RegionTree[regionType]]) {
            if (leaf) {
                target += this
            } else {
                getBranch(left ).appendExtremesToArray(left, right, target)
                getBranch(right).appendExtremesToArray(left, right, target)
            }
        }

        def divide
        def getBranch(dir: Direction): RegionTree[regionType]
        def cousins(clip: Square): ArrayBuffer[RegionTree[regionType]]

        def size: Int = {
            if (leaf) {
                1
            } else {
                branches.map(_.size).sum
            }
        }
    }