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
    val ipLabel = Label("roboRIO IP Address:")

    init {
        println("SettingsTab says hi")

        SettingsTab.alignment = Pos.TOP_CENTER
        SettingsTab.children.addAll(ipLabel, ipInput, connectButton, robotIpButton, lHostButton)
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
    }
}