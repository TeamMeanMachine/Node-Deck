package org.team2471.frc.nodeDeck.DynamicPanes

import javafx.animation.Animation
import javafx.beans.binding.Bindings
import javafx.beans.binding.Bindings.`when`
import javafx.beans.property.DoubleProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.value.ObservableBooleanValue
import javafx.scene.control.Button
import javafx.scene.control.ToggleButton
import javafx.scene.image.ImageView
import javafx.scene.layout.Background
import javafx.scene.layout.Pane
import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import javafx.scene.shape.StrokeLineCap
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.fieldImage
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.fieldImageScale
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.fieldPane
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.genRotAnimation
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.genTransAnimation
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.isAnimationPlaying
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.odomRotAnimation
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.odomTransAnimation
import org.team2471.frc.nodeDeck.DynamicPanes.SideBarPane.isOdomAnimationSelected
import org.team2471.frc.nodeDeck.DynamicPanes.SideBarPane.selectedNode

object PropertiesPane {
    var propertiesPane = Pane()

    var playButton = ToggleButton()
    var playImage = ImageView()

    var sliderPoint = Circle()

    var sliderLine = Line()

    var sliderPointPos: DoubleProperty = SimpleDoubleProperty(120 * fieldImageScale)

    init {
        propertiesPane.layoutY = fieldPane.height

        sliderPoint.centerY = 40 * fieldImageScale
        sliderPoint.centerXProperty().bind(sliderPointPos)
        sliderPoint.radius = 35 * fieldImageScale

        sliderLine.startX = 120 * fieldImageScale
        sliderLine.startY = 40 * fieldImageScale
        sliderLine.endX = fieldPane.width - (40 * fieldImageScale)
        sliderLine.endY = sliderLine.startY

        sliderLine.strokeWidth = 25 * fieldImageScale
        sliderLine.strokeLineCap = StrokeLineCap.ROUND

        playButton.layoutX = -15.0 * fieldImageScale
        playButton.layoutY = -2.0 * fieldImageScale

        playButton.graphic = playImage
        playImage.imageProperty().bind(
            Bindings
                .`when`(isAnimationPlaying)
                .then(SideBarPane.pauseIcon)
                .otherwise(SideBarPane.playIcon)
        )
        playButton.background = Background.EMPTY

        playButton.setOnAction {
            println(isOdomAnimationSelected)
            if (isAnimationPlaying.get()) {
                if (odomTransAnimation.status.equals(Animation.Status.RUNNING)) {
                    odomRotAnimation.pause()
                    odomTransAnimation.pause()
                } else if (genTransAnimation.status.equals(Animation.Status.RUNNING)) {
                    genRotAnimation.pause()
                    genTransAnimation.pause()
                }
            } else if (isOdomAnimationSelected == true) {
                genRotAnimation.stop()
                genTransAnimation.stop()
                odomRotAnimation.play()
                odomTransAnimation.play()
            } else if (isOdomAnimationSelected == false) {
                odomRotAnimation.stop()
                odomTransAnimation.stop()
                genTransAnimation.play()
                genRotAnimation.play()
            } else if (odomTransAnimation.status.equals(Animation.Status.PAUSED)) {
                odomRotAnimation.play()
                odomTransAnimation.play()
            } else if (genTransAnimation.status.equals(Animation.Status.PAUSED)) {
                genRotAnimation.play()
                genTransAnimation.play()
            }
        }

        playImage.fitHeight = 60 * fieldImageScale
        playImage.fitWidth = playImage.fitHeight

        propertiesPane.children.addAll(
            sliderPoint,
            sliderLine,
            playButton
        )
    }


}