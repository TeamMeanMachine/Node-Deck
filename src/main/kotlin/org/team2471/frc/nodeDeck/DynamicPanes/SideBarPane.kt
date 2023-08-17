package org.team2471.frc.nodeDeck.DynamicPanes

import javafx.beans.binding.Bindings
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.control.ToggleButton
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.fieldImageScale
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.fieldPane
import org.team2471.frc.nodeDeck.DynamicTab

object SideBarPane {
    var sidebarScrollPane = ScrollPane()
    var sidebarPane = Pane()

    var showIcon = Image("show-icon.png")
    var hideIcon = Image("hide-icon.png")

    init {
        sidebarScrollPane.layoutX = fieldPane.width
        sidebarScrollPane.content = sidebarPane
        sidebarScrollPane.isFitToWidth = true

    }

    fun sidebarUpdate() {
        var yPos = 0.0
        val yPosIncrement = 150 * fieldImageScale
        for (node in fieldPane.children.asReversed()) {
            if (node.accessibleText != "Field Image") {
                var pane = Pane()

                var toggleButton = ToggleButton()
                var toggleImage = ImageView()

                var label = Label(node.accessibleText)

                toggleButton.graphic = toggleImage
                toggleImage.imageProperty().bind(
                    Bindings
                        .`when`(toggleButton.selectedProperty())
                        .then(hideIcon)
                        .otherwise(showIcon)
                )

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
                label.layoutY = yPos
                label.style = "-fx-font-weight: bold; -fx-font-size: ${toggleImage.fitHeight * 0.75} px"

                pane.children.add(toggleButton)
                pane.layoutY = yPos

                sidebarPane.children.addAll(
                    pane,
                    label
                )

                yPos += yPosIncrement
            }
        }
    }
}