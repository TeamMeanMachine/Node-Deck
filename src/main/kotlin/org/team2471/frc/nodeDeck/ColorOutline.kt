package org.team2471.frc.nodeDeck

import javafx.geometry.Pos
import javafx.scene.layout.VBox

object ColorOutline : VBox() {
    val borderWidth: String = " 13 13 13 13" // format in " ## ## ## ##" Top Right Bottom Left
    init {
        println("ColorOutline says hi!")

        ColorOutline.alignment = Pos.TOP_CENTER
        ColorOutline.children.addAll(TabDeck)
    }
    fun checkAlliance(red: Boolean = NTClient.isRed) {
        if (!NTClient.networkTableInstance.isConnected) {
            ColorOutline.style = "-fx-background-color: #a8a8a8; -fx-border-color: #ffff00; -fx-border-width: $borderWidth"
        } else {
            if (red) {
                ColorOutline.style = "-fx-background-color: #a8a8a8; -fx-border-color: #ff0000; -fx-border-width: $borderWidth"
            }
            if (!red) {
                ColorOutline.style = "-fx-background-color: #a8a8a8; -fx-border-color: #0000ff; -fx-border-width: $borderWidth"
            }
        }
    }
    fun updateButtons() {
        CompactFormat.selectedGridButton.fire()
        CompactFormat.selectedNodeButton.fire()
    }
}