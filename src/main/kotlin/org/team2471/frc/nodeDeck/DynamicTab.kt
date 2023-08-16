package org.team2471.frc.nodeDeck

import `dynamic-functions`.calculateImageDrag
import `dynamic-functions`.scaleImageToHeight
import `dynamic-functions`.toLinearFXPath
import javafx.animation.PathTransition
import javafx.animation.RotateTransition
import javafx.application.Platform
import javafx.geometry.Orientation
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
import javafx.scene.shape.Path
import javafx.stage.Screen
import javafx.util.Duration
import org.team2471.frc.lib.math.Vector2
import org.team2471.frc.lib.motion_profiling.MotionCurve
import org.team2471.frc.lib.motion_profiling.Path2D
import org.team2471.frc.lib.units.*
import org.team2471.frc.nodeDeck.`dynamic-resources`.Position
import org.team2471.frc.nodeDeck.`dynamic-resources`.screenCoords
import org.team2471.frc.nodeDeck.`dynamic-resources`.tmmCoords
import org.team2471.frc.nodeDeck.`dynamic-resources`.wpiCoords
import kotlin.math.cos
import kotlin.math.sin

object DynamicTab: VBox(10.0) {

    private var fieldImage = scaleImageToHeight(ImageView(Image("field-2023.png")), Screen.getPrimary().bounds.height / 1.75)

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

    var linearPath: Path? = Path()
    var cubicPath: Path? = Path()

    val robotPos: Position
        get() = Vector2(robotImage.x, robotImage.y).screenCoords(robotImage.fitWidth, fieldImageScale)

    private val sizeLabel = Label("Robot Size (cm):")

    val sizeInput = TextField("81.3")

    init {
        println("Dynamic Tab up and running")

        fieldPane.resize(fieldImage.fitWidth, fieldImage.fitHeight)

        val testPath = Path2D("GoToScore")
        testPath.addEasePoint(0.0, 0.0)
        val p1 = Vector2(0.0, 0.0).wpiCoords.toTmmCoords()
        var p2 = Vector2(50.0, 26.0+ 7.inches.asFeet)
        var p3 = Vector2(54.0 + 1.inches.asFeet, 26.0 + 7.inches.asFeet)

        val rateCurve = MotionCurve()

        rateCurve.setMarkBeginOrEndKeysToZeroSlope(false)
        rateCurve.storeValue(1.0, 3.0)  // distance, rate
        rateCurve.storeValue(8.0, 6.0)  // distance, rate
        testPath.addVector2(p1)
        testPath.addVector2(p2)
        testPath.addVector2(p3)
//        println("Final: $finalHeading  heading: ${heading.asDegrees}")
        val distance = testPath.length

        val rate = rateCurve.getValue(distance)
        var time = distance / rate
        var finalHeading = 180.0
        testPath.addEasePoint(time, 1.0)
        testPath.addHeadingPoint(0.0, 0.0)
        testPath.addHeadingPoint(time, finalHeading)

        linearPath = testPath.toLinearFXPath()

        robotImage = scaleImageToHeight(robotImage, (sizeInput.text.toDouble() * ppc))
        pathStartImage = scaleImageToHeight(pathStartImage, (sizeInput.text.toDouble() * ppc))
        pathEndImage = scaleImageToHeight(pathEndImage, (sizeInput.text.toDouble() * ppc))


        val robotStartPos = Vector2(0.0, 0.0).tmmCoords.toScreenCoords(robotImage.fitWidth, fieldImageScale)
        robotImage.x = robotStartPos.x; robotImage.y = robotStartPos.y
        pathStartImage.x = robotStartPos.x + 50.0; pathStartImage.y = robotStartPos.y + 50.0
        pathStartImage.opacity = 0.5
        pathEndImage.x = robotStartPos.x - 50.0; pathEndImage.y = robotStartPos.y - 50.0
        pathEndImage.opacity = 0.5

        sizeLabel.style = "-fx-font-weight: bold; -fx-font-size: $fontSize px"
        sizeInput.style = "-fx-font-size: $fontSize px"

        fieldPane.children.addAll(
            fieldImage,
            cubicPath,
            linearPath,
            robotImage
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

        goButton.setOnAction {
            val transAnimation = PathTransition()
            transAnimation.path = linearPath
            transAnimation.duration = Duration(testPath.duration * 1000)
            transAnimation.node = robotImage

            val rotAnimation = RotateTransition()
            rotAnimation.duration = Duration(testPath.duration * 1000)
            rotAnimation.node = robotImage
            rotAnimation.fromAngle = testPath.getAbsoluteHeadingDegreesAt(0.0)
            rotAnimation.toAngle = testPath.getAbsoluteHeadingDegreesAt(testPath.duration)

            rotAnimation.play()
            transAnimation.play()
        }
    }
}