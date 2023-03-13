package org.team2471.frc.nodeDeck

import javafx.scene.control.Button
import javafx.scene.layout.GridPane

object TestTab: GridPane() {

    val testButton = Button("test")

    init {

        testButton.setOnAction {

        }

        testButton.setOnAction {
//            NTClient.connectionJob?.cancel()
//            NTClient.connect()
        }

        addRow(1, testButton)
    }

}