package org.team2471.frc.nodeDeck

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.control.ToggleButton
import javafx.scene.control.ToggleGroup
import javafx.scene.layout.GridPane
import javafx.scene.layout.VBox

object AutoConfig: VBox(10.0) {
    private val leftButton = ToggleButton("Left")
    private val rightButton = ToggleButton("Right")
    val chargeButton = ToggleButton("Charge Up?")
    val amountOfPiecesSelector = ComboBox<String>()

    private val startLeftOrRightGroup = ToggleGroup()
    private val leftOrRightGrid = GridPane()

    private val piecesLabel = Label("Amount of game pieces to score.", amountOfPiecesSelector)
    private val leftOrRightLabel = Label("Is the robot starting on the left or right?", leftOrRightGrid)
    private val chargeLabel = Label("End the auto on charge station?", chargeButton)
    private val saveButton = Button("Save")

    private val validAutoLabel = Label("NOT A VALID AUTO")

    val piece1 = AutoPiecesGrid("#1", true)
    val piece2 = AutoPiecesGrid("#2")
    val piece3 = AutoPiecesGrid("#3")
    val piece4 = AutoPiecesGrid("#4")
    val piece5 = AutoPiecesGrid("#5")
    val piecesGrid = GridPane()

    val fontSize = 50

    val isStartingLeft: Boolean
        get() = leftButton.isSelected

    init {
        println("AutoConfig says hi!")

        saveButton.style = "-fx-font-size: $fontSize px"
        chargeLabel.style = "-fx-font-size: $fontSize px"
        leftOrRightLabel.style = "-fx-font-size: $fontSize px"
        piecesLabel.style = "-fx-font-size: $fontSize px"
        leftButton.style = "-fx-font-size: $fontSize px"
        rightButton.style = "-fx-font-size: $fontSize px"
        amountOfPiecesSelector.style = "-fx-font-size: $fontSize px"
        chargeButton.style = "-fx-font-size: $fontSize px"

        leftButton.toggleGroup = startLeftOrRightGroup
        rightButton.toggleGroup = startLeftOrRightGroup

        amountOfPiecesSelector.items.addAll("1", "2", "3", "4", "5")
        amountOfPiecesSelector.selectionModel.selectFirst()
        amountOfPiecesSelector.setOnAction {
            showPiecesGrid()
            NTClient.setTables()
        }

        leftButton.setPrefSize(200.0, 50.0)
        rightButton.setPrefSize(200.0, 50.0)
        leftOrRightGrid.addRow(0, leftButton, rightButton)

        saveButton.setOnAction {
            NTClient.setTables()
            validAutoLabel.text = "${isValidAuto()}"
        }

        piecesGrid.alignment = Pos.CENTER


        AutoConfig.alignment = Pos.TOP_CENTER
        AutoConfig.children.addAll(leftOrRightLabel, piecesLabel, piecesGrid, chargeLabel, saveButton, validAutoLabel)

        showPiecesGrid()
        rightButton.fire()
    }

    fun showPiecesGrid() {
        if (amountOfPiecesSelector.value == "1") {
            piecesGrid.children.removeAll(piece1, piece2, piece3, piece4, piece5)
            piecesGrid.addRow(0, piece1)

        } else if (amountOfPiecesSelector.value == "2") {
            piecesGrid.children.removeAll(piece1, piece2, piece3, piece4, piece5)
            piecesGrid.addRow(0, piece1, piece2)

        } else if (amountOfPiecesSelector.value == "3") {
            piecesGrid.children.removeAll(piece1, piece2, piece3, piece4, piece5)
            piecesGrid.addRow(0, piece1, piece2, piece3)

        } else if (amountOfPiecesSelector.value == "4") {
            piecesGrid.children.removeAll(piece1, piece2, piece3, piece4, piece5)
            piecesGrid.addRow(0, piece1, piece2, piece3, piece4)

        } else if (amountOfPiecesSelector.value == "5") {
            piecesGrid.children.removeAll(piece1, piece2, piece3, piece4, piece5)
            piecesGrid.addRow(0, piece1, piece2, piece3, piece4, piece5)
        }
    }
    fun isValidAuto(): Boolean {
        var v = true
        //checks if any node values are equal
        if ((piece1.nodeValue == piece2.nodeValue || piece1.nodeValue == piece3.nodeValue || piece1.nodeValue == piece4.nodeValue || piece1.nodeValue == piece5.nodeValue) && piece1.isReady && amountOfPiecesSelector.value.toInt() <= 1) v = false
        if ((piece2.nodeValue == piece1.nodeValue || piece2.nodeValue == piece3.nodeValue || piece2.nodeValue == piece4.nodeValue || piece2.nodeValue == piece5.nodeValue) && piece2.isReady && amountOfPiecesSelector.value.toInt() <= 2) v = false
        if ((piece3.nodeValue == piece1.nodeValue || piece3.nodeValue == piece2.nodeValue || piece3.nodeValue == piece4.nodeValue || piece3.nodeValue == piece5.nodeValue) && piece3.isReady && amountOfPiecesSelector.value.toInt() <= 3) v = false
        if ((piece4.nodeValue == piece1.nodeValue || piece4.nodeValue == piece2.nodeValue || piece4.nodeValue == piece3.nodeValue || piece4.nodeValue == piece5.nodeValue) && piece4.isReady && amountOfPiecesSelector.value.toInt() <= 4) v = false
        if ((piece5.nodeValue == piece1.nodeValue || piece5.nodeValue == piece2.nodeValue || piece5.nodeValue == piece3.nodeValue || piece5.nodeValue == piece4.nodeValue) && piece5.isReady && amountOfPiecesSelector.value.toInt() <= 5) v = false

        println((piece1.nodeValue == piece2.nodeValue || piece1.nodeValue == piece3.nodeValue || piece1.nodeValue == piece4.nodeValue || piece1.nodeValue == piece5.nodeValue) && piece1.isReady)
        return v
    }
}

//todo: Node # textbox/override, invalid auto detector, mini grid that shows what is being placed