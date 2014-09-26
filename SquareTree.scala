package scalabrot

import scala.collection.mutable.ArrayBuffer
import Direction._
import MandelbrotColor.itersToEscape

class SquareTree(override val region: Square, override val stem: Option[RegionTree[Square]]) extends RegionTree(region, stem) {
        def divide = {
            leaf = false
            branches = Array(new SquareTree(region.quadrant(NE), Some(this)),
                             new SquareTree(region.quadrant(NW), Some(this)),
                             new SquareTree(region.quadrant(SE), Some(this)),
                             new SquareTree(region.quadrant(SW), Some(this)))
        }

        def getBranch(dir: Direction): RegionTree[Square] = {
            if (dir == NE) {
                branches(0)
            } else if (dir == NW) {
                branches(1)
            } else if (dir == SE) {
                branches(2)
            } else {
                branches(3)
            }
        }

        def cousins(clip: Square): ArrayBuffer[RegionTree[Square]] = {
            var ret: ArrayBuffer[RegionTree[Square]] = ArrayBuffer()
            if (region.neighbor(N).north < clip.north + clip.halfWidth) {
                ownerOfRegion(region.neighbor(N)).appendExtremesToArray(SE, SW, ret)
            }
            if (region.neighbor(E).east < clip.east + clip.halfWidth) {
                ownerOfRegion(region.neighbor(E)).appendExtremesToArray(SW, NW, ret)
            }
            if (region.neighbor(S).north > clip.north - clip.halfWidth) {
                ownerOfRegion(region.neighbor(S)).appendExtremesToArray(NW, NE, ret)
            }
            if (region.neighbor(W).east > clip.east - clip.halfWidth) {
                ownerOfRegion(region.neighbor(W)).appendExtremesToArray(NE, SE, ret)
            }
            ret
        }
    }