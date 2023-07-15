package org.team2471.frc.nodeDeck.`dynamic-resources`

import `dynamic-resources`.asMeters
import `dynamic-resources`.feet

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
        return Vector2(fieldImageScale * ((152.3 * posAsWPI.x) + 415) + (robotWidth / 2),fieldImageScale * ((-144.35 * posAsWPI.y) + 1177) + (robotWidth / 2))
    }
}

inline val Vector2.tmmCoords get() = Position(Vector2((this.y.feet.asMeters + fieldCenterOffsetInMeters.y), -this.x.feet.asMeters + fieldCenterOffsetInMeters.x))
inline val Vector2.wpiCoords get() = Position(this)

//If you want to look at the .screenCoords function, look at line 67 Vector2.kt

