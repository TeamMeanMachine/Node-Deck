package org.team2471.frc.nodeDeck

import javafx.geometry.Pos
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.control.ToggleButton
import javafx.scene.control.ToggleGroup
import javafx.scene.layout.GridPane
import javafx.scene.layout.Region

object AutoConfigTab: GridPane() {
    val leftButton = ToggleButton("Left")
    val rightButton = ToggleButton("Right")
    val chargeButton = ToggleButton("Charge Up?")
    val startLeftOrRightGroup = ToggleGroup()
    val amountOfPieces = ComboBox<String>()
    val piecesLabel = Label("Amount of game pieces.")
    val spacer = Region()
    val spacer2 = Region()

    init {
        println("AutoConfigTab says hi!")

        leftButton.toggleGroup = startLeftOrRightGroup
        rightButton.toggleGroup = startLeftOrRightGroup

        amountOfPieces.items.addAll("1", "2", "3", "4")

        leftButton.alignment = Pos.CENTER
        rightButton.alignment = Pos.CENTER

        spacer.setMinSize(50.0, 30.0)
        spacer2.setMinSize(30.0, 30.0)
        leftButton.setPrefSize(75.0, 50.0)
        rightButton.setPrefSize(75.0, 50.0)

        AutoConfigTab.style = "-fx-background-color: #f0f0f0"
        AutoConfigTab.alignment = Pos.TOP_CENTER

        AutoConfigTab.add(spacer, 0, 0)
        AutoConfigTab.add(leftButton, 0, 1)
        AutoConfigTab.add(rightButton, 1, 1)
        AutoConfigTab.add(spacer2, 1, 2)
        AutoConfigTab.add(amountOfPieces, 0, 3)
    }
}

//todo: grid of scoring 1st 2nd 3rd 4th? 5th? Top/Mid/Bot, # of pieces Dropdown, charge? True/false button