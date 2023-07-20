package `dynamic-functions`

import `dynamic-resources`.radians
import javafx.scene.image.ImageView
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import javafx.scene.shape.CubicCurve
import javafx.scene.shape.StrokeType
import org.team2471.frc.nodeDeck.DynamicTab
import org.team2471.frc.nodeDeck.DynamicTab.fieldImageScale
import org.team2471.frc.nodeDeck.DynamicTab.robotImage
import org.team2471.frc.nodeDeck.DynamicTab.robotPos
import org.team2471.frc.nodeDeck.DynamicTab.snapRes
import org.team2471.frc.nodeDeck.`dynamic-resources`.Position
import org.team2471.frc.nodeDeck.`dynamic-resources`.Vector2
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
