package org.team2471.frc.nodeDeck.dynamicPanes

import javafx.beans.binding.Bindings
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.control.ToggleButton
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.scene.shape.Rectangle
import javafx.stage.Screen
import org.team2471.frc.nodeDeck.dynamicPanes.FieldPane.fieldImageScale
import org.team2471.frc.nodeDeck.dynamicPanes.FieldPane.fieldPane
import org.team2471.frc.nodeDeck.DynamicTab.backgroundColor
import org.team2471.frc.nodeDeck.dynamicPanes.SettingsPane.settingsPopup


object SideBarPane {
    var sidebarScrollPane = ScrollPane()
    var sidebarPane = Pane()

    var showIcon = Image("show-icon.png")
    var hideIcon = Image("hide-icon.png")
    var playIcon = Image("play-icon.png")
    var pauseIcon = Image("pause-icon.png")

    lateinit var selectedNode: Node
    var isOdomAnimationSelected: Boolean? = null
    var isOdomRobotSelected: Boolean? = null

    var prevOdomOpacity = 1.0
    var prevGenOpacity = 1.0


    init {
        sidebarPane.background = Background.fill(backgroundColor)



        sidebarScrollPane.layoutX = fieldPane.width
        sidebarScrollPane.content = sidebarPane
        sidebarScrollPane.maxHeight = fieldPane.height
        sidebarScrollPane.minHeight = 100 * fieldImageScale
        sidebarScrollPane.maxWidth = Screen.getPrimary().bounds.width - fieldPane.width
        sidebarScrollPane.vbarPolicy = ScrollPane.ScrollBarPolicy.AS_NEEDED
        sidebarScrollPane.hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
        sidebarScrollPane.background = Background.EMPTY

    }

    fun sidebarUpdate() {
        var yPos = 0.0
        val yPosIncrement = 125 * fieldImageScale
        for (node in fieldPane.children.asReversed()) {
            if (node.accessibleText != "Field Image") {
                var pane = Pane()

                val isPath = "Path" in node.accessibleText
                val isRobot = "Robot" in node.accessibleText

                var selectButton = Button()

                var toggleButton = ToggleButton()
                var toggleImage = ImageView()

                var label = Label(node.accessibleText)

                var selectedBox = Rectangle(775 * fieldImageScale, yPosIncrement)

                var isOdomPath = if (isPath) node.accessibleText == "Odometry Path" else null
                var isOdomRobot = if (isRobot) node.accessibleText == "Odometry Robot" else null

                selectedBox.arcHeight = 50.0 * fieldImageScale
                selectedBox.arcWidth = 50.0 * fieldImageScale
                selectedBox.fill = backgroundColor.brighter()

//                selectedBox.opacity = 0.0

//                selectButton.background = Background.EMPTY
                selectButton.maxHeight = yPosIncrement
                selectButton.maxWidth = 1000 * fieldImageScale
                selectButton.resize(yPosIncrement, 1000 * fieldImageScale)

                toggleButton.graphic = toggleImage
                toggleImage.image = showIcon

                node.opacityProperty().addListener{_, oldOpacity, newOpacity ->
                    if (newOpacity == 0.0){
                        toggleImage.image = hideIcon
                    } else {
                        toggleImage.image = showIcon
                    }
                }
                toggleButton.background = Background.EMPTY

                toggleButton.setOnAction {
                    if (!SettingsPane.settingsPopup.isShowing) {
                        if (node.opacity > 0.0) {
                            if (isOdomRobot == true) {
                                prevOdomOpacity = node.opacity
                            } else if (isOdomRobot == false) {
                                prevGenOpacity = node.opacity
                            }
                            node.opacity = 0.0
                        } else {
                            if (isOdomRobot == true) {
                                node.opacity = prevOdomOpacity
                            } else if (isOdomRobot == false) {
                                node.opacity = prevGenOpacity
                            } else {
                                node.opacity = 1.0
                            }
                        }
                    }
                }

                toggleImage.fitHeight = (yPosIncrement * 0.75)
                toggleImage.fitWidth = ((yPosIncrement * 0.75) / 100) * 126

                label.layoutX = toggleImage.fitWidth + (75 * fieldImageScale)
                label.layoutY = (toggleImage.fitHeight * 0.12)
                label.style = "-fx-font-weight: bold; -fx-font-size: ${toggleImage.fitHeight * 0.75} px"

                pane.children.addAll(
                    toggleButton,
                    label
                )
                pane.layoutY = yPos


                pane.children.add(0, selectedBox)
//                pane.border = Border(BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT))

                pane.setOnMouseClicked {
                    if (!SettingsPane.settingsPopup.isShowing) {
                        selectNode(node, isOdomPath, isOdomRobot, pane)
                    }
                }


                selectNode(node, isOdomPath, isOdomRobot, pane)

                sidebarPane.children.addAll(
                    pane
                )

                sidebarScrollPane.content = sidebarPane

                yPos += yPosIncrement
            }
        }
    }

    fun selectNode(node: Node, isOdomAnimation: Boolean?, isOdomRobot: Boolean?, nodePane: Pane) {
        selectedNode = node
        isOdomAnimationSelected = isOdomAnimation
        isOdomRobotSelected = isOdomRobot
        for (pane in sidebarPane.children) {
            if (pane is Pane) {
                pane.children[0].opacity = 0.0
            }

            nodePane.children[0].opacity = 1.0
        }
    }
}