package org.team2471.frc.nodeDeck.DynamicPanes

import `dynamic-functions`.calculateSliderDrag
import javafx.beans.binding.Bindings
import javafx.beans.property.DoubleProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.control.ToggleButton
import javafx.scene.image.ImageView
import javafx.scene.layout.Background
import javafx.scene.layout.Pane
import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import javafx.scene.shape.StrokeLineCap
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.fieldImageScale
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.fieldPane
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.isAnimationPlaying

object PropertiesPane {
    var propertiesPane = Pane()

    var playButton = ToggleButton()
    var playImage = ImageView()

    var timeLabel = Label("0.0/0.0")
    var posLabel = Label("Position: ")
    var xLabel = Label("X: ")
    var yLabel = Label("Y: ")

    var posBox = TextField()

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
            isAnimationPlaying.set(!isAnimationPlaying.get())
        }

        playImage.fitHeight = 60 * fieldImageScale
        playImage.fitWidth = playImage.fitHeight

        timeLabel.layoutX = 10 * fieldImageScale
        timeLabel.layoutY = 80 * fieldImageScale
        timeLabel.style = "-fx-font-weight: bold; -fx-font-size: ${50 * fieldImageScale} px"

        posLabel.layoutX = 180 * fieldImageScale
        posLabel.layoutY = 80 * fieldImageScale
        posLabel.style = "-fx-font-weight: bold; -fx-font-size: ${50 * fieldImageScale} px"

        propertiesPane.children.addAll(
            sliderLine,
            sliderPoint,
            playButton,
            timeLabel,
            posLabel
        )
        calculateSliderDrag(sliderPoint, sliderLine.startX, sliderLine.endX)
    }


}