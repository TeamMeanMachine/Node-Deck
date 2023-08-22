package org.team2471.frc.nodeDeck.DynamicPanes

import javafx.animation.Animation
import javafx.beans.binding.Bindings
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.control.ToggleButton
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.scene.paint.Color
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.fieldImageScale
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.fieldPane
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.genRotAnimation
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.genTransAnimation
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.odomRotAnimation
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.odomTransAnimation
import org.team2471.frc.nodeDeck.DynamicTab.backgroundColor


object SideBarPane {
    var sidebarScrollPane = ScrollPane()
    var sidebarPane = Pane()

    var showIcon = Image("show-icon.png")
    var hideIcon = Image("hide-icon.png")
    var playIcon = Image("play-icon.png")
    var pauseIcon = Image("pause-icon.png")

    lateinit var selectedNode: Node
    var isOdomAnimationSelected: Boolean? = null


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

                var selectButton = Button()

                var toggleButton = ToggleButton()
                var toggleImage = ImageView()

                var label = Label(node.accessibleText)

                var playButton = ToggleButton()
                var playImage = ImageView()

                var isOdom = node.accessibleText == "Odometry Path"

//                selectButton.background = Background.EMPTY
                selectButton.maxHeight = yPosIncrement
                selectButton.maxWidth = 1000 * fieldImageScale
                selectButton.resize(yPosIncrement, 1000 * fieldImageScale)

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
                label.layoutY = (toggleImage.fitHeight * 0.12)
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

                pane.border = Border(BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT))

                pane.setOnMouseClicked {
                   selectNode(node, if (isPath) isOdom else null, pane)
                }

                if (!isOdom) {
                    selectNode(node, if (isPath) isOdom else null, pane)
                }
                sidebarPane.children.addAll(
                    pane
                )

                yPos += yPosIncrement
            }
        }
    }

    fun selectNode(node: Node, isOdomAnimation: Boolean?, nodePane: Pane) {
        selectedNode = node
        isOdomAnimationSelected = isOdomAnimation
        for (pane in sidebarPane.children) {
            if (pane is Pane) {
                pane.background = Background.fill(backgroundColor)
            }

            nodePane.background = Background.fill(backgroundColor.brighter())
        }
    }
}