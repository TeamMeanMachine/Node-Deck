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
    private val twentySeven = Button()
    val buttonSideLength: Double = 400.0
    var selectedNodeButton: Button = one
    val buttonBorderSize = " 2 2 2 2" //format in " ## ## ## ##" Top Right Bottom Left
    val selectedButtonBorderSize = " 10 10 10 10"

    val allButtons = listOf<Button>(one, two, three, four, five, six, seven, eight, nine, ten, eleven, twelve, thirteen, fourteen, fifteen, sixteen, seventeen, eighteen, nineteen, twenty, twentyOne, twentyTwo, twentyThree, twentyFour, twentyFive, twentySix, twentySeven)
    val allFloorButtons = listOf<Button>(seven, eight, nine, sixteen, seventeen, eighteen, twentyFive, twentySix, twentySeven)
    val allConeButtons = listOf<Button>(one, three, four, six, ten, twelve, thirteen, fifteen, nineteen, twentyOne, twentyTwo, twentyFour)
    val allCubeButtons = listOf<Button>(two, five, eleven, fourteen, twenty, twentyThree)

    init {

        var assignedButtonNode = 0
        for (button in allButtons) {
            assignedButtonNode += 1
            val thisButtonNode = assignedButtonNode
            button.setPrefSize(151.0, 151.0)
            button.setOnAction {
                if (!NTClient.isRed) {
                    NodeDeck.selectedNode = NTClient.reflectNodeNumbers(thisButtonNode)
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

        leftNodeSelectorGrid.addRow(1, one, two, three)
        leftNodeSelectorGrid.addRow(2, four, five, six)
        leftNodeSelectorGrid.addRow(3, seven, eight, nine)
        leftNodeSelectorGrid.alignment = Pos.CENTER_LEFT
        middleNodeSelectorGrid.addRow(1, ten, eleven, twelve)
        middleNodeSelectorGrid.addRow(2, thirteen, fourteen, fifteen)
        middleNodeSelectorGrid.addRow(3, sixteen, seventeen, eighteen)
        middleNodeSelectorGrid.alignment = Pos.CENTER
        rightNodeSelectorGrid.addRow(1, nineteen, twenty, twentyOne)
        rightNodeSelectorGrid.addRow(2, twentyTwo, twentyThree, twentyFour)
        rightNodeSelectorGrid.addRow(3, twentyFive, twentySix, twentySeven)
        rightNodeSelectorGrid.alignment = Pos.CENTER_RIGHT

        nodeLabel.alignment = Pos.CENTER
        nodeLabel.font = Font(20.0)
        nodeLabel.style = "-fx-background-color: #f0f0f0; -fx-font-weight: bold; -fx-font-size: 30px"
        nodeLabel.setPrefSize(190.0, 25.0)

        infoPane.addRow(0, nodeLabel)
        infoPane.setMinSize(480.0, 50.0)
        infoPane.alignment = Pos.TOP_CENTER

        LongFormat.setMinSize(1400.0, 1400.0)
        LongFormat.addRow(1, leftNodeSelectorGrid, middleNodeSelectorGrid, rightNodeSelectorGrid)
        LongFormat.add(infoPane, 1, 2)
        LongFormat.alignment = Pos.TOP_CENTER

        changeSelectedNodeButton(one)
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
}