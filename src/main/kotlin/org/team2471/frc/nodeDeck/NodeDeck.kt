package org.team2471.frc.nodeDeck

import javafx.application.Application
import javafx.application.Platform
import javafx.geometry.Rectangle2D
import javafx.scene.Scene
import javafx.stage.Screen
import javafx.stage.Stage

class NodeDeck : Application() {
    companion object {
        lateinit var stage: Stage

        @JvmStatic
        fun main(args: Array<String>) {
            println("Launching NodeDeck...")
            launch(NodeDeck::class.java, *args)
        }
    }

    override fun start(stage: Stage) {

        val screen = Screen.getPrimary()

        stage.title = "NodeDeck"
        Companion.stage = stage

        val bounds = Rectangle2D(screen.visualBounds.minX, screen.visualBounds.minY, screen.visualBounds.width, screen.visualBounds.height)
        stage.scene = Scene(ColorOutline, bounds.width, bounds.height)
        stage.sizeToScene()
        stage.show()
        stage.isFullScreen = true

        // The following doesn't work. Close box gets a NullPointerException
        // and timer keeps running. Needs further study. EK 1/28/23.
        stage.setOnCloseRequest {
            println("Bye from NodeDeck... ")
            NTClient.disconnect()
            Platform.runLater {
                println("...bye (NodeDeck's runLater)")
                Platform.exit()
                println("...return (NodeDeck's runLater)")
            }
        }

        NTClient.setTables()
    }
}