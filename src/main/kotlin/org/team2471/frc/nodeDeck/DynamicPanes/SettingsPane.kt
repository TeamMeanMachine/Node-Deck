package org.team2471.frc.nodeDeck.DynamicPanes

import `dynamic-functions`.scaleImageToHeight
import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.stage.Popup
import javafx.stage.Screen
import org.team2471.frc.nodeDeck.DynamicTab
import org.team2471.frc.nodeDeck.DynamicTab.backgroundColor
import org.team2471.frc.nodeDeck.NodeDeck


object SettingsPane {
    var settingsPane = Pane()

    val settingsPopup = Popup()


    var settingsBackground = Background(BackgroundFill(Color.color(35.0 / 255, 35.0 / 255, 35.0 / 255), CornerRadii.EMPTY, Insets.EMPTY))

    private val sizeLabel = Label("Robot Size (cm):")

    val sizeInput = TextField("81.3")



    init  {
        settingsPane.background = Background.fill(backgroundColor)

        sizeLabel.style = "-fx-font-weight: bold; -fx-font-size: ${DynamicTab.fontSize} px"
        sizeLabel.textFill = Color.WHITE

        sizeInput.style = "-fx-font-size: ${DynamicTab.fontSize} px"
        sizeInput.layoutY = sizeInput.layoutY + 20

        settingsPane.children.addAll(
            sizeLabel,
            sizeInput
        )

        settingsPopup.content.addAll(
            settingsPane
        )

        sizeInput.setOnAction {
            if (sizeInput.text.toDouble() > 125.0) {
                sizeInput.text = "125.0"
            }
            FieldPane.robotImage = scaleImageToHeight(FieldPane.robotImage, (sizeInput.text.toDouble() * FieldPane.ppc))
        }
    }

    fun toggleSettings() {
        if (!settingsPopup.isShowing) {
            settingsPopup.show(NodeDeck.stage)
        } else{
            settingsPopup.hide()
        }
    }
}