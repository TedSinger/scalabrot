package scalabrot

import java.awt.Color

object MandelbrotColor {
    def itersToEscape(startX: Double, startY: Double): Int = {
        val maxIters = 20000
        var currentX = startX
        var currentY = startY
        var newX = currentX
        var newY = currentY
        var currentMagnitude = currentX * currentX + currentY * currentY
        var iterCount = 0

        while (iterCount < maxIters & currentMagnitude <= 4) {
            iterCount = iterCount + 1
            newX = currentX * currentX - currentY * currentY - startX
            newY = 2 * currentX * currentY - startY
            currentX = newX
            currentY = newY
            currentMagnitude = currentX * currentX + currentY * currentY
        }

        if (currentMagnitude > 4) {
            iterCount
        } else {
            -1
        }
    }

    def colorForIters(iters: Int): Color = {
    	if (iters == -1) {
    		Color.black
    	} else {
	        var tempIters: Double = iters * 5
	        while (tempIters > 255*6) {
	            tempIters = math.pow(tempIters - 255*6 + 1, 0.65)
	        }
	        val mappedIters = tempIters.toInt

	        if (mappedIters <= 255) {
	            new Color(0, mappedIters, 255)
	        } else if (mappedIters <= 510) {
	            new Color(0, 255, 510 - mappedIters)
	        } else if (mappedIters <= 765) {
	            new Color(mappedIters - 510, 255, 0)
	        } else if (mappedIters <= 1020) {
	            new Color(255, 1020 - mappedIters, 0)
	        } else if (mappedIters <= 1275) {
	            new Color(255, 0, mappedIters - 1020)
	        } else {
	            new Color(255*6 - mappedIters, 0, 255)
	        }
	    }
    }
    
    val DOUBLE_PRECISION_LIMIT = 2.78E-17
    def equalWithinTolerance(left: Double, right: Double): Boolean = {
        (-DOUBLE_PRECISION_LIMIT < left - right) & (left - right < DOUBLE_PRECISION_LIMIT)
    }
    
}
