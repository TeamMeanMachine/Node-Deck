package org.team2471.frc.nodeDeck

import javafx.scene.control.Button
import javafx.scene.layout.GridPane

object TestTab: GridPane() {

    val testButton = Button("test")

    init {

        testButton.setOnAction {

        }

//        // The following doesn't work. Button gives a IllegalStateException
//        // and timer keeps running. Needs further study. EK 1/28/23.
//        testButton.setOnAction {
//            println("Bye from exitButton... ")
//            Platform.runLater {
//                println("...bye (exitButton's runLater)")
//                NTClient.disconnect()
//                Platform.exit()
//                println("...return (exitButton's runLater)")
//            }
//        }

        addRow(1, testButton)
    }

}