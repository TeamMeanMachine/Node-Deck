package org.team2471.frc.nodeDeck

import javafx.geometry.Pos
import javafx.scene.layout.GridPane

object ColorOutline : GridPane() {
    init {
        println("ColorOutline says hi!")

        ColorOutline.alignment = Pos.CENTER
        ColorOutline.add(SectionSelector, 0, 0)
        ColorOutline.add(NodeSelector, 0, 1)
        ColorOutline.add(TabDeck, 1, 1)
    }
    fun checkAlliance(red: Boolean = Client.isRed) {
        if (!Client.networkTableInstance.isConnected) {
            ColorOutline.style = "-fx-background-color: #a8a8a8; -fx-border-color: #ffff00; -fx-border-width: 10 10 10 10"
        } else {
            if (red) {
                ColorOutline.style = "-fx-background-color: #a8a8a8; -fx-border-color: #ff0000; -fx-border-width: 10 10 10 10"
            }
            if (!red) {
                ColorOutline.style = "-fx-background-color: #a8a8a8; -fx-border-color: #0000ff; -fx-border-width: 10 10 10 10"
            }
            updateButtons()
        }
    }
    fun updateButtons() {
        SectionSelector.selectedSectionButton.fire()
        NodeSelector.selectedNodeButton.fire()
    }
}