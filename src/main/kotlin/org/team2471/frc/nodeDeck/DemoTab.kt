package org.team2471.frc.nodeDeck

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import org.team2471.frc.nodeDeck.NTClient.demoMode

object DemoTab: VBox(10.0) {
    val demoButton = Button("Demo Mode")
    val speedLimitInput = TextField("${NTClient.demoSpeedLimit}")
    val speedLimitLabel = Label("Demo Speed Limit", speedLimitInput)
    const val fontSize = 50

    init {
        demoButton.style = "-fx-font-size: $fontSize px"
        speedLimitLabel.style = "-fx-font-size: $fontSize px"

        demoButton.setOnAction {
            demoMode = !demoMode
            updateDemoButton()
        }
        speedLimitInput.setPrefSize(140.0, 50.0)
        speedLimitInput.setOnAction {
            try {
                NTClient.demoSpeedLimit = speedLimitInput.text.toDouble()
            } catch (ex:Exception) {
                speedLimitInput.text = NTClient.demoSpeedLimit.toString()
                println(ex)
            }
        }
        DemoTab.alignment = Pos.TOP_CENTER
        DemoTab.children.addAll(demoButton, speedLimitLabel)
    }

    fun updateDemoButton() {
        demoButton.style = "-fx-font-size: $fontSize px${if (demoMode) "; -fx-border-color: red; -fx-border-width: 10 10 10 10" else ""}"
        speedLimitInput.isDisable = !demoMode
        ColorOutline.checkAlliance()
    }
}