package org.team2471.frc.nodeDeck

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.GridPane

object NodeSelector : GridPane() {
    private val one = Button("", ImageView(Image("cone-icon.png")))
    private val two = Button("", ImageView(Image("cube-icon.png")))
    private val three = Button("", ImageView(Image("cone-icon.png")))
    private val four = Button("", ImageView(Image("cone-icon.png")))
    private val five = Button("", ImageView(Image("cube-icon.png")))
    private val six = Button("", ImageView(Image("cone-icon.png")))
    private val seven = Button("")
    private val eight = Button("")
    private val nine = Button("")
    val buttonSideLength: Double = 400.0
    var selectedNodeButton: Button = one
    var selectedNode: Int = 1


    init {
        println("NodeSelector says hi!")

        one.setPrefSize(buttonSideLength, buttonSideLength)
        one.style = "-fx-background-color: #FFFF00"
        one.setOnAction {
            if (NTClient.isRed) {
                selectedNode = 1
            } else selectedNode = 3
            changeSelectedNodeButton(one)
            InformationPanel.updateInfoPanel()
        }

        two.setPrefSize(buttonSideLength, buttonSideLength)
        two.style = "-fx-background-color: #9900ff"
        two.setOnAction {
            selectedNode = 2
            changeSelectedNodeButton(two)
            InformationPanel.updateInfoPanel()
        }

        three.setPrefSize(buttonSideLength, buttonSideLength)
        three.style = "-fx-background-color: #FFFF00"
        three.setOnAction {
            if (NTClient.isRed) {
                selectedNode = 3
            } else selectedNode = 1
            changeSelectedNodeButton(three)
            InformationPanel.updateInfoPanel()
        }

        four.setPrefSize(buttonSideLength, buttonSideLength)
        four.style = "-fx-background-color: #FFFF00"
        four.setOnAction {
            if (NTClient.isRed) {
                selectedNode = 4
            } else selectedNode = 6
            changeSelectedNodeButton(four)
            InformationPanel.updateInfoPanel()
        }

        five.setPrefSize(buttonSideLength, buttonSideLength)
        five.style = "-fx-background-color: #9900ff"
        five.setOnAction {
            selectedNode = 5
            changeSelectedNodeButton(five)
            InformationPanel.updateInfoPanel()
        }

        six.setPrefSize(buttonSideLength, buttonSideLength)
        six.style = "-fx-background-color: #FFFF00"
        six.setOnAction {
            if (NTClient.isRed) {
                selectedNode = 6
            } else selectedNode = 4
            changeSelectedNodeButton(six)
            InformationPanel.updateInfoPanel()
        }

        seven.setPrefSize(buttonSideLength, buttonSideLength / 2 + 33.7)
        seven.style = "-fx-background-color: #595959"
        seven.setOnAction {
            if (NTClient.isRed) {
                selectedNode = 7
            } else selectedNode = 9
            changeSelectedNodeButton(seven)
            InformationPanel.updateInfoPanel()
        }

        eight.setPrefSize(buttonSideLength, buttonSideLength / 2 + 33.7)
        eight.style = "-fx-background-color: #595959"
        eight.setOnAction {
            selectedNode = 8
            changeSelectedNodeButton(eight)
            InformationPanel.updateInfoPanel()
        }

        nine.setPrefSize(buttonSideLength, buttonSideLength / 2 + 33.7)
        nine.style = "-fx-background-color: #595959"
        nine.setOnAction {
            if (NTClient.isRed) {
                selectedNode = 9
            } else selectedNode = 7
            changeSelectedNodeButton(nine)
            InformationPanel.updateInfoPanel()
        }

        NodeSelector.addRow(1, nine, eight, seven)
        NodeSelector.addRow(2, six, five, four)
        NodeSelector.addRow(3, three, two, one)
        NodeSelector.alignment = Pos.CENTER

        changeSelectedNodeButton(one)
    }
    fun changeSelectedNodeButton(thisButton: Button) {
        if (selectedNodeButton == two || selectedNodeButton == five) {
            selectedNodeButton.style = "-fx-background-color: #9900ff"
            selectedNodeButton.graphic = ImageView(Image("cube-icon.png"))
        } else if (selectedNodeButton == seven || selectedNodeButton == eight || selectedNodeButton == nine) {
            selectedNodeButton.style = "-fx-background-color: #595959"
            selectedNodeButton.graphic = ImageView()
        } else {
            selectedNodeButton.style = "-fx-background-color: #FFFF00"
            selectedNodeButton.graphic = ImageView(Image("cone-icon.png"))
        }

        thisButton.graphic = ImageView(Image("mean-logo.png"))
        thisButton.style = "-fx-background-color: #ff0000"

        selectedNodeButton = thisButton
    }
}