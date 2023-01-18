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

        initConnectionStatusCheck()


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
    fun connect() {
        val address = ipAddress
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

    private fun initConnectionStatusCheck() {
        println("inside initConnectionStatusCheck")
        val updateFrequencyInSeconds = 5
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                // check network table connection
                if (InetAddress.getLocalHost().hostAddress.startsWith(ipAddress.substringBeforeLast(".", "____"))) {
                    if (!networkTableInstance.isConnected) {
                        // attempt to connect
                        println("found FRC network. Connecting to network table")
                        connect()
                    }
                } else {
                    // stop client only for teams using the ip address format (10.24.71.2). for others don't attempt to stop client.
                    // the main benefit is to reduce log spamming of failed connection errors, so leaving it in is not inherently harmful
                    if (!ipAddress.matches("[1-9](\\d{1,3})?".toRegex())) {
                        networkTableInstance.stopClient()
                    }
                }
            }
        }, 10, 1000L * updateFrequencyInSeconds)
    }
}