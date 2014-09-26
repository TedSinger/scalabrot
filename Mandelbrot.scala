package scalabrot

import swing._
import View.SquareView
import swing.event._
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.FileOutputStream

object Mandelbrot extends SimpleSwingApplication {
    val resolution = 1024
    val northCenter: Double = 0.058
    val eastCenter: Double = 0.7559
    val totalWidth: Double = 1/256.0 // 1
    val field = new Square(northCenter, eastCenter, totalWidth / 2.0)
    val view = new SquareView(resolution, field)
    val startTime = System.nanoTime()

    // Speed test
    // for (i <- 1 until 10) {
    //     view.init
    //     view.solve
    //     println(((System.nanoTime() - startTime) / 1000000000. / i, i))
    // }
    view.init
    view.solve
    println(((System.nanoTime() - startTime) / 1000000000.0, view.thisRoot.size))
    write(view)

    def write(v: SquareView) {
        val img = new BufferedImage(resolution, resolution, BufferedImage.TYPE_INT_RGB)
        val h = img.createGraphics()
        val f = new FileOutputStream("/home/ted/Desktop/saved.png")
        v.paint(h)
        ImageIO.write(img, "png", f)
        f.close()
    }

    def top = new MainFrame {
        contents = view
        view.listenTo(view.mouse.wheel)
        view.reactions += {
            case MouseWheelMoved(s, p, m, r) => println("wheel moved", p)
        }
        size = new Dimension(resolution, resolution)
    }
}
