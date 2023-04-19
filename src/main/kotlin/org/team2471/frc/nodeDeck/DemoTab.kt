package org.team2471.frc.nodeDeck

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.layout.VBox
import org.team2471.frc.nodeDeck.NTClient.demoMode

object DemoTab: VBox(10.0) {
    val demoButton = Button("Demo Mode")
    val button = Button("IM A BUTTON!!!!!  HELLO!!")
    const val fontSize = 50

    init {
        demoButton.style = "-fx-font-size: $fontSize px"
        demoButton.setOnAction {
            demoMode = !demoMode
            button.isDisable = !demoMode
            updateDemoButton()
        }
        button.setOnAction {
            println("HIIIII")
        }
        DemoTab.alignment = Pos.TOP_CENTER
        DemoTab.children.addAll(demoButton, button)
    }

    fun updateDemoButton() {
        demoButton.style = "-fx-font-size: $fontSize px${if (demoMode) "; -fx-border-color: red; -fx-border-width: 10 10 10 10" else ""}"
        button.isDisable = !demoMode
        ColorOutline.checkAlliance()
    }
}