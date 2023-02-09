package org.team2471.frc.nodeDeck

import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.TilePane

object SettingsTab : TilePane(Orientation.VERTICAL) {
    val ipInput = TextField("10.24.71.2")
    val connectButton = Button("Connect")
    val robotIpButton = Button("10.24.71.2")
    val lHostButton = Button("localhost")
    val fullscreenButton = Button("Fullscreen application")
    val ipLabel = Label("roboRIO IP Address:")
    val ndSettingsLabel = Label("NodeDeck Settings:")
    val fontSize = 30

    init {
        println("SettingsTab says hi")

        ipLabel.style = "-fx-font-weight: bold; -fx-font-size: $fontSize px"
        ipInput.style = "-fx-font-size: $fontSize px"
        connectButton.style = "-fx-font-size: $fontSize px"
        robotIpButton.style = "-fx-font-size: $fontSize px"
        lHostButton.style = "-fx-font-size: $fontSize px"
        fullscreenButton.style = "-fx-font-size: $fontSize px"
        ndSettingsLabel.style = "-fx-font-size: $fontSize px"

        SettingsTab.alignment = Pos.TOP_CENTER
        SettingsTab.children.addAll(ipLabel, ipInput, connectButton, robotIpButton, lHostButton, ndSettingsLabel, fullscreenButton)
        ipInput.setOnAction {
            NTClient.connect()
        }
        connectButton.setOnAction {
            NTClient.connect()
        }
        robotIpButton.setOnAction {
            ipInput.text = "10.24.71.2"
            NTClient.connect()
        }
        lHostButton.setOnAction {
            ipInput.text = "localhost"
            NTClient.connect()
        }
        fullscreenButton.setOnAction {
            NodeDeck.stage.isFullScreen = true
        }
    }
}