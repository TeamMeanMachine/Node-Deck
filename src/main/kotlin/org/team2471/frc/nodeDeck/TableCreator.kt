package org.team2471.frc.nodeDeck

import edu.wpi.first.networktables.NetworkTableInstance
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.InetAddress
import java.util.*

object TableCreator {

    val isRedAllianceEntry = NodeDeck.networkTableInstance.getTable("FMSInfo").getEntry("isRedAlliance")
    var isRedAlliance: Boolean = true
        get() = isRedAllianceEntry.getBoolean(true)
        set(value) {
            field = value
            isRedAllianceEntry.setBoolean(value)
            println("set isRedAlliance to $value")
        }
    init {
        println("TableCreator says hi!")
    }
    fun checkAlliance() {
        isRedAlliance = isRedAllianceEntry.setBoolean(isRedAllianceEntry.getBoolean(true))
    }
}