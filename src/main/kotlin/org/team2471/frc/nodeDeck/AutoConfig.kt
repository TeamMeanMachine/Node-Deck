package org.team2471.frc.nodeDeck

import javafx.scene.layout.GridPane

object AutoConfig: GridPane() {
    init {
        println("AutoConfig says hi!")

        AutoConfig.style = "-fx-background-color: #f0f0f0"
    }
}
//todo: L/R, grid of scoring (type, T/M/B), # of pieces, charge? y/n