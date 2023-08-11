package org.team2471.frc.nodeDeck

import `dynamic-functions`.calculateImageDrag
import `dynamic-functions`.scaleImageToHeight
import javafx.animation.PathTransition
import javafx.application.Platform
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.shape.CubicCurve
import javafx.stage.Screen
import javafx.util.Duration
import org.team2471.frc.lib.math.Vector2
import org.team2471.frc.lib.units.asFeet
import org.team2471.frc.lib.units.asRadians
import org.team2471.frc.lib.units.degrees
import org.team2471.frc.lib.units.meters
import org.team2471.frc.nodeDeck.`dynamic-resources`.Position
import org.team2471.frc.nodeDeck.`dynamic-resources`.screenCoords
import org.team2471.frc.nodeDeck.`dynamic-resources`.tmmCoords
import kotlin.math.cos
import kotlin.math.sin

fun updatePath() {
    DynamicTab.path.startX = DynamicTab.pathStartImage.x + DynamicTab.pathStartImage.fitWidth / 2
    DynamicTab.path.startY = DynamicTab.pathStartImage.y + DynamicTab.pathStartImage.fitWidth / 2
    DynamicTab.path.controlX1 = DynamicTab.pathStartImage.x + DynamicTab.pathStartImage.fitWidth / 2
    DynamicTab.path.controlY1 = DynamicTab.pathStartImage.y + DynamicTab.pathStartImage.fitWidth / 2
    DynamicTab.path.controlX2 = DynamicTab.pathEndImage.x + DynamicTab.pathEndImage.fitWidth / 2 - (25 * cos((DynamicTab.pathEndImage.rotate - 90.0).degrees.asRadians))
    DynamicTab.path.controlY2 = DynamicTab.pathEndImage.y + DynamicTab.pathEndImage.fitWidth / 2 - (25 * sin((DynamicTab.pathEndImage.rotate - 90.0).degrees.asRadians))
    DynamicTab.path.endX = DynamicTab.pathEndImage.x + DynamicTab.pathEndImage.fitWidth / 2
    DynamicTab.path.endY = DynamicTab.pathEndImage.y + DynamicTab.pathEndImage.fitWidth / 2

    DynamicTab.path.fill = Color(0.0, 0.0, 0.0, 0.0)
    DynamicTab.path.stroke = Color.BLACK
    DynamicTab.path.strokeWidth = 4.0
}

object DynamicTab: VBox(10.0) {

    private var fieldImage = scaleImageToHeight(ImageView(Image("field-2023.png")), Screen.getPrimary().bounds.height / 2)

    var robotImage = ImageView(Image("robot.png"))
    var pathStartImage = ImageView(Image("robot.png"))
    var pathEndImage = ImageView(Image("robot.png"))

    private var goButton = Button("GO!")


    var fieldPane = Pane()

    val fieldImageScale = fieldImage.fitHeight / 1462.0
    val ppc = fieldImageScale * (250.0 / 156.0)
    private const val fontSize = 10
    val snapRes = 0
    const val curvature = 25
    const val maxVelocity = 20.0
    const val maxAcceleration = 20.0

    var path:CubicCurve = CubicCurve(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)

    val robotPos: Position
        get() = Vector2(robotImage.x, robotImage.y).screenCoords(robotImage.fitWidth, fieldImageScale)

    private val sizeLabel = Label("Robot Size (cm):")

    val sizeInput = TextField("81.3")

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
            path,
            robotImage,
            pathStartImage,
            pathEndImage
        )

        DynamicTab.alignment = Pos.TOP_CENTER
        DynamicTab.children.addAll(
            fieldPane,
            sizeLabel,
            sizeInput,
            goButton
        )

        sizeInput.setOnAction {
            if (sizeInput.text.toDouble() > 125.0) {
                sizeInput.text = "125.0"
            }
            robotImage = scaleImageToHeight(robotImage, (sizeInput.text.toDouble() * ppc))
            pathStartImage = scaleImageToHeight(pathStartImage, (sizeInput.text.toDouble() * ppc))
            pathEndImage = scaleImageToHeight(pathEndImage, (sizeInput.text.toDouble() * ppc))
        }

        robotImage = calculateImageDrag(robotImage)

        pathStartImage = calculateImageDrag(pathStartImage)

        pathEndImage = calculateImageDrag(pathEndImage)

        fieldPane.setOnMouseMoved {
            updatePath()
        }

        fieldPane.setOnMouseDragged {
            updatePath()
        }

        goButton.setOnAction {
            val animation = PathTransition()
            animation.path = path
            animation.duration = Duration(1000.0)
            animation.node = robotImage
            animation.play()
        }
    }
}