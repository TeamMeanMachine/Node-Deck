package `dynamic-functions`

import edu.wpi.first.math.trajectory.Trajectory
import javafx.animation.PathTransition
import javafx.animation.RotateTransition
import javafx.scene.canvas.GraphicsContext
import javafx.scene.image.ImageView
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import javafx.scene.shape.*
import javafx.util.Duration
import org.team2471.frc.lib.math.Vector2
import org.team2471.frc.lib.motion_profiling.Path2D
import org.team2471.frc.lib.units.asMeters
import org.team2471.frc.lib.units.feet
import org.team2471.frc.lib.units.radians
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.fieldImageScale
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.genTransAnimation
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.isAnimationPlaying
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.odomTransAnimation
import org.team2471.frc.nodeDeck.DynamicPanes.PropertiesPane.sliderPointPos
import org.team2471.frc.nodeDeck.DynamicPanes.SettingsPane.sizeInput
import org.team2471.frc.nodeDeck.DynamicPanes.SideBarPane.isOdomAnimationSelected
import org.team2471.frc.nodeDeck.DynamicTab
import org.team2471.frc.nodeDeck.DynamicTab.snapRes
import org.team2471.frc.nodeDeck.`dynamic-resources`.Position
import org.team2471.frc.nodeDeck.`dynamic-resources`.tmmCoords
import org.team2471.frc.nodeDeck.`dynamic-resources`.wpiCoords
import java.lang.Math.floorDiv
import kotlin.coroutines.EmptyCoroutineContext.get
import kotlin.math.*

// This function takes an ImageView and a desired height as parameters.
// It scales the image to the specified height and returns the scaled ImageView.
fun scaleImageToHeight(image: ImageView, height: Double): ImageView {
    return scaleImage(image, height / image.image.height)
}

// This function takes an ImageView and a desired width as parameters.
// It scales the image to the specified width and returns the scaled ImageView.
fun scaleImageToWidth(image: ImageView, width: Double): ImageView {
    return scaleImage(image, width / image.image.width)
}

// This function takes an ImageView and a scaleFactor as parameters.
// It scales the image by multiplying the fitWidth and fitHeight of the ImageView by the scaleFactor.
// It then returns the updated ImageView.
fun scaleImage(image: ImageView, scaleFactor: Double): ImageView {
    image.fitWidth = scaleFactor * image.image.width
    image.fitHeight = scaleFactor * image.image.height
    return image
}

val xOffset: Double
    get() = (30 / 81.3) * sizeInput.text.toDouble()

val yOffset: Double
    get() = (90 / 81.3) * sizeInput.text.toDouble()

// This function calculates the drag behavior for an ImageView.
// When the mouse is dragged on the ImageView, it checks if the primary button is pressed.
// If so, it updates the x and y positions of the ImageView based on the mouse movement and the xOffset and yOffset.
// If the primary button is not pressed, it updates the rotation of the ImageView based on the mouse movement and the xOffset and yOffset.
// If the shift key is pressed, it adjusts the rotation angle to snap to 45-degree increments.
// Finally, it returns the updated ImageView.
fun calculateImageDrag(imageView: ImageView): ImageView {
    imageView.setOnMouseDragged { event ->
        if (event.button == MouseButton.PRIMARY) {
            imageView.x += event.sceneX - imageView.x - xOffset
            imageView.y += event.sceneY - imageView.y - yOffset
        } else {
            imageView.rotate = -atan2(-(event.sceneY - imageView.y - yOffset), event.sceneX - imageView.x - xOffset).radians.asDegrees + 90
            if (event.isShiftDown) {
                if (imageView.rotate < 0) {
                    imageView.rotate = imageView.rotate + 360

                }
                imageView.rotate -= imageView.rotate % 45
            }
        }
    }
    return imageView
}

// This function calculates the drag behavior for a slider point represented by a Circle.
// When the mouse is dragged on the Circle, it checks if the animation is not currently playing.
// If the primary button is pressed, it updates the position of the slider point based on the mouse movement and the specified minX and maxX values.
// It then adjusts the progress of the odomTransAnimation and genTransAnimation based on the new position of the slider point.
// Finally, it returns the updated Circle representing the slider point.
fun calculateSliderDrag(point: Circle, minX: Double, maxX: Double): Circle {
    point.setOnMouseDragged { event ->
        if (!isAnimationPlaying.get()) {
            if (event.button == MouseButton.PRIMARY) {
                sliderPointPos.set((sliderPointPos.get() + event.sceneX - point.centerX - point.radius).coerceIn(minX, maxX))
                odomTransAnimation.play()
                odomTransAnimation.pause()
                odomTransAnimation.jumpTo(Duration(((sliderPointPos.get() - minX) / (maxX - minX))  * odomTransAnimation.duration.toMillis()))

                genTransAnimation.play()
                genTransAnimation.pause()
                genTransAnimation.jumpTo(Duration(((sliderPointPos.get() - minX) / (maxX - minX))  * genTransAnimation.duration.toMillis()))

            }
        }
    }
    return point
}

// This is a private function called addPathLine.
// It takes a Path, a Vector2 point, and an optional stroke color as parameters.
// The function converts the point coordinates to screen coordinates using the tmmCoords and toScreenCoords methods.
// It sets the stroke width of the path to 2.0.
// Then, it creates a new LineTo element with the converted coordinates and adds it to the path's elements.
// This function is used to add a line segment to a path.
private fun addPathLine(path: Path, point: Vector2, stroke: Color = Color.BLACK) {
    val tp = point.tmmCoords.toScreenCoords()
    path.strokeWidth = 2.0
    var line = LineTo(tp.x, tp.y)
    path.elements.add(line)
}

// This extension function adds a starting point to a Path.
// It takes a Vector2 startPoint as a parameter and converts its coordinates to screen coordinates using the tmmCoords and toScreenCoords methods.
// Then, it adds a MoveTo element to the Path with the converted coordinates.
fun Path.addStartPoint(startPoint: Vector2) {
    var startPoint = startPoint.tmmCoords.toScreenCoords()
    this.elements.add(MoveTo(startPoint.x, startPoint.y))
}

// This function converts a Path2D object to a JavaFX Path object.
// If the input Path2D is null or has a duration of 0.0, it returns null.
// It creates a new Path object called outPath.
// It calculates the total time based on the durationWithSpeed property of the input Path2D.
// It sets deltaT as totalTime divided by 200.0.
// It initializes the pos variable with the position at time 0.0.
// It initializes t as deltaT.

// It adds the starting point to the outPath using the addStartPoint extension function.

// It enters a loop that runs as long as t is less than or equal to totalTime.
// Inside the loop, it calculates the ease value as t divided by totalTime.
// It updates the pos variable with the position at time t.
// It creates a stroke color based on the ease value and a predefined white color.
// It adds a line segment to the outPath using the addPathLine function.

// Finally, it returns the outPath.
fun Path2D.toLinearFXPath(): Path? {
    if (this == null || this.duration == 0.0) {
        return null
    }
    var outPath = Path()
    var totalTime = this.durationWithSpeed
    var deltaT = totalTime / 200.0
    var pos = this.getPosition(0.0)
    var t = deltaT

    outPath.addStartPoint(pos)

    while (t <= totalTime) {
        val ease = t / totalTime
        pos = this.getPosition(t)
        val stroke = Color(ease * Color.WHITE.red, ease * Color.WHITE.green, ease * Color.WHITE.blue, 1.0)

        addPathLine(outPath, pos)

        t += deltaT
    }

    return outPath
}

fun Double.roundTo(numFractionDigits: Int): Double {
    val factor = 10.0.pow(numFractionDigits.toDouble())
    return (this * factor).roundToInt() / factor
}


