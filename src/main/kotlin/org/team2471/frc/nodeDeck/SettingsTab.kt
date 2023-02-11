package org.team2471.frc.nodeDeck

import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.control.ToggleButton
import javafx.scene.control.ToggleGroup
import javafx.scene.layout.GridPane
import javafx.scene.layout.TilePane
import javax.swing.ButtonGroup

object SettingsTab : TilePane(Orientation.VERTICAL) {
    val ipInput = TextField("10.24.71.2")
    val connectButton = Button("Connect")
    val robotIpButton = Button("10.24.71.2")
    val lHostButton = Button("localhost")
    val fullscreenButton = Button("Fullscreen application")
    val armCoastButton = Button("Coast")
    val armBrakeButtton = Button("Brake")
    val armModeGroup = ToggleGroup()
    val armModeGrid = GridPane()
    val armModeLabel = Label("sets the arm shoulder motor to coast mode", armCoastButton)
    val ipLabel = Label("roboRIO IP Address:")
    val ndSettingsLabel = Label("NodeDeck Settings:")
    val robotSettingsLabel = Label("Robot Settings:")
    val fontSize = 30

    var coastArmMotor = false
    var brakeArmMotor = false

    init {
        println("SettingsTab says hi")

        ipLabel.style = "-fx-font-weight: bold; -fx-font-size: $fontSize px"
        ipInput.style = "-fx-font-size: $fontSize px"
        connectButton.style = "-fx-font-size: $fontSize px"
        robotIpButton.style = "-fx-font-size: $fontSize px"
        lHostButton.style = "-fx-font-size: $fontSize px"
        fullscreenButton.style = "-fx-font-size: $fontSize px"
        ndSettingsLabel.style = "-fx-font-size: $fontSize px"
        robotSettingsLabel.style = "-fx-font-size: $fontSize px"
        armModeLabel.style = "-fx-font-size: $fontSize px"
        armCoastButton.style = "-fx-font-size: $fontSize px"
        armBrakeButtton.style = "-fx-font-size: $fontSize px"

//        armBrakeButtton.toggleGroup = armModeGroup
//        armCoastButton.toggleGroup = armModeGroup

        armModeGrid.addRow(0, armBrakeButtton, armCoastButton)
        armModeGrid.alignment = Pos.CENTER

        SettingsTab.alignment = Pos.TOP_CENTER
        SettingsTab.children.addAll(ipLabel, ipInput, connectButton, robotIpButton, lHostButton, ndSettingsLabel, fullscreenButton, robotSettingsLabel, armModeGrid)
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
        armCoastButton.setOnAction {
            coastArmMotor = true
            println(coastArmMotor)
            NTClient.setTables()
        }
        armBrakeButtton.setOnAction {
            brakeArmMotor = true
            println(brakeArmMotor)
            NTClient.setTables()
        }
    }
}