package org.team2471.frc.nodeDeck.dynamicPanes

import chairlib.javafx.scaleImageToHeight
import com.google.gson.Gson
import javafx.collections.FXCollections.observableArrayList
import javafx.collections.ObservableList
import javafx.geometry.Insets
import javafx.scene.control.*
import javafx.scene.effect.ColorAdjust
import javafx.scene.effect.GaussianBlur
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.stage.Popup
import org.team2471.frc.nodeDeck.DynamicTab
import org.team2471.frc.nodeDeck.DynamicTab.backgroundColor
import org.team2471.frc.nodeDeck.DynamicTab.tabPane
import org.team2471.frc.nodeDeck.NodeDeck
import org.team2471.frc.nodeDeck.dynamicPanes.FieldPane.fieldImageScale
import org.team2471.frc.nodeDeck.dynamicPanes.FieldPane.genRobotImage
import org.team2471.frc.nodeDeck.dynamicPanes.FieldPane.odomRobotImage
import org.team2471.frc.nodeDeck.dynamicResources.ppc
import java.io.File
import java.lang.reflect.Type


object SettingsPane {
    var settingsPane = Pane()

    var gson = Gson()
    val settingsFile = File("src/main/resources/prev-settings.json")
    var settings: MutableMap<String, String> = gson.fromJson(settingsFile.readText(), MutableMap::class.java) as MutableMap<String, String>

    val settingsPopup = Popup()


    val settingsBackground = Background(BackgroundFill(Color.color(35.0 / 255, 35.0 / 255, 35.0 / 255), CornerRadii(20 * fieldImageScale), Insets.EMPTY))
    val lightSettingsBackground = Background(BackgroundFill(Color.color(150.0 / 255.0, 150.0 / 255.0, 150.0 / 255.0), CornerRadii(20 * fieldImageScale), Insets.EMPTY))

    private val sizeLabel = Label("Robot Size (cm):")

    val sizeInput = TextField(settings["robotSize"].toString())

    private val differentiationLabel = Label("Gen/Odom Differentiation Method:")

    val differentiationDropdown = ComboBox(observableArrayList("Generated Ghosted", "Odometry Ghosted"))

    init  {
        settingsPane.background = settingsBackground

        sizeLabel.style = "-fx-font-weight: bold; -fx-font-size: ${DynamicTab.fontSize * 1.5} px"
        sizeLabel.textFill = Color.WHITE
        sizeLabel.layoutY += 5 * fieldImageScale

        sizeInput.style = "-fx-font-size: ${DynamicTab.fontSize * 1.5} px"
        sizeInput.background = lightSettingsBackground
        sizeInput.layoutY += 75 * fieldImageScale

        differentiationLabel.style = "-fx-font-weight: bold; -fx-font-size: ${DynamicTab.fontSize * 1.5} px"
        differentiationLabel.textFill = Color.WHITE
        differentiationLabel.layoutY += 200 * fieldImageScale

        differentiationDropdown.style = "-fx-font-size: ${DynamicTab.fontSize * 1.5}"
        differentiationDropdown.background = lightSettingsBackground
        differentiationDropdown.layoutY += (275 * fieldImageScale)
        if (settings["differentiationMethod"].toString() == differentiationDropdown.items.first()) {
            differentiationDropdown.selectionModel.selectFirst()
        } else {
            differentiationDropdown.selectionModel.selectLast()
        }

        genRobotImage.opacity = 0.5
        odomRobotImage.opacity = 1.0

        settingsPane.children.addAll(
            sizeLabel,
            sizeInput,
            differentiationLabel,
            differentiationDropdown
        )

        settingsPopup.content.addAll(
            settingsPane
        )

        sizeInput.setOnAction {
            if (sizeInput.text.toDouble() > 125.0) {
                sizeInput.text = "125.0"
            } else if (sizeInput.text.toDouble() < 20) {
                sizeInput.text = "20"
            }
            settings["robotSize"] = sizeInput.text
            settingsFile.writeText(gson.toJson(settings))
            FieldPane.genRobotImage = scaleImageToHeight(FieldPane.genRobotImage, (sizeInput.text.toDouble() * ppc))
            FieldPane.odomRobotImage = scaleImageToHeight(FieldPane.odomRobotImage, (sizeInput.text.toDouble() * ppc))
        }

        differentiationDropdown.valueProperty().addListener{ _, _, newValue ->
            settings["differentiationMethod"] = newValue
            settingsFile.writeText(gson.toJson(settings))
            if (newValue == "Generated Ghosted") {
                genRobotImage.opacity = 0.5
                odomRobotImage.opacity = 1.0
            } else {
                genRobotImage.opacity = 1.0
                odomRobotImage.opacity = 0.5
            }
        }

        settingsPopup.setOnShowing {
            val adjustment = ColorAdjust()
            val blur = GaussianBlur(10.0)
            adjustment.input = blur
            tabPane.effect = adjustment
        }
        settingsPopup.setOnHiding {
            val adjustment = ColorAdjust()
            val blur = GaussianBlur(0.0)
            adjustment.input = blur
            tabPane.effect = adjustment
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