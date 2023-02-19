package org.team2471.frc.nodeDeck

import javafx.scene.canvas.Canvas
import javafx.scene.image.Image
import javafx.scene.layout.GridPane
import javafx.scene.paint.Color

object AutoVisualizer: Canvas(300.0, 300.0) {
    val gc = AutoVisualizer.graphicsContext2D

    init {
        gc.drawImage(Image("node-icon.png"), 0.0, 0.0)
        gc.fill = Color.RED
        gc.fillRect(30.0, 30.0, 50.0, 50.0)

    }
}