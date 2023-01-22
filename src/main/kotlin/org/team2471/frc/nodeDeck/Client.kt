package org.team2471.frc.nodeDeck

import edu.wpi.first.networktables.NetworkTableInstance
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.InetAddress
import java.util.*

class Client {
    fun run() {
        val inst = NetworkTableInstance.getDefault()
        val table = inst.getTable("FMSinfo")
        val isRedEntry = table.getBooleanTopic("isRedAlliance").subscribe(true)
        var connectionJob: Job? = null
        var ipAddress = "10.24.71.2"

    }
    fun connect() {
        val address = NodeDeck.ipAddress
        println("Connecting to address $address")

        connectionJob?.cancel()

        connectionJob = GlobalScope.launch {
            // shut down previous server, if connected
            if (NodeDeck.networkTableInstance.isConnected) {
                NodeDeck.networkTableInstance.stopDSClient()
                NodeDeck.networkTableInstance.stopClient()
            }

            // reconnect with new address
            NodeDeck.networkTableInstance.startClient4("PathVisualizer")
            if (address.matches("[1-9](\\d{1,3})?".toRegex())) {
                NodeDeck.networkTableInstance.setServerTeam(address.toInt())
            } else {
                NodeDeck.networkTableInstance.setServer(address)
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
                if (InetAddress.getLocalHost().hostAddress.startsWith(NodeDeck.ipAddress.substringBeforeLast(".", "____"))) {
                    if (!NodeDeck.networkTableInstance.isConnected) {
                        // attempt to connect
                        println("found FRC network. Connecting to network table")
                        connect()
                    }
                } else {
                    // stop client only for teams using the ip address format (10.24.71.2). for others don't attempt to stop client.
                    // the main benefit is to reduce log spamming of failed connection errors, so leaving it in is not inherently harmful
                    if (!NodeDeck.ipAddress.matches("[1-9](\\d{1,3})?".toRegex())) {
                        NodeDeck.networkTableInstance.stopClient()
                    }
                }
            }
        }, 10, 1000L * updateFrequencyInSeconds)
    }
}