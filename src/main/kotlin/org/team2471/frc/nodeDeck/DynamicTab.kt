package org.team2471.frc.nodeDeck

import `dynamic-functions`.scaleImageToHeight
import `dynamic-resources`.asFeet
import `dynamic-resources`.meters
import `dynamic-resources`.radians
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseButton
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.stage.Screen
import org.team2471.frc.nodeDeck.`dynamic-resources`.Position
import org.team2471.frc.nodeDeck.`dynamic-resources`.Vector2
import org.team2471.frc.nodeDeck.`dynamic-resources`.tmmCoords
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.round
import kotlin.math.sign


object DynamicTab: VBox(10.0) {

    private var fieldImage = scaleImageToHeight(ImageView(Image("field-2023.png")), Screen.getPrimary().bounds.height / 2)

    private var robotImage = ImageView(Image("robot.png"))
    private var pathStartImage = ImageView(Image("robot.png"))
    private var pathEndImage = ImageView(Image("robot.png"))


    private var fieldPane = Pane()

    private val fieldImageScale = fieldImage.fitHeight / 1462.0
    private val ppc = fieldImageScale * (250.0 / 156.0)
    private const val fontSize = 10

    val robotPos: Position
        get() = Vector2(robotImage.layoutX, robotImage.layoutY).screenCoords(robotImage.fitWidth, fieldImageScale)

    private val sizeLabel = Label("Robot Size (cm):")

    private val sizeInput = TextField("81.3")

    init {
        println("Dynamic Tab up and running")



        fieldPane.resize(fieldImage.fitWidth, fieldImage.fitHeight)

        robotImage = scaleImageToHeight(robotImage, (sizeInput.text.toDouble() * ppc))
        pathStartImage = scaleImageToHeight(pathStartImage, (sizeInput.text.toDouble() * ppc))
        pathEndImage = scaleImageToHeight(pathEndImage, (sizeInput.text.toDouble() * ppc))


        val robotStartPos = Vector2(0.0.meters.asFeet, 0.0).tmmCoords.toScreenCoords(robotImage.fitWidth, fieldImageScale)
        robotImage.x = robotStartPos.x; robotImage.y = robotStartPos.y
        pathStartImage.x = robotStartPos.x + 50.0; pathStartImage.y = robotStartPos.y + 50.0
        pathStartImage.opacity = 0.5
        pathEndImage.x = robotStartPos.x - 50.0; pathEndImage.y = robotStartPos.y - 50.0
        pathEndImage.opacity = 0.5

        sizeLabel.style = "-fx-font-weight: bold; -fx-font-size: $fontSize px"
        sizeInput.style = "-fx-font-size: $fontSize px"

        fieldPane.children.addAll(
            fieldImage,
            robotImage,
            pathStartImage,
            pathEndImage
        )

        DynamicTab.alignment = Pos.TOP_CENTER
        DynamicTab.children.addAll(
            fieldPane,
            sizeLabel,
            sizeInput
        )

        sizeInput.setOnAction {
            robotImage = scaleImageToHeight(robotImage, (sizeInput.text.toDouble() * ppc))
        }

        robotImage.setOnMouseDragged {event ->
            if (event.button == MouseButton.PRIMARY) {
                if (event.isShiftDown) {
                    val changeX = event.sceneX - robotImage.x - 30
                    robotImage.x += (if (abs(changeX / ppc) / 100 >= 1) { round(1 * 100 * ppc) * changeX.sign } else 0) as Double
                    robotImage.y -= -(event.sceneY - robotImage.y - 95.0)
                }
                robotImage.x += event.sceneX - robotImage.x - 30
                robotImage.y -= -(event.sceneY - robotImage.y - 95.0)
            } else {
                robotImage.rotate = -atan2(-(event.sceneY - robotImage.y - 95), event.sceneX - robotImage.x - 30).radians.asDegrees + 90
            }
        }

        pathStartImage.setOnMouseDragged {event ->
            pathStartImage.x = event.x - pathStartImage.fitWidth / 2
            pathStartImage.y = event.y - pathStartImage.fitHeight / 2
        }

        pathEndImage.setOnMouseDragged {event ->
            pathEndImage.x = event.x - pathEndImage.fitWidth / 2
            pathEndImage.y = event.y - pathEndImage.fitHeight / 2
        }
        setOnKeyPressed {event ->
            println(event.code)
        }

    }
}