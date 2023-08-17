package org.team2471.frc.nodeDeck.DynamicPanes

import javafx.beans.binding.Bindings
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.control.ToggleButton
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Background
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.animateAlongPath
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.fieldImageScale
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.fieldPane
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.generatedPath
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.generatedPath2D
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.odometryPath
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.odometryPath2D
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.rotAnimation
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.transAnimation
import org.team2471.frc.nodeDeck.DynamicTab
import org.team2471.frc.nodeDeck.DynamicTab.backgroundColor

object SideBarPane {
    var sidebarScrollPane = ScrollPane()
    var sidebarPane = Pane()

    var showIcon = Image("show-icon.png")
    var hideIcon = Image("hide-icon.png")
    var playIcon = Image("play-icon.png")
    var pauseIcon = Image("pause-icon.png")

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
                    playImage.imageProperty().bind(
                        Bindings
                            .`when`(playButton.selectedProperty())
                            .then(pauseIcon)
                            .otherwise(playIcon)
                    )
                    playButton.background = Background.EMPTY

                    playButton.setOnAction {
                        if (transAnimation.path == null) {
                            if (node.accessibleText == "Generated Path") {
                                generatedPath?.let { it1 -> animateAlongPath(it1, generatedPath2D) }
                            } else {
                                odometryPath?.let { it1 -> animateAlongPath(it1, odometryPath2D) }
                            }
                        } else if (transAnimation.path.accessibleText == label.text) {
                            println("Hi")
                            if (playButton.isSelected) {
                                transAnimation.play()
                                rotAnimation.play()
                            } else {
                                transAnimation.pause()
                                rotAnimation.pause()
                            }
                        } else {
                            if (node.accessibleText == "Generated Path") {
                                generatedPath?.let { it1 -> animateAlongPath(it1, generatedPath2D) }
                            } else {
                                odometryPath?.let { it1 -> animateAlongPath(it1, odometryPath2D) }
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