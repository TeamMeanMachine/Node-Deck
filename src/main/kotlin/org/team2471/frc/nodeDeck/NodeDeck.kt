package org.team2471.frc.nodeDeck

import javafx.application.Application
import javafx.geometry.Rectangle2D
import javafx.scene.Scene
import javafx.stage.Screen
import javafx.stage.Stage
import edu.wpi.first.networktables.NetworkTableInstance
import kotlinx.coroutines.*
import java.net.InetAddress
import java.util.*
import java.util.Timer

class NodeDeck : Application() {
    companion object {
        lateinit var stage: Stage
        val networkTableInstance: NetworkTableInstance = NetworkTableInstance.create()
        private var connectionJob: Job? = null
        var ipAddress = "10.24.71.2"


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



        ColorOutline.checkAlliance()
    }
}