package org.team2471.frc.nodeDeck

import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.control.ToggleButton
import javafx.scene.layout.GridPane
import javafx.scene.layout.TilePane

object SettingsTab : TilePane(Orientation.VERTICAL) {
    val ipInput = TextField("10.24.71.2")
    val connectButton = Button("Connect")
    val robotIpButton = Button("10.24.71.2")
    val lHostButton = Button("localhost")
    val armCoastButton = Button("Coast")
    val armBrakeButtton = Button("Brake")
    val toggleTypeButton = ToggleButton("Toggle Type Button")
    val armModeLabel = Label()
    val armModeGrid = GridPane()
//    val armModeLabel = Label("sets the arm shoulder motor to coast mode", armCoastButton)
    val ipLabel = Label("roboRIO IP Address:")
    val ndSettingsLabel = Label("NodeDeck Settings:")
    val robotSettingsLabel = Label("Robot Settings:")
    val toggleTypeButtonLabel = Label("Toggle the cone/cube button", toggleTypeButton)
    val fontSize = 30

    val coastArmMotor
        get() = NTClient.shoulderCoastModeEntry.get()
    val brakeArmMotor
        get() = NTClient.shoulderBrakeModeEntry.get()

    init {
        println("SettingsTab says hi")

        ipLabel.style = "-fx-font-weight: bold; -fx-font-size: $fontSize px"
        ipInput.style = "-fx-font-size: $fontSize px"
        connectButton.style = "-fx-font-size: $fontSize px"
        robotIpButton.style = "-fx-font-size: $fontSize px"
        lHostButton.style = "-fx-font-size: $fontSize px"
        ndSettingsLabel.style = "-fx-font-size: $fontSize px"
        robotSettingsLabel.style = "-fx-font-size: $fontSize px"
        armModeLabel.style = "-fx-font-size: $fontSize px"
        armCoastButton.style = "-fx-font-size: $fontSize px"
        armBrakeButtton.style = "-fx-font-size: $fontSize px"
        toggleTypeButton.style = "-fx-font-size: $fontSize px"
        toggleTypeButtonLabel.style = "-fx-font-size: $fontSize px"


        armModeGrid.addRow(0, armBrakeButtton, armCoastButton, armModeLabel)
        armModeGrid.alignment = Pos.CENTER

        SettingsTab.alignment = Pos.TOP_CENTER
        SettingsTab.children.addAll(ipLabel, ipInput, connectButton, robotIpButton, lHostButton, ndSettingsLabel, toggleTypeButtonLabel, robotSettingsLabel, armModeGrid)
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
        armCoastButton.setOnAction {
            if (NTClient.networkTableInstance.isConnected) {
                NTClient.shoulderCoastModeEntry.set(true)
                NTClient.setTables()
                updateArmModeButtons()
                updateArmModeLabel()
            }
        }
        armBrakeButtton.setOnAction {
            if (NTClient.networkTableInstance.isConnected) {
                NTClient.shoulderBrakeModeEntry.set(true)
                NTClient.setTables()
                updateArmModeButtons()
                updateArmModeLabel()
            }
        }
        toggleTypeButton.setOnAction {
            toggleTypeButton()
        }
        toggleTypeButton()
    }
    fun updateArmModeLabel() {
        if (brakeArmMotor)
            armModeLabel.text = "In brake mode"
        if (coastArmMotor)
            armModeLabel.text = "In coast mode"
        if (brakeArmMotor && coastArmMotor)
            armModeLabel.text = "i might be breaking..."
    }
    fun updateArmModeButtons() {
        if (coastArmMotor) {
            armCoastButton.style = "-fx-font-size: $fontSize px; -fx-border-color: red; -fx-border-width: 5 10 5 10"
        } else {
            armCoastButton.style = "-fx-font-size: $fontSize px"
        }
        if (brakeArmMotor) {
            armBrakeButtton.style = "-fx-font-size: $fontSize px; -fx-border-color: red; -fx-border-width: 5 10 5 10"
        } else {
            armBrakeButtton.style = "-fx-font-size: $fontSize px"
        }
    }
    fun toggleTypeButton() {
        if (toggleTypeButton.isArmed) {
        } else {
        }
    }
} //todo toggle piece type button in LongFormat