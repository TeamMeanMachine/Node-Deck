package org.team2471.frc.nodeDeck.DynamicPanes

import `dynamic-functions`.scaleImageToHeight
import `dynamic-functions`.toLinearFXPath
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import javafx.scene.shape.Path
import javafx.stage.Screen
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

    var generatedPath: Path? = Path()
    val generatedPath2D = Path2D("GoToScore")

    val fieldImageScale = fieldImage.fitHeight / 1462.0
    val ppc = fieldImageScale * (250.0 / 156.0)

    val robotPos: Position
        get() = Vector2(robotImage.x, robotImage.y).screenCoords(robotImage.fitWidth, fieldImageScale)
    init {
        println("FieldPane Online!")

        fieldPane.resize(fieldImage.fitWidth, fieldImage.fitHeight)

        robotImage = scaleImageToHeight(robotImage, (sizeInput.text.toDouble() * ppc))

        generatedPath2D.addEasePoint(0.0, 0.0)
        val p1 = Vector2(0.0, 0.0).wpiCoords.toTmmCoords()
        var p2 = Vector2(50.0, 26.0+ 7.inches.asFeet)
        var p3 = Vector2(54.0 + 1.inches.asFeet, 26.0 + 7.inches.asFeet)

        val rateCurve = MotionCurve()

        rateCurve.setMarkBeginOrEndKeysToZeroSlope(false)
        rateCurve.storeValue(1.0, 3.0)  // distance, rate
        rateCurve.storeValue(8.0, 6.0)  // distance, rate
        generatedPath2D.addVector2(p1)
        generatedPath2D.addVector2(p2)
        generatedPath2D.addVector2(p3)
//        println("Final: $finalHeading  heading: ${heading.asDegrees}")
        val distance = generatedPath2D.length

        val rate = rateCurve.getValue(distance)
        var time = distance / rate
        var finalHeading = 180.0
        generatedPath2D.addEasePoint(time, 1.0)
        generatedPath2D.addHeadingPoint(0.0, 0.0)
        generatedPath2D.addHeadingPoint(time, finalHeading)

        generatedPath = generatedPath2D.toLinearFXPath()

        val robotStartPos = Vector2(0.0, 0.0).tmmCoords.toScreenCoords(robotImage.fitWidth, fieldImageScale)
        robotImage.x = robotStartPos.x; robotImage.y = robotStartPos.y

        fieldImage.accessibleText = "Field Image"
        generatedPath?.accessibleText = "Generated Path"
        robotImage.accessibleText = "Robot Image"

        fieldPane.children.addAll(
            fieldImage,
            generatedPath,
            robotImage
        )

        SideBarPane.sidebarUpdate()
    }
}