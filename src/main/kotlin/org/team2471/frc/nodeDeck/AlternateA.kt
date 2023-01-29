package org.team2471.frc.nodeDeck

import javafx.application.Platform
import javafx.scene.control.Button
import javafx.scene.layout.GridPane

object AlternateA: GridPane() {

    val exitButton = Button("Exit App")

    init {

        // The following doesn't work. Button gives a IllegalStateException
        // and timer keeps running. Needs further study. EK 1/28/23.
        exitButton.setOnAction {
            println("Bye from exitButton... ")
            Platform.runLater {
                println("...bye (exitButton's runLater)")
                NTClient.disconnect()
                Platform.exit()
                println("...return (exitButton's runLater)")
            }
        }

        addRow(1, exitButton)
    }
}