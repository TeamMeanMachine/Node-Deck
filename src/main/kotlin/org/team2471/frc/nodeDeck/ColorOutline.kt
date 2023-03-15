package org.team2471.frc.nodeDeck

import javafx.geometry.Pos
import javafx.scene.layout.VBox

object ColorOutline : VBox() {
    val borderWidth: String = " 13 13 13 13" // format in " ## ## ## ##" Top Right Bottom Left
    val segmentLength: String = "50"
    init {
        println("ColorOutline says hi!")

        ColorOutline.alignment = Pos.TOP_CENTER
        ColorOutline.children.addAll(TabDeck)
    }
    fun checkAlliance(red: Boolean = NTClient.isRed) {
        if (!NTClient.networkTableInstance.isConnected) {
            ColorOutline.style = "-fx-background-color: #a8a8a8; -fx-border-color: #ffff00; -fx-border-width: $borderWidth"
        } else {
            var color = "blue"
            if (red)
                 color = "red"

            ColorOutline.style = "" +
                    "-fx-border-color: yellow, $color ;" +
                    " -fx-background-color: #a8a8a8;" +
                    " -fx-border-width: $borderWidth;"
            if (NTClient.selectedAuto != "NodeDeck") {
                ColorOutline.style = ColorOutline.style + "-fx-border-style: segments($segmentLength, $segmentLength), segments($segmentLength, $segmentLength) phase $segmentLength;"
                TabDeck.autoTab.style = TabDeck.autoTab.style + "; -fx-background-color: red; -fx-text-color: white"
            } else {
                TabDeck.autoTab.style = "-fx-font-size: ${TabDeck.fontSize} px"
            }
        }
    }
}