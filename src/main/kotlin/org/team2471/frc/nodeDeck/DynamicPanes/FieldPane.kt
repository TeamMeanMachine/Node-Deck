package org.team2471.frc.nodeDeck.DynamicPanes

import `dynamic-functions`.scaleImageToHeight
import `dynamic-functions`.toLinearFXPath
import javafx.animation.PathTransition
import javafx.animation.RotateTransition
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import javafx.scene.shape.Path
import javafx.stage.Screen
import javafx.util.Duration
import org.team2471.frc.lib.math.Vector2
import org.team2471.frc.lib.motion_profiling.MotionCurve
import org.team2471.frc.lib.motion_profiling.Path2D
import org.team2471.frc.lib.units.asFeet
import org.team2471.frc.lib.units.inches
import org.team2471.frc.nodeDeck.DynamicPanes.SettingsPane.sizeInput
import org.team2471.frc.nodeDeck.DynamicTab
import org.team2471.frc.nodeDeck.`dynamic-resources`.Position
import org.team2471.frc.nodeDeck.`dynamic-resources`.screenCoords
import org.team2471.frc.nodeDeck.`dynamic-resources`.tmmCoords
import org.team2471.frc.nodeDeck.`dynamic-resources`.wpiCoords

object FieldPane {
    var fieldPane = Pane()

    private var fieldImage = scaleImageToHeight(ImageView(Image("field-2023.png")), Screen.getPrimary().bounds.height / 1.75)
    var robotImage = ImageView(Image("robot.png"))

    val transAnimation = PathTransition()
    val rotAnimation = RotateTransition()


    var generatedPath: Path? = Path()
    val generatedPath2D = Path2D("Generated")

    var odometryPath: Path? = Path()
    val odometryPath2D = Path2D("Generated")

    val fieldImageScale = fieldImage.fitHeight / 1462.0
    val ppc = fieldImageScale * (250.0 / 156.0)

    val robotPos: Position
        get() = Vector2(robotImage.x, robotImage.y).screenCoords(robotImage.fitWidth, fieldImageScale)
    init {
        println("FieldPane Online!")

        fieldPane.resize(fieldImage.fitWidth, fieldImage.fitHeight)

        robotImage = scaleImageToHeight(robotImage, (sizeInput.text.toDouble() * ppc))

        generatedPath2D.addEasePoint(0.0, 0.0)
        var p1 = Vector2(0.0, 0.0).wpiCoords.toTmmCoords()
        var p2 = Vector2(50.0, 26.0+ 7.inches.asFeet).wpiCoords.toTmmCoords()
        var p3 = Vector2(54.0 + 1.inches.asFeet, 26.0 + 7.inches.asFeet).wpiCoords.toTmmCoords()

        var rateCurve = MotionCurve()

        rateCurve.setMarkBeginOrEndKeysToZeroSlope(false)
        rateCurve.storeValue(1.0, 3.0)  // distance, rate
        rateCurve.storeValue(8.0, 6.0)  // distance, rate
        generatedPath2D.addVector2(p1)
        generatedPath2D.addVector2(p2)
        generatedPath2D.addVector2(p3)
//        println("Final: $finalHeading  heading: ${heading.asDegrees}")
        var distance = generatedPath2D.length

        var rate = rateCurve.getValue(distance)
        var time = distance / rate
        var finalHeading = 180.0
        generatedPath2D.addEasePoint(time, 1.0)
        generatedPath2D.addHeadingPoint(0.0, 0.0)
        generatedPath2D.addHeadingPoint(time, finalHeading)

        generatedPath = generatedPath2D.toLinearFXPath()



        odometryPath2D.addEasePoint(0.0, 0.0)
        p1 = Vector2(5.0, 5.0).wpiCoords.toTmmCoords()
        p2 = Vector2(0.0, 0.0)
        p3 = Vector2(5.0, 5.0)

        rateCurve = MotionCurve()

        rateCurve.setMarkBeginOrEndKeysToZeroSlope(false)
        rateCurve.storeValue(1.0, 3.0)  // distance, rate
        rateCurve.storeValue(8.0, 6.0)  // distance, rate
        odometryPath2D.addVector2(p1)
        odometryPath2D.addVector2(p2)
        odometryPath2D.addVector2(p3)
//        println("Final: $finalHeading  heading: ${heading.asDegrees}")
        distance = odometryPath2D.length

        rate = rateCurve.getValue(distance)
        time = distance / rate
        finalHeading = 180.0
        odometryPath2D.addEasePoint(time, 1.0)
        odometryPath2D.addHeadingPoint(0.0, 0.0)
        odometryPath2D.addHeadingPoint(time, finalHeading)

        odometryPath = odometryPath2D.toLinearFXPath()

        val robotStartPos = Vector2(0.0, 0.0).tmmCoords.toScreenCoords(robotImage.fitWidth, fieldImageScale)
        robotImage.x = robotStartPos.x; robotImage.y = robotStartPos.y

        fieldImage.accessibleText = "Field Image"
        generatedPath?.accessibleText = "Generated Path"
        odometryPath?.accessibleText = "Odometry Path"
        robotImage.accessibleText = "Robot Image"

        fieldPane.children.addAll(
            fieldImage,
            odometryPath,
            generatedPath,
            robotImage
        )

        SideBarPane.sidebarUpdate()
    }

    fun animateAlongPath(path: Path, path2D: Path2D) {
        transAnimation.path = path
        transAnimation.duration = Duration(path2D.duration * 1000)
        transAnimation.node = robotImage

        rotAnimation.duration = Duration(path2D.duration * 1000)
        rotAnimation.node = robotImage
        rotAnimation.fromAngle = path2D.getAbsoluteHeadingDegreesAt(0.0)
        rotAnimation.toAngle = path2D.getAbsoluteHeadingDegreesAt(path2D.duration)

        rotAnimation.play()
        transAnimation.play()
    }
}