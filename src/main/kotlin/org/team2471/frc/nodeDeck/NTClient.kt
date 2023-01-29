package org.team2471.frc.nodeDeck

import edu.wpi.first.networktables.NetworkTableInstance
import javafx.application.Platform
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

object NTClient {
    val networkTableInstance = NetworkTableInstance.getDefault()
    val table = networkTableInstance.getTable("FMSInfo")
    val timer = Timer()
    val isRedEntry = table.getBooleanTopic("IsRedAlliance").subscribe(true)
    val isRed: Boolean
        get() = isRedEntry.get();
    var connectionJob: Job? = null
    var ipAddress = "127.0.0.1"

    // var ipAddress = "localhost"
    // Todo: Add way to set IP address and reconnect. Use case is using simulator at "localhost".
    init {
        println("NTClient says hi!")
        initConnectionStatusCheck()
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
            networkTableInstance.startClient4("NodeDeck")
            if (address.matches("[1-9](\\d{1,3})?".toRegex())) { //checks if ip is a team number
                networkTableInstance.setServerTeam(address.toInt())
                networkTableInstance.startDSClient()
            } else {
                networkTableInstance.setServer(address)
                networkTableInstance.startDSClient()
            }
            ColorOutline.checkAlliance()
        }
    }

    private fun initConnectionStatusCheck() {
        println("inside initConnectionStatusCheck")
        val updateFrequencyInSeconds = 2
        timer.schedule(object : TimerTask() {
            override fun run() {
                // check network table connection
                if (!networkTableInstance.isConnected) {
                    // attempt to connect
                    println("Not Connected!!!! Connecting to network table...")
                    ColorOutline.style = "-fx-background-color: #a8a8a8; -fx-border-color: #ffff00; -fx-border-width: 10 10 10 10"
                    connect()
                }

                Platform.runLater {
                    ColorOutline.checkAlliance()
                }
            }
        }, 10, 1000L * updateFrequencyInSeconds)
    }
    fun printNTTopicConnection() {
        println("isRedEntry = ${isRedEntry.exists()}")
    }

    fun disconnect() {
        println("NTClient.disconnect")
        timer.cancel()
        if (networkTableInstance.isConnected) {
            println("NTClient.disconnect stopping networkTableInstance")
            networkTableInstance.stopDSClient()
            networkTableInstance.stopClient()
        }
        println("NTClient.disconnect return")
    }
}