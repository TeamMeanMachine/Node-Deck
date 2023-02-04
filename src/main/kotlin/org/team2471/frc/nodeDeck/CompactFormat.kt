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
    private val toggleAllianceButton = Button("Check Team Color")

    private val leftGrid = Button("Left")
    private val centerGrid = Button("Co-op")
    private val rightGrid = Button("Right")

    private val one = Button("", ImageView(Image("cone-icon.png")))
    private val two = Button("", ImageView(Image("cube-icon.png")))
    private val three = Button("", ImageView(Image("cone-icon.png")))
    private val four = Button("", ImageView(Image("cone-icon.png")))
    private val five = Button("", ImageView(Image("cube-icon.png")))
    private val six = Button("", ImageView(Image("cone-icon.png")))
    private val seven = Button("")
    private val eight = Button("")
    private val nine = Button("")


    val gridButtonHeight: Double = 200.0
    val gridButtonWidth: Double = 600.0
    val nodeButtonSideLength: Double = 400.0
    val buttonBorderSize = " 2 2 2 2" //format in " ## ## ## ##"
    val selectedButtonBorderSize = " 20 20 20 20"

    var selectedNodeButton: Button = one
    var selectedGridButton: Button = rightGrid
    var selectedGrid: Int = 0
    var selectedNode: Int = 0
    init {

        //configure all buttons
        leftGrid.setPrefSize(gridButtonWidth, gridButtonHeight)
        leftGrid.style = "-fx-font-weight: bold; -fx-font-size: 60px; -fx-border-width: $buttonBorderSize; -fx-border-color: black"
        leftGrid.setOnAction {
            if (NTClient.isRed) {
                selectedGrid = 18
            } else selectedGrid = 0
            changeSelectedGridButton(leftGrid)
            updateInfoPanel()
        }
        centerGrid.setPrefSize(gridButtonWidth, gridButtonHeight)
        centerGrid.style = "-fx-font-weight: bold; -fx-font-size: 60px; -fx-border-width: $buttonBorderSize; -fx-border-color: black"
        centerGrid.setOnAction {
            changeSelectedGridButton(centerGrid)
            selectedGrid = 9
            updateInfoPanel()
        }
        rightGrid.setPrefSize(gridButtonWidth, gridButtonHeight)
        rightGrid.style = "-fx-font-weight: bold; -fx-font-size: 60px; -fx-border-width: $buttonBorderSize; -fx-border-color: black"
        rightGrid.setOnAction {
            if (NTClient.isRed) {
                selectedGrid = 0
            } else selectedGrid = 18
            changeSelectedGridButton(rightGrid)
            updateInfoPanel()
        }

        one.setPrefSize(nodeButtonSideLength, nodeButtonSideLength)
        one.style = "-fx-background-color: #FFFF00; -fx-border-width: ${buttonBorderSize}; -fx-border-color: black"
        one.setOnAction {
            if (NTClient.isRed) {
                selectedNode = 1
            } else selectedNode = 3
            changeSelectedNodeButton(one)
            updateInfoPanel()
        }
        two.setPrefSize(nodeButtonSideLength, nodeButtonSideLength)
        two.style = "-fx-background-color: #9900ff; -fx-border-width: ${buttonBorderSize}; -fx-border-color: black"
        two.setOnAction {
            selectedNode = 2
            changeSelectedNodeButton(two)
            updateInfoPanel()
        }
        three.setPrefSize(nodeButtonSideLength, nodeButtonSideLength)
        three.style = "-fx-background-color: #FFFF00; -fx-border-width: ${buttonBorderSize}; -fx-border-color: black"
        three.setOnAction {
            if (NTClient.isRed) {
                selectedNode = 3
            } else selectedNode = 1
            changeSelectedNodeButton(three)
            updateInfoPanel()
        }
        four.setPrefSize(nodeButtonSideLength, nodeButtonSideLength)
        four.style = "-fx-background-color: #FFFF00; -fx-border-width: ${buttonBorderSize}; -fx-border-color: black"
        four.setOnAction {
            if (NTClient.isRed) {
                selectedNode = 4
            } else selectedNode = 6
            changeSelectedNodeButton(four)
            updateInfoPanel()
        }
        five.setPrefSize(nodeButtonSideLength, nodeButtonSideLength)
        five.style = "-fx-background-color: #9900ff; -fx-border-width: ${buttonBorderSize}; -fx-border-color: black"
        five.setOnAction {
            selectedNode = 5
            changeSelectedNodeButton(five)
            updateInfoPanel()
        }
        six.setPrefSize(nodeButtonSideLength, nodeButtonSideLength)
        six.style = "-fx-background-color: #FFFF00; -fx-border-width: ${buttonBorderSize}; -fx-border-color: black"
        six.setOnAction {
            if (NTClient.isRed) {
                selectedNode = 6
            } else selectedNode = 4
            changeSelectedNodeButton(six)
            updateInfoPanel()
        }
        seven.setPrefSize(nodeButtonSideLength, nodeButtonSideLength / 2 + 33.7)
        seven.style = "-fx-background-color: #595959; -fx-border-width: ${buttonBorderSize}; -fx-border-color: black"
        seven.setOnAction {
            if (NTClient.isRed) {
                selectedNode = 7
            } else selectedNode = 9
            changeSelectedNodeButton(seven)
            updateInfoPanel()
        }
        eight.setPrefSize(nodeButtonSideLength, nodeButtonSideLength / 2 + 33.7)
        eight.style = "-fx-background-color: #595959; -fx-border-width: ${buttonBorderSize}; -fx-border-color: black"
        eight.setOnAction {
            selectedNode = 8
            changeSelectedNodeButton(eight)
            updateInfoPanel()
        }
        nine.setPrefSize(nodeButtonSideLength, nodeButtonSideLength / 2 + 33.7)
        nine.style = "-fx-background-color: #595959; -fx-border-width: ${buttonBorderSize}; -fx-border-color: black"
        nine.setOnAction {
            if (NTClient.isRed) {
                selectedNode = 9
            } else selectedNode = 7
            changeSelectedNodeButton(nine)
            updateInfoPanel()
        }

        toggleAllianceButton.setPrefSize(190.0, 50.0)
        toggleAllianceButton.setOnAction {
            NTClient.printNTTopicConnection()
        }

        nodeLabel.alignment = Pos.CENTER
        nodeLabel.font = Font(20.0)
        nodeLabel.style = "-fx-background-color: #f0f0f0; -fx-font-weight: bold; -fx-font-size: 30px"
        nodeLabel.setPrefSize(190.0, 25.0)

        //put buttons and labels into GridPanes
        infoPane.addRow(0, nodeLabel)
        infoPane.addRow(1, toggleAllianceButton)
        infoPane.setMinSize(170.0, 50.0)

        nodeSelectorPane.addRow(1, nine, eight, seven)
        nodeSelectorPane.addRow(2, six, five, four)
        nodeSelectorPane.addRow(3, three, two, one)
        nodeSelectorPane.alignment = Pos.CENTER

        gridSelectorPane.addRow(0, leftGrid, centerGrid, rightGrid)
        gridSelectorPane.alignment = Pos.TOP_CENTER

        CompactFormat.alignment = Pos.CENTER
        CompactFormat.add(gridSelectorPane, 0, 0)
        CompactFormat.add(nodeSelectorPane, 0, 1)
        CompactFormat.add(infoPane, 1, 1)

        changeSelectedGridButton(rightGrid)
        changeSelectedNodeButton(one)
        updateInfoPanel()
    }
    fun changeSelectedNodeButton(thisButton: Button) {
        //checks if previous button was cone or cube then sets its style
        if (selectedNodeButton == two || selectedNodeButton == five) {
            selectedNodeButton.style = "-fx-background-color: #9900ff; -fx-border-width: ${buttonBorderSize}; -fx-border-color: black"
        } else if (selectedNodeButton == seven || selectedNodeButton == eight || selectedNodeButton == nine) {
            selectedNodeButton.style = "-fx-background-color: #595959; -fx-border-width: ${buttonBorderSize}; -fx-border-color: black"
        } else {
            selectedNodeButton.style = "-fx-background-color: #FFFF00; -fx-border-width: ${buttonBorderSize}; -fx-border-color: black"
        }
        //adds red border around new selected button
        thisButton.style = thisButton.style + "; -fx-border-color: red; -fx-border-width: $selectedButtonBorderSize"

        selectedNodeButton = thisButton
        NTClient.setTables()
    }
    fun changeSelectedGridButton(thisButton: Button) {
        selectedGridButton.style = "-fx-font-weight: bold; -fx-font-size: 60px; -fx-border-color: black; -fx-border-width: $buttonBorderSize"
        thisButton.style = "-fx-font-weight: bold; -fx-font-size: 50px; -fx-border-color: red; -fx-border-width: $selectedButtonBorderSize"
        selectedGridButton = thisButton
    }
    fun updateInfoPanel() {
        nodeLabel.text = "Node #: ${selectedNode + selectedGrid}"
    }
}