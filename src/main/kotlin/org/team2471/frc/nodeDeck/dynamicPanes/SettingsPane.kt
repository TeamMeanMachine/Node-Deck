package org.team2471.frc.nodeDeck.dynamicPanes

import chairlib.javafx.scaleImageToHeight
import com.google.gson.Gson
import javafx.collections.FXCollections.observableArrayList
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
import org.team2471.frc.nodeDeck.DynamicTab.tabPane
import org.team2471.frc.nodeDeck.NodeDeck
import org.team2471.frc.nodeDeck.dynamicPanes.FieldPane.fieldImageScale
import org.team2471.frc.nodeDeck.dynamicPanes.FieldPane.genRobotImage
import org.team2471.frc.nodeDeck.dynamicPanes.FieldPane.odomRobotImage
import org.team2471.frc.nodeDeck.dynamicResources.ppc
import java.io.File


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

    private val coordTypeLabel = Label("Coordinate Type:")
    val coordTypeDropdown = ComboBox(observableArrayList("TMM", "WPI"))

    private val unitLabel = Label("Unit:")
    val unitDropdown = ComboBox(observableArrayList("Feet", "Inches", "Meters", "Centimeters"))

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

        coordTypeLabel.style = "-fx-font-weight: bold; -fx-font-size: ${DynamicTab.fontSize * 1.5} px"
        coordTypeLabel.textFill = Color.WHITE
        coordTypeLabel.layoutY += 200 * fieldImageScale

        coordTypeDropdown.style = "-fx-font-size: ${DynamicTab.fontSize * 1.5}"
        coordTypeDropdown.background = lightSettingsBackground
        coordTypeDropdown.layoutY += (275 * fieldImageScale)
        if (settings["coordinateType"].toString() == coordTypeDropdown.items.first()) {
            coordTypeDropdown.selectionModel.selectFirst()
        } else {
            coordTypeDropdown.selectionModel.selectLast()
        }

        unitLabel.style = "-fx-font-weight: bold; -fx-font-size: ${DynamicTab.fontSize * 1.5} px"
        unitLabel.textFill = Color.WHITE
        unitLabel.layoutY += 400 * fieldImageScale

        unitDropdown.style = "-fx-font-size: ${DynamicTab.fontSize * 1.5}"
        unitDropdown.background = lightSettingsBackground
        unitDropdown.layoutY += (475 * fieldImageScale)
        unitDropdown.selectionModel.select(settings["coordinateUnits"].toString())

        differentiationLabel.style = "-fx-font-weight: bold; -fx-font-size: ${DynamicTab.fontSize * 1.5} px"
        differentiationLabel.textFill = Color.WHITE
        differentiationLabel.layoutY += 600 * fieldImageScale

        differentiationDropdown.style = "-fx-font-size: ${DynamicTab.fontSize * 1.5}"
        differentiationDropdown.background = lightSettingsBackground
        differentiationDropdown.layoutY += (675 * fieldImageScale)
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
            coordTypeLabel,
            coordTypeDropdown,
            unitLabel,
            unitDropdown,
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

        coordTypeDropdown.valueProperty().addListener { _, _, newValue ->
            settings["coordinateType"] = newValue
            settingsFile.writeText(gson.toJson(settings))
            setPositionLabel(FieldPane.newValue.duration)
        }

        unitDropdown.valueProperty().addListener { _, _, newValue ->
            settings["coordinateUnits"] = newValue
            settingsFile.writeText(gson.toJson(settings))
            setPositionLabel(FieldPane.newValue.duration)
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