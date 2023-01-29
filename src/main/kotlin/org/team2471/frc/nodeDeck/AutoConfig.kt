package org.team2471.frc.nodeDeck

import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.control.ToggleButton
import javafx.scene.control.ToggleGroup
import javafx.scene.layout.GridPane
import javafx.scene.layout.Region
import javafx.scene.layout.TilePane

object AutoConfig: TilePane(Orientation.VERTICAL) {
    val leftButton = ToggleButton("Left")
    val rightButton = ToggleButton("Right")
    val chargeButton = ToggleButton("Charge Up?")
    val startLeftOrRightGroup = ToggleGroup()
    val leftOrRightGrid = GridPane()
    val amountOfPiecesSelector = ComboBox<String>()
    val piecesLabel = Label("Amount of game pieces to score.", amountOfPiecesSelector)
    val leftOrRightLabel = Label("Is the robot starting on the left or right?", leftOrRightGrid)
    val chargeLabel = Label("End the auto on charge station?", chargeButton)
    val spacer = Region()
    val spacer2 = Region()

    init {
        println("AutoConfig says hi!")

        leftButton.toggleGroup = startLeftOrRightGroup
        rightButton.toggleGroup = startLeftOrRightGroup

        amountOfPiecesSelector.items.addAll("1", "2", "3", "4")

        leftButton.setPrefSize(75.0, 50.0)
        rightButton.setPrefSize(75.0, 50.0)

        leftOrRightGrid.add(leftButton, 0, 0)
        leftOrRightGrid.add(rightButton, 1, 0)

        AutoConfig.style = "-fx-background-color: #f0f0f0"
        AutoConfig.alignment = Pos.TOP_CENTER
        AutoConfig.children.addAll(leftOrRightLabel, piecesLabel, chargeLabel)
    }
}

//todo: grid of scoring 1st 2nd 3rd 4th? 5th? Top/Mid/Bot, # of pieces Dropdown, charge? True/false button