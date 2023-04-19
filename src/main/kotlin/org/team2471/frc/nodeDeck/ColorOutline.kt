package org.team2471.frc.nodeDeck

import javafx.geometry.Pos
import javafx.scene.layout.VBox
import org.team2471.frc.nodeDeck.NTClient.demoMode

object ColorOutline : VBox() {
    val borderWidth: String = " 13 13 13 13" // format in " ## ## ## ##" Top Right Bottom Left
    val segmentLength: String = "50"
    init {
        println("ColorOutline says hi!")

        ColorOutline.alignment = Pos.TOP_CENTER
        ColorOutline.children.addAll(TabDeck)
    }
    fun checkAlliance() {
        if (!NTClient.networkTableInstance.isConnected) {
            ColorOutline.style = "-fx-background-color: #a8a8a8; -fx-border-color: #ffff00; -fx-border-width: $borderWidth"
        } else {
            val color = if (NTClient.isRed) "red" else "blue"

            if (demoMode) {
                ColorOutline.style = "-fx-background-color: #a8a8a8; -fx-border-color: #00c434; -fx-border-width: $borderWidth"
                TabDeck.autoTab.style = "-fx-font-size: ${TabDeck.fontSize} px"
            } else {
                if (NTClient.selectedAuto != "NodeDeck") {
                    ColorOutline.style = "-fx-border-color: yellow, $color ; -fx-background-color: #a8a8a8; -fx-border-width: $borderWidth;-fx-border-style: segments($segmentLength, $segmentLength), segments($segmentLength, $segmentLength) phase $segmentLength;"
                    TabDeck.autoTab.style = "-fx-font-size: ${TabDeck.fontSize} px; -fx-background-color: red; -fx-text-color: white"

                } else {
                    TabDeck.autoTab.style = "-fx-font-size: ${TabDeck.fontSize} px"
                    ColorOutline.style = "" +
                            "-fx-border-color: yellow, $color ;" +
                            " -fx-background-color: #a8a8a8;" +
                            " -fx-border-width: $borderWidth;"
                }
            }
        }
        AutoConfig.updateStartingButtonsOrder()
    }
}