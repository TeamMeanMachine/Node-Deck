package org.team2471.frc.nodeDeck.`dynamic-resources`

import org.team2471.frc.lib.math.Vector2
import org.team2471.frc.lib.units.asMeters
import org.team2471.frc.lib.units.feet
import java.util.Vector

val fieldDimensionsInMeters = Vector2(26.29.feet.asMeters,54.27.feet.asMeters) // field diagram & json is 26.29, 54.27 but includes side walls and barriers
val fieldCenterOffsetInMeters = fieldDimensionsInMeters/2.0

@JvmInline
value class Position(val posAsWPI: Vector2) {

    fun toTmmCoords(): Vector2 {
        return Vector2(posAsWPI.y - fieldCenterOffsetInMeters.x, -(posAsWPI.x - fieldCenterOffsetInMeters.y))
    }
    fun toWpiCoords(): Vector2 {
        return posAsWPI
    }
    fun toScreenCoords(robotWidth: Double, fieldImageScale: Double): Vector2 {
        return Vector2(
            fieldImageScale * ((152.3 * posAsWPI.x) + 415) + (robotWidth / 2),
            fieldImageScale * ((-144.35 * posAsWPI.y) + 1177) + (robotWidth / 2)
        )
    }

//    Because for some reason javafx's Path coords are different
    fun toPathCoords(fieldImageScale: Double): Vector2 {
        return Vector2(
            fieldImageScale * ((-160 * posAsWPI.x) + 2210),
            fieldImageScale * ((158 * posAsWPI.y) + 550)
        )
    }
}

inline val Vector2.tmmCoords get() = Position(Vector2((this.y.feet.asMeters + fieldCenterOffsetInMeters.y), -this.x.feet.asMeters + fieldCenterOffsetInMeters.x))
inline val Vector2.wpiCoords get() = Position(this)

fun Vector2.screenCoords(robotWidth: Double, fieldImageScale: Double): Position {
    return Position(
        Vector2(
            (((x - (robotWidth / 2)) / fieldImageScale) - 415) / 152.3,
            (((y - (robotWidth / 2)) / fieldImageScale) - 1177) / -144.35
        )
    )
}
