package org.team2471.frc.nodeDeck.`dynamic-resources`

import org.team2471.frc.lib.math.Vector2
import org.team2471.frc.lib.units.*
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.fieldImageScale

val fieldDimensionsInMeters = Vector2(26.29.feet.asMeters,54.27.feet.asMeters) // field diagram & json is 26.29, 54.27 but includes side walls and barriers
val fieldCenterOffsetInMeters = fieldDimensionsInMeters/2.0
val fieldImageWPIZeroPos = Vector2(481.0, 1374.0)
val fieldImageOppositeCornerPos = Vector2(3125.0, 89.0)
val ppc = (fieldImageScale * (fieldImageWPIZeroPos.y - fieldImageOppositeCornerPos.y)) / fieldDimensionsInMeters.x.meters.asCm

@JvmInline
value class Position(val posAsWPI: Vector2) {

    fun toTmmCoords(): Vector2 {
        return Vector2(
                -(posAsWPI.y - fieldCenterOffsetInMeters.x).meters.asFeet,
                ((posAsWPI.x - fieldCenterOffsetInMeters.y)).meters.asFeet
        )
    }
    fun toWpiCoords(): Vector2 {
        return posAsWPI
    }

    fun toScreenCoords(): Vector2 {
        return toScreenCoords(0.0)
    }
    fun toScreenCoords(robotWidth: Double): Vector2 {
        return Vector2(
            (posAsWPI.y.meters.asCm * ppc) + (fieldImageWPIZeroPos.x * fieldImageScale) - (robotWidth / 2),
            (-posAsWPI.x.meters.asCm * ppc) + (fieldImageWPIZeroPos.y * fieldImageScale) - (robotWidth / 2)
        )
    }
}

inline val Vector2.tmmCoords get() = Position(
    Vector2(
        -this.x.feet.asMeters + fieldCenterOffsetInMeters.x,
        this.y.feet.asMeters + fieldCenterOffsetInMeters.y
    )
)
inline val Vector2.wpiCoords get() = Position(this)

fun Vector2.screenCoords(robotWidth: Double, fieldImageScale: Double): Position {
    return Position(
        Vector2(
            x,
            y
        )
    )
}
