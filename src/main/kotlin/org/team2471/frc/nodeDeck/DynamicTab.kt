package org.team2471.frc.nodeDeck

import javafx.geometry.Pos
import javafx.scene.canvas.Canvas
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.stage.Screen

fun scaleImageToHeight(image: ImageView, height: Double): ImageView {
    return scaleImage(image, height / image.image.height)
}
fun scaleImage(image: ImageView, scaleFactor: Double): ImageView {
    image.fitWidth = scaleFactor * image.image.width
    image.fitHeight = scaleFactor * image.image.height
    return image
}

object DynamicTab: VBox(10.0) {
    private var fieldImage = ImageView(Image("field-2023.png"))
    var robotImage = ImageView(Image("robot.png"))

    var fieldPane = StackPane()

    val fontSize = 30

    val sizeLabel = Label("Robot Size (cm):")
    val sizeInput = TextField("81.3")

    val testButton = Button("Move Bot")

    init {
        println("Dynamic Tab up and running")

        fieldImage = scaleImageToHeight(fieldImage, Screen.getPrimary().bounds.height / 1.5)

        val ppc = (fieldImage.fitHeight / 1462.0) * (250.0 / 156.0)
        robotImage.x = fieldImage.x; robotImage.y = fieldImage.y
        robotImage = scaleImageToHeight(robotImage, (sizeInput.text.toDouble() * ppc))

        sizeLabel.style = "-fx-font-weight: bold; -fx-font-size: ${fontSize} px"
        sizeInput.style = "-fx-font-size: ${fontSize} px"

        fieldPane.children.addAll(
            fieldImage, robotImage
        )

        DynamicTab.alignment = Pos.TOP_CENTER
        DynamicTab.children.addAll(
            fieldPane,
            sizeLabel,
            sizeInput,
            testButton
        )

        sizeInput.setOnAction {
            robotImage = scaleImageToHeight(robotImage, (sizeInput.text.toDouble() * ppc))
        }

        testButton.setOnAction {
            println("Before: ${robotImage.x}")
            robotImage.x += 5
            println("After: ${robotImage.x}")
        }
    }
}