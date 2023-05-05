package org.team2471.frc.nodeDeck

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import org.team2471.frc.nodeDeck.NTClient.demoReachLimit

object DemoTab: VBox(10.0) {
    val demoSpeedInput = TextField("${NTClient.demoSpeedEntry.getDouble(1.0)}")
    val reachLimitInput = TextField("$demoReachLimit")
    val demoSpeedLabel = Label("Demo Speed", demoSpeedInput)
    val reachLimitLabel = Label("Arm Max Reach", reachLimitInput)
    val demoSpeedOneButton = Button("1.0")
    val reachDefaultButton = Button("47.0")

    const val fontSize = 50

    init {
        demoSpeedLabel.style = "-fx-font-size: $fontSize px"
        demoSpeedOneButton.style = "-fx-font-size: ${fontSize - 20.0} px"
        reachLimitLabel.style = "-fx-font-size: $fontSize px"
        reachDefaultButton.style = "-fx-font-size: ${fontSize - 20.0} px"

        demoSpeedInput.setPrefSize(140.0, 50.0)
        demoSpeedInput.setOnAction {
            try {
                if (demoSpeedInput.text.toDouble() > 1.0) {
                    throw Exception("Demo Speed too large")
                } else {
                    NTClient.demoSpeedEntry.setDouble(demoSpeedInput.text.toDouble())
                }
            } catch (ex:Exception) {
                demoSpeedInput.text = NTClient.demoSpeedEntry.getDouble(1.0).toString()
                println(ex)
            }
        }
        demoSpeedOneButton.setOnAction {
            demoSpeedInput.text = "1.0"
            NTClient.demoSpeedEntry.setDouble(1.0)
        }
        reachLimitInput.setPrefSize(160.0, 50.0)
        reachLimitInput.setOnAction {
            try {
                demoReachLimit = reachLimitInput.text.toDouble()
            } catch (ex:Exception) {
                reachLimitInput.text = demoReachLimit.toString()
                println(ex)
            }
        }
        reachDefaultButton.setOnAction {
            reachLimitInput.text = "47.0"
            demoReachLimit = 47.0
        }
        DemoTab.alignment = Pos.TOP_CENTER
        DemoTab.children.addAll(demoSpeedLabel, demoSpeedOneButton, reachLimitLabel, reachDefaultButton)
    }

    fun updateDemoButtons() {
        ColorOutline.checkAlliance()
    }
}