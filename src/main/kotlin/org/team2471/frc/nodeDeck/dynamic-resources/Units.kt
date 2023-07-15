package `dynamic-resources`

import kotlin.math.IEEErem

@JvmInline
value class Length(val asInches: Double) {
    operator fun plus(other: Length) = Length(asInches + other.asInches)

    operator fun minus(other: Length) = Length(asInches - other.asInches)

    operator fun times(factor: Double) = Length(asInches * factor)

    operator fun div(factor: Double) = Length(asInches / factor)

    operator fun rem(other: Length) = Length(asInches % other.asInches)

    operator fun unaryMinus() = Length(-asInches)

    operator fun unaryPlus() = this

    operator fun compareTo(other: Length) = asInches.compareTo(other.asInches)

    override fun toString() = "$asInches inches"
}

// constructors
inline val Number.inches get() = Length(this.toDouble())
inline val Number.feet get() = Length(this.toDouble() * 12.0)
inline val Number.meters get() = Length(this.toDouble() * 39.37008)
inline val Number.cm get() = Length(this.toDouble() * 0.3937008)

// destructors
inline val Length.asFeet get() = asInches / 12.0
inline val Length.asMeters get() = asInches / 39.37008
inline val Length.asCm get() = asInches / 0.3937008


@JvmInline
value class Angle(val asDegrees: Double) {
    operator fun plus(other: Angle) = Angle(asDegrees + other.asDegrees)

    operator fun minus(other: Angle) = Angle(asDegrees - other.asDegrees)

    operator fun times(factor: Double) = Angle(asDegrees * factor)

    operator fun div(factor: Double) = Angle(asDegrees / factor)

    operator fun rem(other: Angle) = Angle(asDegrees % other.asDegrees)

    operator fun unaryPlus() = this

    operator fun unaryMinus() = Angle(-asDegrees)

    operator fun compareTo(other: Angle) = asDegrees.compareTo(other.asDegrees)

    override fun toString() = "$asDegrees degrees"

    fun sin() = Angle.sin(this)

    fun cos() = Angle.cos(this)

    fun tan() = Angle.tan(this)

    fun wrap() = Angle(asDegrees.IEEErem(360.0))

    fun unWrap(nearByAngle: Angle) : Angle {
        return nearByAngle + (this - nearByAngle).wrap()
    }

    companion object {
        @JvmStatic
        fun sin(angle: Angle) = Math.sin(angle.asRadians)

        @JvmStatic
        fun cos(angle: Angle) = Math.cos(angle.asRadians)

        @JvmStatic
        fun tan(angle: Angle) = Math.tan(angle.asRadians)

        @JvmStatic
        fun asin(value: Double) = Angle(Math.toDegrees(Math.asin(value)))

        @JvmStatic
        fun acos(value: Double) = Angle(Math.toDegrees(Math.acos(value)))

        @JvmStatic
        fun atan(value: Double) = Angle(Math.toDegrees(Math.atan(value)))

        @JvmStatic
        fun atan2(y: Double, x: Double) = Angle(Math.toDegrees(Math.atan2(y, x)))

        @JvmStatic
        fun atan2(y: Length, x: Length) = Angle(Math.toDegrees(Math.atan2(y.asInches, x.asInches)))
    }
}

// constructors
inline val Number.radians get() = Angle(Math.toDegrees(this.toDouble()))
inline val Number.degrees get() = Angle(this.toDouble())

// destructors
inline val Angle.asRadians get() = Math.toRadians(asDegrees)