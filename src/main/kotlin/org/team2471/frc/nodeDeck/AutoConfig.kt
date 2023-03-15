package org.team2471.frc.nodeDeck

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import javafx.scene.layout.VBox

object AutoConfig: VBox(10.0) {
    private val insideButton = Button("Inside")
    private val outsideButton = Button("Outside")
    private val middleButton = Button("Middle")
    private val saveButton = Button("Save")
    private val clearButton = Button("Clear")
    private val nodeDeckAutoButton = Button("Set robot's auto to NodeDeck")
    val chargeButton = Button("Charge Up?")
    val finishWithPieceButton = Button("Finish with piece")

    private val startingPointGrid = GridPane()
    private val endOfAutoGrid = GridPane()
    private val saveButtonGrid = GridPane()

    private val chargeLabel = Label("End the auto on charge station?", chargeButton)//chargeLabel is "labeling" chargeButton. chargeLabel will now call chargeButton and itself (creating a label for chargeButton)
    private val leftOrRightLabel = Label("Where is the robot starting?", startingPointGrid)


    val fontSize = 50

    var startingPoint: StartingPoint = StartingPoint.INSIDE
    var selectedStartingButton: Button = insideButton
    var chargeInAuto = false
    var finishWPiece = false

    init {
        println("AutoConfig says hi!")

        saveButton.style = "-fx-font-size: $fontSize px" //setting style for javaFX Nodes
        chargeLabel.style = "-fx-font-size: $fontSize px"
        finishWithPieceButton.style = "-fx-font-size: $fontSize px"
        leftOrRightLabel.style = "-fx-font-size: $fontSize px"
        insideButton.style = "-fx-font-size: $fontSize px"
        outsideButton.style = "-fx-font-size: $fontSize px"
        chargeButton.style = "-fx-font-size: $fontSize px"
        nodeDeckAutoButton.style = "-fx-font-size: $fontSize px; -fx-text-fill: white; -fx-background-color: red"

        chargeButton.setOnAction {
            updateChargeButton()
        }
        finishWithPieceButton.setOnAction {
            updateFinishButton()
        }
        saveButton.setOnAction {
            NTClient.setTables()
        }
        clearButton.setOnAction {
            AutoInterface.clear()
            NTClient.setTables()
        }
        nodeDeckAutoButton.setOnAction {
            NTClient.setNodeDeckAuto()
            NTClient.setTables()
        }

        insideButton.setPrefSize(250.0, 50.0)
        outsideButton.setPrefSize(250.0, 50.0)
        middleButton.setPrefSize(250.0, 50.0)
        insideButton.setOnAction {
            startingPoint = StartingPoint.INSIDE
            switchStartingPointButton(insideButton)
            NTClient.setTables()
        }
        middleButton.setOnAction {
            startingPoint = StartingPoint.MIDDLE
            switchStartingPointButton(middleButton)
            NTClient.setTables()
        }
        outsideButton.setOnAction {
            startingPoint = StartingPoint.OUTSIDE
            switchStartingPointButton(outsideButton)
            NTClient.setTables()
        }

        startingPointGrid.addRow(0, insideButton, middleButton, outsideButton) //adding L/R buttons to the same grid
        endOfAutoGrid.addRow(0, finishWithPieceButton, chargeLabel)
        endOfAutoGrid.alignment = Pos.CENTER
        saveButtonGrid.add(saveButton, 0, 0)
        saveButtonGrid.alignment = Pos.CENTER

        AutoConfig.alignment = Pos.TOP_CENTER
        AutoConfig.children.addAll(leftOrRightLabel, endOfAutoGrid, AutoInterface, saveButtonGrid, clearButton) //Labels are "labeling" a Node. (see initializer)

        switchStartingPointButton(insideButton)
    }

    fun switchStartingPointButton(thisButton: Button) {
        selectedStartingButton.style = "-fx-font-size: $fontSize px"
        thisButton.style = thisButton.style + "; -fx-border-color: red; -fx-border-width: 10 10 10 10" //adds red border around new selected button
        selectedStartingButton = thisButton
    }
    fun updateChargeButton() {
        if (chargeInAuto) {
            chargeButton.style = "-fx-font-size: $fontSize px"
            chargeInAuto = false
        } else {
            chargeButton.style = "-fx-font-size: $fontSize px; -fx-border-color: red; -fx-border-width: 10 10 10 10"
            chargeInAuto = true
        }
    }
    fun updateFinishButton() {
        if (finishWPiece) {
            finishWithPieceButton.style = "-fx-font-size: $fontSize px"
            finishWPiece = false
        } else {
            finishWithPieceButton.style = "-fx-font-size: $fontSize px; -fx-border-color: red; -fx-border-width: 10 10 10 10"
            finishWPiece = true
        }
    }
    fun showNodeAutoChanger() {
        if (NTClient.networkTableInstance.isConnected) {
            if (NTClient.selectedAuto != "NodeDeck") {
                saveButtonGrid.children.removeAll(nodeDeckAutoButton, saveButton)
                saveButtonGrid.addRow(0, saveButton, nodeDeckAutoButton)
            } else {
                saveButtonGrid.children.removeAll(nodeDeckAutoButton)
            }
        }
    }
}
enum class StartingPoint {
    INSIDE,
    OUTSIDE,
    MIDDLE
}

//todo: Node # override, Auto Presets