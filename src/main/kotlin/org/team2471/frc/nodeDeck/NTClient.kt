package org.team2471.frc.nodeDeck

import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import javafx.application.Platform
import kotlinx.coroutines.DelicateCoroutinesApi
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
    private val finishWithPieceEntry = nodeTable.getBooleanTopic("finishWithPiece").publish()
    private val startingPointEntry = nodeTable.getStringTopic("Starting Point").publish()
    private val selectedNodeEntry = nodeTable.getIntegerTopic("Selected Node").publish()
    val shoulderCoastModeEntry = nodeTable.getBooleanTopic("setShoulderCoastMode").getEntry(false)
    val shoulderBrakeModeEntry = nodeTable.getBooleanTopic("setShoulderBrakeMode").getEntry(false)
    private val amountOfPiecesInAutoEntry = nodeTable.getIntegerTopic("AmountOfAutoPieces").publish()
    private val autoOneEntry = nodeTable.getIntegerTopic("1").publish()
    private val autoTwoEntry = nodeTable.getIntegerTopic("2").publish()
    private val autoThreeEntry = nodeTable.getIntegerTopic("3").publish()
    private val autoFourEntry = nodeTable.getIntegerTopic("4").publish()
    private val autoFiveEntry = nodeTable.getIntegerTopic("5").publish()
    private val isFloorConeEntry = nodeTable.getBooleanTopic("isFloorCone").publish()

    private var reconnected: Boolean = true
    val isRed: Boolean
        get() = isRedEntry.get()
    private val timer = Timer()
    private var connectionJob: Job? = null
    val ipAddress: String
        get() = SettingsTab.ipInput.text
    var quarterCount = 0
    val selectedAuto
        get() = SmartDashboard.getString("Autos/selected", "no auto selected")

    init {
        println("NTClient says hi!!")
        initConnectionStatusCheck()
    }

    @OptIn(DelicateCoroutinesApi::class) //
    fun connect() {
        val address = ipAddress
        println("Connecting to address $address")

        connectionJob?.cancel()

        connectionJob = GlobalScope.launch {
            // shut down previous server, if connected
            if (networkTableInstance.isConnected) {
                networkTableInstance.stopClient()
            }

            // reconnect with new address
            networkTableInstance.startClient4("NodeDeck")
            networkTableInstance.setServer(address)
        }
    }

    private fun initConnectionStatusCheck() {
        println("inside initConnectionStatusCheck")
        val updateFrequencyInSeconds = 1
        timer.schedule(object : TimerTask() {
            override fun run() {
                // check network table connection
                if (!networkTableInstance.isConnected) {
                    // attempt to connect
                    println("\u001b[33m" + "Not Connected!!!! Connecting to network table..." + "\u001b[0m")
                    connect()
                }

                Platform.runLater {
                    setTables()
                    ColorOutline.checkAlliance()

                    if (!networkTableInstance.isConnected) { //QUARTER!!!!!
                        reconnected = false
                        quarterCount += 1
                        println("Quarter count: $quarterCount")
                    } else {
                        secondConnect()
                    }
                    if (!NodeDeck.stage.isFullScreen && !TabDeck.tabs.contains(TabDeck.fullscreenTab)) {
                        TabDeck.tabs.add(TabDeck.fullscreenTab)
                    }
                    NTTab.update()
                }
            }
        }, 10, 1000L * updateFrequencyInSeconds)
    }
    fun secondConnect() { // fix for a strange NetworkTable update bug
        if (!reconnected) {
            connect()
            reconnected = true
        }
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
        chargeInAutoEntry.set(AutoConfig.chargeInAuto)
        finishWithPieceEntry.set(AutoConfig.finishWPiece)
        startingPointEntry.set("${AutoConfig.startingPoint}")
        selectedNodeEntry.set(NodeDeck.selectedNode.toLong())
        amountOfPiecesInAutoEntry.set(AutoInterface.amountOfPieces.toLong())
        (AutoInterface.realNodeNumber(AutoInterface.first))?.let { autoOneEntry.set(it) }
        (AutoInterface.realNodeNumber(AutoInterface.second))?.let { autoTwoEntry.set(it) }
        (AutoInterface.realNodeNumber(AutoInterface.third))?.let { autoThreeEntry.set(it) }
        (AutoInterface.realNodeNumber(AutoInterface.fourth))?.let { autoFourEntry.set(it) }
        (AutoInterface.realNodeNumber(AutoInterface.fifth))?.let { autoFiveEntry.set(it) }
        SettingsTab.updateArmModeLabel()
        SettingsTab.updateArmModeButtons()
        isFloorConeEntry.set(LongFormat.isFloorCone)
        AutoConfig.showNodeAutoChanger()
    }
    fun setNodeDeckAuto() {
        SmartDashboard.putString("Autos/selected", "NodeDeck")
    }
}