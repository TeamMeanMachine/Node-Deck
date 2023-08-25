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
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.robotImage
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.robotPos
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

fun scaleImageToHeight(image: ImageView, height: Double): ImageView {
    return scaleImage(image, height / image.image.height)
}

fun scaleImageToWidth(image: ImageView, width: Double): ImageView {
    return scaleImage(image, width / image.image.width)
}
fun scaleImage(image: ImageView, scaleFactor: Double): ImageView {
    image.fitWidth = scaleFactor * image.image.width
    image.fitHeight = scaleFactor * image.image.height
    return image
}

val xOffset: Double
    get() = (30 / 81.3) * sizeInput.text.toDouble()

val yOffset: Double
    get() = (90 / 81.3) * sizeInput.text.toDouble()
fun calculateImageDrag(imageView: ImageView): ImageView {
    imageView.setOnMouseDragged { event ->
        if (event.button == MouseButton.PRIMARY) {
            imageView.x += event.sceneX - imageView.x - xOffset
            imageView.y += event.sceneY - imageView.y - yOffset
            if (event.isShiftDown) {
                val snappedPos = robotPos.toWpiCoords().round(snapRes).wpiCoords.toScreenCoords(robotImage.fitWidth)
                imageView.x = snappedPos.x
                imageView.y = snappedPos.y
            }
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

fun calculateSliderDrag(point: Circle, minX: Double, maxX: Double): Circle {
    point.setOnMouseDragged { event ->
        if (!isAnimationPlaying.get()) {
            if (event.button == MouseButton.PRIMARY) {
                sliderPointPos.set((sliderPointPos.get() + event.sceneX - point.centerX - point.radius).coerceIn(minX, maxX))
                if (isOdomAnimationSelected == true) {
                    odomTransAnimation.play()
                    odomTransAnimation.pause()
                    odomTransAnimation.jumpTo(Duration(((sliderPointPos.get() - minX) / (maxX - minX))  * odomTransAnimation.duration.toMillis()))

                } else if (isOdomAnimationSelected == false) {
                    genTransAnimation.play()
                    genTransAnimation.pause()
                    genTransAnimation.jumpTo(Duration(((sliderPointPos.get() - minX) / (maxX - minX))  * genTransAnimation.duration.toMillis()))

                }
            }
        }
    }
    return point
}

private fun addPathLine(path: Path, point: Vector2, stroke: Color = Color.BLACK) {
    val tp = point.tmmCoords.toScreenCoords()
    path.strokeWidth = 2.0
    var line = LineTo(tp.x, tp.y)
    path.elements.add(line)
}

fun Path.addStartPoint(startPoint: Vector2) {
    var startPoint = startPoint.tmmCoords.toScreenCoords()
    this.elements.add(MoveTo(startPoint.x, startPoint.y))
}

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


