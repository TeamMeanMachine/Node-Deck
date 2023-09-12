package org.team2471.frc.nodeDeck

import org.team2471.frc.nodeDeck.`dynamic-resources`.scaleImageToHeight
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Background
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.stage.Screen
import org.team2471.frc.nodeDeck.DynamicPanes.FieldPane.fieldPane
import org.team2471.frc.nodeDeck.DynamicPanes.FilePane.fileScrollPane
import org.team2471.frc.nodeDeck.DynamicPanes.PropertiesPane.propertiesPane
import org.team2471.frc.nodeDeck.DynamicPanes.SettingsPane
import org.team2471.frc.nodeDeck.DynamicPanes.SideBarPane.sidebarScrollPane


object DynamicTab: VBox(10.0) {

    var backgroundColor = Color.color(168.0 / 255, 168.0 / 255, 168.0 / 255)

    var settingsImage = scaleImageToHeight(ImageView(Image("settings-icon.png")), Screen.getPrimary().bounds.height / 40)

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
            fileScrollPane,
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