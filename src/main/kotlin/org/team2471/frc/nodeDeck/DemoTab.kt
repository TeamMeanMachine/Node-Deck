package org.team2471.frc.nodeDeck

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import org.team2471.frc.nodeDeck.NTClient.aprilDemo

object DemoTab: VBox(10.0) {
    val demoSpeedInput = TextField("${NTClient.demoSpeedEntry.getDouble(1.0)}")
    val demoSpeedLabel = Label("Demo Speed", demoSpeedInput)
    val aprilDemoButton = Button("Apriltag Demo")

    const val fontSize = 50

    init {
        demoSpeedLabel.style = "-fx-font-size: $fontSize px"
        aprilDemoButton.style = "-fx-font-size: $fontSize px"

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
        aprilDemoButton.setOnAction {
            aprilDemo = !aprilDemo
            updateDemoButton()
        }
        DemoTab.alignment = Pos.TOP_CENTER
        DemoTab.children.addAll(demoSpeedLabel, aprilDemoButton)
    }

    fun updateDemoButton() {
        aprilDemoButton.style = "-fx-font-size: $fontSize px${if (aprilDemo) "; -fx-border-color: red; -fx-border-width: 10 10 10 10" else ""}"
        ColorOutline.checkAlliance()
    }
}