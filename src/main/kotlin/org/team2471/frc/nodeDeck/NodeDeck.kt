package org.team2471.frc.nodeDeck

import javafx.application.Application
import javafx.geometry.Rectangle2D
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Screen
import javafx.stage.Stage

class NodeDeck : Application() {
    companion object {
        lateinit var stage: Stage
        var selectedNode = 0

        @JvmStatic
        fun main(args: Array<String>) {
            println("Launching NodeDeck...")
            launch(NodeDeck::class.java, *args)
        }
    }

    override fun start(stage: Stage) {

        val screen = Screen.getPrimary()

        stage.title = "NodeDeck"
        stage.icons.add(Image("node-icon.png"))
        Companion.stage = stage

        val bounds = Rectangle2D(screen.visualBounds.minX, screen.visualBounds.minY, screen.visualBounds.width, screen.visualBounds.height)
        stage.scene = Scene(ColorOutline, bounds.width, bounds.height)
        stage.sizeToScene()
        stage.show()
        stage.isFullScreen = true

        NTClient.setTables()
    }

    override fun stop() {
        NTClient.disconnect()   // otherwise NTClient's timer keeps running
        println("Bye")
    }
}