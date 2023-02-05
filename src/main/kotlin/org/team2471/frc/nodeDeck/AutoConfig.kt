package org.team2471.frc.nodeDeck

import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.control.ToggleButton
import javafx.scene.control.ToggleGroup
import javafx.scene.layout.GridPane
import javafx.scene.layout.TilePane
import javafx.scene.layout.VBox

object AutoConfig: VBox(10.0) {
    private val leftButton = ToggleButton("Left")
    private val rightButton = ToggleButton("Right")
    val chargeButton = ToggleButton("Charge Up?")
    private val startLeftOrRightGroup = ToggleGroup()
    private val leftOrRightGrid = GridPane()
    private val amountOfPiecesSelector = ComboBox<String>()
    private val piecesLabel = Label("Amount of game pieces to score.", amountOfPiecesSelector)
    private val leftOrRightLabel = Label("Is the robot starting on the left or right?", leftOrRightGrid)
    private val chargeLabel = Label("End the auto on charge station?", chargeButton)
    private val saveButton = Button("Save")
    val piecesGrid = GridPane()
    val isStartingLeft: Boolean
        get() = leftButton.isSelected

    init {
        println("AutoConfig says hi!")

        leftButton.toggleGroup = startLeftOrRightGroup
        rightButton.toggleGroup = startLeftOrRightGroup

        amountOfPiecesSelector.items.addAll("1", "2", "3", "4")
        amountOfPiecesSelector.setOnAction { showPiecesGrid() }

        leftButton.setPrefSize(75.0, 50.0)
        rightButton.setPrefSize(75.0, 50.0)
        leftOrRightGrid.addRow(0, leftButton, rightButton)

        saveButton.setOnAction {
            NTClient.setTables()
        }

        piecesGrid.alignment = Pos.CENTER


        AutoConfig.alignment = Pos.TOP_CENTER
        AutoConfig.children.addAll(leftOrRightLabel, piecesLabel, piecesGrid, chargeLabel, saveButton)
    }

    fun showPiecesGrid() {
    }
}

//todo: grid of scoring 1st 2nd 3rd 4th? 5th? Top/Mid/Bottom