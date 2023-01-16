package org.team2471.frc.nodeDeck

import javafx.application.Application
import javafx.geometry.Rectangle2D
import javafx.scene.Scene
import javafx.stage.Screen
import javafx.stage.Stage
import edu.wpi.first.networktables.NetworkTableInstance
import kotlinx.coroutines.*

class NodeDeck : Application() {
    companion object {
        lateinit var stage: Stage
        var isRedAlliance: Boolean = true
        val networkTableInstance : NetworkTableInstance = NetworkTableInstance.create()
        private var connectionJob: Job? = null

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

        fun connect() {
            val address = "10.24.71.2"
            println("Connecting to address $address")

            connectionJob?.cancel()

            connectionJob = GlobalScope.launch {
                // shut down previous server, if connected
                if (networkTableInstance.isConnected) {
                    networkTableInstance.stopDSClient()
                    networkTableInstance.stopClient()
                }

                // reconnect with new address
                networkTableInstance.startClient4("PathVisualizer")
                if (address.matches("[1-9](\\d{1,3})?".toRegex())) {
                    networkTableInstance.setServerTeam(address.toInt())
                } else {
                    networkTableInstance.setServer(address)
                }
            }
        }

        ColorOutline.checkAlliance()
    }
}