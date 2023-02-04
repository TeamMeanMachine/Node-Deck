package org.team2471.frc.nodeDeck

import edu.wpi.first.networktables.NetworkTableInstance
import javafx.application.Platform
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

object NTClient {
    val networkTableInstance = NetworkTableInstance.getDefault()
    val fmsTable = networkTableInstance.getTable("FMSInfo")
    val nodeTable = networkTableInstance.getTable("NodeDeck")
    val isRedEntry = fmsTable.getBooleanTopic("IsRedAlliance").subscribe(true)
    val chargeInAutoEntry = nodeTable.getBooleanTopic("ChargeInAuto").publish()
    val isStartingLeftEntry = nodeTable.getBooleanTopic("IsStartingLeft").publish()
    val selectedNodeEntry = nodeTable.getIntegerTopic("Selected Node").publish()
    val isRed: Boolean
        get() = isRedEntry.get()
    val timer = Timer()
    var connectionJob: Job? = null
    val ipAddress: String
        get() = SettingsTab.ipInput.text

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
//            setTables()
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
                    setTables()
                }
            }
        }, 10, 1000L * updateFrequencyInSeconds)
    }
    fun printNTTopicConnection() {
        println("isRedEntry = ${isRedEntry.exists()}")
    }

    fun disconnect() {
        timer.cancel()
        if (networkTableInstance.isConnected) {
            println("NTClient.disconnect stopping networkTableInstance")
            networkTableInstance.stopDSClient()
            networkTableInstance.stopClient()
        }
    }
    fun setTables() {
        chargeInAutoEntry.set(AutoConfig.chargeButton.isSelected)
        isStartingLeftEntry.set(AutoConfig.isStartingLeft)
        selectedNodeEntry.set((CompactFormat.selectedNode + CompactFormat.selectedGrid).toLong())
        ColorOutline.checkAlliance()
    }
}