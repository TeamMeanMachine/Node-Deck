package org.team2471.frc.nodeDeck.`dynamic-resources`

import `dynamic-resources`.asMeters
import `dynamic-resources`.feet
import `dynamic-resources`.meters

val fieldDimensionsInMeters = Vector2(26.29.feet.asMeters,54.27.feet.asMeters) // field diagram & json is 26.29, 54.27 but includes side walls and barriers
val fieldCenterOffsetInMeters = fieldDimensionsInMeters/2.0

@JvmInline
value class Position(val posAsWPI: Vector2) {

}

inline val Vector2.tmm get() = Vector2((-this.y.feet.asMeters + fieldCenterOffsetInMeters.y), this.x.feet.asMeters + fieldCenterOffsetInMeters.x)
inline val Vector2.wpi get() = this
