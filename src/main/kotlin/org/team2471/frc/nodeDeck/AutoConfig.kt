package org.team2471.frc.nodeDeck

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import javafx.scene.layout.VBox

object AutoConfig: VBox(10.0) {
    private val insideButton = Button("Inside")
    private val outsideButton = Button("Outside")
    private val saveButton = Button("Save")
    private val clearButton = Button("Clear")
    val chargeButton = Button("Charge Up?")
    val amountOfPiecesSelector = ComboBox<String>()

    private val leftOrRightGrid = GridPane()

    private val chargeLabel = Label("End the auto on charge station?", chargeButton)//chargeLabel is "labeling" chargeButton. chargeLabel will now call chargeButton and itself (creating a label for chargeButton)
    private val piecesLabel = Label("Number of game pieces to score.", amountOfPiecesSelector)
    private val leftOrRightLabel = Label("Is the robot starting on the inside or outside?", leftOrRightGrid)
    private val validAutoLabel = Label("", saveButton)

    val piece1 = AutoPiecesGrid("#1 (preloaded)")
    val piece2 = AutoPiecesGrid("#2")
    val piece3 = AutoPiecesGrid("#3")
    val piece4 = AutoPiecesGrid("#4")
    val piece5 = AutoPiecesGrid("#5")
    private val piecesGrid = GridPane()
    val allPieces = listOf(piece1, piece2, piece3, piece4, piece5)

    val fontSize = 50

    var isStartingInside = true
    var selectedStartingButton: Button = insideButton
    var chargeInAuto = false

    init {
        println("AutoConfig says hi!")

        saveButton.style = "-fx-font-size: $fontSize px" //setting style for javaFX Nodes
        chargeLabel.style = "-fx-font-size: $fontSize px"
        leftOrRightLabel.style = "-fx-font-size: $fontSize px"
        piecesLabel.style = "-fx-font-size: $fontSize px"
        insideButton.style = "-fx-font-size: $fontSize px"
        outsideButton.style = "-fx-font-size: $fontSize px"
        amountOfPiecesSelector.style = "-fx-font-size: $fontSize px"
        chargeButton.style = "-fx-font-size: $fontSize px"

        amountOfPiecesSelector.items.addAll("1", "2", "3", "4", "5") //adding options to selector
        amountOfPiecesSelector.selectionModel.selectFirst()

        piecesGrid.alignment = Pos.CENTER

        amountOfPiecesSelector.setOnAction {  //setting button events
            showPiecesGrid()
            updateValidAutoLabel()
            NTClient.setTables()
        }
        chargeButton.setOnAction {
            updateChargeButton()
        }
        saveButton.setOnAction {
            updateValidAutoLabel()
            NTClient.setTables()
            AutoVisualizer.updateCanvas()
//            validAutoLabel.text = "${validAutoLabel.text} SAVED"
        }
        clearButton.setOnAction {
            for (p in allPieces) {
                p.clear()
            }
            amountOfPiecesSelector.value = "1"
            showPiecesGrid()
            updateValidAutoLabel()
            NTClient.setTables()
            AutoVisualizer.updateCanvas()
        }

        insideButton.setPrefSize(300.0, 50.0)
        outsideButton.setPrefSize(300.0, 50.0)
        insideButton.setOnAction {
            isStartingInside = true
            switchInsideOrOutside(insideButton)
            NTClient.setTables()
        }
        outsideButton.setOnAction {
            isStartingInside = false
            switchInsideOrOutside(outsideButton)
            NTClient.setTables()
        }

        leftOrRightGrid.addRow(0, insideButton, outsideButton) //adding L/R buttons to the same grid

        AutoConfig.alignment = Pos.TOP_CENTER
        AutoConfig.children.addAll(leftOrRightLabel, piecesLabel, piecesGrid, chargeLabel, validAutoLabel, AutoVisualizer, clearButton) //Labels are "labeling" a Node. (see initializer)

        showPiecesGrid()
        switchInsideOrOutside(insideButton)
    }

    fun showPiecesGrid() { //function that adds each of the PiecesGrid's to the grid based off the number of pieces selection.
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
    fun isValidAuto(): Boolean {//checks if any node values are equal
        var v = true
        if (!piece1.isReady) v = false
        if ((((piece2.nodeValue == piece1.nodeValue) && piece2.isReady) || !piece2.isReady) && amountOfPiecesSelector.value.toInt() >= 2) v = false
        if ((((piece3.nodeValue == piece1.nodeValue || piece3.nodeValue == piece2.nodeValue) && piece3.isReady) || !piece3.isReady) && amountOfPiecesSelector.value.toInt() >= 3) v = false
        if ((((piece4.nodeValue == piece1.nodeValue || piece4.nodeValue == piece2.nodeValue || piece4.nodeValue == piece3.nodeValue) && piece4.isReady) || !piece4.isReady) && amountOfPiecesSelector.value.toInt() >= 4) v = false
        if ((((piece5.nodeValue == piece1.nodeValue || piece5.nodeValue == piece2.nodeValue || piece5.nodeValue == piece3.nodeValue || piece5.nodeValue == piece4.nodeValue) && piece5.isReady) || !piece5.isReady) && amountOfPiecesSelector.value.toInt() >= 5) v = false

//        println(amountOfPiecesSelector.value.toInt())
        return v
    }
    fun updateValidAutoLabel() {
        if (isValidAuto()) {
            validAutoLabel.text = "Auto Configuration is Good :)"
            validAutoLabel.style = "-fx-text-fill: green; -fx-font-size: ${fontSize - 20}px"
        } else {
            validAutoLabel.text = "INVALID ROBOT AUTO"
            validAutoLabel.style = "-fx-text-fill: red; -fx-font-size: ${fontSize - 20}px"
        }
    }
    fun switchInsideOrOutside(thisButton: Button) {
        //checks if previous button was cone or cube then sets its style
        if (selectedStartingButton == insideButton) {
            selectedStartingButton.style = "-fx-font-size: $fontSize px"
        } else {
            selectedStartingButton.style = "-fx-font-size: $fontSize px"
        }
        //adds red border around new selected button
        thisButton.style = thisButton.style + "; -fx-border-color: red; -fx-border-width: 10 10 10 10"

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
}

//todo: Node # textbox/override, make Left/Right buttons regular buttons (like the node selectors), make saveButton turn red and/or unpressable if invalid auto, Auto Presets