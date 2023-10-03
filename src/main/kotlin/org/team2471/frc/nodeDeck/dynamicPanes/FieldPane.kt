package org.team2471.frc.nodeDeck.dynamicPanes

import chairlib.javafx.autoScaleImage
import chairlib.javafx.scaleImageToHeight
import org.team2471.frc.nodeDeck.dynamicResources.roundTo
import org.team2471.frc.nodeDeck.dynamicResources.toLinearFXPath
import javafx.animation.PathTransition
import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import javafx.scene.shape.Path
import javafx.stage.Screen
import javafx.util.Duration
import org.team2471.frc.lib.math.Vector2
import org.team2471.frc.lib.motion_profiling.MotionCurve
import org.team2471.frc.lib.motion_profiling.Path2D
import org.team2471.frc.lib.units.asMeters
import org.team2471.frc.lib.units.feet
import org.team2471.frc.lib.units.inches
import org.team2471.frc.nodeDeck.dynamicPanes.PropertiesPane.posLabel
import org.team2471.frc.nodeDeck.dynamicPanes.PropertiesPane.sliderLine
import org.team2471.frc.nodeDeck.dynamicPanes.PropertiesPane.sliderPointPos
import org.team2471.frc.nodeDeck.dynamicPanes.PropertiesPane.timeLabel
import org.team2471.frc.nodeDeck.dynamicPanes.SettingsPane.sizeInput
import org.team2471.frc.nodeDeck.dynamicResources.*


object FieldPane {
    var fieldPane = Pane()

    var fieldImage =
        autoScaleImage(ImageView(Image("field-2023.png")), Screen.getPrimary().bounds.height / 1.75, Screen.getPrimary().bounds.width * 0.75)

    var genRobotImage = ImageView(Image("robot.png"))
    var odomRobotImage = ImageView(Image("robot.png"))

    val genTransAnimation = PathTransition()

    val odomTransAnimation = PathTransition()


    var generatedPath: Path? = Path()
    var generatedPath2D = Path2D("Generated")

    var odometryPath: Path? = Path()
    var odometryPath2D = Path2D("Generated")

    val fieldImageScale = fieldImage.fitHeight / 1462.0

    val genRobotPos: Position
        get() = Vector2(genRobotImage.x, genRobotImage.y).screenCoords(genRobotImage.fitWidth, fieldImageScale)

    val odomRobotPos: Position
        get() = Vector2(odomRobotImage.x, odomRobotImage.y).screenCoords(odomRobotImage.fitWidth, fieldImageScale)


    var isAnimationPlaying: BooleanProperty = SimpleBooleanProperty(false)

    init {
        println("FieldPane Online!")



        fieldPane.resize(fieldImage.fitWidth, fieldImage.fitHeight)

        genRobotImage = scaleImageToHeight(genRobotImage, (sizeInput.text.toDouble() * ppc))
        odomRobotImage = scaleImageToHeight(odomRobotImage, (sizeInput.text.toDouble() * ppc))

        generatedPath2D.addEasePoint(0.0, 0.0)
        var p1 = Vector2(0.0, 0.0).wpiCoords.toTmmCoords()
        var p2 = Vector2(50.0.feet.asMeters, 26.0.feet.asMeters + 7.inches.asMeters).wpiCoords.toTmmCoords()
        var p3 = Vector2(54.0.feet.asMeters + 1.inches.asMeters, 26.0.feet.asMeters + 7.inches.asMeters).wpiCoords.toTmmCoords()

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
        p1 = Vector2(54.0.feet.asMeters + 1.inches.asMeters, 26.0.feet.asMeters + 7.inches.asMeters).wpiCoords.toTmmCoords()
        p2 = Vector2(50.0.feet.asMeters, 26.0.feet.asMeters + 7.inches.asMeters).wpiCoords.toTmmCoords()
        p3 = Vector2(0.0, 0.0).wpiCoords.toTmmCoords()
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

        updateGenAnimation()
        updateOdomAnimation()

        isAnimationPlaying.addListener { _, _, new ->
            if (new) {
                genTransAnimation.play()
                odomTransAnimation.play()
            } else {
                genTransAnimation.pause()
                odomTransAnimation.pause()
            }
        }

        genTransAnimation.currentTimeProperty().addListener { _, _, newValue ->
            sliderPointPos.set(
                ((genTransAnimation.currentTime.toMillis() / genTransAnimation.duration.toMillis()) * (sliderLine.endX - sliderLine.startX)) + (120 * fieldImageScale)
            )
            timeLabel.text = "${genTransAnimation.currentTime.toSeconds().roundTo(1)}/${genTransAnimation.duration.toSeconds().roundTo(1)}"


            posLabel.text = "Position: X: ${
                if (SideBarPane.isOdomRobotSelected == true) odometryPath2D.getPosition(newValue.toSeconds()).x.roundTo(1) else if (SideBarPane.isOdomRobotSelected == false) generatedPath2D.getPosition(newValue.toSeconds()).x.roundTo(1) else 0.0
            } ft Y: ${
                if (SideBarPane.isOdomRobotSelected == true) odometryPath2D.getPosition(newValue.toSeconds()).y.roundTo(1) else if (SideBarPane.isOdomRobotSelected == false) generatedPath2D.getPosition(newValue.toSeconds()).y.roundTo(1) else 0.0
            } ft"

            genRobotImage.rotate = generatedPath2D.getAbsoluteHeadingDegreesAt(newValue.toSeconds())

            odomRobotImage.rotate = odometryPath2D.getAbsoluteHeadingDegreesAt(newValue.toSeconds())
            println(odometryPath2D.getAbsoluteHeadingDegreesAt(newValue.toSeconds()))
        }



        val genRobotStartPos = Vector2(0.0, 0.0).tmmCoords.toScreenCoords(genRobotImage.fitWidth)
        genRobotImage.x = genRobotStartPos.x; genRobotImage.y = genRobotStartPos.y

        val odomRobotStartPos = Vector2(0.0, 0.0).tmmCoords.toScreenCoords(odomRobotImage.fitWidth)
        odomRobotImage.x = odomRobotStartPos.x; odomRobotImage.y = odomRobotStartPos.y

        fieldImage.accessibleText = "Field Image"
        generatedPath?.accessibleText = "Generated Path"
        odometryPath?.accessibleText = "Odometry Path"
        genRobotImage.accessibleText = "Generated Robot"
        odomRobotImage.accessibleText = "Odometry Robot"

        updateFieldPane()


        SideBarPane.sidebarUpdate()

        FilePane.filePaneUpdate()
    }

    fun updateFieldPane() {
        fieldPane.children.setAll(
            fieldImage,
            odometryPath,
            generatedPath,
            genRobotImage,
            odomRobotImage
        )
    }

    fun updateGenAnimation() {
        genTransAnimation.path = generatedPath
        genTransAnimation.duration = Duration(generatedPath2D.duration * 1000)
        genTransAnimation.node = genRobotImage



    }

    fun updateOdomAnimation() {
        odomTransAnimation.path = odometryPath
        odomTransAnimation.duration = Duration(odometryPath2D.duration * 1000)
        odomTransAnimation.node = odomRobotImage




    }
}