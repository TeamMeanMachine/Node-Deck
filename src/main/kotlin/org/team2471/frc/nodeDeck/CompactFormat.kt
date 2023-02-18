package org.team2471.frc.nodeDeck

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.GridPane
import javafx.scene.text.Font

object CompactFormat : GridPane() {
    private val nodeSelectorPane = GridPane()
    private val gridSelectorPane = GridPane()
    private val infoPane = GridPane()

    private val nodeLabel = Label()
    private val flipButton = Button("Flip Buttons")

    private val leftGrid = Button("Left")
    private val centerGrid = Button("Co-op")
    private val rightGrid = Button("Right")

    private val zero = Button("", ImageView(Image("cone-icon.png")))
    private val one = Button("", ImageView(Image("cone-icon.png")))
    private val two = Button("")
    private val three = Button("", ImageView(Image("cube-icon.png")))
    private val four = Button("", ImageView(Image("cube-icon.png")))
    private val five = Button("")
    private val six = Button("", ImageView(Image("cone-icon.png")))
    private val seven = Button("", ImageView(Image("cone-icon.png")))
    private val eight = Button("")


    val gridButtonHeight: Double = 200.0
    val gridButtonWidth: Double = 600.0
    val nodeButtonSideLength: Double = 400.0
    val buttonBorderSize = " 2 2 2 2" //format in " ## ## ## ##" Top Right Bottom Left
    val selectedButtonBorderSize = " 20 20 20 20"
    var flipped = false

    var selectedNodeButton: Button = one
    var selectedGridButton: Button = rightGrid

    var selectedNodeInGrid = 0
    var selectedGrid = 0

    init {
        println("CompactFormat says hi")

        //configure all buttons
        leftGrid.setPrefSize(gridButtonWidth, gridButtonHeight)
        leftGrid.style = "-fx-font-weight: bold; -fx-font-size: 60px; -fx-border-width: $buttonBorderSize; -fx-border-color: black"
        leftGrid.setOnAction {
            if (NTClient.isRed) {
                selectedGrid = 2
            } else selectedGrid = 0
            changeSelectedGridButton(leftGrid)
            updateInfoPanel()
        }
        centerGrid.setPrefSize(gridButtonWidth, gridButtonHeight)
        centerGrid.style = "-fx-font-weight: bold; -fx-font-size: 60px; -fx-border-width: $buttonBorderSize; -fx-border-color: black"
        centerGrid.setOnAction {
            changeSelectedGridButton(centerGrid)
            selectedGrid = 1
            updateInfoPanel()
        }
        rightGrid.setPrefSize(gridButtonWidth, gridButtonHeight)
        rightGrid.style = "-fx-font-weight: bold; -fx-font-size: 60px; -fx-border-width: $buttonBorderSize; -fx-border-color: black"
        rightGrid.setOnAction {
            if (NTClient.isRed) {
                selectedGrid = 0
            } else selectedGrid = 2
            changeSelectedGridButton(rightGrid)
            updateInfoPanel()
        }

        zero.setPrefSize(nodeButtonSideLength, nodeButtonSideLength)
        zero.style = "-fx-background-color: #FFFF00; -fx-border-width: ${buttonBorderSize}; -fx-border-color: black"
        zero.setOnAction {
            if (NTClient.isRed) {
                selectedNodeInGrid = 0
            } else selectedNodeInGrid = 27
            changeSelectedNodeButton(zero)
            updateInfoPanel()
        }
        one.setPrefSize(nodeButtonSideLength, nodeButtonSideLength)
        one.style = "-fx-background-color: #FFFF00; -fx-border-width: ${buttonBorderSize}; -fx-border-color: black"
        one.setOnAction {
            if (NTClient.isRed) {
                selectedNodeInGrid = 1
            } else selectedNodeInGrid = 28
            changeSelectedNodeButton(one)
            updateInfoPanel()
        }
        two.setPrefSize(nodeButtonSideLength, nodeButtonSideLength / 2 + 33.7)
        two.style = "-fx-background-color: #595959; -fx-border-width: ${buttonBorderSize}; -fx-border-color: black"
        two.setOnAction {
            if (NTClient.isRed) {
                selectedNodeInGrid = 2
            } else selectedNodeInGrid = 29
            changeSelectedNodeButton(two)
            updateInfoPanel()
        }
        three.setPrefSize(nodeButtonSideLength, nodeButtonSideLength)
        three.style = "-fx-background-color: #9900ff; -fx-border-width: ${buttonBorderSize}; -fx-border-color: black"
        three.setOnAction {
            if (NTClient.isRed) {
                selectedNodeInGrid = 3
            } else selectedNodeInGrid = 30
            changeSelectedNodeButton(three)
            updateInfoPanel()
        }
        four.setPrefSize(nodeButtonSideLength, nodeButtonSideLength)
        four.style = "-fx-background-color: #9900ff; -fx-border-width: ${buttonBorderSize}; -fx-border-color: black"
        four.setOnAction {
            if (NTClient.isRed) {
                selectedNodeInGrid = 4
            } else selectedNodeInGrid = 31
            changeSelectedNodeButton(four)
            updateInfoPanel()
        }
        five.setPrefSize(nodeButtonSideLength, nodeButtonSideLength / 2 + 33.7)
        five.style = "-fx-background-color: #595959; -fx-border-width: ${buttonBorderSize}; -fx-border-color: black"
        five.setOnAction {
            if (NTClient.isRed) {
                selectedNodeInGrid = 5
            } else selectedNodeInGrid = 32
            changeSelectedNodeButton(five)
            updateInfoPanel()
        }
        six.setPrefSize(nodeButtonSideLength, nodeButtonSideLength)
        six.style = "-fx-background-color: #FFFF00; -fx-border-width: ${buttonBorderSize}; -fx-border-color: black"
        six.setOnAction {
            if (NTClient.isRed) {
                selectedNodeInGrid = 6
            } else selectedNodeInGrid = 33
            changeSelectedNodeButton(six)
            updateInfoPanel()
        }
        seven.setPrefSize(nodeButtonSideLength, nodeButtonSideLength)
        seven.style = "-fx-background-color: #FFFF00; -fx-border-width: ${buttonBorderSize}; -fx-border-color: black"
        seven.setOnAction {
            if (NTClient.isRed) {
                selectedNodeInGrid = 7
            } else selectedNodeInGrid = 34
            changeSelectedNodeButton(seven)
            updateInfoPanel()
        }
        eight.setPrefSize(nodeButtonSideLength, nodeButtonSideLength / 2 + 33.7)
        eight.style = "-fx-background-color: #595959; -fx-border-width: ${buttonBorderSize}; -fx-border-color: black"
        eight.setOnAction {
            if (NTClient.isRed) {
                selectedNodeInGrid = 8
            } else selectedNodeInGrid = 35
            changeSelectedNodeButton(eight)
            updateInfoPanel()
        }


        flipButton.setPrefSize(190.0, 50.0)
        flipButton.setOnAction {
            flipButtons()
        }

        nodeLabel.alignment = Pos.CENTER
        nodeLabel.font = Font(20.0)
        nodeLabel.style = "-fx-background-color: #f0f0f0; -fx-font-weight: bold; -fx-font-size: 30px"
        nodeLabel.setPrefSize(190.0, 25.0)

        //put buttons and labels into GridPanes
        infoPane.addRow(0, nodeLabel)
        infoPane.addRow(1, flipButton)
        infoPane.setMinSize(170.0, 50.0)

        nodeSelectorPane.addRow(1, eight, five, two)
        nodeSelectorPane.addRow(2, seven, four, one)
        nodeSelectorPane.addRow(3, six, three, zero)
        nodeSelectorPane.alignment = Pos.CENTER

        gridSelectorPane.addRow(0, leftGrid, centerGrid, rightGrid)
        gridSelectorPane.alignment = Pos.TOP_CENTER

        CompactFormat.alignment = Pos.CENTER
        CompactFormat.add(gridSelectorPane, 0, 0)
        CompactFormat.add(nodeSelectorPane, 0, 1)
        CompactFormat.add(infoPane, 1, 1)

        changeSelectedGridButton(rightGrid)
        changeSelectedNodeButton(zero)
        updateInfoPanel()
    }
    fun changeSelectedNodeButton(thisButton: Button) {
        //checks if previous button was cone or cube then sets its style
        if (selectedNodeButton == three || selectedNodeButton == four) {
            selectedNodeButton.style = "-fx-background-color: #9900ff; -fx-border-width: ${buttonBorderSize}; -fx-border-color: black"
        } else if (selectedNodeButton == two || selectedNodeButton == five || selectedNodeButton == eight) {
            selectedNodeButton.style = "-fx-background-color: #595959; -fx-border-width: ${buttonBorderSize}; -fx-border-color: black"
        } else {
            selectedNodeButton.style = "-fx-background-color: #FFFF00; -fx-border-width: ${buttonBorderSize}; -fx-border-color: black"
        }
        //adds red border around new selected button
        thisButton.style = thisButton.style + "; -fx-border-color: red; -fx-border-width: $selectedButtonBorderSize"

        selectedNodeButton = thisButton
    }
    fun changeSelectedGridButton(thisButton: Button) {
        selectedGridButton.style = "-fx-font-weight: bold; -fx-font-size: 60px; -fx-border-color: black; -fx-border-width: $buttonBorderSize"
        thisButton.style = "-fx-font-weight: bold; -fx-font-size: 50px; -fx-border-color: red; -fx-border-width: $selectedButtonBorderSize"
        selectedGridButton = thisButton
    }
    fun updateInfoPanel() {
        NodeDeck.selectedNode = selectedGrid * 9 + selectedNodeInGrid
        nodeLabel.text = "Node #: ${NodeDeck.selectedNode}"
//        println("AHHHHHHHHHHHHH")
        NTClient.setTables()
    }
    fun flipButtons() {
        clearGrid()
        if (flipped) {
            nodeSelectorPane.addRow(1, eight, five, two)
            nodeSelectorPane.addRow(2, seven, four, one)
            nodeSelectorPane.addRow(3, six, three, zero)
            rightGrid.text = "Right"
            leftGrid.text = "Left"
            gridSelectorPane.addRow(0, leftGrid, centerGrid, rightGrid)
            flipped = false
        } else {
            nodeSelectorPane.addRow(1, zero, three, six)
            nodeSelectorPane.addRow(2, one, four, seven)
            nodeSelectorPane.addRow(3, two, five, eight)
            rightGrid.text = "Left"
            leftGrid.text = "Right"
            gridSelectorPane.addRow(0, rightGrid, centerGrid, leftGrid)
            flipped = true
        }
    }
    fun clearGrid() {
        nodeSelectorPane.children.removeAll(zero, one, two, three, four, five, six, seven, eight)
        gridSelectorPane.children.removeAll(rightGrid, centerGrid, leftGrid)
    }
}