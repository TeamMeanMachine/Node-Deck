package org.team2471.frc.nodeDeck

import edu.wpi.first.networktables.NetworkTableInstance
import javafx.application.Platform
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

object NTClient {
    val networkTableInstance = NetworkTableInstance.getDefault()
    private val fmsTable = networkTableInstance.getTable("FMSInfo")
    private val nodeTable = networkTableInstance.getTable("NodeDeck")
    private val isRedEntry = fmsTable.getBooleanTopic("IsRedAlliance").subscribe(true)
    private val chargeInAutoEntry = nodeTable.getBooleanTopic("ChargeInAuto").publish()
    private val isStartingLeftEntry = nodeTable.getBooleanTopic("IsStartingLeft").publish()
    private val selectedNodeEntry = nodeTable.getIntegerTopic("Selected Node").publish()
    private val piecesInAutoEntry = nodeTable.getIntegerTopic("PiecesInAuto").publish()
    val isRed: Boolean
        get() = isRedEntry.get()
    private val timer = Timer()
    private var connectionJob: Job? = null
    val ipAddress: String
        get() = SettingsTab.ipInput.text

    init {
        println("NTClient says hi!!")
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
                    ColorOutline.checkAlliance()
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
        selectedNodeEntry.set(NodeDeck.selectedNode.toLong())
        piecesInAutoEntry.set(AutoConfig.amountOfPiecesSelector.value.toLong())
    }
    fun reflectNodeNumbers(n: Int): Int {
        var r: Int = n
        if (n == 1) r = 21
        if (n == 2) r = 20
        if (n == 3) r = 19
        if (n == 4) r = 24
        if (n == 5) r = 23
        if (n == 6) r = 22
        if (n == 7) r = 27
        if (n == 8) r = 26
        if (n == 9) r = 25
        if (n == 10) r = 12
        if (n == 11) r = 11
        if (n == 12) r = 10
        if (n == 13) r = 15
        if (n == 14) r = 14
        if (n == 15) r = 13
        if (n == 16) r = 18
        if (n == 17) r = 17
        if (n == 18) r = 16
        if (n == 19) r = 3
        if (n == 20) r = 2
        if (n == 21) r = 1
        if (n == 22) r = 6
        if (n == 23) r = 5
        if (n == 24) r = 4
        if (n == 25) r = 9
        if (n == 26) r = 8
        if (n == 27) r = 7
        return r
    }
}