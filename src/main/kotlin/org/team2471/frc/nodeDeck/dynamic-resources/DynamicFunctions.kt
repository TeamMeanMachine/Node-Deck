package `dynamic-functions`

import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import org.team2471.frc.nodeDeck.`dynamic-resources`.Vector2

val mousePos:Vector2 = Vector2(0.0, 0.0)

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

fun updateMousePosition(e: MouseEvent) {
    mousePos.x = (e.getX() + 2)
    mousePos.y = (e.getY() - 2)
}
