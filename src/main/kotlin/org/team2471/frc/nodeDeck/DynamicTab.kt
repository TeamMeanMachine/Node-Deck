package org.team2471.frc.nodeDeck

import `dynamic-functions`.mousePos
import `dynamic-functions`.scaleImageToHeight
import `dynamic-functions`.updateMousePosition
import `dynamic-resources`.asFeet
import `dynamic-resources`.meters
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.stage.Screen
import org.team2471.frc.nodeDeck.`dynamic-resources`.*

object DynamicTab: VBox(10.0) {

    private var fieldImage = scaleImageToHeight(ImageView(Image("field-2023.png")), Screen.getPrimary().bounds.height / 1.5)

    var robotImage = ImageView(Image("robot.png"))

    private var fieldPane = Pane()

    var robotOriginalPos = Vector2(robotImage.layoutX, robotImage.layoutY)
    val fieldImageScale = fieldImage.fitHeight / 1462.0
    val ppc = fieldImageScale * (250.0 / 156.0)
    val fontSize = 30

    val sizeLabel = Label("Robot Size (cm):")
    val sizeInput = TextField("81.3")

    val testButton = Button("Move Bot")

    init {
        println("Dynamic Tab up and running")



        fieldPane.resize(fieldImage.fitWidth, fieldImage.fitHeight)

        robotImage = scaleImageToHeight(robotImage, (sizeInput.text.toDouble() * ppc))


        var robotPos = Vector2(0.0.meters.asFeet, 0.0).tmmCoords.toScreenCoords(robotImage.fitWidth, fieldImageScale)
        robotImage.x = robotPos.x; robotImage.y = robotPos.y

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

        fieldPane.setOnMousePressed { e -> updateMousePosition(e) }
        fieldPane.setOnMouseDragged { e -> updateMousePosition(e) }

        robotImage.setOnDragDetected {
            robotOriginalPos = Vector2(robotImage.layoutX, robotImage.layoutY)
        }

        robotImage.setOnMouseDragged {
            robotImage.layoutX = mousePos.x - robotOriginalPos.x
            robotImage.layoutY = mousePos.y - robotOriginalPos.y
        }

    }
}