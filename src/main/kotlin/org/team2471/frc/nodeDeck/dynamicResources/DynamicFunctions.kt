package org.team2471.frc.nodeDeck.dynamicResources

import javafx.scene.image.ImageView
import javafx.scene.input.MouseButton
import javafx.scene.shape.*
import javafx.util.Duration
import org.team2471.frc.lib.math.Vector2
import org.team2471.frc.lib.motion_profiling.Path2D
import org.team2471.frc.lib.units.radians
import org.team2471.frc.nodeDeck.dynamicPanes.FieldPane.genTransAnimation
import org.team2471.frc.nodeDeck.dynamicPanes.FieldPane.isAnimationPlaying
import org.team2471.frc.nodeDeck.dynamicPanes.FieldPane.odomTransAnimation
import org.team2471.frc.nodeDeck.dynamicPanes.PropertiesPane.sliderPointPos
import org.team2471.frc.nodeDeck.dynamicPanes.SettingsPane
import org.team2471.frc.nodeDeck.dynamicPanes.SettingsPane.sizeInput
import kotlin.math.*

// Stores the offset in the X direction from the top-left corner of the robot to where the mouse drags the robot image
val xOffset: Double
    get() = (30 / 81.3) * sizeInput.text.toDouble()

// Stores the offset in the Y direction from the top-left corner of the robot to where the mouse drags the robot image
val yOffset: Double
    get() = (90 / 81.3) * sizeInput.text.toDouble()

// Calculates the translation and rotation of the robot image based off the mouse position when the mouse drags the robot image
fun calculateImageDrag(imageView: ImageView): ImageView {
    // Triggers the calculation when the mouse is dragging the image parameter
    imageView.setOnMouseDragged { event ->
        if (!SettingsPane.settingsPopup.isShowing) {

            // Checks if the button pressed is the left mouse button
            if (event.button == MouseButton.PRIMARY) {
                // Calculates the translation of the image based off the mouse position (event) and the X/Y offsets
                imageView.x += event.sceneX - imageView.x - xOffset
                imageView.y += event.sceneY - imageView.y - yOffset
            } else {
                // On right click, calculates the desired rotation of the image using the ArcTangent function and the offset from the mouse to the centre of the image
                imageView.rotate = -atan2(
                    -(event.sceneY - imageView.y - yOffset),
                    event.sceneX - imageView.x - xOffset
                ).radians.asDegrees + 90
                // If the shift key is held down, snap the rotation of the image in 45 degree increments using the modulo operator
                if (event.isShiftDown) {
                    // Fixes weird issues with going from 359 degrees to 0 degrees
                    if (imageView.rotate < 0) {
                        imageView.rotate += 360
                    }
                    imageView.rotate -= imageView.rotate % 45
                }
            }
        }
    }
        // Returns the rotated and translated imageView
        return imageView
}

// Calculates the translation of a point along the X-Axis and coerces it between the minimum and maximum X values
fun calculateSliderDrag(point: Circle, line: Line, minX: Double, maxX: Double): Circle {
    // Triggers the calculation when the mouse is dragging the point on the slider
    point.setOnMouseDragged { event ->
        if (!SettingsPane.settingsPopup.isShowing) {
            genTransAnimation.pause()
            odomTransAnimation.pause()
            if (event.button == MouseButton.PRIMARY) {
                // Changes the position of the slider so that it is at the same position as the mouse, taking into account the minimum and maximum X values
                sliderPointPos.set(
                    (sliderPointPos.get() + event.sceneX - point.centerX - point.radius).coerceIn(
                        minX,
                        maxX
                    )
                )
                // Starts the odometry animation then jumps to the right duration based off of where the slider is positioned
                odomTransAnimation.play()
                odomTransAnimation.pause()
                odomTransAnimation.jumpTo(Duration(((sliderPointPos.get() - minX) / (maxX - minX)) * odomTransAnimation.duration.toMillis()))
                // Starts the generated animation and jumps to the right duration based off of where the slider is positioned
                genTransAnimation.play()
                genTransAnimation.pause()
                genTransAnimation.jumpTo(Duration(((sliderPointPos.get() - minX) / (maxX - minX)) * genTransAnimation.duration.toMillis()))

            }
        }
    }
    line.setOnMouseDragged { event ->
        if (!SettingsPane.settingsPopup.isShowing) {
            genTransAnimation.pause()
            odomTransAnimation.pause()

            if (event.button == MouseButton.PRIMARY) {
                    // Changes the position of the slider so that it is at the same position as the mouse, taking into account the minimum and maximum X values
                    sliderPointPos.set(
                        (sliderPointPos.get() + event.sceneX - point.centerX - point.radius).coerceIn(
                            minX,
                            maxX
                        )
                    )
                    // Starts the odometry animation then jumps to the right duration based off of where the slider is positioned
                    odomTransAnimation.play()
                    odomTransAnimation.pause()
                    odomTransAnimation.jumpTo(Duration(((sliderPointPos.get() - minX) / (maxX - minX)) * odomTransAnimation.duration.toMillis()))
                    // Starts the generated animation and jumps to the right duration based off of where the slider is positioned
                    genTransAnimation.play()
                    genTransAnimation.pause()
                    genTransAnimation.jumpTo(Duration(((sliderPointPos.get() - minX) / (maxX - minX)) * genTransAnimation.duration.toMillis()))

                }
            }

        }
    // Returns the translated point
    return point
}

// Adds a line to the given point in TMM coordinates to the given path
private fun addPathLine(path: Path, point: Vector2) {
    // Converts the point to the screen coordinates
    val tp = point.tmmCoords.toScreenCoords()
    // Creates the line to the given point
    val line = LineTo(tp.x, tp.y)
    // Adds the line to the path
    path.elements.add(line)
}

// Adds a start point (Move to) to the given path
fun Path.addStartPoint(startPoint: Vector2) {
    // Converts the start point to screen coordinates
    val startPointScreenCoords = startPoint.tmmCoords.toScreenCoords()
    // Adds the start point to the path
    this.elements.add(MoveTo(startPointScreenCoords.x, startPointScreenCoords.y))
}

// Converts meanlib's Path2D to a JavaFX Path
fun Path2D.toLinearFXPath(): Path? {
    // Checks if the path is empty or null
    if (this.duration == 0.0) {
        return null
    }
    // Initializes a new path
    val outPath = Path()
    // Gets the total length of the Path2D, as well as the starting pos and the time to step forwards each loop
    val totalTime = this.durationWithSpeed
    val deltaT = totalTime / 200.0
    var pos = this.getPosition(0.0)
    var t = deltaT

    // Adds the starting position to the output path
    outPath.addStartPoint(pos)

    // Loops through the path, checking the position of the path every deltaT seconds, and adds the position to the output path
    while (t <= totalTime) {
        // Gets the position of the path at the current time
        pos = this.getPosition(t)

        // Adds the position to the output path
        addPathLine(outPath, pos)
        // Increments time
        t += deltaT
    }

    // Sets the width of the path
    outPath.strokeWidth = 2.0

    // Returns the output path
    return outPath
}

fun Double.roundTo(numFractionDigits: Int): Double {
    val factor = 10.0.pow(numFractionDigits.toDouble())
    return (this * factor).roundToInt() / factor
}


