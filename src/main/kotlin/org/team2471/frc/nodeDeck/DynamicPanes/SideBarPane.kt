package org.team2471.frc.nodeDeck.DynamicPanes

import javafx.animation.Animation
import javafx.beans.Observable
import javafx.beans.binding.Bindings
import javafx.beans.value.ObservableBooleanValue
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.control.ToggleButton
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Background
import javafx.scene.layout.Pane
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.fieldImageScale
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.fieldPane
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.genRotAnimation
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.genTransAnimation
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.generatedPath
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.generatedPath2D
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.odomRotAnimation
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.odomTransAnimation
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.odometryPath
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.odometryPath2D
import org.team2471.frc.nodeDeck.DynamicTab.backgroundColor

object SideBarPane {
    var sidebarScrollPane = ScrollPane()
    var sidebarPane = Pane()

    var showIcon = Image("show-icon.png")
    var hideIcon = Image("hide-icon.png")
    var playIcon = Image("play-icon.png")
    var pauseIcon = Image("pause-icon.png")

    lateinit var isAnimLabel: ObservableBooleanValue

    init {
        sidebarPane.background = Background.fill(backgroundColor)

        sidebarScrollPane.background = Background.EMPTY

        sidebarScrollPane.layoutX = fieldPane.width
        sidebarScrollPane.content = sidebarPane
        sidebarScrollPane.isFitToWidth = true

    }

    fun sidebarUpdate() {
        var yPos = 0.0
        val yPosIncrement = 100 * fieldImageScale
        for (node in fieldPane.children.asReversed()) {
            if (node.accessibleText != "Field Image") {
                var pane = Pane()

                val isPath = "Path" in node.accessibleText

                var toggleButton = ToggleButton()
                var toggleImage = ImageView()

                var label = Label(node.accessibleText)

                var playButton = ToggleButton()
                var playImage = ImageView()

                var isOdom = node.accessibleText == "Odometry Path"

                toggleButton.graphic = toggleImage

                toggleImage.imageProperty().bind(
                    Bindings
                        .`when`(toggleButton.selectedProperty())
                        .then(hideIcon)
                        .otherwise(showIcon)
                )
                toggleButton.background = Background.EMPTY

                toggleButton.setOnAction {
                    if (node.opacity > 0.0) {
                        node.opacity = 0.0
                    } else {
                        node.opacity = 100.0
                    }
                }

                toggleImage.fitHeight = (yPosIncrement * 0.75)
                toggleImage.fitWidth = ((yPosIncrement * 0.75) / 100) * 126

                label.layoutX = toggleImage.fitWidth + (75 * fieldImageScale)
                label.style = "-fx-font-weight: bold; -fx-font-size: ${toggleImage.fitHeight * 0.75} px"

                if (isPath) {
                    playButton.graphic = playImage
                    if (isOdom) {
                        playImage.imageProperty().bind(
                            Bindings
                                .`when`(odomTransAnimation.statusProperty().isEqualTo(Animation.Status.RUNNING))
                                .then(pauseIcon)
                                .otherwise(playIcon)
                        )
                    } else {
                        playImage.imageProperty().bind(
                            Bindings
                                .`when`(genTransAnimation.statusProperty().isEqualTo(Animation.Status.RUNNING))
                                .then(pauseIcon)
                                .otherwise(playIcon)
                        )
                    }
                    playButton.background = Background.EMPTY

                    playButton.setOnAction {
                        if (isOdom) {
                            println(odomTransAnimation.status == Animation.Status.STOPPED)
                            if (odomTransAnimation.status == Animation.Status.RUNNING) {
                                odomTransAnimation.pause()
                                odomRotAnimation.pause()
                            } else if (odomTransAnimation.status == Animation.Status.PAUSED) {
                                odomTransAnimation.play()
                                odomRotAnimation.play()
                            } else if (odomTransAnimation.status == Animation.Status.STOPPED) {
                                if (genTransAnimation.status == Animation.Status.RUNNING) {
                                    genTransAnimation.stop()
                                }
                                odomTransAnimation.play()
                                odomRotAnimation.play()
                            }
                        } else {
                            if (genTransAnimation.status == Animation.Status.RUNNING) {
                                genTransAnimation.pause()
                                genRotAnimation.pause()
                            } else if (genTransAnimation.status == Animation.Status.PAUSED) {
                                genTransAnimation.play()
                                genRotAnimation.play()
                            } else if (genTransAnimation.status == Animation.Status.STOPPED) {
                                if (odomTransAnimation.status == Animation.Status.RUNNING) {
                                    odomTransAnimation.stop()
                                }
                                genTransAnimation.playFromStart()
                                genRotAnimation.playFromStart()
                            }
                        }
                    }

                    playImage.fitHeight = (yPosIncrement * 0.75)
                    playImage.fitWidth = playImage.fitHeight

                    playButton.layoutX = label.layoutX + (500 * fieldImageScale)

                    pane.children.add(playButton)

                }

                pane.children.addAll(
                    toggleButton,
                    label
                    )
                pane.layoutY = yPos

                sidebarPane.children.addAll(
                    pane
                )

                yPos += yPosIncrement
            }
        }
    }
}