package `dynamic-functions`

import edu.wpi.first.math.trajectory.Trajectory
import javafx.scene.canvas.GraphicsContext
import javafx.scene.image.ImageView
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import javafx.scene.shape.CubicCurve
import javafx.scene.shape.StrokeType
import org.team2471.frc.lib.math.Vector2
import org.team2471.frc.lib.motion_profiling.Path2D
import org.team2471.frc.lib.units.asMeters
import org.team2471.frc.lib.units.feet
import org.team2471.frc.lib.units.radians
import org.team2471.frc.nodeDeck.DynamicTab
import org.team2471.frc.nodeDeck.DynamicTab.fieldImageScale
import org.team2471.frc.nodeDeck.DynamicTab.robotImage
import org.team2471.frc.nodeDeck.DynamicTab.robotPos
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
    get() = (30 / 81.3) * DynamicTab.sizeInput.text.toDouble()

val yOffset: Double
    get() = (90 / 81.3) * DynamicTab.sizeInput.text.toDouble()
fun calculateImageDrag(imageView: ImageView): ImageView {
    imageView.setOnMouseDragged { event ->
        if (event.button == MouseButton.PRIMARY) {
            imageView.x += event.sceneX - imageView.x - xOffset
            imageView.y += event.sceneY - imageView.y - yOffset
            if (event.isShiftDown) {
                val snappedPos = robotPos.toWpiCoords().round(snapRes).wpiCoords.toScreenCoords(robotImage.fitWidth, fieldImageScale)
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

private fun drawPathLine(gc: GraphicsContext, p1: Vector2, p2: Vector2) {
    val tp1 = p1.tmmCoords.toScreenCoords(robotImage.fitWidth, fieldImageScale)
    val tp2 = p2.tmmCoords.toScreenCoords(robotImage.fitWidth, fieldImageScale)
    gc.lineWidth = 2.0
    gc.strokeLine(tp1.x, tp1.y, tp2.x, tp2.y)
}
fun Path2D.trajectory() : Trajectory {
    return this.generateTrajectory(DynamicTab.maxVelocity.feet.asMeters, DynamicTab.maxAcceleration.feet.asMeters)
}
private fun drawPath(gc: GraphicsContext, path: Path2D?) {
    if (path == null || path.duration == 0.0)
        return
    var totalTime = path.durationWithSpeed
    var deltaT = totalTime / 200.0
    var prevPos = path.getPosition(0.0)
    val maxVelocity = DynamicTab.maxVelocity
    var pos: Vector2 = path.getPosition(0.0)
    var pwPath : Trajectory? = null
    gc.stroke = Color.WHITE
    var t = deltaT
    while (t <= totalTime) {
        val ease = t / totalTime
        pos = path.getPosition(t)
        gc.stroke = Color(ease * Color.WHITE.red, ease * Color.WHITE.green, ease * Color.WHITE.blue, 1.0)
        // center line

        drawPathLine(gc, prevPos, pos)
        prevPos = Vector2(pos.x, pos.y)
        //println(pos.y)
        t += deltaT
    }
}
