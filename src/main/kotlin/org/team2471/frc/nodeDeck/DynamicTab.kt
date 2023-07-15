package org.team2471.frc.nodeDeck

import `dynamic-functions`.scaleImageToHeight
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.stage.Screen

object DynamicTab: VBox(10.0) {

    private var fieldImage = scaleImageToHeight(ImageView(Image("field-2023.png")), Screen.getPrimary().bounds.height / 1.75)
    var robotImage = ImageView(Image("robot.png"))

    private var fieldPane = Pane()

    val ppc = (fieldImage.fitHeight / 1462.0) * (250.0 / 156.0)
    val fontSize = 30

    val sizeLabel = Label("Robot Size (cm):")
    val sizeInput = TextField("81.3")

    val testButton = Button("Move Bot")

    init {
        println("Dynamic Tab up and running")

        fieldPane.resize(fieldImage.image.width, fieldImage.image.width)


        robotImage.x = 0.0; robotImage.y = 0.0
        robotImage = scaleImageToHeight(robotImage, (sizeInput.text.toDouble() * ppc))

        sizeLabel.style = "-fx-font-weight: bold; -fx-font-size: ${fontSize} px"
        sizeInput.style = "-fx-font-size: ${fontSize} px"

        fieldPane.children.addAll(
            fieldImage,
            robotImage
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
            robotImage.layoutY += 5
        }
    }
}