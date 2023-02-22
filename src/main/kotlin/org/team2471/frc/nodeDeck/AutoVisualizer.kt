package org.team2471.frc.nodeDeck

import javafx.scene.canvas.Canvas
import javafx.scene.image.Image

object AutoVisualizer: Canvas(269.0, 225.0) /* nice ;) */{
    val gc = AutoVisualizer.graphicsContext2D

    init {
        gc.drawImage(Image("node-icon.png"), 0.0, 0.0)
    }
    fun updateCanvas() {
        println("UPDATING")
        val allPieces = listOf(AutoConfig.piece1.nodeValue, AutoConfig.piece2.nodeValue, AutoConfig.piece3.nodeValue, AutoConfig.piece4.nodeValue, AutoConfig.piece5.nodeValue)

        gc.drawImage(Image("node-icon.png"), 0.0, 0.0)
        for (n in allPieces) {
//            println(n)
            var p = n
            if (p != null) {
                while (p > 9) {
                    p -= 9
                }
                if (p <= 2) {
                    gc.fillRect(20.0, (90.0 * p) + 20 - if (p == 2) 22.0  else 0.0, 42.0, 42.0)
                } else if (p in 3..5) {
                    gc.fillRect(110.0, 90.0 * (p - 3) + 20 - if (p == 5) 22.0  else 0.0, 42.0, 42.0)
                } else {
                    gc.fillRect(200.0, 90.0 * (p - 6) + 20 - if (p == 8) 22.0  else 0.0, 42.0, 42.0)
                }
            }
        }
    }
}