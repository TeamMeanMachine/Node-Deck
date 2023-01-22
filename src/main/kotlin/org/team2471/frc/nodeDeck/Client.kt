package org.team2471.frc.nodeDeck

import edu.wpi.first.networktables.NetworkTableInstance
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.InetAddress
import java.util.*

object Client {
    val networkTableInstance = NetworkTableInstance.getDefault()
    val table = networkTableInstance.getTable("FMSinfo")
    val isRedEntry = table.getBooleanTopic("isRedAlliance").subscribe(true)
    var isRed: Boolean = isRedEntry.get()
    var connectionJob: Job? = null
    var ipAddress = "10.24.71.2"
    init {
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