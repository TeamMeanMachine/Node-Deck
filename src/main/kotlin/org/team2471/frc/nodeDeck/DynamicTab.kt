package org.team2471.frc.nodeDeck

import javafx.geometry.Pos
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.VBox
import javafx.stage.Screen

object DynamicTab: VBox(10.0) {
    val fieldImage = ImageView(Image("field-2023.png"))


    init {
        println("Dynamic Tab up and running")

        fieldImage.fitWidth = Screen.getPrimary().visualBounds.width * 0.8

        DynamicTab.alignment = Pos.TOP_CENTER
        DynamicTab.children.addAll(
            fieldImage
        )
    }
}