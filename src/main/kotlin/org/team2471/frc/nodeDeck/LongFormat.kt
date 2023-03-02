package org.team2471.frc.nodeDeck

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.GridPane
import javafx.scene.layout.Region
import javafx.scene.text.Font

object LongFormat: GridPane() {
    private val nodeGridPane = GridPane()

    private val leftNodeSelectorGrid = GridPane()
    private val middleNodeSelectorGrid = GridPane()
    private val rightNodeSelectorGrid = GridPane()
    private val pieceTypeGrid = GridPane()
    private val infoPane = GridPane()
    private val nodeLabel = Label()
    private val spacer = Region()

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
    private val typeButton = Button()
//    private val cubeButton = Button()


    val buttonHeight: Double = 220.0
    val buttonWidth: Double = 208.0
    var selectedNodeButton: Button = one
    val buttonBorderSize = " 2 2 2 2" //format in " ## ## ## ##" Top Right Bottom Left
    val selectedButtonBorderSize = " 20 20 20 20"
    val fontSize = 50
    var flipped = false
    var isFloorCone = true

    val allButtons = listOf<Button>(zero, one, two, three, four, five, six, seven, eight, nine, ten, eleven, twelve, thirteen, fourteen, fifteen, sixteen, seventeen, eighteen, nineteen, twenty, twentyOne, twentyTwo, twentyThree, twentyFour, twentyFive, twentySix)
    val allFloorButtons = listOf<Button>(two, five, eight, eleven, fourteen, seventeen, twenty, twentyThree, twentySix)
    val allConeButtons = listOf<Button>(zero, one, six, seven, nine, ten, fifteen, sixteen, eighteen, nineteen, twentyFour, twentyFive)
    val allCubeButtons = listOf<Button>(three, four, twelve, thirteen, twentyOne, twentyTwo)

    init {
        println("LongFormat says hi")

        //assigns all buttons their values
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

        typeButton.setOnAction {
            switchFloorPieceType()
            NTClient.setTables()
        }
        switchFloorPieceType()
        typeButton.setMinSize(buttonWidth * 9, 165.0)
        typeButton.alignment = Pos.CENTER

        pieceTypeGrid.addRow(0, typeButton)
        pieceTypeGrid.alignment = Pos.CENTER


        flipButton.setPrefSize(290.0, 25.0)
        flipButton.font = Font(20.0)
        flipButton.setOnAction {
            println("FLIPPING")
            switchOrientation()
        }

        switchOrientation()

        nodeLabel.alignment = Pos.CENTER
        nodeLabel.font = Font(20.0)
        nodeLabel.style = "-fx-background-color: #f0f0f0; -fx-font-weight: bold; -fx-font-size: $fontSize px"
        nodeLabel.setPrefSize(290.0, 25.0)

        spacer.setPrefSize(0.0, 25.0)

        infoPane.addColumn(0, nodeLabel, flipButton, spacer)
        infoPane.setMinSize(buttonWidth * 3 + 15, 50.0)
        infoPane.alignment = Pos.TOP_CENTER

        nodeGridPane.setMinSize(buttonWidth * 9, buttonHeight * 3)
        nodeGridPane.add(infoPane, 1, 2)
        nodeGridPane.add(pieceTypeGrid, 1, 0)
        nodeGridPane.alignment = Pos.CENTER
        nodeGridPane.style = "-fx-border-width: 5 0 0 0; -fx-border-color: black"

        LongFormat.setMinSize(1400.0, 1000.0)
        LongFormat.add(typeButton, 0, 0)
        LongFormat.add(nodeGridPane, 0, 1)
        LongFormat.alignment = Pos.CENTER

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
            leftNodeSelectorGrid.addColumn(0, zero, one, two)
            leftNodeSelectorGrid.addColumn(1, three, four, five)
            leftNodeSelectorGrid.addColumn(2, six, seven, eight)
            leftNodeSelectorGrid.alignment = Pos.CENTER
            middleNodeSelectorGrid.addColumn(0, nine, ten, eleven)
            middleNodeSelectorGrid.addColumn(1,twelve, thirteen, fourteen)
            middleNodeSelectorGrid.addColumn(2, fifteen, sixteen, seventeen)
            middleNodeSelectorGrid.alignment = Pos.CENTER
            rightNodeSelectorGrid.addColumn(0, eighteen, nineteen, twenty)
            rightNodeSelectorGrid.addColumn(1, twentyOne, twentyTwo, twentyThree)
            rightNodeSelectorGrid.addColumn(2, twentyFour, twentyFive, twentySix)
            rightNodeSelectorGrid.alignment = Pos.CENTER
            nodeGridPane.addRow(1, leftNodeSelectorGrid, middleNodeSelectorGrid, rightNodeSelectorGrid)
            flipped = false
        } else {
            leftNodeSelectorGrid.addColumn(2, two, one, zero)
            leftNodeSelectorGrid.addColumn(1, five, four, three)
            leftNodeSelectorGrid.addColumn(0, eight, seven, six)
            leftNodeSelectorGrid.alignment = Pos.CENTER_LEFT
            middleNodeSelectorGrid.addColumn(2, eleven, ten, nine)
            middleNodeSelectorGrid.addColumn(1, fourteen, thirteen, twelve)
            middleNodeSelectorGrid.addColumn(0, seventeen, sixteen, fifteen)
            middleNodeSelectorGrid.alignment = Pos.CENTER
            rightNodeSelectorGrid.addColumn(2, twenty, nineteen, eighteen)
            rightNodeSelectorGrid.addColumn(1, twentyThree, twentyTwo, twentyOne)
            rightNodeSelectorGrid.addColumn(0, twentySix, twentyFive, twentyFour)
            rightNodeSelectorGrid.alignment = Pos.CENTER_RIGHT
            nodeGridPane.addRow(1, rightNodeSelectorGrid, middleNodeSelectorGrid, leftNodeSelectorGrid)
            flipped = true
        }
    }
    fun clearGrids() {
        rightNodeSelectorGrid.children.removeAll(allButtons)
        middleNodeSelectorGrid.children.removeAll(allButtons)
        leftNodeSelectorGrid.children.removeAll(allButtons)
        nodeGridPane.children.removeAll(rightNodeSelectorGrid, middleNodeSelectorGrid, leftNodeSelectorGrid)
    }
    fun switchFloorPieceType() {
        if (isFloorCone) {
            typeButton.style = "-fx-background-color: #9900ff; -fx-border-width: $buttonBorderSize; -fx-border-color: black"
            typeButton.graphic = ImageView(Image("cube-icon.png"))
            isFloorCone = false
        } else {
            typeButton.style = "-fx-background-color: #FFFF00; -fx-border-width: $buttonBorderSize; -fx-border-color: black"
            typeButton.graphic = ImageView(Image("cone-icon.png"))
            isFloorCone = true
        }
    }
}