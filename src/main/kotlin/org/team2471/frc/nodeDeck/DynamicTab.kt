package org.team2471.frc.nodeDeck

import `dynamic-functions`.calculateImageDrag
import `dynamic-functions`.scaleImageToHeight
import `dynamic-functions`.toLinearFXPath
import javafx.animation.PathTransition
import javafx.animation.RotateTransition
import javafx.geometry.Pos
import javafx.scene.Group
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Background
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.shape.Path
import javafx.stage.Popup
import javafx.stage.Screen
import javafx.util.Duration
import org.team2471.frc.lib.math.Vector2
import org.team2471.frc.lib.motion_profiling.MotionCurve
import org.team2471.frc.lib.motion_profiling.Path2D
import org.team2471.frc.lib.units.*
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.fieldPane
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.generatedPath
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.generatedPath2D
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.robotImage
import org.team2471.frc.nodeDeck.DynamicPanes.PropertiesPane.propertiesPane
import org.team2471.frc.nodeDeck.DynamicPanes.SettingsPane
import org.team2471.frc.nodeDeck.DynamicPanes.SideBarPane.sidebarScrollPane
import org.team2471.frc.nodeDeck.`dynamic-resources`.Position
import org.team2471.frc.nodeDeck.`dynamic-resources`.screenCoords
import org.team2471.frc.nodeDeck.`dynamic-resources`.tmmCoords
import org.team2471.frc.nodeDeck.`dynamic-resources`.wpiCoords


object DynamicTab: VBox(10.0) {

    var backgroundColor = Color.color(168.0 / 255, 168.0 / 255, 168.0 / 255)

    private var settingsImage = scaleImageToHeight(ImageView(Image("settings-icon.png")), Screen.getPrimary().bounds.height / 40)

    private var settingsButton = Button()

    var tabPane = Pane()

    const val fontSize = 10
    val snapRes = 0

    init {
        println("Dynamic Tab up and running")

        settingsButton.graphic = settingsImage
        settingsButton.background = Background.EMPTY
        settingsButton.layoutX = Screen.getPrimary().bounds.width - 35 - settingsImage.fitWidth
        settingsButton.layoutY = settingsButton.layoutY - 4

        tabPane.background = Background.fill(backgroundColor)

        tabPane.children.addAll(
            fieldPane,
            sidebarScrollPane,
            propertiesPane,
            settingsButton
        )

        DynamicTab.alignment = Pos.TOP_CENTER
        DynamicTab.children.addAll(
            tabPane
        )

        settingsButton.setOnAction {
            SettingsPane.toggleSettings()
        }
    }
}