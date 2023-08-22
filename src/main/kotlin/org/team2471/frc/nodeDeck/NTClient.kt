package org.team2471.frc.nodeDeck

import com.google.gson.Gson
import `dynamic-functions`.toLinearFXPath
import edu.wpi.first.networktables.NetworkTableEvent
import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.wpilibj.DriverStation
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import javafx.application.Platform
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.team2471.frc.lib.motion_profiling.Path2D
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane
import java.io.File
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.util.*


object NTClient {
    val networkTableInstance = NetworkTableInstance.getDefault()
    private val fmsTable = networkTableInstance.getTable("FMSInfo")
    private val nodeTable = networkTableInstance.getTable("NodeDeck")
    private val armTable = networkTableInstance.getTable("Arm")
    private val driveTable = networkTableInstance.getTable("Drive")

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
    private val demoReachLimitEntry = armTable.getDoubleTopic("Demo Reach Limit").getEntry(47.0)
    private val demoBoundaryLimitEntry = driveTable.getDoubleTopic("Demo Mode Drive Limit").getEntry(1.0)
    private val demoBoundaryEntry = driveTable.getBooleanTopic("Demo Boundary").getEntry(false)
    private val tagLookingAtEntry = driveTable.getBooleanTopic("Demo Look At Tags").getEntry(false)
    val demoSpeedEntry = SmartDashboard.getEntry("DemoSpeed")
    private var prevDemoSpeed = 1.0
    private var prevReachLimit = 47.0
    private var prevDemoBoundaryLimit = 1.0

    private var genPathEntrySub =
        NetworkTableInstance.getDefault().getTable("Drive").getStringTopic("Planned Path").subscribe("")

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
    val demoMode: Boolean
        get() = demoSpeedEntry.getDouble(1.0) < 1.0
    var demoReachLimit: Double
        get() = demoReachLimitEntry.get()
        set(value) = demoReachLimitEntry.set(value)
    var demoBoundary: Boolean
        get() = demoBoundaryEntry.get()
        set(value) = demoBoundaryEntry.set(value)
    var demoBoundaryLimit: Double
        get() = demoBoundaryLimitEntry.get()
        set(value) = demoBoundaryLimitEntry.set(value)
    var tagLookingAt: Boolean
        get() = tagLookingAtEntry.get()
        set(value) = tagLookingAtEntry.set(value)

    var formatter = SimpleDateFormat("dd-MM-yyyy_HH-mm-ss")


    init {
        println("NTClient says hi!!")
        initConnectionStatusCheck()

        NetworkTableInstance.getDefault().addListener(
            genPathEntrySub,
            EnumSet.of(
                NetworkTableEvent.Kind.kImmediate,
                NetworkTableEvent.Kind.kPublish,
                NetworkTableEvent.Kind.kValueAll
            )
        ) { event ->
            println("Automous change detected")
            var path2dFile: File? = null
            var path2d = null
            var gson = Gson()
            println("generated_2d_path${Instant.now().toString().replace(":", "-")}.json")
            try {
                path2dFile = File("PathJSONs/generated_2d_path${Instant.now().toString().replace(":", "-")}.json")
                if (!path2dFile.createNewFile()) {
                    println("Generated 2D path file already exists.")
                }
            } catch (error: Throwable) {
                DriverStation.reportError("Something went wrong while saving 2D path. Error: ${error}", false)
                println("Something went wrong while saving 2D path.")
            }

            val json = event.valueData?.value?.string
            if (json?.isNotEmpty() == true) {
                if (path2dFile != null) {
                    println("CacheFile != null. Hi.")
                    FieldPane.generatedPath = gson.fromJson(json, Path2D::class.java).toLinearFXPath()
                    path2dFile.writeText(json)
                } else {
                    println("cacheFile == null. Hi.")
                }
                println("New autonomi written to cache")
            } else {
                DriverStation.reportWarning("Empty autonomi received from network tables", false)
            }
        }
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
        if (demoSpeedEntry.getDouble(1.0) != prevDemoSpeed) {
            DemoTab.demoSpeedInput.text = demoSpeedEntry.getDouble(1.0).toString()
        }
        if (demoReachLimit != prevReachLimit) {
            DemoTab.reachLimitInput.text = demoReachLimit.toString()
        }
        if (demoBoundaryLimit != prevDemoBoundaryLimit) {
            DemoTab.reachLimitInput.text = demoReachLimit.toString()
        }
        SettingsTab.updateArmModeLabel()
        SettingsTab.updateArmModeButtons()
        isFloorConeEntry.set(LongFormat.isFloorCone)
        AutoConfig.showNodeAutoChanger()
        DemoTab.updateDemoButtons()
        prevReachLimit = demoReachLimit
        prevDemoSpeed = demoSpeedEntry.getDouble(1.0)
        prevDemoBoundaryLimit = demoBoundaryLimit
    }
    fun setNodeDeckAuto() {
        SmartDashboard.putString("Autos/selected", "NodeDeck")
    }
}