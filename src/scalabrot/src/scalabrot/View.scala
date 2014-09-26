package scalabrot

import Direction._
import java.awt.Graphics2D
import swing.Panel
import scala.collection.mutable.{Queue, ArrayBuffer}
import MandelbrotColor.colorForIters

object View {

    class SquareView(val resolution: Int, val field: Square) extends Panel {
        val minWidth = 2 * field.halfWidth / resolution
        var thisRoot = new SquareTree(field, None)
        val todoQueue: Queue[Traversable[RegionTree[Square]]] = new Queue()
        var doneTrees: ArrayBuffer[RegionTree[Square]] = new ArrayBuffer()

        def init {
            thisRoot = new SquareTree(field, None)
            doneTrees = new ArrayBuffer()
            thisRoot.divide
            for (b <- thisRoot.branches) {
                b.divide
                for (c <- b.branches) {
                    c.divide
                    todoQueue += c.branches
                }
            }
        }

        def solve = {
            while (!todoQueue.isEmpty) {
                var group = todoQueue.dequeue()
                divideIfNeeded(group)
            }
        }

        // If a leaf needs to be subdivided, then all of its cousins need to be reexamined
        def divideIfNeeded(group: Traversable[RegionTree[Square]]) {
            for (tree <- group) {
                if (tree.leaf & (tree.region.halfWidth.toDouble * 2 > minWidth)) {
                    val cousins = tree.cousins(field)
                    if (cousins.exists(_.iters != tree.iters)) {
                        tree.divide

                        todoQueue += cousins
                        todoQueue += tree.branches
                    } else {
                        doneTrees += tree
                    }
                } else {
                    doneTrees += tree
                }
            }
        }

        override def paint(g: Graphics2D) {
            for (tree <- doneTrees) {
                if (tree.leaf) {
                    g.setColor(colorForIters(tree.iters))
                    tree.region.paint(g, field.north, field.east, resolution, 2 * field.halfWidth)
                }
            }
        }
    }
}

