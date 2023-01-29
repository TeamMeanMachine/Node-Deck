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
    val defaultIpButton = Button("10.24.71.2")
    val ipLabel = Label("roboRIO IP Address:")

    init {
        SettingsTab.alignment = Pos.TOP_CENTER
        SettingsTab.children.addAll(ipLabel, ipInput, connectButton, defaultIpButton)
        ipInput.setOnAction {
            NTClient.connect()
        }
        connectButton.setOnAction {
            NTClient.connect()
        }
        defaultIpButton.setOnAction {
            ipInput.text = "10.24.71.2"
            NTClient.connect()
        }
    }
}