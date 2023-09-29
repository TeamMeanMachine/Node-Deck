package org.team2471.frc.nodeDeck.dynamicResources

import com.google.gson.Gson
import org.team2471.frc.lib.math.Vector2
import org.team2471.frc.lib.units.*
import org.team2471.frc.nodeDeck.dynamicPanes.FieldPane.fieldImageScale
import java.io.File

// Define the dimensions of the field in meters
val fieldDimensionsInMeters = Vector2(26.29.feet.asMeters, 54.27.feet.asMeters) // field diagram & json is 26.29, 54.27 but includes side walls and barriers

// Calculate the center offset of the field in meters
val fieldCenterOffsetInMeters = fieldDimensionsInMeters / 2.0

// The pixel value of the WPI Zero position in the field image
val fieldImageWPIZeroPos = Vector2(481.0, 1374.0)

// Define the position of the opposite corner of the field image in WPI coordinates
val fieldImageOppositeCornerPos = Vector2(3125.0, 89.0)

// Calculate the pixels per centimeter based on the field image scale
val ppc: Double
    get() = (fieldImageScale * (fieldImageWPIZeroPos.y - fieldImageOppositeCornerPos.y)) / fieldDimensionsInMeters.x.meters.asCm

// Define a value class for representing positions in WPI coordinates
@JvmInline
value class Position(val posAsWPI: Vector2) {

    // Convert the position to Tmm coordinates
    fun toTmmCoords(): Vector2 {
        return Vector2(
            -(posAsWPI.y - fieldCenterOffsetInMeters.x).meters.asFeet,
            ((posAsWPI.x - fieldCenterOffsetInMeters.y)).meters.asFeet
        )
    }

    // Return the position in WPI coordinates
    fun toWpiCoords(): Vector2 {
        return posAsWPI
    }

    // Convert the position to screen coordinates
    fun toScreenCoords(): Vector2 {
        return toScreenCoords(0.0)
    }

    // Convert the position to screen coordinates with a specified robot width
    fun toScreenCoords(robotWidth: Double): Vector2 {
        return Vector2(
            (posAsWPI.y.meters.asCm * ppc) + (fieldImageWPIZeroPos.x * fieldImageScale) - (robotWidth / 2),
            (-posAsWPI.x.meters.asCm * ppc) + (fieldImageWPIZeroPos.y * fieldImageScale) - (robotWidth / 2)
        )
    }
}

// Extension property to convert a Vector2 to Tmm coordinates
inline val Vector2.tmmCoords get() = Position(
    Vector2(
        -this.x.feet.asMeters + fieldCenterOffsetInMeters.x,
        this.y.feet.asMeters + fieldCenterOffsetInMeters.y
    )
)

// Extension property to convert a Vector2 to WPI coordinates
inline val Vector2.wpiCoords get() = Position(this)

// Function to convert a Vector2 to screen coordinates with a specified robot width and field image scale
fun Vector2.screenCoords(robotWidth: Double, fieldImageScale: Double): Position {
    return Position(
        Vector2(
            x,
            y
        )
    )
}

data class fieldImageProperties(val fileName: String, val name: String, val imageWPIZeroPos: Vector2, val imageOppositeCornerPos: Vector2) {
    val ppc: Double
        get() = (fieldImageScale * (imageWPIZeroPos.y - imageOppositeCornerPos.y)) / fieldDimensionsInMeters.x.meters.asCm
}