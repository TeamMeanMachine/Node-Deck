package org.team2471.frc.nodeDeck

import javafx.geometry.Pos
import javafx.scene.layout.GridPane

object ColorOutline : GridPane() {
    init {
        println("ColorOutline says hi!")

        ColorOutline.alignment = Pos.CENTER
        ColorOutline.add(SectionSelector, 0, 0)
        ColorOutline.add(NodeSelector, 0, 1)
        ColorOutline.add(InformationPanel, 1, 1)
    }
    fun checkAlliance(red: Boolean = NodeDeck.isRedAlliance) {
        if (red) {
            ColorOutline.style = "-fx-background-color: #a8a8a8; -fx-border-color: #ff0000; -fx-border-width: 10 10 10 10"
        } else {
            ColorOutline.style = "-fx-background-color: #a8a8a8; -fx-border-color: #0000ff; -fx-border-width: 10 10 10 10"
        }

    }
}