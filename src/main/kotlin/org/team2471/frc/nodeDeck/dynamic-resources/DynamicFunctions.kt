package `dynamic-functions`

import javafx.scene.image.ImageView

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
