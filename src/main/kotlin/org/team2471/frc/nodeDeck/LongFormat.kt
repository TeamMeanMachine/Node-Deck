package org.team2471.frc.nodeDeck

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import javafx.scene.text.Font

object LongFormat: GridPane() {
    private val leftNodeSelectorGrid = GridPane()
    private val middleNodeSelectorGrid = GridPane()
    private val rightNodeSelectorGrid = GridPane()
    private val infoPane = GridPane()
    private val nodeLabel = Label()

    private val zero = Button()
    private val one = Button()
    private val two = Button()
    private val three = Button()
    private val four = Button()
    private val five = Button()
    private val six = Button()
    private val seven = Button()
    private val eight = Button()
    private val nine = Button()
    private val ten = Button()
    private val eleven = Button()
    private val twelve = Button()
    private val thirteen = Button()
    private val fourteen = Button()
    private val fifteen = Button()
    private val sixteen = Button()
    private val seventeen = Button()
    private val eighteen = Button()
    private val nineteen = Button()
    private val twenty = Button()
    private val twentyOne = Button()
    private val twentyTwo = Button()
    private val twentyThree = Button()
    private val twentyFour = Button()
    private val twentyFive = Button()
    private val twentySix = Button()
    private val flipButton = Button("Flip Buttons")


    val buttonHeight: Double = 220.0
    val buttonWidth: Double = 205.0
    var selectedNodeButton: Button = one
    val buttonBorderSize = " 2 2 2 2" //format in " ## ## ## ##" Top Right Bottom Left
    val selectedButtonBorderSize = " 15 15 15 15"
    val fontSize = 50
    var flipped = false

    val allButtons = listOf<Button>(zero, one, two, three, four, five, six, seven, eight, nine, ten, eleven, twelve, thirteen, fourteen, fifteen, sixteen, seventeen, eighteen, nineteen, twenty, twentyOne, twentyTwo, twentyThree, twentyFour, twentyFive, twentySix)
    val allFloorButtons = listOf<Button>(two, five, eight, eleven, fourteen, seventeen, twenty, twentyThree, twentySix)
    val allConeButtons = listOf<Button>(zero, one, six, seven, nine, ten, fifteen, sixteen, eighteen, nineteen, twentyFour, twentyFive)
    val allCubeButtons = listOf<Button>(three, four, twelve, thirteen, twentyOne, twentyTwo)

    init {
        println("LongFormat says hi")

        var assignedButtonNode = -1
        for (button in allButtons) {
            assignedButtonNode += 1
            val thisButtonNode = assignedButtonNode
            button.setPrefSize(buttonWidth, buttonHeight)
            button.setOnAction {
                if (!NTClient.isRed) {
                    NodeDeck.selectedNode = thisButtonNode + 27
                } else NodeDeck.selectedNode = thisButtonNode

                changeSelectedNodeButton(button)
                updateInfoPanel()
            }
        }
        for (button in allConeButtons) {
            button.style = "-fx-background-color: #FFFF00; -fx-border-width: $buttonBorderSize; -fx-border-color: black"
        }
        for (button in allCubeButtons) {
            button.style = "-fx-background-color: #9900ff; -fx-border-width: $buttonBorderSize; -fx-border-color: black"
        }
        for (button in allFloorButtons) {
            button.style = "-fx-background-color: #595959; -fx-border-width: $buttonBorderSize; -fx-border-color: black"
        }
        flipButton.setPrefSize(300.0, 25.0)
        flipButton.font = Font(20.0)
        flipButton.setOnAction {
            println("FLIPPING")
            switchOrientation()
        }

        switchOrientation()

        nodeLabel.alignment = Pos.CENTER
        nodeLabel.font = Font(20.0)
        nodeLabel.style = "-fx-background-color: #f0f0f0; -fx-font-weight: bold; -fx-font-size: $fontSize px"
        nodeLabel.setPrefSize(300.0, 25.0)

        infoPane.addColumn(0, nodeLabel, flipButton)
        infoPane.setMinSize(buttonWidth * 3 + 30, 50.0)
        infoPane.alignment = Pos.TOP_CENTER

        LongFormat.setMinSize(1400.0, 1400.0)
//        LongFormat.addRow(1, leftNodeSelectorGrid, middleNodeSelectorGrid, rightNodeSelectorGrid)
        LongFormat.add(infoPane, 1, 2)
        LongFormat.alignment = Pos.TOP_CENTER

        changeSelectedNodeButton(zero)
        updateInfoPanel()
    }
    fun changeSelectedNodeButton(thisButton: Button) {
        //checks if previous button was cone or cube then sets its style
        if (allCubeButtons.contains(selectedNodeButton)) {
            selectedNodeButton.style = "-fx-background-color: #9900ff; -fx-border-width: $buttonBorderSize; -fx-border-color: black"
        } else if (allFloorButtons.contains(selectedNodeButton)) {
            selectedNodeButton.style = "-fx-background-color: #595959; -fx-border-width: $buttonBorderSize; -fx-border-color: black"
        } else {
            selectedNodeButton.style = "-fx-background-color: #FFFF00; -fx-border-width: $buttonBorderSize; -fx-border-color: black"
        }
        //adds red border around new selected button
        thisButton.style = thisButton.style + "; -fx-border-color: red; -fx-border-width: $selectedButtonBorderSize"

        selectedNodeButton = thisButton
        NTClient.setTables()
    }
    fun updateInfoPanel() {
        nodeLabel.text = "Node #: ${NodeDeck.selectedNode}"
    }
    fun switchOrientation() {
        clearGrids()
        if (flipped) {
            leftNodeSelectorGrid.addColumn(1, zero, one, two)
            leftNodeSelectorGrid.addColumn(2, three, four, five)
            leftNodeSelectorGrid.addColumn(3, six, seven, eight)
            leftNodeSelectorGrid.alignment = Pos.CENTER_LEFT
            middleNodeSelectorGrid.addColumn(1, nine, ten, eleven)
            middleNodeSelectorGrid.addColumn(2,twelve, thirteen, fourteen)
            middleNodeSelectorGrid.addColumn(3, fifteen, sixteen, seventeen)
            middleNodeSelectorGrid.alignment = Pos.CENTER
            rightNodeSelectorGrid.addColumn(1, eighteen, nineteen, twenty)
            rightNodeSelectorGrid.addColumn(2, twentyOne, twentyTwo, twentyThree)
            rightNodeSelectorGrid.addColumn(3, twentyFour, twentyFive, twentySix)
            rightNodeSelectorGrid.alignment = Pos.CENTER_RIGHT
            LongFormat.addRow(1, leftNodeSelectorGrid, middleNodeSelectorGrid, rightNodeSelectorGrid)
            flipped = false
        } else {
            leftNodeSelectorGrid.addColumn(3, two, one, zero)
            leftNodeSelectorGrid.addColumn(2, five, four, three)
            leftNodeSelectorGrid.addColumn(1, eight, seven, six)
            leftNodeSelectorGrid.alignment = Pos.CENTER_LEFT
            middleNodeSelectorGrid.addColumn(3, eleven, ten, nine)
            middleNodeSelectorGrid.addColumn(2, fourteen, thirteen, twelve)
            middleNodeSelectorGrid.addColumn(1, seventeen, sixteen, fifteen)
            middleNodeSelectorGrid.alignment = Pos.CENTER
            rightNodeSelectorGrid.addColumn(3, twenty, nineteen, eighteen)
            rightNodeSelectorGrid.addColumn(2, twentyThree, twentyTwo, twentyOne)
            rightNodeSelectorGrid.addColumn(1, twentySix, twentyFive, twentyFour)
            rightNodeSelectorGrid.alignment = Pos.CENTER_RIGHT
            LongFormat.addRow(1, rightNodeSelectorGrid, middleNodeSelectorGrid, leftNodeSelectorGrid)
            flipped = true
        }
    }
    fun clearGrids() {
        rightNodeSelectorGrid.children.removeAll(allButtons)
        middleNodeSelectorGrid.children.removeAll(allButtons)
        leftNodeSelectorGrid.children.removeAll(allButtons)
        LongFormat.children.removeAll(rightNodeSelectorGrid, middleNodeSelectorGrid, leftNodeSelectorGrid)
    }
}